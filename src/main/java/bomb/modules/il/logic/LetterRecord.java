package bomb.modules.il.logic;

import org.jetbrains.annotations.NotNull;

import static bomb.tools.logic.LogicOperator.XOR;

public record LetterRecord(boolean isNegated, @NotNull LogicLetter letter) {
    public boolean getBooleanValue() {
        return XOR.test(isNegated, letter.getAsBoolean());
    }
}
