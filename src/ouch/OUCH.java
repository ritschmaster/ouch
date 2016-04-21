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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ouch.transcoders.Transformable;

/**
 * @author Alexander Kopp
 * @author Dominik Koller
 * @author Richard Paul Bäck
 * @author Zhe Wu
 *
 */
public class OUCH {
	
	/**
	 * @param path - String containing path to file to read
	 * @throws IOException when no such File or I/O error 
	 * @returns String array w/ file contents
	 */
	public String[] parseFile(String path) throws IOException {

		ArrayList<String> lines = new ArrayList<String>();	
		
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
			
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		}String line;

		return lines.toArray(new String[lines.size()]);
	}
	/**
	 * @param path - String containing path to file to write
	 * @returns true if file successfully written, false if not
	 */
	//TODO
	public boolean writeFile(String path) {
		
		return true;
	}

	
	/**
	 * @param t - the given transcoder
	 * @param input - input string(s)
	 * @returns string array containing the encoded text
	 */
	public String[] encode(Transformable t, String[] input) {
		String[] ret = new String[input.length];
		for (int i = 0; i < input.length; i++)
			ret[i] = t.encode(input[i]);
		return ret;
	}
	
	/**
	 * @param t - the given transcoder
	 * @param input - input string(s)
	 * @returns string array containing the decoded text
	 */
	public String[] decode(Transformable t, String[] input) {
		String[] ret = new String[input.length];
		for (int i = 0; i < input.length; i++)
			ret[i] = t.decode(input[i]);
		return ret;
	}
	
	//TODO Metrics
	
	
	
}
