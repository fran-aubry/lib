package lib.geometry;

public class Triangles {

	/*
	 * Determines if there exists a triangle with
	 * sides of lengths a, b and c
	 */
	public static boolean canBeTriangle(int a, int b, int c) {
		return a + b > c && a + c > b && b + c > a;
	}
	
}
