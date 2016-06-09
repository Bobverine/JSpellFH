package jspellfh;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by axelheine on 08/06/2016.
 */
public class SpellSelector {
    private File input;
    private File output;
    private File personnalDictionary;
    private ArrayList<String> ignoreAll;

    /**
     * Creates instance of SpellSelector which contains input file, output file, personal dictionary file and list of ignored words
     * @param inp
     *          Path to input file
     * @param outp
     *          Path to output file
     * @param dict
     *          Path to dictionary
     */
    public SpellSelector(String inp, String outp, String dict) {
        this.input = new File(inp);
        this.output = new File(outp);
        this.personnalDictionary = new File(dict);
        this.ignoreAll = new ArrayList<>();
    }

    /**
     * Parse the input file and add words to output if no errors.
     * If the method found a <spell> </spell> it calls userChoice to find the good word
     * @throws IOException
     *          If one of the file can not be opened
     */
    /* Fonction qui lit et écrit dans les fichier input et output, si une faute est repérée (balise <spell> la ligne est passée a la fonction userChoice.*/
    private void spellSelect() throws IOException {
        Scanner sc = new Scanner(input);
        BufferedWriter bw = new BufferedWriter(new FileWriter(output, true));
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            Scanner sc2 = new Scanner(line);
            while (sc2.hasNext()) {
                String word = sc2.next();
                if (word.contains("<spell>")) {
                    word = userChoice(word, line);
                }
                bw.write(word + " ");
            }
            bw.write("\n");
        }
        bw.flush();
    }

    /**
     * Prints the misspelled word with all the options.
     * Waits the user interaction.
     * @param word
     *          Balise <spell> </spell>
     * @param line
     *          Line where found the balise
     * @return
     *          Word that user choices
     * @throws IOException
     *          If the dictionary can not be opened
     */
    /* Fonction de selection de correction, renvoie le mot choisi par l'utilisateur.
     * Affiche les propositions et le contexte. */
    private String userChoice(String word, String line) throws IOException {
        ArrayList<String> spell = new ArrayList<>(Arrays.asList(word.substring(7, word.length() - 8).split("\\|")));
        spell.addAll(Arrays.asList(spell.remove(1).split(",")));
        if(!ignoreAll.contains(spell.get(0))) {
            System.out.println(line.replace(word, " ** " + spell.get(0) + " ** "));
            System.out.println("Propositions : ");
            int i;
            for (i = 1; i < spell.size(); i++) {
                System.out.println(i + " - replace with " + spell.get(i));
            }
            System.out.println(i + 1 + " - ignore the misspelled word " + spell.get(0));
            System.out.println(i + 2 + " - ignore all the occurences of the misspelled word " + spell.get(0));
            System.out.println(i + 3 + " - add the word " + spell.get(0) + " in " + personnalDictionary.getName());

            Scanner sc = new Scanner(System.in);
            if (sc.hasNext()) {
                String number = sc.next();
                for (i = 1; i < spell.size(); i++) {
                    if (number.equals(Integer.toString(i))) {
                        return spell.get(i);
                    }
                }
                if (number.equals(Integer.toString(i + 1))) {
                    return spell.get(0);
                }
                if (number.equals(Integer.toString(i + 2))) {
                    ignoreAll.add(spell.get(0));
                    return spell.get(0);
                }
                if (number.equals(Integer.toString(i + 3))) {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(personnalDictionary, true));
                    bw.write(spell.get(0) + "\n");
                    bw.flush();
                    ignoreAll.add(spell.get(0));
                    return spell.get(0);
                }
            }
        }
        return spell.get(0);
    }


    public static void main(String[] args) throws IOException {
        if(args.length < 1) {
            System.out.println("Too few arguments (use -help for help)");
            return;
        }
        String outp = "";
        String inp = "";
        String dict = "";

        for(int i = 0; i < args.length; i = i+2 ) {
            switch (args[i]) {
                case "-d":
                    dict = args[i+1];
                    break;
                case "-in":
                    inp = args[i+1];
                    break;
                case "-out":
                    outp = args[i+1];
                    break;
                case "-help":
                    System.out.println( "Use : \n" +
                                        "java SpellSelector -d <personnal_dictionnary> -in <file_to_correct> -out <output_file>");
                    return;
                default:
                    System.out.println("Unknown argument (use -help for help)");
                    return;
            }
        }
        SpellSelector sl = new SpellSelector(inp, outp, dict);
        try {
            sl.spellSelect();
        } catch (IOException e) {
            System.out.println( "Use : \n" +
                    "java SpellSelector -d <personnal_dictionnary> -in <file_to_correct> -out <output_file>");
        }
    }
}
