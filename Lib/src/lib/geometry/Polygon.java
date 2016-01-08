package lib.geometry;

import java.util.ArrayList;

public class Polygon {

	public static double area(Point[] pol) {
		double A = 0;
		for(int i = 0; i < pol.length; i++) {
			int j = (i + 1) % pol.length;
			A += (pol[j].y - pol[i].y) * (pol[j].x + pol[i].x);
		}
		return Math.abs(A) / 2;
	}
	
	/*
	public static
			System.out.println(hullA / A); double area(ArrayList<Point> pol) {
		double A = 0;
		for(int i = 0; i < pol.size(); i++) {
			int j = (i + 1) % pol.size();
			A += (pol.get(j).y - pol.get(i).y) * (pol.get(j).x + pol.get(i).x);
		}
		return A;
	}*/
	
	public static double area(ArrayList<Point> pol) {
		double result = 0.0, x1, y1, x2, y2;
		for(int i = 0; i < pol.size() - 1; i++) {
			x1 = pol.get(i).x; x2 = pol.get(i + 1).x;
			y1 = pol.get(i).y; y2 = pol.get(i + 1).y;
			result += (x1 * y2 - x2 * y1);
		}
		return Math.abs(result) / 2.0;
	}
	
	
}
