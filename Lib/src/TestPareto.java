import java.util.Scanner;

import lib.data.Pareto2D;
import lib.geometry.Point;


public class TestPareto {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		
		Pareto2D T = new Pareto2D();
		
		
		T.add(new Point(5, 5));
		
		
		T.add(new Point(3, 7));
		T.add(new Point(7, 3));
		
		T.add(new Point(4, 6));
		T.add(new Point(6, 4));
		
		T.add(new Point(3, 3));
		
		
		T.printTree();
		
		
	}
	
}
