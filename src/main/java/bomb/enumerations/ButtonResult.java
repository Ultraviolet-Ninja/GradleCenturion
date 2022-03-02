package bomb.enumerations;

import static bomb.tools.string.StringFormat.FIRST_LETTER_CAPITAL;

public enum ButtonResult {
    TAP, HOLD;

    @Override
    public String toString() {
        return FIRST_LETTER_CAPITAL.apply(name());
    }
}
