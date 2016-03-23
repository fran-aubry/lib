package lib.graphs.algorithms;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import lib.graphs.EdgeFunction;
import lib.graphs.Graph;

/**
 * Implementation of Dijkstra's algorithm.
 * 
 * @author f.aubry@uclouvain.be
 */

public class Dijkstra {
	
	/*
	 * Compute the shortest paths from a given source.
	 * O[E log(V)]
	 */
	public static DijkstraData shortestPathFrom(Graph g, int source,  EdgeFunction w) {
		// initialize distance array
		int[] distance = new int[g.V()];
		Arrays.fill(distance, Integer.MAX_VALUE);
		distance[source] = 0;
		// initialize parent array
		@SuppressWarnings("unchecked")
		List<Integer>[] parent = new LinkedList[g.V()];
		for(int i = 0; i < g.V(); i++) {
			parent[i] = new LinkedList<>();
		}
		// initialize path length (edges)
		int[] length = new int[g.V()];
		Arrays.fill(length, -1);
		length[source] = 0;
		// compute shortest paths
		PriorityQueue<Integer> Q = new PriorityQueue<Integer>(g.V(), new VertexCmp(distance));
		Q.add(source);
		while(!Q.isEmpty()) {
			int v = Q.poll();
			for(int u : g.outNeighbors(v)) {
				if(distance[v] + w.get(v, u) < distance[u]) {
					distance[u] = distance[v] + w.get(v, u);
					length[u] = length[v] + 1;
					parent[u].clear();
					parent[u].add(v);
					Q.add(u);
				} else if(distance[v] + w.get(v, u) == distance[u]) {
					parent[u].add(v);
				}
			} 
		}
		return new DijkstraData(g, source, distance, length, parent);
	}
	
	public static DijkstraData shortestPathTo(Graph g, int t, EdgeFunction w) {
		Graph gr = g.reverse();
		DijkstraData data = shortestPathFrom(gr, t, w);
		data.g = g;
		data.dag = data.dag.reverse();
		return data;
	}
	
	
	/*
	 * Class used to compare vertices by distance.
	 */
	private static class VertexCmp implements Comparator<Integer> {
		
		private int[] distance;
		
		public VertexCmp(int[] distance) {
			this.distance = distance;
		}
		
		public int compare(Integer o1, Integer o2) {
			return Double.compare(distance[o1], distance[o2]);
		}
	}
	
	/*
	 * Compute all shortest path dags.
	 * O[V E log(V)]
	 */
	public static Graph[] shortestPathDags(Graph g, EdgeFunction IGP) {
		Graph[] spDag = new Graph[g.V()];
		for(int x = 0; x < g.V(); x++) {
			spDag[x] = shortestPathFrom(g, x, IGP).dag;
		}
		return spDag;
	}
	
	/*
	 * Compute the center of a graph.
	 * O[V E log(V)]
	 */
	public static int center(Graph g, EdgeFunction w) {
		// initialize the center and diameter
		int center = -1;
		int diameter = Integer.MAX_VALUE;
		for(int x = 0; x < g.V(); x++) {
			// compute shortest paths from x
			int[] distance = shortestPathFrom(g, x, w).distance;
			// get the longest shortest path
			int max = Integer.MIN_VALUE;
			for(int d : distance) {
				max = Math.max(max, d);
			}
			// update if smaller than diameter
			if(max < diameter) {
				diameter = max;
				center = x;
			}
		}
		return center;
	}
	
	/*
	 * Compute an EdgeFunction that is 1 on edges that belong
	 * to no shortest path dag and 0 otherwise
	 */
	public EdgeFunction edgesInNoSpDag(Graph g, Graph[] spDag) {
		EdgeFunction inNoDag = new EdgeFunction();
		for(int x : g) {
			for(int y : g.outNeighbors(x)) {
				boolean inDag = false;
				for(Graph dag : spDag) {
					if(dag.connected(x, y)) {
						inDag = true;
						break;
					}
				}
				if(!inDag) {
					inNoDag.set(x, y, 1);
				}
			}
		}
		return inNoDag;
	}
	
	public static Graph subDag(Graph dag, int t) {
		Graph sub = dag.create(dag.V());
		Queue<Integer> Q = new LinkedList<>();
		Q.add(t);
		BitSet visited = new BitSet();
		visited.set(t);
		while(!Q.isEmpty()) {
			int cur = Q.poll();
			for(int y : dag.inNeighbors(cur)) {
				sub.connect(y, cur);
				if(!visited.get(y)) {
					visited.set(y);
					Q.add(y);
				}
			}
		}
		return sub;
	}
	
}
