package lib.testing;

import java.util.Arrays;
import java.util.LinkedList;

import lib.numbers.NumberTheory;
import lib.numbers.Sym;

public class TestPrimes {

	public static void main(String[] args) {
		int[] p = new int[] {1, 5, 4, 3, 2, 0};
		LinkedList<LinkedList<Integer>> C = Sym.cycles(p);
		System.out.println(C);
	}

	static long visiblePoints(int n) {
		long count = 8;
		int[] div = NumberTheory.sieve2(n);
		int[] phi = new int[n + 1];
		for(int i = 1; i <= n; i++) {
			phi[i] = NumberTheory.phi(i, div);
		}
		for(int i = 2; i <= n; i++) {
			count += 8 * phi[i];
		}
		return count;
	}
	
	
}
