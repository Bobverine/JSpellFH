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

        Agregator a = new Agregator(dg.getWordList(), l);
        System.out.println("Agregator : " + a.findBestWords("pentoufle"));
	}
}
