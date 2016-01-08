package lib.geometry;

import java.util.ArrayList;
import java.util.LinkedList;

public class Tikz {
	
	private StringBuilder sb;
	private double dataXMAX, dataYMAX, dataXMIN, dataYMIN;
	public double sx, sy;
	
	public Tikz() {
		sx = 1;
		sy = 1;
		sb = new StringBuilder();
	}
	
	public void updateMinMax(Point[] pol) {
		for(Point p : pol) {
			dataXMAX = Math.max(p.x, dataXMAX);
			dataYMAX = Math.max(p.y, dataYMAX);
			dataXMIN = Math.min(p.x, dataXMIN);
			dataYMIN = Math.min(p.y, dataYMIN);
		}
	}
	
	public void updateMinMax(ArrayList<Point> pol) {
		for(Point p : pol) {
			dataXMAX = Math.max(p.x, dataXMAX);
			dataYMAX = Math.max(p.y, dataYMAX);
			dataXMIN = Math.min(p.x, dataXMIN);
			dataYMIN = Math.min(p.y, dataYMIN);
		}
	}
	
	public void updateMinMax(Circle C) {
		dataXMAX = Math.max(C.center.x + C.radius, dataXMAX);
		dataYMAX = Math.max(C.center.y + C.radius, dataYMAX);
		dataXMIN = Math.max(C.center.x - C.radius, dataXMIN);
		dataYMIN = Math.max(C.center.y - C.radius, dataYMIN);
	}
	
	public void updateMinMax(Point p, Point q) {
		dataXMAX = Math.max(p.x, dataXMAX);
		dataYMAX = Math.max(p.y, dataYMAX);
		dataXMIN = Math.min(p.x, dataXMIN);
		dataYMIN = Math.min(p.y, dataYMIN);
		
		dataXMAX = Math.max(q.x, dataXMAX);
		dataYMAX = Math.max(q.y, dataYMAX);
		dataXMIN = Math.min(q.x, dataXMIN);
		dataYMIN = Math.min(q.y, dataYMIN);
	}
	
	public void drawSegment(Point p, Point q, String options) {
		sb.append(String.format("\\draw[%s] (%.3f, %.3f) -- (%.3f, %.3f);\n", options, sx * p.x, sy * p.y, sx * q.x, sy * q.y)); 
	}
	
	public void drawCircle(Circle c, String options) {
		sb.append(String.format("\\draw[%s] (%.3f, %.3f) circle (%.3f);\n", options, sx * c.center.x, sy * c.center.y, sx * c.radius));
	}
	
	public void drawPolygon(Point[] pol, String options) {
		sb.append(String.format("\\draw[%s] ", options));
		for(Point p : pol) {
			sb.append(String.format("(%.3f, %.3f) node[red] {\\tiny $(%d, %d)$} -- ", sx * p.x, sy * p.y, p.xi(), p.yi()));
		}
		sb.append(String.format("(%.3f, %.3f);\n", sx * pol[0].x, sy * pol[0].y));
	}
	
	public void drawPolygon(ArrayList<Point> pol, String options) {
		sb.append(String.format("\\draw[%s] ", options));
		for(Point p : pol) {
			sb.append(String.format("(%.3f, %.3f) node[red] {\\tiny $(%d, %d)$} -- ", sx * p.x, sy * p.y, p.xi(), p.yi()));
		}
		sb.append(String.format("(%.3f, %.3f);\n", sx * pol.get(0).x, sy * pol.get(0).y));
	}
	
	public void drawTriangle(Triangle T, String options) {
		drawPolygon(T.toPol(), options);
	}
	
	public void setDataScale(int xmax, int ymax) {
		sx = xmax / (dataXMAX - dataXMIN);
		sy = ymax / (dataYMAX - dataYMIN);
		sy = sx;
	}
	
	public String getTikz(int xmax, int ymax) {
		return getTikz(Math.min(xmax / (dataXMAX - dataXMIN), ymax / (dataYMAX - dataYMIN)));
	}
	
	public void newPage() {
		sb.append("\\newpage");
	}
	
	public String getTikz(double scale) {
		StringBuilder out = new StringBuilder();
		out.append("\\begin{center}\n");
		out.append(String.format("\\begin{tikzpicture}[scale = %.3f]\n", scale));

		out.append(sb.toString());

		out.append("\\end{tikzpicture}\n");
		out.append("\\end{center}\n");
		return out.toString();
	}

}
