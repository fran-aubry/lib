package lib.dp;

public class MaxSum {

	/*
	 * Return an array [i1, i2, s] such that
	 * a[i1...i2] has sum s and s is maximum.
	 * Break ties by i2 - i1 and then by minimum i1
	 */
	public static int[] maxSum(int[] a) {
		int sum = 0;
		int n = a.length;
		int i1 = -1, i2 = -1, s = -1;
		int j = 0;
		for(int i = 0; i < n; i++) {
			if(sum + a[i] >= 0) {
				sum += a[i];
				if(sum > s || (sum == s && (i - j) > (i2 - i1))) {
					s = sum;
					i1 = j;
					i2 = i;
				}
			} else {
				sum = 0;
				j = i + 1;
			}
		}
		return new int[] {i1, i2, s};
	}

	public static long[] maxSum(int[][] M) {
		long[] res = new long[5];
		res[4] = Integer.MIN_VALUE;
		int n = M.length;
		int m = M[0].length;
		for(int i = 0; i < n; i++) {
			int[] a = new int[m];
			for(int j = i; j < n; j++) {
				for(int k = 0; k < m; k++) {
					a[k] += M[j][k];
				}
				int[] result = maxSum(a);
				if(result[2] > res[4]) {
					res[0] = i; res[1] = result[0];
					res[2] = j; res[3] = result[1];
					res[4] = result[2];
				}
			}
		}
		return res;
	}

}
