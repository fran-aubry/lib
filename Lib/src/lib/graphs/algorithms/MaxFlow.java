package lib.graphs.algorithms;

import lib.graphs.EdgeFunction;
import lib.graphs.Graph;

public interface MaxFlow {

	public int maxFlow(Graph g, EdgeFunction cap, int s, int t);
	
	public Cut minCut(Graph g, EdgeFunction cap, int s, int t);
	
}
