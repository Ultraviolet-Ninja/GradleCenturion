package bomb.modules.s.switches;

import bomb.Widget;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;

public class Switches extends Widget {
    protected static final byte BIT_LENGTH = 5;
    private static final Set<Byte> FORBIDDEN_MOVES;
    private static final Map<Byte, String[]> SPECIAL_CONDITIONS;

    static {
        Byte[] forbiddenMoveSource = {4, 11, 15, 18, 19, 23, 24, 26, 28, 30};
        FORBIDDEN_MOVES = new HashSet<>(asList(forbiddenMoveSource));
        SPECIAL_CONDITIONS = new HashMap<>();
        setUpSpecialConditions();
    }

    private static void setUpSpecialConditions() {
        Byte[] specialConditionsSource = {12, 20, 27, 31};
        String[][] specialConditionsOutputs = {{"3", "2"}, {"3", "1"}, {"4", "1", "2", "5"}, {"3", "4", "1", "2", "5"}};
        for (int i = 0; i < 4; i++)
            SPECIAL_CONDITIONS.put(specialConditionsSource[i], specialConditionsOutputs[i]);
    }

    public static @NotNull List<String> produceMoveList(byte startingState, byte desiredState)
            throws IllegalArgumentException {
        validateInput(startingState, desiredState);
        List<String> outputList = new ArrayList<>();
        setToZero(outputList, startingState);
        setNeededSwitchesOn(outputList, desiredState);
        removeAdjacentDuplicates(outputList);
        return outputList;
    }

    private static void validateInput(byte startingState, byte desiredState)
            throws IllegalArgumentException {
        if (isForbiddenMove(startingState))
            throw new IllegalArgumentException("The switches are in an illegal position");
        if (isForbiddenMove(desiredState))
            throw new IllegalArgumentException("The lights are in an illegal position");
        if (startingState == desiredState)
            throw new IllegalArgumentException("You're already at the desired state");
        if (inputOutOfRange(startingState) || inputOutOfRange(desiredState))
            throw new IllegalArgumentException("Input was out of range (31 > n >= 0)");
    }

    protected static boolean inputOutOfRange(byte state) {
        return state < 0 || state >= (1 << BIT_LENGTH);
    }

    private static boolean isForbiddenMove(byte currentState) {
        return FORBIDDEN_MOVES.contains(currentState);
    }

    private static boolean isSpecialCondition(byte currentState) {
        return SPECIAL_CONDITIONS.containsKey(currentState);
    }

    private static void setToZero(List<String> moveList, byte startingState) {
        if (isSpecialCondition(startingState)) {
            moveList.addAll(asList(SPECIAL_CONDITIONS.get(startingState)));
            return;
        }

        for (int i = BIT_LENGTH - 1; i >= 0; i--) {
            if (retrieveNBit(startingState, i) == 1) {
                startingState = turnOffN(startingState, i);
                moveList.add(String.valueOf(BIT_LENGTH - i));
            }
        }
    }

    private static void setNeededSwitchesOn(List<String> moveList, byte desiredState) {
        if (isSpecialCondition(desiredState)) {
            List<String> tempList = asList(SPECIAL_CONDITIONS.get(desiredState).clone());
            Collections.reverse(tempList);
            moveList.addAll(tempList);
            return;
        }

        for (int i = 0; i < BIT_LENGTH; i++) {
            if (retrieveNBit(desiredState, i) == 1)
                moveList.add(String.valueOf(BIT_LENGTH - i));
        }
    }

    private static int retrieveNBit(int number, int n) {
        return (number >> n) & 1;
    }

    private static byte turnOffN(int number, int n) {
        return (byte) ((n < 0) ?
                number :
                (number & ~(1 << n)));
    }

    private static void removeAdjacentDuplicates(List<String> moveList) {
        for (int i = 1; i <= BIT_LENGTH; i++) {
            String index = String.valueOf(i);
            if (moveList.indexOf(index) + 1 == moveList.lastIndexOf(index)) {
                moveList.remove(index);
                moveList.remove(index);
                return;
            }
        }
    }
}
