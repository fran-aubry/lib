package lib.string;

import java.util.LinkedList;

public class Strings {

	public static LinkedList<Integer> occurences(String P, String T) {
		LinkedList<Integer> occ = new LinkedList<>();
		int i = T.indexOf(P, 0);
		while(i != -1) {
			occ.add(i);
			i = T.indexOf(P, i + 1);
		}
		return occ;
	}
	
}
