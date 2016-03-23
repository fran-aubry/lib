package lib.graphs.algorithms;

import java.util.LinkedList;
import java.util.Queue;

import lib.data.IntPair;

public class FloodFill {

	public static int[][] dir = new int[][] {
		new int[] {-1, 0},
		new int[] {1, 0},
		new int[] {0, -1},
		new int[] {0, 1}
	};
	
	public static LinkedList<IntPair> visit(char[][] map, boolean[][] vis, int r, int c, boolean rowWarp, boolean colWarp) {
		int n = map.length;
		int m = map[0].length;
		Queue<IntPair> Q = new LinkedList<>();
		IntPair start = new IntPair(r, c);
		Q.add(start);
		vis[r][c] = true;
		LinkedList<IntPair> positions = new LinkedList<>();
		positions.add(start);
		while(!Q.isEmpty()) {
			IntPair cur = Q.poll();
			for(int[] d : dir) {
				int i = cur.x() + d[0];
				if(rowWarp) i = (i + n) % n;
				int j = cur.y() + d[1];
				if(colWarp) j = (j + m) % m;
				if(0 <= i && i < n && 0 <= j && j < m && map[i][j] == map[r][c] && !vis[i][j]) {
					Q.add(new IntPair(i, j));
					vis[i][j] = true;
					positions.add(new IntPair(i, j));
				}
			}
		}
		return positions;
	}
	
}
