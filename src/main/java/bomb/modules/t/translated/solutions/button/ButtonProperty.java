package bomb.modules.t.translated.solutions.button;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum ButtonProperty {
    BLUE, RED, YELLOW, WHITE, ABORT, DETONATE, PRESS, HOLD;

    @Contract(value = " -> new", pure = true)
    public static ButtonProperty @NotNull [] getColors() {
        return new ButtonProperty[]{RED, YELLOW, BLUE, WHITE};
    }

    @Contract(value = " -> new", pure = true)
    public static ButtonProperty @NotNull [] getLabels() {
        return new ButtonProperty[]{DETONATE, ABORT, PRESS, HOLD};
    }
}
