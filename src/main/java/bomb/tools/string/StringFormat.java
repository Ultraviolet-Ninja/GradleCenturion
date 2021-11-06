package bomb.tools.string;

import java.util.function.UnaryOperator;

public class StringFormat {
    public static final String BULLET_POINT = "\\u2022 ";
    public static final UnaryOperator<String> FIRST_LETTER_CAPITAL = sample -> sample.length() > 1 ?
            sample.substring(0, 1).toUpperCase() + sample.substring(1).toLowerCase() :
            sample.toUpperCase();
}
