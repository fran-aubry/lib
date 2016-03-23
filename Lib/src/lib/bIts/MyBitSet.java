package lib.bIts;

public class MyBitSet {

	public static void main(String[] args) {
		new MyBitSet(10);

		long l = 0;
		//l |= (1l << 63);
		l |= (1l << 50);
		l |= (1l << 30);
		l |= (1l << 20);
		l |= (1l << 10);
		l |= (1l << 5);
		l |= (1l << 2);

		System.out.println(l);
		System.out.println(Long.toBinaryString(l));
		System.out.println(Long.toBinaryString(l >> 5));
	}

	private final int MOD_64_MASK = 0x0000003f;

	private final long ALL_ONES = 0xffffffffffffffffL;
	private final long ONE = 1;


	private long[] b;
	private int div, mod;

	private int blockIndex;
	private int indexInBlock;

	public MyBitSet(int maxSize) {
		System.out.println(Integer.toBinaryString(MOD_64_MASK));
		// the number of long we need is maxSize / 64 = maxSize >> 6         
		b = new long[maxSize >> 6];
	}

	public void set(int i) {
		div = i >> 6; // compute i / 64
		mod = i & MOD_64_MASK; // compute i % 64
		b[div] &= (ONE << mod);
	}

	public boolean get(int i) {
		div = i >> 6; // compute i / 64
		mod = i & MOD_64_MASK; // compute i % 64
		return (b[div] & (ONE << mod)) == (ONE << mod);
	}

	public void clear(int i) {
		div = i >> 6; // compute i / 64
		mod = i & MOD_64_MASK; // compute i % 64
		b[div] &= (ALL_ONES ^ (ONE << mod));	
	}

	public int next() {
		if(blockIndex == -1 || (b[blockIndex] >> (indexInBlock + 1)) == 0) {
			blockIndex++;
			while(b[blockIndex] == 0) {
				blockIndex++;
			}
			indexInBlock = (int)(b[blockIndex] & -b[blockIndex]);
			return (blockIndex << 6) + indexInBlock;
		} 
		long block = b[blockIndex] >> (indexInBlock + 1);
		indexInBlock = (int)(block & -block);
		return blockIndex ;
	}

}
