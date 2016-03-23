package lib.numbers;

import java.math.BigInteger;
import java.util.Arrays;

public class Polynomials {

	public static double eps = 1e-8;
	
	/*
	 * c[0] + c[1] * x + c[2] * x^2 + ... + c[n] x^n
	 */
	public static double evaluate(double[] coefs, double x) {
		double b = 0.0;
		for(int i = coefs.length - 1; i >= 0; i--) {
			b = coefs[i] + b * x;
		}
		return b;
	}
	
	public static BigInteger evaluate(long[] coefs, long x) {
		BigInteger y = BigInteger.ZERO;
		BigInteger X = BigInteger.valueOf(x);
		for(int i = coefs.length - 1; i >= 0; i--) {
			y = BigInteger.valueOf(coefs[i]).add(y.multiply(X));
		}
		return y;
	}
	
	public static BigInteger evaluate(BigInteger[] coefs, BigInteger x) {
		BigInteger y = BigInteger.ZERO;
		for(int i = coefs.length - 1; i >= 0; i--) {
			y = coefs[i].add(y.multiply(x));
		}
		return y;
	}

	public static double[] derivative(double[] coefs) {
		if(coefs.length == 1) return new double[] {0};
		double[] dCoefs = new double[coefs.length - 1];
		for(int i = 1; i < coefs.length; i++) {
			dCoefs[i - 1] = coefs[i] * i;
		}
		return dCoefs;
	}
	
	public static long[] derivative(long[] coefs) {
		if(coefs.length == 1) return new long[] {0};
		long[] dCoefs = new long[coefs.length - 1];
		for(int i = 1; i < coefs.length; i++) {
			dCoefs[i - 1] = coefs[i] * i;
		}
		return dCoefs;
	}

	public static double[] primitive(double[] f) {
		double[] F = new double[f.length + 1];
		for(int i = 0; i < f.length; i++) {
			F[i + 1] = f[i] / (i + 1);
		}
		return F;
	}

	public static double[] add(double[] f, double[] g) {
		int n = Math.max(f.length, g.length);
		double[] h = new double[n];
		for(int i = f.length - 1; i >= 0; i--) {
			h[i + (n - f.length)] += f[i];
		}
		for(int i = g.length - 1; i >= 0; i--) {
			h[i + (n - g.length)] += g[i];
		}
		int i = 0;
		while(Math.abs(h[i]) < eps) {
			i++;
		}
		return Arrays.copyOfRange(h, i, n);
	}

	public static double[] symmetric(double[] f) {
		double[] g = new double[f.length];
		for(int i = 0; i < f.length; i++) {
			g[i] = -f[i];
		}
		return g;
	}

	public static double[] subtract(double[] f, double[] g) {
		return add(f, symmetric(g));
	}

	public static double[] scalarProd(double[] f, double x) {
		double[] g = new double[f.length];
		for(int i = 0; i < f.length; i++) {
			g[i] = f[i] * x;
		}
		return g;
	}

	public static double[] multiplyXpow(double[] f, int e) {
		double[] g = new double[f.length + e];
		for(int i = 0; i < f.length; i++) {
			g[i + e] = f[i];
		}
		return g;
	}

	public static boolean isZero(double[] f) {
		return f.length == 1 && Math.abs(f[0]) < eps;
	}

	public static double[] compose(double[] f, double[] g) {
		int df = f.length - 1;
		double[] c = new double[] {0.0};
		for(int i = 0; i <= df; i++) {
			double[] gc = multiply(g, c);
			gc[gc.length - 1] += f[i];
			c = gc;
		}
		return c;
	}
	
	public static double[] multiply(double[] f, double[] g) {
		if(isZero(f) || isZero(g)) {
			return new double[] {0};
		}
		int df = f.length - 1;
		int dg = g.length - 1;
		double[] c = new double[df + dg + 1];
		for (int i = 0; i <= df; i++) {
			for (int j = 0; j <= dg; j++) {
				c[i + j] += (f[i] * g[j]);
			}
		}
		return c;
	}

	public static double[][] divide(double[] f, double[] g) {
		double[] r = Arrays.copyOf(f, f.length);
		int dr = r.length - 1;
		int dg = g.length - 1;
		int dq = dr - dg;
		double[] q = new double[dq + 1];
		int i = 0;
		while(dr >= dg) {
			double[] t = multiplyXpow(g, dr - dg);
			q[dq - i] = r[dr] / g[dg];
			t = scalarProd(t, -q[dq - i]);
			r = add(r, t);
			dr = r.length - 1;
			i++;
		}
		return new double[][] {q, r};
	}
	
	public static double[][] syntheticDivision(double[] f, double[] g) {
		int n = f.length;
		int m = g.length;
		if(m > n) return new double[][] {new double[1], f};
		double[][] t = new double[m + 1][n + 1];
		for(int j = 1; j <= n; j++) {
			t[0][j] = f[n - j];
		}
		for(int i = 1; i <= m; i++) {
			t[i][0] = -g[i - 1];
		}
		t[m][0] *= -1;
		int dq = n - m;
		for(int j = 1; j <= n; j++) {
			double sum = 0;
			for(int i = 0; i < t.length - 1; i++) {
				sum += t[i][j];
			}
			t[m][j] = sum;
			if(j <= dq + 1) {
				t[m][j] /= t[m][0];
				int k = m + j - 1;
				for(int i = 1; i < m; i++, k--) {
					t[i][k] = t[i][0] * t[m][j];
				}
			}
		}
		double[] q = new double[dq + 1];
		for(int j = 1; j <= 1 + dq; j++) {
			q[q.length - j] = t[m][j];
		}
		double[] r = new double[m - 1];
		for(int j = 0; j < r.length; j++) {
			r[j] = t[m][n - j];
		}
		if(r.length == 0) r = new double[1];
		return new double[][] {q, r};
	}

	public static double[] gcd(double[] f, double[] g) {
		double[][] d = syntheticDivision(f, g);
		double[] r = d[1];
		if(isZero(r)) {
			return g;
		} else if(r.length == 1) {
			return new double[] {1};
		} else {
			return gcd(g, r);
		}
	}

}
