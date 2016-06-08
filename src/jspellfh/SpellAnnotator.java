package jspellfh;

import jspellfh.PropositionModules.Agregator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class SpellAnnotator {

	public static void main(String[] args){
		if(args.length == 0)
			throw new IllegalArgumentException("must have at least one dictionary");

		HashMap<String, Float> hm = new HashMap<>();

		/* Create hashmap with each dictionary */
		for(String dic : args) {
			try {
				Scanner sc = new Scanner(new File(dic));
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					if ("".equals(line))
						break;
					String[] entry = line.split("\\t");

					// Penser aux doublons...
					/*if(hm.containsKey(word)) {
						hm.replace(word, hm.get(word) + 1);
					} else {
						hm.put(word, (float) 1);
					}*/

					hm.put(entry[0], Float.parseFloat(entry[1]));
				}
			} catch (FileNotFoundException e) {
				System.err.println("Dictionary not found : + " + e);
				break;
			}
		}

		// Déterminer la langue ici

		/* Read text to analize */
		Scanner sc = new Scanner(System.in);
		String word;
		String[] cleaned_words;
		/*Pattern p = Pattern.compile("[a-zA-ZÀ-ÿ'\\-.]+[a-zA-ZÀ-ÿ]|[a-zA-ZÀ-ÿ'-]+\\s*");
		Matcher m;*/

		while(sc.hasNextLine()) {
			word = sc.nextLine();

			cleaned_words = word.split("\\p{P}|\\s");
			for(String cleaned_word : cleaned_words) {
				/*m = p.matcher(cleaned_word);
				if(m.matches()) {*/
					if(!hm.containsKey(cleaned_word)) {
						Locale l = new Locale("fr");
						Agregator a = new Agregator(hm, l);
						ArrayList<String> propositions = a.findBestWords(cleaned_word);

						StringBuilder sb = new StringBuilder("<spell>").append(cleaned_word).append("|");
						for(int i = 0; i < propositions.size(); i++){
							sb.append(propositions.get(i));
							if(i < propositions.size() - 1)
								sb.append(",");
						}
						sb.append("</spell>");

						word = word.replace(cleaned_word, sb.toString());
					}
				/*} else
					System.out.println("Not matched : " + cleaned_word);*/
			}

			System.out.print(word);
		}
	}
}
