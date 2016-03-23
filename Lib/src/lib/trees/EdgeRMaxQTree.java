package lib.trees;

public class EdgeRMaxQTree extends Tree {
	
	public int[][] RMQ;
	
	/*
	 * val[i] = value of edge between node i and its parent
	 */
	public EdgeRMaxQTree(int[] T, int[] val) {
		super(T);
		buildRMQ(val);
	}
	
	/*
	 * RMQ[i][j] = minimum on the path from i to its 2^(j-1)th ancestor
	 * RMQ[i][0] = 0
	 * RMQ[i][1] = val[i]
	 * RMQ[i][j] = min(RMQ[i][j - 1], RMQ[P[i][j - 1]][j - 1]
	 */
	
	public void buildRMQ(int[] val) {
		RMQ = new int[n][lg + 1];
		int i, j;
		//we initialize every element in RMQ with +oo
		for(i = 0; i < n; i++) {
			for(j = 0; j < RMQ[i].length; j++) {
				RMQ[i][j] = Integer.MIN_VALUE;
			}
		}
		for(i = 0; i < n; i++) {
			if(T[i] != -1) {
				RMQ[i][1] = val[i];				
			}
		}
		//bottom up dynamic programming
		for(j = 1; 1 << j < n; j++) {
			for(i = 0; i < n; i++) {
				if(P[i][j] != -1) {
					int k = P[i][j - 1];
					RMQ[i][j + 1] = Math.max(RMQ[i][j], RMQ[k][j]);
				}
			}
		}
	}

	public int rmq(int p, int q) {
		int a = lca(p, q);
		return Math.max(rmq2(p, a), rmq2(q, a));
	}
	
	public int rmq2(int p, int a) {
		int dL = L[p] - L[a];
		int res = RMQ[p][0];
		for(int i = 0; (1 << i) <= dL; i++) {
			if(((dL >> i) & 1) == 1) {
				res = Math.max(res, RMQ[p][i + 1]);
				p = P[p][i];
			}
		}
		return res;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append('\n');
		sb.append('\n');
		for(int i = 0; i < n; i++) {
			sb.append(i + ": ");
			for(int j = 0; j < lg; j++) {
				sb.append((RMQ[i][j] == Integer.MAX_VALUE ? "I" : RMQ[i][j]) + "\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}


	
}