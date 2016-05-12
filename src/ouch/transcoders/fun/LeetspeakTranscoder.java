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

package ouch.transcoders.fun;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map.Entry;

import ouch.Readers.TextReadable;
import ouch.transcoders.EitherUpperOrLowerable;
import ouch.transcoders.Transformable;
import ouch.transcoders.EitherUpperOrLowerable.StringFormat;
import ouch.transcoders.Metricable;

public class LeetspeakTranscoder implements Transformable, EitherUpperOrLowerable {	
	static class LeetspeakMetrics implements Metricable {
		public LeetspeakMetrics() {
			this.substitutedCharactersAmount = 0;
		}
		
		int substitutedCharactersAmount;
		
		public int getSubstitutedCharactersAmount() {
			return this.substitutedCharactersAmount;
		}
		
		public void increaseSubstitutedCharacterAmount() {
			this.substitutedCharactersAmount++;
		}
		
		public String toString() {
			return "Substituted characters:" + "\t" + Integer.toString(this.substitutedCharactersAmount);
		}
	}
	
	private StringFormat encodeFormat;
	private StringFormat decodeFormat;
	private LeetspeakMetrics lastMetric = null;	
	
	private static HashMap<Character, String> LEET_MAP;
	
	public LeetspeakTranscoder() {
		this(StringFormat.UPPER, StringFormat.LOWER);
	}
	
	public LeetspeakTranscoder(StringFormat decodeFormat) {
		this(decodeFormat, StringFormat.LOWER);
	}
	
	public LeetspeakTranscoder(StringFormat decodeFormat, StringFormat encodeFormat) {
		if (LEET_MAP == null) {
			LEET_MAP = new HashMap<Character, String>();
			LEET_MAP.put('A', "4");
			LEET_MAP.put('B', "B");
            LEET_MAP.put('C', "C");
            LEET_MAP.put('D', "D");
            LEET_MAP.put('E', "3");
            LEET_MAP.put('F', "F");
            LEET_MAP.put('G', "G");
            LEET_MAP.put('H', "|-|");
            LEET_MAP.put('I', "!");
            LEET_MAP.put('J', "_|");
            LEET_MAP.put('K', "K");
            LEET_MAP.put('L', "|_");
            LEET_MAP.put('M', "|\\/|");
            LEET_MAP.put('N', "|\\|");
            LEET_MAP.put('O', "[]");
            LEET_MAP.put('P', "P");
            LEET_MAP.put('Q', "Q");
            LEET_MAP.put('R', "R");
            LEET_MAP.put('S', "$");
            LEET_MAP.put('T', "T");
            LEET_MAP.put('U', "|_|");
            LEET_MAP.put('V', "\\/");
            LEET_MAP.put('W', "|/\\|");
            LEET_MAP.put('X', "X");
            LEET_MAP.put('Y', "Y");
            LEET_MAP.put('Z', "2");
//            LEET_MAP.put('a', "a");
//            LEET_MAP.put('b', "b");
//            LEET_MAP.put('c', "c");
//            LEET_MAP.put('d', "d");
//            LEET_MAP.put('e', "e");
//            LEET_MAP.put('f', "f");
//            LEET_MAP.put('g', "g");
//            LEET_MAP.put('h', "h");
//            LEET_MAP.put('i', "i");
//            LEET_MAP.put('j', "j");
//            LEET_MAP.put('k', "k");
//            LEET_MAP.put('l', "l");
//            LEET_MAP.put('m', "m");
//            LEET_MAP.put('n', "n");
//            LEET_MAP.put('o', "o");
//            LEET_MAP.put('p', "p");
//            LEET_MAP.put('q', "q");
//            LEET_MAP.put('r', "r");
//            LEET_MAP.put('s', "s");
//            LEET_MAP.put('t', "t");
//            LEET_MAP.put('u', "u");
//            LEET_MAP.put('v', "v");
//            LEET_MAP.put('w', "w");
//            LEET_MAP.put('x', "x");
//            LEET_MAP.put('y', "y");
//            LEET_MAP.put('z', "z");
//            LEET_MAP.put('1', "1");
//            LEET_MAP.put('2', "2");
//            LEET_MAP.put('3', "3");
//            LEET_MAP.put('4', "4");
//            LEET_MAP.put('5', "5");
//            LEET_MAP.put('6', "6");
//            LEET_MAP.put('7', "7");
//            LEET_MAP.put('8', "8");
//            LEET_MAP.put('9', "9");
//            LEET_MAP.put('0', "0");
            LEET_MAP.put('-', "-");
            LEET_MAP.put('\'', "\\");
            LEET_MAP.put('=', "=");
            LEET_MAP.put(' ', " ");
		}
		this.decodeFormat = decodeFormat;
		this.encodeFormat = encodeFormat;
	}

	@Override
	public String encode(TextReadable text) {
		String ret = "";
		this.lastMetric = new LeetspeakMetrics();		

		try {
			byte[] textToEncode = null;			
			switch(this.encodeFormat) {
				case UPPER:
					textToEncode = text.getEntireString().toUpperCase().getBytes("US-ASCII");
					break;
				case LOWER:
					textToEncode = text.getEntireString().toLowerCase().getBytes("US-ASCII");
					break;
			}
			
			for (int i = 0; i < textToEncode.length; i++) {
				char charToEncode = (char) (textToEncode[i] & 0xFF);
				ret += LEET_MAP.get(Character.toUpperCase(charToEncode));
				this.lastMetric.increaseSubstitutedCharacterAmount();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return ret;
	}
	
	private static Character getCharacterForString(String character) {
		Character ret = null;
		for (Entry<Character, String> entry : LEET_MAP.entrySet()) {
            if (entry.getValue().equals(character)) {
                ret = entry.getKey(); 
            }
        }
		return ret;
	}

	@Override
	public String decode(TextReadable text) {
		String 
		ret = "",	
		morseCodeUntilNow = "",
		textToDecode = text.getEntireString();
		
		this.lastMetric = new LeetspeakMetrics();		
					
		for (int i = 0; i < textToDecode.length(); i++) {
			morseCodeUntilNow += textToDecode.charAt(i);
			Character adding = getCharacterForString(morseCodeUntilNow);
			if (adding != null) {			
				switch(this.encodeFormat) {
					case UPPER:
						ret += Character.toUpperCase(adding);
						break;
					case LOWER:
						ret += Character.toLowerCase(adding);
						break;
				}
				this.lastMetric.increaseSubstitutedCharacterAmount();
				morseCodeUntilNow = "";
			}
		}		
	
		return ret;
	}

	@Override
	public StringFormat getDecodeFormat() {
		return this.decodeFormat;
	}

	@Override
	public StringFormat getEncodeFormat() {
		return this.encodeFormat;
	}

	@Override
	public Metricable getLastDiff() {
		return this.lastMetric;
	}

}
