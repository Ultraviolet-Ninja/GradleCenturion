package bomb.modules.s.simon.states;

import bomb.Widget;
import bomb.modules.s.simon.SimonColors.StateColor;
import bomb.modules.s.souvenir.Souvenir;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import static bomb.modules.s.simon.SimonColors.StateColor.BLUE;
import static bomb.modules.s.simon.SimonColors.StateColor.GREEN;
import static bomb.modules.s.simon.SimonColors.StateColor.RED;
import static bomb.modules.s.simon.SimonColors.StateColor.YELLOW;
import static bomb.modules.s.simon.states.StageState.FIRST;
import static bomb.tools.string.StringFormat.FIRST_LETTER_CAPITAL;
import static java.util.Arrays.asList;
import static java.util.Collections.reverse;
import static java.util.stream.Collectors.joining;

public class SimonStates extends Widget {
    private static final int HIGH = 1, LOW = 2, LOWEST = 3;
    private static final List<StateColor> PRESSED_COLOR_HISTORY;
    private static final String ERROR_MESSAGE = "The element does not exist";
    private static final BiPredicate<EnumSet<StateColor>, StateColor> FLASHED, DID_NOT_FLASH;

    private static final StateColor[][] PRIORITY_ORDERS = new StateColor[][]{
            {RED, BLUE, GREEN, YELLOW}, //Highest to Lowest
            {BLUE, YELLOW, RED, GREEN},
            {GREEN, RED, YELLOW, BLUE},
            {YELLOW, GREEN, BLUE, RED}
    };

    private static int dominantColorIndex;
    private static StageState currentStage;

    static {
        dominantColorIndex = -1;
        currentStage = FIRST;

        PRESSED_COLOR_HISTORY = new ArrayList<>();
        FLASHED = AbstractCollection::contains;
        DID_NOT_FLASH = FLASHED.negate();
    }

    public static void setDominantColor(@NotNull StateColor dominantColor) {
        dominantColorIndex = dominantColor.ordinal();
    }

    public static List<StateColor> calculateNextColorPress(@NotNull EnumSet<StateColor> colorsFlashed)
            throws IllegalArgumentException {
        validate(colorsFlashed);
        if (isSouvenirActive)
            Souvenir.addRelic(
                    "Simon States - Stage " + currentStage.getStageNum(),
                    writeOutToSouvenir(colorsFlashed)
            );
        resetHistory();

        StateColor colorToPress = switch (currentStage) {
            case FIRST -> getStageOne(colorsFlashed);
            case SECOND -> getStageTwo(colorsFlashed);
            case THIRD -> getStageThree(colorsFlashed);
            default -> getStageFour(colorsFlashed);
        };
        currentStage = currentStage.nextState();

        PRESSED_COLOR_HISTORY.add(colorToPress);
        return PRESSED_COLOR_HISTORY;
    }

    private static StateColor getStageOne(EnumSet<StateColor> colorsFlashed) {
        return switch (colorsFlashed.size()) {
            case 1 -> getColorFlashed(colorsFlashed);

            case 2 -> DID_NOT_FLASH.test(colorsFlashed, BLUE) ?
                    BLUE :
                    getFirstInOrder(FLASHED, colorsFlashed, getHighestPriorityOrder());

            case 3 -> DID_NOT_FLASH.test(colorsFlashed, RED) ?
                    RED :
                    getFirstInOrder(FLASHED, colorsFlashed, getLowestPriorityOrder());

            default -> getAtPriorityLevel(HIGH);
        };
    }

    private static StateColor getStageTwo(EnumSet<StateColor> colorsFlashed) {
        return switch (colorsFlashed.size()) {
            case 1 -> DID_NOT_FLASH.test(colorsFlashed, BLUE) ? BLUE : YELLOW;

            case 2 -> (FLASHED.test(colorsFlashed, RED) && FLASHED.test(colorsFlashed, BLUE)) ?
                    getFirstInOrder(DID_NOT_FLASH, colorsFlashed, getHighestPriorityOrder()) :
                    getFirstInOrder(DID_NOT_FLASH, colorsFlashed, getLowestPriorityOrder());

            case 3 -> getFirstInOrder(DID_NOT_FLASH, colorsFlashed, getHighestPriorityOrder());
            default -> PRESSED_COLOR_HISTORY.get(0);
        };
    }

    private static StateColor getStageThree(EnumSet<StateColor> colorsFlashed) {
        return switch (colorsFlashed.size()) {
            case 1 -> getColorFlashed(colorsFlashed);

            case 2 -> PRESSED_COLOR_HISTORY.containsAll(colorsFlashed) ?
                    getFirstInOrder(DID_NOT_FLASH, colorsFlashed, getLowestPriorityOrder()) :
                    PRESSED_COLOR_HISTORY.get(0);

            case 3 -> historyContainsAnyFlashed(colorsFlashed) ?
                    getFirstInOrder(//Get the highest priority that has flash and not been pressed
                            FLASHED.and((set, color) -> !PRESSED_COLOR_HISTORY.contains(color)),
                            colorsFlashed,
                            getHighestPriorityOrder()
                    ) :
                    getFirstInOrder(FLASHED, colorsFlashed, getHighestPriorityOrder());
            default -> getAtPriorityLevel(LOW);
        };
    }

    private static boolean historyContainsAnyFlashed(EnumSet<StateColor> colorsFlashed) {
        return PRESSED_COLOR_HISTORY.stream()
                .map(colorsFlashed::contains)
                .reduce((boolOne, boolTwo) -> boolOne || boolTwo)
                .orElse(false);
    }

    private static StateColor getStageFour(EnumSet<StateColor> colorsFlashed) {
        if (areAllPressesDistinct()) {
            EnumSet<StateColor> complement = notInSet(EnumSet.copyOf(PRESSED_COLOR_HISTORY));
            return getColorFlashed(complement);
        }
        int size = colorsFlashed.size();

        if (size == 3) {
            Stream<StateColor> stream = colorsFlashed.stream()
                    .filter(color -> !PRESSED_COLOR_HISTORY.contains(color));
            if (stream.count() == 1)
                return stream.findFirst().orElseThrow(IllegalStateException::new);
        }

        if (size >= 3)
            return getAtPriorityLevel(LOWEST);
        else if (size == 1)
            return getColorFlashed(colorsFlashed);
        return GREEN;
    }

    private static StateColor getColorFlashed(EnumSet<StateColor> colorsFlashed) {
        return colorsFlashed.iterator().next();
    }

    private static StateColor getFirstInOrder(BiPredicate<EnumSet<StateColor>, StateColor> condition,
                                              EnumSet<StateColor> colorsFlashed, List<StateColor> priorityOrder)throws IllegalArgumentException {
        return filter(condition, colorsFlashed, priorityOrder)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ERROR_MESSAGE));
    }

    private static Stream<StateColor> filter(BiPredicate<EnumSet<StateColor>, StateColor> condition,
                                             EnumSet<StateColor> colorsFlashed, List<StateColor> priorityOrder) {
        return priorityOrder.stream()
                .filter(state -> condition.test(colorsFlashed, state));
    }

    private static List<StateColor> getHighestPriorityOrder() {
        return asList(PRIORITY_ORDERS[dominantColorIndex]);
    }

    private static List<StateColor> getLowestPriorityOrder() {
        List<StateColor> highest = getHighestPriorityOrder();
        reverse(highest);
        return highest;
    }

    private static StateColor getAtPriorityLevel(int priorityLevel) {
        return PRIORITY_ORDERS[dominantColorIndex][priorityLevel];
    }

    private static EnumSet<StateColor> notInSet(EnumSet<StateColor> colorsFlashed) {
        return EnumSet.complementOf(colorsFlashed);
    }

    private static boolean areAllPressesDistinct() {
        return PRESSED_COLOR_HISTORY.size() == EnumSet.copyOf(PRESSED_COLOR_HISTORY).size();
    }

    private static void validate(EnumSet<StateColor> colorsFlashed) throws IllegalArgumentException {
        if (dominantColorIndex == -1)
            throw new IllegalArgumentException("Priority has not been set");
        if (colorsFlashed.isEmpty())
            throw new IllegalArgumentException("Empty set is not allowed");
    }

    public static void reset() {
        dominantColorIndex = -1;
        currentStage = FIRST;
        resetHistory();
    }

    private static void resetHistory() {
        if (currentStage == FIRST)
            PRESSED_COLOR_HISTORY.clear();
    }

    private static String writeOutToSouvenir(EnumSet<StateColor> colorsFlashed) {
        return colorsFlashed.stream()
                .map(color -> FIRST_LETTER_CAPITAL.apply(color.name()))
                .collect(joining(" "));
    }
}
