package bomb.tools.filter;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class Mechanics {
    public static final String[]
            VOWEL_REGEX = {"a", "æ", "ą", "å", "à", "e", "ê", "ę", "è", "é", "i", "î",
            "ï", "o", "ô", "ö", "ó", "ø", "œ", "u", "û", "ü", "ŭ"},
            NUMBER_REGEX = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "."},
            LOWERCASE_REGEX = {"a", "æ", "ą", "å", "à", "b", "c", "ć", "ç", "ĉ", "d", "e",
                    "ê", "ę", "è", "é", "f", "g", "ĝ", "h", "ĥ", "i", "î", "ï", "j", "ĵ", "k", "l",
                    "ł", "m", "n", "ń", "ñ", "o", "ô", "ö", "ó", "ø", "œ", "p", "q", "r", "s", "ŝ", "ś",
                    "t", "u", "û", "ü", "ŭ", "v", "w", "x", "y", "z", "ź", "ż", "ß"},
            NORMAL_CHAR_REGEX = {"a", "æ", "ą", "å", "à", "b", "c", "ć", "ç", "ĉ", "d", "e",
                    "ê", "ę", "è", "é", "f", "g", "ĝ", "h", "ĥ", "i", "î", "ï", "j", "ĵ", "k", "l", "ł",
                    "m", "n", "ń", "ñ", "o", "ô", "ö", "ó", "ø", "œ", "p", "q", "r", "s", "ŝ", "ś", "t",
                    "u", "û", "ü", "ŭ", "v", "w", "x", "y", "z", "ź", "ż", "ß",
                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    private static final BiFunction<String, Set<String>, String> FILTER_LETTERS =
        (input, passableLetters) ->
                stream(input.split(""))
                .filter(letter -> passableLetters.contains(letter.toLowerCase()))
                .collect(joining(""));

    public static String ultimateFilter(String input, String... exceptions) {
        Set<String> passableLetters = new TreeSet<>(asList(exceptions));

        return FILTER_LETTERS.apply(input, passableLetters);
    }

    public static String ultimateFilter(String input, String[] regex, String... exceptions) {
        Set<String> passableLetters = new TreeSet<>(combine(regex, exceptions));

        return FILTER_LETTERS.apply(input, passableLetters);
    }

    private static ArrayList<String> combine(String[] a, String[] b) {
        ArrayList<String> out = new ArrayList<>();
        out.addAll(asList(a));
        out.addAll(asList(b));
        return out;
    }
}
