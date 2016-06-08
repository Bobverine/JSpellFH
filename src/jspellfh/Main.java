package jspellfh;

import jspellfh.PropositionModules.Agregator;
import jspellfh.PropositionModules.Hamming;
import jspellfh.PropositionModules.Levenshtein;
import jspellfh.PropositionModules.Soundex;

import java.util.Locale;

public class Main {
	public static void main(String[] args) {
		Locale l = new Locale("fr");
		DictionaryGenerator dg = new DictionaryGenerator();
		dg.generateDictionary(args[0]);
		Hamming ham = new Hamming(dg.getWordList());
        //System.out.println("Hamming" + ham.getZones());

        Levenshtein lev = new Levenshtein(dg.getWordList());
        //System.out.println("Levenshtein : " + lev.getMap());

		Soundex sound = new Soundex(dg.getWordList(), l);
		//System.out.println("Soundex : " + sound.getMap());

		String word = "mrch";
		System.out.println("Mot demandé : " + word);
		System.out.println("Hamming : " + ham.findWords(word));
        System.out.println("Levenshtein : " + lev.findWords(word));
		System.out.println("Soundex : " + sound.findWords(word));

        String word2 = "bjour";
        System.out.println("Mot demandé : " + word2);
        System.out.println("Hamming : " + ham.findWords(word2));
        System.out.println("Levenshtein : " + lev.findWords(word2));
        System.out.println("Soundex : " + sound.findWords(word2));

        String word3 = "alfabet";
        System.out.println("Mot demandé : " + word3);
        System.out.println("Hamming : " + ham.findWords(word3));
        System.out.println("Levenshtein : " + lev.findWords(word3));
        System.out.println("Soundex : " + sound.findWords(word3));


        Agregator a = new Agregator(dg.getWordList(), l);
        System.out.println("Agregator : " + a.findBestWords("mrch"));
	}
}
