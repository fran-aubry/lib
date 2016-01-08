package lib.geometry;

public class Triangle {

	public Point a, b, c;
	
	public Triangle(Point a, Point b, Point c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public Point[] toPol() {
		return new Point[] {a, b, c};
	}
	
	public String toString() {
		return String.format("(%s; %s; %s)", a.toString(), b.toString(), c.toString());
	}
	
}
