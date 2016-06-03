package jspellfh;

import jspellfh.PropositionModules.Hamming;

public class Main {
	public static void main(String[] args) {
		DictionaryGenerator dg = new DictionaryGenerator();
		dg.generateDictionary(args[0]);
		Hamming ham /*OUAIS COMME LE JAMBON */ = new Hamming(dg.getWordList());
        System.out.println(ham.getZones());
		/*System.out.println(dg.getWordList());
		System.out.println(dg.getTotalWords());*/
	}
}
