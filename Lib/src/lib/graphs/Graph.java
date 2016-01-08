package lib.graphs;

public abstract class Graph {

	public abstract int V();
	
	public abstract int E();
	
	public abstract Edge[] edges();
	
	public abstract void addNode();
	
	public abstract void removeLast();
	
	public abstract boolean connected(int v, int u);
	
	public abstract boolean connected(Edge e);
	
	public abstract Edge[] outEdges(int v);
	
	public abstract int[] outNeighbors(int v);
	
	public abstract int[] outNeighborsRnd(int v);
	
	public abstract int[] inNeighbors(int v);
	
	public abstract  int outDeg(int v);
	
	public abstract int inDeg(int v);

	public abstract void connect(int v, int u);

	public abstract void connect(Edge e);
	
	public abstract void clear(int v);

	public abstract void disconnect(int v, int u);
	
	public abstract void disconnect(Edge e);
	
	public abstract Graph copy();
	
	public abstract void setNodeLabels(String[] nodeLabels);
	
	public abstract String[] getNodeLabels();
	
	public abstract String getName();
	
	public abstract void setName(String name);
	
}
