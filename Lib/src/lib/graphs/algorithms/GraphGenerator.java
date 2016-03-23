package lib.graphs.algorithms;

import lib.graphs.SparseGraph;
import lib.util.Constants;
import lib.util.Grids;

public class GraphGenerator {
	
	public static SparseGraph generateGrid(int n, int m) {
		SparseGraph g = new SparseGraph(n * m);
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				int x = Grids.posToIndex(m, i, j);
				for(int[] d : Constants.dir) {
					int ii = i + d[0];
					int jj = j + d[1];
					if(Grids.inBounds(n, m, ii, jj)) {
						int y = Grids.posToIndex(m, ii, jj);
						g.connectBoth(x, y);
					}
				}
			}
		}
		return g;
	}
	
	public static SparseGraph generateCycle(int n) {
		SparseGraph g = new SparseGraph(n);
		for(int i = 0; i < n - 1; i++) {
			g.connectBoth(i, i + 1);
		}
		g.connectBoth(n - 1, 0);
		return g;
	}

}
