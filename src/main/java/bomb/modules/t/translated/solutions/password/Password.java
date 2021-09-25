package bomb.modules.t.translated.solutions.password;

import java.util.Arrays;
import java.util.List;

public class Password {
    private static String[] passwords;

    static {
        passwords = null;
    }

    public static void setPossiblePasswords(String[] input) {
        passwords = input;
    }

    public static List<String> getPasswords(String[] letterSets) {
        if (passwords == null) throw new IllegalArgumentException("Please set a language to start");
        List<String> outputPasswords = Arrays.asList(passwords.clone());

        for (int i = 0; i < letterSets.length; i++) {
            if (!letterSets[i].isEmpty())
                filterByLetterSet(outputPasswords, letterSets[i], i);
        }

        if (outputPasswords.isEmpty())
            outputPasswords.add("No passwords found");

        return outputPasswords;
    }

    private static void filterByLetterSet(List<String> passwordList, String letterSet, int index) {
        passwordList.removeIf(password ->
                letterSet.indexOf(password.charAt(index)) != -1
        );
    }
}
