package lib.data;

import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedList;

public class LinkedSet implements Iterable<Integer> {
	
	private BitSet indicator;
	private LinkedList<Integer> elements;
	
	public LinkedSet() {
		indicator = new BitSet();
		elements = new LinkedList<>();
	}
	
	public void add(int x) {
		if(!contains(x)) {
			indicator.set(x);
			elements.add(x);
		}
	}
	
	public boolean contains(int x) {
		return indicator.get(x);
	}

	public Iterator<Integer> iterator() {
		return elements.iterator();
	}
	
}
