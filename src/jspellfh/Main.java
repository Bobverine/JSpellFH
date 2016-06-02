package jspellfh;

public class Main {
	public static void main(String[] args) {
		DictionaryGenerator dg = new DictionaryGenerator();
		dg.generateDictionary(args[0]);
		
		System.out.println(dg.getWordList());
		System.out.println(dg.getTotalWords());
	}
}
