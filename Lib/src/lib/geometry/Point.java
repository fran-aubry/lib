package lib.geometry;

public class Point {

	public static Point O = new Point(0, 0);
	public double x, y;
	public int id;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		id = 0;
	}
	
	public Point(int[] c) {
		this.x = c[0];
		this.y = c[1];
		id = 0;
	}
	
	public int xi() {
		return (int)x;
	}
	
	public int yi() {
		return (int)y;
	}
	
	public long xl() {
		return (long)x;
	}
	
	public long yl() {
		return (long)y;
	}
	
	public String toString() {
		return String.format("(%.3f, %.3f) %d", x, y, id);
	}
	
}
