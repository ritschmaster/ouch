package ouch.tests.transcoders;

import static org.junit.Assert.*;

import org.junit.Test;

import ouch.transcoders.MirroredTranscoder;
import ouch.transcoders.Transformable;

public class MirroredTranscoderTest {
	public static final String[] DECODED_STRINGS = {
			"hello world",
			"AAAABBBAAAA",
			""
			//"Some test sentence which is a bit longer than usual."
	};
	
	public static final String[] ENCODED_STRINGS = {
			"dlrow olleh",
			"AAAABBBAAAA",
			""
	};

	@Test
	public void testEncode() {
		Transformable t = new MirroredTranscoder();
		for (int i = 0; i < DECODED_STRINGS.length; i++) {			
			assertEquals(t.encode(DECODED_STRINGS[i]),
							ENCODED_STRINGS[i]);
		}
	}
	
	@Test
	public void testDecode() {
		Transformable t = new MirroredTranscoder();
		for (int i = 0; i < DECODED_STRINGS.length; i++) {			
			assertEquals(t.decode(ENCODED_STRINGS[i]),
						DECODED_STRINGS[i]);
		}
	}	
}
