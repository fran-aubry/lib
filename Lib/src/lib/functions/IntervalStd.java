package lib.functions;

public class IntervalStd implements RealFunction {

	public double a, b;
	
	public IntervalStd(double a, double b) {
		this.a = a;
		this.b = b;
	}

	/*
	 * Map elements from [a, b] linearly to [0, 1].
	 * Assumes that a < b.
	 */
	public double eval(double x) {
		assert a < b;
		return (x - a) / (b - a);
	}
	
}
