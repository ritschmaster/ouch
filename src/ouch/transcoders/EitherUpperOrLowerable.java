package ouch.transcoders;

public interface EitherUpperOrLowerable {
	public enum StringFormat {
		UPPER,
		LOWER
	}
	
	public StringFormat getDecodeFormat();
	
	public StringFormat getEncodeFormat();
}
