package ouch.transcoders.Normal;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import ouch.Readers.TextReadable;
import ouch.transcoders.Transformable;

public class MorseCodeTranscoder implements Transformable {
	private static HashMap<Byte, String> MORSE_MAP;
	
	public MorseCodeTranscoder() {
		if (MORSE_MAP == null) {
			MORSE_MAP = new HashMap<Byte, String>();
			MORSE_MAP.put((byte) 'A', ",--");
		}
	}

	@Override
	public String encode(TextReadable text) {
		String ret = "";			
	
		try {
			byte[] textToEncode = text.getEntireString().getBytes("US-ASCII");
			
			for (byte b : textToEncode) {
				ret += MORSE_MAP.get(b);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
				
		return ret;
	}

	@Override
	public String decode(TextReadable text) {
		String 
			ret = "",			
			textToDecode = text.getEntireString(),
			morseCodeUntilNow = "";		
		
		for (int i = 0; i < textToDecode.length(); i++) {
			morseCodeUntilNow += textToDecode.charAt(i);
			if (morseCodeUntilNow != null) { // TODO hit a morse code
				ret += morseCodeUntilNow;
				morseCodeUntilNow = "";
			}			
		}		
		
		return ret;
	}

}
