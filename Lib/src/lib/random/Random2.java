package lib.random;

import java.util.HashSet;
import java.util.Random;

import lib.data.IntPair;
import lib.graphs.BitGraph;
import lib.graphs.Edge;
import lib.graphs.EdgeFunction;
import lib.graphs.Graph;

public class Random2 {

	private static Random rnd = new Random();

	public static void setSeed(long seed) {
		rnd.setSeed(seed);
	}

	// generate a random symmetric matrix with entries in [0, bound[
	public static int[][] symmetricMatrix(int n, int bound) {
		int[][] m = new int[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = i; j < n; j++) {
				int x = rnd.nextInt(bound);
				m[i][j] = m[j][i] = x;
			}
		}
		return m;
	}

	// generate a random matrix with entries in [0, bound[
	public static int[][] matrix(int n, int bound) {
		int[][] m = new int[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				int x = rnd.nextInt(bound);
				m[i][j] = m[j][i] = x;
			}
		}
		return m;
	}

	// shuffle and array of int
	public static void shuffleArray(int[] ar) {
		for(int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	// shuffle and array of Object
	public static void shuffleArray(Object[] ar) {
		for(int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			Object a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}
	
	// generate n random pairs of integers with components in [0, bound[
	public static HashSet<IntPair> randomPairs(int n, int bound) {
		HashSet<IntPair> pairs = new HashSet<IntPair>();
		while(pairs.size() < n) {
			int x = rnd.nextInt(bound);
			int y = rnd.nextInt(bound);
			pairs.add(new IntPair(x, y));
		}
		return pairs;
	}
	
	public static Graph genConnectedUndGraph(int n, int m) {
		Graph g = new BitGraph(n);
		// build a spanning tree, to ensure connectedness
		for(int i = 1; i < n; i++) {
			int j = rnd.nextInt(i);
			g.connect(i, j);
			g.connect(j, i);
		}
		m -= n - 1;
		// add the remaining edges
		while(m > 0) {
			int i = rnd.nextInt(n);
			int j = rnd.nextInt(n);
			while(i == j || g.connected(i, j) || g.connected(j, i)) {
				i = rnd.nextInt(n);
				j = rnd.nextInt(n);
			}
			g.connect(i, j);
			g.connect(j, i);
			m--;
		}
		return g;
	}
	
	public static int genInt(int min, int max) {
		return rnd.nextInt(max + 1) + min;
	}
 
}
