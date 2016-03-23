package lib.io;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import lib.graphs.EdgeFunction;
import lib.graphs.SparseGraph;
import lib.graphs.algorithms.GraphMetadata;

import java.util.Scanner;
import java.util.TreeMap;

/**
 * Class that allows to read the instances.
 * 
 * @author f.aubry@uclouvain.be
 */
public class GraphIO {

	public SparseGraph g; // The graph that is read.
	public GraphMetadata gmdt;
	public EdgeFunction IGP, LAT, MUL; // The IGP and Latency values.
	public boolean undirected; // Whether the graphs is to be read as undirected.
	public String instance; // Instance filename.

	/*
	 * -GML- 
	 * 
	 * Graph Modeling Language (GML) is a hierarchical 
	 * ASCII-based file format for describing graphs.
	 * See more online.
	 * 
	 * -NTFL-
	 * 
	 * List of edges:
	 * 
	 * origin destination IGP latency
	 * 
	 * -NTF-
	 * 
	 * Same as NTF but without latency
	 * 
	 * -GMUL--
	 * 
	 * Only gives topology (edges) with multiplicity.
	 * A line with
	 * x y m
	 * means that there are m edges between x and y
	 *	
	 */
	
	public GraphIO(String path, String instance, boolean undirected) {
		String filename = path + instance;
		this.instance = instance;
		this.undirected = undirected;
		if(filename.endsWith("gml")) {
			readGML(filename);
		} else if(filename.endsWith("ntfl")) {
			readNTFL(filename, true);
		} else if(filename.endsWith("ntf")) {
			readNTFL(filename, false);
		} else if(filename.endsWith("gmul")) {
			readNTFL(filename, false);
			MUL = IGP.copy();
			IGP = null;
		} else {			
			readGraph(filename);
		}
	}

	public void readGraph(String filename) {
		Scanner reader;
		try {
			reader = new Scanner(new FileReader(filename));
			int V = reader.nextInt();
			int E = reader.nextInt();
			g = new SparseGraph(V);
			IGP = new EdgeFunction();
			gmdt = new GraphMetadata();
			for(int i = 0; i < E; i++) {
				int orig = reader.nextInt();
				int dest = reader.nextInt();
				int igp = reader.nextInt();
				if(orig != dest) g.connect(orig, dest);
				IGP.set(orig, dest, igp);
			}
			if(undirected) {
				for(int i = 0; i < V; i++) {
					for(int j : g.outNeighbors(i)) {
						if(!g.connected(j, i)) {
							g.connect(j, i);
							IGP.set(j, i, IGP.get(i, j));
						}
					}
				}
			}
			MUL = new EdgeFunction(g, 1);
			int k = instance.indexOf('.');
			if(k >= 0) gmdt.setGraphName(instance.substring(0, k));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}

	public void readNTFL(String filename, boolean readLat) {
		Scanner reader;
		try {
			TreeMap<String, Integer> M = new TreeMap<String, Integer>();
			reader = new Scanner(new FileReader(filename));
			int index = 0;
			List<E> edges = new LinkedList<E>();
			while(reader.hasNextLine()) {
				String line = reader.nextLine();
				if(line.charAt(0) == '#') continue;
				String[] data = line.split(" ");
				String s1 = data[0];
				String s2 = data[1];
				int igp = (int)Double.parseDouble(data[2]);
				int lat = 0;
				if(readLat) lat = (int)(Double.parseDouble(data[3]) * 100);
				if(!M.containsKey(s1)) {
					M.put(s1, index);
					index++;
				}
				if(!M.containsKey(s2)) {
					M.put(s2, index);
					index++;
				}
				edges.add(new E(s1, s2, igp, lat));
			}
			g = new SparseGraph(index);
			gmdt = new GraphMetadata();
			String[] labels = new String[g.V()];
			for(Entry<String, Integer> e : M.entrySet()) {
				labels[e.getValue()] = e.getKey();
			}
			gmdt.setNodeLabels(labels);
			IGP = new EdgeFunction();
			for(E edge : edges) {
				int v = M.get(edge.orig);
				int u = M.get(edge.dest);
				if(v != u) g.connect(v, u);
				IGP.set(v, u, edge.igp);
			}
			LAT = new EdgeFunction();
			for(E edge : edges) {
				int v = M.get(edge.orig);
				int u = M.get(edge.dest);
				if(v != u) g.connect(v, u);
				LAT.set(v, u, edge.lat);
			}
			
			if(undirected) {
				for(int i = 0; i < g.V(); i++) {
					for(int j : g.outNeighbors(i)) {
						if(!g.connected(j, i)) {
							g.connect(j, i);
							IGP.set(j, i, IGP.get(i, j));
							LAT.set(j, i, LAT.get(i, j));
						}
					}
				}
			}
			MUL = new EdgeFunction(g, 1);
			gmdt.setGraphName(instance.substring(0, instance.indexOf('.')));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public void writeToFile(String name) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter("paper2/instances/" + name));
			writer.println(g.V() + " " + g.E());
			for(int x = 0; x < g.V(); x++) {
				for(int y : g.outNeighbors(x)) {
					writer.println(x + " " + y + " " + IGP.get(x, y));
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readGML(String filename) {
		Scanner reader;
		try {
			reader = new Scanner(new FileReader(filename));
			Node[] nodes = new Node[10000];
			ArrayList<int[]> edges = new ArrayList<int[]>();
			int V = 0;
			while(reader.hasNextLine()) {
				String line = reader.nextLine().trim();
				if(line.startsWith("node")) {
					// process node
					int id = -1;
					String label = "";
					double lon = 0, lat = 0;
					String cur = reader.nextLine().trim();
					while(!cur.equals("]")) {
						int space = cur.indexOf(' ');
						String key = cur.substring(0, space);
						String value = cur.substring(space + 1);
						switch(key) {
						case "id":
							id = Integer.parseInt(value);
							break;
						case "label":
							label = value;
							break;
						case "Longitude":
							lon = Double.parseDouble(value);
							break;
						case "Latitude":
							lat = Double.parseDouble(value);
							break;
						}
						cur = reader.nextLine().trim();
					}
					nodes[id] = new Node(id, label, lon, lat);
					V = Math.max(V, id);
				} else if(line.startsWith("edge")) {
					// process edge
					int orig = -1;
					int dest = -1;
					String cur = reader.nextLine().trim();
					while(!cur.equals("]")) {
						int space = cur.indexOf(' ');
						String key = cur.substring(0, space);
						String value = cur.substring(space + 1);
						switch(key) {
						case "source":
							orig = Integer.parseInt(value);
							break;
						case "target":
							dest = Integer.parseInt(value);
							break;
						}
						cur = reader.nextLine().trim();
					}
					edges.add(new int[] {orig, dest});
				}
			}
			V++;
			// build graph
			g = new SparseGraph(V);
			gmdt = new GraphMetadata();
			gmdt.setGraphName(instance.substring(0, instance.indexOf('.')));
			String[] labels = new String[V];
			for(int i = 0; i < V; i++) {
				labels[i] = nodes[i].label;
			}
			gmdt.setNodeLabels(labels);
			for(int[] edge : edges) {
				if(edge[0] != edge[1]) {
					g.connect(edge[0], edge[1]);
					g.connect(edge[1], edge[0]);					
				}
			}
			IGP = new EdgeFunction(g, 0);
			for(int[] edge : edges) {
				int orig = edge[0];
				int dest = edge[1];
				double lat1 = nodes[orig].lat;
				double lon1 = nodes[orig].lon;
				double lat2 = nodes[dest].lat;
				double lon2 = nodes[dest].lon;
				
				double dlon = lon2 - lon1; 
				double dlat = lat2 - lat1;
				
				double a = Math.sin(dlat/2) * Math.sin(dlat/2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dlon/2) * Math.sin(dlon/2); 
				double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
				int d = (int)(6373 * c);
				
				IGP.set(orig, dest, d);
			}
			MUL = new EdgeFunction(g, 1);
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeNTF(SparseGraph g, GraphMetadata gmdt, EdgeFunction IGP, String path) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(path + gmdt.getGraphName() + ".ntf"));
			for(int x = 0; x < g.V(); x++) {
				for(int y : g.outNeighbors(x)) {
					writer.println(x + " " + y + " " + IGP.get(x, y));
				}
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void writeNormalized(SparseGraph g, GraphMetadata gmdt, EdgeFunction IGP, String path) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(path + gmdt.getGraphName() + ".g"));
			for(int x = 0; x < g.V(); x++) {
				for(int y : g.outNeighbors(x)) {
					writer.println(x + " " + y + " " + IGP.get(x, y));
				}
			}
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}

	private class Node {
		
		public String label;
		public double lat, lon;
		
		public Node(int id, String label, double lon, double lat) {
			this.label = label;
			this.lon = lon;
			this.lat = lat;
		}
		
	}

	private class E {

		String orig, dest;
		int igp, lat;

		public E(String orig, String dest, int igp, int lat) {
			this.orig = orig;
			this.dest = dest;
			this.igp = igp;
			this.lat = lat;
		}

	}

}
