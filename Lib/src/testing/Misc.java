package testing;

import lib.numbers.Combinatorics;

public class Misc {

	
	public static void main(String[] args) {
		for(int n = 0; n <= 20; n++) {
			for(int k = 0; k <= n; k++) {
				System.out.print(Combinatorics.nChooseKmod2(n, k) + " ");
			}
			System.out.println();
		}
	}
	
}
