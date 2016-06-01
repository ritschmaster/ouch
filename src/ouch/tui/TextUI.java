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
import ouch.transcoders.Compressions.LZ77Transcoder;
import ouch.transcoders.Compressions.QuotedPrintableTranscoder;
import ouch.transcoders.Normal.MorseCodeTranscoder;
import ouch.transcoders.Normal.PlainTranscoder;
import ouch.transcoders.fun.*;
import ouch.transcoders.NumberSystems.*;

public class TextUI {
    private static final String FILE_NOT_SUPPLIED = "/missing";
    
    @Option(name="-i")
    private String inputEncoding = "";
    
    @Option(name="-o")
    private String outputEncoding = "";

    @Option(name="-s")
    private String inputNumberSystem = "";

    @Option(name="-d")
    private String outputNumberSystem = "";

    @Option(name="--file")
    private String filename = FILE_NOT_SUPPLIED;
    
    @Option(name="--metrics")
    private boolean metricsEnabled = false;

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
            System.err.println("java ouch [options...] [input-encoding]\n"
                               + "\n"
                               + "Available options:\n"
                               + "-i\tspecify input encoding\n"
                               + "-o\tspecify output encoding\n"
                               + "-s\tspecify base for the input (source) number system\n"
                               + "-d\tspecify base for the output (destination) number system\n"
                               + "--file\tfilename (use the contents of the file as input)\n"
                               + "--metrics\tprint metrics\n"
                               + "\n"
                               + "Available encodings (for -i and -o):\n"
                               + "- plain\n"
                               + "- mirrored\n"
                               + "- leetspeak\n"
                               + "- morse\n"
                               + "- lz77\n"
                               + "- quoted\n"
                               + "\n"
                               + "Available number systems (use -s and -d)\n"
                               + "- An arbitary Integer to describe the base of a number system\n"
                               + "- roman\n"
                               + "\n"
                               + "PLEASE DO NOT MIX ENCODINGS AND NUMBER SYSTEMS. ONLY USE EITHER ONE."
                               + "\n");
            parser.printUsage(System.err);
            System.err.println();
            return;
        }

        TextReadable reader;
        String output = "";
        String inputStringPlain = "";
        TextReadable inputReader = null;

        /** Decoding of the given input text */
        if (this.filename.equals(FILE_NOT_SUPPLIED))
        	inputReader = new StringReader(this.arguments.get(0));
        else
        	inputReader = new FileTextReader(this.filename);

        int inputBase;
        if (this.inputEncoding.equals("plain")) {
            inputStringPlain = (new PlainTranscoder().decode(inputReader));
        }
        else if (this.inputEncoding.equals("mirrored")) {
            inputStringPlain = (new MirroredTranscoder().decode(inputReader));
        } else if (this.inputEncoding.equals("leetspeak")) {
            inputStringPlain = (new LeetspeakTranscoder().decode(inputReader));
        } else if (this.inputEncoding.equals("morse")) {
            inputStringPlain = (new MorseCodeTranscoder().decode(inputReader));
        } else if (this.inputEncoding.equals("lz77")) {
        	inputStringPlain = (new LZ77Transcoder().decode(inputReader));
        } else if (this.inputEncoding.equals("quoted")) {
        	inputStringPlain = (new QuotedPrintableTranscoder().decode(inputReader));
        } else if (this.inputNumberSystem.equals("roman")) {
        	inputStringPlain = (new RomanNumberTranscoder().decode(inputReader));  
        } else if ((inputBase = Integer.parseInt(this.inputNumberSystem)) > 0) {
        	NumberSystemTranscoder numTranscoder = new NumberSystemTranscoder();
	        numTranscoder.setSource(inputBase);
	        numTranscoder.setDestination(10);
            inputStringPlain  = numTranscoder.decode(inputReader);
        }
        reader = new StringReader(inputStringPlain);

        /** Encoding of the given input text */ 
        int outputBase;
        Transformable transcoder = null;
        if (this.outputEncoding.equals("plain")) {
            transcoder = new PlainTranscoder();
        }
        else if (this.outputEncoding.equals("mirrored")) {
            transcoder = new MirroredTranscoder();
        } else if (this.outputEncoding.equals("leetspeak")) {
            transcoder = new LeetspeakTranscoder();
        } else if (this.outputEncoding.equals("morse")) {
            transcoder = new MorseCodeTranscoder();
        } else if (this.outputEncoding.equals("lz77")) {
        	transcoder = new LZ77Transcoder();
        } else if (this.outputEncoding.equals("quoted")) {
        	transcoder = new QuotedPrintableTranscoder();
        } else if (this.outputNumberSystem.equals("roman")) {
        	transcoder = new RomanNumberTranscoder();
        } else if ((outputBase = Integer.parseInt(this.outputNumberSystem)) > 0) {
        	NumberSystemTranscoder numTranscoder = new NumberSystemTranscoder();
	        numTranscoder.setSource(10);
	        numTranscoder.setDestination(outputBase);
	        transcoder = numTranscoder;
        }
        output = transcoder.encode(reader);
        
        if (this.metricsEnabled)
        	System.out.println(transcoder.getLastDiff().toString());
        System.out.println(output);
    }
}
