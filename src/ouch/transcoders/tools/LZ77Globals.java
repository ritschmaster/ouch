package ouch.transcoders.tools;

public class LZ77Globals {
	
	public static final int SEARCH_BUFFER_SIZE = 1024; //A larger number means better compression, but worse performance (MAX  4KB - 1 Byte)
	public static final int LOOKAHEAD_BUFFER_SIZE = 15; //maximum length (4 bit)
	public static final char FILE_SEPERATOR = (char) 28;

}
