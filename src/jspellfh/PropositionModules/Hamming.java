package jspellfh.PropositionModules;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * Created by axelheine on 02/06/2016.
 */
public class Hamming {
    private HashMap<Integer, Set<String>> zones;

    public Hamming(HashMap<String, Integer> dictionary) {
        this.zones = generateZones(dictionary);
    }

    private HashMap<Integer, Set<String>> generateZones(HashMap<String, Integer> dictionary) {
        HashMap<Integer, Set<String>> map = new HashMap<>();
        Set<String> dico = dictionary.keySet();
        String center = (String) dico.toArray()[dico.size()/2];
        System.out.println(center);

        dico.forEach(word -> {
            int distance = calculateHammingDistance(word, center);
            Set<String> words = map.get(distance);
            if(words == null) {
                words = new HashSet<>();
            }
            words.add(word);
            map.put(distance, words);
        });
        return map;
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
