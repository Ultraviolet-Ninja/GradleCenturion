package bomb.modules.t.translated.solutions.password;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Password {
    static final String EMPTY_RESULTS = "No passwords found";
    private static String[] passwords;

    static {
        passwords = null;
    }

    public static void setPossiblePasswords(String[] input) {
        passwords = input;
    }

    public static List<String> getPasswords(String[] letterSets) throws IllegalArgumentException {
        if (passwords == null) throw new IllegalArgumentException("Please set a language to start");
        if (areAllEmpty(letterSets)) return Collections.emptyList();

        var outputPasswords = new ArrayList<>(List.of(passwords.clone()));

        for (int i = 0; i < letterSets.length && !outputPasswords.isEmpty(); i++) {
            if (!letterSets[i].isEmpty())
                filterByLetterSet(outputPasswords, letterSets[i], i);
        }

        if (outputPasswords.isEmpty())
            return Collections.singletonList(EMPTY_RESULTS);

        return outputPasswords;
    }

    private static void filterByLetterSet(List<String> passwordList, String letterSet, int index) {
        passwordList.removeIf(password ->
                letterSet.indexOf(password.charAt(index)) == -1
        );
    }

    private static boolean areAllEmpty(String[] arrays) {
        if (arrays == null) return true;
        for (String instance : arrays) {
            if (!instance.isEmpty())
                return false;
        }
        return true;
    }
}
