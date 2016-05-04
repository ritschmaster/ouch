package ouch.tests.transcoders;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ouch.transcoders.Normal.MorseCodeTranscoder;

public class LeetSpeakTranscoderTest extends TranscoderTest {

	@Before
	public void setUp() throws Exception {
		ENCODED_STRINGS[0] = "";
		ENCODED_STRINGS[1] = "";
		ENCODED_STRINGS[2] = "";
	}

	// @Test
	public void testEncode() {	
		super.testEncode(new MorseCodeTranscoder());
	}

	// @Test
	public void testDecode() {		
		super.testDecode(new MorseCodeTranscoder());
	}	

}
