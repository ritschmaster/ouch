package ouch.Readers;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import ouch.transcoders.Metricable;

public class StringReader implements TextReadable {
	private String text;
	private String charset;
	private int currentPosInBytesOfText;
	private boolean endReached;
	
	public StringReader(String text) {
		this(text, "US-ASCII");
	}

	public StringReader(String text, String charset) {
		this(text, charset, null);
	}
	
	public StringReader(String text, 
						String charset, 
						Metricable metrics) {
		this.text = text;
		this.endReached = false;
		this.currentPosInBytesOfText = 0;
	}

	@Override
	public String getEntireString() {
		return this.text;
	}

	@Override
	public byte[] getNextBytes(int amount) {
		byte[] textAsBytes = null;
		int newPos;
		try {
			textAsBytes = this.text.getBytes(this.charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (this.currentPosInBytesOfText + amount 
				> textAsBytes.length) {
			
			this.endReached = true;			
			newPos = textAsBytes.length - 1;
		} else {
			newPos = this.currentPosInBytesOfText + amount;
		}
		textAsBytes = Arrays.copyOfRange(textAsBytes, 
										 this.currentPosInBytesOfText,
										 newPos);
		return textAsBytes;
	}

	@Override
	public boolean canReadBytes() {
		return this.endReached;
	}

	@Override
	public void resetByteReader() {
		this.currentPosInBytesOfText = 0;
		this.endReached = false;
	}

	@Override
	public Metricable getMetrics() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getCharset() {
		return this.charset;
	}

}
