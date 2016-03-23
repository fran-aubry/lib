package lib.string;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map.Entry;

public class Trie {

	private Node root;
	private int nbNodes;
	private BitSet terminal;
	
	public Trie() {
		root = new Node(0);
		nbNodes = 1;
		terminal = new BitSet();
	}
	
	public int nbNodes() {
		return nbNodes;
	}
	
	public void add(String s) {
		Node cur = root;
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(cur.contains(c)) {
				cur = cur.get(c);
			} else {
				cur = cur.add(c, nbNodes++);
			}
		}
		terminal.set(cur.index);
	}
	
	public String printAsAutomata() {
		StringBuilder sb = new StringBuilder();
		sb.append(nbNodes + " " + (nbNodes - 1) + '\n');
		for(int i = 0; i < nbNodes; i++) {
			if(terminal.get(i)) {
				sb.append('1');
			} else {
				sb.append('0');
			}
			if(i < nbNodes - 1) {
				sb.append(' ');
			}
		}
		sb.append('\n');
		printEdges(root, sb);
		return sb.toString();
	}
	
	private void printEdges(Node cur, StringBuilder sb) {
		
		if(terminal.get(cur.index)) {
			sb.append((cur.index + 1) + " " + 1 + " " + "0\n");
		}
		
		for(Entry<Character, Node> e : cur.children.entrySet()) {
			sb.append((cur.index + 1) + " " + (e.getValue().index + 1) + " " + e.getKey() + '\n');
			printEdges(e.getValue(), sb);
		}
		
	}
	
	private class Node {
	
		private HashMap<Character, Node> children;
		private int index;
		
		public Node(int index) {
			this.index = index;
			children = new HashMap<>();
		}
		
		public Node add(char c, int index) {
			Node n = new Node(index);
			children.put(c, n);
			return n;
		}
		
		public boolean contains(char c) {
			return children.containsKey(c);
		}
		
		public Node get(char c) {
			return children.get(c);
		}
		
	}
	
}
