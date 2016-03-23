package testing;

import java.util.Random;
import lib.graphs.BitGraph;
import lib.graphs.Graph;
import lib.graphs.LinkedGraph;
import lib.graphs.SparseGraph;
import lib.graphs.algorithms.BFS;

public class GraphBenchmark {

	private static Random rnd = new Random();

	public static void main(String[] args) {
		test();
	}

	public static void test() {

		int V = 10000;
		int K = 1000000;
		

		Graph gs = new SparseGraph(V);
		Graph gl = new LinkedGraph(V);
		Graph gb = new BitGraph(V);

		/*
		for(int i = 0; i < V; i++) {
			for(int j = 0; j < V; j++) {
				//gs.connect(i, j);
				gb.connect(i, j);
			}
		}
	
		*/

		
		for(int x = 0; x < V; x++) {
			gs.connect(x, V - 1);
			gl.connect(x, V - 1);
			gb.connect(x, V - 1);
		}
		
		System.out.println("start");
	
		System.out.println(addDelete(gs, K));
		System.out.println(addDelete(gl, K));
		System.out.println(addDelete(gb, K));
		
	}


	public static void benchmark(Graph g, int N, int seed) {
		rnd = new Random(seed);
		System.out.println(addDelete(g, N));
		System.out.println(loop(g));
		//System.out.println(allBFS(g));
	}

	public static long addDelete(Graph g, int N) {
		long time = 0;
		for(int i = 0; i < N; i++) {
			int x1 = rnd.nextInt(g.V());
			int y1 = rnd.nextInt(g.V());
			int x2 = rnd.nextInt(g.V());
			int y2 = rnd.nextInt(g.V());
			long t0 = System.nanoTime();
			g.connect(x1, y1);
			g.disconnect(x2, y2);
			long t1 = System.nanoTime();
			time += t1 - t0;
		}
		return time;
	}

	public static long loop(Graph g) {
		long time = 0;
		int cnt = 0;
		long t0 = System.nanoTime();
		for(int x = 0; x < g.V(); x++) {
			for(int y : g.outNeighbors(x)) {
				cnt++;
			}
		}
		long t1 = System.nanoTime();
		time += t1 - t0;
		return time;
	}

	public static long allBFS(Graph g) {
		long time = 0;
		for(int x = 0; x < g.V(); x++) {
			BFS.bfsInit(g);
			long t0 = System.nanoTime();
			BFS.bfsVisit(g, x);
			long t1 = System.nanoTime();
			time += t1 - t0;
		}
		return time;
	}
	

}
