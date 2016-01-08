package lib.trees;

import java.util.Arrays;

public class Tree {

	public int[] h;
	public int[][] P;
	public int lg;
	public int[] T, L;
	public int n;

	/*
	 * Tree representation:  An array T such that T[i]
	 * is the parent of node i in the tree. T[root] = -1
	 */
	public Tree(int[] T) {
		this.n = T.length;
		this.T = T;
		lg = (int)(Math.log(n) / Math.log(2)) + 1;
		buildParents();
		buildLevels();
	}

	private void buildLevels() {
		L = new int[n];
		Arrays.fill(L, -1);
		for(int i = 0; i < n; i++) {
			if(L[i] == -1) {
				buildLevels(i);
			}
		}
	}

	private void buildLevels(int i) {
		if(T[i] == -1) {
			L[i] = 0;
		} else if(L[i] == -1) {
			buildLevels(T[i]);
			L[i] = 1 + L[T[i]];
		}
	}
	
	/*
	 * P[i][j] is the 2^j-th ancestor of i
	 * P[i][0] = T[i]
	 * P[i][j] = P[ P[i][j - 1] ][j - 1], j > 0
	 */

	private void buildParents() {
		P = new int[n][lg];
		int i, j;
		//we initialize every element in P with -1
		for (i = 0; i < n; i++) {
			for (j = 0; 1 << j < n; j++) {
				P[i][j] = -1;
			}
		}
		//the first ancestor of every node i is T[i]
		for (i = 0; i < n; i++) {
			P[i][0] = T[i];
		}
		//bottom up dynamic programming
		for(j = 1; 1 << j < n; j++) {
			for(i = 0; i < n; i++) {
				if(P[i][j - 1] != -1) {
					P[i][j] = P[P[i][j - 1]][j - 1];
				}
			}
		}
	}
	
	public int L(int i) {
		return L[i];
	}
	
	public int P(int i, int j) {
		return P[i][j];
	}
	
	public int size() {
		return n;
	}

	// O(log(n))
	public int lca(int p, int q) {
		int tmp, log, i;
		// if p is situated on a higher level than q then we swap them
		if (L[p] < L[q]) {
			tmp = p;
			p = q;
			q = tmp;
		}
		//we compute the value of [log(L[p])]
		for (log = 1; 1 << log <= L[p]; log++);
		log--;
		//we find the ancestor of node p situated on the same level
		//with q using the values in P
		for (i = log; i >= 0; i--) {
			if (L[p] - (1 << i) >= L[q]) {
				p = P[p][i];
			}
		}
		if(p == q) return p;
		// we compute LCA(p, q) using the values in P
		for (i = log; i >= 0; i--) {
			if (P[p][i] != -1 && P[p][i] != P[q][i]) {
				p = P[p][i];
				q = P[q][i];
			}
		}
		return T[p];
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < n; i++) {
			sb.append(i + ": ");
			for(int j = 0; j < lg; j++) {
				sb.append((P[i][j] == -1 ? "-" : P[i][j]) + "\t");
			}
			sb.append("\n");
		}
		sb.append(Arrays.toString(L));
		return sb.toString();
	}


}
