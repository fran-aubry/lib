package lib.graphs;

import java.util.HashMap;
import java.util.Map.Entry;

public class BoolMeasure {

	public HashMap<Long, Boolean> H;
	public boolean isBoolean;
	
	// Constructors
	
	public BoolMeasure() {
		H = new HashMap<Long, Boolean>();
	}
	
	public BoolMeasure(Graph g, boolean c) {
		H = new HashMap<Long, Boolean>();
		for(int x = 0; x < g.V(); x++) {
			for(int y : g.outNeighbors(x)) {
				set(x, y, c);
			}
		}
	}
	
	public BoolMeasure(Path p, boolean c) {
		H = new HashMap<Long, Boolean>();
		for(Edge e : p.edges()) {
			set(e, c);
		}
	}
	
	// Methods
	
	public boolean get(int v, int u) {
		return H.get(h(v, u));
	}
	
	public boolean get(Edge e) {
		return get(e.orig, e.dest);
	}

	public void set(int v, int u, boolean w) {
		H.put(h(v, u), w); 
	}
	
	public void set(Edge e, boolean w) {
		set(e.orig, e.dest, w);
	}
	
	public void set(Path p, boolean w) {
		for(Edge e : p.edges()) {
			set(e, w);
		}
	}
	
	public void clear(int v, int u) {
		H.remove(h(v, u));
	}

 	
	private long h(int v, int u) {
		return ((long) v << 31) | u;
	}
	
	

	public BoolMeasure getCopy() {
		BoolMeasure copy = new BoolMeasure();
		for(Entry<Long, Boolean> e : H.entrySet()) {
			copy.H.put(e.getKey(), e.getValue());
		}
		return copy;
	}
	
	public int totalWeight() {
		int total = 0;
		for(boolean v : H.values()) {
			total += v ? 1 : 0;
		}
		return total;
	}
	
	public int eval(Path path) {
		int total = 0;
		for(Edge e : path.edges()) {
			total += get(e) ? 1 : 0;
		}
		return total;
	}

	public int eval(Graph g) {
		int total = 0;
		for(int x = 0; x < g.V(); x++) {
			for(int y : g.outNeighbors(x)) {
				total += get(x, y) ? 1 : 0;
			}
		}
		return total;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		long mask = (1 << 31) - 1;
		for(Entry<Long, Boolean> e : H.entrySet()) {
			int v = (int) (e.getKey() >> 31);
			int u = (int) (e.getKey() & mask);
			sb.append(String.format("(%d, %d) | %.3f\n", v, u, e.getValue() ? "T" : "F"));
		}
		return sb.toString();
	}

}
