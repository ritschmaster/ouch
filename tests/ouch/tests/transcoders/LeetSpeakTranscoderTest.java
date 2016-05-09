package ouch.tests.transcoders;

import org.junit.Before;
import org.junit.Test;

import ouch.transcoders.EitherUpperOrLowerable;
import ouch.transcoders.fun.LeetspeakTranscoder;

public class LeetSpeakTranscoderTest extends TranscoderTest {

	@Before
	public void setUp() throws Exception {
		ENCODED_STRINGS[0] = "|-|3|_|_[] |/\\|[]R|_D";
		ENCODED_STRINGS[1] = "4444BBB4444";
		ENCODED_STRINGS[2] = "";
	}

	@Test
	public void testEncode() {	
		super.testEncode(new LeetspeakTranscoder(EitherUpperOrLowerable.StringFormat.LOWER, 
				 								 EitherUpperOrLowerable.StringFormat.LOWER));
	}

	 @Test
	public void testDecode() {		
		super.testDecode(new LeetspeakTranscoder(EitherUpperOrLowerable.StringFormat.LOWER, 
												 EitherUpperOrLowerable.StringFormat.LOWER));
	}	

}
