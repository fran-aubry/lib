package lib.graphs;

import java.util.HashMap;

public class EdgeFunction {

	private HashMap<Edge, Integer> F;
	
	public EdgeFunction() {
		F = new HashMap<>();
	}
	
	public int val(Edge e) {
		return F.get(e);
	}
	
	public void set(Edge e, int val) {
		F.put(e, val);
	}
	
	public void add(Edge e, int val) {
		if(!defined(e)) set(e, val);
		else set(e, val + val(e));
	}
	
	public boolean defined(Edge e) {
		return F.containsKey(e);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		System.out.println(F);
		for(Edge e : F.keySet()) {
			sb.append(e.toString() + " = " + F.get(e) + "\n");
		}
		return sb.toString();
	}
	
}
