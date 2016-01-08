
import java.util.LinkedList;
import java.util.Scanner;

import lib.util.ArraysExt;

/*

-7
10
9
2
3
8
8
1

1

1
2
3
4
5
6
7
8
9

1000 2000 3000 4000
100 200 300 400
10 20 30 40


 */

public class p00481 {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		LinkedList<Integer> L = new LinkedList<>();
		while(reader.hasNextInt()) {
			L.add(reader.nextInt());
		}
		int[] a = new int[L.size()];
		int i = 0;
		for(int x : L) {
			a[i++] = x;
		}
		int[] lis = ArraysExt.LIS(a);
		System.out.println(lis.length);
		System.out.println("-");
		for(int x : lis) {
			System.out.println(x);
		}
		reader.close();
	}

}
