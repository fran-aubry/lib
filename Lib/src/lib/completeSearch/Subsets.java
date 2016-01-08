package lib.completeSearch;

import java.util.Arrays;

public class Subsets {

	public static void main(String[] args) {
		iterateSubs(3);
	}


	static void genSubs(int n) {
		genSubs(new int[n], 0, 0);
	}

	static void genSubs(int[] S, int i, int size) {
		if(i == S.length) {
			System.out.println(Arrays.toString(Arrays.copyOf(S, size)));
		} else {
			genSubs(S, i + 1, size);
			S[size] = i;
			genSubs(S, i + 1, size + 1);
		}
	}
	
	static void iterateSubs(int n) {
		for(int s = 0; s < (1 << n); s++) {
			System.out.println(Integer.toBinaryString(s));
		}
	}
	
	
	static String binToStr(int s, int n) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		for(int i = 0; i < n; i++) {
			if(get(s, i)) sb.append(i + " ");
		}
		sb.append(" }");
		return sb.toString();
	}
	
	static boolean get(int s, int i) {
		return ((s >> i) & 1) == 1;
	}


}
