/*
 * Author: Ultraviolet-Ninja
 * Project: Bomb Defusal Manual for Keep Talking and Nobody Explodes [Vanilla]
 * Section: Password
 */

package bomb.modules.t.translated;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Password class refers to the Password module
 */
@Deprecated
public final class Passwords extends TranslationCenter {

    /**
     * solve() solves the password module from gathering the letters of any column and
     * compares the possible passwords to find ones that are possible based on the given combinations.
     *
     * @param a - The letters of each column set. This can range from 1 to 5 sets of letters
     * @return - The possible passwords
     */
    public static String solve(String... a) {
        //TODO - Needs to be split up
        ArrayList<String> torchBringer;
        StringBuilder solutions = new StringBuilder();

        if (!testAllEmpty(a)) {
            if (!a[0].isEmpty()) {
                torchBringer = trial(0, a[0], passwords);
            } else {
                torchBringer = new ArrayList<>(Arrays.asList(passwords));
            }

            for (int i = 1; i < a.length; i++) {
                if (!a[i].isEmpty()) {
                    torchBringer = trial(i, a[i], converter(torchBringer));
                }
            }

            if (torchBringer.isEmpty()) {
                solutions.append("No results found");
            } else {
                torchBringer = removeDoubles(torchBringer);
                Collections.sort(torchBringer);
                for (String password : torchBringer) {
                    solutions.append(password).append("\n");
                }
            }
        } else {
            solutions.append("You realize I need inputs to work, right?");
        }
        return solutions.toString();
    }

    private static ArrayList<String> trial(int position, String lineSet, String[] compareTo) {
        ArrayList<String> passed = new ArrayList<>();

        for (int i = 0; i < lineSet.length(); i++) {
            for (String compare : compareTo) {
                if (matched(position, lineSet.charAt(i), compare)) {
                    passed.add(compare);
                }
            }
        }
        return passed;
    }

    private static boolean matched(int position, char compare, String sample) {
        return compare == sample.charAt(position);
    }

    private static String[] converter(ArrayList<String> in) {
        String[] out = new String[in.size()];

        for (int i = 0; i < in.size(); i++) {
            out[i] = in.get(i);
        }
        return out;
    }

    private static ArrayList<String> removeDoubles(ArrayList<String> old) {
        ArrayList<String> newList = new ArrayList<>();

        for (String toNext : old) {
            if (!newList.contains(toNext)) {
                newList.add(toNext);
            }
        }
        return newList;
    }

    private static boolean testAllEmpty(String[] arrays) {
        for (String instance : arrays) {
            if (!instance.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
