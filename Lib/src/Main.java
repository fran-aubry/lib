import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/*******************************
* Main
*******************************/
public class Main {
/*
4 4 0 45 2.9
4 4 0 0 1
4 4 0 90 1
100  50  10  90  10
100  50  10  0  40
100  100  10  45  15
100  50  10  1  200
100  50  10  89  200
100  50  10  45  1000
100  100  10  30  200
0  0  0  0  0

4 4 100 45 2

 */
	public static void main(String[] args) throws IOException {
		InputReader reader = new InputReader();
		while(reader.hasNextLine()) {
			double[] data = reader.splitDoubleLine();
			double a = data[0];
			double b = data[1];
			double v = data[2];
			double A = data[3];
			double s = data[4];
			if(a + b + v + A + s == 0) break;
			double alpha, dx, dy;
			alpha = Math.toRadians(A);
			dx = Math.cos(alpha);
			dy = Math.sin(alpha);
			double x = (v * s * dx) / 2;
			double y = (v * s * dy) / 2;
			int xinter = 0, yinter = 0;
			if(Cmp.geq(x, a / 2)) {
				xinter = 1 + (int)Math.floor((x - a / 2) / a);
			}
			if(Cmp.geq(y, b / 2)) {
				yinter = 1 + (int)Math.floor((y - b / 2) / b);
			}
			System.out.println(xinter + " " + yinter);
		}
	}
	/*******************************
	* InputReader
	*******************************/
	public static class InputReader {
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
	/*******************************
	* Config
	*******************************/
	public static class Config {
		public static double EPS = 1e-8;
		public static int DECP = 3;
	}
	/*******************************
	* Cmp
	*******************************/
	public static class Cmp {
		public static boolean eq(double x, double y) {
			return Math.abs(x - y) <= Config.EPS;
		}
		public static boolean le(double x, double y) {
			return x < y - Config.EPS;
		}
		public static boolean ge(double x, double y) {
			return x > y + Config.EPS;
		}
		public static boolean leq(double x, double y) {
			return x <= y + Config.EPS;
		}
		public static boolean geq(double x, double y) {
			return x >= y - Config.EPS;
		}
		public static int cmp(double x, double y) {
			if(eq(x, y)) return 0;
			if(le(x, y)) return -1;
			return 1;
		}
	}
}
