package lib.numbers;

import java.math.BigInteger;

public class Fibonacci {

	public static BigInteger[] allFib(int n, int fib0, int fib1) {
		BigInteger[] fib = new BigInteger[n + 1];
		fib[0] = BigInteger.valueOf(fib0);
		fib[1] = BigInteger.valueOf(fib1);
		for(int i = 2; i <= n; i++) {
			fib[i] = fib[i - 1].add(fib[i - 2]);
		}
		return fib;
	}
	
	public static String decimalToFibinary(BigInteger n, BigInteger[] fib) {
		StringBuilder sb = new StringBuilder();
		int i = findLargestFibLeq(n, fib);
		while(i >= 0) {
			if(fib[i].compareTo(n) <= 0) {
				sb.append('1');
				n = n.subtract(fib[i]);
			} else {
				sb.append('0');
			}
			i--;
		}
		return sb.toString();
	}
	
	public static int findLargestFibLeq(BigInteger t, BigInteger[] fib) {
		int lb = 0;
		int ub = fib.length - 1;
		while(ub - lb > 1) {
			int mid = (lb + ub) / 2;
			if(fib[mid].compareTo(t) > 0) {
				ub = mid - 1;
			} else {
				lb = mid;
			}
		}
		if(fib[ub].compareTo(t) > 0) return lb;
		return ub;
	}
	
	public static BigInteger fibinaryToDecimal(String n , BigInteger[] fib) {
		BigInteger dec = BigInteger.ZERO;
		for(int i = 0; i < n.length(); i++) {
			if(n.charAt(i) == '1') {
				dec = dec.add(fib[n.length() - i - 1]);
			}
		}
		return dec;
	}
	
}
