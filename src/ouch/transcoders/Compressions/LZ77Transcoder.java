
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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import ouch.Readers.FileTextReader;
import ouch.Readers.StringReader;
import ouch.Readers.TextReadable;
import ouch.transcoders.tools.FixedSizeStack;
import ouch.transcoders.Metricable;
import ouch.transcoders.Transformable;

import static ouch.transcoders.tools.LZ77Globals.*;

public class LZ77Transcoder implements Transformable {
	private boolean endReached;
	private boolean isFile;
	private StringBuilder outString;
	LinkedList<Character> lookAheadBuffer;
	LZ77Metrics metrics;

	public LZ77Transcoder() {
		this.outString = new StringBuilder();
		this.lookAheadBuffer = new LinkedList<Character>();
		this.metrics = new LZ77Metrics();
		this.endReached = false;
		this.isFile = false;
	}

	/*
	 *(non-Javadoc)
	 * @see ouch.transcoders.Transformable#encode(ouch.Readers.TextReadable)
	 */
	@Override
	public String encode(TextReadable text) {
		String path = null;
	
		metrics.reset();
		metrics.setModeToEncode();

		//File handling if path entered
		File file = getFile(text);

		if (file != null) {
			try {
				path = file.getAbsolutePath();
				text = new FileTextReader(path);
			} catch (NullPointerException e) {
				return "no such file";
			}
				
		} else {
			path =  System.getProperty("user.home") + File.separator + "LZ77_" + System.currentTimeMillis();
		}
		//end file handling
		
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
		    			if (newLength >= LOOKAHEAD_BUFFER_SIZE)  {
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
		PrintWriter writer;
	
		try {
			writer = new PrintWriter(new File(path + "_output"));
			writer.print(outString.toString());
			writer.close();
		} catch (IOException e) {
			return "writing file failed, try again";
		}
		
		return "saved under " + path + "_output\n\nOutput String:\n" + new String(outString);
	}
	
	/**
	 * @param amount - The amount of chars you want
	 * @param text - given reader
	 *
	 * 	Fills the look ahead buffer with the next amount chars. If there are fewer available then 
	 *	requested the buffer is filled with the rest. If none available returns null
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see ouch.transcoders.Transformable#decode(ouch.Readers.TextReadable)
	 */
	@Override
	public String decode(TextReadable text) {
		metrics.reset();
		metrics.setModeToDecode();
		isFile = false;
		
		File file = getFile(text);

		if (file != null) {
			text = new FileTextReader(file.getAbsolutePath());
		}
		
		StringBuilder in = new StringBuilder(LOOKAHEAD_BUFFER_SIZE);
		char[] chars = null;
		do {
			chars = text.getNextChars(LOOKAHEAD_BUFFER_SIZE);
			if (chars != null) {
				in.append(new String(chars));
			}	
		} while (chars != null);

		if (!isFile) {
			return "LZ77 String could not be parsed, use path to File instead";
		}
		
		String input = in.toString();
		metrics.increaseSizeBefore(input.length());
		outString = new StringBuilder();

		for (int i = 0; i < input.length(); i = i + 3) {
			Triple t = null;
			try {
				t = new Triple(input.substring(i, i+3));
			} catch (StringIndexOutOfBoundsException e) {
				break;
			}

			if (t.length != 0 || t.offset != 0) {			
				int beginIndex = outString.length() - t.offset;
				int endIndex = beginIndex + t.length;
				
				for(; beginIndex < endIndex; beginIndex++) {
					try {
						outString.append(outString.charAt(beginIndex));
					} catch (StringIndexOutOfBoundsException e) {
						return "Error when decoding, probably invalid or broken file";
					}
				}					
			}
			outString.append(t.followChar);
		}

		metrics.increaseSizeAfter(outString.length());
		return new String(outString);
	}
	
	/**
	 * @param text - given reader
	 * @returns File if given text contains a valid path to a file, else retruns null.
	 */
	private File getFile(TextReadable text) {
		if (text instanceof FileTextReader) {
			isFile = true;
			return null;
			
		} else if (text instanceof StringReader){
			
			String path;
			try {
				path = new String(text.getNextLines(1));
			} catch (NullPointerException e) {
				return null;
			}

			File file = new File(path);
			if (file.exists()) {
				this.isFile = true;
				return file;
			} else {
				return null;
			}
		} else {
			return null;
		}
		
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
	
	/*
	 * Implementation of Metrics, outputs No of Chars of source- and output files, and compression percentage
	 */
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
