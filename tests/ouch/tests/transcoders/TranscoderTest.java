package ouch.tests.transcoders;

import static org.junit.Assert.assertEquals;

import ouch.Readers.StringReader;
import ouch.transcoders.Transformable;

abstract public class TranscoderTest {
	public static final String[] DECODED_STRINGS = {
			"hello world",
			"AAAABBBAAAA",
			""
			//"Some test sentence which is a bit longer than usual."
	};
	protected String[] ENCODED_STRINGS = new String[3];
	
	protected void testEncode(Transformable transcoder) {
		for (int i = 0; i < DECODED_STRINGS.length; i++) {
			StringReader reader = new StringReader(DECODED_STRINGS[i]);
			assertEquals(transcoder.encode(reader),
							ENCODED_STRINGS[i]);
		}
	}
	
	protected void testDecode(Transformable transcoder) {		
		for (int i = 0; i < DECODED_STRINGS.length; i++) {
			StringReader reader = new StringReader(ENCODED_STRINGS[i]);
			assertEquals(transcoder.decode(reader),
						DECODED_STRINGS[i]);
		}
	}	
		
	abstract public void testEncode();
	abstract public void testDecode();
}
