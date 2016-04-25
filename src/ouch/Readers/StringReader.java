package ouch.Readers;

import ouch.transcoders.Metricable;

public class StringReader implements TextReadable {
	private String text;

	public StringReader(String text) {
		this.text = text;
	}
	
	@Override
	public String getEntireString() {
		return this.text;
	}

	@Override
	public byte[] getNextBytes(int amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canReadBytes() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetByteReader() {
		// TODO Auto-generated method stub
	}

	@Override
	public Metricable getMetrics() {
		// TODO Auto-generated method stub
		return null;
	}

}
