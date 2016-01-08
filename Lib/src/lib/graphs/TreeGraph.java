package lib.graphs;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

public class TreeGraph extends Graph {

	public int V, E;
	public ArrayList<TreeSet<Integer>> out;
	public ArrayList<TreeSet<Integer>> in;
	public String[] nodeLabels;
	public String name;
	
	public TreeGraph(int V) {
		this.V = V;
		out = new ArrayList<>();
		for(int v = 0; v < V; v++) {
			out.add(new TreeSet<Integer>());
		}
		in = new ArrayList<>();
		for(int v = 0; v < V; v++) {
			in.add(new TreeSet<Integer>());
		}
		nodeLabels = new String[V];
		for(int i = 0; i < V; i++) {
			nodeLabels[i] = i + "";
		}
		name = "undefined";
	}

	public TreeGraph(boolean[][] A) {
		this.V = A.length;
		out = new ArrayList<>();
		for(int v = 0; v < A.length; v++) {
			out.add(new TreeSet<Integer>());
		}
		in = new ArrayList<>();
		for(int v = 0; v < A.length; v++) {
			in.add(new TreeSet<Integer>());
		}
		for(int v = 0; v < A.length; v++) {
			for(int u = 0; u < A.length; u++) {
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
		return out.size();
	}

	/**
	 * O(log(V))
	 */
	public boolean connected(int v, int u) {
		return out.get(v).contains(u);
	}

	/**
	 * O(dv)
	 */
	public int[] outNeighbors(int v) {
		int[] outN = new int[out.get(v).size()];
		int i = 0;
		for(int x : out.get(v)) {
			outN[i++] = x;
		}
		return outN;
	}

	/**
	 * O(log(dv))
	 */
	public void connect(int v, int u) {
		if(!connected(v, u)) {
			out.get(v).add(u);	
			in.get(u).add(v);
			E++;
		}
	}

	public void clear(int v) {
		out.get(v).clear();
		in.get(v).clear();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int v = 0; v < out.size(); v++) {
			sb.append(v);
			sb.append(": ");
			for(int u : out.get(v)) {
				sb.append("(" + u + ")");
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
			in.get(u).remove(v);
			E--;
		}
	}

	public Graph copy() {
		Graph gc = new TreeGraph(V());
		for(int i = 0; i < V(); i++) {
			for(int j : outNeighbors(i)) {
				gc.connect(i, j);
			}
		}
		return gc;
	}

	public int E() {
		int cnt = 0;
		for(int i = 0; i < V(); i++) {
			cnt += out.get(i).size();
		}
		return cnt;
	}

	public void addNode() {
		in.add(new TreeSet<Integer>());
		out.add(new TreeSet<Integer>());
		V++;
	}

	public int[] inNeighbors(int v) {
		int[] inN = new int[in.get(v).size()];
		int i = 0;
		for(int x : in.get(v)) {
			inN[i++] = x;
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
		for(int x : in.get(in.size() - 1)) {
			out.get(x).remove(V() - 1);
		}
		out.remove(in.size() - 1);
		in.remove(in.size() - 1);
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
	
	public void splitEdge(int x, int y) {
		assert connected(x, y);
		addNode();
		disconnect(x, y);
		connect(x, V - 1);
		connect(V - 1, x);
		connect(y, V - 1);
		connect(V - 1, y);
	}

}
