package jspellfh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictionaryGenerator {
	private HashMap<String, Float> wordList;

	public DictionaryGenerator() {
		wordList = new HashMap<String, Float>();
	}
	
	public void generateDictionary(String folder) {
		try {
			Files.walk(Paths.get(folder)).forEach(filePath -> {
				if(Files.isRegularFile(filePath)) {
					Scanner sc = null;
					try {
						sc = new Scanner(filePath);
						//sc.useDelimiter(Pattern.compile(""));
					} catch (IOException eIOE) {
						System.out.println("Error with file : " + filePath + " " + eIOE);
					}
					
					while(sc.hasNext()) {
						// La regex remplace les apostrophes... A corriger
						String word = sc.next().toLowerCase().replaceAll("\\p{Ps}|\\p{Pe}|\\p{Pi}|\\p{Pf}|\\p{Pc}|\\p{Po}", "");
						Pattern p = Pattern.compile("[a-zA-ZÀ-ÿ'\\-.]+[a-zA-ZÀ-ÿ]|[a-zA-ZÀ-ÿ'-]+\\s*");
						Matcher m = p.matcher(word);

						if(m.matches()){
							if(wordList.containsKey(word)) {
								wordList.replace(word, wordList.get(word) + 1);
							} else {
								wordList.put(word, (float) 1);
							}
						}/* else {
							System.out.println("Not matched : " + word);
						}*/
					}
				}
			});
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
