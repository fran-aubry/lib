package lib.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Points {

	public static double orientVal(Point p, Point q, Point r) {
		return q.x * r.y - r.x * q.y - p.x * (r.y - q.y) + p.y * (r.x - q.x);
	}

	public static int orient(Point p, Point q, Point r) {
		return sgn(orientVal(p, q, r));
	}

	public static boolean eq(Point p, Point q) {
		return Cmp.eq(p.x, q.x) && Cmp.eq(p.y, q.y);
	}

	public static boolean ccw(Point p, Point q, Point r) {
		return Cmp.ge(orient(p, q, r), 0);
	}

	public static boolean cw(Point p, Point q, Point r) {
		return Cmp.le(orient(p, q, r), 0);
	}

	public static boolean collinear(Point p, Point q, Point r) {
		return Cmp.eq(orient(p, q, r), 0);
	}

	public static boolean intersects(Point p1, Point p2, Point p3, Point p4) {
		double o1 = orient(p1, p2, p3);
		double o2 = orient(p1, p2, p4);
		double o3 = orient(p3, p4, p1);
		double o4 = orient(p3, p4, p2);
		// check first condition of the lemma
		if(o1 * o2 < 0 && o3 * o4 < 0) return true;
		// check seconds condition of the lemma
		if(o1 == 0 && inBox(p1, p2, p3)) return true;
		if(o2 == 0 && inBox(p1, p2, p4)) return true;
		if(o3 == 0 && inBox(p3, p4, p1)) return true;
		if(o4 == 0 && inBox(p3, p4, p2)) return true;
		return false;
	}


	public static int sgn(double x) {
		if(Cmp.le(x, 0)) return -1;
		if(Cmp.ge(x, 0)) return 1;
		return 0;
	}

	public static double distance(Point p, Point q) {
		return Math.sqrt(sqDistance(p, q));
	}

	public static double sqDistance(Point p, Point q) {
		double dx = p.x - q.x;
		double dy = p.y - q.y;
		return dx * dx + dy * dy;
	}

	public static boolean onSegment(Point p1, Point p2, Point p) {
		return collinear(p1, p2, p) && inBox(p1, p2, p);
	}

	public static boolean inBox(Point p1, Point p2, Point p) {
		return Cmp.leq(Math.min(p1.x, p2.x), p.x) && Cmp.leq(p.x, Math.max(p1.x, p2.x)) &&
				Cmp.leq(Math.min(p1.y, p2.y), p.y) && Cmp.leq(p.y, Math.max(p1.y, p2.y));
	}

	public static double sqDistance(int x1, int y1, int x2, int y2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		return dx * dx + dy * dy;
	}

	public static double distance(int x1, int y1, int x2, int y2) {
		return Math.sqrt(sqDistance(x1, y1, x2, y2));
	}

	public static Point mid(Point p, Point q) {
		return new Point((p.x + q.x) / 2, (p.y + q.y) / 2);
	}

	public static boolean inTriangle(Point p, Point p1, Point p2, Point p3) {
		double o1 = orient(p, p1, p2);
		double o2 = orient(p, p2, p3);
		double o3 = orient(p, p3, p1);
		return sgn(o1) == sgn(o2) && sgn(o2) == sgn(o3);
	}


	/** Compute the convex hull with lower and upper chain algorithm. O(n log(n)) */
	public static Point[] convexHull(Point[] points) {
		// sort points by increasing x coordinates
		Arrays.sort(points, new Xcmp());
		// build upper chain
		Point[] upChain = buildChain(points, 1);
		// build lower chain
		Point[] loChain = buildChain(points, -1);
		Point[] hull = new Point[upChain.length + loChain.length - 2];
		int i;
		// build convex hull from upper and lower chain
		for(i = 0; i < upChain.length; i++) {
			hull[i] = upChain[i];
		}
		for(int j = loChain.length - 2; j >= 1; j--) {
			hull[i] = loChain[j]; i++;
		}
		return hull;
	}

	/** 
	 * Build a convex hull chain. If sgn is 1 then upper chain, if -1 then lower chain. O(n) 
	 * @PRE: The points must be sorted in increasing order of x coordinates.
	 */
	public static Point[] buildChain(Point[] points, int sgn) {
		Point[] S = new Point[points.length];
		int k = 0;
		S[k++] = points[0]; // push points[0]
		S[k++] = points[1]; // push points[1]
		// build chain
		for(int i = 2; i < points.length; i++) {
			//double orient = orient(S[k - 2], S[k - 1], points[i]);
			while(k >= 2 && sgn * orient(S[k - 2], S[k - 1], points[i]) >= 0) {
				S[k - 1] = null; // pop
				k--;
			}
			S[k++] = points[i]; // push points[i]
		}
		return Arrays.copyOf(S, k);
	}



	/** Add (dx, dy) to each point of P. O(n) */
	public static Point[] translation(Point[] P, double dx, double dy) {
		Point[] Q = new Point[P.length];
		for(int i = 0; i < P.length; i++) Q[i] = new Point(P[i].x + dx, P[i].y + dy);
		return Q;
	}
	
	/** Add (dx, dy) to each point of P. O(n) */
	public static Point[] translation(ArrayList<Point> P, double dx, double dy) {
		Point[] Q = new Point[P.size()];
		for(int i = 0; i < P.size(); i++) Q[i] = new Point(P.get(i).x + dx, P.get(i).y + dy);
		return Q;
	}

	/** Comparator to sort points by non-decreasing x. In case of ties sort by non-decreasing y. */
	public static class Xcmp implements Comparator<Point> {

		public int compare(Point o1, Point o2) {
			int dx = sgn(o1.x - o2.x);
			if(dx == 0) return sgn(o1.y - o2.y);
			return dx;
		}

	}

	/** Comparator to sort points by non-decreasing y. In case of ties sort by non-decreasing x. */
	public static class Ycmp implements Comparator<Point> {

		public int compare(Point o1, Point o2) {
			int dy = sgn(o1.y - o2.y);
			if(dy == 0) return sgn(o1.x - o2.x);
			return dy;
		}

	}

	public static int quadrant(Point o, Point p) {
		if(Cmp.geq(p.x, o.x) && Cmp.geq(p.y, o.y)) return 0;
		if(Cmp.leq(p.x, o.x) && Cmp.geq(p.y, o.y)) return 1;
		if(Cmp.leq(p.x, o.x) && Cmp.leq(p.y, o.y)) return 2;
		return 3;
	}

	/** Sort points by angle arround ref. Break ties by distance to ref. O(n log(n)) */
	public static void sortPolar(Point[] P, Point ref) {
		@SuppressWarnings("unchecked")
		LinkedList<Point>[] Q = new LinkedList[4];
		for(int i = 0; i < 4; i++) {
			Q[i] = new LinkedList<>();
		}
		for(Point p : P) {
			Q[quadrant(ref, p)].add(p);
		}
		PolarCmp cmp = new PolarCmp(ref);
		int k = 0;
		for(int i = 0; i < 4; i++) {
			Collections.sort(Q[i], cmp);
			for(Point p : Q[i]) {
				P[k++] = p;
			}
		}
	}

	/** Comparator class for polar sort. Points must be on same quadrant */
	public static class PolarCmp implements Comparator<Point> {

		private Point ref;

		public PolarCmp(Point orig) {
			this.ref = orig;
		}

		public int compare(Point p, Point q) {
			int o = orient(ref, p, q);
			if(o == 0) {
				if(Points.sqDistance(ref, p) > Points.sqDistance(ref, q)) {
					return 1;					
				}
				return -1;
			}
			return -o;
		}

	}



}
