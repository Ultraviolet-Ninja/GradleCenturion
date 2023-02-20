package bomb.tools.logic;

import java.util.function.IntPredicate;

public final class BitConverter {
    public static final IntPredicate TO_BOOL = value -> value == 1;
    public static final BitFunction TO_INT = bool -> bool ? 1 : 0;

    private BitConverter(){}

    @FunctionalInterface
    public interface BitFunction {
        int apply(boolean value);
    }
}
