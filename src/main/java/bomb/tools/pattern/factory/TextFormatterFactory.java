package bomb.tools.pattern.factory;

import bomb.tools.filter.RegexFilter;
import javafx.scene.control.TextFormatter;

import java.util.function.Function;

public class TextFormatterFactory {
    private static final Function<String, TextFormatter<String>> REGEX_MATCH_FORMATTER = regex ->
            new TextFormatter<>(change -> {
                if (!change.isContentChange()) return change;

                String text = change.getControlNewText();
                if (text.isEmpty() || text.matches(regex)) return change;

                return null;
            });

    public static TextFormatter<String> createSerialCodeFormatter() {
        return new TextFormatter<>(change -> {
            if (!change.isContentChange()) return change;

            String text = change.getControlNewText();
            if (text.contains("\n") || text.length() > 6) return null;

            return change;
        });
    }

    public static TextFormatter<String> createNumbersOnlyFormatter() {
        return new TextFormatter<>(change -> {
            if (!change.isContentChange()) return change;

            String text = change.getControlNewText();
            RegexFilter.NUMBER_PATTERN.loadText(text);
            if (text.isEmpty()) return change;
            return RegexFilter.NUMBER_PATTERN.matchesRegex() ? change : null;
        });
    }

    public static TextFormatter<String> createNewLineRestrictionFormatter() {
        return new TextFormatter<>(change ->
                (!change.isContentChange() || !change.getControlNewText().contains("\n")) ?
                        change :
                        null);
    }

    public static TextFormatter<String> createOneLetterFormatter() {
        return REGEX_MATCH_FORMATTER.apply("\\b[a-zA-Z]\\b");
    }

    public static TextFormatter<String> createTwoDigitTextFormatter() {
        return REGEX_MATCH_FORMATTER.apply("\\b\\d{1,2}\\b");
    }

    public static TextFormatter<String> createChessNotationTextFormatter() {
        return REGEX_MATCH_FORMATTER.apply("\\b[A-Fa-f]-?[1-6]?");
    }

    public static TextFormatter<String> createSixLetterTextFormatter() {
        return REGEX_MATCH_FORMATTER.apply("\\b[a-zA-Z]{1,6}\\b");
    }

    public static TextFormatter<String> createBattleshipCounterTextFormatter() {
        return REGEX_MATCH_FORMATTER.apply("\\b[0-4]\\b");
    }
}
