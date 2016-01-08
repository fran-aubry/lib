package lib.graphs;

import java.util.Arrays;

public class PushRelabel {

	public int[] E, H;
	public EdgeFunction c;
	public Graph g;

	public PushRelabel(Graph g, EdgeFunction c, int s, int t) {
		this.c = c;
		this.g = g;
		E = new int[g.V()];
		H = new int[g.V()];
		initPreflow(s);
		pushRelabel(s, t);
	}

	public void push(Edge e, int flow) {
		c.add(e, -flow);
		c.add(e.reverse(), flow);
		E[e.orig] -= flow;
		E[e.dest] += flow;
	}

	public void relabel(int u) {
		int h = Integer.MAX_VALUE;
		for(Edge e : g.outEdges(u)) {
			if(c.val(e) > 0) {
				h = Math.min(h, 1 + H[e.dest]);				
			}
		}
		H[u] = h;
	}

	public void initPreflow(int s) {
		for(int u = 0; u < g.V(); u++) {
			H[u] = E[u] = 0;
			for(Edge e : g.outEdges(u)) {
				Edge er = e.reverse();
				g.connect(er);
				if(!c.defined(er)) {
					c.set(er, 0);
				}
			}
		}
		H[s] = g.V();
		for(Edge e : g.outEdges(s)) {
			push(e, c.val(e));
		}
	}

	public void pushRelabel(int s, int t) {
		boolean found = true;
		while(found) {
			found = false;
			for(int u = 0; u < g.V(); u++) {
				if(u == t) continue;
				if(E[u] > 0) {
					// look for push operation
					boolean relabel = true;
					for(Edge e : g.outEdges(u)) {
						if(H[u] == H[e.dest] + 1 && c.val(e) > 0) {
							push(e, Math.min(E[u], c.val(e)));
							relabel = false;
							found = true;
						}
					}
					if(relabel) {
						relabel(u);
						found = true;
					}
				}
			}
		}
	}

	// DEBUG
	
	public void debugPrint() {
		System.out.println("E: " + Arrays.toString(E));
		System.out.println("H: " + Arrays.toString(H));
	}
	
}
