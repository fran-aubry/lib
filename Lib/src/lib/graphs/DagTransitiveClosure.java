package lib.graphs;

import java.util.BitSet;

public class DagTransitiveClosure {

	private BitSet[] reachable;
	
	public DagTransitiveClosure(Graph g) {
		reachable = new BitSet[g.V()];
		for(int i = 0; i < g.V(); i++) reachable[i] = new BitSet();
		TopologicalSort tsort = new TopologicalSort(g);
		int[] order = tsort.getOrder();
		for(int i = g.V() - 1; i >= 0; i--) {
			int x = order[i];
			for(int y : g.outNeighbors(x)) {
				reachable[x].or(reachable[y]);
			}
			reachable[x].set(x);
		}
	}
	
	public boolean hasPath(int x, int y) {
		return reachable[x].get(y);
	}
	
}
