package lib.data;

import java.util.Comparator;

public class IntPair implements Comparable<IntPair> {

	public long xy;
	
	public IntPair(int x, int y) {
		this.xy = getXY(x, y);
	}
	
	/*
	 * Compose two int's into a long by
	 * concatenating they bits.
	 * O[1]
	 */
	private long getXY(int x, int y) {
		return ((long)x << 31) | y;
	}
	
	public int x() {
		return x(xy);
	}

	public int y() {
		return y(xy);
	}
	
	/*
	 * Get the x from a long x|y (first half of the bits).
	 * O[1]
	 */
	private int x(long xy) {
		return (int)(xy >> 31);
	}
	
	/*
	 * Get the y from a long x|y (second half of the bits).
	 * O[1]
	 */
	private int y(long xy) {
		return (int)(xy & 0x7fffffff);
	}

	
	public int hashCode() {
		return Long.hashCode(xy);
	}
	
	public boolean equals(Object other) {
		if(other instanceof IntPair) {
			IntPair o = (IntPair)other;
			return xy == o.xy;
		}
		return false;
	}
	
	public String toString() {
		return String.format("(%d, %d)", x(), y());
	}

	public int compareTo(IntPair o) {
		int x1 = this.x();
		int x2 = o.x();
		if(x1 == x2) {
			int y1 = this.y();
			int y2 = o.y();
			return y1 - y2;
		}
		return x1 - x2;
	}
	
	public static class XFirstCmp implements Comparator<IntPair> {

		public int compare(IntPair o1, IntPair o2) {
			if(o1.x() == o2.x()) {
				return o1.y() - o2.y();
			}
			return o1.x() - o2.x();
		}
		
	}
	
	public static class XFirstRevCmp implements Comparator<IntPair> {

		public int compare(IntPair o1, IntPair o2) {
			if(o1.x() == o2.x()) {
				return -(o1.y() - o2.y());
			}
			return -(o1.x() - o2.x());
		}
		
	}

	
	public static class YFirstCmp implements Comparator<IntPair> {

		public int compare(IntPair o1, IntPair o2) {
			if(o1.y() == o2.y()) {
				return o1.x() - o2.x();
			}
			return o1.y() - o2.y();
		}
		
	}
	
	public static class YFirstRevCmp implements Comparator<IntPair> {

		public int compare(IntPair o1, IntPair o2) {
			if(o1.y() == o2.y()) {
				return -(o1.x() - o2.x());
			}
			return -(o1.y() - o2.y());
		}
		
	}
	
}
