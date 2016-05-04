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

import org.junit.Before;
import org.junit.Test;

import ouch.transcoders.fun.MirroredTranscoder;

public class MirroredTranscoderTest extends TranscoderTest {	
	@Before
	public void setUp() throws Exception {
		ENCODED_STRINGS[0] = "dlrow olleh";
		ENCODED_STRINGS[1] = "AAAABBBAAAA";
		ENCODED_STRINGS[2] = "";
	}

	@Test
	public void testEncode() {
		this.testEncode(new MirroredTranscoder());
	}

        @Test
	public void testDecode() {
		this.testDecode(new MirroredTranscoder());
	}
}
