package bomb.tools.filter;

import java.util.function.BiPredicate;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class RegexFilter {
    public static BiPredicate<String, Regex> EMPTY_FILTER = (text, regex) ->
            filter(text, regex).isEmpty();

    public static final String LOGIC_REGEX = "[∧∨↓⊻←→↔|]";

    public static final Regex SERIAL_CODE_PATTERN = new Regex("\\b[^yY]{2}\\d[^yY0-9]{2}\\d\\b"),
            NUMBER_PATTERN = new Regex("\\d+\\.?\\d*"),
            MORSE_CODE_PATTERN = new Regex("^[-. ]+$");

    public static final Regex VOWEL_FILTER = new Regex("[aæąåàeêęèéiîïoôöóøœuûüŭ]+?", CASE_INSENSITIVE),
            LOGIC_SYMBOL_FILTER = new Regex(LOGIC_REGEX),
            CHAR_FILTER = new Regex("[a-zæąåàêęèéîïôöóøœûüŭ]+?", CASE_INSENSITIVE),
            ALL_CHAR_FILTER = new Regex("[a-z0-9æąåàêęèéîïôöóøœûüŭ]+?", CASE_INSENSITIVE);

    public static String filter(String input, Regex pattern) {
        pattern.loadText(input);
        return pattern.createFilteredString();
    }
}
