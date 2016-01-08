package lib.graphs;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

public class HashMeasure implements Measure {

	public HashMap<Long, Double> H;
	
	public HashMeasure() {
		H = new HashMap<Long, Double>();
	}
	
	public HashMeasure(Graph g, double c) {
		H = new HashMap<Long, Double>();
		for(int x = 0; x < g.V(); x++) {
			for(int y : g.outNeighbors(x)) {
				setWeight(x, y, c);
			}
		}
	}
	
	public HashMeasure(Path p, double c) {
		H = new HashMap<Long, Double>();
		for(Edge e : p.edges()) {
			setWeight(e, c);
		}
	}

	
	public double getWeight(int v, int u) {
		return H.get(h(v, u));
	}

	public void setWeight(int v, int u, double w) {
		H.put(h(v, u), w); 
	}
	
	public double getWeight(Edge e) {
		return getWeight(e.orig, e.dest);
	}

	public void setWeight(Edge e, double w) {
		setWeight(e.orig, e.dest, w);
	}


	private long h(int v, int u) {
		return ((long) v << 31) | u;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		long mask = (1 << 31) - 1;
		for(Entry<Long, Double> e : H.entrySet()) {
			int v = (int) (e.getKey() >> 31);
			int u = (int) (e.getKey() & mask);
			sb.append(String.format("(%d, %d) | %.3f\n", v, u, e.getValue()));
		}
		return sb.toString();
	}

	public static Measure constMeasure(Graph g, double c) {
		Measure measure = new HashMeasure();
		for(int x = 0; x < g.V(); x++) {
			for(int y : g.outNeighbors(x)) {
				measure.setWeight(x, y, c);
			}
		}
		return measure;
	}
	
	public HashMeasure copy() {
		HashMeasure copy = new HashMeasure();
		for(Entry<Long, Double> e : H.entrySet()) {
			copy.H.put(e.getKey(), e.getValue());
		}
		return copy;
	}
	
	public double totalWeight() {
		double total = 0;
		for(double v : H.values()) {
			total += v;
		}
		return total;
	}
	
	public double eval(Path path) {
		double total = 0;
		for(Edge e : path.edges()) {
			total += getWeight(e);
		}
		return total;
	}

	public double eval(Graph g) {
		double total = 0;
		for(int x = 0; x < g.V(); x++) {
			for(int y : g.outNeighbors(x)) {
				total += getWeight(x, y);
			}
		}
		return total;
	}
	
	@Override
	public void remove(int v, int u) {
		H.remove(h(v, u));
	}

	@Override
	public void setWeight(Path p, double w) {
		for(Edge e : p.edges()) {
			setWeight(e, w);
		}
	}

}
