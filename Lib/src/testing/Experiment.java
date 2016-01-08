package testing;

import java.util.Arrays;

public class Experiment {

	public static void main(String[] args) {
		int n = 4;
		cnt = 0;
		count(new int[n][n], 0, 1);
		System.out.println(cnt);
		System.out.println(total);
		System.out.println(cnt / (double)total);
	}
	
	static boolean balanced(int[][] m) {
		int n = m.length;
		for(int i = 0; i < n; i++) {
			for(int j = i + 1; j < n; j++) {
				for(int k = j + 1; k < n; k++) {
					if(m[i][j] + m[j][k] + m[k][i] <= 1) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	static int cnt = 0;
	static int total = 0;
	
	static void count(int[][] m, int i, int j) {
		int n = m.length;
		if(j == n) {
			// fix indexes
			i++;
			j = i + 1;
		}
		if(i == n - 1) {
			// over
			boolean ok = true;
			for(int i1 = 0; i1 < n; i1++) {
				for(int i2 = i1 + 1; i2 < n; i2++) {
					for(int i3 = i2 + 1; i3 < n; i3++) {
						int sum = m[i1][i2] + m[i2][i3] + m[i3][i1];
						if(sum == 0 || sum == 2) {
							ok = false;
						}
					}
				}
			}
			if(ok) {
				cnt++;
			}
			total++;
		} else {	
			m[i][j] = 1;
			m[j][i] = 1;
			count(m, i, j + 1);
			
			m[i][j] = 0;
			m[j][i] = 0;
			count(m, i, j + 1);
		}
	}
		
}
