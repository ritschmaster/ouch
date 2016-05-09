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

import ouch.Readers.StringReader;
import ouch.transcoders.Transformable;

abstract public class TranscoderTest {
	public static final String[] DECODED_STRINGS = {
			"hello world",
			"aaaabbbaaaa",
			""
			//"Some test sentence which is a bit longer than usual."
	};
	protected String[] ENCODED_STRINGS = new String[3];
	
	protected void testEncode(Transformable transcoder) {
		for (int i = 0; i < DECODED_STRINGS.length; i++) {
			StringReader reader = new StringReader(DECODED_STRINGS[i]);
			assertEquals(ENCODED_STRINGS[i], transcoder.encode(reader));
							
		}
	}
	
	protected void testDecode(Transformable transcoder) {		
		for (int i = 0; i < DECODED_STRINGS.length; i++) {
			StringReader reader = new StringReader(ENCODED_STRINGS[i]);
			assertEquals(DECODED_STRINGS[i], transcoder.decode(reader));
							
		}
	}	
		
	abstract public void testEncode();
	abstract public void testDecode();
}
