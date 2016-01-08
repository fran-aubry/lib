package lib.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class HashGraph extends Graph {

	public int V, E;
	public ArrayList<HashSet<Edge>> out;
	public ArrayList<HashSet<Edge>> in;
	public String[] nodeLabels;
	public String name;
	
	public HashGraph(int V) {
		this.V = V;
		out = new ArrayList<>();
		for(int v = 0; v < V; v++) {
			out.add(new HashSet<Edge>());
		}
		in = new ArrayList<>();
		for(int v = 0; v < V; v++) {
			in.add(new HashSet<Edge>());
		}
		nodeLabels = new String[V];
		for(int i = 0; i < V; i++) {
			nodeLabels[i] = i + "";
		}
		name = "undefined";
	}
	
	public HashGraph(boolean[][] A) {
		this.V = A.length;
		out = new ArrayList<>();
		for(int v = 0; v < V; v++) {
			out.add(new HashSet<Edge>());
		}
		in = new ArrayList<>();
		for(int v = 0; v < V; v++) {
			in.add(new HashSet<Edge>());
		}
		for(int v = 0; v < V; v++) {
			for(int u = 0; u < V; u++) {
				if(A[u][v]) {
					connect(v, u);
				}
			}
 		}
		nodeLabels = new String[V];
		for(int i = 0; i < V; i++) {
			nodeLabels[i] = i + "";
		}
		name = "undefined";
	}
	
	public int V() {
		return V;
	}
	
	public Edge[] outEdges(int v) {
		Edge[] outE = new Edge[out.get(v).size()];
		int i = 0;
		for(Edge e : out.get(v)) {
			outE[i++] = e;
		}
		return outE;
	}

	/**
	 * O(log(V))
	 */
	public boolean connected(int v, int u) {
		return out.get(v).contains(new Edge(v, u));
	}

	/**
	 * O(dv)
	 */
	public int[] outNeighbors(int v) {
		int[] outN = new int[out.get(v).size()];
		int i = 0;
		for(Edge e : out.get(v)) {
			outN[i++] = e.dest;
		}
		return outN;
	}

	/**
	 * O(log(dv))
	 */
	public void connect(int v, int u) {
		if(!connected(v, u)) {
			Edge e = new Edge(v, u);
			out.get(v).add(e);
			in.get(u).add(e);
			E++;
		}
	}

	public void clear(int v) {
		out.get(v).clear();
		in.get(v).clear();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int v = 0; v < V; v++) {
			sb.append(v);
			sb.append(": ");
			for(Edge e : out.get(v)) {
				sb.append("(" + e.dest + ")");
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * O(log(dv))
	 */
	public void disconnect(int v, int u) {
		if(connected(v, u)) {
			out.get(v).remove(u);
			out.get(u).remove(v);
			E--;
		}
	}

	public Graph copy() {
		Graph gc = new HashGraph(V);
		for(int i = 0; i < V; i++) {
			for(int j : outNeighbors(i)) {
				gc.connect(i, j);
			}
		}
		return gc;
	}

	public int E() {
		return E;
	}

	public void addNode() {
		out.add(new HashSet<Edge>());
		in.add(new HashSet<Edge>());
		V++;
	}

	public int[] inNeighbors(int v) {
		int[] inN = new int[in.get(v).size()];
		int i = 0;
		for(Edge e : in.get(v)) {
			inN[i++] = e.dest;
		}
		return inN;
	}

	public int outDeg(int v) {
		return out.get(v).size();
	}

	public int inDeg(int v) {
		return in.get(v).size();
	}

	public void removeLast() {
		for(Edge e : in.get(in.size() - 1)) {
			out.remove(e.dest);
		}
		in.remove(in.size() - 1);
		V--;
	}
	
	public int[] outNeighborsRnd(int v) {
		int[] out = outNeighbors(v);
		shuffleArray(outNeighbors(v));
		return out;
	}

	private void shuffleArray(int[] a) {
		Random rnd = new Random();
		for (int i = a.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			int tmp = a[index];
			a[index] = a[i];
			a[i] = tmp;
		}
	}

	public boolean connected(Edge e) {
		return connected(e.orig, e.dest);
	}

	public void connect(Edge e) {
		connect(e.orig, e.dest);
	}

	public void disconnect(Edge e) {
		disconnect(e.orig, e.dest);
	}

	public String[] getNodeLabels() {
		return nodeLabels;
	}
	
	public void setNodeLabels(String[] nodeLabels) {
		this.nodeLabels = nodeLabels;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
 
	public Edge[] edges() {
		Edge[] edges = new Edge[E()];
		int i = 0;
		for(int x = 0; x < V(); x++) {
			for(int y : outNeighbors(x)) {
				edges[i++] = new Edge(x, y);
			}
		}
		return edges;
	}
	
}
