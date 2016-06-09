package jspellfh.PropositionModules;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by axelheine on 08/06/2016.
 */
public class Agregator {
    private List<PropositionModule> modulesList;
    private HashMap<String, Float> dictionary;

    /**
     * Create an instance of Agregator which create List of PropositionModules
     * @param dictionary
     *          Dictionary of words
     * @param lang
     *          Language of the dictionary
     */
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

    /**
     * Call each modules and return list of words sorted by proportion of appear
     * @param str
     *          Word with mistake
     * @return
     *          ArrayList of words sorted by proportion of appear
     */
    public ArrayList<String> findBestWords(String str) {
        HashMap<String, Float> wordList = new HashMap<>();
        //appel de chaque module pour le mot courrant
        for(PropositionModule module : modulesList) {
            ArrayList<String> words = (ArrayList<String>) module.findWords(str);
            if(words != null) {
                for (String w : words) {
                    if (wordList.containsKey(w)) {
                        wordList.put(w, wordList.get(w) + 1);
                    } else {
                        wordList.put(w, 1.0f);
                    }
                }
            }
        }
        //calcul des proportion pour chaque mot
        for (Map.Entry<String, Float> entry : wordList.entrySet()) {
            entry.setValue(entry.getValue() / wordList.size() + dictionary.get(entry.getKey()));
        }

        //tri par ordre décroissant de la map et transformation en liste.
        ArrayList<String> lst = (ArrayList<String>) wordList.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return lst;
    }
}
