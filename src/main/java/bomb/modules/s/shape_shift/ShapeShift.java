package bomb.modules.s.shape_shift;

import bomb.Widget;
import bomb.tools.data.structures.graph.list.ListGraph;
import org.javatuples.Pair;

import java.util.ArrayList;
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
import static bomb.tools.logic.BitConverter.TO_INT;

public class ShapeShift extends Widget {
    private static final int[][] COUNT_TRACKER;

    private static ListGraph<Pair<ShapeEnd, ShapeEnd>> graph;

    //<editor-fold desc="Init methods">
    static {
        int size = ShapeEnd.values().length;
        COUNT_TRACKER = new int[size][size];
        zeroOutArray();
        initializeGraph();
    }

    private static void zeroOutArray() {
        for (ShapeEnd leftSide : ShapeEnd.values()) {
            for (ShapeEnd rightSide : ShapeEnd.values())
                COUNT_TRACKER[leftSide.ordinal()][rightSide.ordinal()] = 0;
        }
    }

    private static void initializeGraph() {
        graph = new ListGraph<>(false);
        initializeTriples();
    }

    private static List<Pair<ShapeEnd, ShapeEnd>> createList() {
        List<Pair<ShapeEnd, ShapeEnd>> list = new ArrayList<>();
        for (ShapeEnd left : ShapeEnd.values()) {
            for (ShapeEnd right : ShapeEnd.values())
                list.add(new Pair<>(left, right));
        }
        return list;
    }

    private static void initializeTriples() {
        List<Pair<ShapeEnd, ShapeEnd>> list = createList();
        initializePairs(list.get(0), list.get(8), list.get(15));
        initializePairs(list.get(1), list.get(10), list.get(15));
        initializePairs(list.get(2), list.get(3), list.get(0));
        initializePairs(list.get(3), list.get(11), list.get(14));
        initializePairs(list.get(4), list.get(2), list.get(9));
        initializePairs(list.get(5), list.get(10), list.get(7));
        initializePairs(list.get(6), list.get(3), list.get(12));
        initializePairs(list.get(7), list.get(0), list.get(1));
        initializePairs(list.get(8), list.get(13), list.get(1));
        initializePairs(list.get(9), list.get(6), list.get(13));
        initializePairs(list.get(10), list.get(4), list.get(6));
        initializePairs(list.get(11), list.get(5), list.get(12));
        initializePairs(list.get(12), list.get(2), list.get(9));
        initializePairs(list.get(13), list.get(4), list.get(7));
        initializePairs(list.get(14), list.get(5), list.get(8));
        initializePairs(list.get(15), list.get(11), list.get(14));
    }

    private static void initializePairs(Pair<ShapeEnd, ShapeEnd> firstPair,
                                        Pair<ShapeEnd, ShapeEnd> secondPair,
                                        Pair<ShapeEnd, ShapeEnd> thirdPair) {
        graph.addEdge(firstPair, secondPair);
        graph.addEdge(firstPair, thirdPair);
    }
    //</editor-fold>

    public static ShapeEnd[] solve(ShapeEnd left, ShapeEnd right) {
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
