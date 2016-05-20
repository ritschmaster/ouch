
/************************************************************************
 * Copyright (C) 2016 Richard Paul Bäck <richard.baeck@free-your-pc.com>
 * 					  Dominik Koller <kollerdominik@icloud.com>
 * 					  Alexander Kopp <alexander.kopp@gmx.at>
 * 					  Zhe Wu <wuzhe1996@gmail.com>
 *
 * This file is part of OUCH.
 *
 * OUCH is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OUCH is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OUCH. If not, see <http://www.gnu.org/licenses/>.
 ***********************************************************************/

package ouch.transcoders.Compressions;

import java.util.LinkedList;

import ouch.Readers.TextReadable;
import ouch.transcoders.tools.FixedSizeStack;
import ouch.transcoders.Metricable;
import ouch.transcoders.Transformable;
import static ouch.transcoders.tools.LZ77Globals.*;

public class LZ77Transcoder implements Transformable {
	private boolean endReached;
	private StringBuilder outString;
	LinkedList<Character> lookAheadBuffer;
	LZ77Metrics metrics;
	
	public LZ77Transcoder() {
		this.outString = new StringBuilder();
		this.lookAheadBuffer = new LinkedList<Character>();
		this.endReached = false;
		this.metrics = new LZ77Metrics();
	}

	@Override
	public String encode(TextReadable text) {
		metrics.reset();
		metrics.setModeToEncode();

		refillLookAheadBuffer(LOOKAHEAD_BUFFER_SIZE - lookAheadBuffer.size(), text);		
		
		FixedSizeStack<Character> searchBuffer = new FixedSizeStack<Character>(SEARCH_BUFFER_SIZE);		
		
		while (lookAheadBuffer.size() > 0) {
			int index = 0;
		    int length = 0; 
		    
		    for (int i = 0; i < searchBuffer.size(); i++) {
		    	int newIndex = 0;
				int newLength = 0;
		    	
		    	if (lookAheadBuffer.getFirst() == searchBuffer.get(i)) {
		    		newIndex = searchBuffer.size() - i;
		    		newLength++;
		
		    		while((newLength < lookAheadBuffer.size()) && (i+newLength < searchBuffer.size()) && (lookAheadBuffer.get(newLength) == searchBuffer.get(i+newLength))) { 
		    			if (newLength >= 15)  {
		    				break;
		    			} else {
		    				newLength++;
		    			}
		    		}

		    		if (newLength >= length) {
		    			length = newLength;
		    			index = newIndex;
		    			if (length >= LOOKAHEAD_BUFFER_SIZE) {
			    			break;
			    		}
		    		} 
		    		
		    		
		    	}
		    }
		    
		    if (index != 0 && length > 0) {
	    		outString.append(new Triple(index, length, lookAheadBuffer.get(length)).str);
	    		
		    	for (int j = 0; j <= length; j++) {
		    		searchBuffer.push(lookAheadBuffer.removeFirst());	
				}
		    	
		    } else {
		    	char c = lookAheadBuffer.removeFirst();
		    	searchBuffer.push(c);
		    	outString.append(new Triple(0,0, c).str);
		    }
		    
		    searchBuffer.trim();
	
			refillLookAheadBuffer(LOOKAHEAD_BUFFER_SIZE - lookAheadBuffer.size() + 1, text);
		}

		metrics.increaseSizeAfter(outString.length());
		return outString.toString();
	}
		
	private void refillLookAheadBuffer(int amount, TextReadable text) {
		char[] chars = text.getNextChars(amount);
		int i = 0;
		
		if (endReached) {
			return;
		} 
		
		if (chars == null) {
			if (amount != 0) {
				endReached = true;
				lookAheadBuffer.add(FILE_SEPERATOR);
				i++;
			}
		} else {
			for (char c : chars) {
				lookAheadBuffer.add(c);
				i++;
			}
		}
		
		metrics.increaseSizeBefore(i);
		
	}
	
	@Override
	public String decode(TextReadable text) {
		metrics.reset();
		metrics.setModeToDecode();
		String input = text.getEntireString();
		metrics.increaseSizeBefore(input.length());
		outString = new StringBuilder(input.length());
		
		for (int i = 0; i < input.length(); i = i + 3) {
			Triple t = new Triple(input.substring(i, i+3));
			
			if (t.length != 0 || t.offset != 0) {			
				int beginIndex = outString.length() - t.offset;
				int endIndex = beginIndex + t.length;
				
				for(; beginIndex < endIndex; beginIndex++) {
					outString.append(outString.charAt(beginIndex));
				}	
			}
			outString.append(t.followChar);
		}
		
		metrics.increaseSizeAfter(outString.length());
		return new String(outString);
	}
	

	/*	Representing Triple (offset, length, character) for LZ77
	 *  Output String encoded as follows:
	 *  
	 * |------------|--------------------------------------|------------------------|
	 * |  Length    |				Offset				   | 		Character		|
	 * |------------|--------------------------------------|------------------------|
	 * | 4  3  2  1 | 12 11 10  9 | 8  7  6  5  4  3  2  1 | 8  7  6  5  4  3  2  1 | 
	 * |--------------------------|------------------------|------------------------|
	 * |           Byte 1         |         Byte 2         |		  Byte 3        | 
	 * |--------------------------|------------------------|------------------------|
	 * 
	 * 	
	 */
	private static class Triple {
		String str;
		
		int length;
		int offset;
		char followChar;
		
		public Triple(String str) {
			this.str = str;
			assert str.length() == 3;
			unpack();
		}
		
		public Triple(int offset, int length, char followChar) {
			this.length = length;
			this.offset = offset;
			this.followChar = followChar;
			pack();
		}

		private void unpack() {
			this.followChar = str.charAt(2);
			
			byte b = (byte) (str.charAt(0) >>> 4);
			this.length = (b & 0b00001111);
			
			int i1 = str.charAt(0) << 8;
			i1 = i1 & 0xFFF;
			int i2 = str.charAt(1) & 0xFF;
			this.offset = i1 | i2;
		}
		
		private void pack() {
			char[] c = new char[3];
			byte b1, b2;
			
			b1 = (byte) (length << 4);
			b1 = (byte) (b1 & 0b11110000);
						
			b2 = (byte) (offset >>> 8);
			b2 = (byte) (b2 & 0b00001111);
						
			c[0] = (char) (b1 | b2);
			
			b1 = (byte) offset;
			c[1] = (char) b1;
			
			c[2] = followChar;
			
			this.str =  new String(c);
		}
	}
	
	private static class LZ77Metrics implements Metricable {
		
		private String mode;
		private int sizeBefore;
		private int sizeAfter;
		
		
		public LZ77Metrics() {
			reset();		
		}
		
		public void reset() {
			this.mode = null;
			this.sizeBefore = 0;
			this.sizeAfter = 0;
		}
		
		public void setModeToEncode() {
			this.mode = "En";
		}
		
		public void setModeToDecode() {
			this.mode = "De";
		}
		
		public void increaseSizeBefore(int amount) {
			this.sizeBefore += amount;
		}
		
		public void increaseSizeAfter(int amount) {
			this.sizeAfter += amount;
		}
		
		private double calculateCompressionPercentage() {
			
			return (double)(100/(double)sizeBefore) * sizeAfter;
		}

		@Override
		public String toString() {
			
			StringBuilder sb = new StringBuilder();
			sb.append(mode + "coding Statistics\n\n");
			sb.append("Source  size: " + sizeBefore);
			sb.append(" Characters\n");
			sb.append(mode + "coded size: " + sizeAfter);
			sb.append(" Characters\n\nSize of ");
			sb.append(mode);
			sb.append("coded String is " + calculateCompressionPercentage());
			sb.append("% of the source one");
			
			
			
			return sb.toString();
		}	
	}

	@Override
	public Metricable getLastDiff() {
		return metrics;
	}
}
