package lib.graphs.algorithms;

import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;

import lib.graphs.EdgeFunction;
import lib.graphs.Graph;
import lib.graphs.SparseGraph;

public class PushRelabel implements MaxFlow {

	public int[] E, H;
	public EdgeFunction rcap;
	public Graph gr;
	public Graph ga; // admissible network
	public int s, t;
	
	public ActiveList active;
	
	public int maxFlow(Graph g, EdgeFunction cap, int s, int t) {
		System.out.println(s + " " + t);
		this.rcap = cap.copy();
		this.gr = g.deepCopy();
		this.s = s;
		this.t = t;
		E = new int[g.V()];
		H = new int[g.V()];
		pushRelabel();
		return E[t];
	}

	public Cut minCut(Graph g, EdgeFunction cap, int s, int t) {
		int f = maxFlow(g, cap, s, t);
		BFS.bfsInit(gr);
		BFS.bfsVisit(gr, s);
		return new Cut(BFS.visited, f);
	}

	/*
	 * PRE: E[u] > 0, (u, v) in Er and h[u] = h[v] + 1
	 */
	public void push(int u, int v, int flow) {
		// update capacities
		rcap.add(u, v, -flow);
		rcap.add(v, u, flow);
		// update admissible graph
		if(rcap.get(u, v) == 0) {
			ga.disconnect(u, v);
		}
		// update residual graph
		if(rcap.get(u, v) == 0) gr.disconnect(u, v);
		if(!gr.connected(v, u)) gr.connect(v, u);
		// update excess flow
		E[u] -= flow;
		E[v] += flow;
		// update active list
		if(v != t && E[v] > 0) active.add(v);
		if(E[u] == 0) active.removeFirst();
	}

	/*
	 * PRE: E[u] > 0 and for all (u, v) in Er, h[u] <= h[v]
	 */
	public void relabel(int u) {
		int h = Integer.MAX_VALUE;
		for(int v : gr.outNeighbors(u)) {
			h = Math.min(h, 1 + H[v]);
		}
		H[u] = h;
		// update admissible graph
		for(int v : ga.inNeighbors(u)) {
			ga.disconnect(v, u);
		}
		for(int v : gr.outNeighbors(u)) {
			if(H[u] == H[v] + 1) {
				ga.connect(u, v);
			}
		}
	}

	public void initPreflow() {
		for(int u = 0; u < gr.V(); u++) {
			H[u] = E[u] = 0;
		}
		ga = new SparseGraph(gr.V());
		H[s] = gr.V();
		active = new ActiveList(gr.V());
		makeFrame("initial");
		for(int u : gr.outNeighbors(s)) {
			push(s, u, rcap.get(s, u));
		}
	}

	public void pushRelabel() {
		initPreflow();
		makeFrame("after initial push");
		while(!active.isEmpty()) {
			int u = active.getFirst();
			if(ga.outDeg(u) > 0) {
				int v = ga.neighbor(u);
				push(u, v, Math.min(E[u], rcap.get(u, v)));
				makeFrame("push " + u + " " + v);
			} else {
				relabel(u);
				makeFrame("relabel " + u);
			}
		}
	}
	
	private class ActiveList {
		
		private BitSet isActive;
		private LinkedList<Integer> active;
		
		public ActiveList(int n) {
			isActive = new BitSet();
			active = new LinkedList<>();
		}
		
		public void add(int x) {
			if(!isActive.get(x)) {
				isActive.set(x);
				active.add(x);
			}
		}
		
		public boolean contains(int x) {
			return isActive.get(x);
		}
		
		public int getFirst() {
			return active.getFirst();
		}
		
		public int removeFirst() {
			isActive.clear(active.getFirst());
			return active.removeFirst();
		}
		
		public boolean isEmpty() {
			return active.isEmpty();
		}
		
		public String toString() {
			return active.toString();
		}
		
	}
	
	public String toString() {
		return active.toString();
	}

	// DEBUG
	
	/*
	
1
6
0 1 1 0 1 0
1 0 0 1 0 1
1 0 0 1 0 0
0 1 1 0 0 0
1 0 0 0 0 1
0 1 0 0 1 0

	
	 */
	
	public void debugPrint() {
		System.out.println("E: " + Arrays.toString(E));
		System.out.println("H: " + Arrays.toString(H));
	}
	
	public void makeFrame(String title) {
		if(true) return;
		System.out.println("\\begin{frame}");
		System.out.printf("\\frametitle{%s}\n", title);
		System.out.println("\\begin{tikzpicture}[scale = 0.5]");

		int[] x = new int[gr.V()];
		for(int i = 0; i < gr.V(); i++) {
			x[i] = 3 * i;
		}
		
		for(int i = 0; i < 2 * gr.V(); i++) {
			System.out.printf("\\draw[dashed, gray!50!white](%d, %d) -- (%d, %d);\n", -1, i, x[gr.V() - 1] + 1, i);
		}
		
		for(int i = 0; i < gr.V(); i++) {
			String  color = "white";
			if(active.contains(i)) {
				color = "green!50!white";
			}
			System.out.printf("\\node[draw, fill = %s] (n%d) at (%d, %d) {\\tiny %d};\n", color, i, x[i], H[i], i);
			System.out.printf("\\node[below=0.02 of n%d, gray] (e%d) {\\tiny %d};\n", i, i, E[i]);
		}
		
		for(int i = 0; i < gr.V(); i++) {
			for(int j : gr.outNeighbors(i)) {
				String color = "blue!50!white";
				String bend = "left";
				if(i > j) {
					//bend = "right";
					color = "red!50!white";
				}
				if(ga.connected(i, j)) {
					color = "green!50!white";
				}
				System.out.printf("\\draw[->, thick, opacity = 0.5, %s] (n%d) edge[bend %s] node (e%d%d) {} (n%d);\n", color, i, bend, i, j, j);
				
				System.out.printf("\\draw[%s, fill = white, thick, opacity = 0.5, fill opacity=1] (e%d%d) circle (0.2);\n", color, i, j);
				System.out.printf("\\node[font=\\tiny] at (e%d%d) {%d};\n", i, j, rcap.get(i, j));

			}
		}
		
		System.out.println("\\end{tikzpicture}");
		System.out.println("\\end{frame}");	
	}

}
