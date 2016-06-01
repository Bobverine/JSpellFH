package jspellfh;

public class Main {
	public static void main(String[] args) {
		DictionaryGenerator dg = new DictionaryGenerator("fr");
		dg.generateDictionary(args[0]);
		
		System.out.println(dg.getWordList());
	}
}
