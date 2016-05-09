package ouch.tests.transcoders;

import org.junit.Before;
import org.junit.Test;

import ouch.transcoders.*;
import ouch.transcoders.Normal.MorseCodeTranscoder;


public class MorseCoderTranscoderTest extends TranscoderTest {
	@Before
	public void setUp() throws Exception {		
		ENCODED_STRINGS[0] = ".... . .-.. .-.. --- / .-- --- .-. .-.. -..";
		ENCODED_STRINGS[1] = ".- .- .- .- -... -... -... .- .- .- .-";
		ENCODED_STRINGS[2] = "";
	}

	 @Test
	public void testEncode() {	
		super.testEncode(new MorseCodeTranscoder(EitherUpperOrLowerable.StringFormat.LOWER, 
												 EitherUpperOrLowerable.StringFormat.LOWER));
	}

	 @Test
	public void testDecode() {		
		super.testDecode(new MorseCodeTranscoder(EitherUpperOrLowerable.StringFormat.LOWER, 
												 EitherUpperOrLowerable.StringFormat.LOWER));
	}	

}
