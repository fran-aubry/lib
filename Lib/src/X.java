import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Random;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeSet;
import java.util.ArrayList;
/*******************************
* Main
*******************************/
/*
5 3
1 2 3 4 5
3 2
4 78 9
 */
public class X {
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		while(reader.hasNextInt()) {
			int n = reader.nextInt();
			int k = reader.nextInt();
			int[] a = new int[n];
			for(int i = 0; i < n; i++) {
				a[i] = reader.nextInt();
			}
			int val = Partition.partition(a, k);
			System.out.println(val);
		}
		reader.close();
	}
	/*******************************
	* Partition
	*******************************/
	public static class Partition {
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
	/*******************************
	* ArraysExt
	*******************************/
	public static class ArraysExt {
		// expected O(n), n = length a
		public static boolean alldiff(int[] a) {
			HashSet<Integer> H = new HashSet<>();
			for(int x : a) H.add(x);
			return H.size() == a.length;
		}
		// O(n + ub), n = length a
		public static boolean alldiff(int[] a, int ub) {
			int[] cnt = new int[ub + 1];
			for(int x : a) {
				assert x <= ub;
				cnt[x]++;
				if(cnt[x] > 1) return false;
			}
			return true;
		}
		// O(n + ub), n = length a
		private static int[] freq(int[] a, int ub) {
			int[] freq = new int[ub + 1];
			for(int x : a) {
				assert x <= ub;
				freq[x]++;
			}
			return freq;
		}
		// O(n + max a), n = length a
		public static int[] freq(int[] a) {
			return freq(a, max(a));
		}
		// O(1)
		public static boolean alldiff(int x, int y, int z) {
			return x != y && x != z && y != z; 
		}
		// O(n), n = length a
		public static int sum(int[] a) {
			int sum = 0;
			for(int i = 0; i < a.length; i++) {
				sum += a[i];
			}
			return sum;
		}
		// O(n), n = length a
		public static int max(int[] a) {
			int max = a[0];
			for(int i = 1; i < a.length; i++) {
				max = Math.max(max, a[i]);
			}
			return max;
		}
		// O(n), n = length a
		public static double max(double[] a) {
			double max = a[0];
			for(int i = 1; i < a.length; i++) {
				max = Math.max(max, a[i]);
			}
			return max;
		}
		// O(n), n = length a
		public static int min(int[] a) {
			int min = a[0];
			for(int i = 1; i < a.length; i++) {
				min = Math.min(min, a[i]);
			}
			return min;
		}
		// O(n), n = length a
		public static double min(double[] a) {
			double min = a[0];
			for(int i = 1; i < a.length; i++) {
				min = Math.min(min, a[i]);
			}
			return min;
		}
		// O(n), n = length a
		public static List<Integer> argmaxs(double[] a) {
			double max = max(a);
			List<Integer> L = new LinkedList<>();
			for(int i = 0; i < a.length; i++) {
				if(a[i] == max) L.add(i);
			}
			return L;
		}
		// O(n), n = length a
		public static List<Integer> argmaxs(int[] a) {
			int max = max(a);
			List<Integer> L = new LinkedList<>();
			for(int i = 0; i < a.length; i++) {
				if(a[i] == max) L.add(i);
			}
			return L;
		}
		// O(n), n = length a
		public static int argmax(int[] a) {
			int max = a[0];
			int k = 0;
			for(int i = 1; i < a.length; i++) {
				if(a[i] > max) {
					max = a[i];
					k = i;
				}
			}
			return k;
		}
		// O(n), n = length a
		public static int argmax(double[] a) {
			double max = a[0];
			int k = 0;
			for(int i = 1; i < a.length; i++) {
				if(a[i] > max) {
					max = a[i];
					k = i;
				}
			}
			return k;
		}
		// O(n), n = length a
		public static int argmin(int[] a) {
			int min = a[0];
			int k = 0;
			for(int i = 1; i < a.length; i++) {
				if(a[i] < min) {
					min = a[i];
					k = i;
				}
			}
			return k;
		}
		/*
		 * Finds i and j such that:
		 * i < j
		 * a[i] = a[j]
		 * |i - j| is minimal
		 * 
		 * expected O(n)
		 */
		public static int[] closestsPair(int[] a) {
			HashMap<Integer, Integer> last = new HashMap<>();
			int min = Integer.MAX_VALUE;
			int im = -1, jm = -1;
			for(int i = 0; i < a.length; i++) {
				Integer l = last.get(a[i]);
				if(l != null) {
					if(i - l < min) {
						min = i - l;
						im = l;
						jm = i;
					}
				}
				last.put(a[i], i);
			}
			if(min == Integer.MAX_VALUE) return null;
			return new int[] {im, jm};
		}
		/*
		 * Finds i and j such that:
		 * i < j
		 * a[i] = a[j]
		 * |i - j| is minimal
		 * 
		 * O(n + ub)
		 */
		public static int[] closestsPair(int[] a, int ub) {
			int[] last = new int[ub + 1];
			Arrays.fill(last, -1);
			int min = Integer.MAX_VALUE;
			int im = -1, jm = -1;
			for(int i = 0; i < a.length; i++) {
				assert a[i] <= ub;
				if(last[a[i]] != -1) {
					if(i - last[a[i]] < min) {
						min = i - last[a[i]];
						im = last[a[i]];
						jm = i;
					}
				}
				last[a[i]] = i;
			}
			if(min == Integer.MAX_VALUE) return null;
			return new int[] {im, jm};
		}
		// O(n)
		public static int indexOf(int[] a, int v) {
			for(int i = 0; i < a.length; i++)
				if(a[i] == v) return i;
			return -1;
		}
		/*
		 * Generate a random array with elements in [min, max]
		 * using the given seed. If 0 is given then no seed is used.
		 * 
		 * If alldiff is true then all the elements in the array will
		 * be distinct.
		 */
		public static int[] generateRandomArray(int size, boolean alldiff, int min, int max, int seed) {
			int[] a = new int[size];
			Random rnd = seed == 0 ? new Random() : new Random(seed);
			HashSet<Integer> S = new HashSet<>();
			assert size >= max - min + 1;
			S = new HashSet<Integer>();
			for(int i = 0; i < size; i++) {
				int x = rnd.nextInt(max + 1) + min;
				if(alldiff) {
					while(S.contains(x)) x = rnd.nextInt(max + 1) + min;
					S.add(x);
				}
				a[i] = x;
			}
			return a;
		}
		// generateRandomArray with seed = 0
		public static int[] generateRandomArray(int size, boolean alldiff, int min, int max) {
			return generateRandomArray(size, alldiff, min, max, 0);
		}
		/*
		 * shuffle subrange of array from i1 to i2
		 * O(i2 - i1)
		 */
		public static void shuffle(int[] a, int i1, int i2) {
			Random rnd = new Random();
			for(int i = i2; i > i1; i--){
				int index = rnd.nextInt(i + 1);
				int tmp = a[index];
				a[index] = a[i];
				a[i] = tmp;
			}
		}
		// O(n), n = length a
		public static void shuffle(int[] a) {
			shuffle(a, 0, a.length - 1);
		}
		/*
		 * builds an array nl of the same size such that
		 * nl[i] = smallest j > i such that nl[j] > nl[j]
		 * 
		 * O(n log(n))
		 */
		public static int[] nextLargerId(int[] a) {
			Stack<ValIdPair> S = new Stack<>();
			int[] nl = new int[a.length];
			for(int i = 0; i < a.length; i++) {
				nl[i] = -1;
				while(!S.isEmpty() && S.peek().val < a[i]) nl[S.pop().id] = i;
				S.push(new ValIdPair(i, a[i]));
			}
			return nl;
		}
		// auxiliary static class for nextLagerId
		private static class ValIdPair implements Comparable<ValIdPair> {
			public int id, val;
			public ValIdPair(int id, int val) {
				this.id = id;
				this.val = val;
			}
			public int compareTo(ValIdPair o) {
				return val - o.val;
			}
			public String toString() {
				return String.format("(%d, %d)", id, val);
			}
		}
		// accumulated sum, O(n)
		public static int[] acSum(int[] a) {
			int[] acs = new int[a.length];
			acs[0] = a[0];
			for(int i = 1; i < a.length; i++) {
				acs[i] = acs[i - 1] + a[i];
			}
			return acs;
		}
		// accumulated products, O(n)
		public static long[] acMul(int[] a) {
			long[] acm = new long[a.length];
			acm[0] = a[0];
			for(int i = 1; i < a.length; i++) {
				acm[i] = acm[i - 1] * a[i];
			}
			return acm;
		}
		// accumulated products, O(n)
		public static double[] acMul(double[] a) {
			double[] acm = new double[a.length];
			acm[0] = a[0];
			for(int i = 1; i < a.length; i++) {
				acm[i] = acm[i - 1] * a[i];
			}
			return acm;
		}
		// O(n)
		public static long[] copy(long[] a) {
			long[] c = new long[a.length];
			for(int i = 0; i < a.length; i++) {
				c[i] = a[i];
			}
			return c;
		}
		// O(n)
		public static int[] copy(int[] a) {
			int[] c = new int[a.length];
			for(int i = 0; i < a.length; i++) {
				c[i] = a[i];
			}
			return c;
		}
		// O(n)
		public static String boolArrayToBinStr(boolean[] a) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < a.length; i++) {
				sb.append(a[i] ? 1 : 0);
			}
			return sb.toString();
		}
		// O(n)
		public static int[] identity(int n) {
			int[] a = new int[n];
			for(int i = 0; i < n; i++) {
				a[i] = i;
			}
			return a;
		}
		// O(n)
		public static Integer[] identity2(int n) {
			Integer[] a = new Integer[n];
			for(int i = 0; i < n; i++) {
				a[i] = i;
			}
			return a;
		}
		// O(n log(n))
		public static void sortByValue(Integer[] a, double[] value) {
			Arrays.sort(a, new ValCmp(value));
		}
		private static class ValCmp implements Comparator<Integer> {
			private double[] value;
			public ValCmp(double[] value) {
				this.value = value;
			}
			public int compare(Integer o1, Integer o2) {
				return Double.compare(value[o1], value[o2]);
			}
		}
		// O(n)
		public static double minX(Point[] P) {
			double min = Double.POSITIVE_INFINITY;
			for(Point p : P) min = Math.min(min, p.x);
			return min;
		}
		// O(n)
		public static double minY(Point[] P) {
			double min = Double.POSITIVE_INFINITY;
			for(Point p : P) min = Math.min(min, p.y);
			return min;
		}
		// O(n)
		public static double maxX(Point[] P) {
			double max = Double.NEGATIVE_INFINITY;
			for(Point p : P) max = Math.max(max, p.x);
			return max;
		}
		// O(n)
		public static double maxY(Point[] P) {
			double max = Double.NEGATIVE_INFINITY;
			for(Point p : P) max = Math.max(max, p.y);
			return max;
		}
		// O(n)
		public static HashSet<Integer> toSet(int[] a) {
			HashSet<Integer> S = new HashSet<>();
			for(int x : a) S.add(x);
			return S;
		}
		// O(n log(n))
		public static LinkedList<int[]> frequency(int[] a) {
			Arrays.sort(a);
			int j = 0;
			LinkedList<int[]> f = new LinkedList<>();
			for(int i = 1; i < a.length; i++) {
				if(a[i] != a[j]) {
					f.add(new int[] {a[j], i - j});
					j = i;
				}
			}
			f.add(new int[] {a[a.length - 1], a.length - j});
			return f;
		}
		public static LinkedList<double[]> cdf(double[] a) {
			Arrays.sort(a);
			LinkedList<double[]> cdf = new LinkedList<>();
			for(int i = 0; i < a.length; i++) {
				if(i == a.length - 1 || a[i] != a[i + 1]) {
					cdf.add(new double[] {a[i], (i + 1.0) / a.length});				
				}
			}
			return cdf;
		}
		public static LinkedList<double[]> cdf(double[] a, int step) {
			Arrays.sort(a);
			LinkedList<double[]> cdf = new LinkedList<>();
			double dx = a[a.length - 1] / step;
			double x = dx;
			for(int i = 0; i < a.length; i++) {
				if(a[i] >= x) {
					cdf.add(new double[] {x, (double)i / a.length});
					x += dx;
				}
			}
			cdf.add(new double[] {a[a.length - 1], 1});
			return cdf;
		}
		public static LinkedList<int[]> cdf(int[] a) {
			Arrays.sort(a);
			int j = 0;
			LinkedList<int[]> cdf = new LinkedList<>();
			for(int i = 1; i < a.length; i++) {
				if(a[i] != a[j]) {
					cdf.add(new int[] {a[j], i});
					j = i;
				}
			}
			cdf.add(new int[] {a[a.length - 1], a.length});
			return cdf;
		}
		public static LinkedList<double[]> cdfPercent(int[] a) {
			Arrays.sort(a);
			int j = 0;
			LinkedList<double[]> cdf = new LinkedList<>();
			for(int i = 1; i < a.length; i++) {
				if(a[i] != a[j]) {
					cdf.add(new double[] {a[j], (double)i / a.length});
					j = i;
				}
			}
			cdf.add(new double[] {a[a.length - 1], 1});
			return cdf;
		}
		public static boolean and(boolean[] b) {
			for(int i = 0; i < b.length; i++) 
				if(!b[i]) return false;
			return true;
		}
		public static boolean or(boolean[] b) {
			for(int i = 0; i < b.length; i++) 
				if(b[i]) return true;
			return false;
		}
		public static double max(double[][] m) {
			double max = Double.NEGATIVE_INFINITY;
			for(int i = 0; i < m.length; i++) {
				for(int j = 0; j < m[i].length; j++) {
					max = Math.max(max, m[i][j]);
				}
			}
			return max;
		}
		public static String matrixToStr(double[][] m, int precision, String colSep, String rowSep) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < m.length; i++) {
				for(int j = 0; j < m[i].length; j++) {
					if(precision == 0) {
						sb.append((int)m[i][j]);
					} else {
						sb.append(String.format("%." + precision + "f", m[i][j]));					
					}
					if(j < m[i].length - 1) {
						sb.append(colSep);
					}
				}
				sb.append(rowSep);
			}
			return sb.toString();
		}
		public static String matrixToStr(double[][] m) {
			return matrixToStr(m, 3, "\t", "\n");
		}
		/*
		 * Reduce all values to the range [0, len(a) - 1]
		 * while preserving order
		 */
		public static int[] normalize(int[] a) {
			TreeSet<Integer> T = new TreeSet<>();
			for(int x : a) T.add(x);
			int i = 0;
			TreeMap<Integer, Integer> M = new TreeMap<>();
			for(int x : T) {
				M.put(x, i++);
			}
			int[] b = new int[a.length];
			for(i = 0; i < a.length; i++) {
				b[i] = M.get(a[i]);
			}
			return b;
		}
		public static TreeMap<Integer, Integer> normalization(int[] a) {
			TreeSet<Integer> T = new TreeSet<>();
			for(int x : a) T.add(x);
			int i = 0;
			TreeMap<Integer, Integer> M = new TreeMap<>();
			for(int x : T) {
				M.put(x, i++);
			}
			return M;
		}
		public static class Normalization {
			public TreeMap<Integer, Integer> N;
			public int[] Ni;
			public int[] an;
			public Normalization(int[] a) {
				TreeSet<Integer> T = new TreeSet<>();
				for(int x : a) T.add(x);
				int i = 0;
				N = new TreeMap<>();
				Ni = new int[T.size()];
				for(int x : T) {
					Ni[i] = x;
					N.put(x, i++);
				}
				an = new int[a.length];
				for(i = 0; i < a.length; i++) {
					an[i] = N.get(a[i]);
				}
			}
		}
		// O(n log(n))
		public static int[] LIS(int[] a) {
			Normalization norm = new Normalization(a);
			LisTree T = new LisTree(a.length);
			for(int v : norm.an) {
				T.add(v);
			}
			int[] lis = T.lis();
			int[] res = new int[lis.length];
			for(int i = 0; i < lis.length; i++) {
				res[i] = norm.Ni[lis[i]];
			}
			return res;
		}
	}
	/*******************************
	* LisTree
	*******************************/
	public static class LisTree extends SegmentTree<LisData> {
		private int index;
		public LisTree(int n) {
			super(n);
			index = 0;
		}
		public void add(int v) {
			if(v == 0) {
				set(v, new LisData(1, v, index, null));
			} else {
				LisData x = getValue(0, v - 1);
				set(v, new LisData((x == null ? 0 : x.len) + 1, v, index, x));
			}
			index++;
		}
		public int lisSize() {
			return t.get(0).len;
		}
		public int[] lis() {
			int[] lis = new int[lisSize()];
			LisData cur = t.get(0);
			for(int i = lis.length - 1; i >= 0; i--) {
				lis[i] = cur.cur;
				cur = cur.prev;
			}
			return lis;
		}
		protected LisData f(LisData left, LisData right) {
			if(left == null) return right;
			if(right == null) return left;
			if(left.len > right.len) return left;
			if(left.len < right.len) return right;
			if(left.index > right.index) return left;
			return right;
		}
	}
	/*******************************
	* Point
	*******************************/
	public static class Point {
		public double x, y;
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
		public Point(int[] c) {
			this.x = c[0];
			this.y = c[1];
		}
		public int xi() {
			return (int)x;
		}
		public int yi() {
			return (int)y;
		}
		public String toString() {
			return String.format("(%.3f, %.3f)", x, y);
		}
	}
	/*******************************
	* LisData
	*******************************/
	public static class LisData {
		int len, cur, index;
		LisData prev;
		public LisData(int len, int cur, int index, LisData prev) {
			this.len = len;
			this.cur = cur;
			this.index = index;
			this.prev = prev;
		}
		public String toString() {
			return String.format("(%d, %d, %d, %s)", len, cur, index, prev == null ? "-1" : prev.cur);
		}
	}
	/*******************************
	* SegmentTree
	*******************************/
	public abstract static class SegmentTree<E> {
		protected int n;
		protected ArrayList<E> t;
		public SegmentTree(int n) {
			this.n = n;
			t = new ArrayList<>(4 * n - 1);
			for(int i = 0; i < 4 * n - 1; i++) {
				t.add(null);
			}
		}
		public int size() {
			return n;
		}
		public void set(int i, E val) {
			set(0, n - 1, i, val, 0);
		}
		/**
		 * O(log(n))
		 */
		private void set(int l, int r, int i, E val, int node) {
			if(l == r) {
				t.set(node, val);
			} else {
				int m = (l + r) / 2;
				if(i <= m) {
					set(l, m, i, val, left(node));
				} else {
					set(m + 1, r, i, val, right(node));
				}
				int left = left(node);
				int right = right(node);
				t.set(node, f(t.get(left), t.get(right)));
			}
		}
		public E getValue(int i, int j) {
			return getValue(0, n - 1, i, j, 0);
		}
		/**
		 * O(log(n))
		 */
		private E getValue(int l, int r, int i, int j, int node) {
			if(l == i && r == j) {
				return t.get(node);
			}
			int m = (l + r) / 2;
			if(j <= m) {
				return getValue(l, m, i, j, left(node));
			} else if(i > m) {
				return getValue(m + 1, r, i, j, right(node));
			}
			return f(getValue(l, m, i, m, left(node)), getValue(m + 1, r, m + 1, j, right(node)));
		}
		protected int left(int node) {
			return (node << 1) + 1;
		}
		protected int right(int node) {
			return (node << 1) + 2;
		}
		protected abstract E f(E left, E right);
		/**
		 * @time O(n log(n))
		 */
		public String toString() {
			StringBuilder sb = new StringBuilder();
			toString(0, 0, n - 1, "", sb);
			return sb.toString().trim();
		}
		private void toString(int node, int i, int j, String pre, StringBuilder sb) {
			sb.append(pre);
			sb.append(String.format("[%d, %d]:", i, j));
			sb.append("\t");
			sb.append(t.get(node));
			sb.append("\n");
			if(i != j) {
				int m = (i + j) / 2;
				toString(2 * node + 1, i, m, pre + "\t", sb);
				toString(2 * node + 2, m + 1, j, pre + "\t", sb);
			}
		}
	}
}
