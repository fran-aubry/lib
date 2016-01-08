package lib.graphs;

import lib.graphs.Graph;

public class TopologicalSort {

	// order[i] = node at position i in the order
	// position[x] = position on node x (inverse of order)
	public int[] order, position;	
	private int index;
	
	public TopologicalSort(Graph g) {
		toposort(g);
	}

	public int[] getOrder() {
		return order;
	}
	
	private void toposort(Graph g) {
		order = new int[g.V()];
		index = g.V() - 1;
		boolean[] visited = new boolean[g.V()];
		for(int v = 0; v < g.V(); v++) {
			if(!visited[v]) {
				dfsVisit(g, v, visited);
			}
		}
		// compute positions
		position = new int[g.V()];
		for(int i = 0; i < g.V(); i++) {
			position[order[i]] = i;
		}
	}
	
	private void dfsVisit(Graph g, int v, boolean[] visited) {
		visited[v] = true;
		for(int u : g.outNeighbors(v)) {
			if(!visited[u]) {
				dfsVisit(g, u, visited);
			}
		}
		order[index--] = v;
	}
	
}
