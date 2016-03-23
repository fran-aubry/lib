package testing;

import java.util.Arrays;
import java.util.Scanner;

import lib.graphs.BitGraph;
import lib.graphs.Graph;
import lib.graphs.LinkedGraph;

public class BitGraphTest {

/*

5 6
0 1
0 2
1 2
2 3
2 4
3 4


 */
	
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		int n = reader.nextInt();
		int m = reader.nextInt();
		Graph g = new LinkedGraph(n);
		for(int i = 0; i < m; i++) {
			int x = reader.nextInt();
			int y = reader.nextInt();
			g.connect(x, y);
		}
		System.out.println(g);
		System.out.println();
		for(int x = 0; x < g.V(); x++) {
			System.out.println(Arrays.toString(g.outNeighbors(x)));
		}
		System.out.println();
		for(int x = 0; x < g.V(); x++) {
			System.out.println(Arrays.toString(g.inNeighbors(x)));
		}
		reader.close();
	}
	
}
