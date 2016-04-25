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

package ouch.tui;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class TextUI {
    @Option(name="--input")
    private String inputEncoding = "not given";
    
    @Option(name="--output")
    private String outputEncoding = "not given";
        
	@Argument
	private List<String> arguments = new ArrayList<String>();
	
	public void doMain(String[] args) {
		CmdLineParser parser = new CmdLineParser(this);
		try {
            parser.parseArgument(args);

            if(arguments.isEmpty())
            	throw new CmdLineException(parser,"No argument is given");
            else if (arguments.size() < 1)
            	throw new CmdLineException(parser,"Not enough arguments are given");

        } catch( CmdLineException e ) {        	
            System.err.println(e.getMessage());
            System.err.println("java ouch [options...] arguments...");
            parser.printUsage(System.err);
            System.err.println();
            return;
        }

	}
	
}
