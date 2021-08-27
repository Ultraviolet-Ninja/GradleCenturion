package bomb.modules.dh.emoji;

import bomb.abstractions.Labeled;

public enum Emojis implements Labeled {
    COLON_CLOSE(":)"), EQUAL_OPEN("=("), OPEN_COLON("(:"),
    CLOSED_EQUAL(")="), COLON_OPEN(":("), CLOSED_COLON("):"),
    EQUAL_CLOSED("=)"), OPEN_EQUAL("(="), COLON_BAR(":|"),
    BAR_COLON("|:");

    private final String label;

    @Override
    public String getLabel() {
        return label;
    }

    public static Emojis getEmojiFromText(String incoming) {
        for (Emojis emoji : Emojis.values()) {
            if (emoji.label.equals(incoming))
                return emoji;
        }
        return null;
    }

    public static String generateCaptureGroup() {
        StringBuilder sb = new StringBuilder("(?:");
        Emojis[] emojis = Emojis.values();
        for (int i = 0; i < emojis.length; i++) {
            sb.append(emojis[i].label
                    .replace("(", "\\(")
                    .replace(")", "\\)")
                    .replace("|", "\\|")
            );
            if (i < emojis.length - 1) sb.append("|");
        }
        return sb.append("){1,2}").toString();
    }

    Emojis(String label) {
        this.label = label;
    }
}
