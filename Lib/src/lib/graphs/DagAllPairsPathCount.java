package lib.graphs;

import java.math.BigInteger;

public class DagAllPairsPathCount {

	// cnt[x][y] is the number of path between x and y
	public BigInteger[][] cnt;
	
	// O(n^2), assumes that g is a directed acyclic graph
	public DagAllPairsPathCount(Graph dag) {
		cnt = new BigInteger[dag.V()][dag.V()];
		for(int x = 0; x < dag.V(); x++) {
			for(int y = 0; y < dag.V(); y++) {
				cnt[x][y] = BigInteger.ZERO;
			}
		}
		TopologicalSort ts = new TopologicalSort(dag);
		int[] order = ts.order;
		int[] position = ts.position;
		for(int i = 0; i < dag.V(); i++) {
			int x = order[i];
			cnt[x][x] = BigInteger.ONE;
			for(int j = i + 1; j < dag.V(); j++) {
				int y = order[j];
				for(int z : dag.inNeighbors(y)) {
					if(position[z] >= i) {
						cnt[x][y] = cnt[x][y].add(cnt[x][z]);
					}
				}
			}
		}
	}
	
	public long pathCountLong(int x, int y) {
		BigInteger LONG = BigInteger.valueOf(Long.MAX_VALUE);
		assert cnt[x][y].compareTo(LONG) <= 0;
		return cnt[x][y].longValue();
	}
	
	public BigInteger pathCount(int x, int y) {
		return cnt[x][y];
	}
	
	public boolean multipath(int x, int y) {
		return cnt[x][y].compareTo(BigInteger.ONE) > 0;
	}

	
}
