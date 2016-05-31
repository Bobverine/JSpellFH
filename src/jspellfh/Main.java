package jspellfh;

public class Main {
	public static void main(String[] args) {
		
		System.setProperty("file.encoding", "UTF-32");
		DictionnaryGenerator dg = new DictionnaryGenerator("fr");
		dg.generateDictionnary(args[0]);
		
		System.out.println(dg.getWordList());
	}
}
