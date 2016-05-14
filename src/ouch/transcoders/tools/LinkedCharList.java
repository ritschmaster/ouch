package ouch.transcoders.tools;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedCharList {
	private static final int SEARCH_BUFFER_SIZE = 1024;
	
	final char chr;
	private final LinkedList<LinkedChar> list;
	
	
	public LinkedCharList(char chr) {
		this.chr = chr;
		this.list = new LinkedList<LinkedChar>();
		add(chr);
		
	}
	
	public void add(char chr) {
		list.add(new LinkedChar(chr));
	}
	
	public void removeFirst() {
		list.removeFirst();
	}
	
	public int getSize() {
		return list.size();
	}
	
	public void updateIndex(int amount) {
		for (int i = 0; i < list.size(); i++) {
			LinkedChar c = list.get(i);
			c.updateIndex(amount);
			if (c.getIndex() > SEARCH_BUFFER_SIZE) {
				list.remove(i);
			}
		}
	}
	
	public Iterator<LinkedChar> getIterator() {
		return list.iterator();
	}

}
