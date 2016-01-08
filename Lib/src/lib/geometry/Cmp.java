package lib.geometry;

public class Cmp {
	
	public static boolean eq(double x, double y) {
		return Math.abs(x - y) <= Config.EPS;
	}
	
	public static boolean le(double x, double y) {
		return x < y - Config.EPS;
	}
	
	public static boolean ge(double x, double y) {
		return x > y + Config.EPS;
	}
	
	public static boolean leq(double x, double y) {
		return x <= y + Config.EPS;
	}
	
	public static boolean geq(double x, double y) {
		return x >= y - Config.EPS;
	}
	
	public static int cmp(double x, double y) {
		if(eq(x, y)) return 0;
		if(le(x, y)) return -1;
		return 1;
	}
	
}
