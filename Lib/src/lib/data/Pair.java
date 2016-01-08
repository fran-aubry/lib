package lib.data;

public class Pair<E, T> {

	public E x;
	public T y;
	
	public Pair(E x, T y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return String.format("(%s, %s)", x.toString(), y.toString());
	}
	
}
