package jspellfh.PropositionModules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by axelheine on 08/06/2016.
 */
public class Agregator {
    private List<PropositionModule> modulesList;
    private HashMap<String, Float> dictionary;

    public Agregator(HashMap<String, Float> dictionary, Locale lang) {
        this.dictionary = dictionary;
        Levenshtein l = new Levenshtein(dictionary);
        Hamming h = new Hamming(dictionary);
        Soundex s = new Soundex(dictionary, lang);
        modulesList = new ArrayList<>();
        modulesList.add(l);
        modulesList.add(h);
        modulesList.add(s);
    }

    public ArrayList<String> findBestWords(String str) {
        ArrayList<String> wordList = new ArrayList<>();

        for(PropositionModule module : modulesList) {
            wordList.addAll(module.findWords(str));
        }

        return wordList;
    }
}
