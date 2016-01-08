package lib.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import lib.graphs.Graph;
import lib.graphs.HashMeasure;
import lib.graphs.Measure;
import lib.graphs.TreeGraph;
import lib.graphs.WGraph;


public class GraphIO {

	public Graph g;
	public Measure IGP;
	public int nbs;
	public int[] sources;
	public boolean und;
	public String instance;

	public GraphIO(Scanner reader, boolean und) {
		this.und = und;
		readGraph(reader);
	}

	public GraphIO(String path, String instance, boolean und) {
		this.und = und;
		this.instance = instance;
		try {
			Scanner reader = new Scanner(new FileReader(path + instance));
			readGraph(reader);
		} catch (FileNotFoundException e) {
			System.out.printf("File %s not found!\n", path + instance);
			e.printStackTrace();
		}
	}
	
	public GraphIO(String path, String instance, boolean und, boolean old, boolean readLat) {
		this.und = und;
		this.instance = instance;
		try {
			Scanner reader = new Scanner(new FileReader(path + instance));
			if(old) readGraphOldFormat(reader, readLat);
			else readGraph(reader);
		} catch (FileNotFoundException e) {
			System.out.printf("File %s not found!\n", path + instance);
			e.printStackTrace();
		}
	}

	public void readGraph(Scanner reader) {
		nbs = reader.nextInt();
		sources = new int[nbs];
		for(int i = 0; i < nbs; i++) {
			sources[i] = reader.nextInt();
		}
		int V = reader.nextInt();
		int E = reader.nextInt();
		g = new TreeGraph(V);
		IGP = new HashMeasure();
		for(int i = 0; i < E; i++) {
			int orig = reader.nextInt();
			int dest = reader.nextInt();
			double igp = reader.nextInt();
			g.connect(orig, dest);
			IGP.setWeight(orig, dest, igp);
		}
		if(und) {
			for(int i = 0; i < V; i++) {
				for(int j : g.outNeighbors(i)) {
					if(!g.connected(j, i)) {
						g.connect(j, i);
						IGP.setWeight(j, i, IGP.getWeight(i, j));
					}
				}
			}
		}
		g.setName(instance);
	}

	public void readGraphOldFormat(Scanner reader, boolean readLat) {
		TreeMap<String, Integer> M = new TreeMap<String, Integer>();
		int index = 0;
		List<Edge> edges = new LinkedList<Edge>();
		while(reader.hasNextLine()) {
			String line = reader.nextLine();
			if(line.charAt(0) == '#') continue;
			String[] data = line.split(" ");
			String s1 = data[0];
			String s2 = data[1];
			double igp = Double.parseDouble(data[2]);
			//double lat = 0;
			if(readLat) Double.parseDouble(data[3]);
			if(!M.containsKey(s1)) {
				M.put(s1, index);
				index++;
			}
			if(!M.containsKey(s2)) {
				M.put(s2, index);
				index++;
			}
			edges.add(new Edge(s1, s2, igp));
		}
		g = new TreeGraph(index);
		String[] labels = new String[g.V()];
		for(Entry<String, Integer> e : M.entrySet()) {
			labels[e.getValue()] = e.getKey();
		}
		g.setNodeLabels(labels);
		IGP = new HashMeasure();
		for(Edge edge : edges) {
			int v = M.get(edge.orig);
			int u = M.get(edge.dest);
			g.connect(v, u);
			IGP.setWeight(v, u, edge.igp);
		}
		if(und) {
			for(int i = 0; i < g.V(); i++) {
				for(int j : g.outNeighbors(i)) {
					if(!g.connected(j, i)) {
						g.connect(j, i);
						IGP.setWeight(j, i, IGP.getWeight(i, j));
					}
				}
			}
		}
		sources = new int[] {0};
		g.setName(instance);
		/*
		sources = new int[g.V() / 5 + 1];
		for(int i= 0; 5 * i < g.V(); i++) {
			sources[i] = 5 * i;
		}
		*/
	}
	
	public void writeToFile(String name) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter("paper2/instances/" + name));
			writer.println(g.V() + " " + g.E());
			for(int x = 0; x < g.V(); x++) {
				for(int y : g.outNeighbors(x)) {
					writer.println(x + " " + y + " " + IGP.getWeight(x, y));
				}
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class Edge {

		String orig, dest;
		double igp;

		public Edge(String orig, String dest, double igp) {
			this.orig = orig;
			this.dest = dest;
			this.igp = igp;
		}

	}

	public static WGraph readStandardWeighted(String file, boolean und) {
		try {
			Scanner reader = new Scanner(new FileReader(file));
			int n = reader.nextInt();
			int m = reader.nextInt();
			Graph g = new TreeGraph(n);
			Measure w = new HashMeasure();
			for(int i = 0; i < m; i++) {
				int orig = reader.nextInt();
				int dest = reader.nextInt();
				double cost = reader.nextInt();
				g.connect(orig, dest);
				w.setWeight(orig, dest, cost);
				if(und) {
					g.connect(dest, orig);
					w.setWeight(dest,  orig, cost);
				}
			}
			reader.close();
			return new WGraph(g, w);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
