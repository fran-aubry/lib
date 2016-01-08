package lib.trees;

public class LisTree extends SegmentTree<LisData> {

	private int index;

	public LisTree(int n) {
		super(n);
		index = 0;
	}

	public void add(int v) {
		if(v == 0) {
			set(v, new LisData(1, v, index, null));
		} else {
			LisData x = getValue(0, v - 1);
			set(v, new LisData((x == null ? 0 : x.len) + 1, v, index, x));
		}
		index++;
	}

	public int lisSize() {
		return t.get(0).len;
	}

	public int[] lis() {
		int[] lis = new int[lisSize()];
		LisData cur = t.get(0);
		for(int i = lis.length - 1; i >= 0; i--) {
			lis[i] = cur.cur;
			cur = cur.prev;
		}
		return lis;
	}

	protected LisData f(LisData left, LisData right) {
		if(left == null) return right;
		if(right == null) return left;
		if(left.len > right.len) return left;
		if(left.len < right.len) return right;
		if(left.index > right.index) return left;
		return right;
	}

}

