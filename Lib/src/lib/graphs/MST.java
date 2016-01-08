package lib.graphs;

import java.util.Arrays;

import lib.data.DisjointSet;

public class MST {

	public static Graph kruskal(Graph g, Measure w) {
		DisjointSet ds = new DisjointSet(g.V());
		Edge[] edges = g.edges();
		Arrays.sort(edges, new EdgeCmp(w));
		Graph mst = new TreeGraph(g.V());
		for(Edge e : edges) {
			if(ds.findSet(e.orig) != ds.findSet(e.dest)) {
				mst.connect(e.orig, e.dest);
				mst.connect(e.dest, e.orig);
				ds.union(e.orig, e.dest);
			}
		}
		return mst;

	}

}