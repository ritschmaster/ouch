package ouch.transcoders.Compressions;

import ouch.Readers.StringReader;
import ouch.Readers.TextReadable;
import ouch.transcoders.Metricable;
import ouch.transcoders.Transformable;
import ouch.transcoders.NumberSystems.NumberSystemTranscoder;


public class QuotedPrintableTranscoder implements Transformable{
	static class QuotedPrintableMetrics implements Metricable {
		private final int countChanges;
		
		public QuotedPrintableMetrics(int countChanges) {
			super();
			this.countChanges = countChanges;
		}

		public String toString() {
			StringBuffer result = new StringBuffer();
			result.append("Quoted Printable Conversion:\n");
			result.append("Changed ");
			result.append(this.countChanges);
			if (this.countChanges > 1) {
				result.append(" signs");
			}
			else {
				result.append(" sign");
			}
			result.append(".");
			return result.toString();
		}
	}
	private String value;
	private int countChanges;

    public QuotedPrintableTranscoder() {
        this.value = null;
        this.countChanges = 0;
    }
	@Override
	public String encode(TextReadable text) {
		this.countChanges = 0;
		String inputValue = text.getEntireString();
		this.value = inputValue;
		
		StringBuffer result = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) >= 32 && value.charAt(i) <= 60) {
                result.append(value.charAt(i));
            }
            else if (value.charAt(i) >= 62 && value.charAt(i) <=126) {
                result.append(value.charAt(i));
            }
            else if (value.charAt(i) == 9) {
                result.append(value.charAt(i));
            }
            else {
                result.append("=" + Integer.toHexString(value.charAt(i)));
                this.countChanges++;
            }
        }
        return result.toString();
	}

	@Override
	public String decode(TextReadable text) {
		this.countChanges = 0;
		String inputValue = text.getEntireString();
		StringBuffer result = new StringBuffer();
		String[] splitString = new String[150];
		splitString = inputValue.split("=");
		int count = 0;
		for (String s: splitString) {
			if (count == 0 && !s.contains("=")) {
				result.append(s);
			}
			else {
				StringBuffer abc = new StringBuffer();
				abc.append(s.charAt(0));
				abc.append(s.charAt(1));
				NumberSystemTranscoder decodeHex = new NumberSystemTranscoder();
				decodeHex.setSource(16);
				decodeHex.setDestination(10);
				int intResult = Integer.parseInt(decodeHex.decode(new StringReader(abc.toString())));
				result.append((char)intResult);
				result.append(s.substring(2, s.length()));
				this.countChanges++;
			}
			count++;
		}
		return result.toString();
	}

	@Override
	public Metricable getLastDiff() {
		return new QuotedPrintableMetrics(this.countChanges);
	}
	

}
