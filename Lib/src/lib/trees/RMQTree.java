package lib.trees;

public class RMQTree extends Tree {
	
	public int[][] RMQ;
	
	/*
	 * val[x] = value of node x
	 */
	public RMQTree(int[] T, int[] val) {
		super(T);
		buildRMQ(val);
	}
	
	/*
	 * RMQ[i][j] = minimum on the path from i to its 2^j-th ancestor
	 * RMQ[i][0] = val[i]
	 * RMQ[i][j] = min(RMQ[i][j - 1], RMQ[P[i][j - 1]][j - 1]
	 */
	
	public void buildRMQ(int[] val) {
		RMQ = new int[n][lg + 1];
		int i, j;
		//we initialize every element in P with -1
		for(i = 0; i < n; i++) {
			for(j = 0; j < RMQ[i].length; j++) {
				RMQ[i][j] = Integer.MAX_VALUE;
			}
		}
		for(i = 0; i < n; i++) {
			RMQ[i][0] = val[i];
		}
		for(i = 0; i < n; i++) {
			if(T[i] != -1) {
				RMQ[i][1] = Math.min(val[i], val[T[i]]);				
			}
		}
		//bottom up dynamic programming
		for(j = 1; 1 << j < n; j++) {
			for(i = 0; i < n; i++) {
				if(P[i][j] != -1) {
					int k = P[i][j - 1];
					RMQ[i][j + 1] = Math.min(RMQ[i][j], RMQ[k][j]);
				}
			}
		}
	}

	public int rmq(int p, int q) {
		int a = lca(p, q);
		return Math.min(rmq2(p, a), rmq2(q, a));
	}
	
	public int rmq2(int p, int a) {
		int dL = L[p] - L[a];
		int res = RMQ[p][0];
		for(int i = 0; (1 << i) <= dL; i++) {
			if(((dL >> i) & 1) == 1) {
				res = Math.min(res, RMQ[p][i + 1]);
				p = P[p][i];
			}
		}
		return res;
	}

	
}