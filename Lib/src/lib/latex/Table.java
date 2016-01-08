package lib.latex;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Table {

	public static void makeTable(String outfile, String[][] table) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(outfile));
			int lin = table.length;
			int col = table[0].length;
			String s = "";
			for(int i = 0; i < col; i++) {
				s = s + " c";
			}
			s = s + " ";
			writer.println("\\begin{tabular}{" + s + "}");
			for(int i = 0; i < lin; i++) {
				for(int j = 0; j < col; j++) {
					writer.print(table[i][j]);
					if(j < col - 1) {
						writer.print("\t&\t");
					} else {
						if(i < lin - 1) {
							writer.print("\t\\\\");
						}
						writer.println();
					}
				}
			}
			writer.println("\\end{tabular}");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
