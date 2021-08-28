package bomb.modules.s.switches;

import java.util.ArrayList;
import java.util.List;

public class Switches {
    private static final byte[] FORBIDDEN_MOVES, SPECIAL_CONDITIONS;

    static {
        FORBIDDEN_MOVES = new byte[]{4, 11, 15, 18, 19, 23, 24, 26, 28, 30};
        SPECIAL_CONDITIONS = new byte[]{12, 20, 27, 31};
    }

    public static List<Byte> produceMoveList(byte currentState, byte desiredState) {
        List<Byte> outputList = new ArrayList<>();
        return null;
    }

    private static boolean isForbiddenMove(byte currentState) {
        for (byte forbiddenMove : FORBIDDEN_MOVES)
            if (currentState == forbiddenMove)
                return true;
        return false;
    }
}
