package testing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

import lib.geometry.Point;
import lib.geometry.RangeTree;
import lib.util.ArraysExt;

public class TestRangeTree {

	
	public static void main(String[] args) {
		makeRandomTestAllDiff(10, 100, 100);
	}
	
	public static void makeRandomTestAllDiff(int n, int q, int max) {
		Random rnd = new Random();
		Point[] p = generateRandomAllDiffPoints(n, 0, max);
		
		RangeTree T = new RangeTree(p);
		
		for(int i = 0; i < q; i++) {
			int x1 = rnd.nextInt(max);
			int x2 = rnd.nextInt(max);
			int y1 = rnd.nextInt(max);
			int y2 = rnd.nextInt(max);
			
			int xq1 = Math.min(x1, x2);
			int xq2 = Math.max(x1, x2);
			int yq1 = Math.min(y1, y2);
			int yq2 = Math.max(y1, y2);
			
			drawTestCase(p, x1, x2, y1, y2, max);
			LinkedList<Point> resT = T.query(xq1, xq2, yq1, yq2);
			LinkedList<Point> resB = bruteQuery(p, xq1, xq2, yq1, yq2);
			
			if(resT.size() != resB.size()) {
				System.out.println(n);
				for(Point x : p) {
					System.out.println(x.xi() + " " + x.yi());
				}
				System.out.println(x1 + " " + x2 + " " + y1 + " " + y2);
				System.out.println();
				
				System.out.println("Tree output: " + resT);
				System.out.println("Correct out: " + resB);
				
				return;
			}
		}
		System.out.println("all ok");
	}
	
	public static LinkedList<Point> bruteQuery(Point[] p, int x1, int x2, int y1, int y2) {
		LinkedList<Point> res = new LinkedList<>();
		for(Point x : p) {
			if(RangeTree.inRange(x, x1, x2, y1, y2)) {
				res.add(x);
			}
		}
		return res;
	}
	
	public static Point[] generateRandomAllDiffPoints(int n, int min, int max) {
		int[] x = ArraysExt.generateRandomArray(n, true, min, max);
		int[] y = ArraysExt.generateRandomArray(n, true, min, max);
		Point[] p = new Point[n];
		for(int i = 0; i < n; i++) {
			p[i] = new Point(x[i], y[i]);
		}
		return p;
	}
	
	public static void drawTestCase(Point[] p, int x1, int x2, int y1, int y2, int max) {
		BufferedImage img = new BufferedImage(max, max, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D)img.getGraphics();
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, max, max);
		g2.setColor(Color.GRAY);
		g2.fillRect(x1, y1, x2 - x1, y2 - y1);
		g2.setColor(Color.BLACK);
		for(Point x : p) {
			g2.fillOval(x.xi(), x.yi(), 4, 4);
		}
		try {
			ImageIO.write(img, "png", new File("test.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
