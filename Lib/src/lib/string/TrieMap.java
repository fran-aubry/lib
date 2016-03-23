package lib.string;

import java.util.Arrays;

public class TrieMap {
	
	int[][] go, val;
	boolean[][] term;
	int curSize;

	int ND = -1;
	
	int[] map;
	
	public TrieMap(int maxSize, String alphabet) {
		go = new int[maxSize][alphabet.length()];
		val = new int[maxSize][alphabet.length()];
		term = new boolean[maxSize][alphabet.length()];
		map = new int[400];
		for(int i = 0; i < alphabet.length(); i++) {
			map[alphabet.charAt(i)] = i;
		}
		Arrays.fill(val[0], ND);
		Arrays.fill(go[0], -1);
		curSize = 1;
	}

	public void add(char[] s, int v) {
		int cur = 0, prev = -1;
		int ch = -1;
		for(int i = 0; i < s.length; i++) {
			ch = map[s[i]];
			if(go[cur][ch] == -1) {
				go[cur][ch] = curSize;
				Arrays.fill(val[curSize], ND);
				Arrays.fill(go[curSize], -1);
				curSize++;
			}
			prev = cur;
			cur = go[cur][ch];
		}
		term[prev][ch] = true;
		val[prev][ch] = v;
	}

	public boolean contains(char[] s) {
		int cur = 0, prev = -1;
		int ch = -1;
		for(int i = 0; i < s.length; i++) {
			ch = s[i] - 'a';
			if(go[cur][ch] == -1) return false;
			prev = cur;
			cur = go[cur][ch];
		}
		return term[prev][ch];
	}

	public int value(char[] s) {
		int cur = 0, prev = -1;
		int ch = -1;
		for(int i = 0; i < s.length; i++) {
			ch = s[i] - 'a';
			if(go[cur][ch] == -1) return ND;
			prev = cur;
			cur = go[cur][ch];
		}
		return val[prev][ch];
	}

	public String toString() {
		//for(int[] x : go) {System.out.println(Arrays.toString(x));}
		StringBuilder sb = new StringBuilder();
		build(0, sb, "");
		return sb.toString();
	}

	private void build(int cur, StringBuilder sb, String pre) {
		for(int j = 0; j < go[cur].length; j++) {
			if(go[cur][j] != -1) {
				if(val[cur][j] == ND) {
					sb.append(pre + (char)('a' + j));
				} else {
					sb.append(pre + "[" + (char)('a' + j) + "/" + val[cur][j] + "]");						
				}
				sb.append('\n');
				build(go[cur][j], sb, pre + "  ");
			}
		}
	}

}




