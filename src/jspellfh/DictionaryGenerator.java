package jspellfh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictionaryGenerator {
	private HashMap<String, Float> wordList;

	public DictionaryGenerator() {
		wordList = new HashMap<>();
	}

	private void readFile(Path filePath) {
		if(Files.isRegularFile(filePath)) {
			Scanner sc = null;
			try {
				sc = new Scanner(filePath);
			} catch (IOException eIOE) {
				System.out.println("Error with file : " + filePath + " " + eIOE);
			}

			String word;
			String[] cleaned_words;
			/*Pattern p = Pattern.compile("[a-zA-ZÀ-ÿ'\\-.]+[a-zA-ZÀ-ÿ]|[a-zA-ZÀ-ÿ'-]+\\s*");
			Matcher m;*/

			while(sc.hasNext()) {
				word = sc.next();
				cleaned_words = word.split("\\p{P}|\\s"); /* Remove punction and whitespaces */

				/* For each word */
				for(String cleaned_word : cleaned_words) {
					/* if it is already in the dictionary */
					if(wordList.containsKey(cleaned_word))
						wordList.replace(cleaned_word, wordList.get(cleaned_word) + 1); /* add +1 occurrence */
					else
						wordList.put(cleaned_word, (float) 1); /* add it to the dictionary */
				}
			}
		}
	}

	public void generateDictionary(String folder) {
		try {
			/* Process each text files */
			Files.walk(Paths.get(folder)).forEach(filePath -> readFile(filePath));
			/* Update word occurrence to frequency */
			for(Map.Entry<String, Float> entry : wordList.entrySet())
				entry.setValue(entry.getValue() / wordList.size());
		} catch (IOException eIOE) {
			System.out.println("It was impossible to parse files in directory. " + eIOE);
		} catch (SecurityException eSE) {
			System.out.println("Allow rights to this folder. " + eSE);
		}
	}

	public HashMap<String, Float> getWordList() {
		return wordList;
	}

	public int getTotalWords() {
		return wordList.size();
	}

	public void printDictionary() {
		for(Map.Entry<String, Float> entry : wordList.entrySet()) {
			String key = entry.getKey();
			Float value = entry.getValue();
			System.out.println(key + '\t' + value);
		}
	}

	public static void main(String[] args) {
		DictionaryGenerator dg = new DictionaryGenerator();
		dg.generateDictionary(args[0]);
		dg.printDictionary();
	}
}