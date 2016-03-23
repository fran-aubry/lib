import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

public class SingleClassMaker {

	private static final String LIB_PATH = "/home/francois/Documents/lib/lib/Lib/src/lib/";
	//private static final String SOURCE_PATH = "/home/francois/workspace2/uHunt/src/chap4/";
	private static final String SOURCE_PATH = "/home/francois/workspace2/UAC/src/UAC12016/";
	private static final String OUT_PATH = "/home/francois/Documents/lib/lib/Lib/src/";
	
	private static File sourcefile;
	private static String classname;
	private static Dependencies dependencies;
	private static HashMap<String, File> libClasses;

	public static void main(String[] args) {
		libClasses = getAllLibClasses();
		getAllLibClasses();
		Scanner reader = new Scanner(System.in);
		System.out.println("classname?");
		classname = reader.next();
		sourcefile = new File(SOURCE_PATH + classname + ".java");
		dependencies = findDependencies(sourcefile, new HashSet<String>());
		buildOutput();
		reader.close();
	}

	static HashMap<String, File> getAllLibClasses() {
		File root = new File(LIB_PATH);
		HashMap<String, File> libClasses = new HashMap<>();
		getAllLibClasses(libClasses, root);
		return libClasses;
	}

	static void getAllLibClasses(HashMap<String, File> libClasses, File file) {
		if(file.isFile()) {
			String classname = file.getName().substring(0, file.getName().length() - 5);
			libClasses.put(classname, file);
		} else {
			for(File f : file.listFiles()) {
				getAllLibClasses(libClasses, f);
			}
		}
	}

	static void buildOutput() {
		LinkedList<String> output = new LinkedList<>();
		for(String imp : dependencies.java) {
			output.add("import " + imp + ";");
		}
		LinkedList<String> original = getClass("Main", sourcefile, "", true, false);
		original.removeLast();
		output.addAll(original);
		for(String classname : dependencies.lib) {
			output.addAll(getClass(classname, libClasses.get(classname), "\t", false, true));
		}
		output.add("}");
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(OUT_PATH +  "Main.java"));
			for(String line : output) {
				writer.println(line);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	static String toFile(String imp) {
		return LIB_PATH +  imp.replace(".", "/") + ".java";
	}

	static LinkedList<String> getClass(String name, File file, String tab, boolean main, boolean makeStatic) {
		LinkedList<String> lines = new LinkedList<>();
		lines.add(tab + "/*******************************");
		lines.add(tab + "* " + name);
		lines.add(tab + "*******************************/");
		try {
			Scanner reader = new Scanner(new FileReader(file));
			while(reader.hasNextLine()) {
				String line = reader.nextLine();
				if(line.equals("")) continue;
				if(line.matches("[ ]+")) continue;
				if(line.matches("[\t]+")) continue;
				if(line.startsWith("import")) continue;
				if(line.startsWith("package")) continue;
				if(makeStatic && line.contains("class") && !line.contains("static")) {
					line = line.replace("class", "static class");
				}
				if(main && line.contains(classname)) {
					line = "public class Main {";
				}
				lines.add(tab + line);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	static Dependencies findDependencies(File file, HashSet<String> processed) {
		Dependencies dep = new Dependencies();
		System.out.println(processed);
		try {
			Scanner reader = new Scanner(new FileReader(file));
			while(reader.hasNextLine()) {
				String line = reader.nextLine().trim();
				if(line.startsWith("import")) {
					String[] data = line.split("[ ]+");
					String imp = data[1].substring(0, data[1].length() - 1);
					if(imp.startsWith("java")) {
						// java import
						dep.addJava(imp);
					}
				} else {
					for(String classname : libClasses.keySet()) {
						String c1 = " " + classname + " ";
						String c2 = " " + classname + ".";
						String c3 = " " + classname + "(";
						String c4 = " " + classname + "<";
						String c5 = "<" + classname + ">";
						String c6 = "(" + classname + " ";
						String c7 = "(" + classname + "[";
						String c8 = "(" + classname + ".";
						String c9 = ")" + classname + ".";						
						if(line.contains(c1) || line.contains(c2) || line.contains(c3) || line.contains(c4) || line.contains(c5) || line.contains(c6) || line.contains(c7) || line.contains(c8) || line.contains(c9)) {
							if(processed.contains(classname)) continue;
							if(classname.equals("Tree")) {
								System.out.println();
							}
							if(file.getName().equals(classname + ".java")) continue;
							processed.add(classname);
							dep.addLib(classname);
							System.out.println(classname);
							dep.merge(findDependencies(libClasses.get(classname), processed));
						}
					}
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return dep;
	}
	
	static class Dependencies {

		public HashSet<String> java;
		public HashSet<String> lib;

		public Dependencies() {
			java = new HashSet<>();
			lib = new HashSet<>();
		}

		public void addJava(String imp) {
			java.add(imp);
		}

		public void addLib(String imp) {
			lib.add(imp);
		}

		public void merge(Dependencies other) {
			java.addAll(other.java);
			lib.addAll(other.lib);
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(String imp : java) {
				sb.append(imp);
				sb.append("\n");
			}
			sb.append("\n");
			for(String imp : lib) {
				sb.append(imp);
				sb.append("\n");
			}
			return sb.toString();
		}

	}
	

}
