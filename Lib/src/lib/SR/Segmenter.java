package lib.SR;

import java.util.LinkedList;

import lib.graphs.Dijkstra;
import lib.graphs.Graph;
import lib.graphs.Measure;
import lib.graphs.Path;


public class Segmenter {
	
	public Graph g;
	public Graph[] dags;
	public Measure IGP;
	public boolean allowECMP;
	
	public Segmenter(Graph g, Measure IGP, boolean allowECMP) {
		this.g = g;
		this.IGP = IGP;
		this.allowECMP = allowECMP;
		dags = new Graph[g.V()];
	}
	
	public Segmenter(boolean allowECMP, Graph[] dags) {
		this.allowECMP = allowECMP;
		this.dags = dags;
	}
	
	public LinkedList<Segment> segmentPath(Path path) {
		int r = path.first();
		LinkedList<Segment> segments = new LinkedList<>();
		for(int i = 0; i < path.length(); i++) {
			int v = path.v[i];
			int u = path.v[i + 1];
			if(!getDag(r).connected(v, u)) {
				// edge (v, u) not in dag r
				if(getDag(v).inDeg(u) == 1) {
					// edge (v, u) in dag v
					segments.add(new NodeSegment(v));
					r = v;
				} else {
					// edge (v, u) not in dag v
					segments.add(new EdgeSegment(v, u));
					r = u;
				}
			} else if(getDag(r).inDeg(u) > 1) {
				// edge (v, u) is in dage but multipath
				if(getDag(v).inDeg(u) > 1) {
					// multipath also in dag v
					segments.add(new EdgeSegment(v, u));
					r = u;
				} else {
					segments.add(new NodeSegment(v));
					r = v;
				}
			}
		}
		return segments;
	}
	
	private Graph getDag(int root) {
		if(dags[root] == null) {
			dags[root] = (new Dijkstra(g, root, IGP)).getDag();
		}
		return dags[root];
	}

}
