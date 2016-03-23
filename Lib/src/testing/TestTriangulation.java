package testing;
import java.util.LinkedList;
import java.util.Scanner;

import lib.geometry.Circle;
import lib.geometry.Point;
import lib.geometry.Polygons;
import lib.geometry.Tikz;
import lib.geometry.Triangle;

public class TestTriangulation {

/*

4
0 0
1 0
1 1
0 1


6
-8 2
8 2
8 14
0 14
0 6
-8 14

8
0 0
4 0
4 4
2 4
2 2
1 2
1 4
0 4

6
-8 2
8 2
8 14
0 14
0 6
-8 14

8
-71 22
61 58
43 20
77 30
77 60
-39 60
-39 39
-71 39

80
-594 599
-968 947
-966 1012
-936 1075
-848 1025
-565 1133
-916 1143
331 1227
706 568
805 763
833 858
1002 562
1127 181
325 725
384 662
519 486
-56 928
289 800
547 781
240 1125
112 1108
-51 1048
229 1185
-781 783
2 272
-194 127
1097 110
-254 562
-432 640
873 193
997 164
-366 732
-280 805
1197 132
853 922
1265 338
1222 1138
1259 1241
1056 1352
1276 1296
1301 1361
1290 1398
-92 1470
-909 1492
890 1482
-1201 1494
-36 1380
872 1326
-559 1363
1129 1265
1017 1072
864 1155
-642 1366
-484 1334
-585 1203
-837 1263
-645 1395
-1064 1238
-1208 1062
-639 558
-469 342
-1168 269
-788 457
-938 746
-1195 630
-1046 728
-1249 1112
-1353 1302
-1360 922
-1143 431
-1269 193
-1307 260
-1457 202
-1083 83
-907 77
-717 115
-1057 173
-1013 195
-848 253
-168 222

99
-120 1
110 2
-110 3
110 4
-110 5
110 6
-110 7
110 8
-110 9
110 10
-110 11
110 12
-110 13
110 14
-110 15
110 16
-110 17
110 18
-110 19
110 20
-110 21
110 22
-110 23
110 24
-110 25
110 26
-110 27
110 28
-110 29
110 30
-110 31
110 32
-110 33
110 34
-110 35
110 36
-110 37
110 38
-110 39
110 40
-110 41
110 42
-110 43
110 44
-110 45
110 46
-110 47
110 48
-110 49
110 50
-110 51
110 52
-110 53
110 54
-110 55
110 56
-110 57
110 58
-110 59
110 60
-110 61
110 62
-110 63
110 64
-110 65
110 66
-110 67
110 68
-110 69
110 70
-110 71
110 72
-110 73
110 74
-110 75
110 76
-110 77
110 78
-110 79
110 80
-110 81
110 82
-110 83
110 84
-110 85
110 86
-110 87
110 88
-110 89
110 90
-110 91
110 92
-110 93
110 94
-110 95
110 96
-110 97
110 98
-120 99

 */
	
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		int n = reader.nextInt();
		reader.nextInt();
		Point[] pol = new Point[n];
		for(int i = 0; i < n; i++) {
			pol[i] = new Point(reader.nextInt(), reader.nextInt());
		}	
		
		System.out.println("triangulate");
		
		LinkedList<Triangle> T = Polygons.triangulate(pol);
	
		Tikz ti = new Tikz();
	
		ti.updateMinMax(pol);
		ti.setDataScale(15, 15);
		
		ti.drawPolygon(pol, "->, thick");
		for(Triangle t : T) {
			ti.drawTriangle(t, "red");
		}
		
		ti.drawCircle(new Circle(new Point(0, 0), 65), "opacity = 0.5, fill = cyan");
		
		System.out.println(ti.getTikz(1));
		
		reader.close();
	}
	
	
}
