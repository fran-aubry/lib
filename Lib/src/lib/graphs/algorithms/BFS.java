package lib.graphs.algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import lib.graphs.Graph;
import lib.util.Arrays2;
import lib.util.Constants;

/**
 * 
 * This class allows to perform a BFS of
 * a graph. It is used to check for
 * connectivity in the instances.
 * 
 * @author f.aubry@uclouvain.be
 *
 */

public class BFS {

	/*
	 * Check if a graph is connected.
	 * O[V + E]
	 */
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
		return Arrays2.and(visited);
	}
	
	/*
	 * Compute the distance (number of edges) from
	 * s to each other node of the graph.
	 * O[V + E]
	 */
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
	
	public static boolean[] visited;
	public static int[] distance;
	public static int[] parent;
	public static int[] connectedComponent;
	public static int V;
	public static int nbConnectedComponents;
	
	public static int[] getDistances() {
		return Arrays.copyOf(distance, distance.length);
	}
	
	public static int[] getParents() {
		return Arrays.copyOf(parent, parent.length);
	}
	
	public static void bfsInit(Graph g) {
		V = g.V();
		visited = new boolean[V];
		distance = new int[V];
		Arrays.fill(distance, Integer.MAX_VALUE);
		parent = new int[V];
		Arrays.fill(parent, -1);
		connectedComponent = new int[V];
		nbConnectedComponents = 0;
	}

	public static void bfs(Graph g) {
		bfsInit(g);
		for(int x : g) {
			if(distance[x] == Integer.MAX_VALUE) {
				bfsVisit(g, x);
				nbConnectedComponents++;
			}
		}
	}
	
	public static void bfsVisit(Graph g, int s) {
		Queue<Integer> Q = new LinkedList<Integer>();
		Q.add(s);
		distance[s] = 0;
		visited[s] = true;
		connectedComponent[s] = nbConnectedComponents;
		while(!Q.isEmpty()) {
			int x = Q.poll();
			for(int y : g.outNeighbors(x)) {
				if(distance[y] == Integer.MAX_VALUE) {
					parent[y] = x;
					visited[y] = true;
					distance[y] = 1 + distance[x];
					connectedComponent[y] = nbConnectedComponents;
					Q.add(y);
				}
			}
		}
	}
	
	public static int diameter(Graph g) {
		int[][] distance = new int[g.V()][];
		for(int x : g) {
			bfsInit(g);
			BFS.bfsVisit(g, x);
			distance[x] = BFS.distance;
		}
		return Arrays2.max(distance);
	}
	
	public static int[] shortestPathThrough(int[][] parent, int[][] distance, int s, int t, int x) {
		int[] path = new int[distance[s][x] + distance[x][t] + 1];
		int cur = x;
		int i = distance[s][x];
		while(cur != -1) {
			path[i--] = cur;
			cur = parent[s][cur];
		}
		i = path.length - 1;
		cur = t;
		while(cur != x) {
			path[i--] = cur;
			cur = parent[x][cur];
		}
		return path;
	}
	
	
	public static int[][] gridBfs(int[][] grid, int wall, int rs, int cs, int re, int ce) {
		Queue<int[]> Q = new LinkedList<>();
		Q.add(new int[] {rs, cs});
		int[][] dist = new int[grid.length][grid[0].length];
		for(int i = 0; i < grid.length; i++) {
			Arrays.fill(dist[i], Integer.MAX_VALUE);
		}
		dist[rs][cs] = 0;
		while(!Q.isEmpty() && dist[re][ce] == Integer.MAX_VALUE) {
			int[] cur = Q.poll();
			for(int[] d : Constants.dir) {
				int i = cur[0] + d[0];
				int j = cur[1] + d[1];
				if(0 <= i && i < grid.length && 0 <= j && j < grid[0].length && grid[i][j] != wall && dist[i][j] == Integer.MAX_VALUE) {
					dist[i][j] = dist[cur[0]][cur[1]] + 1;
					Q.add(new int[] {i, j});
				}
			}
		}
		return dist;
	}

}
