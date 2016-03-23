package lib.util;

public class Grids {

	public static int posToIndex(int ncol, int row, int col) {
		return row * ncol + col;
	}
	
	public static int[] indexToPos(int nrow, int ncol, int index) {
		int row = index / ncol;
		int col = index % ncol;
		return new int[] {row, col};
	}
	
	public static boolean inBounds(int nrow, int ncol, int row, int col) {
		return 0 <= row && row < nrow && 0 <= col && col < ncol; 
	}
	
}
