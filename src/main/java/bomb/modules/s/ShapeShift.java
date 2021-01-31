package bomb.modules.s;

import bomb.Widget;
import bomb.enumerations.Indicators;
import bomb.enumerations.Ports;
import bomb.enumerations.ShiftShape;
import bomb.tools.data.structures.graph.list.ListGraph;

import java.util.AbstractMap;
import java.util.ArrayList;

public class ShapeShift extends Widget {
    private static boolean serialCodeCheck = false;
    private static final int[][] countTracker = new int[4][4];
    private static ListGraph<AbstractMap.SimpleEntry<ShiftShape, ShiftShape>> graph;

    static {
        zeroOutArray();
//        initializeGraph();
    }

    public static ShiftShape[] solve(ShiftShape left, ShiftShape right){
        serialCodeChecker();
        increment(left, right);
        if (checkIfTwo(left, right)) {
            AbstractMap.SimpleEntry<ShiftShape, ShiftShape> pair = graph.get(
                    new AbstractMap.SimpleEntry<>(left, right))
                    .get(booleanIntConversion(conditionMap(left, right)));
            return solve(pair.getKey(), pair.getValue());
        }
        return new ShiftShape[]{left, right};
    }

    private static void serialCodeChecker() throws IllegalArgumentException{
        if (!serialCodeCheck) {
            if (serialCode.isEmpty()) throw new IllegalArgumentException("Serial Code is empty");
            serialCodeCheck = true;
        }
    }

    private static void zeroOutArray(){
        for (ShiftShape leftSide : ShiftShape.values()){
            for (ShiftShape rightSide : ShiftShape.values())
                countTracker[leftSide.getIdx()][rightSide.getIdx()] = 0;
        }
    }

    private static void initializeGraph(){
        graph = new ListGraph<>(false);
//        initializePairs();
    }

    private static ArrayList<AbstractMap.SimpleEntry<ShiftShape, ShiftShape>> createList(){
        ArrayList<AbstractMap.SimpleEntry<ShiftShape, ShiftShape>> list = new ArrayList<>();
        for (ShiftShape left : ShiftShape.values()){
            for (ShiftShape right : ShiftShape.values())
                list.add(new AbstractMap.SimpleEntry<>(left, right));
        }
        return list;
    }

    private static void initializePairs(AbstractMap.SimpleEntry<ShiftShape, ShiftShape>[] trios){
        graph.addEdge(trios[0], trios[1]);
        graph.addEdge(trios[0], trios[2]);
    }

    private static void increment(ShiftShape left, ShiftShape right){
        countTracker[left.getIdx()][right.getIdx()] += 1;
    }

    private static boolean checkIfTwo(ShiftShape left, ShiftShape right){
        return countTracker[left.getIdx()][right.getIdx()] > 1;
    }

    private static boolean conditionMap(ShiftShape left, ShiftShape right){
        switch (left){
            case ROUNDED:
                return roundedOptions(right);
            case RECTANGULAR:
                return rectangularOptions(right);
            case TRIANGULAR:
                return triangularOptions(right);
            default:
                return ticketOptions(right);
        }
    }

    private static boolean roundedOptions(ShiftShape right){
        switch (right){
            case ROUNDED:
                return hasVowel();
            case RECTANGULAR:
                return hasLitIndicator(Indicators.SND);
            case TRIANGULAR:
                return hasLitIndicator(Indicators.SIG);
            default:
                return numDoubleAs > 1;
        }
    }

    private static boolean rectangularOptions(ShiftShape right){
        switch (right){
            case ROUNDED:
                return hasMoreThan(Ports.DVI, 0);
            case RECTANGULAR:
                return lastDigit() % 2 == 1;
            case TRIANGULAR:
                return hasLitIndicator(Indicators.MSA);
            default:
                return hasUnlitIndicator(Indicators.BOB);
        }
    }

    private static boolean triangularOptions(ShiftShape right){
        switch (right){
            case ROUNDED:
                return hasMoreThan(Ports.PARALLEL, 0);
            case RECTANGULAR:
                return hasUnlitIndicator(Indicators.CAR);
            case TRIANGULAR:
                return hasLitIndicator(Indicators.IND);
            default:
                return hasMoreThan(Ports.RJ45, 0);
        }
    }

    private static boolean ticketOptions(ShiftShape right){
        switch (right){
            case ROUNDED:
                return hasMoreThan(Ports.RCA, 0);
            case RECTANGULAR:
                return hasUnlitIndicator(Indicators.FRQ);
            case TRIANGULAR:
                return hasMoreThan(Ports.PS2, 0);
            default:
                return getAllBatteries() >= 3;
        }
    }

    private static int booleanIntConversion(boolean bool){
        return bool ? 1 : 0;
    }
}
