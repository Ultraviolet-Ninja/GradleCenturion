package bomb.modules.ab.alphabet;

import bomb.Widget;
import bomb.annotation.DisplayComponent;
import bomb.tools.filter.Regex;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static bomb.tools.filter.Regex.CREATE_NEGATED_SET;
import static bomb.tools.filter.RegexFilter.EMPTY_FILTER_RESULTS;

/**
 * This class deals with the Alphabet module. The module comprises a 2x2 square containing 4 tiles
 * with letters on them. The order in which they're pressed is based on the given sets in the {@link #WORD_BANK}
 * if the letters appear in those 4 tiles.
 * But if no set matches the 4 tiles, the tiles are to be pressed in alphabetical order.
 */
@DisplayComponent(resource = "alphabet.fxml", buttonLinkerName = "Alphabet")
public final class Alphabet extends Widget {
    private static final String[] WORD_BANK;

    static {
        WORD_BANK = new String[]{"JQXZ", "QEW", "AC", "ZNY", "TJL", "OKBV", "DFW", "YKQ", "LXE", "GS",
                "VSI", "PQJS", "VCN", "JR", "IRNM", "OP", "QYDX", "HDU", "PKD", "ARGF"};
    }

    /**
     * Arranges the given letters into the order they need to be pressed
     *
     * @param input The given letter order of tiles
     * @return The letters in the way they should be pressed
     */
    public static @NotNull String order(@NotNull String input) throws IllegalArgumentException {
        validateInput(input);
        input = input.toUpperCase();
        StringBuilder output = new StringBuilder();
        Regex filterRegex = CREATE_NEGATED_SET.apply(input);

        for (String letterSet : WORD_BANK) {
            if (EMPTY_FILTER_RESULTS.test(letterSet, filterRegex)) {
                output.append(letterSet);
                input = input.replaceAll("[" + letterSet + "]", "");
                if (input.isEmpty())
                    return output.toString();
                filterRegex = CREATE_NEGATED_SET.apply(input);
            }
        }

        if (!input.isEmpty()) {
            char[] arr = input.toCharArray();
            Arrays.sort(arr);
            output.append(arr);
        }

        return output.toString();
    }

    private static void validateInput(String input) throws IllegalArgumentException {
        if (!input.matches("[a-zA-Z]{4}"))
            throw new IllegalArgumentException("Input is not 4 letters");
        if (hasRepeatedCharacters(input))
            throw new IllegalArgumentException("Input can't have repeated characters");
    }

    private static boolean hasRepeatedCharacters(String input) {
        Set<Character> set = new HashSet<>();
        for (char letter : input.toCharArray()) {
            if (set.contains(letter))
                return true;
            set.add(letter);
        }
        return false;
    }
}
