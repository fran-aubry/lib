package lib.dp;

import java.util.Arrays;

import lib.util.ArraysExt;

public class SubsetSum {

	/*
	 * Compute the minimum numbers of elements from
	 * S are needed to sum t. If it is impossible it
	 * returns -1
	 */
	public static int minSetSizeRep(int[] S, int t) {
		double[] dp = new double[t + 1];
		Arrays.fill(dp, Double.POSITIVE_INFINITY); // dp[i] = +oo means that it is not possible to sum i
		dp[0] = 0;
		for(int i = 1; i <= t; t++) {
			double min = Double.POSITIVE_INFINITY;
			for(int x : S) {
				int j = i - x;
				min = Math.min(min, dp[j]);
			}
			dp[i] = 1 + min;
		}
		return dp[t] == Double.POSITIVE_INFINITY ? -1 : (int)dp[t];
	}
	
	/*
	 * Compute the minimum numbers of elements from
	 * S are needed to sum any integer up to t. 
	 * Returns an array such that a[i] is the number
	 * of ways we can sum i. It is -1 if it is not
	 * possible to sum i.
	 */
	public static int[] minSetSizeRepAll(int[] S, int t) {
		Arrays.sort(S);
		double[] dp = new double[t + 1];
		Arrays.fill(dp, Double.POSITIVE_INFINITY); // dp[i] = +oo means that it is not possible to sum i
		dp[0] = 0;
		int[] ans = new int[t + 1];
		for(int i = 1; i <= t; i++) {
			double min = Double.POSITIVE_INFINITY;
			for(int x : S) {
				int j = i - x;
				if(j < 0) break;
				min = Math.min(min, dp[j]);
			}
			dp[i] = 1 + min;
			ans[i] = dp[i] == Double.POSITIVE_INFINITY ? -1 : (int)dp[i];
		}
		return ans;
	}
	
	/*
	 * Minimize |sum S1 - sum S2| such that S1 union S2 = S
	 */
	public static int fairSplit(int[] S) {
		int sum = ArraysExt.sum(S);
		int n = S.length;
		boolean[][] dp = new boolean[n + 1][sum + 1];
		dp[0][0] = true;
		for(int i = 1; i <= n; i++) {
			for(int j = 1; j <= sum; j++) {
				dp[i][j] = (j - S[i - 1] >= 0 && dp[i - 1][j - S[i - 1]]) || dp[i - 1][j];
			}
		}
		int min = Integer.MAX_VALUE;
		for(int j = 0; j <= sum; j++) {
			if(dp[n][j]) {
				min = Math.min(min, Math.abs(2 * j - sum));
			}
		}
		return min;
	}

}
