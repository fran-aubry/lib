package lib.numbers;

import java.math.BigInteger;

public class Combinatorics {

	/*
	 * Return C(n, k) mod 2
	 */
	public static int nChooseKmod2(int n, int k) {
		return (k & (n - k)) == 0 ? 1 : 0;
	}

	public static long C2(int n, int k) {
		double r = 1;
		k = Math.min(k, n - k);
		for(int i = 1; i <= k; i++) {
			r /= i;
		}
		for(int i = n; i >= n - k + 1; i--) {
			r *= i;
		}
		return Math.round(r);
	}

	public static int nbDigits(int n, int k) {
		double log = 0;
		if(k > n - k) {
			for(int i = k + 1; i <= n; i++ ) {
				log += Math.log10 (i);
				log -= Math.log10(n - i + 1);
			}
		} else {
			for(int i = n - k + 1; i <= n; i++) {
				log += Math.log10(i);
				log -= Math.log10(n - i + 1);				
			}
		}
		return (int)Math.floor(log) + 1;
	}

	public static BigInteger C(int n, int k) {
		BigInteger Cnk = BigInteger.ONE;
		for(int i = k + 1; i <= n; i++) {
			Cnk = Cnk.multiply(big(i));
		}
		for(int i = 2; i <= n - k; i++) {
			Cnk = Cnk.divide(big(i));
		}
		return Cnk;
	}

	/*
	 * C(n, k + 1) = C(n, k) * (n - k) / (k + 1)
	 */
	public static BigInteger[] pascalLine(int n) {
		BigInteger[] c = new BigInteger[n + 1];
		c[0] = BigInteger.ONE;
		for(int k = 0; k < n; k++) {
			c[k + 1] = c[k].multiply(big(n - k)).divide(big(k + 1));
		}
		return c;
	}

	public static BigInteger catalan(int n) {
		return C(2 * n, n).divide(big(n + 1));
	}

	private static BigInteger big(long x) {
		return BigInteger.valueOf(x);
	}

	public static BigInteger factorial(int n) {
		BigInteger f = BigInteger.ONE;
		for(int i = 2; i <= n; i++) {
			f = f.multiply(big(i));
		}
		return f;
	}

	/*
	 * For n = 4 there are 11 bracketings:
	 * 
	 * xxxx
	 * (xx)xx 
	 * x(xx)x
	 * xx(xx)
	 * (xxx)x 
	 * x(xxx)
	 * ((xx)x)x
	 * (x(xx))x
	 * (xx)(xx)
	 * x((xx)x)
	 * x(x(xx))
	 * 
	 * b[n] = (3(2n - 3)b[n-1] - (n-3)b[n-2]) / n
	 * 
	 * Bracketings built up of binary operations 
	 * only are called binary bracketings.
	 * 
	 * From the above list the following are binary:
	 * 
	 * ((xx)x)x
	 * (x(xx))x
	 * (xx)(xx)
	 * x((xx)x)
	 * x(x(xx))
	 * 
	 * The number of binary bracketigs is
	 * catalan(n - 1)
	 */
	public static BigInteger[] allBracketings(int n) {
		BigInteger[] b = new BigInteger[n + 1];
		b[1] = BigInteger.ONE;
		if(n == 1) return b;
		b[2] = BigInteger.ONE;
		for(int i = 3; i <= n; i++) {
			BigInteger c1 = big(3 * (2 * i - 3));
			BigInteger c2 = big(i - 3);
			b[i] = (c1.multiply(b[i - 1])).subtract(c2.multiply(b[i - 2]));
			b[i] = b[i].divide(big(i));
		}
		return b;
	}



}
