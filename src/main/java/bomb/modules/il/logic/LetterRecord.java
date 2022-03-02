package bomb.modules.il.logic;

import org.jetbrains.annotations.NotNull;

public record LetterRecord(boolean isNegated, @NotNull LogicLetter letter) {
    public boolean getBooleanValue() {
        return ((isNegated && !letter.getAsBoolean()) ||
                (!isNegated && letter.getAsBoolean()));
    }
}
