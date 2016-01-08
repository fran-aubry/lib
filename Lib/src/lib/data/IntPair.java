package lib.data;

public class IntPair {

	public int x, y;
	
	public IntPair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int hashCode() {
		return x ^ y;
	}
	
	public boolean equals(Object other) {
		if(other instanceof IntPair) {
			IntPair o = (IntPair)other;
			return x == o.x && y == o.y;
		}
		return false;
	}
	
	public String toString() {
		return String.format("(%d, %d)", x, y);
	}
	
}
