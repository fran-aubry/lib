package lib.graphs.algorithms;

import lib.graphs.EdgeFunction;
import lib.graphs.Graph;

public class DirectedMST {

	
	public static void edminds(Graph g, EdgeFunction w) {
		
	}
	
	
	/*
	public static E[] in;
	public static int[] constant;
	public static int[] prev;
	public static int[] parent;
	public static int[][] children;
	public static PriorityQueue<E>[] P;
	public static int V;
	
	public static EdgeFunction W;
	
	@SuppressWarnings("unchecked")
	public static void initialize(Graph g) {
		in = new E[g.V()];
		constant = new int[g.V()];
		prev = new int[g.V()];
		parent = new int[g.V()];
		Arrays.fill(parent, -1);
		children = new int[g.V()][g.V()]; // TODO
		P = new PriorityQueue[g.V()];
		for(int i = 0; i < g.V(); i++) {
			P[i] = new PriorityQueue<>();
		}
		for(int x : g) {
			for(int y : g.outNeighbors(x)) {
				P[y].add(new E(x, y, W.get(x, y)));
			}
		}
	}
	
	public static int find(int u) {
		while(parent[u] != -1) {
			u = parent[u];
		}
		return u;
	}
	
	public static int weight(int u, int v) {
		int w = W.get(u, v);
		while(parent[u] != -1) {
			w += constant[v];
			v = parent[v];
		}
		return w;
	}
	
	public static void contract(Graph g, EdgeFunction W) {
		initialize(g);
		int a = 0;
		while(!P[a].isEmpty()) {
			E e = P[a].poll();
			int b = find(e.u);
			if(a == b) continue;
			in[a] = e;
			prev[a] = b;
			if(in[e.u] == null) {
				a = b;
			} else {
				int c = V + 1;
				while(parent[a] == -1) {
					parent[a] = c;
					constant[a] = -in[a].w;
					
					P[c] = new PriorityQueue<>();
					P[c].addAll(P[a]);
					a = prev[a];
				}
			}
		}
	}
	
	
	public static void dismantle(int u) {
		while(parent[u] != -1) {
			u = parent[u];
			for(int v : children[u]) {
				parent[v] = -1;
			

			}
		}
	}
	
	public static class E implements Comparable<E> {

		private int u, v, w;
		
		public E(int u, int v, int w) {
			this.u = u;
			this.v = v;
			this.w = w;
		}
		
		public int compareTo(E o) {
			return w - o.w;
		}
		
	}
	*/
	
	
}
