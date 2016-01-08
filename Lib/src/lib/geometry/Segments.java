package lib.geometry;

public class Segments {

	public static boolean intersects(Point p1, Point p2, Point p3, Point p4) {
		double o1 = Points.orient(p1, p2, p3);
		double o2 = Points.orient(p1, p2, p4);
		double o3 = Points.orient(p3, p4, p1);
		double o4 = Points.orient(p3, p4, p2);
		// check first condition of the lemma
		if(Cmp.le(o1 * o2, 0) && Cmp.le(o3 * o4, 0)) return true;
		// check seconds condition of the lemma
		if(Cmp.eq(o1, 0) && Points.inBox(p1, p2, p3)) return true;
		if(Cmp.eq(o2, 0) && Points.inBox(p1, p2, p4)) return true;
		if(Cmp.eq(o3, 0) && Points.inBox(p3, p4, p1)) return true;
		if(Cmp.eq(o4, 0) && Points.inBox(p3, p4, p2)) return true;
		return false;
	}
	
	public static boolean onSegment(Point s1, Point s2, Point p) {
		return Points.collinear(s1, s2, p) && Points.inBox(s1, s2, p);
	}
	
	public static double distance(Point s1, Point s2, Point p) {
		Line sl = new Line(s1, s2);
		Line slp = sl.perpendicular(p);
		Point inter = sl.intersection(slp);
		if(onSegment(s1, s2, inter)) return Points.distance(p, inter);
		return Math.min(Points.distance(p, s1), Points.distance(p, s2));
	}

}
