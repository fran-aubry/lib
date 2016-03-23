package lib.graphs.algorithms;

import java.util.BitSet;
import java.util.LinkedList;

public class Cut {

	private BitSet X, Y;
	private int value;
	
	/*
	 * Create a cut (X, Y) where X = {i : x[i] = true}
	 */
	public Cut(boolean[] x, int value) {
		this.value = value;
		X = new BitSet();
		Y = new BitSet();
		for(int i = 0; i < x.length; i++) {
			if(x[i]) X.set(i);
			else Y.set(i);
		}
	}
	
	public int value() {
		return value;
	}
	
	public BitSet X() {
		return X;
	}
	
	public BitSet Y() {
		return Y;
	}
	
	private class CutSet {
		
		private LinkedList<Integer> elements;
		private BitSet set;
		
		public CutSet(LinkedList<Integer> elements) {
			this.elements = elements;
			for(int x : elements) {
				set.set(x);
			}
		}
		
		public int size() {
			return elements.size();
		}
		
		public void intersect(CutSet other) {
			if(elements.size() < other.size()) {
				
			}
		}
		
	}
	
	public String toString() {
		return "[" + X.toString() + " , " + Y.toString() + "]";
	}
	
}
