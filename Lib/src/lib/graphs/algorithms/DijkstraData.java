package lib.graphs.algorithms;

import java.util.List;

import lib.graphs.Graph;
import lib.graphs.Path;

public class DijkstraData {
	
	public int source, V; // source vertex and number of vertices.
	public int[] distance; // distance[x] = distance from source to x
	public int[] length; // length[x] = number of edges in the shortest path from source to x
	public Graph g, dag; // shortest path dag rooted at source
	public List<Integer>[] parent; // parent[x] = all precessesors of x in the shortest path dag
	
	public DijkstraData(Graph g, int source, int[] distance, int[] length, List<Integer>[] parent) {
		this.V = distance.length;
		this.g = g;
		this.source = source;
		this.distance = distance;
		this.length = length;
		this.parent = parent;
		buildSPDag();
	}
	
	/*
	 * Build the shortest path dag from the parents.
	 * O(V + E)
	 */
	private void buildSPDag() {
		dag = g.create(V);
		for(int v = 0; v < V; v++) {
			for(int u : parent[v]) {
				dag.connect(u, v);
			}
		}
	}
	
	/*
	 * Builds a shortest path from s to t. In case there are
	 * several we get the first parent at each step.
	 * O[size of the path]
	 */
	public Path getPath(int t) {
		// check if a path exists
		if(t != source && parent[t].isEmpty()) return null;
		// build path from parents
		int[] v = new int[length[t] + 1];
		for(int i = length[t], cur = t; i > 0; i--, cur = parent[cur].get(0)) {
			v[i] = cur;
		}
		v[0] = source;
		return new Path(v);
	}
	

}
