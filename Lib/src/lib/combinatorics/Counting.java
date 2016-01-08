package lib.combinatorics;

import java.math.BigInteger;

public class Counting {

	/*
	 * Counts the number of permutations of s.
	 * Note that s may have repeated characters.
	 * 
	 * n! / (r1! * r2! * ... * rk!)
	 * 
	 * where k is the number of different characters and
	 * ri is the number of occurences of each character.
	 * 
	 */
	public static BigInteger wordPerm(String s) {
		int[] cnt = new int[128];
		for(char c : s.toCharArray()) {
			cnt[c]++;
		}
		BigInteger[] f = factorials(s.length());
		BigInteger res =  f[s.length()];
		for(int i = 0; i < cnt.length; i++) {
			if(cnt[i] > 0) {
				res = res.divide(f[cnt[i]]);
			}
		}
		return res;
	}
	
	/*
	 * Compute the factorial from 0 to n, inclusive.
	 */
	public static BigInteger[] factorials(int n) {
		BigInteger[] f = new BigInteger[n + 1];
		f[0] = BigInteger.ONE;
		for(int i = 1; i <= n; i++) {
			f[i] = f[i - 1].multiply(big(i));
		}
		return f;
	}
	
	public static BigInteger big(int x) {
		return BigInteger.valueOf(x);
	}
	
}
