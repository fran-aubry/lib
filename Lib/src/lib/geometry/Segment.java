package lib.geometry;

public class Segment {
	
	public Point p, q;
	
	public Segment(Point p, Point q) {
		this.p = p;
		this.q = q;
	}

	public boolean on(Point r) {
		return Points.collinear(p, q, r) && Points.inBox(p, q, r);
	}
	
}
