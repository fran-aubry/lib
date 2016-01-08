package lib.trees;
import java.util.ArrayList;

public abstract class SegmentTree<E> {

	protected int n;
	protected ArrayList<E> t;

	public SegmentTree(int n) {
		this.n = n;
		t = new ArrayList<>(4 * n - 1);
		for(int i = 0; i < 4 * n - 1; i++) {
			t.add(null);
		}
	}

	public int size() {
		return n;
	}

	public void set(int i, E val) {
		set(0, n - 1, i, val, 0);
	}

	/**
	 * O(log(n))
	 */
	private void set(int l, int r, int i, E val, int node) {
		if(l == r) {
			t.set(node, val);
		} else {
			int m = (l + r) / 2;
			if(i <= m) {
				set(l, m, i, val, left(node));
			} else {
				set(m + 1, r, i, val, right(node));
			}
			int left = left(node);
			int right = right(node);
			t.set(node, f(t.get(left), t.get(right)));
		}
	}

	public E getValue(int i, int j) {
		return getValue(0, n - 1, i, j, 0);
	}

	/**
	 * O(log(n))
	 */
	private E getValue(int l, int r, int i, int j, int node) {
		if(l == i && r == j) {
			return t.get(node);
		}
		int m = (l + r) / 2;
		if(j <= m) {
			return getValue(l, m, i, j, left(node));
		} else if(i > m) {
			return getValue(m + 1, r, i, j, right(node));
		}
		return f(getValue(l, m, i, m, left(node)), getValue(m + 1, r, m + 1, j, right(node)));
	}

	protected int left(int node) {
		return (node << 1) + 1;
	}

	protected int right(int node) {
		return (node << 1) + 2;
	}

	protected abstract E f(E left, E right);

	/**
	 * @time O(n log(n))
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toString(0, 0, n - 1, "", sb);
		return sb.toString().trim();
	}

	private void toString(int node, int i, int j, String pre, StringBuilder sb) {
		sb.append(pre);
		sb.append(String.format("[%d, %d]:", i, j));
		sb.append("\t");
		sb.append(t.get(node));
		sb.append("\n");
		if(i != j) {
			int m = (i + j) / 2;
			toString(2 * node + 1, i, m, pre + "\t", sb);
			toString(2 * node + 2, m + 1, j, pre + "\t", sb);
		}
	}

	
}
