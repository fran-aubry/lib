package lib.graphs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Paths {

	public static HashSet<Edge> intersection(List<Path> paths) {
		Path smallest = getSmallestPath(paths);
		HashSet<Edge> intersection = new HashSet<>();
		for(Edge e : smallest.edges) {
			boolean inAll = true;
			for(Path path : paths) {
				if(!path.contains(e)) {
					inAll = false;
					break;
				}
			}
			if(inAll) {
				intersection.add(e);
			}
		}
		return intersection;
	}
	
	public static Path getSmallestPath(List<Path> paths) {
		int minSize = Integer.MAX_VALUE;
		Path min = null;
		for(Path path : paths) {
			if(path.length() < minSize) {
				minSize = path.length();
				min = path;
			}
		}
		return min;
	}
	
	public static List<Path> pathContainingEdge(Path[] paths, Edge e) {
		List<Path> pathsWithE = new LinkedList<Path>();
		for(Path path : paths) {
			if(path.contains(e)) {
				pathsWithE.add(path);
			}
		}
		return pathsWithE;
	}
	
}
 