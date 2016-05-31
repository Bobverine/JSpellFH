package jspellfh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class DictionnaryGenerator {
	private String lang;
	private HashMap<String, Float> wordList;
	private int totalWords; 
	
	public DictionnaryGenerator(String lang) {
		this.lang = lang;
		wordList = new HashMap<String, Float>();
		totalWords = 0;
	}
	
	public void generateDictionnary(String folder) {
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
						if(wordList.containsKey(word)) {
							wordList.replace(word, wordList.get(word) + 1);
						} else {
							wordList.put(word, (float)1);
						}
						totalWords++;
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
