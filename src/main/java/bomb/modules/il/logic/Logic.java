package bomb.modules.il.logic;

import bomb.Widget;
import bomb.tools.logic.LogicOperator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class Logic extends Widget {
    public static boolean solve(@NotNull LetterRecord @NotNull[] letters,
                                @NotNull LogicOperator @NotNull[] intermediateOperators,
                                boolean isPriorityOnFirstTwo) throws IllegalArgumentException {
        checkSerialCode();
        List<LetterRecord> letterList = new ArrayList<>(asList(letters));
        List<LogicOperator> operators = new ArrayList<>(asList(intermediateOperators));

        validateInput(letterList, operators);
        if (!isPriorityOnFirstTwo) {
            Collections.reverse(operators);
            LetterRecord lastRecord = letterList.remove(0);
            letterList.add(lastRecord);
        }

        boolean firstHalf = operators.get(0).test(
                letterList.get(0).getBooleanValue(),
                letterList.get(1).getBooleanValue()
        );

        return operators.get(1).test(
                firstHalf,
                letterList.get(2).getBooleanValue()
        );
    }

    private static void validateInput(List<LetterRecord> letters, List<LogicOperator> intermediateOperators)
            throws IllegalArgumentException {
        if (letters.size() != 3)
            throw new IllegalArgumentException("There must be 3 letters in the array");

        if (intermediateOperators.size() != 2)
            throw new IllegalArgumentException("There must be 2 operators in the array");
    }
}
