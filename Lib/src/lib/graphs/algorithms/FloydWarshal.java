package lib.graphs.algorithms;

import java.util.LinkedList;

public class FloydWarshal {

	public static double[][] apsp(double[][] g) {
		int n = g.length;
		double[][] d = new double[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				d[i][j] = g[i][j];
			}
		}
		for(int k = 0; k < n; k++) {
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
				}
			}
		}
		return d;
	}
	
	public static double[][] maxProd(double[][] g) {
		int n = g.length;
		double[][] d = new double[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				d[i][j] = g[i][j];
			}
		}
		for(int k = 0; k < n; k++) {
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					d[i][j] = Math.max(d[i][j], d[i][k] * d[k][j]);
				}
			}
		}
		return d;
	}
	
	public static boolean[][] transitiveClosure(boolean[][] g) {
		int n = g.length;
		boolean[][] tc= new boolean[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				tc[i][j] = g[i][j];
			}
		}
		for(int k = 0; k < n; k++) {
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					tc[i][j] |= tc[i][k] & tc[k][j];
				}
			}
		}
		return tc;
	}
	
	/*
	 * Given a graph g with n vertices find the smallest cycle of length at most n
	 * that as cost at least lb.
	 */
	public static LinkedList<Integer> arbitrage(double[][] g, double lb) {
		int n = g.length;
		int[][][] parent = new int[n][n][n + 1];
		double[][][] dp = new double[n][n][n + 1];
		/*
		 * dp[i][j][m] = maximum product of a path from i to j
		 *               that uses at most m edges
		 *               
		 * dp[i][j][1] = g[i][j]
		 * 
		 * dp[i][j][m] = max_k dp[i][k][m - 1] * dp[k][j][1]
		 */
		for(int i = 0; i < n; i++) {
			dp[i][i][0] = 1;
		}
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				dp[i][j][1] = g[i][j];
				parent[i][j][1] = i;
			}
		}
		for(int m = 2; m <= n; m++) {
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					for(int k = 0; k < n; k++) {
						double val = dp[i][k][m - 1] * dp[k][j][1];
						if(dp[i][j][m] < val) {
							dp[i][j][m] = val;
							parent[i][j][m] = k;
						}
					}
				}
			}
			for(int i = 0; i < n; i++) {
				if(dp[i][i][m] > lb) {
					LinkedList<Integer> path = new LinkedList<Integer>();
					// build path
					int cur = i;
					while(m > 0) {
						path.addFirst(cur + 1);
						cur = parent[i][cur][m];
						m--;
					}
					path.addFirst(cur + 1);
					return path;
				}
			}
		}
		return null;
	}
	
	public static void buildPath(int u, int v, int[][] next) {
		System.out.println(next[u][v]);
		if(next[u][v] == v) {
		
		} else {
			int k = next[u][v];
			buildPath(u, next[u][k], next);
			buildPath(next[u][k], v, next);
		}
		
	}

	
}
