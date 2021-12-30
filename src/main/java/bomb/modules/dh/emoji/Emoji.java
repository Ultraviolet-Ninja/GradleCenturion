package bomb.modules.dh.emoji;

import bomb.abstractions.Labeled;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public enum Emoji implements Labeled {
    COLON_CLOSE(":)"), EQUAL_OPEN("=("), OPEN_COLON("(:"),
    CLOSED_EQUAL(")="), COLON_OPEN(":("), CLOSED_COLON("):"),
    EQUAL_CLOSED("=)"), OPEN_EQUAL("(="), COLON_BAR(":|"),
    BAR_COLON("|:");

    static final Emoji[] EMOJI_ARRAY = values();

    private final String label;

    @Override
    public String getLabel() {
        return label;
    }

    public static Emoji getEmojiFromText(String incoming) {
        return stream(EMOJI_ARRAY)
                .filter(emoji -> emoji.label.equals(incoming))
                .findFirst()
                .orElse(null);
    }

    Emoji(String label) {
        this.label = label;
    }
}
