package lib.data;

import lib.geometry.Cmp;
import lib.geometry.Point;

public class Pareto2D {

	Node root;
	int size;

	public Pareto2D() {
		size = 0;
	}

	public void add(Point p) {
		if(root == null) root = new Node(null, p);
		else {
			Node prev = null;
			Node cur = root;
			while(cur != null) {
				int quad = quadrant(cur.p, p);
				if(quad == 2) return;
				if(quad == 3) {
					// the current node is dominated
					deleteNode(cur);
					if(cur.dr == null && cur.ul == null) {
						cur = null;
					}
				} else {
					prev = cur;
					cur = quad == 1 ? cur.ul : cur.dr; 
				}
			}
			int quad = quadrant(prev.p, p);
			if(quad == 1) prev.ul = new Node(prev, p);
			else prev.dr = new Node(prev, p);
		}
	}
	
	public boolean contains(Point p) {
		return find(p) != null;
	}

	public Node find(Point p) {
		if(root == null) return null;
		Node cur = root;
		while(cur != null) {
			if(Cmp.eq(cur.p.x, p.x) && Cmp.eq(cur.p.y, p.y)) return cur;
			int q = quadrant(cur.p, p);
			if(q == 2 || q == 3) return null;
			if(q == 1) cur = cur.ul;
			if(q == 4) cur = cur.dr;
		}
		return null;
	}

	public void delete(Point p) {
		deleteNode(find(p));
	}
	
	public void printTree() {
		if(root == null) System.out.println("[]");
		else printTree(root, "");
	}
	
	private void printTree(Node node, String pref) {
		if(node == null) return;
		System.out.println(pref + node.p);
		printTree(node.ul, pref + "\t");
		printTree(node.dr, pref + "\t");
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		buildString(root, sb, "");
		return sb.toString();
	}
	
	private void buildString(Node node, StringBuilder sb, String pref) {
		if(node == null) return;
		sb.append(pref + node.p + "\n");
		buildString(node.ul, sb, pref + "\t");
		buildString(node.dr, sb, pref + "\t");
	}
	

	/*
	 * 1 | 2
	 * --p--
	 * 3 | 4 
	 */
	private int quadrant(Point p, Point q) {
		if(Cmp.eq(q.x, p.x) && Cmp.eq(q.y, p.y)) return 0;
		if(Cmp.leq(q.x, p.x) && Cmp.leq(p.y, q.y)) return 1;
		if(Cmp.leq(p.x, q.x) && Cmp.leq(q.y, p.y)) return 4;
		if(Cmp.le(q.x, p.x)) return 3;
		return 2;
	}
	
	private Node leftmost(Node node) {
		if(node == null) return null;
		Node cur = node;
		while(cur.ul != null) cur = cur.ul;
		return cur;
	}
	
	private Node rightmost(Node node) {
		if(node == null) return null;
		Node cur = node;
		while(cur.dr != null) cur = cur.dr;
		return cur;
	}

	private void deleteLeaf(Node node) {
		if(node.parent == null) root = null;
		Node parent = node.parent;
		if(parent.dr != null && quadrant(parent.dr.p, node.p) == 0) {
			parent.dr = null;
		} else if(parent.ul != null && quadrant(parent.ul.p, node.p) == 0) {
			parent.ul = null;
		}
	}
	
	private void deleteNode(Node node) {
		if(node.dr == null && node.ul == null) {
			deleteLeaf(node);
		} else if(node.dr == null) {
			node.p = node.ul.p;
			node.dr = node.ul.dr;
			node.ul = node.ul.ul;
		} else if(node.ul == null) {
			node.p = node.dr.p;
			node.dr = node.dr.dr;
			node.ul = node.dr.ul;
		} else {
			Node replacement;
			if(node.dr.height >= node.ul.height) {
				replacement = leftmost(node.dr);
			} else {
				replacement = rightmost(node.ul);
			}
			node.p = replacement.p;
			deleteNode(replacement);				
		}		
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public class Node {

		Node ul, dr, parent;
		Point p;
		int height;
		
		public Node(Node parent, Point p) {
			this.parent = parent;
			this.p = p;
			this.height = 1;
		}
		
		public String toString() {
			return p.toString();
		}

	}

}
