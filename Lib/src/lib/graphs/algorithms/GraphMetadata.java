package lib.graphs.algorithms;

import lib.data.StrToIntBijection;

/**
 * Class to represent metadata about the graph like
 * a name and vertex labels.
 *
 */
public class GraphMetadata {

	private String name;
	private StrToIntBijection M;
	
	public GraphMetadata() {
		M = new StrToIntBijection();
		name = "";
	}
	
	public int add(String name) {
		return M.add(name);
	}
	
	public int getIndex(String name) {
		return M.getIndex(name);
	}
	
	public String getLabel(int index) {
		return M.getLabel(index);
	}
	
	public void setGraphName(String name) {
		this.name = name;
	}
	
	public String getGraphName() {
		return name;
	}
	
	public void setNodeLabels(String[] nodeLabels) {
		for(int i = 0; i < nodeLabels.length; i++) {
			M.add(nodeLabels[i]);
		}
	}
	
	
	
}
