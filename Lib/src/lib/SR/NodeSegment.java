package lib.SR;

public class NodeSegment extends Segment {

	public int node;
	
	public NodeSegment(int node) {
		this.node = node;
	}
	
	public String toString() {
		return String.format("[%d]", node);
	}
	
}
 