package lib.graphs;

public class WGraph {

	private Graph g;
	private Measure w;
	
	public WGraph(Graph g, Measure w) {
		this.g = g;
		this.w = w;
	}
	
	public Graph g() {
		return g;
	}
	
	public Measure w() {
		return w;
	}

	
}
