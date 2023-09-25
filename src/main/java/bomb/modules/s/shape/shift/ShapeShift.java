package bomb.modules.s.shape.shift;

import bomb.Widget;
import bomb.annotation.DisplayComponent;
import bomb.tools.data.structures.graph.list.ListGraph;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static bomb.enumerations.Indicator.BOB;
import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.FRQ;
import static bomb.enumerations.Indicator.IND;
import static bomb.enumerations.Indicator.MSA;
import static bomb.enumerations.Indicator.SIG;
import static bomb.enumerations.Indicator.SND;
import static bomb.enumerations.Port.DVI;
import static bomb.enumerations.Port.PARALLEL;
import static bomb.enumerations.Port.PS2;
import static bomb.enumerations.Port.RCA;
import static bomb.enumerations.Port.RJ45;
import static bomb.modules.s.shape.shift.ShapeEnd.SHAPE_END_ARRAY;
import static bomb.tools.logic.BitConverter.TO_INT;

@DisplayComponent(resource = "shape_shift.fxml", buttonLinkerName = "Shape Shift")
public final class ShapeShift extends Widget {
    private static final int[][] COUNT_TRACKER;

    private static ListGraph<Pair<ShapeEnd, ShapeEnd>> graph;

    //<editor-fold desc="Init methods">
    static {
        int size = SHAPE_END_ARRAY.length;
        COUNT_TRACKER = new int[size][size];
        zeroOutArray();
        initializeGraph();
    }

    private static void zeroOutArray() {
        for (ShapeEnd leftSide : SHAPE_END_ARRAY) {
            for (ShapeEnd rightSide : SHAPE_END_ARRAY)
                COUNT_TRACKER[leftSide.ordinal()][rightSide.ordinal()] = 0;
        }
    }

    private static void initializeGraph() {
        graph = new ListGraph<>(false);
        initializeTriples();
    }

    private static List<Pair<ShapeEnd, ShapeEnd>> createList() {
        return Arrays.stream(SHAPE_END_ARRAY)
                .flatMap(leftEnd -> Arrays.stream(SHAPE_END_ARRAY)
                        .map(rightEnd -> new Pair<>(leftEnd, rightEnd))
                )
                .toList();
    }

    private static void initializeTriples() {
        List<Pair<ShapeEnd, ShapeEnd>> list = createList();

        int[][] elementIndexTriples = {
                {0, 8, 15}, {1, 10, 15}, {2, 3, 0},
                {3, 11, 14}, {4, 2, 9}, {5, 10, 7},
                {6, 3, 12}, {7, 0, 1}, {8, 13, 1},
                {9, 6, 13}, {10, 4, 6}, {11, 5, 12},
                {12, 2, 9}, {13, 4, 7}, {14, 5, 8},
                {15, 11, 14}
        };

        Arrays.stream(elementIndexTriples)
                .forEach(triple -> initializePairs(list.get(triple[0]), list.get(triple[1]), list.get(triple[2])));
    }

    private static void initializePairs(Pair<ShapeEnd, ShapeEnd> firstPair,
                                        Pair<ShapeEnd, ShapeEnd> secondPair,
                                        Pair<ShapeEnd, ShapeEnd> thirdPair) {
        graph.addEdge(firstPair, secondPair);
        graph.addEdge(firstPair, thirdPair);
    }
    //</editor-fold>

    public static ShapeEnd @NotNull [] solve(@NotNull ShapeEnd left, @NotNull ShapeEnd right) {
        checkSerialCode();
        increment(left, right);
        if (checkIfVisitedTwice(left, right)) {
            Pair<ShapeEnd, ShapeEnd> pair = graph.getTargetVertices(
                            new Pair<>(left, right))
                    .get(TO_INT.apply(conditionMap(left, right)));
            return solve(pair.getValue0(), pair.getValue1());
        }
        resetMod();
        return new ShapeEnd[]{left, right};
    }

    private static void resetMod() {
        zeroOutArray();
    }

    private static void increment(ShapeEnd left, ShapeEnd right) {
        COUNT_TRACKER[left.ordinal()][right.ordinal()] += 1;
    }

    private static boolean checkIfVisitedTwice(ShapeEnd left, ShapeEnd right) {
        return COUNT_TRACKER[left.ordinal()][right.ordinal()] < 2;
    }

    //<editor-fold desc="Boolean Methods">
    private static boolean conditionMap(ShapeEnd left, ShapeEnd right) {
        return switch (left) {
            case ROUND -> roundedOptions(right);
            case FLAT -> rectangularOptions(right);
            case POINT -> triangularOptions(right);
            default -> ticketOptions(right);
        };
    }

    private static boolean roundedOptions(ShapeEnd right) {
        return switch (right) {
            case ROUND -> hasVowelInSerialCode();
            case FLAT -> hasLitIndicator(SND);
            case POINT -> hasLitIndicator(SIG);
            default -> numDoubleAs > 1;
        };
    }

    private static boolean rectangularOptions(ShapeEnd right) {
        return switch (right) {
            case ROUND -> hasMorePortsThanSpecified(DVI, 0);
            case FLAT -> getSerialCodeLastDigit() % 2 == 1;
            case POINT -> hasLitIndicator(MSA);
            default -> hasUnlitIndicator(BOB);
        };
    }

    private static boolean triangularOptions(ShapeEnd right) {
        return switch (right) {
            case ROUND -> hasMorePortsThanSpecified(PARALLEL, 0);
            case FLAT -> hasUnlitIndicator(CAR);
            case POINT -> hasLitIndicator(IND);
            default -> hasMorePortsThanSpecified(RJ45, 0);
        };
    }

    private static boolean ticketOptions(ShapeEnd right) {
        return switch (right) {
            case ROUND -> hasMorePortsThanSpecified(RCA, 0);
            case FLAT -> hasUnlitIndicator(FRQ);
            case POINT -> hasMorePortsThanSpecified(PS2, 0);
            default -> getAllBatteries() >= 3;
        };
    }
    //</editor-fold>
}
