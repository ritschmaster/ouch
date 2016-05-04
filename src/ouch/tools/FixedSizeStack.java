package ouch.tools;

import java.util.Stack;

public class FixedSizeStack<E> extends Stack<E> {

	private static final long serialVersionUID = 285604025862597850L;
	private int maxSize;

    public FixedSizeStack(int maxSize) {
    	super();
    	this.maxSize = maxSize;
	}

	@Override
	public E push(E item) {
		
		if (this.size() >= maxSize) {
			this.remove(0);
		}
		
		return super.push(item);
	}
    
    
	
	
}
