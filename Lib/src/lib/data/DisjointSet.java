package lib.data;

public class DisjointSet {
	
	private int[] rank, p, size;
	private int nSets;
	
	public DisjointSet(int n) {
		nSets = n;
		size = new int[n];
		rank = new int[n];
		p = new int[n];
		for (int i = 0; i < p.length; i++) {
			p[i] = i;
			size[i] = 1;
		}
	}
	
    public void union(int x, int y) {
		if (findSet(x) != findSet(y)) {
			link(findSet(x), findSet(y));
			nSets--;
		}
	}

	public void link(int x, int y) {
		if (rank[x] > rank[y]) {
			p[y] = x;
			size[x] += size[y];
		} else {
			p[x] = y;
			size[y] += size[x];
			if (rank[x] == rank[y]) {
				rank[y]++;
			}
		}
	}
	
	public int findSet(int x) {
		if (x != p[x]) {
			p[x] = findSet(p[x]);
		}
		return p[x];
	}
	
	public int nSets() {
		return nSets;
	}
	
}