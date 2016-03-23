package lib.graphs.algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import lib.data.IntPair;
import lib.data.LinkedSet;
import lib.graphs.Graph;

public class ArticulationPointsAndBridges {

	public int[] low, num, parent;
	public int counter, root, rootChildren;
	
	public LinkedSet articulation;
	public LinkedList<IntPair> bridges;
	
	public Graph g;
	
	public ArticulationPointsAndBridges(Graph g) {
		this.g = g;
		counter = 0;
		num = new int[g.V()];
		Arrays.fill(num, -1);
		low = new int[g.V()];
		Arrays.fill(low, 0);
		parent = new int[g.V()];
		Arrays.fill(parent, -1);
		articulation = new LinkedSet();
		bridges = new LinkedList<>();
		for(int x : g) {
			if(num[x] == -1) {
				root = x;
				rootChildren = 0;
				dfs(x);
				if(rootChildren > 1) {
					articulation.add(x);
				}
			}
		}
		
	}
	
	private void dfs(int u) {
		low[u] = num[u] = counter++;
		for(int v : g.outNeighbors(u)) {
			if(num[v] == -1) {
				parent[v] = u;
				if(u == root) rootChildren++;
				dfs(v);
				if(low[v] >= num[u] && u != root) {
					articulation.add(u);
				}
				if(low[v] > num[u]) {
					bridges.add(new IntPair(Math.min(u, v), Math.max(u, v)));
				}
				low[u] = Math.min(low[u], low[v]);
			}  
		}
	}
	
}
