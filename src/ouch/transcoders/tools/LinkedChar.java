/**
 * 
 */
package ouch.transcoders.tools;

/**
 * @author Alexander Kopp
 *
 */

public class LinkedChar {
	
	private final char chr;
	private int index;
	
	/**
	 * 
	 */
	public LinkedChar(char chr) {
		this.chr = chr;
		this.index = 0;
	}
	

	public char getChar() {
		return chr;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public void updateIndex(int amount) {
		this.index += amount;
	}
	
	
	
	

}
