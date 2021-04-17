package bomb.modules.ab.alphabet;

import bomb.Widget;

import java.util.Arrays;

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
     * @param buttons The given letter order of tiles
     * @return The letters in the way they should be pressed
     */
    public static String order(String buttons){
        StringBuilder output = new StringBuilder();
        buttons = buttons.toUpperCase();

        for (String s : WORD_BANK) {
            boolean[] tests = new boolean[s.length()];
            int j = 0;
            for (char sample : s.toCharArray()) tests[j++] = buttons.indexOf(sample) != -1;

            if (allTrue(tests)) {
                output.append(s);
                buttons = cutOut(buttons, s);
            }
        }
        if (buttons.length() != 0){
            char[] arr = buttons.toCharArray();
            Arrays.sort(arr);
            output.append(arr);
        }
        return output.toString();
    }

    /**
     * Checks the boolean array to see if all are true or not
     *
     * @param check The boolean array
     * @return True if all are true
     */
    private static boolean allTrue(boolean[] check){
        for (boolean instance : check)
            if (!instance) return false;
        return true;
    }

    /**
     * Cuts out the letters that were found by a particular letter pattern in the wordBank
     *
     * @param buttons The String containing the input letters
     * @param toCut The letters that need to be cut out
     * @return The remaining letters of the input String
     */
    private static String cutOut(String buttons, String toCut){
        for (char cut : toCut.toCharArray()) buttons = buttons.replace(String.valueOf(cut), "");
        return buttons;
    }
}
