package jspellfh.PropositionModules;

/**
 * Created by axelheine on 04/06/2016.
 */
public class Soundex {


    public static String calculateSoundex(String s, String lang) {
        char[] word = s.toUpperCase().toCharArray();
        char firstLetter = word[0];

        //Convert to numeric code
        if(lang.equals("fr")) {
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
        } else if(lang.equals("en")) {
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
}
