package bomb.tools;

import java.util.regex.Pattern;

public class Filter {
    public static final String LOGIC_REGEX = "(?:∧|∨|↓|⊻|←|→|↔|\\|)";
//            VOWEL_REGEX = "[aæąåàeêęèéiîïoôöóøœuûüŭ]+?",
//            NUMBER_REGEX = "\\d+(?:\\.\\d+)?",
//            CHAR_REGEX = "[a-zæąåàêęèéîïôöóøœûüŭ]+?",
//            NORMAL_CHAR_REGEX = "[a-z0-9æąåàêęèéîïôöóøœûüŭ]+?";

    public static final Regex SERIAL_CODE_PATTERN = new Regex("\\b[a-zA-Z0-9]{6}\\b"),
            NUMBER_PATTERN = new Regex("\\d+(?:\\.\\d+)?");

    public static final Regex VOWEL_FILTER = new Regex("[aæąåàeêęèéiîïoôöóøœuûüŭ]+?", Pattern.CASE_INSENSITIVE),
            LOGIC_SYMBOL_FILTER = new Regex(LOGIC_REGEX),
            CHAR_FILTER = new Regex("[a-zæąåàêęèéîïôöóøœûüŭ]+?", Pattern.CASE_INSENSITIVE),
            ALL_CHAR_FILTER = new Regex("[a-z0-9æąåàêęèéîïôöóøœûüŭ]+?", Pattern.CASE_INSENSITIVE);

    public static String ultimateFilter(String input, Regex pattern){
        pattern.loadText(input);
        return pattern.toNewString();
    }
}
