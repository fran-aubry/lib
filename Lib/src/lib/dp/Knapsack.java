package lib.dp;

import java.util.LinkedList;
import java.util.Scanner;

public class Knapsack {

/*
4 7
2 3
1 2
2 1
3 3

 */
	
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		int n = reader.nextInt();
		int C = reader.nextInt();
		int[] v = new int[n];
		int[] w = new int[n];
		for(int i = 0; i < n; i++) {
			v[i] = reader.nextInt();
			w[i] = reader.nextInt();
		}
		knapsack(w, v, C);
		for(int i : buildSol(w, v, C, parent)) {
			System.out.println(v[i] + " " + w[i]);
		}
		
		reader.close();
	}

	static int TAKE = 0, SKIP = 1;
	static int[][] best, parent;
	
	static void knapsack(int[] w, int[] v, int C) {
		int n = w.length;
		best = new int[n][C + 1];
		// initialize tha parent matrix
		parent = new int[n + 1][C + 1];
		// solve the base case (easier sub-problems)
		for(int c = 0; c <= C; c++) {
			if(c >= w[0]) {
				best[0][c] = v[0];
				parent[0][c] = TAKE;
			} else {
				parent[0][c] = SKIP;
			}
		}
		// iterate in the right order and solve all other sub-problems
		for(int i = 1; i < n; i++) {
			for(int c = 0; c <= C; c++) {
				if(w[i] > c) {
					best[i][c] = best[i - 1][c];
					parent[i][c] = SKIP;
				} else {
					int take = v[i] + best[i - 1][c - w[i]];
					int skip = best[i - 1][c];
					if(take >= skip) {
						best[i][c] = take;
						parent[i][c] = TAKE;
					} else {
						best[i][c] = skip;
						parent[i][c] = SKIP;
					}
				}
			}
		}
	}
	
	static LinkedList<Integer> buildSol(int[] w, int[] v, int C, int[][] parent) {
		int n = w.length;
		LinkedList<Integer> taken = new LinkedList<>();
		int i = n - 1;
		int c = C;
		while(i >= 0) {
			if(parent[i][c] == TAKE) {
				taken.add(i);
				c -= w[i];
			}
			i--;
		}
		return taken;
	}

}
