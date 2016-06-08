package jspellfh;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
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
						word = word.replace(cleaned_word, "<spell>" + cleaned_word + "</spell>");
					}
				/*} else
					System.out.println("Not matched : " + cleaned_word);*/
			}

			System.out.println(word);
		}
	}
}
