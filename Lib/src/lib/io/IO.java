package lib.io;

import java.util.Scanner;

public class IO {

	public static Scanner reader;
	
	public static void setScanner(Scanner r) {
		reader = r;
	}
	
	public static int readInt() {
		return reader.nextInt();
	}
	
	public static int[] readIntArray() {
		int n = reader.nextInt();
		int[] a = new int[n];
		for(int i = 0; i < n; i++) {
			a[i] = reader.nextInt();
		}
		return a;
	}
	
}
