package jspellfh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictionaryGenerator {
	private String lang;
	private HashMap<String, Float> wordList;
	private int totalWords; 
	
	public DictionaryGenerator(String lang) {
		this.lang = lang;
		wordList = new HashMap<String, Float>();
		totalWords = 0;
	}
	
	public void generateDictionary(String folder) {
		Pattern p = Pattern.compile("[a-zA-Z'\\-.]+[a-zA-Z]|[a-zA-Z'-]+\\s*");
		try {
			Files.walk(Paths.get(folder)).forEach(filePath -> {
				if(Files.isRegularFile(filePath)) {
					Scanner sc = null;
					try {
						sc = new Scanner(filePath);
					} catch (IOException eIOE) {
						System.out.println("Error with file : " + filePath + " " + eIOE);
					}
					
					while(sc.hasNext()) {
						String word = sc.next().toLowerCase();
						Matcher m = p.matcher(word);

						if(m.matches()){
							if(wordList.containsKey(word)) {
								wordList.replace(word, wordList.get(word) + 1);
							} else {
								wordList.put(word, (float)1);
							}
							totalWords++;
						}
					}
				}
			});
		} catch (IOException eIOE) {
			System.out.println("It was impossible to parse files in directory. " + eIOE);
		} catch (SecurityException eSE) {
			System.out.println("Allow rights to this folder. " + eSE);
		}
	}

	public String getLang() {
		return lang;
	}

	public HashMap<String, Float> getWordList() {
		return wordList;
	}
}
