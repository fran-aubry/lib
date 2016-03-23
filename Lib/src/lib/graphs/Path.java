package lib.graphs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that represents a path.
 * 
 */
public class Path {
	
	private int[] nodes; // nodes of the path
	private Edge[] edges; // edges of the path
	private HashSet<Edge> edgeSet; // edges as a set for O[1] lookup
	
	public Path(int[] v) {
		this.nodes = v;
		edges = new Edge[v.length - 1];
		edgeSet = new HashSet<>();
		for(int i = 0; i < v.length - 1; i++) {
			edges[i] = new Edge(v[i], v[i + 1]);
			edgeSet.add(edges[i]);
		}
	}
	
	public Path(LinkedList<Integer> Lv) {
		nodes = new int[Lv.size()];
		int i = 0;
		for(int x : Lv) {
			nodes[i++] = x;
		}
		edges = new Edge[nodes.length - 1];
		edgeSet = new HashSet<>();
		for(i = 0; i < nodes.length - 1; i++) {
			edges[i] = new Edge(nodes[i], nodes[i + 1]);
			edgeSet.add(edges[i]);
		}
		
	}
	
	public Path() {
		nodes = new int[0];
	}
	
	/*
	 * Check if the path contains a given edge.
	 * O[1] expected
	 */
	public boolean contains(Edge e) {
		return edgeSet.contains(e);
	}
	
	/*
	 * Get the length of the path (number of edges).
	 * O[1]
	 */
	public int length() {
		return nodes.length - 1;
	}
	
	/*
	 * Get the first node in the path.
	 * O[1]
	 */
	public int first() {
		return nodes[0];
	}
	
	/*
	 * Get the last node in the path.
	 * O[1]
	 */
	public int last() {
		return nodes[length()];
	}
	
	/*
	 * Get the nodes in the path.
	 */
	public int[] nodes() {
		return nodes;
	}
	
	/*
	 * Get the edges in the path.
	 * O[1]
	 */
	public Edge[] edges() {
		return edges;
	}
	
	/*
	 * Check is there are edge repetitions in the path.
	 * O[length of the path] expected
	 */
	public boolean repeatsEdges() {
		EdgeFunction measure = new EdgeFunction(this, 0);
		for(Edge e : edges()) {
			if(measure.get(e.orig, e.dest) == 1) return true;
			measure.set(e.orig, e.dest, 1);
		}
		return false;
	}
	
	/*
	 * Find all edge repetitions in the path.
	 * O[length of the path] expected
	 */
	public List<Edge> repeatedEdges() {
		EdgeFunction measure = new EdgeFunction(this, 0);
		List<Edge> rep = new LinkedList<Edge>();
		for(Edge e : edges()) {
			if(measure.get(e.orig, e.dest) == 1) {
				rep.add(e);
			}
			measure.set(e.orig, e.dest, 1);
		}
		return rep;
	}
	
	/*
	 * Concatenate two paths (must end with the node the other starts with).
	 * O[sum of length of paths]
	 */
	public Path concat(Path o) {
		int[] V = new int[nodes.length + o.nodes.length];
		for(int i = 0; i < V.length; i++) {
			V[i] = i < nodes.length ? nodes[i] : o.nodes[i - nodes.length];
		}
		return new Path(V);
	}
	
	/*
	 * Append two paths.
	 * O[sum of length of paths]
	 */
	public Path append(Path o) {
		int[] V = new int[nodes.length + o.nodes.length - 1];
		for(int i = 0; i < V.length; i++) {
			V[i] = i < nodes.length - 1 ? nodes[i] : o.nodes[i - nodes.length + 1];
		}
		return new Path(V);
	}
	
	public String toString() {
		return Arrays.toString(nodes);
	}

}
