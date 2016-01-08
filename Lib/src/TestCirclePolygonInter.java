
import java.util.ArrayList;
import java.util.Scanner;

import lib.geometry.Circle;
import lib.geometry.Intersection;
import lib.geometry.Point;
import lib.geometry.Points;

public class TestCirclePolygonInter {

/*

12 2
1 0
2 0
2 2
3 2
3 0
4 0
4 6
3 6
3 4
2 4
2 6
1 6

8
-2 0
-1 0
-1 -1
1 -1
1 0
2 0
2 2
-2 2
0 0 2

6 10
-8 2
8 2
8 14
0 14
0 6
-8 14

4 1
1 0
2 0
2 1
1 1


4 2
-3 -1
3 -1
3 1
-3 1

4 3
-1 -1
1 -1
1 1
-1 1


4 1
-1 -1
1 -1
1 1
-1 1


4 0.5
-1 -1
1 -1
1 1
-1 1

8 3
1 1
2 1
2 5
-2 5
-2 1
-1 1
-1 4
1 4

4 3
1 1
2 1
2 5
1 5

3 3
0 0
1 5
1 1

3 3
0 0
1 1
1 5

6 65
0 85
85 0
91 0
0 91
-91 0
-85 0
6 65
0 85
85 0
91 0
0 91
-91 0
-85 0

3 65
0 0
0 85
85 0

3 65
0 0
0 91
91 0

3 65
0 0
85 0
0 85

3 3
3 0
0 3
-3 -6

 */
	
	public static void main(String[] args) {
		toTikz();
		System.exit(0);
		Scanner reader = new Scanner(System.in);
		int n = reader.nextInt();
		Point[] pol = new Point[n];
		for(int i = 0; i < n; i++) {
			pol[i] = new Point(reader.nextDouble(), reader.nextDouble());
		}
		Circle C = new Circle(new Point(reader.nextDouble(), reader.nextDouble()), reader.nextDouble());
		
		double A = Intersection.interArea(pol, C);
		System.out.println();
		System.out.println(A);
		reader.close();
	}
	
	static void toTikz() {
		Scanner reader = new Scanner(System.in);
		int n = reader.nextInt();
		double r = reader.nextDouble();
		Point[] pol = new Point[n];
		for(int i = 0; i < n; i++) {
			pol[i] = new Point(reader.nextDouble(), reader.nextDouble());
		}
		Circle C = new Circle(new Point(0, 0), r);
		
		ArrayList<Point> boundary = Intersection.boundary(pol, C);
		
		double s = 0.1;

		double rd = 0.1;
		
		System.out.println("\\begin{center}");
		
		System.out.println("\\begin{tikzpicture}");
		
		System.out.printf("\\def\\r{%.3f}\n", rd);
		
		System.out.printf("\\draw[fill = red, opacity=0.3] (%.3f, %.3f) circle (%.3f);\n", s * C.center.xi(), s * C.center.yi(),  s * C.radius);
		
		for(Point p : boundary) {
			System.out.printf("\\fill[red] (%.3f, %.3f) circle (\\r);\n", s * p.x, s * p.y);
		}
		
		/*
		for(int i = 0; i < pol.length; i++) {
			int j = (i + 1) % pol.length;
			if(Points.ccw(new Point(0, 0),  pol[i], pol[j])) {
				System.out.printf("\\fill[green, opacity = 0.5] (%.3f, %.3f) -- (%.3f, %.3f) -- (%.3f, %.3f) -- (%.3f, %.3f);\n", 0.0, 0.0, s * pol[i].x, s * pol[i].y, s * pol[j].x, s * pol[j].y, 0.0, 0.0);				
			} else {
				System.out.printf("\\fill[red, opacity = 0.5] (%.3f, %.3f) -- (%.3f, %.3f) -- (%.3f, %.3f) -- (%.3f, %.3f);\n", 0.0, 0.0, s * pol[i].x, s * pol[i].y, s * pol[j].x, s * pol[j].y, 0.0, 0.0);					
			}
		}
		*/

		System.out.print("\\draw[fill = green, opacity=0.3]");
		for(Point p : pol) {
			System.out.printf(" (%.3f, %.3f) -- ", s * p.x, s * p.y);
		}
		System.out.printf("(%.3f, %.3f);\n", s * pol[0].x, s * pol[0].y);

		
		System.out.println("\\end{tikzpicture}");
		System.out.println("\\end{center}");
		
		System.out.println(Intersection.interArea(pol, C));
		
		reader.close();	
	}
	
	static void solvePolution() {
		Scanner reader = new Scanner(System.in);
		int n = reader.nextInt();
		int r = reader.nextInt();
		Point[] pol = new Point[n];
		for(int i = 0; i < n; i++) {
			pol[i] = new Point(reader.nextDouble(), reader.nextDouble());
		}
		Circle C = new Circle(new Point(0, 0), r);
		
		double A = Intersection.interArea(pol, C);
		System.out.println(A);
		reader.close();
	}
	
}
