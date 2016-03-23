package lib.graphs.algorithms;

import java.util.Collections;
import java.util.LinkedList;

import lib.data.DisjointSet;
import lib.geometry.Point;
import lib.geometry.Points;
import lib.graphs.BitGraph;
import lib.graphs.EdgeFunction;
import lib.graphs.Graph;
import lib.trees.EdgeRMaxQTree;

public class MST {
	
	public static LinkedList<MSTEdge> kruskal(int V, LinkedList<MSTEdge> edges) {
		DisjointSet ds = new DisjointSet(V);
		Collections.sort(edges);
		LinkedList<MSTEdge> mst = new LinkedList<>();
		for(MSTEdge e : edges) {
			if(ds.findSet(e.x) != ds.findSet(e.y)) {
				mst.add(e);
				ds.union(e.x, e.y);
			}
		}
		return mst;
	}
	
	/*
	 * This code works but it not very well integrated with 
	 * all the rest.
	 * 
	 * TODO: need to make it simples to go from one representation
	 * to the other.
	 */
	public static int secondBestMST(int V, LinkedList<MSTEdge> edges) {
		LinkedList<MSTEdge> mst = kruskal(V, edges);
		Graph g = new BitGraph(V);
		EdgeFunction w = new EdgeFunction();
		int cost = 0;
		for(MSTEdge edge : mst) {
			g.connectBoth(edge.x, edge.y);
			w.set(edge.x, edge.y, (int)edge.cost);
			w.set(edge.y, edge.x, (int)edge.cost);
			cost += (int)edge.cost;
		}
		BFS.bfs(g);
		int[] val = new int[g.V()];
		for(int x : g) {
			if(BFS.parent[x] != -1) {
				val[x] = w.get(x, BFS.parent[x]);				
			}
		}
		EdgeRMaxQTree rmq = new EdgeRMaxQTree(BFS.parent, val);
		int best = Integer.MAX_VALUE;
		for(MSTEdge e : edges) {
			if(g.connected(e.x, e.y)) continue;
			int max = rmq.rmq(e.x, e.y);
			best = Math.min(cost - max + (int)e.cost, best);
		}
		return best;
	}
	
	public static LinkedList<MSTEdge> discardedEdges(int V, LinkedList<MSTEdge> edges) {
		DisjointSet ds = new DisjointSet(V);
		Collections.sort(edges);
		LinkedList<MSTEdge> discarded = new LinkedList<>();
		for(MSTEdge e : edges) {
			if(ds.findSet(e.x) != ds.findSet(e.y)) {
				ds.union(e.x, e.y);
			} else {
				discarded.add(e);				
			}
		}
		return discarded;
	}
	
	public static double kruskalCost(int V, LinkedList<MSTEdge> edges) {
		LinkedList<MSTEdge> mst = kruskal(V, edges);
		double cost = 0;
		for(MSTEdge e : mst) {
			cost += e.cost;
		}
		return cost;
	}
	
	/*
	 * O(n^2) can be done in O(n log(n)) using Voronoi Diagram
	 */
	public static LinkedList<MSTEdge> euclideanMST(Point[] P) {
		LinkedList<MSTEdge> edges = new LinkedList<>();
		for(int i = 0; i < P.length; i++) {
			for(int j = i + 1; j < P.length; j++) {
				edges.add(new MSTEdge(i, j, Points.distance(P[i], P[j])));
			}
		}
		return kruskal(P.length, edges);
	}

	public static class MSTEdge implements Comparable<MSTEdge> {
		public int x, y;
		public double cost;
		public MSTEdge(int x, int y, double cost) {
			this.x = x;
			this.y = y;
			this.cost = cost;
		}
		public int compareTo(MSTEdge o) {
			return Double.compare(cost, o.cost);
		}
		public String toString() {
			return String.format("(%d, %d, %.3f)", x, y, cost);
		}
	}

}
