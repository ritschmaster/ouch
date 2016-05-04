package ouch.transcoders.Compressions;

import java.util.LinkedList;

import ouch.Readers.StringReader;
import ouch.Readers.TextReadable;
import ouch.tools.FixedSizeStack;
import ouch.transcoders.Transformable;

public class LZ77Transcoder implements Transformable {
	
	private static final int SEARCH_BUFFER_SIZE = 500; //A larger number means better compression, but worse performance
	private static final int LOOKAHEAD_BUFFER_SIZE = 15; //maximum length (4 bit)
	
	private StringBuilder outString;
	private LinkedList<Character> lookAheadBuffer;
	private FixedSizeStack<Character> searchBuffer;

	@Override
	public String encode(TextReadable text) {
		outString = new StringBuilder();
		lookAheadBuffer = new LinkedList<Character>();
		
		//fill look ahead buffer
		for (char c : text.getEntireString().toCharArray()) {
			lookAheadBuffer.add(c);
		}
		searchBuffer = new FixedSizeStack<Character>(SEARCH_BUFFER_SIZE);
		
		while (lookAheadBuffer.size() > 0) {
			int index = 0;
		    int length = 0; //MAX 15
		   
		    //search buffer for best (longest) result
		    for (int i = 0; i < searchBuffer.size(); i++) {
		    	int newIndex = 0;
				int newLength = 0;
		    	
		    	if (lookAheadBuffer.getFirst() == searchBuffer.get(i)) {
		    		newIndex = searchBuffer.size() - i;
		    		newLength++;
		    		
		    		
		    		while((lookAheadBuffer.get(newLength) == searchBuffer.get(i+newLength))) {
		    					    			
		    			if (newLength+1 >= lookAheadBuffer.size() || (i+newLength+1) >= searchBuffer.size()) {
		    				break;
		    			} else {
		    				newLength++;
		    			}
		    		}
		    		
		    		if (newLength >= length) {
		    			length = newLength;
		    			index = newIndex;
		    		} 
		    		
		    		//longest encodable result found, 
		    		if (length == LOOKAHEAD_BUFFER_SIZE) {
		    			break;
		    		}
		    	}
		    }

		    //Output result
		    if (index != 0 && length > 0) {
		    	outString.append(new Triple(index, length, lookAheadBuffer.get(length)).str);
		    	System.out.print("(" + index + "," + length + "," + lookAheadBuffer.get(length) + ")");
		    	
		    	for (int j = 0; j <= length; j++) {
		    		searchBuffer.push(lookAheadBuffer.removeFirst());	
				}
		    } else {
		    	char c = lookAheadBuffer.removeFirst();
		    	searchBuffer.push(c);
		    	outString.append(new Triple(0,0, c).str);
		    	System.out.print("(" + 0 + "," + 0 + "," + c + ")");	
		    }	
		}

		System.out.println();
		return outString.toString();
	}
	
	
	@Override
	public String decode(TextReadable text) {
		return null;
	}
	
	public static void main(String[] args) {			
		//Quick Test TODO: proper test
		LZ77Transcoder trc = new LZ77Transcoder();
		String str = "The long-string instrument is an instrument in which the string is of such a length that the fundamental transverse wave is below what a person can hear as a tone (±20 Hz). If the tension and the length result in sounds with such a frequency, the tone becomes a beating frequency that ranges from a short reverb (approx 5–10 meters) to longer echo sounds (longer than 10 meters). Besides the beating frequency, the string also gives higher pitched natural overtones. Since the length is that long, this has an effect on the attack tone. The attack tone shoots through the string in a longitudinal wave and generates the typical science-fiction laser-gun sound as heard in Star Wars.[1] The sound is also similar to that occurring in upper electricity cables for trains (which are ready made long-string instruments in a way).";
		String str2 = "In Ulm, um Ulm, und um Ulm herum.";
				
				
		String s = trc.encode(new StringReader(str));
		System.out.println();
		System.out.println(s);
		System.out.println("\nRESULTS:");
		System.out.println("UNCOMPRESSED: " + str.length());
		System.out.println("COMPRESSED: " + s.length());
		
		
	}

	/*	Representing Triple (offset, length, character) for LZ77
	 *  Output String encoded as follows:
	 *  
	 * |------------|--------------------------------------|------------------------|
	 * |  Length    |				Offset				   | 		Character		|
	 * |------------|--------------------------------------|------------------------|
	 * | 4  3  2  1 | 12 11 10  9 | 8  7  6  5  4  3  2  1 | 8  7  6  5  4  3  2  1 | 
	 * |--------------------------|------------------------|------------------------|
	 * |           Byte 1         |         Byte 2         |		  Byte 3        | 
	 * |--------------------------|------------------------|------------------------|
	 * 
	 * 	
	 */
	private static class Triple {
		String str;
		
		int length;
		int offset;
		char followChar;
		
		public Triple(String str) {
			this.str = str;
			assert str.length() == 3;
			unpack();
		}
		
		public Triple(int offset, int length, char followChar) {
			this.length = length;
			this.offset = offset;
			this.followChar = followChar;
			pack();
		}

		private void unpack() {
			this.followChar = str.charAt(2);
			
			byte b = (byte) (str.charAt(0) >>> 4);
			this.length = (byte) (b & 0b00001111);
			
			int i = (byte) (str.charAt(0) << 8);
						this.offset = i | str.charAt(1);
		}
		
		private void pack() {
			char[] c = new char[3];
			byte b1, b2;
			
			b1 = (byte) (length << 4);
			b1 = (byte) (b1 & 0b11110000);
						
			b2 = (byte) (offset >>> 8);
			b2 = (byte) (b2 & 0b00001111);
						
			c[0] = (char) (b1 | b2);
			
			b1 = (byte) offset;
			c[1] = (char) b1;
			
			c[2] = followChar;
			
			this.str =  new String(c);
		}
	}

}
