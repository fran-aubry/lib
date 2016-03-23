package lib.graphs;

import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedList;

public class LinkedGraph extends Graph {

	private BitSet[] inSet, outSet;
	private LinkedList<Integer>[] inN, outN;
	
	@SuppressWarnings("unchecked")
	public LinkedGraph(int V) {
		this.V = V;
		this.E = 0;
		inSet = new BitSet[V];
		outSet = new BitSet[V];
		inN = new LinkedList[V];
		outN = new LinkedList[V];
		for(int x = 0; x < V; x++) {
			inSet[x] = new BitSet();
			outSet[x] = new BitSet();
			inN[x] = new LinkedList<>();
			outN[x] = new LinkedList<>();
		}
	}
	
	public void connect(int x, int y) {
		if(!connected(x, y)) {
			outSet[x].set(y);
			outN[x].add(y);
			inSet[y].set(x);
			inN[y].add(x);
		}
	}


	public void disconnect(int x, int y) {
		outSet[x].clear(y);
		inSet[y].clear(x);
	}


	public boolean connected(int x, int y) {
		return outSet[x].get(y);
	}

	public int[] outNeighbors(int x) {
		int[] out = new int[outSet[x].cardinality()];
		Iterator<Integer> it = outN[x].iterator();
		int i = 0;
		while(it.hasNext()) {
			int y = it.next();
			if(outSet[x].get(y)) {
				out[i++] = y;
			} else {
				it.remove();
			}
		}
		return out;
	}

	public int[] inNeighbors(int x) {
		int[] in = new int[inSet[x].cardinality()];
		Iterator<Integer> it = inN[x].iterator();
		int i = 0;
		while(it.hasNext()) {
			int y = it.next();
			if(inSet[x].get(y)) {
				in[i++] = y;
			} else {
				it.remove();
			}
		}
		return in;
	}

	public Graph create(int V) {
		return new LinkedGraph(V);
	}

	public int inDeg(int x) {
		return inN[x].size();
	}

	public int outDeg(int x) {
		return outN[x].size();
	}

	public int neighbor(int x) {
		Iterator<Integer> it = outN[x].iterator();
		while(it.hasNext()) {
			int y = it.next();
			if(!outSet[x].get(y)) {
				it.remove();
			} else {
				return y;
			}
		}
		return -1;
	}
	
}
