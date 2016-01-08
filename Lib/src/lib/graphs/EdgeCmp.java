package lib.graphs;

import java.util.Comparator;

public class EdgeCmp implements Comparator<Edge> {

	private Measure measure;
	
	public EdgeCmp(Measure measure) {
		this.measure = measure;
	}

	public int compare(Edge e1, Edge e2) {
		return Double.compare(measure.getWeight(e1), measure.getWeight(e2));
	}
	
	
}
