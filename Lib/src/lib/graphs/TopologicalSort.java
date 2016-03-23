package lib.graphs;


/**
 * Class to compute a topological sort of a directed acyclic graph.
 * 
 * @author f.aubry@uclouvain.Be
 */
public class TopologicalSort {

	/*
	 * Compute a topological sort of a directed acyclic graph.
	 * 
	 * order[i] = node at position i in the order
	 * position[x] = position on node x (inverse of order)
	 * 
	 * O[V + E]
	 */
	public int[] order, position;
	
	private int index; // index of the next element of the order
	
	public TopologicalSort(Graph g) {
		toposort(g);
	}

	/*
	 * Get the order.
	 */
	public int[] getOrder() {
		return order;
	}
	
	/*
	 * Get the positions.
	 */
	public int[] position() {
		return position;
	}
	
	/*
	 * Compute the topological order of g.
	 * 
	 * O[V + E]
	 */
	private void toposort(Graph g) {
		order = new int[g.V()];
		index = g.V() - 1;
		boolean[] visited = new boolean[g.V()];
		// perform a dfs from each unvisited v
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
	
	/*
	 * DFS visit algorithm.
	 */
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
