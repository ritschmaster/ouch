package ouch.Readers;

import java.io.UnsupportedEncodingException;

import ouch.transcoders.Metricable;

public class StringReader implements TextReadable {
	private String text;
	private String charset;
	private long currentPosInBytesOfText;
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
	}

	@Override
	public String getEntireString() {
		return this.text;
	}

	@Override
	public byte[] getNextBytes(int amount) {
		byte[] textAsBytes = null;
		try {
			textAsBytes = this.text.getBytes(this.charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (this.currentPosInBytesOfText + amount 
				> textAsBytes.length) {
			this.currentPosInBytesOfText = textAsBytes.length - 1;
			this.endReached = true;
		} else {
			this.currentPosInBytesOfText += amount;
		}
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
