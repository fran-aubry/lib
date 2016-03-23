package lib.string;

import java.util.Arrays;
import java.util.LinkedList;

public class Strings {

	public static LinkedList<Integer> occurences(String P, String T) {
		LinkedList<Integer> occ = new LinkedList<>();
		int i = T.indexOf(P, 0);
		while(i != -1) {
			occ.add(i);
			i = T.indexOf(P, i + 1);
		}
		return occ;
	}
	
	/*
	 *  Count the number of distinct subsequences of a string s
	 *  modulo mod. The empty string is counted as a subsequence.
	 */
	public static int numberOfDistinctSubsequences(String s, int mod) {
		// dp[i] = number of distinct subsequences ending at s[i - 1]
		// dp[0] = 1 (empty sequence)
		int[] dp = new int[s.length() + 1];
		dp[0] = 1;
		// sum[i] = dp[0] + dp[1] + ... + dp[i]
		int[] sum = new int[s.length() + 1];
		sum[0] = 1;
		// last[c] = last position we saw s[i - 1] 
		int[] last = new int[1000];
		Arrays.fill(last, -1);
		for(int i = 1; i <= s.length(); i++) {
			dp[i] = sum[i - 1];
			if(last[s.charAt(i - 1)] != -1) {
				dp[i] = (dp[i] - sum[last[s.charAt(i - 1)] - 1] + mod) % mod;
			}
			sum[i] = (sum[i - 1] + dp[i]) % mod;
			last[s.charAt(i - 1)] = i;
		}
		return sum[s.length()];
	}
	
}
