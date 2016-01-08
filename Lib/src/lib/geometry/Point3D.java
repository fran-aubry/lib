package lib.geometry;

public class Point3D {

	public double x, y, z;
	
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String toString() {
		return String.format("(%.3f %.3f %.3f)", x, y, z);
	}
	
}
