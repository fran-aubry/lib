package lib.graphs;

import lib.util.ArraysExt;

public class Trees {

	public static int diameter(Graph T) {
		int[] d = BFS.distances(T, 0);
		int vmax = ArraysExt.argmax(d);
		d = BFS.distances(T, vmax);
		return ArraysExt.max(d);
	}
	
}
