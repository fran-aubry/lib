package lib.bIts;

public class Bits {

	public static int swap(int x, int i, int j) {
		if(get(x, i) == get(x, j)) return x;
		x = set(x, i, 1 - get(x, i));
		x = set(x, j, 1 - get(x, j));
		return x;
	}

	public static int set(int x, int i, int val) {
		return val == 1 ? x | (1 << i) : x & ~(1 << i);
	}

	public static int get(int x, int i) {
		return (x >> i) & 1;
	}
	
	public static int mostSignificant(int x) {
		int k = 0;
		while(x > 0) {
			x >>= 1;
			k++;
		}
		return k - 1;
	}

	public static String bin(int x, int n) {
		StringBuilder sb = new StringBuilder(Integer.toBinaryString(x));
		while(sb.toString().length() < n) {
			sb.append('0');
		}
		return sb.reverse().toString();
	}
	
}
