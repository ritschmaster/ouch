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

package ouch.transcoders;

import java.util.ArrayList;

/**
 * @author Alexander Kopp
 * 
 * This transcoder mirrors a given text input
 *
 */
public class MirroredTranscoder extends Transcoder {

	@Override
	public String[] encode(String[] input) {
		ArrayList<String> output = new ArrayList<String>();
		for (int i = input.length-1; i >= 0; i--) {
			output.add(new StringBuilder(input[i]).reverse().toString());
		}
		return output.toArray(new String[output.size()]);
	}

	@Override
	public String[] decode(String[] input) {
		return encode(input);
	}

}
