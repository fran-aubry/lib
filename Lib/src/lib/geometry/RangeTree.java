package lib.geometry;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeSet;

public class RangeTree {

	private static XCmp xcmp = new XCmp();
	private static YCmp ycmp = new YCmp();
	private static Node root;

	public RangeTree(Point[] p) {
		Arrays.sort(p, xcmp);
		root = build(p, 0, p.length - 1);
	}

	private Node build(Point[] p, int i, int j) {
		if(i == j) {
			TreeSet<Point> Ty = new TreeSet<Point>(ycmp);
			Ty.add(p[i]);
			return new Node(p[i], Ty);
		}
		int m = (i + j) / 2;
		TreeSet<Point> Ty = new TreeSet<Point>(ycmp);
		for(int k = i; k <= j; k++) {
			Ty.add(p[k]);
		}
		Node node = new Node(p[m], Ty);
		node.left =  i > m - 1 ? null : build(p, i, m - 1);
		node.right = m + 1 > j ? null :build(p, m + 1, j);
		return node;
	}

	private Node findSplit(Node cur, int x1, int x2) {
		if(cur.p.x < x1) {
			return findSplit(cur.left, x1, x2);
		}
		if(cur.p.x > x2) {
			return findSplit(cur.right, x1, x2);
		}
		return cur;
	}

	public LinkedList<Point> query(int x1, int x2, int y1, int y2) {
		Node split = findSplit(root, x1, x2);
		LinkedList<Point> res = new LinkedList<>(); 
		if(inRange(split.p, x1, x2, y1, y2)) {
			res.add(split.p);
		}
		if(!split.isLeaf()) {
			Point L = new Point(-1, y1);
			Point R = new Point(-1, y2);
			Node cur = split.left;
			while(cur != null && !cur.isLeaf()) {
				if(inRange(cur.p, x1, x2, y1, y2)) res.add(cur.p);
				if(cur.isLeaf()) break;
				if(x1 > cur.p.x) {
					cur = cur.right;
				} else {
					// go left, report right subtree
					if(cur.right != null) res.addAll(cur.right.Ty.subSet(L, R));
					cur = cur.left;
				}
			}
			if(cur != null && inRange(cur.p, x1, x2, y1, y2)) {
				res.add(cur.p);
			}
			cur = split.right;
			while(cur != null) {
				if(inRange(cur.p, x1, x2, y1, y2)) res.add(cur.p);
				if(cur.isLeaf()) break;
				if(x2 <= cur.p.x) cur = cur.left;
				else {
					// go right, report left subtree
					if(cur.left != null) res.addAll(cur.left.Ty.subSet(L, R));
					cur = cur.right;
				}
			}
		}
		return res;
	}

	public static boolean inRange(Point p, int x1, int x2, int y1, int y2) {
		return x1 <= p.x && p.x <= x2 && y1 <= p.y && p.y <= y2;
	}

	private class Node {

		public Point p;
		public TreeSet<Point> Ty;
		public Node left, right;

		public Node(Point px, TreeSet<Point> Ty) {
			this.p = px;
			this.Ty = Ty;
		}

		public boolean isLeaf() {
			return Ty.size() == 1;
		}

		public String toString() {
			return p + " -> " + Ty;
		}

	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		buildString(root, "", sb);
		return sb.toString();
	}

	static void buildString(Node cur, String pre, StringBuilder sb) {
		if(cur == null) return;
		sb.append(pre + cur + "\n");
		buildString(cur.left, pre + "\t", sb);
		buildString(cur.right, pre + "\t", sb);
	}

}
