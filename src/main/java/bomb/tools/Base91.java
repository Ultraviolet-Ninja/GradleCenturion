package bomb.tools;

public class Base91 {
    private static final char[] CODEX_STRING =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&()*+,./;<=>?@[]^_`{|}~\":"
                    .toCharArray();

    public static String encrypt(int val) {
        if (val < CODEX_STRING.length) return String.valueOf(CODEX_STRING[val]);

        StringBuilder builder = new StringBuilder();
        while (val >= CODEX_STRING.length) {
            int nextVal = val / CODEX_STRING.length;
            int mod = val % CODEX_STRING.length;
            builder.append(CODEX_STRING[mod]);
            val = nextVal;
        }
        return builder.append(CODEX_STRING[val]).reverse().toString();
    }

    public static long decrypt(String val) throws IllegalArgumentException {
        int degree = val.length() - 1;
        long output = 0;
        String searchString = String.valueOf(CODEX_STRING);

        for (char charInVal : val.toCharArray()) {
            int tempNum = searchString.indexOf(charInVal);
            if (tempNum == -1) throw new IllegalArgumentException();
            output += (long) Math.pow(CODEX_STRING.length, degree--) * tempNum;
        }
        return output;
    }
}
