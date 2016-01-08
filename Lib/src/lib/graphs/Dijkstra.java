package lib.graphs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import lib.geometry.Cmp;
import lib.util.ArraysExt;

public class Dijkstra {

	public final int NO_PARENT = -1;
	
	public int s;
	public double[] d;
	public int[] l;
	public Graph D;
	public List<Integer>[] p;
	
	public Dijkstra(Graph g, int s, Measure w) {
		this.s = s;
		dijkstra(g, w, new HashMeasure(g, 0));
		buildSPDag(g, p);
	}
	
	public static int center(Graph g, Measure w) {
		int center = -1;
		double diameter = Double.POSITIVE_INFINITY;
		for(int x = 0; x < g.V(); x++) {
			Dijkstra dijkstra = new Dijkstra(g, x, w);
			double[] d = dijkstra.d;
			double max = ArraysExt.max(d);
			if(Cmp.le(max, diameter)) {
				diameter = max;
				center = x;
			}
		}
		return center;
	}
	
	public static double[][] apsp(Graph g, Measure w) {
		double[][] d = new double[g.V()][];
		for(int x = 0; x < g.V(); x++) {
			d[x] = (new Dijkstra(g, x, w)).d;
		}
		return d;
	}
	
	public Dijkstra(Graph g, int s, Measure w, Measure forbidden) {
		this.s = s;
		dijkstra(g, w, forbidden);
		buildSPDag(g, p);
	}
	
	@SuppressWarnings("unchecked")
	private void dijkstra(Graph g, Measure w, Measure forbidden) {
		// initialize distance array
		d = new double[g.V()];
		Arrays.fill(d, Double.POSITIVE_INFINITY);
		d[s] = 0;
		// initialize parent array
		p = new LinkedList[g.V()];
		for(int i = 0; i < g.V(); i++) {
			p[i] = new LinkedList<Integer>();
		}
		// initialize path length (edges)
		l = new int[g.V()];
		Arrays.fill(l, NO_PARENT);
		l[s] = 0;
		// initialize shortest path DAG
		D = new TreeGraph(g.V());
		D.setName(g.getName());
		// compute shortest paths
		PriorityQueue<Integer> Q = new PriorityQueue<Integer>(g.V(), new VertexCmp());
		Q.add(s);
		while(!Q.isEmpty()) {
			int v = Q.poll();
			for(int u : g.outNeighbors(v)) {
				if(d[v] + w.getWeight(v, u) < d[u]) {
					d[u] = d[v] + w.getWeight(v, u);
					l[u] = l[v] + 1;
					p[u].clear();
					p[u].add(v);
					Q.add(u);
				} else if(d[v] + w.getWeight(v, u) == d[u]) {
					p[u].add(v);
				}
			} 
		}
	}
	
	public Graph getDag() {
		return D;
	}
	
	private void buildSPDag(Graph g, List<Integer>[] p) {
		D = new TreeGraph(g.V());
		for(int v = 0; v < g.V(); v++) {
			for(int u : p[v]) {
				D.connect(u, v);
			}
		}
	}
	
	public Path getPath(int t) {
		// check if a path exists
		if(t != s && p[t].isEmpty()) return null;
		// build path from parents
		int[] v = new int[l[t] + 1];
		for(int i = l[t], cur = t; i > 0; i--, cur = p[cur].get(0)) {
			v[i] = cur;
		}
		v[0] = s;
		return new Path(v);
	}
	
	
	private class VertexCmp implements Comparator<Integer> {
		
		@Override
		public int compare(Integer o1, Integer o2) {
			return Double.compare(d[o1], d[o2]);
		}
		
		
	}
	
}
