package lib.numbers;

public class Bases {
	
	public static long baseBto10(String n, int B) {
		long val = 0;
		long mul = 1;
		for(int i = n.length() - 1; i >= 0; i--) {
			char c = n.charAt(i);
			if(Character.isDigit(c)) {
				val += Character.getNumericValue(c) * mul;
			} else {
				val += (c - 'A' + 10) * mul;
			}
			mul *= B;
		}
		return val;
	}
	
	public static String base10toB(long val, int B) {
		StringBuilder sb = new StringBuilder();
		while(val > 0) {
			int r = (int)(val % B);
			if(r < 10) sb.append(r);
			else sb.append((char)(r - 10 + 'A'));
			val = val / B;
		}
		sb.append(val);
		return sb.reverse().toString();
	}
	
	public static boolean checkBase(String n, int B) {
		for(int i = 0; i < n.length(); i++) {
			char c = n.charAt(i);
			if(Character.isDigit(c)) {
				if(Character.getNumericValue(c) >= B) return false;
			} else {
				if((c - 'A' + 10) >= B) return false;
			}
		}
		return true;
	}

}
