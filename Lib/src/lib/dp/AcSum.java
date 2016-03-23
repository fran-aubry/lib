package lib.dp;

public class AcSum {

	public static long[] acsum(int[] a) {
		int n = a.length;
		long[] acs = new long[n];
		acs[0] = a[0];
		for(int i = 1; i < n; i++) {
			acs[i] = a[i] + acs[i - 1];
		}
		return acs;
	}
	
	public static long sum(long[] acs, int i, int j) {
		if(i == 0) return acs[j];
		return acs[j] - acs[i - 1];
	}
	
	/*
	 * Return the accumulated sum matrix of M.
	 */
	public static long[][] acsum(int[][] M) {
		int n = M.length;
		int m = M[0].length;
		long[][] acs = new long[n][m];
		acs[0][0] = M[0][0];
		for(int i = 1; i < n; i++) {
			acs[i][0] = acs[i - 1][0] + M[i][0];
		}
		for(int j = 1; j < m; j++) {
			acs[0][j] = acs[0][j - 1] + M[0][j];
		}
		for(int i = 1; i < n; i++) {
			for(int j = 1; j < n; j++) {
				acs[i][j] = acs[i - 1][j] + acs[i][j - 1] - acs[i - 1][j - 1] + M[i][j];
			}
		}
		return acs;
	}
	
	/*
	 * Compute the sum of a sub-matrix with up left corder: (i1, j1)
	 * and down right corner (i2, j2) given the accumulated sum matrix.
	 */
	public static long sum(long[][] acs, int i1, int j1, int i2, int j2) {
		long S = 0;
		S += acs[i2][j2];
		if(i1 > 0) S -= acs[i1 - 1][j2];
		if(j1 > 0) S -= acs[i2][j1 - 1];
		if(i1 > 0 && j1 > 0) S += acs[i1 - 1][j1 - 1];
		return S;
	}
	
	
}
