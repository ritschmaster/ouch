package ouch.transcoders.tools;

import java.util.Stack;
//TODO (if time) better data structure
public class FixedSizeStack<E> extends Stack<E> {

	private static final long serialVersionUID = 285604025862597850L;
	private int maxSize;

    public FixedSizeStack(int maxSize) {
    	super();
    	this.maxSize = maxSize;
	}

//	@Override
//	public E push(E item) {
//		
//		
//		
//		return super.push(item);
//	}
	
	public void trim() {
		while (this.size() > maxSize) {
			this.remove(0);
		}
	}
    
    
	
	
}
