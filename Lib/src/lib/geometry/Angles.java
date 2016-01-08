
package lib.geometry;

public class Angles {

	/**
	 * 
	 *   0 -90
	 * - - - - - - -  p--->---q - - - - - - -  
	 *   0  90             x /
	 *                      / 
	 *                     r
	 * 
	 * 
	 *                     r   
	 *                      \  
	 *   0 -90            -x \
	 * - - - - - - -  p--->---q - - - - - - -  
	 *   0  90                    
	 *                       
	 *                     
	 *                     
	 * PRE: p, q and r are not collinear
	 */
	
	public static double angle(Point p, Point q, Point r) {
		assert !Points.collinear(p, q, r);
		double pq = Points.distance(p, q);
		double qr = Points.distance(q, r);
		double rp = Points.distance(r, p);
		double x = Math.acos((pq * pq + qr * qr - rp * rp) / (2 * pq * qr));
		if(Points.ccw(p, q, r)) return x;
		return -x;
	}
	
}
