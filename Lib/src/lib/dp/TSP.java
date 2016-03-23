package lib.dp;

public class TSP {

	public static double tsp(double[][] d) {
		double[][] dp = tspMatrix(d);
		int n = d.length;
		// find the best solution (we need to finish the tour)
		double best = Double.POSITIVE_INFINITY;
		for(int i = 1; i < n; i++) {
			best = Math.min(best, dp[(1 << n) - 1][i] + d[i][0]);				
		}
		return best;
	}
	
	/*
	 * Return a matrix M of size 2^n x n such that:
	 * M[s][i] = cost of the minimum tour that visits
	 * the vertices in S and end at vertex i.
	 * 
	 * n = len(d)
	 */
	public static double[][] tspMatrix(double[][] d) {
		int n = d.length;
		double[][] dp = new double[(1 << n)][n];
		// base case
		for(int s = 1; s < (1 << n); s += 2) {
			dp[s][0] = Double.POSITIVE_INFINITY;
		}
		dp[1][0] = 0;
		// iterate over all sets that include node 0 (odd numbers)
		for(int S = 1; S < (1 << n); S += 2) {
			// iterate over last vertex candidates
			for(int i = 1; i < n; i++) {
				if(((S >> i) & 1) == 1) {
					double min = Double.POSITIVE_INFINITY;
					// iterate over previous to last
					for(int j = 0; j < n; j++) {
						// compute Si = S \ i
						int Si = S ^ (1 << i);
						if(((Si >> j) & 1) == 1) {
							min = Math.min(min, dp[Si][j] + d[j][i]);
						}
					}
					dp[S][i] = min;
				} else{
					dp[S][i] = Double.POSITIVE_INFINITY;
				}
			}
		}
		return dp;
	}
	
}
