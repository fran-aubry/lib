package lib.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReader {

	private BufferedReader reader;
	private String line;
	private boolean hasLine;

	public InputReader() {
		reader = new BufferedReader(new InputStreamReader(System.in));
		line = null;
		hasLine = false;
	}
	
	public boolean nextLineIsEmpty() throws IOException {
		if(hasLine) return line.length() == 0;
		line = reader.readLine();
		hasLine = true;
		return line.length() == 0;
	}

	public boolean hasNextLine() throws IOException {
		if(hasLine) return line != null;
		line = reader.readLine();
		hasLine = true;
		return line != null;
	}

	public String readLine() throws IOException {
		if(hasLine) {
			hasLine = false;
			return line;
		}
		return reader.readLine();
	}
	
	public int readInt() throws IOException {
		line = readLine();
		line = line.trim();
		return Integer.parseInt(line);
	}
	
	public long readLong() throws IOException {
		line = readLine();
		line = line.trim();
		return Long.parseLong(line);
	}

	public int[] splitIntLine() throws IOException {	
		int[] a = null;
		line = readLine();
		line = line.trim();
		String[] data = line.split("[ ]+");
		a = new int[data.length];
		for(int i = 0; i < data.length; i++) {
			a[i] = Integer.parseInt(data[i]);
		}
		return a;
	}
	
	public double[] splitDoubleLine() throws IOException {
		double[] a = null;
		line = readLine();
		line = line.trim();
		String[] data = line.split("[ ]+");
		a = new double[data.length];
		for(int i = 0; i < data.length; i++) {
			a[i] = Double.parseDouble(data[i]);
		}
		return a;
	}
	
	public long[] splitLongLine() throws IOException {	
		long[] a = null;
		line = readLine();
		line = line.trim();
		String[] data = line.split("[ ]+");
		a = new long[data.length];
		for(int i = 0; i < data.length; i++) {
			a[i] = Long.parseLong(data[i]);
		}
		return a;
	}
	
	public String[] splitLine() throws IOException {
		line = readLine();
		line = line.trim();
		return line.split("[ ]+");
	}
	
	
	
	public String toString() {
		return hasLine + " " + line;
	}

}
