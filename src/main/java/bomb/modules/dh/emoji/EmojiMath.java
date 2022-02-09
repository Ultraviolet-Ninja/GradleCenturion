package bomb.modules.dh.emoji;

import bomb.Widget;
import bomb.tools.filter.Regex;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import static bomb.modules.dh.emoji.Emoji.EMOJI_MAP;

/**
 * This class deals with the Emoji Math module.
 * It's simple math, but replacing numbers with text emojis.
 */
public class EmojiMath extends Widget {
    @Language("regexp")
    private static final String EMOJI_PATTERN, VALIDATION_PATTERN;

    static {
        EMOJI_PATTERN = "(?:[=:][|()]|[|()][=:]){1,2}";
        VALIDATION_PATTERN = "^" + EMOJI_PATTERN + "([+\\-])" + EMOJI_PATTERN + "$";
    }

    /**
     * Calculates the sum/difference from the equation of emojis
     *
     * @param input The equation from the TextField
     * @return The values gathered from the emoji equation
     */
    public static int calculate(@NotNull String input) throws IllegalArgumentException {
        Regex validationRegex = new Regex(VALIDATION_PATTERN, input);
        if (!validationRegex.matchesRegex()) throw new IllegalArgumentException(input + " does not match pattern");

        boolean toAdd = validationRegex.captureGroup(1).equals("+");
        int[] results = convertEmojis(input.split(toAdd ? "\\+" : "-"));

        return toAdd ?
                results[0] + results[1] :
                results[0] - results[1];
    }

    private static int[] convertEmojis(String[] operands) {
        final int[] output = new int[operands.length];
        final StringBuilder tempResult = new StringBuilder();
        int counter = 0;

        for (String operand : operands) {
            if (operand.length() == 4) {
                tempResult.append(EMOJI_MAP.get(operand.substring(0, 2)).ordinal());
                tempResult.append(EMOJI_MAP.get(operand.substring(2)).ordinal());
            } else tempResult.append(EMOJI_MAP.get(operand).ordinal());
            output[counter++] = Integer.parseInt(tempResult.toString());
            tempResult.setLength(0);
        }

        return output;
    }
}