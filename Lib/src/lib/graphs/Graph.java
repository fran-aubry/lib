package lib.graphs;

import java.util.Iterator;

public abstract class Graph implements Iterable<Integer> {

	protected int V, E;

	public int V() { 
		return V;
	}

	public int E() {
		return E;
	}

	public void disconnectAll() {
		for(int x = 0; x < V; x++) {
			for(int y : outNeighbors(x)) {
				disconnect(x, y);
			}
		}
	}

	public Graph reverse() {
		Graph g = this.create(V);
		for(int x = 0; x < V; x++) {
			for(int y : outNeighbors(x)) {
				g.connect(y, x);
			}
		}
		return g;
	}


	public void connect(Edge e) {
		connect(e.orig, e.dest);
	}

	public void connectBoth(Edge e) {
		connectBoth(e.orig, e.dest);
	}

	public void disconnect(Edge e) {
		disconnect(e.orig, e.dest);
	}

	public void disconnectBoth(Edge e) {
		disconnectBoth(e.orig, e.dest);
	}

	public boolean connected(Edge e) {
		return connected(e.orig, e.dest);
	}

	public boolean connectedBoth(Edge e) {
		return connectedBoth(e.orig, e.dest);
	}

	public Iterator<Integer> iterator() {
		return new NodeIterator(V);
	}

	public void connectBoth(int x, int y) {
		connect(x, y);
		connect(y, x);
	}

	public void disconnectBoth(int x, int y) {
		disconnect(x, y);
		disconnect(y, x);
	}

	public boolean connectedBoth(int x, int y) {
		return connected(x, y) && connected(y, x);
	}

	public void or(Graph g) {
		for(int x : g) {
			for(int y : g.outNeighbors(x)) {
				connect(x, y);
			}
		}
	}

	public void orBoth(Graph g) {
		for(int x : g) {
			for(int y : g.outNeighbors(x)) {
				connectBoth(x, y);
			}
		}
	}

	public boolean disjoint(Graph g) {
		if(E <= g.E) {
			for(int x : this) {
				for(int y : outNeighbors(x)) {
					if(g.connected(x, y)) return false;
				}
			}
			return true;
		}
		return g.disjoint(this);
	}

	/*
	 * Compute a string representation of the graph.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("out neighbors\n");
		for(int x = 0; x < V; x++) {
			sb.append(x);
			sb.append(':');
			for(int y : outNeighbors(x)) {
				sb.append(' ');
				sb.append(y);
			}
			sb.append('\n');
		}
		/*
		sb.append("in neighbors\n");
		for(int x = 0; x < V; x++) {
			sb.append(x);
			sb.append(':');
			for(int y : inNeighbors(x)) {
				sb.append(' ');
				sb.append(y);
			}
			sb.append('\n');
		}
		 */
		return sb.toString();
	}

	public Graph deepCopy() {
		Graph g = create(V);
		for(int x = 0; x < V; x++) {
			for(int y : outNeighbors(x)) {
				g.connect(x, y);
			}
		}
		return g;
	}

	/*
	 * Retrieves some neighbor of node x.
	 * Returns -1 if outDeg(x) = 0.
	 */
	public abstract int neighbor(int x);
	
	public abstract int inDeg(int x);

	public abstract int outDeg(int x);

	public abstract void connect(int x, int y);

	public abstract void disconnect(int x, int y);

	public abstract boolean connected(int x, int y);

	public abstract int[] outNeighbors(int x);

	public abstract int[] inNeighbors(int x);

	public abstract Graph create(int V); 

}
