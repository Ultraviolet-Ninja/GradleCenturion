package bomb.tools.pattern.factory;

import bomb.tools.filter.Filter;
import javafx.scene.control.TextFormatter;

public class TextFormatterFactory {
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
            Filter.NUMBER_PATTERN.loadText(text);
            if (text.isEmpty()) return change;
            return Filter.NUMBER_PATTERN.matchesRegex() ? change : null;
        });
    }

    public static TextFormatter<String> createNewLineRestrictionFormatter() {
        return new TextFormatter<>(change ->
                (!change.isContentChange() || !change.getControlNewText().contains("\n")) ?
                        change :
                        null);
    }

    public static TextFormatter<String> createOneLetterFormatter(){
        return new TextFormatter<>(change -> {
            if (!change.isContentChange()) return change;

            String text = change.getControlNewText();
            if (text.isEmpty() || text.matches("\\b[a-zA-Z]\\b")) return change;

            return null;
        });
    }

    public static TextFormatter<String> createTwoBitTextFormatter(){
        return new TextFormatter<>(change -> {
            if (!change.isContentChange()) return change;

            String text = change.getControlNewText();
            if (text.isEmpty() || text.matches("\\b\\d{1,2}\\b"))
                return change;

            return null;
        });
    }
}
