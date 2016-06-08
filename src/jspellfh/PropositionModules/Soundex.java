package jspellfh.PropositionModules;

import java.util.*;

/**
 * Created by axelheine on 04/06/2016.
 */
public class Soundex implements PropositionModule {
    private HashMap<String, Set<String>> map;
    private Locale lang;

    public Soundex(HashMap<String, Float> dictionary, Locale lang) {
        this.lang = lang;
        this.map = generateMap(dictionary);
    }

    private HashMap<String, Set<String>> generateMap(HashMap<String, Float> dictionary) {
        HashMap<String, Set<String>> map = new HashMap<>();
        for(String word : dictionary.keySet()) {
            String soundex = calculateSoundex(word, lang);
            if(soundex == null) {
                continue;
            }
            if(map.containsKey(soundex)) {
                Set<String> set = map.get(soundex);
                set.add(word);
                map.put(soundex, set);
            } else {
                HashSet<String> hashSet = new HashSet<>();
                hashSet.add(word);
                map.put(soundex, hashSet);
            }
        }
        return map;
    }

    public String calculateSoundex(String s, Locale lang) {
        if(s.equals("")) {
            return null;
        }
        char[] word = s.toUpperCase().toCharArray();
        char firstLetter = word[0];

        //Convert to numeric code
        if(lang.getLanguage().equals("fr")) {
            for(int i = 0; i < word.length; i++) {
                switch (word[i]) {
                    case 'B':
                    case 'P':
                        word[i] = '1';
                        break;
                    case 'C':
                    case 'K':
                    case 'Q':
                        word[i] = '2';
                        break;
                    case 'D':
                    case 'T':
                        word[i] = '3';
                        break;
                    case 'L':
                        word[i] = '4';
                        break;
                    case 'M':
                    case 'N':
                        word[i] = '5';
                        break;
                    case 'R':
                        word[i] = '6';
                        break;
                    case 'G':
                    case 'J':
                        word[i] = '7';
                        break;
                    case 'X':
                    case 'Z':
                    case 'S':
                        word[i] = '8';
                        break;
                    case 'F':
                    case 'V':
                        word[i] = '9';
                        break;
                    default:
                        word[i] = '0';
                        break;
                }
            }
        } else {
            for(int i = 0; i < word.length; i++) {
                switch (word[i]) {
                    case 'B':
                    case 'F':
                    case 'P':
                    case 'V':
                        word[i] = '1';
                        break;
                    case 'C':
                    case 'G':
                    case 'J':
                    case 'K':
                    case 'Q':
                    case 'S':
                    case 'X':
                    case 'Z':
                        word[i] = '2';
                        break;
                    case 'D':
                    case 'T':
                        word[i] = '3';
                        break;
                    case 'L':
                        word[i] = '4';
                        break;
                    case 'M':
                    case 'N':
                        word[i] = '5';
                        break;
                    case 'R':
                        word[i] = '6';
                        break;
                    default:
                        word[i] = '0';
                        break;
                }
            }
        }
        String output = "" + firstLetter;
        for(int i = 1; i < word.length; i++) {
            if(word[i] != word[i-1] && word[i] != '0') {
                output += word[i];
            }
        }
        output = output + "0000"; //if the string is too short
        return output.substring(0, 4);
    }

    public void updateModule(String word) {
        String soundex = calculateSoundex(word, lang);
        if(map.containsKey(soundex)) {
            Set<String> set = map.get(soundex);
            set.add(word);
            map.put(soundex, set);
        } else {
            HashSet<String> hashSet = new HashSet<>();
            hashSet.add(word);
            map.put(soundex, hashSet);
        }
    }


    @Override
    public List<String> findWords(String str) {
        System.out.println();
        String soundex = calculateSoundex(str, lang);
        if(map.containsKey(soundex)) {
            return new ArrayList(map.get(soundex));
        } else {
            return null;
        }

    }

    public HashMap<String, Set<String>> getMap() {
        return map;
    }
}
