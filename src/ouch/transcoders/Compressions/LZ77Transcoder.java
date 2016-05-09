/************************************************************************
 * Copyright (C) 2016 Richard Paul Bäck <richard.baeck@free-your-pc.com>
 * 					  Dominik Koller <kollerdominik@icloud.com>
 * 					  Alexander Kopp <alexander.kopp@gmx.at>
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

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;

import ouch.Readers.TextReadable;
import ouch.tools.FixedSizeStack;
import ouch.transcoders.Metricable;
import ouch.transcoders.Transformable;

public class LZ77Transcoder implements Transformable {
	
	private static final int SEARCH_BUFFER_SIZE = 4094; //A larger number means better compression, but worse performance (MAX  4Kb - 1 Byte)
	private static final int LOOKAHEAD_BUFFER_SIZE = 15; //maximum length (4 bit)
	private static final char	FILE_SEPERATOR = (char) 28;
	
	private StringBuilder outString;

	@Override
	public String encode(TextReadable text) {
		outString = new StringBuilder();
		LinkedList<Character> lookAheadBuffer = new LinkedList<Character>();
		
		//fill look ahead buffer
		//TODO DEBUG
		Timestamp t1 =  new Timestamp(System.currentTimeMillis());
		System.out.println("START      : " + t1);
		//DEBUG
		
		//FIXME bottleneck!!
		char[] chars = text.getEntireString().toCharArray();
		
		//TODO DEBUG
		System.out.println("GOT CHARS  : " + new Timestamp(System.currentTimeMillis()));
		//DEBUG
		
		for (char c : chars) {
			lookAheadBuffer.add(c);
		}
		lookAheadBuffer.add(FILE_SEPERATOR);
		Timestamp t2 =  new Timestamp(System.currentTimeMillis());
		System.out.println("BUFFER FLLD: " + t2);
		
		//FIXME bottleneck!!
		FixedSizeStack<Character> searchBuffer = new FixedSizeStack<Character>(SEARCH_BUFFER_SIZE);
		
		long lng = 0;
		
		while (lookAheadBuffer.size() > 0) {
			int index = 0;
		    int length = 0; 
		   
		    //search buffer for best (longest) result
		    
		    //TODO DEBUG
		    if (lookAheadBuffer.size() > 4000 && (lng % 10000 == 0 || lng % 10001 == 0)) {
		    	System.out.println("BEGIN SRCH  : " + new Timestamp(System.currentTimeMillis()));
		    }
		    lng++;
		    //DEBUG
		    
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
		    		} 
		    		
		    		//longest encodeable result found, 
		    		if (length >= LOOKAHEAD_BUFFER_SIZE) {
		    			break;
		    		}
		    	}
		    }

		    //TODO DEBUG
		    if (lookAheadBuffer.size() > 4000 && (lng % 10000 == 0 || lng % 10001 == 0)) {
		    	System.out.println("END SEARCH  : " + new Timestamp(System.currentTimeMillis()));
		    }
		    //DEBUG
		    
		    if (index != 0 && length > 0) {
	    		outString.append(new Triple(index, length, lookAheadBuffer.get(length)).str);
	    		//System.out.print("(" + index + "," + length + "," + lookAheadBuffer.get(length) + ")");

		    	for (int j = 0; j <= length; j++) {
		    		searchBuffer.push(lookAheadBuffer.removeFirst());	
				}
		    	
		    } else {
		    	char c = lookAheadBuffer.removeFirst();
		    	searchBuffer.push(c);
		    	outString.append(new Triple(0,0, c).str);
		    	//System.out.print("(" + 0 + "," + 0 + "," + c + ")");	
		    }	
		}
		System.out.println("END        : " + new Timestamp(System.currentTimeMillis()));
		return outString.toString();
	}
	
	@Override
	public String decode(TextReadable text) {
		String input = text.getEntireString();
		outString = new StringBuilder();
		
		for (int i = 0; i < input.length(); i = i + 3) {
			Triple t = new Triple(input.substring(i, i+3));
			
			//System.out.print("(" + t.offset + "," + t.length + "," + t.followChar + ")");
			
			if (t.length != 0 || t.offset != 0) {			
				int beginIndex = outString.length() - t.offset;
				int endIndex = beginIndex + t.length;
				
				outString.append(outString.toString().substring(beginIndex, endIndex));	
			}
			outString.append(t.followChar);
		}
		return outString.toString();
	}
	
//	public static void main(String[] args) {			
//		//Quick Test - semms to work TODO: write Unit-Test
//		LZ77Transcoder trc = new LZ77Transcoder();
//		String str = "The long-string instrument is an instrument in which the string is of such a length that the fundamental transverse wave is below what a person can hear as a tone (±20 Hz). If the tension and the length result in sounds with such a frequency, the tone becomes a beating frequency that ranges from a short reverb (approx 5–10 meters) to longer echo sounds (longer than 10 meters). Besides the beating frequency, the string also gives higher pitched natural overtones. Since the length is that long, this has an effect on the attack tone. The attack tone shoots through the string in a longitudinal wave and generates the typical science-fiction laser-gun sound as heard in Star Wars.[1] The sound is also similar to that occurring in upper electricity cables for trains (which are ready made long-string instruments in a way).";
//		String str2 = "In Ulm, um Ulm, und um Ulm herum.";
//		String str3 = "abracadabra";
//				
//				
//		String s = trc.encode(new StringReader(str3));
//		String out = trc.decode(new StringReader(s));
//		System.out.println("BEFORE: " + str3);
//		System.out.println("AFTER:  " + out);
//		
//	}

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

	@Override
	public Metricable getLastDiff() {
		// TODO Auto-generated method stub
		return null;
	}
}
