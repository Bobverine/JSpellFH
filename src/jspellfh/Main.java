package jspellfh;

import jspellfh.PropositionModules.Hamming;
import jspellfh.PropositionModules.Soundex;

public class Main {
	public static void main(String[] args) {
		DictionaryGenerator dg = new DictionaryGenerator();
		dg.generateDictionary(args[0]);
		Hamming ham = new Hamming(dg.getWordList());
        System.out.println("Hamming" + ham.getZones());

		Soundex sound = new Soundex(dg.getWordList(), "fr");
		System.out.println("Soundex : " + sound.getMap());
		String word = "marchz";
		System.out.println("Mot demandé : " + word);
		System.out.println("Hamming : " + ham.findWords(word));
		System.out.println("Soundex : " + sound.findWords(word));

	}
}
