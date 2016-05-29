package ouch.tests.transcoders;

import static org.junit.Assert.*;

import org.junit.Test;

import ouch.Readers.StringReader;
import ouch.transcoders.Compressions.QuotedPrintableTranscoder;
import ouch.transcoders.NumberSystems.RomanNumberTranscoder;

public class QuotedPrintableTranscoderTest {
	//SHORT_STRING, LARGE_STRING --> Quelle: www.spiegel.de
	public static final String SHORT_STRING = "Post an den Zwübelfüsch: Übel wütet der Gürtelwürger";
	public static final String LARGE_STRING = "Die pünktlich gewünschte Trüffelfüllung im übergestülpten Würzkümmel-Würfel ist "
			+ "kümmerlich und dürfte fürderhin zu Rüffeln in Hülle und Fülle führen.";
	public static final String SHORT_ENCODE_RESULT = "Post an den Zw=fcbelf=fcsch: =dcbel w=fctet der G=fcrtelw=fcrger";
	public static final String LARGE_ENCODE_RESULT = "Die p=fcnktlich gew=fcnschte Tr=fcf"
			+ "felf=fcllung im =fcbergest=fclpten W=fcrzk=fcmmel-W=fcrfel ist k=fc"
			+ "mmerlich und d=fcrfte f=fcrderhin zu R=fcffeln in H=fclle und F=f"
			+ "clle f=fchren.";
	@Test
	public void testEncode() {
		QuotedPrintableTranscoder testTranscoder = new QuotedPrintableTranscoder();
		String result = testTranscoder.encode(new StringReader(SHORT_STRING));
		String largeResult = testTranscoder.encode(new StringReader(LARGE_STRING));
		assertEquals(SHORT_ENCODE_RESULT, result);
		assertEquals(LARGE_ENCODE_RESULT, largeResult);
	}

	@Test
	public void testDecode() {
		QuotedPrintableTranscoder testTranscoder = new QuotedPrintableTranscoder();
		String result = testTranscoder.decode(new StringReader(SHORT_ENCODE_RESULT));
		String largeResult = testTranscoder.decode(new StringReader(LARGE_ENCODE_RESULT));
		assertEquals(SHORT_STRING, result);
		assertEquals(LARGE_STRING, largeResult);
	}

}
