package lib.geometry;

import java.util.ArrayList;
import java.util.LinkedList;

public class Polygons {

	/*
	 * check if vertex i forms an ear: triangle (i-1, i, i+1) inside pol
	 * ccw is true iff polygon is in counter-clockwise order
	 */
	private static boolean ear(Point[] pol, int i) {
		int n = pol.length;
		int j = (i - 1 + n) % n;
		int k = (i + 1 + n) % n;
		// if ccw then points must also be ccw
		if(Points.cw(pol[j], pol[i], pol[k])) return false;
		for(int m = 0; m < n; m++) {
			if(m != i && m != j && m !=k && Points.inTriangle(pol[m], pol[j], pol[i], pol[k])) {
				return false;
			}
		}
		return true;
	}

	/** Triangulate a convex polygon. O(n) */
	public static LinkedList<Triangle> triangulateConvex(Point[] pol) {
		LinkedList<Triangle> T = new LinkedList<>();
		for(int i = 1; i < pol.length - 1; i++) {
			T.add(new Triangle(pol[0], pol[i], pol[i + 1]));
		}
		return T;
	}

	/** Check if a polygon is convex. O(n) */
	public static boolean convex(Point[] pol) {
		int n = pol.length;
		int orient1 = 0;
		// find a vertex with non-zero orientation
		for(int i = 0; i < n; i++) {
			int prev = (i - 1 + n) % n;
			int next = (i + 1) % n;
			orient1 = Points.orient(pol[prev], pol[i], pol[next]);
			if(orient1 != 0) break;
		}
		// check if all vertices have either zero orientation or orient1
		for(int i = 0; i < n; i++) {
			int prev = (i - 1 + n) % n;
			int next = (i + 1) % n;
			int orient2 = Points.orient(pol[prev], pol[i], pol[next]);
			if(orient2 != 0 && orient2 != orient1) return false;		
		}
		return true;
	}

	public static LinkedList<Triangle> triangulate(Point[] pol) {
		LinkedList<Triangle> T = new LinkedList<>();
		while(pol.length > 2) {
			int n = pol.length;
			for(int i = 0; i < n; i++) {
				if(ear(pol, i)) {
					int prev = (i - 1 + n) % n;
					int next = (i + 1 + n) % n;
					T.add(new Triangle(pol[prev], pol[i], pol[next]));
					Point[] pol2 = new Point[n - 1];
					for(int j = 0, k = 0; j < n; j++) {
						if(j != i) {
							pol2[k++] = pol[j];
						}
					}
					pol = pol2;
					break;
				}
			}
		}

		return T;
	}

	public static double area(Point[] pol) {
		int n = pol.length;
		double A = 0;
		for(int i = 0; i < n; i++) {
			int j = (i + 1) % n;
			A += (pol[j].x + pol[i].x) * (pol[j].y - pol[i].y);
		}
		return Math.abs(A) / 2.0;
	}

	public static double area(ArrayList<Point> pol) {
		int n = pol.size();
		double A = 0;
		for(int i = 0; i < n; i++) {
			int j = (i + 1) % n;
			A += (pol.get(j).x + pol.get(i).x) * (pol.get(j).y - pol.get(i).y);
		}
		return Math.abs(A) / 2.0;
	}

	// assumes p is not on P
	public static double winding(Point[] P, Point p) {
		//make a translation so p = (0, 0)
		Point[] Q = Points.translation(P, -p.x, -p.y);
		double w = 0;
		for(int i = 0; i < Q.length; i++) {
			int j = (i + 1) % Q.length;
			if(Q[i].y * Q[j].y <= 0) {
				// segment crosses the x-axis
				double r = (Q[i].y - Q[j].y) * Q[i].x + Q[i].y * (Q[j].x - Q[i].x);
				//check for intersection with the positive x-axis
				if((Q[i].y - Q[j].y > 0 && r > 0) || (Q[i].y - Q[j].y < 0 && r < 0)) {
					// segment fully crosses the x-axis
					// - to + add 1, + to - subtract 1
					w += Q[i].y < 0? 1 : -1;
				} else if(Q[i].y == 0 && Q[i].x > 0) {
					// the segment starts at the x-axis
					// 0 to + add 0.5, 0 to - subtract 0.5
					w += Q[j].y > 0? 0.5 : -0.5;
				} else if(Q[j].y == 0 && Q[j].x > 0) {
					// the segment ends at the x-axis
					// - to 0 add 0.5, + to 0 subtract 0.5
					w += Q[i].y < 0? 0.5 : -0.5;
				}
			}
		}
		return w;
	}

	public static boolean inPolygon2(Point[] pol, Point p) {
		double xmin = Double.POSITIVE_INFINITY;
		for(Point q : pol) {
			xmin = Math.min(xmin, q.x);
		}
		int n = pol.length;
		int inter = 0;
		Point q = new Point(xmin - 1, p.y);
		for(int i = 0; i < n; i++) {
			int j = (i + 1) % n;
			if(Points.intersects(pol[i], pol[j], p, q)) {
				inter++;
			}
		}
		return inter % 2 == 1;
	}

	public static boolean inPolygon2(ArrayList<Point> pol, Point p) {
		double xmin = Double.POSITIVE_INFINITY;
		for(Point q : pol) {
			xmin = Math.min(xmin, q.x);
		}
		int n = pol.size();
		int inter = 0;
		Point q = new Point(xmin - 1, p.y);
		for(int i = 0; i < n; i++) {
			int j = (i + 1) % n;
			if(Points.intersects(pol.get(i), pol.get(j), p, q)) {
				inter++;
			}
		}
		return inter % 2 == 1;
	}

	public static boolean onBoundary(Point[] pol, Point p) {
		int n = pol.length;
		for(int i = 0; i < n; i++) {
			int j = (i + 1) % n;
			if(Points.onSegment(pol[i], pol[j], p)) return true;
		}
		return false;
	}

	public static boolean inPolygon(Point[] pol, Point p) {
		return onBoundary(pol, p) || strictInPolygon(pol, p);
	}

	public static boolean strictInPolygon(Point[] pol, Point p) {
		return !Cmp.eq(winding(pol, p), 0);
	}

	public static boolean inPolygon(ArrayList<Point> pol, Point p) {
		return onBoundary(pol, p) || strictInPolygon(pol, p);
	}

	public static boolean strictInPolygon(ArrayList<Point> pol, Point p) {
		return !Cmp.eq(winding(pol, p), 0);
	}

	// assumes p is not on P
	public static double winding(ArrayList<Point> P, Point p) {
		//make a translation so p = (0, 0)
		Point[] Q = Points.translation(P, -p.x, -p.y);
		double w = 0;
		for(int i = 0; i < Q.length; i++) {
			int j = (i + 1) % Q.length;
			if(Q[i].y * Q[j].y <= 0) {
				// segment crosses the x-axis
				double r = (Q[i].y - Q[j].y) * Q[i].x + Q[i].y * (Q[j].x - Q[i].x);
				//check for intersection with the positive x-axis
				if((Q[i].y - Q[j].y > 0 && r > 0) || (Q[i].y - Q[j].y < 0 && r < 0)) {
					// segment fully crosses the x-axis
					// - to + add 1, + to - subtract 1
					w += Q[i].y < 0? 1 : -1;
				} else if(Q[i].y == 0 && Q[i].x > 0) {
					// the segment starts at t - 1he x-axis
					// 0 to + add 0.5, 0 to - subtract 0.5
					w += Q[j].y > 0? 0.5 : -0.5;
				} else if(Q[j].y == 0 && Q[i+1].x > 0) {
					// the segment ends at the x-axis
					// - to 0 add 0.5, + to 0 subtract 0.5
					w += Q[i].y < 0? 0.5 : -0.5;
				}
			}
		}
		return w;
	}

	public static boolean onBoundary(ArrayList<Point> pol, Point p) {
		int n = pol.size();
		for(int i = 0; i < n; i++) {
			int j = (i + 1) % n;
			if(Points.onSegment(pol.get(i), pol.get(j), p)) return true;
		}
		return false;
	}

	public static double area2(Point[] pol) {
		double sum = 0.0;
		int n = pol.length;
		for (int i = 0; i < n; i++) {
			int j = (i + 1) % n;
			sum = sum + (pol[i].x * pol[j].y) - (pol[i].y * pol[j].x);
		}
		return 0.5 * sum;
	}

	/*
	 * Cut a polygon by the line pq
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList[] cut(ArrayList<Point> pol, Point p, Point q) {
		ArrayList<Point> polWithInter = new ArrayList<>();
		Line L = new Line(p, q);
		for(int i = 0; i < pol.size(); i++) {
			polWithInter.add(pol.get(i));
			int j = (i + 1) % pol.size();
			Point inter = L.intersection(new Line(pol.get(i), pol.get(j)));
			if(inter != null && Points.onSegment(pol.get(i), pol.get(j), inter) && !Points.eq(pol.get(i), inter) && !Points.eq(pol.get(j), inter)) {
				polWithInter.add(inter);
			}
		}
		ArrayList<Point> pol1 = new ArrayList<Point>();
		ArrayList<Point> pol2 = new ArrayList<Point>();
		for(Point x : polWithInter) {
			int orient = Points.orient(x, p, q);
			if(orient <= 0) pol1.add(x);
			if(orient >= 0) pol2.add(x);
		}
		return new ArrayList[] {pol1, pol2};
	}

	public static ArrayList<Point> cut(ArrayList<Point> pol, Point p, Point q, Point x) {
		ArrayList<Point> res = new ArrayList<>();
		int orient = Points.orient(p, q, x);
		Line L = new Line(p, q);
		for(int i = 0; i < pol.size(); i++) {
			int j = (i + 1) % pol.size();
			if(Points.orient(p, q, pol.get(i)) == orient) {
				res.add(pol.get(i));
			}
			Point inter = L.intersection(new Line(pol.get(i), pol.get(j)));
			if(inter != null && Points.onSegment(pol.get(i), pol.get(j), inter)) {
				res.add(inter);
			}
		}
		return res;
	}

}
