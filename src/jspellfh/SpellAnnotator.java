package jspellfh;

import jspellfh.PropositionModules.Agregator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SpellAnnotator {
	private ArrayList<HashMap<String, Float>> dictionaries;
	private ArrayList<Integer> nb_unknow;
	private ArrayList<String> text_to_analize;
	private int min_unkown_index;

	public SpellAnnotator() {
		dictionaries = new ArrayList<HashMap<String, Float>>();
		nb_unknow = new ArrayList<Integer>();
		text_to_analize = new ArrayList<String>();
		min_unkown_index = 0;
	}

	public void setDictionaries(String[] args){
		/* Create an hashmap in the list for each dictionary */
		for(int i = 0; i < args.length; i++) {
			try {
				Scanner sc = new Scanner(new File(args[i]));
				dictionaries.add(new HashMap<String, Float>());
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					if ("".equals(line))
						break;
					String[] entry = line.split("\\t");

					// Penser aux doublons...
					dictionaries.get(i).put(entry[0], Float.parseFloat(entry[1]));
				}
			} catch (FileNotFoundException e) {
				System.err.println("Dictionary not found : + " + e);
				break;
			}
		}
	}

	public void setTextAndLang(){
		Scanner sc = new Scanner(System.in);
		String line;
		String[] words;
		int i;
		/*Pattern p = Pattern.compile("[a-zA-ZÀ-ÿ'\\-.]+[a-zA-ZÀ-ÿ]|[a-zA-ZÀ-ÿ'-]+\\s*");
		Matcher m;*/
		for(i = 0; i < dictionaries.size(); i++)
			nb_unknow.add(0);

		/* Get text to analize and determine the language */
		while(sc.hasNextLine()){
			line = sc.nextLine();
			text_to_analize.add(line);
			words = line.split("\\p{P}|\\s");
			for(String word : words){
				for(i = 0; i < dictionaries.size(); i++) {
					if(!dictionaries.get(i).containsKey(word)) {
						nb_unknow.set(i, nb_unknow.get(i) + 1);
					}
				}
			}
		}

		for(i = 1; i < nb_unknow.size(); i++)
			if(nb_unknow.get(min_unkown_index) > nb_unknow.get(i))
				min_unkown_index = i;
	}

	private void displayAnalizedText(){
		String line;
		String[] words;
		int i;
		/* Read text to analize */
		for(String l : text_to_analize) {
			line = l;

			words = line.split("\\p{P}|\\s");
			for(String word : words) {
				/*m = p.matcher(word);
				if(m.matches()) {*/
				if(!dictionaries.get(min_unkown_index).containsKey(word)) {
					Locale lang = new Locale("fr");
					Agregator a = new Agregator(dictionaries.get(min_unkown_index), lang);
					ArrayList<String> propositions = a.findBestWords(word);

					StringBuilder annotation = new StringBuilder("<spell>").append(word).append("|");
					for(i = 0; i < 7/*propositions.size()*/; i++){
						annotation.append(propositions.get(i));
						if(i < 6/*propositions.size() - 1*/)
							annotation.append(",");
					}
					annotation.append("</spell>");

					line = line.replace(word, annotation.toString());
				}
				/*} else
					System.out.println("Not matched : " + word);*/
			}

			System.out.println(line);
		}
	}

	public static void main(String[] args){
		if(args.length == 0)
			throw new IllegalArgumentException("must have at least one dictionary");

		SpellAnnotator sa = new SpellAnnotator();
		sa.setDictionaries(args);
		sa.setTextAndLang();
		sa.displayAnalizedText();
	}
}
