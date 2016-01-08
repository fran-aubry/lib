package lib.trees;

import java.util.TreeSet;

public class RkMQTree extends Tree {
	
	public TreeSet<Integer>[][] RMQ;
	public int k;
	
	/*
	 * Maintains the k minimums, k >= 2
	 */
	public RkMQTree(int[] T, int[] val, int k) {
		super(T);
		this.k = k;
		buildRMQ(val);
	}
	
	/*
	 * RMQ[i][j] = minimum on the path from i to its 2^j-th ancestor
	 * RMQ[i][0] = val[i]
	 * RMQ[i][j] = min(RMQ[i][j - 1], RMQ[P[i][j - 1]][j - 1]
	 */
	@SuppressWarnings("unchecked")
	public void buildRMQ(int[] val) {
		RMQ = new TreeSet[n][lg + 1];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < RMQ[i].length; j++) {
				RMQ[i][j] = new TreeSet<>();				
			}
		}
		int i, j;
		for(i = 0; i < n; i++) {
			RMQ[i][0].add(val[i]);
		}
		for(i = 0; i < n; i++) {
			if(T[i] != -1) {
				RMQ[i][1].add(val[i]);
				RMQ[i][1].add(val[T[i]]);				
			}
		}
		//bottom up dynamic programming
		for(j = 1; 1 << j < n; j++) {
			for(i = 0; i < n; i++) {
				if(P[i][j] != -1) {
					int k = P[i][j - 1];
					RMQ[i][j + 1] = merge(RMQ[i][j], RMQ[k][j]);
				}
			}
		}
	}
	
	private TreeSet<Integer> merge(TreeSet<Integer> L1, TreeSet<Integer> L2) {
		TreeSet<Integer> T = new TreeSet<>();
		T.addAll(L1);
		T.addAll(L2);
		TreeSet<Integer> res = new TreeSet<>();
		for(int i = 0; !T.isEmpty() && i < k; i++) {
			res.add(T.pollFirst());
		}
		return res;
	}

	public TreeSet<Integer> rmq(int p, int q) {
		int a = lca(p, q);
		return merge(rmq2(p, a), rmq2(q, a));
	}
	
	public TreeSet<Integer> rmq2(int p, int a) {
		int dL = L[p] - L[a];
		TreeSet<Integer> res = new TreeSet<>();
		res.addAll(RMQ[p][0]);
		for(int i = 0; (1 << i) <= dL; i++) {
			if(((dL >> i) & 1) == 1) {
				res = merge(res, RMQ[p][i + 1]);
				p = P[p][i];
			}
		}
		return res;
	}

	
}