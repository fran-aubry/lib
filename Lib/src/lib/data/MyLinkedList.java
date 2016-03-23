package lib.data;

public class MyLinkedList {

	Node root;
	
	public MyLinkedList() {
		root = null;
	}
	
	public void add(int x) {
		if(root == null) {
			root = new Node(x);
		}
		Node temp = root;
		root = new Node(x);
		root.next = temp;
	}
	
	private class Node {
		
		Node next;
		int v;
		
		public Node(int v) {
			this.v = v;
		}
		
	}
	
}
