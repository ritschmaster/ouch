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

package ouch.Readers;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import ouch.transcoders.Metricable;

public class StringReader implements TextReadable {
	private String text;
	private String charset;
	private int currentPosInBytesOfText;
	private boolean endReached;
	private int currentPosinCharsOfText;
	
	public StringReader(String text) {
		this(text, "US-ASCII");
	}

	public StringReader(String text, String charset) {
		this(text, charset, null);
	}
	
	public StringReader(String text, 
						String charset, 
						Metricable metrics) {
		this.text = text;
		this.endReached = false;
		this.currentPosInBytesOfText = 0;
		this.currentPosinCharsOfText = 0;
	}

	@Override
	public String getEntireString() {
		return this.text;
	}

	@Override
	public byte[] getNextBytes(int amount) {
		byte[] textAsBytes = null;
		int newPos;
		try {
			textAsBytes = this.text.getBytes(this.charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (this.currentPosInBytesOfText + amount 
				> textAsBytes.length) {
			
			this.endReached = true;			
			newPos = textAsBytes.length - 1;
		} else {
			newPos = this.currentPosInBytesOfText + amount;
		}
		textAsBytes = Arrays.copyOfRange(textAsBytes, 
										 this.currentPosInBytesOfText,
										 newPos);
		return textAsBytes;
	}

	@Override
	public char[] getNextLines(int noOfLines) {
		if (currentPosinCharsOfText >= text.length()) {
			return null;
		} else {
			StringBuilder sb = new StringBuilder();
			for (;currentPosinCharsOfText < text.length(); currentPosinCharsOfText++) {
				char c = text.charAt(currentPosinCharsOfText);
				sb.append(c);
				
				if (c == '\n') {
					noOfLines--;
				}
				if (noOfLines <= 0) {
					currentPosinCharsOfText++;
					break;
				}
			}
			return sb.toString().toCharArray();
			
			
		}
		
	}
	
	@Override
	public boolean canReadBytes() {
		return this.endReached;
	}

	@Override
	public void resetByteReader() {
		this.currentPosInBytesOfText = 0;
		this.endReached = false;
	}
	
	@Override
	public String getCharset() {
		return this.charset;
	}

}
