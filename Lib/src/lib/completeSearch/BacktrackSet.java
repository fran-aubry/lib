package lib.completeSearch;

public class BacktrackSet {

	private int[] s;
	private int size;

	public BacktrackSet(int n) {
		s = new int[n];
		for(int i = 0; i < n; i++) s[i] = i;
		size = n;
	}

	public int get(int i) {
		return s[i];
	}

	public int size() {
		return size;
	}

	public void remove(int i) {
		int temp = s[i];
		s[i] = s[size - 1];
		s[size - 1] = temp;
		size--;
	}

	public void backtrack(int i) {
		int temp = s[i];
		s[i] = s[size];
		s[size] = temp;
		size++;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for(int i = 0; i < size; i++) {
			sb.append(s[i]);
			if(i < size - 1) sb.append(", ");
		}
		sb.append("}");
		return sb.toString();
	}

}