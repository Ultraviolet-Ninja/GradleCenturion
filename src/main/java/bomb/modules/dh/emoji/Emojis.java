package bomb.modules.dh.emoji;

import bomb.interfaces.Index;
import bomb.interfaces.Labeled;

public enum Emojis implements Index, Labeled {
    COLON_CLOSE(0, ":)"), EQUAL_OPEN(1, "=("), OPEN_COLON(2, "(:"),
    CLOSED_EQUAL(3, ")="), COLON_OPEN(4, ":("), CLOSED_COLON(5, "):"),
    EQUAL_CLOSED(6, "=)"), OPEN_EQUAL(7, "(="), COLON_BAR(8, ":|"),
    BAR_COLON(9, "|:");

    private final String label;
    private final int index;

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public int getIdx() {
        return index;
    }

    public static Emojis getEmojiFromText(String incoming){
        for (Emojis emoji : Emojis.values()) {
            if (emoji.label.equals(incoming))
                return emoji;
        }
        return null;
    }

    public static String generateCaptureGroup(){
        StringBuilder sb = new StringBuilder("(");
        Emojis[] emojis = Emojis.values();
        for (int i = 0; i < emojis.length; i++) {
            sb.append(emojis[i].label
                    .replace("(", "\\(")
                    .replace(")", "\\)")
                    .replace("|", "\\|")
            );
            if (i < emojis.length - 1) sb.append("|");
        }
        return sb.append(")").toString();
    }

    Emojis(int index, String label){
        this.index = index;
        this.label = label;
    }
}
