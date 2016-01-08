import java.util.LinkedList;
import java.util.Scanner;

import lib.geometry.Point;
import lib.geometry.RangeTree;

public class TestRangeTree {

/*

8
2 5
5 2
4 4
6 7
7 1
3 8
1 6
8 3

 */
	
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		int n = reader.nextInt();
		Point[] p = new Point[n];
		for(int i = 0; i < n; i++) {
			int x = reader.nextInt();
			int y = reader.nextInt();
			p[i] = new Point(x, y);
 		}
		RangeTree T = new RangeTree(p);
		System.out.println(T);
		LinkedList<Point> res = T.query(4, 9, 1, 5);
		System.out.println(res);
		reader.close();
	}
	
}
