package lib.geometry;

import java.util.Comparator;

public class Line {

	// equation Ax + By = C
		
	public double A, B, C;
	
	public Line(Point p1, Point p2) {
		A = p2.y - p1.y;
		B = p1.x - p2.x;
		C = A * p1.x + B * p1.y;
	}
	
	public Line(double x1, double y1, double x2, double y2) {
		A = y2 - y1;
		B = x1 - x2;
		C = A * x1 + B * y1;
	}
	
	public Line(int x1, int y1, int x2, int y2) {
		A = y2 - y1;
		B = x1 - x2;
		C = A * x1 + B * y1;
	}
	
	public Line(double A, double B, double C) {
		this.A = A;
		this.B = B;
		this.C = C;
	}
	
	public boolean intersects(Line other) {
		return !parallel(other);
	}
	
	public boolean parallel(Line other) {
		return Cmp.eq(det(other), 0);
	}
	
	public double det(Line other) {
		return A * other.B - other.A * B;
	}
	
	public boolean contains(Point p) {
		return Cmp.eq(A * p.x + B * p.y, C);
	}
	
	public Point intersection(Line other) {
		if(parallel(other)) return null;
		double d = det(other);
		return new Point((other.B * C - B * other.C) / d, (-other.A * C + A * other.C) / d);
	}
	
	public Line perpendicular(Point p) {
		return new Line(-B, A, -B * p.x + A * p.y);
	}
	
	public double distance(Point p) {
		Point inter = intersection(perpendicular(p));
		return Points.distance(inter, p);
	}
	
	public String toString() {
		return String.format("%.3f x + %.3f y = %.3f", A, B, C);
	}
	
}
