package ouch.transcoders.NumberSystems;

import java.util.HashMap;

import ouch.Readers.StringReader;
import ouch.Readers.TextReadable;
import ouch.transcoders.Metricable;

public class RomanNumberTranscoder<T> implements NumbersystemTransformable {
	static class RomanNumberMetrics implements Metricable {
		private final int[][] valueIncr;
		private final String result;
		private final long inputDec;
		
		
		public RomanNumberMetrics(int[][] valueIncr, String result, long inputDec/*, String type*/) {
			super();
			this.valueIncr = valueIncr;
			this.result = result;
			this.inputDec = inputDec;
		}


		public String toString() {
			HashMap<Long, String> lookup = new HashMap<>();
			lookup.put(10000L, "ↂ");
			lookup.put(5000L, "ↁ");
			lookup.put(1000L, "M");
			lookup.put(900L, "CM");
			lookup.put(500L, "D");
			lookup.put(400L, "CD");
			lookup.put(100L, "C");
			lookup.put(90L, "XC");
			lookup.put(50L, "L");
			lookup.put(40L, "XL");
			lookup.put(10L, "X");
			lookup.put(9L, "IX");
			lookup.put(5L, "V");
			lookup.put(4L, "IV");
			lookup.put(1L, "I");

			StringBuffer resultEncode = new StringBuffer();
			resultEncode.append("Convert from Decimal to Roman Number:\n");
			int count = 0;
			for (int i = 0; i < this.valueIncr.length; i++) {
				if (this.valueIncr[i][0] != 0) {
					if (count == 0 && this.valueIncr[i][0] != 0) {
						resultEncode.append(this.valueIncr[i][0]);
						resultEncode.append(" * ");
						resultEncode.append(this.valueIncr[i][1]);
					}
					else {
						resultEncode.append(" + ");
						resultEncode.append(this.valueIncr[i][0]);
						resultEncode.append(" * ");
						resultEncode.append(this.valueIncr[i][1]);
					}
					count++;
						
				}
				
			}
			resultEncode.append(" = ");
			resultEncode.append(inputDec);
			resultEncode.append("\n");
			int counter = 0;
			for (int i = 0; i < this.valueIncr.length; i++) {
				if (this.valueIncr[i][0] != 0) {
					if (counter == 0 && this.valueIncr[i][0] != 0) {
						resultEncode.append(this.valueIncr[i][0]);
						resultEncode.append(" * ");
						resultEncode.append(lookup.get((long)this.valueIncr[i][1]));
					}
					else {
						resultEncode.append(" + ");
						resultEncode.append(this.valueIncr[i][0]);
						resultEncode.append(" * ");
						resultEncode.append(lookup.get((long)this.valueIncr[i][1]));
					}
					counter++;
				}
				
			}
			resultEncode.append(" = ");
			resultEncode.append(result);
			return resultEncode.toString();
		}
			
	}
	
	private T value;
	private int[][] valueIncrement;
	private RomanNumberMetrics metric;
	private String result;
	
	public RomanNumberTranscoder(T value) {
		this.value = value;
	}
	
	public RomanNumberTranscoder() {
		this.value = null;
	}

	@Override
	public String encode(TextReadable text) {
		this.value = (T) text.getEntireString();
		StringBuffer result = new StringBuffer();
		this.valueIncrement = new int[14][2];
        Long value = valueToLong();
        while (value != 0) {
            if (value >= 10000) {
                value -= 10000;
                result.append("ↂ");
                valueIncrement[0][0] += 1;
                valueIncrement[0][1] = 10000;
            }
            else if (value >= 5000) {
                value -= 5000;
                result.append("ↁ");
                valueIncrement[1][0] += 1;
                valueIncrement[1][1] = 5000;
            }
            else if (value >= 1000) {
                value -= 1000;
                result.append("M");
                valueIncrement[2][0] += 1;
                valueIncrement[2][1] = 1000;
            }
            else if (value >= 900) {
                value -= 900;
                result.append("CM");
                valueIncrement[3][0] += 1;
                valueIncrement[3][1] = 900;
            }
            else if (value >= 500) {
                value -= 500;
                result.append("D");
                valueIncrement[4][0] += 1;
                valueIncrement[4][1] = 500;
            }
            else if (value >= 400) {
                value -= 400;
                result.append("CD");
                valueIncrement[5][0] += 1;
                valueIncrement[5][1] = 400;
            }
            else if (value >= 100) {
                value -= 100;
                result.append("C");
                valueIncrement[6][0] += 1;
                valueIncrement[6][1] = 100;
            }
            else if (value >= 90) {
                value -= 90;
                result.append("XC");
                valueIncrement[7][0] += 1;
                valueIncrement[7][1] = 90;
            }
            else if (value >= 50) {
                value -= 50;
                result.append("L");
                valueIncrement[8][0] += 1;
                valueIncrement[8][1] = 50;
            }
            else if (value >= 40) {
                value -= 40;
                result.append("XL");
                valueIncrement[9][0] += 1;
                valueIncrement[9][1] = 40;
            }
            else if (value >= 10) {
                value -= 10;
                result.append("X");
                valueIncrement[10][0] += 1;
                valueIncrement[10][1] = 10;
            }
            else if (value >= 9) {
                value -= 9;
                result.append("IX");
                valueIncrement[11][0] += 1;
                valueIncrement[11][1] = 9;
            }
            else if (value >= 5) {
                value -= 5;
                result.append("V");
                valueIncrement[12][0] += 1;
                valueIncrement[12][1] = 5;
            }
            else if (value >= 4) {
                value -= 4;
                result.append("IV");
                valueIncrement[13][0] += 1;
                valueIncrement[13][1] = 4;
            }
            else if (value >= 1) {
                value -= 1;
                result.append("I");
                valueIncrement[14][0] += 1;
                valueIncrement[14][1] = 1;
            }
        }
        this.result = result.toString();
        return result.toString();
	}

	@Override
	public String decode(TextReadable text) {
		this.value = (T) text.getEntireString();
		long res = 0L;
		StringBuffer input = new StringBuffer();
		input.append(this.value);
		String inputValue = input.toString();
		for (int i = 0; i < input.length(); i++) {
			if (inputValue.charAt(i) == 'ↂ') {
				res += 10000L;
			}
			else if (inputValue.charAt(i) =='ↁ') {
				res += 5000L;
			}
			else if (inputValue.charAt(i) == 'M') {
				res += 1000L;
			}
			else if (inputValue.charAt(i) == 'D') {
				res += 500L;
			}
			else if (inputValue.charAt(i) == 'C') {
				res += 100L;
			}
			else if (inputValue.charAt(i) == 'L') {
				res += 50L;
			}
			else if (inputValue.charAt(i) == 'X') {
				res += 10L;
			}
			else if (inputValue.charAt(i) == 'V') {
				res += 5L;
			}
			else if (inputValue.charAt(i) == 'I') {
				res += 1L;
			}
		}
		if (inputValue.contains("CM")) {
			res -= 1000L;
			res -= 100L;
			res += 900L;
		}
		if (inputValue.contains("CD")) {
			res -= 500L;
			res -= 100L;
			res += 400L;
		}
		if (inputValue.contains("XC")) {
			res -= 100L;
			res -= 10L;
			res += 90L;
		}
		if (inputValue.contains("XL")) {
			res -= 50L;
			res -= 10L;
			res += 40L;
		}
		if (inputValue.contains("IX")) {
			res -= 10L;
			res -= 1L;
			res += 9L;
		}
		if (inputValue.contains("IV")) {
			res -= 5L;
			res -= 1L;
			res += 4L;
		}
		StringBuffer resBuf = new StringBuffer();
		resBuf.append(res);
		StringReader resReader = new StringReader(resBuf.toString());
		this.result = encode(resReader);
		return Long.toString(res);
	}

	@Override
	public Metricable getLastDiff() {
		return new RomanNumberMetrics(this.valueIncrement, result, valueToLong());//, "encode");
	}

	@Override
	public void setSource(int base) {
	}

	@Override
	public void setDestination(int base) {
	}
	private final long valueToLong() {
        StringBuffer valueString = new StringBuffer();
        valueString.append(this.value);
        return Long.parseLong(valueString.toString());
    }

}
	
