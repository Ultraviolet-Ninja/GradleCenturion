package bomb.modules.s.simon.states;

import bomb.Widget;
import bomb.modules.s.simon.SimonColors.States;
import bomb.modules.s.souvenir.Souvenir;

import java.util.AbstractCollection;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import static bomb.modules.s.simon.SimonColors.States.BLUE;
import static bomb.modules.s.simon.SimonColors.States.GREEN;
import static bomb.modules.s.simon.SimonColors.States.RED;
import static bomb.modules.s.simon.SimonColors.States.YELLOW;
import static bomb.modules.s.simon.states.StageState.FIRST;
import static bomb.tools.string.StringFormat.FIRST_LETTER_CAPITAL;
import static java.util.Arrays.asList;
import static java.util.Collections.reverse;
import static java.util.stream.Collectors.joining;

public class NewSimonStates extends Widget {
    private static final StringBuilder toPress;
    private static final BiPredicate<EnumSet<States>, States> FLASHED, DID_NOT_FLASH;

    private static final States[][] PRIORITY_ORDERS = new States[][]{
            {RED, BLUE, GREEN, YELLOW}, //Highest to Lowest
            {BLUE, YELLOW, RED, GREEN},
            {GREEN, RED, YELLOW, BLUE},
            {YELLOW, GREEN, BLUE, RED}
    };

    private static int topLeftButtonColor;
    private static StageState currentStage;

    static {
        currentStage = FIRST;
        toPress = new StringBuilder();
        FLASHED = AbstractCollection::contains;
        DID_NOT_FLASH = FLASHED.negate();
    }

    public static void setTopLeftButtonColor(States color) {
        topLeftButtonColor = color.ordinal();
    }

    public static String calculateNextStage(EnumSet<States> colorsFlashed) {
        if (isSouvenirActive)
            Souvenir.addRelic("Simon States - Stage " + currentStage.getIndex(), writeOut(colorsFlashed));

        String stageContent = switch (currentStage) {
            case FIRST -> getStageOne(colorsFlashed);
            case SECOND -> "second";
            case THIRD -> "third";
            default -> "forth";
        };

        currentStage = currentStage.nextState();
        return toPress.append(stageContent).toString();
    }

    private static String getStageOne(EnumSet<States> colorsFlashed) {
        States returnColor = switch (colorsFlashed.size()) {
            case 1 -> colorsFlashed.iterator().next();

            case 2 -> !colorsFlashed.contains(BLUE) ?
                    BLUE :
                    RED;

            case 3 -> !colorsFlashed.contains(RED) ?
                    RED : BLUE;

            case 4 -> YELLOW;
            default -> throw new IllegalArgumentException("Empty set is not allowed");
        };
        return FIRST_LETTER_CAPITAL.apply(returnColor.name());
    }



    private static Stream<States> filter(BiPredicate<EnumSet<States>, States> condition,
                                         EnumSet<States> colorsFlashed, List<States> priorityOrder) {
        return priorityOrder.stream()
                .filter(state -> condition.test(colorsFlashed, state));
    }

    private static States getHighestPriority(EnumSet<States> colorsFlashed) {
        States output = RED;
        for (States current : getHighestPriorityOrder()) {
            if (colorsFlashed.contains(current))
                output = current;
        }
        return output;
    }

    private static States getLowestPriorityNotFlashed(EnumSet<States> colorsFlashed) {
        States output = RED;
        for (States current : getLowestPriorityOrder()) {
            if (!colorsFlashed.contains(current))
                output = current;
        }
        return output;
    }

    private static String writeOut(EnumSet<States> colorsFlashed) {
        return colorsFlashed.stream()
                .map(color -> FIRST_LETTER_CAPITAL.apply(color.name()))
                .collect(joining(" "));
    }

    private static List<States> getHighestPriorityOrder() {
        return asList(PRIORITY_ORDERS[topLeftButtonColor]);
    }

    private static List<States> getLowestPriorityOrder() {
        List<States> highest = getHighestPriorityOrder();
        reverse(highest);
        return highest;
    }

    public static void reset() {
        toPress.setLength(0);
        currentStage = FIRST;
    }

    private enum Priorities {
        HIGHEST, HIGH, LOW, LOWEST
    }
}
