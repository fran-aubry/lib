package lib.graphs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;

public class MaxFlow {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		int V = reader.nextInt();
		int E = reader.nextInt();
		Graph g = new TreeGraph(V);
		Measure cap = new HashMeasure();
		for(int i = 0; i < E; i++) {
			int o = reader.nextInt();
			int d = reader.nextInt();
			int c = reader.nextInt();
			g.connect(o, d);
			cap.setWeight(o, d, c);
		}
		System.out.println(maxflow(g, cap, 0, V - 1));
		reader.close();
	}

	static int maxflow(Graph g, Measure cap, int s, int t) {
		int flow = 0;
		int f = 0;
		while((f = multiBFS(g, cap, s, t)) > 0) {
			flow += f;
		}
		return flow;
	}

	static int multiBFS(Graph g, Measure cap, int s, int t) {
		Queue<Integer> Q = new LinkedList<>();
		Q.add(s);
		int[] parent = new int[g.V()];
		Arrays.fill(parent, -1);
		int[] pcap = new int[g.V()];
		Arrays.fill(pcap, Integer.MAX_VALUE);
		int[] len = new int[g.V()];
		Arrays.fill(len, -1);
		LinkedList<Integer> parentT = new LinkedList<>();
		while(!Q.isEmpty()) {
			int c = Q.poll();
			for(int x : g.outNeighbors(c)) {
				if(parent[x] == -1 && cap.getWeight(c, x) > 0) {
					if(x == t) {
						parentT.add(c);
					} else {						
						pcap[x] = Math.min(pcap[c], (int)cap.getWeight(c, x));
						parent[x] = c;
						Q.add(x);
						len[x] = 1 + len[c];
					}
				}
			}
		}
		if(parentT.isEmpty()) return 0;
		int flow = 0;
		for(int x : parentT) {
			// build path
			Path path = new Path();
			int y = t;
			while(x != -1) {
				path.a
			}
			
			// flow to augment on this path
			int pflow = Math.min(pcap[x], (int)cap.getWeight(x, t));
			flow += pflow;
			int y = t;
			while(x != -1) {
				cap.setWeight(x, y, cap.getWeight(x, y) - pflow);
				if(g.connected(y, x)) {
					cap.setWeight(y, x, cap.getWeight(y, x) + pflow);
				} else {
					g.connect(y, x);
					cap.setWeight(y, x, pflow);
				}
				y = x;
				x = parent[x];
			}
		}
		return flow;
	}

}
