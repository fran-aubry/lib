package lib.SR;

public class EdgeSegment extends Segment {

	public int orig, dest;
	
	public EdgeSegment(int orig, int dest) {
		this.orig = orig;
		this.dest = dest;
	}
	
	public String toString() {
		return String.format("(%d, %d)", orig, dest);
	}
	
}
