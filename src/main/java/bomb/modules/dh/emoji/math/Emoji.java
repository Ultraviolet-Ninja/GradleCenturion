package bomb.modules.dh.emoji.math;

import bomb.abstractions.Labeled;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

public enum Emoji implements Labeled {
    COLON_CLOSE(":)"), EQUAL_OPEN("=("), OPEN_COLON("(:"),
    CLOSED_EQUAL(")="), COLON_OPEN(":("), CLOSED_COLON("):"),
    EQUAL_CLOSED("=)"), OPEN_EQUAL("(="), COLON_BAR(":|"),
    BAR_COLON("|:");

    public static final Map<String, Emoji> EMOJI_MAP;

    static {
        EMOJI_MAP = stream(values())
                .collect(toUnmodifiableMap(
                        emoji -> emoji.label,
                        identity()
                ));
    }

    private final String label;

    @Override
    public String getLabel() {
        return label;
    }

    Emoji(String label) {
        this.label = label;
    }
}
