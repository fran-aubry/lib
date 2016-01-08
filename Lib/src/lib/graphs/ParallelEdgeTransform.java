package lib.graphs;

import java.util.HashMap;

public class ParallelEdgeTransform {

	public Measure wt;
	public Graph gt;
	
	public ParallelEdgeTransform(Graph g, Measure mul, Measure w, boolean directed) {
		if(directed) {
			
		} else {
			transformUndirected(g, mul, w);
		}
	}
	
	public void transformUndirected(Graph g, Measure mul, Measure w) {
		wt = new HashMeasure();
		String[] original = g.getNodeLabels();
		HashMap<Integer, String> labels = new HashMap<>();
		for(int x = 0; x < g.V(); x++) {
			labels.put(x, original[x]);
		}
		for(Edge e : g.edges()) {
			g.disconnect(e);
			if(e.orig > e.dest) {
				continue;
			}
			// split the weight of the edge
			int we = (int)w.getWeight(e);
			int we1 = we / 2;
			int we2 = we1 + (2 * we1 == we ? 0 : 1);
			// add edges equal to the multiplicity
			for(int i = 0; i < (int)mul.getWeight(e); i++) {
				// change graph structure
				g.addNode();
				int x = g.V() - 1;
				g.connect(e.orig, x);
				g.connect(x, e.orig);
				g.connect(x, e.dest);
				g.connect(e.dest, x);
				// set new weights
				wt.setWeight(e.orig, x, we1);
				wt.setWeight(x, e.orig, we1);
				wt.setWeight(x, e.dest, we2);
				wt.setWeight(e.dest, x, we2);
				labels.put(x, labels.get(e.orig) + ";" + labels.get(e.dest) + "_" + i);
			}
		}
		String[] lab = new String[g.V()];
		for(int x = 0; x < g.V(); x++) {
			lab[x] = labels.get(x);
		}
		g.setNodeLabels(lab);
	}
}
