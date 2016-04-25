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

package ouch.tests.transcoders;

import static org.junit.Assert.*;

import org.junit.Test;

import ouch.Readers.StringReader;
import ouch.transcoders.Transformable;
import ouch.transcoders.fun.MirroredTranscoder;

public class MirroredTranscoderTest {
	public static final String[] DECODED_STRINGS = {
			"hello world",
			"AAAABBBAAAA",
			""
			//"Some test sentence which is a bit longer than usual."
	};
	
	public static final String[] ENCODED_STRINGS = {
			"dlrow olleh",
			"AAAABBBAAAA",
			""
	};

	@Test
	public void testEncode() {
		Transformable t = new MirroredTranscoder();
		for (int i = 0; i < DECODED_STRINGS.length; i++) {
			StringReader reader = new StringReader(DECODED_STRINGS[i]);
			assertEquals(t.encode(reader),
							ENCODED_STRINGS[i]);
		}
	}
	
	@Test
	public void testDecode() {
		Transformable t = new MirroredTranscoder();
		for (int i = 0; i < DECODED_STRINGS.length; i++) {
			StringReader reader = new StringReader(ENCODED_STRINGS[i]);
			assertEquals(t.decode(reader),
						DECODED_STRINGS[i]);
		}
	}	
}
