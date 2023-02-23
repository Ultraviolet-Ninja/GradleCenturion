package bomb.tools.string;

import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public final class StringFormat {
    public static final char INDEX_ZERO_UPPERCASE_LETTER = 'A',
            INDEX_ZERO_LOWERCASE_LETTER = 'a',
            INDEX_ONE_LOWERCASE_LETTER = '`',
            CONVERT_CHAR_NUMBER_AT_ONE = '1';

    public static final String BULLET_POINT = "\\u2022 ", ARROW = " -> ",
            YES = "Yes", NO = "No";

    public static final UnaryOperator<String> FIRST_LETTER_CAPITAL = sample -> sample.length() > 1 ?
            sample.substring(0, 1).toUpperCase() + sample.substring(1).toLowerCase() :
            sample.toUpperCase();

    public static final UnaryOperator<String> TO_TITLE_CASE = text ->
            stream(text.split("[-_ ]"))
                    .map(FIRST_LETTER_CAPITAL)
                    .collect(joining(" "));

    public static final UnaryOperator<String> TO_SCREAM_SNAKE_CASE = text ->
            stream(text.split("[- ]"))
                    .map(String::toUpperCase)
                    .collect(joining("_"));

    public static @NotNull String createOrdinalNumber(int number) {
        if (number < 0)
            throw new IllegalArgumentException("Number cannot be negative");
        int mod = number % 100;

        if (mod == 1) return number + "st";
        if (mod == 2) return number + "nd";
        if (mod == 3) return number + "rd";
        return number + "th";
    }
}
