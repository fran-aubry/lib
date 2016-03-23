package lib.dp;

import java.math.BigInteger;

import lib.util.Arrays2;

public class OptimalBST {

	public static int cnt;
	
	/*
	 * n = number of words
	 * 
	 * write the words w[1] < w[1] < ... < w[n]
	 * 
	 * p[i] = probability of looking up word i
	 * q[0] = probability of looking up a word < w[1]
	 * q[n] = probability of looking up a word > w[n]
	 * q[i] = probability of looking up a work between w[i] and w[i + 1]
	 */
	public static void optimalBST(double[] p, double[] q, int n) {
		
		cnt = 0;
		
		double[][] e = new double[n + 2][n + 1];
		double[][] w = new double[n + 2][n + 1];
		int[][] root = new int[n + 1][n + 1];
		for(int i = 1; i <= n + 1; i++) {
			e[i][i - 1] = q[i - 1];
			w[i][i - 1] = q[i - 1];
		}
		for(int l = 1; l <= n; l++) {
			for(int i = 1; i <= n - l + 1; i++) {
				int j = i + l - 1;
				e[i][j] = Double.POSITIVE_INFINITY;
				w[i][j] = w[i][j - 1] + p[j] + q[j];
				int rl = i < j ? root[i][j - 1] : i;
				int rr = i < j ? root[i + 1][j] : j;
				for(int r = rl; r <= rr; r++) {
				//for(int r = i; r <= j; r++) {
					double t = e[i][r - 1] + e[r + 1][j] + w[i][j];
					if(t < e[i][j]) {
						e[i][j] = t;
						root[i][j] = r;
					}
					cnt++;
				}
			}
		}
		String s = Arrays2.matrixToStr(e);
		s = Arrays2.matrixToStr(root);
		//System.out.println(s);
	}
	
	/*
	 * dp[i][i - 1] = 0 (empty tree)
	 * dp[i][i] = 0 (all paths have length 0)
	 * 
	 * if i < j then
	 * dp[i][j] = min[i <= k <= j] dp[i][k - 1] + dp[k + 1][j] + p[i] + ... + p[j] - p[k]
	 */
	public static void optimalBSTSlow(double[] p) {
		int n = p.length;
		// build accumulated sum of p
		double[] P = new double[n];
		P[0] = p[0];
		for(int i = 1; i < n; i++) {
			P[i] = p[i] + P[i - 1];
		}
		// initialize DP states
		double[][] dp = new double[n][n];
		int[][] root = new int[n][n];
		for(int i = 0; i < n; i++) {
			root[i][i] = i;
		}
		for(int l = 1; l < n; l++) { // loop on interval size
			for(int i = 0; i + l < n; i++) { // loop on first index
				int j = i + l;
				dp[i][j] = Double.POSITIVE_INFINITY;
				// solve interval [i, j]
				for(int r = i; r <= j; r++) { 
					double psum = i > 0 ? P[j] - P[i - 1] : P[j];
					double cost = psum - p[r];
					if(r - 1 >= 0) cost += dp[i][r - 1];
					if(r + 1 < n) cost += dp[r + 1][j];
					if(cost < dp[i][j]) {
						dp[i][j] = cost;
						root[i][j] = r;
					}
				}
			}
		}
		System.out.println(dp[0][n - 1]);
	}
	
	public static double optimalBST(double[] p) {
		int n = p.length;
		// build accumulated sum of p
		double[] P = new double[n];
		P[0] = p[0];
		for(int i = 1; i < n; i++) {
			P[i] = p[i] + P[i - 1];
		}
		// initialize DP states
		double[][] dp = new double[n][n];
		int[][] root = new int[n][n];
		for(int i = 0; i < n; i++) {
			root[i][i] = i;
		}
		for(int l = 1; l < n; l++) { // loop on interval size
			for(int i = 0; i + l < n; i++) { // loop on first index
				int j = i + l;
				dp[i][j] = Double.POSITIVE_INFINITY;
				// solve interval [i, j]
				for(int r = root[i][j - 1]; r <= root[i + 1][j]; r++) { 
					double psum = i > 0 ? P[j] - P[i - 1] : P[j];
					double cost = psum - p[r];
					if(r - 1 >= 0) cost += dp[i][r - 1];
					if(r + 1 < n) cost += dp[r + 1][j];
					if(cost < dp[i][j]) {
						dp[i][j] = cost;
						root[i][j] = r;
					}
				}
			}
		}
		return dp[0][n - 1];
	}
	
	public static double optimalBST(int[] p) {
		int n = p.length;
		// build accumulated sum of p
		int[] P = new int[n];
		P[0] = p[0];
		for(int i = 1; i < n; i++) {
			P[i] = p[i] + P[i - 1];
		}
		// initialize DP states
		int[][] dp = new int[n][n];
		int[][] root = new int[n][n];
		for(int i = 0; i < n; i++) {
			root[i][i] = i;
		}
		for(int l = 1; l < n; l++) { // loop on interval size
			for(int i = 0; i + l < n; i++) { // loop on first index
				int j = i + l;
				dp[i][j] = Integer.MAX_VALUE;
				// solve interval [i, j]
				for(int r = root[i][j - 1]; r <= root[i + 1][j]; r++) { 
					int psum = i > 0 ? P[j] - P[i - 1] : P[j];
					int cost = psum - p[r];
					if(r - 1 >= 0) cost += dp[i][r - 1];
					if(r + 1 < n) cost += dp[r + 1][j];
					if(cost < dp[i][j]) {
						dp[i][j] = cost;
						root[i][j] = r;
					}
				}
			}
		}
		return dp[0][n - 1];
	}
	
	
	public static int val;
	
	public static BigInteger optimalBSTCount(int[] freq) {
		val = 0;
		int n = freq.length;
		// build accumulated sum of p
		int[] P = new int[n];
		P[0] = freq[0];
		for(int i = 1; i < n; i++) {
			P[i] = freq[i] + P[i - 1];
		}
		// initialize DP states
		int[][] cost = new int[n][n]; // optimal cost
		BigInteger[][] count = new BigInteger[n][n]; // tree count
		// initialize tree count
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(i < j) {
					count[i][j] = BigInteger.ZERO;
				} else {
					count[i][j] = BigInteger.ONE;									
				}
			}
		}
		// initialize roots
		int[][] root = new int[n][n];
		for(int i = 0; i < n; i++) {
			root[i][i] = i;
		}
		// dp iteration
		for(int l = 1; l < n; l++) { // loop on interval size
			for(int i = 0; i + l < n; i++) { // loop on first index
				int j = i + l;
				cost[i][j] = Integer.MAX_VALUE;
				// solve interval [i, j]
				for(int r = root[i][j - 1]; r <= root[i + 1][j]; r++) { 
					int psum = i > 0 ? P[j] - P[i - 1] : P[j];
					int c = psum - freq[r];
					if(r - 1 >= 0) c += cost[i][r - 1];
					if(r + 1 < n) c += cost[r + 1][j];
					if(c < cost[i][j]) {
						cost[i][j] = c;
						root[i][j] = r;
					}
				}
				// we have the optimal cost, now we count
				for(int r = root[i][j - 1]; r <= root[i + 1][j]; r++) {
					int psum = i > 0 ? P[j] - P[i - 1] : P[j];
					int c = psum - freq[r];
					if(r - 1 >= 0) c += cost[i][r - 1];
					if(r + 1 < n) c += cost[r + 1][j];
					if(c == cost[i][j]) {
						BigInteger left = r - 1 >= 0 ? count[i][r - 1] : BigInteger.ONE;
						BigInteger right = r + 1 < n ? count[r + 1][j] : BigInteger.ONE;
						count[i][j] = count[i][j].add(left.multiply(right));
					}
				}
			}
		}
		val = cost[0][n - 1];
		return count[0][n - 1];
	}
	
}
