package ouch.transcoders.NumberSystems;

import java.util.ArrayList;
import java.util.Collections;

import ouch.Readers.TextReadable;
import ouch.transcoders.Metricable;

public class NumberSystemTranscoder implements NumbersystemTransformable {

  static class NumberSystemMetrics implements Metricable {
	private long valueToCalc;
	private final int base;
	private final int destBase;
	private final String transcoderResult;
	private final long transcoderResultLong;
	private final int numLength;
	private ArrayList<Integer> valueList;

	public NumberSystemMetrics(long valueToCalc, int base, 
			int destBase, long transcoderResultLong, ArrayList<Integer> valueList, int numLength, String transcoderResult) {
		super();
		this.valueToCalc = valueToCalc;
		this.base = base;
		this.destBase = destBase;
		this.transcoderResultLong = transcoderResultLong;
		this.valueList = valueList;
		this.numLength = numLength;
		this.transcoderResult = transcoderResult;
	}

	public String toString() {
		if (base == 10) {
		StringBuffer result = new StringBuffer();
		result.append("Convert:");
		result.append("\n");
		ArrayList<Long> calcList = new ArrayList<>();
	      long rest = valueToCalc;
	      
	      if (destBase != 0) {
		      while (rest != 0) {
		    	  result.append(rest);
			      result.append(" : ");
			      result.append(destBase);
			      result.append(" = ");
		          calcList.add(rest % destBase);
		          rest = rest / destBase;
		          result.append(rest);
		          result.append(" Rest: ");
		          result.append(rest % destBase);
		          result.append("\n");
		      }
	      }
	      result.append("Result: ");
	      result.append(transcoderResult);
		return result.toString();
		}
		else if (base != 10) {
			StringBuffer result = new StringBuffer();
		    StringBuffer resultToDestBase = new StringBuffer();
		    resultToDestBase.append("Convert from base ");
		    resultToDestBase.append(base);
		    resultToDestBase.append(" to base 10 (Decimalsystem):\n");
			ArrayList<Long> showCalc = new ArrayList<>();
			long decTmp = 0L;
			long res = 0L;
	
		      int count = 0;
		      for (int i: this.valueList) {
		    	  resultToDestBase.append(i);
		    	  resultToDestBase.append(" * ");
		    	  resultToDestBase.append((int)Math.pow(base, (numLength-1) - count));
		    	  resultToDestBase.append(" = ");
		          decTmp = (long) (i * Math.pow(base, (numLength-1) - count));
		          resultToDestBase.append(decTmp);
		          resultToDestBase.append("\n");
		          showCalc.add(decTmp);
		          res += decTmp;
		          count++;
		      }
		      resultToDestBase.append("Result: ");
		      int counter = 0;
		      for (long l: showCalc) {
		    	  resultToDestBase.append(l);
		    	  counter++;
		    	  if (counter != showCalc.size()) {
		    		  resultToDestBase.append(" + ");
		    	  }
		      }
		      resultToDestBase.append(" = ");
		      resultToDestBase.append(res);
		      
		      result.append(resultToDestBase.toString());
		      result.append("\n");
			      
			StringBuffer resultToBase = new StringBuffer();
			resultToBase.append("\n");
			resultToBase.append("Convert from base 10 (Decimalsystem) to ");
			resultToBase.append(destBase);
			resultToBase.append(" :\n");
			ArrayList<Long> calcList = new ArrayList<>();
		      long rest = valueToCalc;
		      
		      if (destBase != 0) {
			      while (rest != 0) {
			    	  resultToBase.append(rest);
			    	  resultToBase.append(" : ");
			    	  resultToBase.append(destBase);
			    	  resultToBase.append(" = ");
			          calcList.add(rest % destBase);
			          rest = rest / destBase;
			          resultToBase.append(rest);
			          resultToBase.append(" Rest: ");
			          resultToBase.append(rest % destBase);
			          resultToBase.append("\n");
			      }
		      }
		      resultToBase.append("Result: ");
		      resultToBase.append(transcoderResult);
		      result.append(resultToBase.toString());
		      

			      
			      return result.toString();
			
		}
		else {
			StringBuffer result = new StringBuffer();
			result.append("Convert:");
			result.append("\n");
			ArrayList<Long> showCalc = new ArrayList<>();
			long decTmp = 0L;
			long res = 0L;

		      int count = 0;
		      for (int i: this.valueList) {
		    	  result.append(i);
		    	  result.append(" * ");
			      result.append((int)Math.pow(base, (numLength-1) - count));
			      result.append(" = ");
		          decTmp = (long) (i * Math.pow(base, (numLength-1) - count));
		          result.append(decTmp);
		          result.append("\n");
		          showCalc.add(decTmp);
		          res += decTmp;
		          count++;
		      }
		      result.append("Result: ");
		      int counter = 0;
		      for (long l: showCalc) {
		    	  result.append(l);
		    	  counter++;
		    	  if (counter != showCalc.size()) {
		    		  result.append(" + ");
		    	  }
		      }
		      result.append(" = ");
		      result.append(res);
		      
		      return result.toString();
		}
	}
  }
	
  private int base;
  private int destBase;
  private int numLength;
  private String value;
  private long valueToCalc = 0;

  public NumberSystemTranscoder(String value) {
      this.value = value;
      numLength = getValueLength();
  }
  public NumberSystemTranscoder() {
      this.base = 0;
      this.value = null;
      this.destBase = 0;
      numLength = 0;
  }
  public String convert() {
	  
	  this.numLength = getValueLength();
      valueToCalc = numToDec();
      return decToBase();
  }
  private final Long numToDec() {
      long decTmp = 0L;

      int count = 0;
      for (int i: valueList()) {
          decTmp += (long) (i * Math.pow(base, (numLength-1) - count));
          count++;
      }
      return decTmp;
  }
  private final String decToBase() {
      ArrayList<Long> calcList = new ArrayList<>();
      long rest = valueToCalc;
      if (destBase != 0) {
	      while (rest != 0) {
	          calcList.add(rest % destBase);
	          rest = rest / destBase;
	      }
      }

      Collections.reverse(calcList);

      StringBuffer result = new StringBuffer();
 
          calcList.forEach(elem->{
                  if (elem.intValue() > 9) {
                      switch (elem.intValue()) {
                          case 10:
                              result.append("A");
                              break;
                          case 11:
                              result.append("B");
                              break;
                          case 12:
                              result.append("C");
                              break;
                          case 13:
                              result.append("D");
                              break;
                          case 14:
                              result.append("E");
                              break;
                          case 15:
                              result.append("F");
                              break;
                      }
                  }
                  else {
                      result.append(elem);
                  }
              });
      return result.toString();
  }

  private final int getValueLength() {
      String valueToString = this.value.toString();
      return valueToString.length();
  }

  private final ArrayList<Integer> valueList() {
      StringBuffer valueString = new StringBuffer();
      valueString.append(this.value);
      ArrayList<Integer> result = new ArrayList<>();
      for (int i = 0; i < this.numLength; i++) {
          switch (valueString.charAt(i)) {
              case 'A': result.add(10);
                  break;
              case 'B': result.add(11);
                  break;
              case 'C': result.add(12);
                  break;
              case 'D': result.add(13);
                  break;
              case 'E': result.add(14);
                  break;
              case 'F': result.add(15);
                  break;
              default:result.add(valueString.charAt(i) - '0');
          }
      }
      return result;
  }
  
	@Override
	public String encode(TextReadable text) {
		String inputValue = text.getEntireString();
		this.value = inputValue.toUpperCase();
		this.valueToCalc = numToDec();
	    return convert();
	}

	@Override
	public String decode(TextReadable text) {
		return encode(text);
	}
	
	@Override
	public Metricable getLastDiff() {
		return (new NumberSystemMetrics(this.valueToCalc, 
				this.base, this.destBase, numToDec(), valueList(), numLength, decToBase()));
	}

	@Override
	public void setSource(int base) {
		this.base = base;
	}

	@Override
	public void setDestination(int base) {
		this.destBase = base;
	}
}
