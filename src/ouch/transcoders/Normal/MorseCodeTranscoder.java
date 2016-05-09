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

package ouch.transcoders.Normal;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map.Entry;

import ouch.Readers.TextReadable;
import ouch.transcoders.EitherUpperOrLowerable;
import ouch.transcoders.Metricable;
import ouch.transcoders.Transformable;

public class MorseCodeTranscoder implements Transformable, EitherUpperOrLowerable {	
	private static HashMap<Character, String> MORSE_MAP;
	
	private StringFormat encodeFormat;
	private StringFormat decodeFormat;
	
	public MorseCodeTranscoder() {
		this(StringFormat.LOWER, StringFormat.LOWER);
	}
	
	public MorseCodeTranscoder(StringFormat decodeFormat) {
		this(decodeFormat, StringFormat.LOWER);
	}
	
	public MorseCodeTranscoder(StringFormat decodeFormat, StringFormat encodeFormat) {
		if (MORSE_MAP == null) {
			MORSE_MAP = new HashMap<Character, String>();
			MORSE_MAP.put('A', ".-");
			MORSE_MAP.put('B', "-...");
            MORSE_MAP.put('C', "-.-.");
            MORSE_MAP.put('D', "-..");
            MORSE_MAP.put('E', ".");
            MORSE_MAP.put('F', "..-.");
            MORSE_MAP.put('G', "--.");
            MORSE_MAP.put('H', "....");
            MORSE_MAP.put('I', "..");
            MORSE_MAP.put('J', ".---");
            MORSE_MAP.put('K', "-.-");
            MORSE_MAP.put('L', ".-..");
            MORSE_MAP.put('M', "--");
            MORSE_MAP.put('N', "-.");
            MORSE_MAP.put('O', "---");
            MORSE_MAP.put('P', ".--.");
            MORSE_MAP.put('Q', "--.-");
            MORSE_MAP.put('R', ".-.");
            MORSE_MAP.put('S', "...");
            MORSE_MAP.put('T', "-");
            MORSE_MAP.put('U', "..-");
            MORSE_MAP.put('V', "...-");
            MORSE_MAP.put('W', ".--");
            MORSE_MAP.put('X', "-..-");
            MORSE_MAP.put('Y', "-.--");
            MORSE_MAP.put('Z', "--..");
            MORSE_MAP.put('1', ".----");
            MORSE_MAP.put('2', "..---");
            MORSE_MAP.put('3', "...--");
            MORSE_MAP.put('4', "....-");
            MORSE_MAP.put('5', ".....");
            MORSE_MAP.put('6', "-....");
            MORSE_MAP.put('7', "--...");
            MORSE_MAP.put('8', "---..");
            MORSE_MAP.put('9', "----.");
            MORSE_MAP.put('0', "-----");
            MORSE_MAP.put('-', "-....-");
            MORSE_MAP.put('\'', ".----.");
            MORSE_MAP.put('=', "-...-");
            MORSE_MAP.put(' ', "/");
		}
		this.decodeFormat = decodeFormat;
		this.encodeFormat = encodeFormat;
	}

	@Override
	public String encode(TextReadable text) {
		String ret = "";

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
				ret += MORSE_MAP.get(Character.toUpperCase(charToEncode));
				if (i + 1 < textToEncode.length)
					ret += " ";				
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return ret;
	}
	
	private static Character getCharacterForString(String character) {
		Character ret = null;
		for (Entry<Character, String> entry : MORSE_MAP.entrySet()) {
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
						
		for (int i = 0; i < textToDecode.length(); i++) {
			if (textToDecode.charAt(i) != ' ') {
				morseCodeUntilNow += textToDecode.charAt(i);
			}
			if (textToDecode.charAt(i) == ' '
				|| i + 1 >= textToDecode.length()) {
				char adding = getCharacterForString(morseCodeUntilNow);				
				switch(this.encodeFormat) {
					case UPPER:
						ret += Character.toUpperCase(adding);
						break;
					case LOWER:
						ret += Character.toLowerCase(adding);
						break;
				}				
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
		// TODO Auto-generated method stub
		return null;
	}

}
