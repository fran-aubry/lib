package testing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.TreeMap;

import lib.graphs.Edge;
import lib.graphs.EdgeFunction;
import lib.graphs.HashGraph;
import lib.graphs.PushRelabel;

public class TestFlow {

	public static void main(String[] args) throws FileNotFoundException {
		solve("graphs/instance5.txt");
	}
	
	static void solve(String filename) throws FileNotFoundException {
		Scanner reader = new Scanner(new FileReader(filename));
		int V = reader.nextInt();
		int E = reader.nextInt();
		HashGraph g = new HashGraph(V);
		EdgeFunction cap = new EdgeFunction();
		for(int i = 0; i < E; i++) {
			int o = reader.nextInt();
			int d = reader.nextInt();
			Edge e = new Edge(o, d);
			g.connect(e);
			int c = reader.nextInt();
			cap.set(e, c);
		}
		int s = 0;
		int t = g.V() - 1;
		PushRelabel pr =  new PushRelabel(g, cap, s, t);
		System.out.println(pr.E[t]);
		reader.close();
	}
	
}
