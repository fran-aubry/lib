package lib.geometry;

import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

public class LowerEnvelope {

	/*

5
0 6 15 6
5 12 7 0
0 14 14 0
0 0 12 12
0 3 13 7

3
0 0 4 4
4 4 8 0
0 5 8 5

3
0 0 4 4
0 5 8 5
4 4 8 0

3
0 5 8 5
0 0 4 4
4 4 8 0

3
0 4 8 4
0 0 4 4
4 4 8 0

3
0 0 4 4
0 4 8 4
4 4 8 0

3
0 0 4 4
4 4 8 0
0 4 8 4

4
0 -1 10 4
0 1 12 1
5 9 9 -3
5 6 7 -4

	 */

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		int n = reader.nextInt();

		LowerEnvelope E = new LowerEnvelope();

		for(int i = 0; i < n; i++) {
			int x1 = reader.nextInt();
			int y1 = reader.nextInt();
			int x2 = reader.nextInt();
			int y2 = reader.nextInt();
			Line l = new Line(x1, y1, x2, y2);
			l.id = i + "";
			E.add3(l);
		}

		/*
		System.out.println(E.T.size());
		for(Line l : E.T) {
			System.out.println(l);
		}
		*/

		Tikz tikz = new Tikz();
		tikz.drawSegment(Point.O, new Point(20, 0), "->");
		tikz.drawSegment(Point.O, new Point(0, 20), "->");
		
		for(MyLine l : E.T) {
			tikz.drawSegment(new Point(0, l.f(0)), new Point(20, l.f(20)), "thick");
			if(l.x2 != Double.POSITIVE_INFINITY) {
				tikz.drawSegment(new Point(l.x2, 0), new Point(l.x2, l.f(l.x2)), "dashed");				
			}
		}
		
		System.out.println(tikz.getTikz(0.5));
		
		reader.close();
	}

	private class SlopeCmp implements Comparator<Line> {

		public int compare(Line l1, Line l2) {
			return -Double.compare(l1.m, l2.m);
		}

	}

	private TreeSet<MyLine> T;

	public LowerEnvelope() {
		T = new TreeSet<>(new SlopeCmp());
	}

	/*
	public void add(Line l) {
		//Line l = new Line(m , b); // TODO
		T.add(l);
		while(parallel(l) || leftLeft(l) || rightRight(l) || leftRight(l));
	}
	 */

	public void add2(Line L) {
		MyLine l = new MyLine(L.m, L.b);
		l.id = L.id;
		if(!useless(l)) {
			T.add(l);
			MyLine left = T.lower(l);
			MyLine right = T.higher(l);
			while(left != null && useless(left)) {
				T.remove(left);
				left = T.lower(l);
			}
			while(right != null && useless(right)) {
				T.remove(right);
				right = T.higher(l);
			}
		}
	}
	
	public void add3(Line L) {
		MyLine l = new MyLine(L.m, L.b);
		l.id = L.id;
		if(add(l)) {
			MyLine left = T.lower(l);
			while(left != null && remove(left)) {
				left = T.lower(l);
			}
			MyLine right = T.higher(l);
			while(right != null && remove(right)) {
				right = T.higher(l);
			}
		}
	}
	
	private boolean remove(MyLine l) {
		MyLine left = T.lower(l);
		if(left == null) return false;
		MyLine right = T.higher(l);
		if(right == null) return false;
		double x1 = left.intersection(l).x;
		double x2 = right.intersection(l).x;
		if(Cmp.geq(x1, x2)) {
			T.remove(l);
			double x3 = left.intersection(right).x;
			left.x2 = x3;
			right.x1 = x3;
			return true;
		}
		return false;
	}
	
	private boolean add(MyLine l) {
		MyLine left = T.lower(l);
		MyLine right = T.higher(l);
		double x1 = left == null ? Double.NEGATIVE_INFINITY : left.intersection(l).x;
		double x2 = right == null ? Double.POSITIVE_INFINITY : right.intersection(l).x;
		if(Cmp.le(x1, x2)) {
			l.x1 = x1;
			l.x2 = x2;
			T.add(l);
			if(left != null) left.x2 = x1;
			if(right != null) right.x1 = x2;
			return true;
		}
		return false;
	}

	private boolean useless(MyLine l) {
		// check if l is minimal in a non empty interval
		Line left = T.lower(l);
		if(left != null) {
			Line right = T.higher(l);
			if(right != null) {
				double x1 = left.intersection(l).x;
				double x2 = right.intersection(l).y;
				if(Cmp.geq(x2, x1)) {
					// l is never minimal
					return true;
				}
			}
		}
		return false;
	}

	/*
	private boolean parallel(MyLine l) {
		Line left = T.lower(l);
		if(left != null && Cmp.eq(l.m, left.m)) {
			if(Cmp.leq(l.b, left.b)) {
				T.remove(left);
				return true;
			} else {
				T.remove(l);
				return false;
			}
		}
		Line right = T.higher(l);
		if(right != null && Cmp.eq(l.m, right.m)) {
			if(Cmp.leq(l.b, right.b)) {
				T.remove(right);
				return true;
			} else {
				T.remove(l);
				return false;
			}
		}
		return false;
	}
	 */

	/*
	private boolean leftLeft(Line l) {
		Line left = T.lower(l);
		if(left == null) return false;
		Line leftleft = T.lower(left);
		if(leftleft == null) return false;
		double x1 = leftleft.intersection(left).x;
		double x2 = leftleft.intersection(l).x;
		if(Cmp.geq(x1, x2)) {
			T.remove(left);
			return true;
		}
		return false;
	}

	private boolean rightRight(Line l) {
		Line right = T.higher(l);
		if(right == null) return false;
		Line rightright = T.higher(right);
		if(rightright == null) return false;
		double x1 = l.intersection(right).x;
		double x2 = l.intersection(rightright).x;
		if(Cmp.geq(x1, x2)) {
			T.remove(right);
			return true;
		}
		return false;
	}


	private boolean leftRight(Line l) {
		Line left = T.lower(l);
		if(left == null) return false;
		Line right = T.higher(l);
		if(right == null) return false;
		double x1 = l.intersection(left).x;
		double x2 = l.intersection(right).x;
		if(Cmp.geq(x1, x2)) {
			T.remove(l);
		}
		return false;
	}
	 */

	public class MyLine extends Line {

		private double x1, x2; // interval where line is minimum

		public MyLine(double m, double b) {
			super(m, b);
		}

		public String toString() {
			return String.format("%s [%.3f, %.3f]", id, x1, x2);
		}

	}

}
