package lib.trees;

public class LisData {

	int len, cur, index;
	LisData prev;
	
	public LisData(int len, int cur, int index, LisData prev) {
		this.len = len;
		this.cur = cur;
		this.index = index;
		this.prev = prev;
	}
	
	public String toString() {
		return String.format("(%d, %d, %d, %s)", len, cur, index, prev == null ? "-1" : prev.cur);
	}
	
}