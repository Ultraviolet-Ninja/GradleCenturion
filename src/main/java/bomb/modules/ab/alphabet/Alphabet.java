package bomb.modules.ab.alphabet;

import bomb.Widget;
import bomb.tools.Regex;

import java.util.Arrays;

import static bomb.tools.Filter.ultimateFilter;

/**
 * This class deals with the Alphabet module. The module is comprised of a 2x2 square containing 4 tiles
 * with letters on them. The order in which they're pressed is based on the given sets if the letters appear
 * in those 4 tiles. But if no set matches the 4 tiles, they are to be pressed in alphabetical order.
 */
public class Alphabet extends Widget {
    private static final String[] WORD_BANK = {"JQXZ", "QEW", "AC", "ZNY", "TJL",
            "OKBV", "DFW", "YKQ", "LXE", "GS", "VSI", "PQJS", "VCN", "JR", "IRNM",
            "OP", "QYDX", "HDU", "PKD", "ARGF"};

    /**
     * Arranges the given letters into the order they need to be pressed
     *
     * @param input The given letter order of tiles
     * @return The letters in the way they should be pressed
     */
    public static String order(String input){
        input = input.toUpperCase();
        StringBuilder output = new StringBuilder();
        Regex filterRegex = new Regex("[^" + input + "]");

        for (String letterSet : WORD_BANK){
            if (ultimateFilter(letterSet, filterRegex).isEmpty()){
                output.append(letterSet);
                input = input.replaceAll("[" + letterSet + "]", "");
                if(input.isEmpty())
                    return output.toString();
                filterRegex = new Regex("[^" + input + "]");
            }
        }

        if (!input.isEmpty()){
            char[] arr = input.toCharArray();
            Arrays.sort(arr);
            output.append(arr);
        }

        return output.toString();
    }
}
