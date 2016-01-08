import java.util.List;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

import javax.tools.DiagnosticCollector;

import lib.geometry.Circle;
import lib.geometry.Circles;
import lib.geometry.Intersection;
import lib.geometry.Line;
import lib.geometry.Point;
import lib.graphs.APSP;
import lib.graphs.Dijkstra;
import lib.graphs.Graph;
import lib.graphs.Measure;
import lib.graphs.WGraph;
import lib.io.GraphIO;
import lib.util.ArraysExt;

/*
0 0 2
-2 2 2 2

0 0 2
-2 1 2 1

0 0 2
-1 -1 1 1

0 0 2
2 -2 2 2

// circles

0 0 2
1 0 2

0 0 2
2 0 2

0 0 2
4 0 2

// distances

4
0 1 1 1.4142135623730951
1 0 1.4142135623730951 1
1 1.4142135623730951 0 1
1.4142135623730951 1 1 0

5
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1
1 1 1 1 1


0 3.5 8 3.5

0 3 8 0
0 1 8 4
0 3 8 0
0 2 8 1

0 0 12 12
0 2.5 13 7
0 6 6 6

0 14 14 0

5 12 7 0 

 */

public class Test {

	public static void main(String[] args) {
		Circle C = new Circle(new Point(0, 0), 3);
		Point A = new Point(0, 3);
		Point B = new Point(-3, -6);
		System.out.println(Intersection.intersect(A, B, C));
		
		
	}
	

	static Point[] pointsFromDistances(double[][] d) {
		int n = d.length;
		Point[] P = new Point[n];
		// fix two points arbitrarily
		P[0] = new Point(0, 0);
		P[1] = new Point(d[0][1], 0);
		// compute positions for all other points
		boolean hasSol = pointsFromDistances(d, 2, P);
		if(!hasSol) return null;
		// make all coordinates positive
		double minX = ArraysExt.minX(P);
		double minY = ArraysExt.minY(P);
		for(Point p : P) {
			p.x += Math.abs(minX);
			p.y += Math.abs(minY);
		}
		return P;
	}

	static boolean pointsFromDistances(double[][] d, int i, Point[] P) {
		if(i == P.length) return true;
		Circle[] C = new Circle[i];
		for(int j = 0; j < i; j++) {
			C[j] = new Circle(P[j], d[j][i]);
		}
		List<Point> L = Circles.intersect(C);
		for(Point p : L) {
			P[i] = p;
			if(pointsFromDistances(d, i + 1, P)) {
				return true;
			}
		}
		return false;
	}

}
