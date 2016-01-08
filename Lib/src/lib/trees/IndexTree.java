package lib.trees;

public class IndexTree extends SegmentTree<Integer> {

	public IndexTree(int n) {
		super(n);
	}
	
	public int removeKth(int k) {
		int ret = getKth(k);
		if(ret != -1) set(ret, 0);
		return ret;
	}
	
	public int getKth(int k) {
		if(k >= t.get(0)) return -1;
		return getKth(k, 0, n - 1, 0);
	}
	
	private int getKth(int k, int l, int r, int node) {
		if(l == r) {
			return t.get(r) == 0? -1 : r;
		}
		int m = (l + r) / 2;
		if(t.get(left(node)) > k) {
			return getKth(k, l, m, left(node));
		} else {
			return getKth(k - t.get(left(node)), m + 1, r, right(node));
		}
	}

	protected Integer f(Integer left, Integer right) {
		return t.get(left) + t.get(right);
	}

}
