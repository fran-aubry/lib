package lib.numbers;

import java.math.BigInteger;

public class Math2 {

	public static long gcd(long a, long b) {
		if(a < 0) return gcd(-a, b);
		if(b < 0) return gcd(a, -b);
		if(b == 0) return a;
		return gcd(b, a % b);
	}
	
	public static BigInteger gcd(BigInteger a, BigInteger b) {
		if(a.compareTo(BigInteger.ZERO) < 0) return gcd(a.negate(), b);
		if(b.compareTo(BigInteger.ZERO) < 0) return gcd(a, b.negate());
		if(b.equals(BigInteger.ZERO)) return a;
		return gcd(b, a.mod(b));
	}
	
	public static BigInteger lcm(BigInteger a, BigInteger b) {
		return a.multiply(b.divide(gcd(a, b)));
	}
	
	public static long lcm(long a,long b) {
		return (long)a * (b / gcd(a, b));
	}
	
	public static long gcd(long[] a) {
		if(a.length == 1) return a[0];
		long d = gcd(a[0], a[1]);
		for(int i = 2; i < a.length; i++) {
			d = gcd(d, a[i]);
		}
		return d;
	}
	
	public static long lcm(int[] a) {
		if(a.length == 1) return a[0];
		long m = lcm(a[0], a[1]);
		for(int i = 2; i < a.length; i++) {
			m = lcm(m, a[i]);
		}
		return m;
	}
	
	public static int pow(int a, int e) {
		if(e == 0) return 1;
	    if(e == 1) return a;
	    if(e == 2) return a * a;
	    if(e % 2 == 0){
	    	return pow(pow(a, e / 2), 2);
	    } else {
	        return a * pow(pow(a, (e - 1) / 2), 2);
	    }
	}
	
	public static int powMod(int a, int e, int mod) {
		if(e == 0) return 1;
	    if(e == 1) return a % mod;
	    if(e == 2) return ((a % mod) * (a % mod)) % mod;
	    if(e % 2 == 0){
	    	return powMod(powMod(a, e / 2, mod), 2, mod);
	    } else {
	        return ((a % mod) * powMod(powMod(a, (e - 1) / 2, mod), 2, mod) % mod);
	    }
	}
	
	public static int modPow(int a, int e, int mod) {
		return BigInteger.valueOf(a).modPow(BigInteger.valueOf(e), BigInteger.valueOf(mod)).intValue();
	}
	
	public static long modPow(long a, long e, long mod) {
		return BigInteger.valueOf(a).modPow(BigInteger.valueOf(e), BigInteger.valueOf(mod)).longValue();
	}
	
	public static long powMod(long a, int e, int mod) {
		if(e == 0) return 1;
	    if(e == 1) return a % mod;
	    if(e == 2) return ((a % mod) * (a % mod)) % mod;
	    if(e % 2 == 0){
	    	return powMod(powMod(a, e / 2, mod), 2, mod);
	    } else {
	        return ((a % mod) * powMod(powMod(a, (e - 1) / 2, mod), 2, mod) % mod);
	    }
	}
	
	public static BigInteger[][] pow(BigInteger[][] a, int e) {
	    if(e == 1) return a;
	    if(e == 2) return multiply(a, a);
	    if(e % 2 == 0){
	    	return pow(pow(a, e / 2), 2);
	    } else {
	        return multiply(a, pow(pow(a, (e - 1) / 2), 2));
	    }
	}
	
	public static int[][] powMod(int[][] a, int e, int mod) {
	    if(e == 1) return a;
	    if(e == 2) return multiplyMod(a, a, mod);
	    if(e % 2 == 0){
	    	return powMod(powMod(a, e / 2, mod), 2, mod);
	    } else {
	        return multiplyMod(a, powMod(powMod(a, (e - 1) / 2, mod), 2, mod), mod);
	    }
	}
	
	public static long[][] powMod(long[][] a, int e, int mod) {
	    if(e == 1) return a;
	    if(e == 2) return multiplyMod(a, a, mod);
	    if(e % 2 == 0){
	    	return powMod(powMod(a, e / 2, mod), 2, mod);
	    } else {
	        return multiplyMod(a, powMod(powMod(a, (e - 1) / 2, mod), 2, mod), mod);
	    }
	}
	
	
	public static BigInteger[][] multiply(BigInteger[][] a, BigInteger[][] b) {
		int n = a.length;
		int m = b.length;
		int r = b[0].length;
		BigInteger[][] c = new BigInteger[n][r];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < r; j++) {
				c[i][j] = BigInteger.ZERO;
				for(int k = 0; k < m; k++) {
					c[i][j] = c[i][j].add(a[i][k].multiply(b[k][j]));
				}
			}
		}
		return c;
	}
	
	public static double[][] multiply(double[][] a, double[][] b) {
		int n = a.length;
		int m = b.length;
		int r = b[0].length;
		double[][] c = new double[n][r];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < r; j++) {
				c[i][j] = 0;
				for(int k = 0; k < m; k++) {
					c[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		return c;
	}
	
	public static int[][] multiplyMod(int[][] a, int[][] b, int mod) {
		int n = a.length;
		int m = b.length;
		int r = b[0].length;
		int[][] c = new int[n][r];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < r; j++) {
				for(int k = 0; k < m; k++) {
					c[i][j] += a[i][k] * b[k][j];
					c[i][j] %= mod;
				}
			}
		}
		return c;
	}
	
	public static long[][] multiplyMod(long[][] a, long[][] b, int mod) {
		int n = a.length;
		int m = b.length;
		int r = b[0].length;
		long[][] c = new long[n][r];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < r; j++) {
				for(int k = 0; k < m; k++) {
					c[i][j] += a[i][k] * b[k][j];
					c[i][j] %= mod;
				}
			}
		}
		return c;
	}

		
}
