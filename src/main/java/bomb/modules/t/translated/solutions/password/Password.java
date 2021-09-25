package bomb.modules.t.translated.solutions.password;

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


        return null;
    }
}
