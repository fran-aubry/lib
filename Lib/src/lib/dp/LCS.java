package lib.dp;

public class LCS {
	
	public static int lcs(int[] x, int[] y) {
		int n = x.length;
		int m = y.length;
		// dp[i][j] = lcs(x0...xi, y0...yj)
		int[][] dp = new int[n][m];
		dp[0][0] = x[0] == y[0] ? 1 : 0;
		for(int i = 1; i < n; i++) {
			dp[i][0] = x[i] == y[0] ? 1 : dp[i - 1][0];
		}
		for(int j = 1; j < m; j++) {
			dp[0][j] = x[0] == y[j] ? 1 : dp[0][j - 1];
		}
		for(int i = 1; i < n; i++) {
			for(int j = 1; j < m; j++) {
				if(x[i] == y[j]) {
					dp[i][j] = 1 + dp[i - 1][j - 1];
				} else {
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				}
			}
		}
		return dp[n - 1][m - 1];
	}

}
