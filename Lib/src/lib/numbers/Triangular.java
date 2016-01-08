package lib.numbers;

public class Triangular {

	public static long getKth(long k) {
		return k * (k + 1) / 2;
	}
	
	/*
	 * Compute maximum k such that T(k) <= n
	 */
	public static long floor(long n) {
		return (long)Math.floor((-1 + Math.sqrt(1 + 8 * n)) / 2.0);
	}
	
}
