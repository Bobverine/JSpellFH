package jspellfh.PropositionModules;

import java.util.*;

/**
 * Created by axelheine on 07/06/2016.
 */
public class Levenshtein implements PropositionModule{
    private HashMap<Integer, Set<String>> map;
    private String pivot;

    public Levenshtein(HashMap<String, Float> dictionary) {
        this.map = generateMap(dictionary);
    }

    private HashMap<Integer, Set<String>> generateMap(HashMap<String, Float> dictionary) {
        HashMap<Integer, Set<String>> map = new HashMap<>();
        Set<String> dico = dictionary.keySet();
        pivot = (String) dico.toArray()[dico.size()/2];
        dico.forEach(word -> {
            int distance = compute(word, pivot);
            Set<String> words = map.get(distance);
            if(words == null) {
                words = new HashSet<>();
            }
            words.add(word);
            map.put(distance, words);
        });
        return map;
    }

    public List<String> findWords(String str) {
        int setDistance = compute(str, pivot);
        ArrayList<String> bestWord = new ArrayList<>();
        Set<String> bestSet = map.get(setDistance);
        int distance = -1, tmpDistance;
        for (String s : bestSet) {
            tmpDistance = compute(str, s);
            if(distance == -1 || tmpDistance == distance) {
                bestWord.add(s);
                distance = tmpDistance;
            }
            if(tmpDistance < distance) {
                bestWord.clear();
                bestWord.add(s);
                distance = tmpDistance;
            }
        }
        return bestWord;
    }

    public void updateModule(String word) {
        int distance = compute(word, pivot);
        Set<String> words = map.get(distance);
        if(words == null) {
            words = new HashSet<>();
        }
        words.add(word);
        map.put(distance, words);
    }

    public int compute(String str1, String str2) {
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();

        int[] costs = new int[str2.length() + 1];
        for(int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }
        for(int i = 1; i <= str1.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for(int j = 1; j <= str2.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j-1]), str1.charAt(i - 1) == str2.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[str2.length()];
    }

    public HashMap<Integer, Set<String>> getMap() {
        return map;
    }
}
