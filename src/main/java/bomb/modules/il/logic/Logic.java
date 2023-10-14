package bomb.modules.il.logic;

import bomb.Widget;
import bomb.annotation.DisplayComponent;
import bomb.tools.logic.LogicOperator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static bomb.tools.logic.LogicOperator.IMPLIED_BY;
import static bomb.tools.logic.LogicOperator.IMPLIES;
import static java.util.Arrays.asList;

@DisplayComponent(resource = "logic.fxml", buttonLinkerName = "Logic")
public final class Logic extends Widget {
    public static boolean solve(@NotNull LetterRecord @NotNull[] letters,
                                @NotNull LogicOperator @NotNull[] intermediateOperators,
                                boolean isPriorityOnFirstTwo) throws IllegalArgumentException {
        checkSerialCode();
        List<LetterRecord> letterList = new ArrayList<>(asList(letters));
        List<LogicOperator> operators = new ArrayList<>(asList(intermediateOperators));

        validateInput(letterList, operators);

        if (!isPriorityOnFirstTwo) {
            LetterRecord lastRecord = letterList.removeFirst();
            letterList.add(lastRecord);

            Collections.reverse(operators);
            LogicOperator reversedOperator = operators.get(1);

            if (reversedOperator == IMPLIES) {
                operators.set(1, IMPLIED_BY);
            } else if (reversedOperator == IMPLIED_BY) {
                operators.set(1, IMPLIES);
            }
        }

        boolean firstHalf = operators.getFirst().test(
                letterList.getFirst().getBooleanValue(),
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
