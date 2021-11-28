package bomb.tools.filter;

import java.util.regex.Pattern;

public class RegexFilter {
    public static final String LOGIC_REGEX = "[∧∨↓⊻←→↔|]";

    public static final Regex SERIAL_CODE_PATTERN = new Regex("\\b[a-zA-Z0-9]{6}\\b"),
            NUMBER_PATTERN = new Regex("\\d+\\.?\\d*"),
            MORSE_CODE_PATTERN = new Regex("^[-. ]+$");

    public static final Regex VOWEL_FILTER = new Regex("[aæąåàeêęèéiîïoôöóøœuûüŭ]+?", Pattern.CASE_INSENSITIVE),
            LOGIC_SYMBOL_FILTER = new Regex(LOGIC_REGEX),
            CHAR_FILTER = new Regex("[a-zæąåàêęèéîïôöóøœûüŭ]+?", Pattern.CASE_INSENSITIVE),
            ALL_CHAR_FILTER = new Regex("[a-z0-9æąåàêęèéîïôöóøœûüŭ]+?", Pattern.CASE_INSENSITIVE);

    public static String filter(String input, Regex pattern) {
        pattern.loadText(input);
        return pattern.createFilteredString();
    }
}
