package lib.numbers;

import java.math.BigInteger;

public class BigFraction implements Comparable<BigFraction> {

	public static final BigFraction ZERO = new BigFraction(BigInteger.ZERO);
	public static final BigFraction ONE = new BigFraction(BigInteger.ONE);
	
	public BigInteger a, b;
	
	public BigFraction(BigInteger a) {
		this.a = a;
		b = BigInteger.ONE;
	}
	
	public BigFraction(BigInteger a, BigInteger b) {
		this.a = a;
		this.b = b;
		if(a.compareTo(BigInteger.ZERO) < 0 && b.compareTo(BigInteger.ZERO) < 0) {
			a = a.abs();
			b = b.abs();
		} else if(b.compareTo(BigInteger.ZERO) < 0) {
			b = b.abs();
			a = a.negate();
		}
		reduce();
	}
	
	public boolean isZero() {
		return a.compareTo(BigInteger.ZERO) == 0;
	}
	
	public BigFraction add(BigFraction o) {
		return new BigFraction(a.multiply(o.b).add(b.multiply(o.a)), b.multiply(o.b));
	}
	
	public BigFraction subtract(BigFraction o) {
		return new BigFraction(a.multiply(o.b).subtract(b.multiply(o.a)), b.multiply(o.b));
	}
	
	public BigFraction multiply(BigFraction o) {
		return new BigFraction(a.multiply(o.a), b.multiply(o.b));
	}
	
	public BigFraction divide(BigFraction o) {
		if(o.isZero()) throw new ArithmeticException("/ by zero");
		return new BigFraction(a.multiply(o.b), b.multiply(o.a));
	}
	

	public void reduce() {
		BigInteger d = Math2.gcd(a, b);
		a = a.divide(d);
		b = b.divide(d);
	}
	
	public boolean equals(Object other) {
		if(other instanceof BigFraction) {
			BigFraction o = (BigFraction)other;
			return a == o.a && b == o.b;
		}
		return false;
	}

	public int compareTo(BigFraction o) {
		return a.multiply(o.b).compareTo(b.multiply(o.a));
	}
	
	public String toString() {
		if(b.compareTo(BigInteger.ONE) == 0) {
			return a.toString();
		}
		return String.format("%d/%d", a, b);
	}
	
	public int hashCode() {
		return a.add(BigInteger.valueOf(31).multiply(b)).intValue();
	}
	
}
