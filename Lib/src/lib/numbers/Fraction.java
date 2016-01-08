package lib.numbers;

public class Fraction implements Comparable<Fraction> {

	public long a, b;
	
	public Fraction(long a, long b) {
		this.a = a;
		this.b = b;
		reduce();
	}

	public void reduce() {
		long d = Math2.gcd(a, b);
		a /= d;
		b /= d;
	}
	
	public boolean equals(Object other) {
		if(other instanceof Fraction) {
			Fraction o = (Fraction)other;
			return a == o.a && b == o.b;
		}
		return false;
	}

	public int compareTo(Fraction o) {
		long delta = a * o.b - b * o.a;
		if(delta == 0) return 0;
		if(delta > 0) return 1;
		return -1;
	}
	
	public String toString() {
		return String.format("%d/%d", a, b);
	}
	
	public int hashCode() {
		return (int)(a + 31 * b);
	}
	
}
