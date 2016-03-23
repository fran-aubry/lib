package lib.completeSearch;

import java.math.BigInteger;
import java.util.Arrays;

import lib.numbers.Combinatorics;


public class Permutations {

	public static void main(String[] args) {
		int[] p = new int[4];
		for(int i = 0; i < p.length; i++) {
			p[i] = i;
		}
		do {
			System.out.println(Arrays.toString(p));
		} while(nextPerm(p));
		/*

		t2 = System.nanoTime();
		System.out.println((t2 - t1) / 1e9);

		t1 = System.nanoTime();
		iterPerm(n, new int[n], new boolean[n], 0);

		t2 = System.nanoTime();
		System.out.println((t2 - t1) / 1e9);
		 */
	}

	public static void iterPerm(int n, int[] p, boolean[] used, int i) {
		if(i == n) {
			//System.out.println(Arrays.toString(p));
		} else {
			for(int j = 0; j < n; j++) {
				if(!used[j]) {
					used[j] = true;
					p[i] = j; 
					iterPerm(n, p, used, i + 1);
					used[j] = false;
				}
			}
		}
	}
	
	public static int[] firstPerm(int n) {
		int[] p = new int[n];
		for(int i = 0; i < n; i++) {
			p[i] = i;
		}
		return p;
	}

	public static void iterPerm(int n) {
		iterPermRec(0, new int[n], new BacktrackSet(n));
	}

	public static void iterPermRec(int i, int[] p, BacktrackSet s) {
		if(i == p.length) {
			System.out.println(Arrays.toString(p));
		} else {
			for(int j = 0; j < s.size(); j++) {
				p[i] = s.get(j);
				s.remove(j);
				iterPermRec(i + 1, p, s);
				s.backtrack(j);
			}
		}
	}

	public static boolean nextPerm(int[] p) {
		// (1.) find the rightmost i such that p[i] < p[i + 1]
		int i = p.length - 2;
		while(i >= 0 && p[i] >= p[i + 1]) i--;
		// check if not such i was found
		if(i == -1) return false;
		// (2.) find the rightmost j such that p[i] < p[j]
		int j = p.length - 1;
		while(p[i] >= p[j]) j--;
		// (3.) swap p[i] and p[j]
		swap(p, i, j);
		// (4.) reverse p[i + 1], p[i + 2], ...
		for(int k = 0; k < (p.length - i - 1) / 2; k++)
			swap(p, i + 1 + k, p.length - 1 - k);
		return true;
	}

	public static void swap(int[] p, int i, int j) {
		int t = p[i];
		p[i] = p[j];
		p[j] = t;
	}

	public static class BacktrackSet {

		private int[] s;
		private int size;

		public BacktrackSet(int n) {
			s = new int[n];
			for(int i = 0; i < n; i++) s[i] = i;
			size = n;
		}

		public int get(int i) {
			return s[i];
		}

		public int size() {
			return size;
		}

		public void remove(int i) {
			int temp = s[i];
			s[i] = s[size - 1];
			s[size - 1] = temp;
			size--;
		}

		public void backtrack(int i) {
			int temp = s[i];
			s[i] = s[size];
			s[size] = temp;
			size++;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < size; i++) {
				sb.append(s[i] + " ");
			}
			sb.append("|");
			for(int i = size; i < s.length; i++) {
				sb.append(" " + s[i]);
			}
			return sb.toString();
		}

	}
	
	public static BigInteger findIndex(int[] p) {
		int n = p.length;
		BigInteger F = Combinatorics.factorial(n - 1);
		while(true) {
			
		}
	}
	
	
	public static void perm(int[] p, int i) {
	    for(int a = p.length; a > 0; a--) {
	    	int tmp = p[a - 1];
	    	p[a - 1] = p[i];
	    	p[i] = tmp;
	    	i = i / a;
	    }
	}

}
