package lib.graphs.algorithms;

import java.io.IOException;

import lib.data.Pair;
import lib.graphs.SparseGraph;
import lib.io.InputReader;

public class GraphIO2 {
	
	public static Pair<SparseGraph, GraphMetadata> readLabeledUndirected(InputReader reader) {
		//InputReader reader = new InputReader();
		try {
			int[] VE = reader.splitIntLine();
			int V = VE[0];
			int E = VE[1];
			SparseGraph g = new SparseGraph(V);
			GraphMetadata gmdt = new GraphMetadata();
			for(int i = 0; i < E; i++) {
				String[] OD = reader.splitLine();
				int x = gmdt.add(OD[0]);
				int y = gmdt.add(OD[1]);
				g.connect(x, y);
			}
			return new Pair<>(g, gmdt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
