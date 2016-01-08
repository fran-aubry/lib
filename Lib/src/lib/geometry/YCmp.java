package lib.geometry;

import java.util.Comparator;

public class YCmp implements Comparator<Point> {

	public int compare(Point p1, Point p2) {
		return Double.compare(p1.y, p2.y);
	}

	
}
