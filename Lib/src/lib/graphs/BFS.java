package lib.graphs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import lib.util.ArraysExt;

public class BFS {

	public static boolean connected(Graph g) {
		Queue<Integer> Q = new LinkedList<Integer>();
		Q.add(0);
		boolean[] visited = new boolean[g.V()];
		visited[0] = true;
		while(!Q.isEmpty()) {
			int cur = Q.poll();
			for(int x : g.outNeighbors(cur)) {
				if(!visited[x]) {
					visited[x] = true;
					Q.add(x);
				}
			}
		}
		return ArraysExt.and(visited);
	}
	
	public static int[] distances(Graph g, int s) {
		Queue<Integer> Q = new LinkedList<Integer>();
		Q.add(s);
		int[] d = new int[g.V()];
		Arrays.fill(d, Integer.MAX_VALUE);
		d[s] = 0;
		while(!Q.isEmpty()) {
			int cur = Q.poll();
			for(int x : g.outNeighbors(cur)) {
				if(d[x] == Integer.MAX_VALUE) {
					d[x] = 1 + d[cur];
					Q.add(x);
				}
			}
		}
		return d;
	}
	
}
