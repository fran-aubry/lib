package lib.graphs;

import java.util.HashMap;
import java.util.Map.Entry;

import lib.graphs.algorithms.GraphMetadata;

/**
 * This class represents a function with integer
 * values defined on pairs of integers. 
 * We use it for edges.
 */
public class EdgeFunction {

	private HashMap<Long, Integer> F;
	
	public EdgeFunction() {
		F = new HashMap<>();
	}
	
	public EdgeFunction(Graph g, int c) {
		F = new HashMap<>();
		for(int x = 0; x < g.V(); x++) {
			for(int y : g.outNeighbors(x)) {
				set(x, y, c);
			}
		}
	}
	
	public EdgeFunction(Path p, int c) {
		F = new HashMap<>();
		for(Edge e : p.edges()) {
			set(e.orig, e.dest, c);
		}
	}
	
	/*
	 * Get the value of F(x, y).
	 * O[1] expected
	 */
	public int get(int x, int y) {
		return F.get(getXY(x, y));
	}
	
	public int get(Edge e) {
		return get(e.orig, e.dest);
	}
	
	/*
	 * Set the value of F(x, y) = val.
	 * O[1] expected
	 */
	public void set(int x, int y, int val) {
		F.put(getXY(x, y), val);
	}
	
	/*
	 * Set the value of F(x, y) = F(y, x) = val.
	 * O[1] expected
	 */
	public void setUnd(int x, int y, int val) {
		set(x, y, val);
		set(y, x, val);
	}
	
	/*
	 * Add a value to F(x, y), i.e, F(x, y) += val
	 * O[1] expected
	 */
	public boolean add(int x, int y, int val) {
		if(!defined(x, y)) set(x, y, val);
		else set(x, y, val + get(x, y));
		return get(x, y) == 0;
	}
	
	public boolean add(Edge e, int val) {
		return add(e.orig, e.dest, val);
	}
	
	/*
	 * Check if F is defined on (x, y).
	 * O[1] expected
	 */
	public boolean defined(int x, int y) {
		return F.containsKey(getXY(x, y));
	}
	
	/*
	 * Evaluate the function on a graph.
	 * Computes sum[e in E(g)] F(e).
	 * O[V + E] expected
	 */
	public int eval(Graph g) {
		int val = 0;
		for(int x = 0; x < g.V(); x++) {
			for(int y : g.outNeighbors(x)) {
				val += get(x, y);
			}
		}
		return val;
	}
	
	/*
	 * Make a copy of this function.
	 * O[E]
	 */
	public EdgeFunction copy() {
		EdgeFunction cp = new EdgeFunction();
		for(Entry<Long, Integer> e : F.entrySet()) {
			cp.set(getX(e.getKey()), getY(e.getKey()), e.getValue());
		}
		return cp;
	}
	
	/*
	 * Set the value of the function on the edges
	 * of a path to be c.
	 * O[number of edges in p] expected
	 */
	public void set(Path p, int c) {
		for(Edge e : p.edges()) {
			set(e.orig, e.dest, c);
		}
	}
	
	/*
	 * Evaluate the function on a path.
	 * Computes sum[e in E(p)] F(e).
	 * O[number of edges of p] expected
	 */
	public int eval(Path p) {
		int val = 0;
		for(Edge e : p.edges()) {
			val += get(e.orig, e.dest);
		}
		return val;
	}
	
	/*
	 * Compose two int's into a long by
	 * concatenating they bits.
	 * O[1]
	 */
	private long getXY(int x, int y) {
		return ((long)x << 31) | y;
	}
	
	/*
	 * Get the x from a long x|y (first half of the bits).
	 * O[1]
	 */
	private int getX(long xy) {
		return (int)(xy >> 31);
	}
	
	/*
	 * Get the y from a long x|y (second half of the bits).
	 * O[1]
	 */
	private int getY(long xy) {
		return (int)(xy & 0x7fffffff);
	}
	
	public Edge[] domain() {
		Edge[] dom = new Edge[F.size()];
		int i = 0;
		for(Long xy : F.keySet()) {
			dom[i++] = new Edge(getX(xy), getY(xy));
		}
		return dom;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(long e : F.keySet()) {
			sb.append(String.format("(%d, %d) = %d\n", getX(e), getY(e), F.get(e)));
		}
		return sb.toString();
	}
	
	public String toString(GraphMetadata gmdt) {
		StringBuilder sb = new StringBuilder();
		for(long e : F.keySet()) {
			sb.append(String.format("(%s, %s) = %d\n", gmdt.getLabel(getX(e)), gmdt.getLabel(getY(e)), F.get(e)));
		}
		return sb.toString();
	}
	
}
