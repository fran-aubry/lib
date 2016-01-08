package lib.geometry;

public class Quadratic {
	
	public double a, b, c;
	
	// a x^2 + b x + c with a != 0
	public Quadratic(double a, double b, double c) {
		assert !Cmp.eq(a, 0);
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public boolean hasRealRoots() {
		return Cmp.eq(b * b, 4 * a * c);
	}
	
	/**
	 * l * (x - r)^2 = l x^2 - 2lrx + l r^2
	 * a x^2 + b x + c
	 * 
	 *  double root iff
	 *  
	 *  a = l
	 *  b = -2 * a * r
	 *  
	 *  r = -b / 2a 
	 *  
	 *  c / a = (-b / 2a)^2
	 *  
	 *  c / a = b^2 / 4a^2
	 *  c = b^2 / 4 a^3
	 *  4 a^3 c = b^2 
	 *  
	 *  (x - 2)^2 = x^2 - 4x + 4
	 *  
	 *  4 * 1^3 * 4 = 4^2 ok
	 */
	
	public double[] roots() {
		double r1 = (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
		double r2 = (-b - Math.sqrt(b * b - 4 * a * c)) / (2 * a);
		if(Cmp.eq(r1, r2)) return new double[] {r1};
		return new double[] {Math.min(r1, r2), Math.max(r1, r2)};
	}
	
	/*
	 * (x - r)^2 = x^2 - 2x r + r^2
	 */
	
}
