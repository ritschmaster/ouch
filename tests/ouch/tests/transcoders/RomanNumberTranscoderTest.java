package ouch.tests.transcoders;

import static org.junit.Assert.*;

import org.junit.Test;

import ouch.Readers.StringReader;
import ouch.transcoders.NumberSystems.RomanNumberTranscoder;

public class RomanNumberTranscoderTest {
	public static final String SHORT_DEC_NUM = "123";
	public static final String LARGE_DEC_NUM = "48734967";
	public static final String SHORT_ENCODE_RESULT = "CXXIII";
	public static final String LARGE_ENCODE_RESULT = "ↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂↂ"
			+ "ↂↂↂↂↂMMMMCMLXVII";

	@Test
	public void testEncode() {
		RomanNumberTranscoder testTranscoder = new RomanNumberTranscoder();
		String result = testTranscoder.encode(new StringReader(SHORT_DEC_NUM));
		String largeResult = testTranscoder.encode(new StringReader(LARGE_DEC_NUM));
		assertEquals(SHORT_ENCODE_RESULT, result);
		assertEquals(LARGE_ENCODE_RESULT, largeResult);
	}

	@Test
	public void testDecode() {
		RomanNumberTranscoder testTranscoder = new RomanNumberTranscoder();
		String result = testTranscoder.decode(new StringReader(SHORT_ENCODE_RESULT));
		String largeResult = testTranscoder.decode(new StringReader(LARGE_ENCODE_RESULT));
		assertEquals(SHORT_DEC_NUM, result);
		assertEquals(LARGE_DEC_NUM, largeResult);
	}

}
