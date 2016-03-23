package lib.graphs.algorithms;

import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.Queue;

import lib.data.Pair;
import lib.graphs.EdgeFunction;
import lib.graphs.SparseGraph;

public class GomoryHu {
	
	public static int[][] topCoder(SparseGraph g, EdgeFunction cap, MaxFlow flow) {
		int[] parent = new int[g.V()]; //initialized to 0
		int[][] answer = new int[g.V()][g.V()]; //initialize this one to infinity
		for(int i = 0; i < g.V(); i++) {
			Arrays.fill(answer[i], Integer.MAX_VALUE);
		}
		for(int i = 1; i < g.V(); i++) {
			//Compute the minimum cut between i and parent[i].
			Cut c = flow.minCut(g, cap, i, parent[i]);
			//Let the i-side of the min cut be S, and the value of the min-cut be F
			BitSet S = c.X().get(i) ? c.X() : c.Y();
			for(int j = i + 1; j < g.V(); j++) {
				if(S.get(j) && parent[j] == parent[i]) {
					parent[j] = i;
				}
			}
			answer[i][parent[i]] = answer[parent[i]][i] = c.value();
			for(int j = 0; j < i; j++) {
				answer[i][j] = answer[j][i] = Math.min(c.value(), answer[parent[i]][j]);				
			}
		}
		for(int i = 0; i < g.V(); i++) {
			answer[i][i] = 0;
		}
		return answer;
	}

	public static Pair<SparseGraph, EdgeFunction> buildTree(SparseGraph g, EdgeFunction cap, MaxFlow flow) {
		SparseGraph gomoryHuTree = new SparseGraph(g.V());
		EdgeFunction minCut = new EdgeFunction();
		// compute an arbitrary minimum cut
		Cut c = flow.minCut(g, cap, 0, g.V() - 1);
		// build the first edge of the tree
		gomoryHuTree.connectBoth(0, g.V() - 1);
		minCut.setUnd(0, g.V() - 1, c.value());
		// initialize the queue
		Queue<BitSet> Q = new LinkedList<>();
		if(c.X().cardinality() > 1) Q.add(c.X());
		if(c.Y().cardinality() > 1) Q.add(c.Y());
		while(!Q.isEmpty()) {
			BitSet W = Q.poll();
			int s = W.nextSetBit(0);
			int t = W.nextSetBit(s + 1);
			c = flow.minCut(g, cap, s, t);
			// add new edge to the tree
			gomoryHuTree.connectBoth(s, t);
			minCut.setUnd(s, t, c.value());
			// add new sets to the queue
			c.X().and(W);				
			c.Y().and(W);
			if(c.X().cardinality() > 1) Q.add(c.X());
			if(c.Y().cardinality() > 1) Q.add(c.Y());
		}
		return new Pair<>(gomoryHuTree, minCut);
	}
	
	public static int[][] allPairsMaxFlow(SparseGraph g, EdgeFunction cap, MaxFlow flow) {
		// compute the Gomory-Hu tree
		Pair<SparseGraph, EdgeFunction> gf = buildTree(g, cap, flow);
		SparseGraph gomoryHuTree = gf.x;
		EdgeFunction minCut = gf.y;
		// initialize the flow matrix
		int[][] maxFlow = new int[g.V()][];
		// compute the flow from each vertex to the others
		for(int x : g) {
			// perform BFS on the Gomory-Hu tree from x
			int[] f = new int[g.V()];
			Arrays.fill(f, Integer.MAX_VALUE);
			Queue<Integer> Q = new LinkedList<>();
			Q.add(x);
			while(!Q.isEmpty()) {
				int y = Q.poll();
				for(int z : gomoryHuTree.outNeighbors(y)) {
					if(z != x && f[z] == Integer.MAX_VALUE) {
						f[z] = Math.min(f[y], minCut.get(y, z));
						Q.add(z);
					}
				}
			}
			// we use the convention that mf(x, x) = 0
			f[x] = 0;
			maxFlow[x] = f;
		}
		return maxFlow;
	}
	
	public static int[][] allPairsMaxFlow(int[][] A, MaxFlow flow) {
		SparseGraph g = new SparseGraph(A.length);
		EdgeFunction cap = new EdgeFunction();
		for(int i = 0; i < g.V(); i++) {
			for(int j = 0; j < g.V(); j++) {
				if(A[i][j] > 0) {
					g.connect(i, j);
					cap.set(i, j, A[i][j]);
				}
			}
		}
		return allPairsMaxFlow(g, cap, flow);
	}
	
	
}