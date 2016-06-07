package jspellfh;

import jspellfh.PropositionModules.Hamming;
import jspellfh.PropositionModules.Levenshtein;
import jspellfh.PropositionModules.Soundex;

public class Main {
	public static void main(String[] args) {
		DictionaryGenerator dg = new DictionaryGenerator();
		dg.generateDictionary(args[0]);
		Hamming ham = new Hamming(dg.getWordList());
        System.out.println("Hamming" + ham.getZones());

        Levenshtein lev = new Levenshtein(dg.getWordList());
        System.out.println("Levenshtein : " + lev.getMap());

		Soundex sound = new Soundex(dg.getWordList(), "fr");
		System.out.println("Soundex : " + sound.getMap());



		String word = "mrch";
		System.out.println("Mot demandé : " + word);
		System.out.println("Hamming : " + ham.findWords(word));
        System.out.println("Levenshtein : " + lev.findWords(word));
		System.out.println("Soundex : " + sound.findWords(word));


	}
}
