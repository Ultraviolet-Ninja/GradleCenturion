package bomb.tools.string;

import java.util.Arrays;
import java.util.function.UnaryOperator;

import static java.util.stream.Collectors.joining;

public class StringFormat {
    public static final String BULLET_POINT = "\\u2022 ", ARROW = " -> ";
    public static final UnaryOperator<String> FIRST_LETTER_CAPITAL = sample -> sample.length() > 1 ?
            sample.substring(0, 1).toUpperCase() + sample.substring(1).toLowerCase() :
            sample.toUpperCase();

    public static final UnaryOperator<String> FROM_SNAKE_CASE = text ->
            Arrays.stream(text.split("_"))
                    .map(FIRST_LETTER_CAPITAL)
                    .collect(joining(" "));
}
