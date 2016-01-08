import java.util.Arrays;
import java.util.LinkedList;

import lib.util.ArraysExt;


public class TestCdf {

	public static void main(String[] args) {
		int[] a = new int[] {1, 2, 3, 4};
		LinkedList<int[]> freq = ArraysExt.frequency(a);
		for(int[] x : freq) System.out.println(Arrays.toString(x));
		System.out.println("#####");
		LinkedList<double[]> cdf = ArraysExt.cdfPercent(a);
		for(double[] x : cdf) System.out.println(Arrays.toString(x));
		
	}
	
}
