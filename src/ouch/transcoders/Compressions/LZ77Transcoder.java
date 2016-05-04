package ouch.transcoders.Compressions;

import ouch.Readers.TextReadable;
import ouch.transcoders.Transformable;

public class LZ77Transcoder implements Transformable {

	@Override
	public String encode(TextReadable text) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String decode(TextReadable text) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Small test packing unpacking - I think it works....
	public static void main(String[] args) {		
		LZ77Transcoder lz77 = new LZ77Transcoder();
		Triple t = lz77.new Triple((byte) 15, 20, 'b');
		System.out.println(t.str);
		t = lz77.new Triple(t.str);
		System.out.println(t.length + " " + t.offset + " " + t.followChar);
		
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
	 */
	class Triple {
		String str;
		
		byte length;
		int offset;
		char followChar;
		
		
		public Triple(String str) {
			this.str = str;
			assert str.length() == 3;
			unpack(str);
		}
		
		public Triple(byte length, int offset, char followChar) {
			this.length = length;
			this.offset = offset;
			this.followChar = followChar;
			pack();
		}


		private void unpack(String str) {
			this.followChar = str.charAt(2);
			
			byte b = (byte) (str.charAt(0) >>> 4);
			this.length = (byte) (b & 0x00001111);
			
			int i = (byte) (str.charAt(0) << 8);
			i = (byte) (i & 0x00000000);
			this.offset = i | str.charAt(1);
		}
		
		private void pack() {
			char[] c = new char[3];
			byte b1, b2;
			
			b1 = (byte) (length << 4);
			b1 = (byte) (b1 & 0x11110000);
						
			b2 = (byte) (offset >>> 8);
			b2 = (byte) (b2 & 0x00001111);
						
			c[0] = (char) (b1 | b2);
			//System.out.println("b2 " + Integer.toBinaryString(Byte.toUnsignedInt(length)));
			
			b1 = (byte) offset;
			c[1] = (char) b1;
			
			c[2] = followChar;
			
			this.str =  new String(c);
		}
	}

}
