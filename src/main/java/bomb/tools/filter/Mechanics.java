package bomb.tools.filter;

import java.util.ArrayList;
import java.util.Arrays;

public class Mechanics {
    public static final String[]
            VOWEL_REGEX = {"a","æ","ą","å","à","e","ê","ę","è","é","i","î",
                    "ï","o","ô","ö","ó","ø","œ","u","û","ü","ŭ"},
            NUMBER_REGEX = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "."},
            LOWERCASE_REGEX = {"a","æ","ą","å","à","b","c","ć","ç","ĉ","d","e",
                    "ê","ę","è","é","f","g","ĝ","h","ĥ","i","î","ï","j","ĵ","k","l",
                    "ł","m","n","ń","ñ","o","ô","ö","ó","ø","œ","p","q","r","s","ŝ","ś",
                    "t","u","û","ü","ŭ","v","w","x","y","z","ź","ż","ß"},
            NORMAL_CHAR_REGEX = {"a","æ","ą","å","à","b","c","ć","ç","ĉ","d","e",
                    "ê","ę","è","é","f","g","ĝ","h","ĥ","i","î","ï","j","ĵ","k","l","ł",
                    "m","n","ń","ñ","o","ô","ö","ó","ø","œ","p","q","r","s","ŝ","ś", "t",
                    "u","û","ü","ŭ","v","w","x","y","z","ź","ż","ß",
                    "0","1","2","3","4","5","6","7","8","9"};

    public static String ultimateFilter(String input, String... exceptions){
        StringBuilder builder = new StringBuilder();
        for (char in : input.toLowerCase().toCharArray()){
            for (String exception : exceptions){
                if (exception.indexOf(in) != -1)
                    builder.append(in);
            }
        }
        return builder.toString();
    }

    public static String ultimateFilter(String input, String[] regex, String...exceptions){
        StringBuilder builder = new StringBuilder();
        ArrayList<String> combine = combine(regex, exceptions);
        input = input.toLowerCase();
        for (char next : input.toCharArray()){
            for (String exception : combine){
                if (exception.indexOf(next) != -1)
                    builder.append(exception);
            }
        }
        return builder.toString();
    }

    private static ArrayList<String> combine (String[] a, String[] b){
        ArrayList<String> out = new ArrayList<>();
        out.addAll(Arrays.asList(a));
        out.addAll(Arrays.asList(b));
        return out;
    }
}