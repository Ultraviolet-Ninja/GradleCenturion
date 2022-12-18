package bomb.modules.c.color_flash;

import bomb.Widget;
import bomb.annotation.Puzzle;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static bomb.modules.c.color_flash.ColorFlashColor.BLUE;
import static bomb.modules.c.color_flash.ColorFlashColor.GREEN;
import static bomb.modules.c.color_flash.ColorFlashColor.RED;
import static bomb.modules.c.color_flash.ColorFlashColor.WHITE;
import static bomb.tools.string.StringFormat.NO;
import static bomb.tools.string.StringFormat.YES;

/**
 *
 */
@Puzzle(resource = "color_flash.fxml", buttonLinkerName = "Color Flash")
public final class ColorFlash extends Widget {
    private static final int COMBO_LIMIT;

    private static final String RESULT_IS_COLOR_OR_WORD;

    static {
        COMBO_LIMIT = 8;
        RESULT_IS_COLOR_OR_WORD = "is either the word or color";
    }

    /**
     *
     * @param words
     * @param colors
     * @return
     * @throws IllegalArgumentException
     */
    public static @NotNull String decipher(@NotNull ColorFlashColor[] words, @NotNull ColorFlashColor[] colors)
            throws IllegalArgumentException {
        validateInput(words, colors);
        var comboList = forgeCombos(words, colors);
        ColorFlashColor lastColor = comboList.get(comboList.size() - 1).getValue1();

        return switch (lastColor) {
            case RED -> handleLastColorRed(comboList);
            case YELLOW -> handleLastColorYellow(comboList);
            case GREEN -> handleLastColorGreen(comboList);
            case BLUE -> handleLastColorBlue(comboList);
            case MAGENTA -> handleLastColorMagenta(comboList);
            default -> handleLastColorWhite(comboList);
        };
    }

    private static String handleLastColorRed(List<Pair<ColorFlashColor, ColorFlashColor>> comboList) {
        if (countParticularWord(comboList, GREEN) >= 3)
            return YES + " on the third time Green " + RESULT_IS_COLOR_OR_WORD;
        if (countParticularColor(comboList, BLUE) == 1)
            return NO + " when the word Magenta is shown";
        return YES + " the last time White " + RESULT_IS_COLOR_OR_WORD;
    }

    private static String handleLastColorYellow(List<Pair<ColorFlashColor, ColorFlashColor>> comboList) {
        if (doesMatchExists(comboList, GREEN, BLUE))
            return "";
        if (doesMatchExists(comboList, WHITE, WHITE) || doesMatchExists(comboList, RED, WHITE))
            return "";
        return "";
    }

    private static String handleLastColorGreen(List<Pair<ColorFlashColor, ColorFlashColor>> comboList) {
        return "";
    }

    private static String handleLastColorBlue(List<Pair<ColorFlashColor, ColorFlashColor>> comboList) {
        return "";
    }

    private static String handleLastColorMagenta(List<Pair<ColorFlashColor, ColorFlashColor>> comboList) {
        return "";
    }

    private static String handleLastColorWhite(List<Pair<ColorFlashColor, ColorFlashColor>> comboList) {
        return "";
    }

    private static List<Pair<ColorFlashColor, ColorFlashColor>> forgeCombos(ColorFlashColor[] words,
                                                                            ColorFlashColor[] colors) {
        List<Pair<ColorFlashColor, ColorFlashColor>> combos = new ArrayList<>(COMBO_LIMIT);
        for (int i = 0; i < COMBO_LIMIT; i++) {
            combos.add(new Pair<>(words[i], colors[i]));
        }
        return combos;
    }

    private static int countParticularWord(List<Pair<ColorFlashColor, ColorFlashColor>> combos, ColorFlashColor word) {
        return (int) combos.stream()
                .filter(pair -> pair.getValue0() == word)
                .count();
    }

    private static int countParticularColor(List<Pair<ColorFlashColor, ColorFlashColor>> combos, ColorFlashColor color) {
        return (int) combos.stream()
                .filter(pair -> pair.getValue1() == color)
                .count();
    }

    private static boolean doesMatchExists(List<Pair<ColorFlashColor, ColorFlashColor>> combos,
                                           ColorFlashColor color, ColorFlashColor word) {
        return combos.stream()
                .anyMatch(pair -> pair.getValue0() == word && pair.getValue1() == color);
    }

    private static void validateInput(ColorFlashColor[] words, ColorFlashColor[] colors)
            throws IllegalArgumentException {
        if (words.length != colors.length && words.length != COMBO_LIMIT)
            throw new IllegalArgumentException("One of the inputs is not of the specified length: 8");
        for (var word : words) {
            if (word == null)
                throw new IllegalArgumentException("Null element found in words");
        }

        for (var color : colors) {
            if (color == null)
                throw new IllegalArgumentException("Null element found in colors");
        }
    }
}