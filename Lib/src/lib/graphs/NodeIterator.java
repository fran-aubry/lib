package lib.graphs;

import java.util.Iterator;

public class NodeIterator implements Iterator<Integer> {

	private int cur, V;
	
	public NodeIterator(int V) {
		cur = 0;
		this.V = V;
	}
	
	public boolean hasNext() {
		return cur < V;
	}

	public Integer next() {
		return cur++;
	}
	
	public void remove() {}
	
}