package lib.functions;

public class IntervalStdRev implements RealFunction {

	public double a, b;
	
	public IntervalStdRev(double a, double b) {
		this.a = a;
		this.b = b;
	}
	
	/*
	 * Map elements from [a, b] linearly to [0, 1] but
	 * reversing the interval, that is, f(a) = 1 and f(b) = 0.
	 * Assumes that a < b.
	 */
	public double eval(double x) {
		return (x - b) / (a - b);
	}
	
}
