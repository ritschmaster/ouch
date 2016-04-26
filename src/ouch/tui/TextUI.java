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

package ouch.tui;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import ouch.Readers.*;
import ouch.transcoders.*;
import ouch.transcoders.fun.*;

public class TextUI {
    private static final String FILE_NOT_SUPPLIED = "/missing";
    
    @Option(name="-i")
    private String inputEncoding = "plain";
    
    @Option(name="-o")
    private String outputEncoding = "plain";

    @Option(name="--file")
    private String filename = FILE_NOT_SUPPLIED;

    @Argument
    private List<String> arguments = new ArrayList<String>();

    public void doMain(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            
            if(this.arguments.isEmpty())
            	throw new CmdLineException(parser,"No argument is given");
            else if (this.arguments.size() < 1)
            	throw new CmdLineException(parser,"Not enough arguments are given");

        } catch( CmdLineException e ) {
            System.err.println(e.getMessage());
            System.err.println("java ouch [options...] arguments...");
            parser.printUsage(System.err);
            System.err.println();
            return;
        }

        TextReadable reader;
        if (this.filename.equals(FILE_NOT_SUPPLIED)) {
            String input = this.arguments.get(0);
            if (this.inputEncoding.equals("mirrored")) {
                input = (new MirroredTranscoder().decode(new StringReader(this.arguments.get(0))));;
            }
            reader = new StringReader(input);
        } else {
            reader = new StringReader(this.arguments.get(0));
        }

        Transformable transcoder = new PlainTranscoder();
        if (this.outputEncoding.equals("mirrored")) {
            transcoder = new MirroredTranscoder();
        }
        System.out.println(transcoder.encode(reader));
    }
}
