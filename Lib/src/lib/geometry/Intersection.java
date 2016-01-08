package lib.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class Intersection {

	/** 
	 * compute the intersection between segment p-q and circle C 
	 * http://mathworld.wolfram.com/Circle-LineIntersection.html
	 **/
	public static LinkedList<Point> intersect(Point p, Point q, Circle C) {
		double dx = q.x - p.x;
		double dy = q.y - p.y;
		double d2 = Points.sqDistance(p, q);
		double D = p.x * q.y - q.x * p.y;
		double discriminant = C.rr() * d2 - D * D;
		// discriminant < 0 -> no solution, = 0 -> tangent, > 0 -> two solutions
		LinkedList<Point> res = new LinkedList<>();
		if(Cmp.eq(discriminant, 0)) {
			// tangent
			Point s = new Point(D * dy / d2, -D * dx / d2);
			if(Points.onSegment(p, q, s)) res.add(s);
		} else {
			// two solutions
			double x1 = (D * dy + sgn2(dy) * dx * Math.sqrt(discriminant)) / d2;
			double x2 = (D * dy - sgn2(dy) * dx * Math.sqrt(discriminant)) / d2;
			double y1 = (-D * dx + Math.abs(dy) * Math.sqrt(discriminant)) / d2;
			double y2 = (-D * dx - Math.abs(dy) * Math.sqrt(discriminant)) / d2;
			Point s = new Point(x1, y1);
			if(Points.onSegment(p, q, s)) res.add(s);
			Point r = new Point(x2, y2);
			if(Points.onSegment(p, q, r)) res.add(r);
		}
		// return the points from closest to p to furthest
		Collections.sort(res, new DistCmp(p));
		return res;
	}
	
	/** compute the proper (no end-points) intersection between segment p-q and circle C */
	public static LinkedList<Point> properIntersect(Point p, Point q, Circle C) {
		LinkedList<Point> inter = intersect(p, q, C);
		Iterator<Point> it = inter.iterator();
		while(it.hasNext()) {
			Point r = it.next();
			if(Points.eq(p, r) || Points.eq(q, r)) {
				it.remove();
			}
		}
		return inter;
	}

	/**
	 * Compares points by their distance to a reference point r
	 */
	public static class DistCmp implements Comparator<Point> {

		// reference point for comparison
		private Point r;

		public DistCmp(Point r) {
			this.r = r;
		}

		public int compare(Point o1, Point o2) {
			double d1 = Points.sqDistance(r, o1);
			double d2 = Points.sqDistance(r, o2);
			return Cmp.cmp(d1, d2);
		}

	}

	private static int sgn2(double x) {
		if(Cmp.le(x, 0)) return -1;
		return 1;
	}

	/**
	 * Observations:
	 * - If the current point is inside the circle then
	 *   we can have at most one intersection
	 */
	public static ArrayList<Point> boundary2(Point[] pol, Circle C) {
		ArrayList<Point> boundary = new ArrayList<>();
		boolean inside;
		// build boundary		
		for(int i = 0; i < pol.length; i++) {
			inside = C.insideStrict(pol[i]);
			int j = (i + 1) % pol.length;
			if(C.inside(pol[i])) {
				boundary.add(pol[i]);
				pol[i].id = 0;
			}
			LinkedList<Point> inter = intersect(pol[i], pol[j], C);
			if(inter == null) continue;
			if(inter.size() == 1) {
			    inter.get(0).id = inside ? 2 : 1;
				boundary.add(inter.get(0));
			} else if(inter.size() == 2) {
				inter.get(0).id = inside ? 2 : 1;
				inter.get(1).id = inside ? 1 : 2;
				boundary.add(inter.get(0));
				boundary.add(inter.get(1));
			}
		}
		return boundary;
	}

	public static ArrayList<Point> boundary(Point[] pol, Circle C) {
		ArrayList<Point> boundary = new ArrayList<>();
		for(int i = 0; i < pol.length; i++) {
			if(C.insideStrict(pol[i])) {
				// the point is strictly inside the circle so it belongs to the boundary
				// if it is on boundary then we will find it in the intersection anyway
				pol[i].id = -1;
				boundary.add(pol[i]);
			}
			int j = (i + 1) % pol.length;
			// compute the intersection between edge pol[i]pol[j] and C
			LinkedList<Point> inter = intersect(pol[i], pol[j], C);
			
			for(Point p : inter) {
				p.id = i;
				boundary.add(p);
			}
		}
		return boundary;
	}
	
	/**
	 * Compute the area of intersection between a convex polygon
	 * and a circle C.
	 */
	public static double interArea(Point[] pol, Circle C) {
		// build boundary
		ArrayList<Point> boundary = boundary(pol, C);
		if(boundary.size() == 0) {
			/*
			 *  either the circle is fully inside the polygon,
			 *  in which case the center of the cycle is also inside
			 *  or the intersection is empty
			 */
			if(Polygons.contains(pol, C.center)) {
				return C.area();
			}
			return 0;
		}
		// compute area with Riemann-Green theorem
		double A = 0;
		for(int i = 0; i < boundary.size(); i++) {
			int j = (i + 1) % boundary.size();
			Point p = boundary.get(i);
			Point q = boundary.get(j);
			double integral = 0;
			if(p.id >= 0 && q.id >= 0 && p.id != q.id) {
				// integrate on circle
				double ap = angle(p.x - C.center.x, p.y - C.center.y);
				double aq = angle(q.x - C.center.x, q.y - C.center.y);
				if(Cmp.le(aq, ap)) {
					aq += 2 * Math.PI;
				}
				double AP = ap * 360 / (2 * Math.PI);
				double AQ = aq * 360 / (2 * Math.PI);
				
				System.out.println(AP);
				System.out.println(AQ);
				
				double start = ap + Math.sin(ap) * Math.cos(ap);
				double end = aq + Math.sin(aq) * Math.cos(aq);
				integral = C.rr() * (end - start) / 2.0;
			} else {
				// integrate on segment
			}
			integral = (q.y - p.y) * (q.x - p.x) / 2.0;
			A += integral;
		}
		return A;
	}


	public static double interArea3(Point[] pol, Circle C) {
		// build boundary
		ArrayList<Point> boundary = boundary(pol, C);
		if(boundary.size() == 0) {
			// the circle is fully inside the polygon
			return 0;
		}
		// compute area with Riemann-Green theorem
		double A = 0;
		for(int i = 0; i < boundary.size(); i++) {
			int j = (i + 1) % boundary.size();
			Point p = boundary.get(i);
			Point q = boundary.get(j);
			double integral = 0;
			if(p.id == 2 && q.id == 1) {
				// integrate on circle
				double ap = angle(p.x - C.center.x, p.y - C.center.y);
				double aq = angle(q.x - C.center.x, q.y - C.center.y);
				if(Cmp.le(aq, ap)) {
					aq += 2 * Math.PI;
				}
				double start = ap + Math.sin(ap) * Math.cos(ap);
				double end = aq + Math.sin(aq) * Math.cos(aq);

				double AP = ap * 360 / (2 * Math.PI);
				double AQ = aq * 360 / (2 * Math.PI);
				integral = C.rr() * (end - start) / 2.0;
			} else {
				// integrate on segment
				integral = (q.y - p.y) * (q.x + p.x) / 2.0;
			}
			System.out.println(integral);
			A += integral;
		}
		return A;
	}

	public static double interArea2(Point[] pol, Circle C) {
		// build boundary
		ArrayList<Point> boundary = boundary(pol, C);
		if(boundary.size() == 0) {
			// the circle is fully inside the polygon
			return C.area();
		}
		double A = Polygon.area(boundary);
		// compute arcs areas
		Point O = new Point(0, 0);
		for(int i = 0; i < boundary.size(); i++) {
			int j = (i + 1) % boundary.size();
			Point p = boundary.get(i);
			Point q = boundary.get(j);
			if(p.id == 1 && q.id == 1) {
				double ap = angle(p.x - C.center.x, p.y - C.center.y);
				double aq = angle(q.x - C.center.x, q.y - C.center.y);
				if(Cmp.le(aq, ap)) {
					aq += 2 * Math.PI;
				}
				double a = aq - ap;
				A += a * C.rr() / 2.0;
				A -= Polygon.area(new Point[] {O, p, q});
			}
		}
		return A;
	}

	private static double angle(double x, double y) {
		double alpha = Math.atan2(y, x);
		if(alpha < 0) return -alpha + 2 * (Math.PI + alpha);
		return alpha;
	}

}
