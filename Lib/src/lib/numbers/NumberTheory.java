package lib.numbers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.TreeSet;

public class NumberTheory {

	public static HashSet<Integer> divisors(int x) {
		HashSet<Integer> div = new HashSet<>();
		for(int i = 1; i <= x / i + 1; i++) {
			if(x % i == 0) {
				div.add(i);
				div.add(x / i);
			}
		}
		return div;
	}
	
	public static int digitSum(int x) {
		int sum = 0;
		while(x > 0) {
			sum += x % 10;
			x /= 10;
		}
		return sum;
	}

	public static HashMap<Integer, LinkedList<Integer>> coprimes(int n) {
		HashMap<Integer, LinkedList<Integer>> M = new HashMap<>();
		for(int i = 1; i <= n; i++) {
			LinkedList<Integer> coprimes = new LinkedList<>();
			for(int j = 1; j <= n; j++) {
				if(Math2.gcd(i, j) == 1) {
					coprimes.add(j);
				}
			}
			M.put(i, coprimes);
		}
		return M;
	}
	
	// O(n log(log(n)))
	public static boolean[] sieve(int n) {
		boolean[] prime = new boolean[n + 1];
		for(int i = 2; i <= n; i++) prime[i] = true;
		for(int i = 2; i * i <= n; i++)      {      
			if(prime[i]) { 
				/*
				 * we can start at i * i because
				 * all multiples of i of the for i * k
				 * with k < i have already been crossed.
				 */
				for(int j = i; j * i <= n; j++) {
					prime[i * j] = false; 
				}
			}
		}
		return prime;
	}
	
	/*
	 * O(n log(log(n)))
	 * 
	 * Counts the number of distinct prime factors.
	 */

	public static int[] sieveCountDistinctPrimeFactors(int n) {
		int[] dpf = new int[n + 1];
		for(int i = 2; i <= n; i++) {      
			if(dpf[i] == 0) { 
				for(int j = i; j <= n; j += i) {
					dpf[j]++; 
				}
			}
		}
		return dpf;
	}
	
	/*
	 * O(n log(log(n)))
	 * 
     * Compute mu(i) for all 1 <= i <= n 
	 */
	public static int[] sieveMobious(int n) {
		int[] mu = new int[n + 1];
		Arrays.fill(mu, -2);
		mu[1] = 1;
		for(int i = 2; i <= n; i++) {
			if(mu[i] == -2) {
				mu[i] = 1;
				for(int j = 2 * i; j <= n; j += i) {
					if(mu[j] == 0) continue;
					if(mu[j] == -2) mu[j] = 0;
					if((j / i) % i == 0) {
						mu[j] = 0;
					} else {
						mu[j]++;
					}
				}
			}
		}
		for(int i = 2; i <= n; i++) {
			if(mu[i] != 0) {
				mu[i] = mu[i] % 2 == 0 ? 1 : -1;
			}
		}
		return mu;
	}

	/*
	 * O(n), given mu
	 * 
     * Compute M(i) for all 1 <= i <= n with
     * M(i) = mu(1) + ... + mu(n)  
	 */
	public static int[] allMertens(int[] mu) {
		int[] M = new int[mu.length];
		M[1] = mu[1];
		for(int i = 2; i < mu.length; i++) {
			M[i] = mu[i] + M[i - 1];
		}
		return M;
	}
	
	/*
	 * Compute phi(i) for each 1 <= i <= n
	 */
	public static int[] sieveTotient(int n) {
	    int[] phi = new int[n + 1];
	    for(int i = 1; i <= n; i++) {
	    	phi[i] = i;
	    }
	    phi[1] = 1;
		for(int i = 2; i <= n; i++) {      
			if(phi[i] == i) {
				// i is a prime, phi[i] = i - 1
				phi[i]--;
				for(int j = 2 * i; j <= n; j += i) {
					phi[j] -= phi[j] / i; 
				}
			}
		}
		return phi;
	}
	
	/*
	 * Compute phi(i) for each 1 <= i <= n
	 */
	public static int[] sieveSumOfFactors(int n) {
	    int[] sumf = new int[n + 1];
	    Arrays.fill(sumf, 1);
	    sumf[1] = 1;
	    for(int i = 2; i <= n; i++) {      
			if(sumf[i] == 1) {
				// i is prime, sumf[i] = i + 1
				sumf[i] += i;
				for(int j = 2 * i; j <= n; j += i) {
					int mul = 1;
					int sum = 0;
					while(j % mul == 0) {
						sum += mul;
						mul *= i;
					}
					sumf[j] *= sum;
				}
			}
		}
		return sumf;
	}
	
	/*
	 * Compute depthphi(i) for each 1 <= i <= n
	 * 
	 *  depthphi(i) is the number of iterations requiered to reach 1 in
	 *  the sequence phi(i), phi(phi(i)), ...
	 */
	public static int[] allDepthphi(int[] phi) {
		int[] depthphi = new int[phi.length];
		depthphi[1] = 1;
		depthphi[2] = 1;
		for(int i = 3; i < phi.length; i++) {
			depthphi[i] = 1 + depthphi[phi[i]];
		}
		return depthphi;
	}
	
	public static int[] sieve2(int n) {
		int[] div = new int[n + 1];
		for(int i = 2; i <= n; i++) div[i] = i;
		for(int i = 2; i * i <= n; i++)      {      
			if(div[i] == i) {
				/*
				 * we can start at i * i because
				 * all multiples of i of the for i * k
				 * with k < i have already been crossed.
				 */
				for(int j = i; j * i <= n; j++) {
					div[i * j] = i; 
				}
			}
		}
		return div;
	}

	// factor n given the result of sieve2. div[i] must be any prime factor of i
	public static LinkedList<Integer> factor(int n, int[] div) {
		LinkedList<Integer> f = new LinkedList<>();
		while(n > 1) {
			int p = div[n];
			while(n % p == 0) {
				f.add(p);		
				n /= p;
			}

		}
		return f;
	}

	// factor n given the result of sieve2. div[i] must be any prime factor of i
	public static HashMap<Integer, Integer> factorMultiplicity(int n, int[] div) {
		HashMap<Integer, Integer> f = new HashMap<>();
		while(n > 1) {
			int p = div[n];
			int e = 0;
			while(n % p == 0) {
				e++;
				n /= p;
			}
			f.put(p, e);
		}
		return f;
	}

	// factor n given the result of sieve2. div[i] must be any prime factor of i
	public static LinkedList<Integer> factorSet(int n, int[] div) {
		LinkedList<Integer> f = new LinkedList<>();
		while(n > 1) {
			int p = div[n];
			if(n % p == 0) {
				f.add(p);		
				n /= p;
			}
			while(n % p == 0) n /= p;
		}
		return f;
	}

	// compute phi(n) given the result of sieve2. div[i] must be any prime factor of i
	public static int phi(int n, int[] div) {
		int phi = n;
		while(n > 1) {
			int p = div[n];
			phi -= phi / p;
			while(n % p == 0) {
				n /= p;				
			}
		}
		return phi;
	}

	public static int phi(int n, LinkedList<Integer> P) {
		int phi = n;
		for(int p : P) {
			if(n % p == 0) {
				phi -= phi / p;
			}
			while(n % p == 0) {
				n /= p;				
			}
			if(n == 1) break;
		}
		return phi;
	}


	// O(n log(log(n)))
	public static LinkedList<Integer> sieveToLinkedList(int n) {
		boolean[] prime = sieve(n);
		LinkedList<Integer> P = new LinkedList<>();
		for(int i = 2; i <= n; i++) {
			if(prime[i]) P.add(i);
		}
		return P;
	}

	// O(n log(log(n)))
	public static ArrayList<Integer> sieveToArrayList(int n) {
		boolean[] prime = sieve(n);
		ArrayList<Integer> P = new ArrayList<>();
		for(int i = 2; i <= n; i++) {
			if(prime[i]) P.add(i);
		}
		return P;
	}

	// O(n log(log(n)))
	public static HashSet<Integer> sieveToHashSet(int n) {
		boolean[] prime = sieve(n);
		HashSet<Integer> P = new HashSet<>();
		for(int i = 2; i <= n; i++) {
			if(prime[i]) P.add(i);
		}
		return P;
	}

	// O(n log(log(n)))
	public static TreeSet<Integer> sieveToTreeSet(int n) {
		boolean[] prime = sieve(n);
		TreeSet<Integer> P = new TreeSet<>();
		for(int i = 2; i <= n; i++) {
			if(prime[i]) P.add(i);
		}
		return P;
	}

	public static LinkedList<Integer> factor(int x, LinkedList<Integer> P) {
		int sq = (int)Math.sqrt(x);
		LinkedList<Integer> F = new LinkedList<>();
		for(int p : P) {
			if(x == 1 || p > sq) break;
			while(x % p == 0) {
				F.add(p);
				x /= p;
			}
		}
		if(x > 1) F.add(x);
		return F;
	}

	public static HashMap<Integer, Integer> factorMultiplicity(int x, LinkedList<Integer> P) {
		int sq = (int)Math.sqrt(x);
		HashMap<Integer, Integer> f = new HashMap<>();
		for(int p : P) {
			if(x == 1 || p > sq) break;
			int e = 0;
			while(x % p == 0) {
				e++;
				x /= p;
			}
			if(e > 0) f.put(p, e);
		}
		if(x > 1) f.put(x, 1);
		return f;
	}

	// O(sqrt(x))
	public static LinkedList<Long> factor(long x) {     
		LinkedList<Long> F = new LinkedList<>();
		while(x % 2 == 0) {
			F.add(2l);
			x /= 2;
		}
		for(long i = 3; i * i <= x; i += 2) {            
			while(x % i == 0) {
				F.add(i);
				x /= i;            
			}          
		}          
		if(x > 1) F.add(x);          
		return F; 
	}

	// O(sqrt(x))
	public static HashMap<Integer, Integer> factorMultiplicity(int x) {     
		HashMap<Integer, Integer> F = new HashMap<>();
		int cnt = 0;
		while(x % 2 == 0) {
			cnt++;
			x /= 2;
		}
		if(cnt > 0) F.put(2, cnt);
		for(int i = 3; i * i <= x; i += 2) { 
			cnt = 0;
			while(x % i == 0) {
				cnt++;
				x /= i;            
			}
			if(cnt > 0) F.put(i, cnt);
		}          
		if(x > 1) F.put(x, 1);          
		return F;
	}

	// O(sqrt(x))
	public static HashMap<Long, Integer> factorMultiplicity2(long x) {     
		HashMap<Long, Integer> F = new HashMap<>();
		int cnt = 0;
		while(x % 2 == 0) {
			cnt++;
			x /= 2;
		}
		if(cnt > 0) F.put(2l, cnt);
		for(long i = 3; i * i <= x; i += 2) { 
			cnt = 0;
			while(x % i == 0) {
				cnt++;
				x /= i;            
			}
			if(cnt > 0) F.put(i, cnt);
		}          
		if(x > 1) F.put(x, 1);          
		return F;
	}

	public static HashMap<Long, Integer> factorMultiplicity(long x, LinkedList<Integer> P) {
		int sq = (int)Math.sqrt(x);
		HashMap<Long, Integer> f = new HashMap<>();
		for(int p : P) {
			if(x == 1 || p > sq) break;
			int e = 0;
			while(x % p == 0) {
				e++;
				x /= p;
			}
			if(e > 0) f.put((long)p, e);
		}
		if(x > 1) f.put(x, 1);
		return f;
	}
	
	public static int nbDivisors(long x) {
		HashMap<Long, Integer> F = factorMultiplicity2(x);
		int ndiv = 1;
		for(Entry<Long, Integer> entry : F.entrySet()) {
			int e = entry.getValue();
			ndiv *= (e + 1);
		}
		return ndiv;
	}
	
	public static int nbDivisors(long x, LinkedList<Integer> primes) {
		HashMap<Long, Integer> F = factorMultiplicity(x, primes);
		int ndiv = 1;
		for(Entry<Long, Integer> entry : F.entrySet()) {
			int e = entry.getValue();
			ndiv *= (e + 1);
		}
		return ndiv;
	}

	/*
	 *  check if the number has only binary digits, 0 and 1
	 *  O(d)
	 */
	public static boolean hasOnly01(int x) {
		String s = x + "";
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) != '0' && s.charAt(i) != '1') {
				return false;
			}
		}
		return true;
	}

	/*
	 * fi(n) = #{a in N | 1 <= a <= n and gcd(a, n) = 1}
	 * 
	 * Properties:
	 * (1.) fi(p) = p - 1
	 * (2.) if gcd(n, m) = 1 then fi(n * m) = fi(n) * fi(m)
	 * 
	 * (3.) if n = p1^e1 * p2^e2 * ... * pk^ek then 
	 * fi(n) =  n * (1 - 1/p1) * (1 - 1/p2) * ... * (1 - 1/pk)
	 * 
	 * O(sqrt(n)
	 */
	public static int phi(int n) {          
		int phi = n;         
		if(n % 2 == 0) phi -= phi / 2;            
		while(n % 2 == 0) n /= 2;
		for(int i = 3; i * i <= n; i += 2) {            
			if(n % i == 0) phi -= phi / i;            
			while(n % i == 0) n /= i;          
		}          
		if(n > 1) phi -= phi / n;          
		return phi;        
	}

	public static long[] firtKPrimes(int k) {
		long[] primes = new long[k];
		primes[0] = 2;
		int i = 1;
		long p = 3;
		while(i < k) {
			if(BigInteger.valueOf(p).isProbablePrime(80)) {
				primes[i++] = p;
			}
			p++;
		}
		return primes;
	}

	/*
	 * Compute the largest e such that p^e | n!
	 * 
	 * e = sum[k = 1 -> floor(log_p(N))] floor(N / p^k)
	 * 
	 */
	public static int getExponentInFactorial(int n, int p) {
		int e = 0;
		for(long power = p; power <= n; power *= p) {
			e += n / power;	    	
		}
		return e;
	}

	/*
	 * Find the minimum x > 0 such that lcm(a, x) = b
	 * 
	 * If no solution exists, -1 is returned
	 */
	public static int lcmEquation(int a, int b, LinkedList<Integer> P) {
		HashMap<Integer, Integer> fa = factorMultiplicity(a, P);
		HashMap<Integer, Integer> fb = factorMultiplicity(b, P);
		int x = b;
		for(Entry<Integer, Integer> entry : fa.entrySet()) {
			int p = entry.getKey();
			int e1 = entry.getValue();
			Integer e2 = fb.get(p);
			if(e2 == null || e1 > e2) return -1;
			if(e1 == e2) {
				for(int i = 0; i < e1; i++) {
					x /= p;
				}
			}
		}
		return x;
	}

	public static BigInteger[][] FIBM_BIG = new BigInteger[][] {
		new BigInteger[] {BigInteger.ONE, BigInteger.ONE},
		new BigInteger[] {BigInteger.ONE, BigInteger.ZERO}
	};

	public static int[][] FIBM_int = new int[][] {
		new int[] {1, 1},
		new int[] {1, 0}
	};

	public static long[][] FIBM_long = new long[][] {
		new long[] {1, 1},
		new long[] {1, 0}
	};

	/*
	 * fib(0) = 0
	 * fib(1) = 1    
	 *     
	 * 1 1^n  = fib(n + 1) fib(n)
	 * 1 0      fib(n)     fib(n - 1)
	 */
	public static BigInteger fib(int n) {
		if(n == 0) return BigInteger.ZERO;
		BigInteger[][] fib = Math2.pow(FIBM_BIG, n);
		return fib[0][1];
	}

	/*
	 * fib(0) = 0
	 * fib(1) = 1    
	 *     
	 * 1 1^n  = fib(n + 1) fib(n)
	 * 1 0      fib(n)     fib(n - 1)
	 */
	public static long fibMod(int n, int mod) {
		if(n == 0) return 0;
		long[][] fib = Math2.powMod(FIBM_long, n, mod);
		return fib[0][1];
	}

	/*
	 * f(0) = a
	 * f(1) = b
	 * f(n) = f(n - 1) + f(n - 2)
	 * 
	 * f(n) = fib(n - 1) * a + fib(n) * b
	 */
	public static long fibMod(int n, int a, int b, int mod) {
		if(n == 0) return a % mod;
		if(n == 1) return b % mod;
		long[][] fib = Math2.powMod(FIBM_long, n, mod);
		return (a * fib[1][1] % mod + b * fib[0][1] % mod) % mod;
	}

	/*
	 * f(0) = a
	 * f(1) = b
	 * f(n) = f(n - 1) + f(n - 2)
	 * 
	 * f(n) = fib(n - 1) * a + fib(n) * b
	 */
	public static BigInteger fib(int n, int a, int b) {
		if(n == 0) return BigInteger.valueOf(a);
		if(n == 1) return BigInteger.valueOf(b);
		BigInteger[][] fib = Math2.pow(FIBM_BIG, n);
		return BigInteger.valueOf(a).multiply(fib[1][1]).add(BigInteger.valueOf(b)).multiply(fib[0][1]);
	}

	/*
	 * Generate the first n numbers that are only divisible by primes in P in order.
	 * 
	 * O(n * P)
	 * 
	 * ugly numbers: P = 2,3,5
	 * humble numbers: P = 2,3,5,7
	 */
	public static long[] generateNumberWithGivenPrimesFactors(int n, int[] P) {
		long[] a = new long[n + 1];
		a[1] = 1;
		int[] index = new int[P.length];
		int i = 1;
		while(i < n) {
			for(int j = 0; j < P.length; j++) {
				while(index[j] <= n && a[index[j]] * P[j] <= a[i]) index[j]++;
			}
			long min = Long.MAX_VALUE;
			int jmin = -1;
			for(int j = 0; j < P.length; j++) {
				if(a[index[j]] * P[j] < min) {
					min = a[index[j]] * P[j];
					jmin = j;
				}
			}
			index[jmin]++;
			a[++i] = min;
		}
		return a;
	}

	/*
	 * Compute the largest d such that
	 * a[0] = a[1] = ... = a[n - 1] mod (d)
	 */
	public static long largestThatGivenSameRemainder(long[] a) {
		long[] d = new long[a.length - 1];
		for(int i = 1; i < a.length; i++) {
			d[i - 1] = a[i] - a[i - 1];
		}
		return Math2.gcd(d);
	}

	
	/**
	 * Bezout
	 */
	
	/*
	 * Compute a solution (x, y) to ax + by = c where gcd(a, b) = d | c
	 * 
	 * (a/d)x + (b/d)y = (c/d)
	 * (a/d)x = (c/d) mod (b/d)
	 * x = (a/d)^-1 (c/d) mod(b/d)
	 * 
	 */
	public static long[] initialSolution(long a, long b, long c) {
		long d = Math2.gcd(a, b);
		if(c % d != 0) return null;
		long mod = b / d;
		long x = modInverse(a / d, mod) * ((c / d) % mod) % mod;
		return new long[] {x, (c - a * x) / b};
	}
	
	/*
	 * Bezout identity
	 * 
	 * General solution of ax + by = c with gcd(a, b) = d | c
	 * 
	 * (x + k (b / d), y - k (a / d)) with k in Z
	 */
	
	/*
	 * Compute the interval for k that gives solutions
	 * with x >= 0, y >= 0 of
	 * 
	 * ax + by = c
	 */
	public static long[] getNonNegativeSolutions(long a, long b, long c) {
		long[] sol = initialSolution(a, b, c);
		if(sol == null) return null;
		long x = sol[0];
		long y = sol[1];
		long d = Math2.gcd(a, b);
		long lb = (long)Math.ceil(-(x * d) / (double)b);
		long ub = (long)Math.floor((y * d) / (double)a);
		if(lb > ub) return null;
		return new long[] {lb, ub};
	}
	
	public static long[] getSol(long a, long b, long c, long k) {
		long[] sol = initialSolution(a, b, c);
		if(sol == null) return null;
		long x = sol[0];
		long y = sol[1];
		long d = Math2.gcd(a, b);
		return new long[] {x + k * (b / d), y - k * (a / d)};
	}
	
	public static long modInverse(long x, long m) {
		return big(x).modInverse(big(m)).longValue();
	}
	
	public static BigInteger big(long x) {
		return BigInteger.valueOf(x);
	}
	
	private static long xeu, yeu;
	
	public static long[] extendedEuclid(long a, long b) {
		long d = extendedEuclidRec(a, b);
		return new long[] {xeu, yeu, d};
	}
	
	private static long extendedEuclidRec(long a, long b) {
	  if (b == 0) { xeu = 1; yeu = 0; return a; }
	  long d = extendedEuclidRec(b, a % b);
	  long x1 = yeu;
	  long y1 = xeu - (a / b) * yeu;
	  xeu = x1;
	  yeu = y1;
	  return d;
	}
	
	// Farey sequence
	
	/*
	 * Find the number of irreducible fractions p/q <= x
	 * with q <= n
	 * 
	 * O(n log(n))
	 */
	public static long fareyRank(int x, int n) {
		long[] A = new long[n + 1];
		for(int q = 1; q <= n; q++) {
			A[q] = (long)Math.floor(x * q);
		}
		long rank = 0;
		for(int q = 1; q <= n; q++) {
			for(int m = 2 * q; m <= n; m += q) {
				A[m] -= A[q];
			}
			rank += A[q];
		}
		return rank;
	}
	
	/*
	 * Find the number of irreducible fractions p/q <= f
	 * with q <= n
	 * 
	 * O(n log(n))
	 */
	public static long fareyRank(Fraction f, int n) {
		long[] A = new long[n + 1];
		for(int q = 1; q <= n; q++) {
			A[q] = (f.a * q) / f.b;
		}
		long rank = 0;
		for(int q = 1; q <= n; q++) {
			for(int m = 2 * q; m <= n; m += q) {
				A[m] -= A[q];
			}
			rank += A[q];
		}
		return rank;
	}

	/*
	 * O(n log(n)^2)
	 */
	public static Fraction getKthFarey(long k, int n) {
		// determine j such that the answers is in I(j) = [j/n, (j+1)/n[
		int lb = 1;
		int ub = n - 1;
		while(ub - lb > 1) {
			int mid = (lb + ub) / 2;
			long r = fareyRank(new Fraction(mid, n), n);
			if(r <= k) {
				lb = mid;
			} else {
				ub = mid - 1;
			}
		}
		int j = lb;
		if(fareyRank(new Fraction(ub, n), n) <= k) {
			j = ub;
		}
		// compute the factions in the interval I(j) (at most n exist)
		Fraction f1 = new Fraction(j, n);
		Fraction f2 = new Fraction(j + 1, n);
		TreeSet<Fraction> frac = new TreeSet<>(); 
		frac.add(f1);
		frac.add(f2);
		for(int q = 1; q < n; q++) {
			long a = ((j + 1) * q - 1) / n;
			Fraction f = new Fraction(a, q);
			if(f1.compareTo(f) <= 0 && f.compareTo(f2) < 0) {
				frac.add(f);
			}
		}
		long r = k - fareyRank(f1, n);
		for(Fraction f : frac) {
			if(r == 0) return f;
			r--;
		}
		// this point will never be reached
		return null;
	}

	
}
