package bomb.tools;

import java.util.regex.Pattern;

public class Filter {
    public static final String
            VOWEL_REGEX = "[aæąåàeêęèéiîïoôöóøœuûüŭ]+?",
            SERIAL_CODE_REGEX = "\\w{6,6}?",
            NUMBER_REGEX = "\\d+(?:\\.\\d+)?",
            LOGIC_REGEX = "(?:∧|∨|↓|⊻|←|→|↔|\\|)",
            CHAR_REGEX = "[a-zæąåàêęèéîïôöóøœûüŭ]+?",
            NORMAL_CHAR_REGEX = "[a-z0-9æąåàêęèéîïôöóøœûüŭ]+?";



    public static String ultimateFilter(String input, String regexPattern){
        return Regex.quickCompile(regexPattern, input, Pattern.CASE_INSENSITIVE);
    }
}
