package lib.graphs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Path {
	
	public int[] v;
	public Edge[] edges;
	HashSet<Edge> edgeSet;
	
	public Path(int[] v) {
		this.v = v;
		edges = new Edge[v.length - 1];
		edgeSet = new HashSet<>();
		for(int i = 0; i < v.length - 1; i++) {
			edges[i] = new Edge(v[i], v[i + 1]);
			edgeSet.add(edges[i]);
		}
	}
	
	public Path(LinkedList<Integer> Lv) {
		v = new int[Lv.size()];
		int i = 0;
		for(int x : Lv) {
			v[i++] = x;
		}
		edges = new Edge[v.length - 1];
		edgeSet = new HashSet<>();
		for(i = 0; i < v.length - 1; i++) {
			edges[i] = new Edge(v[i], v[i + 1]);
			edgeSet.add(edges[i]);
		}
		
	}
	
	public boolean contains(Edge e) {
		return edgeSet.contains(e);
	}
	
	public Path() {
		v = new int[0];
	}
	
	public int length() {
		return v.length - 1;
	}
	
	public int first() {
		return v[0];
	}
	
	public int last() {
		return v[length()];
	}
	
	public Edge[] edges() {
		return edges;
	}
	
	public boolean repeatsEdges() {
		Measure measure = new HashMeasure(this, 0);
		for(Edge e : edges()) {
			if(measure.getWeight(e) == 1) return true;
			measure.setWeight(e, 1);
		}
		return false;
	}
	
	public List<Edge> repeatedEdges() {
		Measure measure = new HashMeasure(this, 0);
		List<Edge> rep = new LinkedList<Edge>();
		for(Edge e : edges()) {
			if(measure.getWeight(e) == 1) {
				rep.add(e);
			}
			measure.setWeight(e, 1);
		}
		return rep;
	}
	
	public Path concat(Path o) {
		int[] V = new int[v.length + o.v.length];
		for(int i = 0; i < V.length; i++) {
			V[i] = i < v.length ? v[i] : o.v[i - v.length];
		}
		return new Path(V);
	}
	
	public Path append(Path o) {
		int[] V = new int[v.length + o.v.length - 1];
		for(int i = 0; i < V.length; i++) {
			V[i] = i < v.length - 1 ? v[i] : o.v[i - v.length + 1];
		}
		return new Path(V);
	}
	
	public String toString() {
		return Arrays.toString(v);
	}

}
