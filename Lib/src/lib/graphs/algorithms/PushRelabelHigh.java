package lib.graphs.algorithms;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;

import lib.graphs.EdgeFunction;
import lib.graphs.Graph;
import lib.graphs.SparseGraph;

public class PushRelabelHigh implements MaxFlow {

	public int[] E;
	public EdgeFunction rcap;
	public Graph gr;
	public Graph ga; // admissible network
	public int s, t;

	public ActiveList active;

	public int maxFlow(Graph g, EdgeFunction cap, int s, int t) {
		this.rcap = cap.copy();
		this.gr = g.deepCopy();
		this.s = s;
		this.t = t;
		E = new int[g.V()];
		//H = new int[g.V()];
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
		if(v != t && E[v] > 0) active.activate(u, v);
	}

	/*
	 * PRE: E[u] > 0 and for all (u, v) in Er, h[u] <= h[v]
	 */
	public void relabel(int u) {
		int h = Integer.MAX_VALUE;
		for(int v : gr.outNeighbors(u)) {
			h = Math.min(h, 1 + active.getH(v));
		}
		active.updateTop(h);
		// update admissible graph
		for(int v : ga.inNeighbors(u)) {
			ga.disconnect(v, u);
		}
		for(int v : gr.outNeighbors(u)) {
			if(active.getH(u) == active.getH(v) + 1) {
				ga.connect(u, v);
			}
		}
	}

	public void initPreflow() {
		ga = new SparseGraph(gr.V());
		//H[s] = gr.V();
		active = new ActiveList(gr.V());
		active.setH(s, gr.V());
		//makeFrame("initial");
		for(int u : gr.outNeighbors(s)) {
			push(s, u, rcap.get(s, u));
		}
		active.next[active.getH(s)] = -1; // TODO fix this hack
	}

	public void pushRelabel() {
		//out = null;
		initPreflow();
		//makeFrame("after initial push");
		while(!active.isEmpty()) {
			int u = active.getHighestActive();
			if(ga.outDeg(u) > 0) {
				int v = ga.neighbor(u);
				push(u, v, Math.min(E[u], rcap.get(u, v)));
				//makeFrame("push " + u + " " + v);
				if(E[u] == 0) {
					active.deactivateTop();
				}
			} else {
				relabel(u);
				//makeFrame("relabel " + u);
			}
		}
		//if(out != null) out.close();

	}

	private class ActiveList {

		private BitSet isActive;
		private LinkedList<Integer>[] buckets;
		private int index;
		private int[] next;
		private int[] H;

		@SuppressWarnings("unchecked")
		public ActiveList(int n) {
			next = new int[2 * n];
			Arrays.fill(next, -1);
			
			H = new int[n];
			
			isActive = new BitSet();
			buckets = new LinkedList[2 * n];
			for(int i = 0; i < 2 * n; i++) {
				buckets[i] = new LinkedList<>();
			}
			index = 0;
		}
		
		public void setH(int u, int h) {
			H[u] = h;
			//buckets[h].add(u);
		}
		
		public int getH(int u) {
			return H[u];
		}
		
		public void activate(int u, int v) {
			// make sure v is not already in the list
			if(isActive.get(v)) return;
			if(buckets[H[v]].isEmpty()) {
				next[H[v]] = next[H[u]];
			}
			buckets[H[v]].add(v);
			isActive.set(v);
			next[H[u]] = H[v];
		}
		
		public void deactivateTop() {
			int u = buckets[index].removeFirst();
			if(buckets[index].isEmpty()) {
				index = next[H[u]];
				next[H[u]] = -1;
			}
			isActive.clear(u);
		}
		
		/*
		 * Set the new height of the highest node to be h
		 */
		public void updateTop(int h) {
			int u = buckets[index].removeFirst();
			if(buckets[H[u]].isEmpty()) {
				next[h] = next[H[u]];
			} else {
				next[h] = H[u];
			}
			H[u] = h;
			buckets[h].add(u);
			index = h;
		}
		
		public int getHighestActive() {
			return buckets[index].getFirst();
		}

		/*
		public void add(int x) {
			if(!isActive.get(x)) {
				isActive.set(x);
				buckets[H[x]].add(x);
				index = Math.max(index, H[x]);
			}
		}
		*/

		public boolean contains(int x) {
			return isActive.get(x);
		}

		/*
		public int getHighest() {
			if(!buckets[index].isEmpty()) {
				return buckets[index].getFirst();
			}
			return buckets[--index].getFirst();
		}
		 */

		/*
		public int removeHighest() {
			int val = -1;
			if(!buckets[index].isEmpty()) {
				val = buckets[index].removeFirst();
			} else {
				val = buckets[--index].removeFirst();
			}
			isActive.clear(val);
			return val;
		}
		*/

		public boolean isEmpty() {
			return isActive.cardinality() == 0;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < buckets.length; i++) {
				sb.append(next[i] == -1 ? "-" : next[i]);
				sb.append(" ");
				sb.append(i);
				sb.append(": ");
				sb.append(buckets[i]);
				if(index == i) {
					sb.append(" <--");
				}
				sb.append("\n");
			}
			sb.append(Arrays.toString(H));
			sb.append("\n");
			sb.append(isActive);
			return sb.toString();
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

/*
	PrintWriter out;
	public void makeFrame(String title) {
		if(true) return;
		try {
			if(out == null) out = new PrintWriter("tikz");

			out.println("\\begin{frame}");
			out.printf("\\frametitle{%s}\n", title);
			out.println("\\begin{tikzpicture}[scale = 0.5]");

			int[] x = new int[gr.V()];
			for(int i = 0; i < gr.V(); i++) {
				x[i] = 3 * i;
			}

			for(int i = 0; i < 2 * gr.V(); i++) {
				out.printf("\\draw[dashed, gray!50!white](%d, %d) -- (%d, %d);\n", -1, i, x[gr.V() - 1] + 1, i);
			}

			for(int i = 0; i < gr.V(); i++) {
				String  color = "white";
				if(active.contains(i)) {
					color = "green!50!white";
				}
				out.printf("\\node[draw, fill = %s] (n%d) at (%d, %d) {\\tiny %d};\n", color, i, x[i], active.getH(i), i);
				out.printf("\\node[below=0.02 of n%d, gray] (e%d) {\\tiny %d};\n", i, i, E[i]);
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
					out.printf("\\draw[->, thick, opacity = 0.5, %s] (n%d) edge[bend %s] node (e%d%d) {} (n%d);\n", color, i, bend, i, j, j);

					out.printf("\\draw[%s, fill = white, thick, opacity = 0.5, fill opacity=1] (e%d%d) circle (0.2);\n", color, i, j);
					out.printf("\\node[font=\\tiny] at (e%d%d) {%d};\n", i, j, rcap.get(i, j));

				}
			}

			out.println("\\end{tikzpicture}");
			out.println("\\end{frame}");
			out.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
}
