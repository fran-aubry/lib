package lib.graphs;

import java.util.Collection;

import lib.graphs.algorithms.GraphMetadata;


/**
 * Class that represents a graph.
 * 
 *
 */
public class SparseGraph extends Graph {

	private SparseSet[] outN; // outN[x] contains the out-neighbors of x
	private SparseSet[] inN; // inN[x] contains the in-neighbors of x
	
	public SparseGraph(int V) {
		this.V = V;
		E = 0;
		outN = new SparseSet[V];
		inN = new SparseSet[V];
		for(int x = 0; x < V; x++) {
			outN[x] = new SparseSet();
			inN[x] = new SparseSet();
		}
	}

	public void connectAll(Collection<Edge> edges) {
		for(Edge e : edges) {
			connect(e);
		}
	}
	
	public boolean[][] adjacencyMatrix() {
		boolean[][] A = new boolean[V][V];
		for(int i = 0; i < V; i++) {
			for(int j = 0; j < V; j++) {
				A[i][j] = connected(i, j);
			}
		}
		return A;
	}
	
	public Edge[] edges() {
		Edge[] edges = new Edge[E];
		int i = 0;
		for(int x : this) {
			for(int y : outNeighbors(x)) {
				edges[i++] = new Edge(x, y);
			}
		}
		return edges;
	}

	/*
	 * Get the number of vertices in the graph.
	 * O(1)
	 */
	public int V() {
		return V;
	}
	
	/*
	 * Get the number of edges in the graph.
	 * O(1)
	 */
	public int E() {
		return E;
	}
	
	public void connect(Edge e) {
		connect(e.orig, e.dest);
	}
	
	/*
	 * Create an edge from x to y.
	 * O(1)
	 */
	public void connect(int x, int y) {
		if(!connected(x, y)) {
			outN[x].add(y);
			inN[y].add(x);
			E++;
		}
	}

	public void connectBoth(int x, int y) {
		connect(x, y);
		connect(y, x);
	}
	
	public void connectBoth(Edge e) {
		connect(e.orig, e.dest);
		connect(e.dest, e.orig);
	}
	
	/*
	 * Check if there exists an edge from x to y.
	 * O(1)
	 */
	public boolean connected(int x, int y) {
		return outN[x].contains(y);
	}
	
	public boolean connected(Edge e) {
		return connected(e.orig, e.dest);
	}
	
	public boolean connectedBoth(int x, int y) {
		return connected(x, y) && connected(y, x);
	}
	
	public boolean connectedBoth(Edge e) {
		return connectedBoth(e.orig, e.dest);
	}
	
	/*
	 * Remove the edge from x to y. If no
	 * such edge exists, the method does
	 * nothing.
	 * O(1)
	 */
	public void disconnect(int x, int y) {
		if(connected(x, y)) {
			outN[x].remove(y);
			inN[y].remove(x);
			E--;
		}
	}
	
	public void disconnectBoth(int x, int y) {
		disconnect(x, y);
		disconnect(y, x);
	}
	
	
	public int[] outNeighbors(int x) {
		return outN[x].toArray();
	}
	
	/*
	 * Retrieve the out-neighbors of x.
	 * O(1) (iteration is O(out-deg(x)))
	 */
	public int[] inNeighbors(int x) {
		return inN[x].toArray();
	}
	
	public void deleteInNeighbors(int x) {
		inN[x].clear();
	}
	
	public void deleteOutNeighbors(int x) {
		outN[x].clear();
	}
	
	/*
	 * Get the in-degree of x.
	 * O(1)
	 */
	public int inDeg(int x) {
		return inN[x].size();
	}
	
	/*
	 * Get the out-degree of x.
	 * O(1)
	 */
	public int outDeg(int x) {
		return outN[x].size();
	}
	
	public SparseGraph reverse() {
		SparseGraph gr = new SparseGraph(V);
		for(int x : this) {
			for(int y : outNeighbors(x)) {
				gr.connect(y, x);
			}
		}
		return gr;
	}
		
	public String toString(GraphMetadata gmdt) {
		StringBuilder sb = new StringBuilder();
		sb.append(gmdt.getGraphName());
		sb.append('\n');
		for(int x = 0; x < V; x++) {
			sb.append(gmdt.getLabel(x));
			sb.append(':');
			for(int y : outNeighbors(x)) {
				sb.append(' ');
				sb.append(gmdt.getLabel(y));
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	public Graph create(int V) {
		return new SparseGraph(V);
	}

	public int neighbor(int x) {
		return outN[x].getFirst();
	}
	
}
