package bomb.modules.dh.hexamaze.hexalgorithm.maze_finding;

import bomb.modules.dh.hexamaze.hexalgorithm.AbstractHexagon;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure.HexNode;
import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import bomb.tools.data.structures.BufferedQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class HexHashLibrary {
    public static final int HASH_STRING_SHAPES = 0, HASH_STRING_WALLS = 1;

    /**
     * Don't let anyone instantiate this class
     */
    private HexHashLibrary(){}

    public static void initialize(Maze fullMaze, int userGridSpan) {
        int iterations = fullMaze.getSpan() - userGridSpan;
        ArrayList<Integer> columnList = new ArrayList<>();
        for (int i = 0; i <= iterations; i++)
            columnList.add(i);

        ForkJoinPool mazePool = new ForkJoinPool(2);
        HashingThread task = new HashingThread(columnList, fullMaze, userGridSpan);
        mazePool.invoke(task);
    }

    public static HexGrid find(HexGrid userGrid){
        String wallHash = HashingThread.library().get(userGrid.hexport().hashString());

        if (wallHash == null) return null;
        else {
            userGrid.addWallsToHexagon(wallHash);
            return userGrid;
        }
    }
}

class HashingThread extends RecursiveAction {
    private static final ConcurrentHashMap<String, String> PILE = new ConcurrentHashMap<>();

    private final Maze maze;
    private final int userGridSpan;
    private final List<Integer> colList;

    @Override
    protected void compute() {
        if (colList.size() != 1){
            ArrayList<ArrayList<Integer>> splitList = splitList(colList);
            HashingThread taskOne = new HashingThread(splitList.get(0), maze, userGridSpan);
            HashingThread taskTwo = new HashingThread(splitList.get(1), maze, userGridSpan);

            invokeAll(taskOne, taskTwo);
        } else sequentialWork(colList.get(0));
    }

    private ArrayList<ArrayList<Integer>> splitList(List<Integer> list){
        int split = list.size() / 2;
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();
        output.add(new ArrayList<>(list.subList(0, split)));
        output.add(new ArrayList<>(list.subList(split, list.size())));
        return output;
    }

    public HashingThread(ArrayList<Integer> columnList, Maze maze, int userGridSpan){
        colList = columnList;
        this.maze = maze;
        this.userGridSpan = userGridSpan;
    }

    public static ConcurrentHashMap<String, String> library(){
        return PILE;
    }

    private void sequentialWork(int col){
        BufferedQueue<BufferedQueue<HexNode>> columns = getColumns(userGridSpan, col);
        int[] startPositions = calculateStartPositions(columns);
        runColumns(columns, startPositions);
    }

    private BufferedQueue<BufferedQueue<HexNode>> getColumns(int iteratorSize, int startColumn){
        BufferedQueue<BufferedQueue<HexNode>> strippedMaze = maze.exportTo2DQueue();
        BufferedQueue<BufferedQueue<HexNode>> outputColumns = new BufferedQueue<>(iteratorSize);
        for (int i = startColumn; i < iteratorSize + startColumn; i++)
            outputColumns.add(deepCopyList(strippedMaze.get(i)));

        return outputColumns;
    }

    private static BufferedQueue<HexNode> deepCopyList(BufferedQueue<HexNode> input) {
        BufferedQueue<HexNode> output = new BufferedQueue<>(input.cap());
        for (int i = 0; i < output.cap(); i++)
            output.add(new HexNode(input.get(i)));
        return output;
    }

    private static int[] calculateStartPositions(BufferedQueue<BufferedQueue<HexNode>> columns){
        int[] positions = new int[columns.cap()];
        int middleValue = columns.get(columns.cap()/2).cap();
        for(int i = 0; i < columns.cap(); i++){
            int placeholderValue = columns.get(i).cap() - middleValue;
            positions[i] = Math.max(placeholderValue, 0);
        }

        if (arrayIsAllZero(positions))
            return positions;

        return verifyPositiveValues(positions, leftHalfHasValues(positions));
    }

    private static boolean arrayIsAllZero(int[] array){
        for (int val : array) {
            if (val != 0) return false;
        }
        return true;
    }

    private static boolean leftHalfHasValues(int[] array){
        for (int i = 0; i < array.length/2; i++){
            if (array[i] != 0) return true;
        }
        return false;
    }

    private static int[] verifyPositiveValues(int[] positions, boolean leftSideHasValues){
        if (leftSideHasValues){
            for (int i = positions.length/2 - 1; i > 0; i--){
                if (positions[i - 1] < positions[i]) positions[i - 1] = positions[i];
            }
            return positions;
        }

        for (int i = positions.length/2 + 1; i < positions.length - 1; i++){
            if (positions[i] > positions[i + 1]) positions[i + 1] = positions[i];
        }
        return positions;
    }

    private void runColumns(BufferedQueue<BufferedQueue<HexNode>> columns, int[] startPositions){
        final int hexagonalSideLength = (startPositions.length + 1) / 2;
        final int[] travelDistances = AbstractHexagon.calculateColumnLengths(hexagonalSideLength);
        int[] endPositions = addArrays(travelDistances, startPositions);

        while(notDone(columns, endPositions)){
            HexagonDataStructure currentIterator = new HexagonDataStructure(
                    retrieveHexagon(startPositions, travelDistances, endPositions, columns),
                    hexagonalSideLength);
            HexGrid current = new HexGrid(currentIterator);
            for (int i = 0; i < 6; i++){
                ArrayList<String> temp = current.hashStrings();
                PILE.put(temp.get(HexHashLibrary.HASH_STRING_SHAPES), temp.get(HexHashLibrary.HASH_STRING_WALLS));
                current.rotate();
            }

            incrementArray(startPositions);
            incrementArray(endPositions);
        }
    }

    private static void incrementArray(int[] array) {
        for (int idx = 0; idx < array.length; idx++) array[idx] += 1;
    }

    private boolean notDone(BufferedQueue<BufferedQueue<HexNode>> columns, int[] endPositions){
        for (int i = 0; i < columns.cap(); i++)
            if (endPositions[i] > columns.get(i).cap()) return false;
        return true;
    }

    private BufferedQueue<BufferedQueue<HexNode>> retrieveHexagon
            (int[] startPositions, int[] travelDistances, int[] endPositions,
             BufferedQueue<BufferedQueue<HexNode>> columns){
        BufferedQueue<BufferedQueue<HexNode>> hexagon = new BufferedQueue<>(travelDistances.length);
        for (int i = 0; i < startPositions.length; i++){
            BufferedQueue<HexNode> inputColumn = new BufferedQueue<>(travelDistances[i]);
            for (int j = startPositions[i]; j < endPositions[i]; j++)
                inputColumn.add(columns.get(i).get(j));
            hexagon.add(inputColumn);
        }

        return hexagon;
    }

    private int[] addArrays(int[] arrayOne, int[] arrayTwo){
        int[] output = new int[arrayOne.length];
        for (int i = 0; i < arrayOne.length; i++)
            output[i] = arrayOne[i] + arrayTwo[i];
        return output;
    }
}
