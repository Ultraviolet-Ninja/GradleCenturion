/*
 * Author: Ultraviolet-Ninja
 * Project: Bomb Defusal Manual for Keep Talking and Nobody Explodes [Vanilla]
 * Section: Morse Code
 */

package bomb.modules.t.translated;

import bomb.tools.data.structures.avl.AVLTree;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;

/**
 * Morse class deals with the Morse Code module.
 */
public class Morse extends TranslationCenter {
    private static final AVLTree<String> CODE_TREE = new AVLTree<>();
    private static final String ABSOLUTE_PATH = System.getProperty("user.dir") +
            "\\src\\main\\resources\\bomb\\modules\\t\\translated\\morsecode.txt";

    public static void init() throws IOException{
        BufferedReader input = Files.newBufferedReader(
                new File(ABSOLUTE_PATH).toPath(), StandardCharsets.UTF_8);
        while (input.ready()) {
            String[] next = input.readLine().split("\\|");
            CODE_TREE.addNode(next[1], next[0]);
        }
    }

    public static String[] decode(String code){
        String test = CODE_TREE.dig(code);
        if (test != null)
            return test.split(",");
        else
            return new String[]{"", "null"};
    }

    /**
     * translate() turns the morse code letters into letters and possible words
     * with that combination of characters.
     *
     * @param sample - the morse code letters
     * @return - [0] - the resulting words found from those letters.
     *           [1] - the letters that match their morse code counter parts.
     */
    public static String[] translate(String sample){
        String[] out = new String[2];
        out[1] = transcode(sample.split(" "));
        out[0] = findWords(out[1]);
        return out;
    }

    private static String transcode(String[] inputs){
        StringBuilder results = new StringBuilder();

        for (String input : inputs) {
            results.append(Arrays.toString(decode(input)).replace(" ", "")).append(", ");
        }

        return results.toString();
    }

    private static String findWords(String letters){
        StringBuilder finalWords = new StringBuilder();
        String[] combos = wordMaker(letters);

        for (String sample : combos) {
            for (Map.Entry<String, Double> tempEntry : TranslationCenter.frequencies.entrySet()) {
                if (tempEntry.getKey().contains(sample.toLowerCase()))
                    finalWords.append(tempEntry.getKey()).append(" - ").append(tempEntry.getValue())
                            .append("MHz").append("/");
            }
        }
        return finalWords.toString();
    }

    private static String[] splitter(String in){
        return in.replace("[", "")
                .replace("]", "").split(",");
    }

    private static String[] wordMaker(String letters){
        //TODO - Split up
        int max = 0;
        String[] letterSplit = letters.split(", ");

        for (String split : letterSplit){
            String[] temp = splitter(split);
            if (max < temp.length){
                max = temp.length;
            }
        }

        StringBuilder[] words = builderInit(max);

        for (String split : letterSplit){
            String[] temp = splitter(split);

            if (temp.length == 1){
                appendToEach(words, temp[0]);
            } else if (temp.length == 2 && words.length == 2){
                words[0].append(temp[0]);
                words[1].append(temp[1]);
            } else if (temp.length == 3 && words.length == 3){
                words[0].append(temp[0]);
                words[1].append(temp[1]);
                words[2].append(temp[2]);
            }
        }

        return stringConvert(words);
    }

    private static void appendToEach(StringBuilder[] array, String letter){
        for (StringBuilder instance : array){
            instance.append(letter);
        }
    }

    private static StringBuilder[] builderInit(int num){
       if (num == 1)
           return new StringBuilder[]{new StringBuilder()};
       else if (num == 2)
           return new StringBuilder[]{new StringBuilder(), new StringBuilder()};
       return new StringBuilder[]{new StringBuilder(), new StringBuilder(), new StringBuilder()};
    }

    private static String[] stringConvert(StringBuilder[] builders){
        String[] out = new String[builders.length];
        for (int i = 0; i < out.length; i++){
            out[i] = builders[i].toString();
        }
        return out;
    }
}
