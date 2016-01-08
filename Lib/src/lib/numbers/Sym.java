package lib.numbers;

import java.util.LinkedList;

public class Sym {

	public static LinkedList<LinkedList<Integer>> cycles(int[] p) {
		int n = p.length;
		boolean[] done = new boolean[n];
		LinkedList<LinkedList<Integer>> C = new LinkedList<>();
		for(int i = 0; i < n; i++) {
			if(!done[i]) {
				LinkedList<Integer> c = new LinkedList<>();
				int x = i;
				c.add(x);
				done[x] = true;
				while(i != p[x]) {
					c.add(p[x]);
					x = p[x];
					done[x] = true;
				}
				C.add(c);
			}
		}
		return C;
	}

}
