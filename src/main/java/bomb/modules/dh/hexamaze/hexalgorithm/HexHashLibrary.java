package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.tools.data.structures.FixedArrayQueue;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static void initialize(Maze fullMaze, HexGrid grid){
        int iterations = fullMaze.hexport().getSpan() - grid.hexport().getSpan();
        ArrayList<Integer> columnList = new ArrayList<>();
        for (int i = 0; i <= iterations; i++)
            columnList.add(i);

        ForkJoinPool mazePool = new ForkJoinPool();
        HashingThread task = new HashingThread(columnList, fullMaze, grid);
        mazePool.invoke(task);
    }

    public static HexGrid find(HexGrid userGrid){
        String shapeHash = userGrid.hashStrings().get(HexHashLibrary.HASH_STRING_SHAPES);
        String wallHash = HashingThread.library().get(shapeHash);
        return null;

//        if (hashOut == null) return null;
//        else return new HexGrid(hashOut);
    }
}

class HashingThread extends RecursiveAction {
    private static final ConcurrentHashMap<String, String> PILE = new ConcurrentHashMap<>();

    private final Maze maze;
    private final HexGrid grid;
    private final List<Integer> colList;

    @Override
    protected void compute() {
        if (colList.size() != 1){
            ArrayList<ArrayList<Integer>> splitList = splitList(colList);
            HashingThread taskOne = new HashingThread(splitList.get(0), maze, grid);
            HashingThread taskTwo = new HashingThread(splitList.get(1), maze, grid);

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

    public HashingThread(ArrayList<Integer> columnList, Maze maze, HexGrid grid){
        colList = columnList;
        this.maze = maze;
        this.grid = grid;
    }

    public static ConcurrentHashMap<String, String> library(){
        return PILE;
    }

    private void sequentialWork(int col){
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> columns = getColumns(grid.hexport().getSpan(), col);
        int[] startPositions = calculateStartPositions(columns);
        runColumns(columns, startPositions);
    }

    private FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> getColumns(int iteratorSize, int startColumn){
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> strippedMaze = maze.exportTo2DQueue();
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> outputColumns = new FixedArrayQueue<>(iteratorSize);
        for (int i = startColumn; i < iteratorSize + startColumn; i++)
            outputColumns.add(HexComparator.deepCopyList(strippedMaze.get(i)));

        return outputColumns;
    }

    private int[] calculateStartPositions(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> columns){
        int[] positions = new int[columns.cap()];

        for(int i = 0; i < columns.cap(); i++)
            positions[i] = columns.get(i).cap();

        int lastIndex = positions.length - 1,
                middleIndex = positions.length/2;

        if (positions[0] < positions[lastIndex]){//We're in the first half of the maze
            //Alters the second half
            for (int i = lastIndex; i > middleIndex; i--)
                positions[i] -= positions[lastIndex - i];
            //Zeroes out the first half
            for (int j = 0; j <= middleIndex; j++)
                positions[j] = 0;
        } else if (positions[0] > positions[lastIndex]) {//We're in the second half of the maze
            //Alters the first half
            for (int i = 0; i < middleIndex; i++)
                positions[i] -= positions[lastIndex - i];
            //Zeroes out the second half
            for (int j = middleIndex; j <= lastIndex; j++)
                positions[j] = 0;
        } else Arrays.fill(positions, 0); //We're in the middle columns of the maze

        for (int i = 0; i < positions.length; i++)
            positions[i] /= 2;

        return positions;
    }

    private void runColumns(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> columns, int[] startPositions){
        final int hexagonalSideLength = (startPositions.length + 1) / 2;
        final int[] travelDistances = AbstractHexagon.calculateColumnLengths(hexagonalSideLength);
        int[] endPositions = addArrays(travelDistances, startPositions);

        while(notDone(columns, endPositions)){
            Hex currentIterator = new Hex(retrieveHexagon(startPositions, travelDistances, endPositions, columns),
                    hexagonalSideLength);
            HexGrid current = new HexGrid(currentIterator);
            for (int i = 0; i < 6; i++){
                ArrayList<String> temp = current.hashStrings();
                PILE.put(temp.get(HexHashLibrary.HASH_STRING_SHAPES), temp.get(HexHashLibrary.HASH_STRING_WALLS));
                current.rotateColorOrder();
                current.hexport().rotate();
            }

            HexComparator.incrementArray(startPositions);
            HexComparator.incrementArray(endPositions);
        }
    }

    private boolean notDone(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> columns, int[] endPositions){
        for (int i = 0; i < columns.cap(); i++)
            if (endPositions[i] > columns.get(i).cap()) return false;
        return true;
    }

    private FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> retrieveHexagon
            (int[] startPositions, int[] travelDistances,int[] endPositions,
             FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> columns){
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> hexagon = new FixedArrayQueue<>(travelDistances.length);
        for (int i = 0; i < startPositions.length; i++){
            FixedArrayQueue<Hex.HexNode> inputColumn = new FixedArrayQueue<>(travelDistances[i]);
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
