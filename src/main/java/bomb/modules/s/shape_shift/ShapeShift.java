package bomb.modules.s.shape_shift;

import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.tools.data.structures.graph.list.ListGraph;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

//TODO Eventually document the code
public class ShapeShift extends Widget {
    private static final int[][] COUNT_TRACKER = new int[4][4];
    private static final ToIntFunction<Boolean> CONVERTER = bool -> bool ? 1 : 0;

    private static ListGraph<Pair<ShapeEnd, ShapeEnd>> graph;

    //<editor-fold desc="Init methods">
    static {
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

    @SafeVarargs
    private static void initializePairs(Pair<ShapeEnd, ShapeEnd>... trios) {
        graph.addEdge(trios[0], trios[1]);
        graph.addEdge(trios[0], trios[2]);
    }
    //</editor-fold>

    public static ShapeEnd[] solve(ShapeEnd left, ShapeEnd right) {
        serialCodeChecker();
        increment(left, right);
        if (checkIfVisitedTwice(left, right)) {
            Pair<ShapeEnd, ShapeEnd> pair = graph.get(
                            new Pair<>(left, right))
                    .get(CONVERTER.applyAsInt(conditionMap(left, right)));
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
            case ROUND -> hasVowel();
            case FLAT -> hasLitIndicator(Indicator.SND);
            case POINT -> hasLitIndicator(Indicator.SIG);
            default -> numDoubleAs > 1;
        };
    }

    private static boolean rectangularOptions(ShapeEnd right) {
        return switch (right) {
            case ROUND -> hasMoreThan(Port.DVI, 0);
            case FLAT -> lastDigit() % 2 == 1;
            case POINT -> hasLitIndicator(Indicator.MSA);
            default -> hasUnlitIndicator(Indicator.BOB);
        };
    }

    private static boolean triangularOptions(ShapeEnd right) {
        return switch (right) {
            case ROUND -> hasMoreThan(Port.PARALLEL, 0);
            case FLAT -> hasUnlitIndicator(Indicator.CAR);
            case POINT -> hasLitIndicator(Indicator.IND);
            default -> hasMoreThan(Port.RJ45, 0);
        };
    }

    private static boolean ticketOptions(ShapeEnd right) {
        return switch (right) {
            case ROUND -> hasMoreThan(Port.RCA, 0);
            case FLAT -> hasUnlitIndicator(Indicator.FRQ);
            case POINT -> hasMoreThan(Port.PS2, 0);
            default -> getAllBatteries() >= 3;
        };
    }
    //</editor-fold>
}
