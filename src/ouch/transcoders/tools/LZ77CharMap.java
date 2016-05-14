package ouch.transcoders.tools;

import java.util.LinkedHashMap;

public class LZ77CharMap {
	
	private final LinkedHashMap<Character, LinkedCharList> map;

	public LZ77CharMap() {
		map = new LinkedHashMap<Character, LinkedCharList>();
	}
	
	public void add(char chr) {
		if (map.containsKey(chr)) {
			map.get(chr).add(chr);
		} else {
			map.put(chr, new LinkedCharList(chr));
		}
	}
	
	public void updateIndexes(int amount) {
		for (LinkedCharList lcl : map.values()) {
			//char key = lcl.chr;
			lcl.updateIndex(amount);
			if (lcl.getSize() <= 0) {
				map.remove(lcl.chr);
			}
		}
	}
	
	

}
