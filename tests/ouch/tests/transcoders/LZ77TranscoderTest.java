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

package ouch.tests.transcoders;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ouch.Readers.StringReader;
import ouch.transcoders.Transformable;
import ouch.transcoders.Compressions.LZ77Transcoder;

public class LZ77TranscoderTest {
	public static final char FILE_SEPERATOR = (char) 28;
	public static final String[] SHORT_STRINGS = {
			"hello world",
			"abracadabra",
			"abababababab",
			"aaaaaaaaaaaaaaa",
			"aaaaaaaaaaaaaab"
			
			//"Some test sentence which is a bit longer than usual."
	};
	protected String[] ENCODED_STRINGS = new String[3];
	
//	protected void testEncode(Transformable transcoder) {
//		for (int i = 0; i < DECODED_STRINGS.length; i++) {
//			StringReader reader = new StringReader(DECODED_STRINGS[i]);
//			assertEquals(ENCODED_STRINGS[i], transcoder.encode(reader));
//							
//		}
//	}
//	
//	protected void testDecode(Transformable transcoder) {		
//		for (int i = 0; i < DECODED_STRINGS.length; i++) {
//			StringReader reader = new StringReader(ENCODED_STRINGS[i]);
//			assertEquals(DECODED_STRINGS[i], transcoder.decode(reader));
//							
//		}
//	}	
		
	@Test
	public void testEncodeDecodeShortStrings() {
		for (String s : SHORT_STRINGS) {
			LZ77Transcoder t = new LZ77Transcoder();
			String s1 = t.encode(new StringReader(s));
			String out = t.decode(new StringReader(s1));
			assertEquals(s + FILE_SEPERATOR, out);
		}
	}
	
	@Test
	public void testEncodeDecodeMediumStrings() {
		
	}
	
	@Test
	public void testEncodeDecodeLongString() {
		
	}
	
	@Test
	public void testEncodeDecodeFile() {
		
	}
	
	@Test
	public void testEncodeTriple() {
		
	}
	
	@Test
	public void testDecodeTriple() {
		
	}
	

}
