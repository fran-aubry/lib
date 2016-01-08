package lib.util;

public class Utils {

	public static String getOrdinalSuffix(int n) {
		int r100 = n % 100;
		int r10 = n % 10;
		if(r100- r10 == 10) return "th";
		if(r10 == 1) return "st";
		if(r10 == 2) return "nd";
		if(r10 == 3) return "rd";
		return "th";
	}
	
}
