package lib.graphs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 
 * This class represents the neighbors of a
 * node in a graph. All its operations have
 * expected optimal complexity. It allows to
 * iterate over the neighbors, add a neighbor,
 * remove a neighbor and check for connectivity.
 */

public class SparseSet implements Iterable<Integer> {

	private static final int DEFAULT_CAPACITY = 10;

	private int[] a;
	private int size;
	private HashMap<Integer, Integer> indexMap;

	public SparseSet(int outDeg) {
		a = new int[outDeg];
		size = 0;
		indexMap = new HashMap<>();
	}

	public SparseSet() {
		a = new int[DEFAULT_CAPACITY];
		size = 0;
		indexMap = new HashMap<>();
	}

	public int[] toArray() {
		return Arrays.copyOf(a, size);
	}

	/*
	 * Add element x in this list.
	 * O(1)
	 */
	public void add(int x) {
		// grow the list if needed
		if(size == a.length) grow();
		a[size] = x;
		indexMap.put(x, size);
		size++;
	}

	/*
	 * Allocates more size when full.
	 */
	private void grow() {
		int[] b = new int[2 * a.length + 1];
		for(int i = 0; i < a.length; i++) {
			b[i] = a[i];
		}
		a = b;
	}

	/*
	 * Check if element x is in the list.
	 * O(1)
	 */
	public boolean contains(int x) {
		return indexMap.containsKey(x);
	}

	/*
	 * Remove an element x from the list.
	 * If x is not in the list the method
	 * does nothing.
	 * O(1)
	 */
	public void remove(int x) {
		if(contains(x)) {
			size--;
			int i = indexMap.remove(x);
			if(i != size) {
				indexMap.put(a[size], i);
				int tmp = a[size];
				a[size] = a[i];
				a[i] = tmp;
			}
		}
	}

	public void clear() {
		for(int i = 0; i < size; i++) {
			indexMap.remove(a[i]);
		}
		size = 0;
	}

	public int getFirst() {
		return a[0];
	}

	/*
	 * Get an iterator for the list.
	 * Iteration is O(size).
	 */
	public Iterator<Integer> iterator() {
		return new AdjListIter(this);
	}

	/*
	 * Get the size of the list.
	 * O(1).
	 */
	public int size() {
		return size;
	}

	/**
	 * Iterator subclass for this class
	 */
	private class AdjListIter implements Iterator<Integer> {

		private SparseSet L;
		private int i;


		public AdjListIter(SparseSet L) {
			this.L = L;
			i = 0;
		}

		public boolean hasNext() {
			return i < L.size;
		}

		public Integer next() {
			return L.a[i++];
		}

		public void remove() {
			L.remove(L.a[i]);
		}

	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < size; i++) {
			sb.append(a[i]);
			if(i  < size - 1) {
				sb.append(' ');
			}
		}
		return sb.toString();
	}

}
