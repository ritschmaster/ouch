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

package ouch;

import ouch.transcoders.Transcoder;

/**
 * @author Alexander Kopp
 * @author Dominik Koller
 * @author Richard Paul Bäck
 * @author Zhe Wu
 *
 */
public class OUCH {
	
	//TODO
	/**
	 * @param path - String containing path to file to read
	 * @returns String array w/ file contents
	 */
	public String[] parseFile(String path) {
		
		return null;
	}
	/**
	 * @param path - String containing path to file to write
	 * @returns true if file successfully written, false if not
	 */
	public boolean writeFile(String path) {
		
		return true;
	}
	//END TODO
	
	/**
	 * @param t - the given transcoder
	 * @param input - input string(s)
	 * @returns string array containing the encoded text
	 */
	public String[] encode(Transcoder t, String[] input) {
		
		return t.encode(input);
	}
	
	/**
	 * @param t - the given transcoder
	 * @param input - input string(s)
	 * @returns string array containing the decoded text
	 */
	public String[] decode(Transcoder t, String[] input) {
		
		
		return t.decode(input);
	}
	
	//TODO Metrics
	
	
	
	
	
}
