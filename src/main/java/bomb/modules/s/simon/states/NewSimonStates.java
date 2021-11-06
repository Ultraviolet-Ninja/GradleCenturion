package bomb.modules.s.simon.states;

import bomb.Widget;
import bomb.modules.s.simon.SimonColors.States;
import bomb.modules.s.souvenir.Souvenir;

import java.util.EnumSet;

import static bomb.modules.s.simon.SimonColors.States.BLUE;
import static bomb.modules.s.simon.SimonColors.States.GREEN;
import static bomb.modules.s.simon.SimonColors.States.RED;
import static bomb.modules.s.simon.SimonColors.States.YELLOW;
import static bomb.modules.s.simon.states.StageState.FIRST;
import static bomb.tools.string.StringFormat.FIRST_LETTER_CAPITAL;
import static java.util.stream.Collectors.joining;

public class NewSimonStates extends Widget {
    private static final StringBuilder toPress;

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
    }

    public static void setTopLeftButtonColor(States color) {
        topLeftButtonColor = color.ordinal();
    }

    public static String calculateNextStage(EnumSet<States> colorPresses) {
        if (isSouvenirActive)
            Souvenir.addRelic("Simon States - Stage " + currentStage.getIndex(), writeOut(colorPresses));

        String stageContent = switch (currentStage) {
            case FIRST -> "first";
            case SECOND -> "second";
            case THIRD -> "third";
            default -> "forth";
        };

        currentStage = currentStage.nextState();
        return toPress.append(stageContent).toString();
    }

    private static String writeOut(EnumSet<States> colorPresses) {
        return colorPresses.stream()
                .map(color -> FIRST_LETTER_CAPITAL.apply(color.name()))
                .collect(joining(","));
    }

    public static void reset() {
        toPress.setLength(0);
    }

    private enum Priorities {
        HIGHEST, HIGH, LOW, LOWEST
    }
}
