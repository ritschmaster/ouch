package ouch.tests.transcoders;

import static org.junit.Assert.*;

import org.junit.Test;

import ouch.Readers.StringReader;
import ouch.transcoders.NumberSystems.NumberSystemTranscoder;

public class NumberSystemTranscoderTest {
	public static final String SHORT_DEC_NUM = "123";
	public static final String LARGE_DEC_NUM = "4873496743876934698347698374967934679834769";
	public static final String SHORT_HEX_NUM = "3EF2";
	public static final String LARGE_HEX_NUM = "3EF2232324EFA";
	public static final String DEC_SHORT_RESULT = "16114";
	public static final String DEC_LARGE_RESULT = "1107355080216314";
	public static final String BINARY_SHORT_RESULT = "11111011110010";
	public static final String BINARY_LARGE_RESULT = "11111011110010001000110010001100100100111011111010";
	public static final String BASE3_SHORT_RESULT = "11120";
	public static final String BASE3_LARGE_RESULT = "12101012211010000111111201112202";
	public static final String BASE4_SHORT_RESULT = "1323";
	public static final String BASE4_LARGE_RESULT = "3323302020302030210323322";
	public static final String BASE5_SHORT_RESULT = "443";
	public static final String BASE5_LARGE_RESULT = "2130120401201013410224";
	public static final String BASE6_SHORT_RESULT = "323";
	public static final String BASE6_LARGE_RESULT = "14523051522341110202";
	public static final String BASE7_SHORT_RESULT = "234";
	public static final String BASE7_LARGE_RESULT = "452150523121564646";
	public static final String BASE8_SHORT_RESULT = "173";
	public static final String BASE8_LARGE_RESULT = "37362106214447372";
	public static final String BASE9_SHORT_RESULT = "146";
	public static final String BASE9_LARGE_RESULT = "5335733014451482";
	public static final String BASE11_SHORT_RESULT = "102";
	public static final String BASE11_LARGE_RESULT = "2A092371A105086";
	public static final String BASE12_SHORT_RESULT = "A3";
	public static final String BASE12_LARGE_RESULT = "A42449BB9B8362";
	public static final String BASE13_SHORT_RESULT = "96";
	public static final String BASE13_LARGE_RESULT = "386B719CA30AB7";
	public static final String BASE14_SHORT_RESULT = "8B";
	public static final String BASE14_LARGE_RESULT = "157643B3D75C26";
	public static final String BASE15_SHORT_RESULT = "83";
	public static final String BASE15_LARGE_RESULT = "8804CA831CEAE";
	
	@Test
	public void testEncodeDecodeDec() {
		NumberSystemTranscoder testTranscoder = new NumberSystemTranscoder();
		testTranscoder.setSource(16);
		testTranscoder.setDestination(10);
		String result = testTranscoder.encode(new StringReader(SHORT_HEX_NUM));
		String largeResult = testTranscoder.encode(new StringReader(LARGE_HEX_NUM));
		assertEquals(DEC_SHORT_RESULT, result);
		assertEquals(DEC_LARGE_RESULT, largeResult);
	}
	@Test
	public void testEncodeDecodeBinary() {
		NumberSystemTranscoder testTranscoder = new NumberSystemTranscoder();
		testTranscoder.setSource(10);
		testTranscoder.setDestination(2);
		String result = testTranscoder.encode(new StringReader(DEC_SHORT_RESULT));
		String largeResult = testTranscoder.encode(new StringReader(DEC_LARGE_RESULT));
		assertEquals(BINARY_SHORT_RESULT, result);
		assertEquals(BINARY_LARGE_RESULT, largeResult);
	}
	@Test
	public void testEncodeDecodeHex() {
		NumberSystemTranscoder testTranscoder = new NumberSystemTranscoder();
		testTranscoder.setSource(10);
		testTranscoder.setDestination(16);
		String result = testTranscoder.encode(new StringReader(DEC_SHORT_RESULT));
		String largeResult = testTranscoder.encode(new StringReader(DEC_LARGE_RESULT));
		assertEquals(SHORT_HEX_NUM, result);
		assertEquals(LARGE_HEX_NUM, largeResult);
	}
	@Test
	public void testEncodeDecode3() {
		NumberSystemTranscoder testTranscoder = new NumberSystemTranscoder();
		testTranscoder.setSource(10);
		testTranscoder.setDestination(3);
		String result = testTranscoder.encode(new StringReader(SHORT_DEC_NUM));
		String largeResult = testTranscoder.encode(new StringReader(DEC_LARGE_RESULT));
		assertEquals(BASE3_SHORT_RESULT, result);
		assertEquals(BASE3_LARGE_RESULT, largeResult);
	}
	@Test
	public void testEncodeDecode4() {
		NumberSystemTranscoder testTranscoder = new NumberSystemTranscoder();
		testTranscoder.setSource(10);
		testTranscoder.setDestination(4);
		String result = testTranscoder.encode(new StringReader(SHORT_DEC_NUM));
		String largeResult = testTranscoder.encode(new StringReader(DEC_LARGE_RESULT));
		assertEquals(BASE4_SHORT_RESULT, result);
		assertEquals(BASE4_LARGE_RESULT, largeResult);
	}
	@Test
	public void testEncodeDecode5() {
		NumberSystemTranscoder testTranscoder = new NumberSystemTranscoder();
		testTranscoder.setSource(10);
		testTranscoder.setDestination(5);
		String result = testTranscoder.encode(new StringReader(SHORT_DEC_NUM));
		String largeResult = testTranscoder.encode(new StringReader(DEC_LARGE_RESULT));
		assertEquals(BASE5_SHORT_RESULT, result);
		assertEquals(BASE5_LARGE_RESULT, largeResult);
	}
	@Test
	public void testEncodeDecode6() {
		NumberSystemTranscoder testTranscoder = new NumberSystemTranscoder();
		testTranscoder.setSource(10);
		testTranscoder.setDestination(6);
		String result = testTranscoder.encode(new StringReader(SHORT_DEC_NUM));
		String largeResult = testTranscoder.encode(new StringReader(DEC_LARGE_RESULT));
		assertEquals(BASE6_SHORT_RESULT, result);
		assertEquals(BASE6_LARGE_RESULT, largeResult);
	}
	@Test
	public void testEncodeDecode7() {
		NumberSystemTranscoder testTranscoder = new NumberSystemTranscoder();
		testTranscoder.setSource(10);
		testTranscoder.setDestination(7);
		String result = testTranscoder.encode(new StringReader(SHORT_DEC_NUM));
		String largeResult = testTranscoder.encode(new StringReader(DEC_LARGE_RESULT));
		assertEquals(BASE7_SHORT_RESULT, result);
		assertEquals(BASE7_LARGE_RESULT, largeResult);
	}
	@Test
	public void testEncodeDecodeOktal() {
		NumberSystemTranscoder testTranscoder = new NumberSystemTranscoder();
		testTranscoder.setSource(10);
		testTranscoder.setDestination(8);
		String result = testTranscoder.encode(new StringReader(SHORT_DEC_NUM));
		String largeResult = testTranscoder.encode(new StringReader(DEC_LARGE_RESULT));
		assertEquals(BASE8_SHORT_RESULT, result);
		assertEquals(BASE8_LARGE_RESULT, largeResult);
	}
	@Test
	public void testEncodeDecode9() {
		NumberSystemTranscoder testTranscoder = new NumberSystemTranscoder();
		testTranscoder.setSource(10);
		testTranscoder.setDestination(9);
		String result = testTranscoder.encode(new StringReader(SHORT_DEC_NUM));
		String largeResult = testTranscoder.encode(new StringReader(DEC_LARGE_RESULT));
		assertEquals(BASE9_SHORT_RESULT, result);
		assertEquals(BASE9_LARGE_RESULT, largeResult);
	}
	@Test
	public void testEncodeDecode11() {
		NumberSystemTranscoder testTranscoder = new NumberSystemTranscoder();
		testTranscoder.setSource(10);
		testTranscoder.setDestination(11);
		String result = testTranscoder.encode(new StringReader(SHORT_DEC_NUM));
		String largeResult = testTranscoder.encode(new StringReader(DEC_LARGE_RESULT));
		assertEquals(BASE11_SHORT_RESULT, result);
		assertEquals(BASE11_LARGE_RESULT, largeResult);
	}
	@Test
	public void testEncodeDecodeDuodezimal() {
		NumberSystemTranscoder testTranscoder = new NumberSystemTranscoder();
		testTranscoder.setSource(10);
		testTranscoder.setDestination(12);
		String result = testTranscoder.encode(new StringReader(SHORT_DEC_NUM));
		String largeResult = testTranscoder.encode(new StringReader(DEC_LARGE_RESULT));
		assertEquals(BASE12_SHORT_RESULT, result);
		assertEquals(BASE12_LARGE_RESULT, largeResult);
	}
	@Test
	public void testEncodeDecode13() {
		NumberSystemTranscoder testTranscoder = new NumberSystemTranscoder();
		testTranscoder.setSource(10);
		testTranscoder.setDestination(13);
		String result = testTranscoder.encode(new StringReader(SHORT_DEC_NUM));
		String largeResult = testTranscoder.encode(new StringReader(DEC_LARGE_RESULT));
		assertEquals(BASE13_SHORT_RESULT, result);
		assertEquals(BASE13_LARGE_RESULT, largeResult);
	}
	@Test
	public void testEncodeDecode14() {
		NumberSystemTranscoder testTranscoder = new NumberSystemTranscoder();
		testTranscoder.setSource(10);
		testTranscoder.setDestination(14);
		String result = testTranscoder.encode(new StringReader(SHORT_DEC_NUM));
		String largeResult = testTranscoder.encode(new StringReader(DEC_LARGE_RESULT));
		assertEquals(BASE14_SHORT_RESULT, result);
		assertEquals(BASE14_LARGE_RESULT, largeResult);
	}
	@Test
	public void testEncodeDecode15() {
		NumberSystemTranscoder testTranscoder = new NumberSystemTranscoder();
		testTranscoder.setSource(10);
		testTranscoder.setDestination(15);
		String result = testTranscoder.encode(new StringReader(SHORT_DEC_NUM));
		String largeResult = testTranscoder.encode(new StringReader(DEC_LARGE_RESULT));
		assertEquals(BASE15_SHORT_RESULT, result);
		assertEquals(BASE15_LARGE_RESULT, largeResult);
	}


}
