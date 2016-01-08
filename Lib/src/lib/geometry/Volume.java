package lib.geometry;

public class Volume {

	public static double coneVolume(double baseRadius, double height) {
		return cylinderVolume(baseRadius, height) / 3.0;
	}
	
	public static double cylinderVolume(double baseRadius, double height) {
		return Math.PI * baseRadius * baseRadius * height;
	}
	
	public static double truncatedConeVolume(double r1, double r2, double h) {
		return Math.PI * h * (r1 * r1 + r2 * r2 + r1 * r2) / 3.0;
	}
	
}
