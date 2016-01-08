package lib.graphs;


public interface Measure {

	public double getWeight(int v, int u);
	
	public void setWeight(int v, int u, double w);
	
	public double getWeight(Edge e);
	
	public void setWeight(Edge e, double w);

	public void setWeight(Path p, double w);

	public void remove(int v, int u);
	
	public Measure copy();
	
	public double totalWeight();
	
	public double eval(Path p);
	
	public double eval(Graph g);
	
}
