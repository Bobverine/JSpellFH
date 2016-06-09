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

    /*Fonction d'appel au différents modules. Renvoi une liste triée par ordre décroissant de fréquences d'apparition du mot.*/
    public ArrayList<String> findBestWords(String str) {
        HashMap<String, Float> wordList = new HashMap<>();

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
