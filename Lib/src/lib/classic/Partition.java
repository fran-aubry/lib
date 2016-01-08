package lib.classic;

import lib.util.ArraysExt;

public class Partition {
	
	public static int partition(int[] a, int k) {
		int max = ArraysExt.sum(a);
		int lb = ArraysExt.min(a);
		int ub = max;
		while(ub - lb > 1) {
			int mid = (lb + ub) / 2;
			if(canDo(a, k, mid)) {
				ub = mid;
			} else {
				lb = mid + 1;
			}
		}
		if(canDo(a, k, lb)) return lb;
		return ub;
	}
	
	// O(len(a))
	private static boolean canDo(int[] a, int k, int val) {
		int sum = 0;
		int p = 0;
		for(int i = 0; i < a.length; i++) {
			if(a[i] > val) return false;
			if(p >= k) return false;
			if(sum + a[i] > val) {
				p++;
				sum = 0;
			}
			sum += a[i];
		}
		return p < k;
	}

}
