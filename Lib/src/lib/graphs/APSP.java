package lib.graphs;

public class APSP {

	private Graph g;
	private Measure w;
	private double[][] d;

	public APSP(Graph g, Measure w) {
		this.g = g;
		this.w = w;
		floydwarshall(g, w);
	}

	public APSP(WGraph wg) {
		this.g = wg.g();
		this.w = wg.w();
		floydwarshall(wg.g(), wg.w());
	}

	private void floydwarshall(Graph g, Measure w) {
		d = new double[g.V()][g.V()];
		// base case		
		for(int i = 0; i < g.V(); i++) {
			for(int j = 0; j < g.V(); j++) {
				if(i == j) d[i][j] = 0;
				else if(g.connected(i, j)) d[i][j] = w.getWeight(i, j);
				else d[i][j] = Double.POSITIVE_INFINITY;
			}
		}
		// dp
		for(int k = 0; k < g.V(); k++) {
			for(int i = 0; i < g.V(); i++) {
				for(int j = 0; j < g.V(); j++) {
					if(d[i][j] > d[i][k] + d[k][j]) {
						d[i][j] = d[i][k] + d[k][j];
					}
				}
			}
		}	
	}
	
	public boolean isDagEdge(int s, int x, int y) {
		return d[s][x] + w.getWeight(x, y) == d[s][y];
	}
	
	public boolean isDagEdge(int s, Edge e) {
		return d[s][e.orig] + w.getWeight(e.orig, e.dest) == d[s][e.dest];
	}

	public Graph spdag(int s) {
		Graph dag = new TreeGraph(g.V());
		for(int x = 0; x < g.V(); x++) {
			for(int y : g.outNeighbors(x)) {
				if(isDagEdge(s, x, y)) {
					dag.connect(x, y);
				}
			}
		}
		return dag;
	}
	
}
