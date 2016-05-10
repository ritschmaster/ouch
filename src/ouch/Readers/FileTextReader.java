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

package ouch.Readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ouch.transcoders.Metricable;

public class FileTextReader implements TextReadable {
	private String path;
	private boolean endReached;
	private String line;
	BufferedReader reader;
	
	public FileTextReader(String path) {
		this.path = path;
		this.endReached = false;
		try {
			reader = new BufferedReader(new FileReader(new File(this.path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		tryReadLine();
	}
	
	private void tryReadLine() {
		String s = null;
		try {
			s = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (s == null) {
			endReached = true;
		}
		this.line = s;
		
	}
	

	public byte[] getNextBytes(int amount) {
		// TODO Read the file and return a certain amount of bytes
		return null;
	}
	
	@Override
	public char[] getNextLines(int noOfLines) {
		if (endReached || noOfLines == 0) {
			return null;
		} else if (noOfLines == 1) {
			String s = line + "\n";
			tryReadLine();
			return s.toCharArray();
		} else {
			StringBuilder sb = new StringBuilder();
			while (!endReached && noOfLines > 0) {
				sb.append(line);
				sb.append("\n");
				tryReadLine();
				noOfLines--;
			}
			return sb.toString().toCharArray();
		}
		
	}


	public boolean canReadBytes() {
		// TODO Auto-generated method stub
		return false;
	}

	public void resetByteReader() {
		// TODO
	}
	
	public String getEntireString() {		
		String ret = "";
		
		try {
			BufferedReader lazyReader = new BufferedReader(new FileReader(new File(this.path)));
			
			String line;
			while ((line = reader.readLine()) != null) {
				ret += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}	
	
	@Override
	public String getCharset() {
		// TODO implement it
		return null;
	}

}
