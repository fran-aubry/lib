package lib.graphs;

import java.util.Arrays;
import java.util.BitSet;

public class BitGraph extends Graph {

	public BitSet[] inN, outN;
	public int[] outIndex, inIndex;
	
	/*
	 * Constructor
	 */
	public BitGraph(int n) {
		V = n;
		E = 0;
		// initialize in and out neighbors sets
		inN = new BitSet[n];
		outN = new BitSet[n];
		for(int x = 0; x < n; x++) {
			inN[x] = new BitSet();
			outN[x] = new BitSet();
		}
		// initialize out and in indexes
		outIndex = new int[n];
		Arrays.fill(outIndex, -1);
		inIndex = new int[n];
		Arrays.fill(inIndex, -1);
	}
	
	public int neighbor(int x) {
		if(outDeg(x) == 0) return -1;
		return outN[x].nextSetBit(0);
	}
	
	/*
	 * Return the number of nodes in the graph.
	 */
	public int V() {
		return V;
	}
	
	/*
	 * Return the number of edges in the graph.
	 */
	public int E() {
		return E;
	}
	
	/*
	 * Connect nodes x to node y.
	 */
	public void connect(int x, int y) {
		if(!connected(x, y)) {
			outN[x].set(y);
			inN[y].set(x);			
			E++;
		}
	}
	
	/*
	 * Connect nodes x to node y and node y to node x.
	 */
	public void connectBoth(int x, int y) {
		connect(x, y);
		connect(y, x);
	}
	
	/*
	 * Disconnect nodes x from node y.
	 */
	public void disconnect(int x, int y) {
		if(connected(x, y)) {
			outN[x].clear(y);
			inN[y].clear(x);
			E--;
		}
	}
	
	/*
	 * Disconnect nodes x from node y and node y from node x.
	 */
	public void disconnectBoth(int x, int y) {
		disconnect(x, y);
		disconnect(y, x);
	}
	
	/*
	 * Check if x is connected to y.
	 */
	public boolean connected(int x, int y) {
		return outN[x].get(y);
	}
	
	/*
	 * Check if x is connected to y and y to x.
	 */
	public boolean connectedBoth(int x, int y) {
		return connected(x, y) && connected(y, x);
	}
	
	/*
	 * Get the next out-neighbor of x.
	 */
	public int nextOutNeighbor(int x) {
		int y = outN[x].nextSetBit(outIndex[x] + 1);
		outIndex[x] = y;
		return y;
	}
	
	/*
	 * Reset the next out-neighbor of x.
	 */
	public void resetOutNeighbor(int x) {
		outIndex[x] = -1;
	}
	
	/*
	 * Get the next in-neighbor of x.
	 */
	public int nextInNeighbor(int x) {
		int y = inN[x].nextSetBit(inIndex[x] + 1);
		inIndex[x] = y;
		return y;
	}
	
	/*
	 * Reset the next in-neighbor of x.
	 */
	public void resetInNeighbor(int x) {
		inIndex[x] = -1;
	}
	
	/*
	 * Compute and array with all the out-neighbors of x.
	 */
	public int[] outNeighbors(int x) {
		int[] out = new int[outN[x].cardinality()];
		int y = -1, i = 0;
		while((y = outN[x].nextSetBit(y + 1)) != -1) {
			out[i++] = y;
		}
		return out;
	}
	
	/*
	 * Compute and array with all the in-neighbors of x.
	 */
	public int[] inNeighbors(int x) {
		int[] in = new int[inN[x].cardinality()];
		int y = -1, i = 0;
		while((y = inN[x].nextSetBit(y + 1)) != -1) {
			in[i++] = y;
		}
		return in;
	}
	
	public Graph create(int V) {
		return new BitGraph(V);
	}

	public int inDeg(int x) {
		return inN[x].cardinality();
	}

	public int outDeg(int x) {
		return outN[x].cardinality();
	}
	
}
