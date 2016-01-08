package lib.graphs;

public class Edge {

	public int orig, dest;
	private Edge reverse;
	
	public Edge(int orig, int dest) {
		this.orig = orig;
		this.dest = dest;
	}
	
	public Edge reverse() {
		if(reverse == null) {
			reverse = new Edge(dest, orig);
		}
		return reverse;
	}
	
	public int orig() {
		return orig;
	}
	
	public int dest() {
		return dest;
	}
	
	public String toString() {
		return String.format("(%d, %d)", orig, dest);
	}
	
	public boolean equals(Object other) {
		if(other instanceof Edge) {
			Edge o = (Edge)other;
			return orig == o.orig && dest == o.dest;
		}
		return false;
	}
	
	public int hashCode() {
		return orig ^ dest;
	}
	
}