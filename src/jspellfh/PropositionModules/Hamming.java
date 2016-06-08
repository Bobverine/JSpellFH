package jspellfh.PropositionModules;

import java.util.*;

/**
 * Created by axelheine on 02/06/2016.
 */
public class Hamming implements PropositionModule{
    private HashMap<Integer, Set<String>> zones;
    private String reference;
    public Hamming(HashMap<String, Float> dictionary) {
        this.zones = generateZones(dictionary);
    }

    public List<String> findWords(String str) {
        int setDistance = calculateHammingDistance(str, reference);
        ArrayList<String> bestWord = new ArrayList<>();
        Set<String> bestSet = zones.get(setDistance);
        int distance = -1, tmpDistance;
        for (String s : bestSet) {
            tmpDistance = calculateHammingDistance(str, s);
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

    private HashMap<Integer, Set<String>> generateZones(HashMap<String, Float> dictionary) {
        HashMap<Integer, Set<String>> map = new HashMap<>();
        Set<String> dico = dictionary.keySet();
        reference = (String) dico.toArray()[dico.size()/2];
        dico.forEach(word -> {
            int distance = calculateHammingDistance(word, reference);
            Set<String> words = map.get(distance);
            if(words == null) {
                words = new HashSet<>();
            }
            words.add(word);
            map.put(distance, words);
        });
        return map;
    }

    public void updateModule(String word) {
        int distance = calculateHammingDistance(word, reference);
        Set<String> words = zones.get(distance);
        if(words == null) {
            words = new HashSet<>();
        }
        words.add(word);
        zones.put(distance, words);
    }

    private int calculateHammingDistance(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int shorter = Math.min(s1.length, s2.length);
        int longest = Math.max(s1.length, s2.length);

        int result = 0;
        int resultReverse = 0;
        for (int i=0; i<shorter; i++) {
            if (s1[i] != s2[i]) result++;
        }
        for (int i=shorter-1; i>0; i--) {
            if (s1[i] != s2[i]) resultReverse++;
        }

        result += longest - shorter;
        resultReverse += longest - shorter;
        return Math.min(result, resultReverse);
    }

    public HashMap<Integer, Set<String>> getZones() {
        return zones;
    }
}
