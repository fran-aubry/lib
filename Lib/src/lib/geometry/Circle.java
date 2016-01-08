package lib.geometry;

public class Circle {

	public Point center;
	public double radius;
	
	public Circle(Point center, double radius) {
		this.center = center;
		this.radius = radius;
	}
	
	public Circle(double x, double y, double radius) {
		center = new Point(x, y);
		this.radius = radius;
	}
	
	public String toString() {
		return String.format("center: %s, radius: %.3f", center.toString(), radius);
	}
	
	public boolean inside(Point p) {
		return Cmp.le(Points.sqDistance(p, center), rr());
	}
	
	public boolean insideStrict(Point p) {
		return Cmp.leq(Points.sqDistance(p, center), rr());
	}
	
	
	public boolean onBoundary(Point p) {
		return Cmp.eq(Points.sqDistance(p, center), rr());
	}
	
	
	public double x() {
		return center.x;
	}
	
	public double y() {
		return center.y;
	}
	
	public double r() {
		return radius;
	}
	
	public double xx() {
		return center.x * center.x;
	}
	
	public double yy() {
		return center.y * center.y;
	}
	
	public double rr() {
		return radius * radius;
	}
	
	public double area() {
		return rr() * Math.PI;
	}
	
}
