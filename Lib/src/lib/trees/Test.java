package lib.trees;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

import lib.util.ArraysExt;

public class Test {

	/*

9 8
0 1
0 2
1 3
1 4
2 5
2 6
6 7
7 8
1 9 5 3 2 7 4 8 6

9 8
8 7
7 6
6 5
5 4
4 3
3 2
2 1
1 0
1 9 5 3 2 7 4 8 6

5 4 5
1 3
1 2
1 4
4 5
2 1 4 3
4 5 6
1 5 2
5 5 10
2 3 3
5 3 1

8
1 0 1 0 3 0 2 4

8
10 1 10 1 30 1 30 40

8
-7 10 9 2 3 8 8 1

4
0 3 1 2

8
1 6 6 2 7 1 4 5


	 */


	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		int n = reader.nextInt();
		int[] a = new int[n];
		for(int i = 0; i < n; i++) {
			a[i] = reader.nextInt();
		}
		int[] lis = ArraysExt.LIS(a);
		System.out.println(Arrays.toString(lis));
		reader.close();

	}

	static void solve() {
		Scanner reader = new Scanner(System.in);
		int n = reader.nextInt();
		int m = reader.nextInt();
		int q = reader.nextInt();
		int[] T = new int[n];
		Arrays.fill(T, -1);
		for(int i = 0; i < n - 1; i++) {
			int o = reader.nextInt() - 1;
			int d = reader.nextInt() - 1;
			T[d] = o;
		}
		int[] v = new int[n];
		Arrays.fill(v, Integer.MAX_VALUE);
		for(int i = 0; i < m; i++) {
			v[i] = reader.nextInt();
		}
		RkMQTree QT = new RkMQTree(T, v, 10);
		for(int i = 0; i < q; i++) {
			int x = reader.nextInt() - 1;
			int y = reader.nextInt() - 1;
			int a = reader.nextInt();
			TreeSet<Integer> res = QT.rmq(x, y);
			while(!res.isEmpty() && res.last() == Integer.MAX_VALUE) {
				res.pollLast();
			}
			System.out.print(Math.min(res.size(), a));
			for(int j = 0; !res.isEmpty() && j < a; j++) {
				System.out.print(" " + res.pollFirst());
			}
			System.out.println();
		}
		reader.close();
	}

}
