package bomb.modules.s.shape_shift;

import bomb.Widget;
import bomb.enumerations.Indicators;
import bomb.enumerations.Ports;
import bomb.tools.data.structures.graph.list.ListGraph;

import java.util.AbstractMap;
import java.util.ArrayList;

//TODO Eventually document the code
public class ShapeShift extends Widget {
    private static final int[][] countTracker = new int[4][4];
    private static ListGraph<AbstractMap.SimpleEntry<ShiftShape, ShiftShape>> graph;

    //<editor-fold desc="Init methods">
    static {
        zeroOutArray();
        initializeGraph();
    }

    private static void zeroOutArray(){
        for (ShiftShape leftSide : ShiftShape.values()){
            for (ShiftShape rightSide : ShiftShape.values())
                countTracker[leftSide.getIdx()][rightSide.getIdx()] = 0;
        }
    }

    private static void initializeGraph(){
        graph = new ListGraph<>(false);
        initializeTriples();
    }

    private static ArrayList<AbstractMap.SimpleEntry<ShiftShape, ShiftShape>> createList(){
        ArrayList<AbstractMap.SimpleEntry<ShiftShape, ShiftShape>> list = new ArrayList<>();
        for (ShiftShape left : ShiftShape.values()){
            for (ShiftShape right : ShiftShape.values())
                list.add(new AbstractMap.SimpleEntry<>(left, right));
        }
        return list;
    }

    private static void initializeTriples(){
        ArrayList<AbstractMap.SimpleEntry<ShiftShape, ShiftShape>> list = createList();
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
    private static void initializePairs(AbstractMap.SimpleEntry<ShiftShape, ShiftShape>... trios){
        graph.addEdge(trios[0], trios[1]);
        graph.addEdge(trios[0], trios[2]);
    }
    //</editor-fold>

    public static ShiftShape[] solve(ShiftShape left, ShiftShape right){
        serialCodeChecker();
        increment(left, right);
        if (checkIfVisitedTwice(left, right)) {
            AbstractMap.SimpleEntry<ShiftShape, ShiftShape> pair = graph.get(
                    new AbstractMap.SimpleEntry<>(left, right))
                    .get(booleanIntConversion(conditionMap(left, right)));
            return solve(pair.getKey(), pair.getValue());
        }
        resetMod();
        return new ShiftShape[]{left, right};
    }

    private static void resetMod(){
        zeroOutArray();
    }

    private static void increment(ShiftShape left, ShiftShape right){
        countTracker[left.getIdx()][right.getIdx()] += 1;
    }

    private static boolean checkIfVisitedTwice(ShiftShape left, ShiftShape right){
        return countTracker[left.getIdx()][right.getIdx()] < 2;
    }

    //<editor-fold desc="Boolean Methods">
    private static boolean conditionMap(ShiftShape left, ShiftShape right){
        switch (left){
            case ROUND:
                return roundedOptions(right);
            case FLAT:
                return rectangularOptions(right);
            case POINT:
                return triangularOptions(right);
            default:
                return ticketOptions(right);
        }
    }

    private static boolean roundedOptions(ShiftShape right){
        switch (right){
            case ROUND:
                return hasVowel();
            case FLAT:
                return hasLitIndicator(Indicators.SND);
            case POINT:
                return hasLitIndicator(Indicators.SIG);
            default:
                return numDoubleAs > 1;
        }
    }

    private static boolean rectangularOptions(ShiftShape right){
        switch (right){
            case ROUND:
                return hasMoreThan(Ports.DVI, 0);
            case FLAT:
                return lastDigit() % 2 == 1;
            case POINT:
                return hasLitIndicator(Indicators.MSA);
            default:
                return hasUnlitIndicator(Indicators.BOB);
        }
    }

    private static boolean triangularOptions(ShiftShape right){
        switch (right){
            case ROUND:
                return hasMoreThan(Ports.PARALLEL, 0);
            case FLAT:
                return hasUnlitIndicator(Indicators.CAR);
            case POINT:
                return hasLitIndicator(Indicators.IND);
            default:
                return hasMoreThan(Ports.RJ45, 0);
        }
    }

    private static boolean ticketOptions(ShiftShape right){
        switch (right){
            case ROUND:
                return hasMoreThan(Ports.RCA, 0);
            case FLAT:
                return hasUnlitIndicator(Indicators.FRQ);
            case POINT:
                return hasMoreThan(Ports.PS2, 0);
            default:
                return getAllBatteries() >= 3;
        }
    }
    //</editor-fold>

    private static int booleanIntConversion(boolean bool){
        return bool ? 1 : 0;
    }
}
