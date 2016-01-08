package lib.geometry;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Circles {

	public static boolean intersects(Circle c1, Circle c2) {
		double centerSqDist = Points.sqDistance(c1.center, c2.center);
		return Cmp.leq(centerSqDist, (c1.radius + c2.radius) * (c1.radius + c2.radius));
	}

	public static boolean intersects(Circle c, Line l) {
		double centerDist = l.distance(c.center);
		return Cmp.leq(centerDist, c.radius);
	}

	
	public static LinkedList<Point> intersect(Circle C, Line l) {
		if(!intersects(C, l)) return new LinkedList<Point>();
		double a, b, c;
		double yy = C.center.y * C.center.y;
		double rr = C.radius * C.radius;
		LinkedList<Point> inter = new LinkedList<>();
		if(Cmp.eq(l.B, 0)) {
			// line equation of the form Ax = C with A != 0, so x = C / A
			double xterm = (l.C / l.A - C.center.x);
			a = 1;
			b = -2 * C.center.y;
			c = yy - rr + xterm * xterm; 
			Quadratic Q = new Quadratic(a, b, c);
			for(double y : Q.roots()) {
				inter.add(new Point(l.C / l.A, y));
			}
		} else {
			// line equation of the form Ax + By = C with B != 0, so y = (C - Ax) / B
			double rab = l.A / l.B; 
			double rcb = l.C / l.B;
			double xx = C.xx();
			a = 1 + rab * rab;
			b = -2 * C.center.x + 2 * C.center.y * rab;
			c = xx + yy - rr +  rcb * rcb - 2 * rcb * rab - 2 * C.center.y * rcb;
			Quadratic Q = new Quadratic(a, b, c);
			for(double x : Q.roots()) {
				inter.add(new Point(x, (l.C - l.A * x) / l.B));
			}
		}
		return inter;
	}
	
	public static Line getLineTroughtIntersectionPoints(Circle C1, Circle C2) {
		if(!intersects(C1, C2)) return null;
		double A = 2 * (C2.x() - C1.x());
		double B = 2 * (C2.y() - C1.y());
		double C = C1.rr() - C2.rr() + C2.xx() + C2.yy() - C1.xx() - C1.yy();
		return new Line(A, B, C);
	}
	
	public static List<Point> intersect(Circle C1, Circle C2) {
		Line l = getLineTroughtIntersectionPoints(C1, C2);
		if(l == null) return new LinkedList<Point>();
		return intersect(C1, l);
	}
	
	public static List<Point> intersect(Circle[] C) {
		List<Point> inter = intersect(C[0], C[1]);
		for(int i = 2; i < C.length; i++) {
			Iterator<Point> I = inter.iterator();
			while(I.hasNext()) {
				Point p = I.next();
				if(!onCircle(C[i], p)) {
					I.remove();
				}
			}
		}
		return inter;
	}
	
	public static boolean onCircle(Circle C, Point p) {
		return Cmp.eq(Points.sqDistance(C.center, p), C.rr());
	}

}
