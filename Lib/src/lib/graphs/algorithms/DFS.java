package lib.graphs.algorithms;

import java.util.Arrays;
import java.util.LinkedList;

import lib.graphs.Edge;
import lib.graphs.EdgeFunction;
import lib.graphs.Graph;
import lib.graphs.SparseGraph;

public class DFS {

	public static int UNVISITED = 0, OPEN = 1, CLOSED = 2;

	public static int[] state;
	public static int[] parent;
	public static int[] start;
	public static int[] end;
	public static int[] low;
	public static int[] outDeg;
	public static int[] componentsIfDeleted;
	public static int[] toposort;
	public static int[] root;
	public static boolean[] articulation;
	public static EdgeFunction bridges;

	public static int time;
	public static int V;
	public static int topoIndex;
	public static int nbBridges;

	public static Edge backEdge;
	public static Edge undBackEdge;

	/*
	 * Computes a cycle in g. Returns null if not cycle
	 * exists.
	 * 
	 * O[V + E]
	 */
	public static LinkedList<Integer> findCycle(SparseGraph g) {
		dfs(g);
		// check if a cycle exists
		if(backEdge == null) return null;
		/*
		 * If a back edge (u, v) exists, then there is a cycle of the 
		 * form
		 * 
		 * v-->o-->o
		 * ^       .
		 * |       .
		 * u<--o<--o
		 */
		LinkedList<Integer> cyclesNodes = new LinkedList<>();
		int cur = backEdge.orig;
		while(cur != backEdge.dest) {
			cyclesNodes.addFirst(cur);
			cur = parent[cur];
		}
		return cyclesNodes;
	}

	/*
	 * Computes a cycle in g. Returns null if not cycle
	 * exists.
	 * 
	 * O[V + E]
	 */
	public static LinkedList<Integer> findCycle2(SparseGraph g) {
		dfs(g);
		// check if a cycle exists
		if(undBackEdge == null) return null;
		/*
		 * If a back edge (u, v) exists, then there is a cycle of the 
		 * form
		 * 
		 * v-->o-->o
		 * ^       .
		 * |       .
		 * u<--o<--o
		 */
		LinkedList<Integer> cyclesNodes = new LinkedList<>();
		int cur = undBackEdge.orig;
		while(cur != undBackEdge.dest) {
			cyclesNodes.addFirst(cur);
			cur = parent[cur];
		}
		cyclesNodes.add(undBackEdge.dest);
		return cyclesNodes;
	}

	public static void dfs(Graph g) {
		dfsInit(g);
		for(int x : g) {
			if(state[x] == UNVISITED) {
				dfsVisit(g, x, x);

				articulation[x] = outDeg[x] >= 2;
				componentsIfDeleted[x] = outDeg[x];
			}
		}
	}

	public static int[] scc(Graph g) {
		dfs(g.reverse());
		int[] order = Arrays.copyOf(toposort, V);
		dfsInit(g);
		for(int x : order) {
			if(state[x] == UNVISITED) {
				dfsVisit(g, x, x);				
			}
		}
		return Arrays.copyOf(root, V);
	}

	private static void dfsInit(Graph g) {
		V = g.V();
		state = new int[V];
		parent = new int[V];
		Arrays.fill(parent, -1);
		start = new int[V];
		end = new int[V];
		low = new int[V];
		root = new int[V];
		outDeg = new int[V];
		componentsIfDeleted = new int[V];
		Arrays.fill(componentsIfDeleted, 1);
		toposort = new int[V];
		topoIndex = V - 1;
		articulation = new boolean[V];
		bridges = new EdgeFunction(g, 0);
		time = 0;
		backEdge = null;
		undBackEdge = null;
		nbBridges = 0;
	}

	private static void dfsVisit(Graph g, int x, int r) {
		state[x] = OPEN;
		low[x] = start[x] = time++;
		root[x] = r;
		for(int y : g.outNeighbors(x)) {
			if(state[y] == UNVISITED) {
				outDeg[x]++;
				parent[y] = x;
				dfsVisit(g, y, r);
				if(low[y] >= start[x]) {
					// x is articulation
					articulation[x] = true;
					componentsIfDeleted[x]++;
				}
				if(low[y] > start[x]) {
					// (x, y) is bridge
					nbBridges++;
					bridges.set(x, y, 1);
				}
				low[x] = Math.min(low[x], low[y]);
			} else {
				if(state[y] == OPEN) {
					// (x, y) is a back edge (belongs to a cycle)
					backEdge = new Edge(x, y);
					if(parent[x] != y) {
						// (x, y) is a back edge that forms a cycle of length >= 2
						undBackEdge = new Edge(x, y);
					}
				}
				if(parent[x] != y) {
					low[x] = Math.min(low[x], start[y]);					
				}
			}
		}
		time++;
		end[x] = time;
		state[x] = CLOSED;
		toposort[topoIndex--] = x;
	}

	public static void print() {
		System.out.print("articulation:");
		for(int x = 0; x < V; x++) {
			if(articulation[x]) {
				System.out.print(" " + x);
			}
		}
		System.out.println();
		System.out.print("bridges:");
		for(Edge e : bridges.domain()) {
			if(bridges.get(e) == 1) {
				System.out.print(" " + e);
			}
		}
	}
	
	
 

}
