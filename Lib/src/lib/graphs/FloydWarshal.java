package lib.graphs;

public class FloydWarshal {

	public static double[][] apsp(double[][] g) {
		int n = g.length;
		double[][] d = new double[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				d[i][j] = g[i][j];
			}
		}
		for(int k = 0; k < n; k++) {
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
				}
			}
		}
		return d;
	}
	
}
