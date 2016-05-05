package ouch.tests.transcoders;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ouch.Readers.StringReader;
import ouch.transcoders.Transformable;
import ouch.transcoders.Compressions.LZ77Transcoder;

public class LZ77TranscoderTest {
	public static final char FILE_SEPERATOR = (char) 28;
	public static final String[] SHORT_STRINGS = {
			"hello world",
			"abracadabra",
			"abababababab",
			"aaaaaaaaaaaaaaa",
			"aaaaaaaaaaaaaab"
			
			//"Some test sentence which is a bit longer than usual."
	};
	protected String[] ENCODED_STRINGS = new String[3];
	
//	protected void testEncode(Transformable transcoder) {
//		for (int i = 0; i < DECODED_STRINGS.length; i++) {
//			StringReader reader = new StringReader(DECODED_STRINGS[i]);
//			assertEquals(ENCODED_STRINGS[i], transcoder.encode(reader));
//							
//		}
//	}
//	
//	protected void testDecode(Transformable transcoder) {		
//		for (int i = 0; i < DECODED_STRINGS.length; i++) {
//			StringReader reader = new StringReader(ENCODED_STRINGS[i]);
//			assertEquals(DECODED_STRINGS[i], transcoder.decode(reader));
//							
//		}
//	}	
		
	@Test
	public void testEncodeDecodeShortStrings() {
		for (String s : SHORT_STRINGS) {
			LZ77Transcoder t = new LZ77Transcoder();
			String s1 = t.encode(new StringReader(s));
			String out = t.decode(new StringReader(s1));
			assertEquals(s + FILE_SEPERATOR, out);
		}
	}
	
	@Test
	public void testEncodeDecodeMediumStrings() {
		
	}
	
	@Test
	public void testEncodeDecodeLongString() {
		
	}
	
	@Test
	public void testEncodeDecodeFile() {
		
	}
	
	@Test
	public void testEncodeTriple() {
		
	}
	
	@Test
	public void testDecodeTriple() {
		
	}
	

}
