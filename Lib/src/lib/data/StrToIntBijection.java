package lib.data;

import java.util.ArrayList;
import java.util.HashMap;

public class StrToIntBijection {

	private ArrayList<String> indexToLabel;
	private HashMap<String, Integer> labelToIndex;
	private int index;
	
	public StrToIntBijection() {
		labelToIndex = new HashMap<>();
		indexToLabel = new ArrayList<>();
		index = 0;
	}
	
	public int size() {
		return index;
	}
	
	public int add(String name) {
		Integer i = labelToIndex.get(name);
		if(i == null) {
			labelToIndex.put(name, index);
			indexToLabel.add(name);
			return index++;
		}
		return i;
	}
	
	public boolean contains(String name) {
		return labelToIndex.containsKey(name);
	}
	
	public boolean contains(int idx) {
		return idx < size();
	}
	
	public int getIndex(String name) {
		return labelToIndex.get(name);
	}
	
	public String getLabel(int index) {
		return indexToLabel.get(index);
	}
	
}
