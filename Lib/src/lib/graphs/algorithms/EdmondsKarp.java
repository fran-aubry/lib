package lib.graphs.algorithms;

import java.util.LinkedList;
import java.util.Queue;

import lib.graphs.EdgeFunction;
import lib.graphs.Graph;

public class EdmondsKarp implements MaxFlow {
	
	private int flowValue; // value of the maximum flow
	private EdgeFunction flow, capr; // flow function and residual capacity
	private Graph gr; // residual graph
	
	public int maxFlow(Graph g, EdgeFunction cap, int s, int t) {
		flow = new EdgeFunction(g, 0);
		flowValue = 0;
		gr = g.deepCopy();
		capr = cap.copy();
		int pcap;
		while((pcap = augmentBFS(gr, capr, s, t)) != -1) {
			flowValue += pcap;
		}
		return flowValue;
	}

	public Cut minCut(Graph g, EdgeFunction cap, int s, int t) {
		maxFlow(g, cap, s, t);
		BFS.bfsInit(gr);
		BFS.bfsVisit(gr, s);
		return new Cut(BFS.visited, flowValue);
	}

	private int augmentBFS(Graph g, EdgeFunction cap, int s, int t) {
		if(s == t) return -1;
		// initialize bfs
		Queue<Integer> Q = new LinkedList<>();
		Integer[] p = new Integer[g.V()];
		int[] pcap = new int[g.V()];
		pcap[s] = Integer.MAX_VALUE;
		p[s] = -1;
		Q.add(s);
		// compute path
		while(p[t] == null && !Q.isEmpty()) {
			int u = Q.poll(); 
			for(int v : g.outNeighbors(u)) {
				if(p[v] == null) {
					p[v] = u;
					pcap[v] = Math.min(pcap[u], cap.get(u, v));
					Q.add(v);
				}
			}
		}
		if(p[t] == null) return -1;
		// update graph
		int cur = t;
		while(cur != s) {
			int prev = p[cur];
			int c = cap.get(prev, cur);
			flow.add(prev, cur, c);
			flow.add(cur, prev, -c);
			// deal with forward edge
			if(c == pcap[t]) {
				g.disconnect(prev, cur);
			} else {
				cap.add(prev, cur, -pcap[t]);				
			}
			// deal with backward edge
			g.connect(cur, prev);
			cap.add(cur, prev, pcap[t]);
			cur = prev;
		}
		return pcap[t];
	}
}
