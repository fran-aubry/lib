package lib.geometry;

public class Sphere {

	
	public double radlat(double lat) {
		return lat * Math.PI / 180.0;
	}
	
	public double radlon(double lon) {
		return lon * Math.PI / 180.0;
	}
	
	public Point3D getPoint(double lat, double lon) {
		double rlat = radlat(lat);
		double rlon = radlon(lon);
		double x = Math.cos(rlat) * Math.cos(rlon);
		double y = Math.cos(rlat) * Math.sin(rlon);
		double z = Math.sin(lat);
		return new Point3D(x, y, z);
	}
	
}
