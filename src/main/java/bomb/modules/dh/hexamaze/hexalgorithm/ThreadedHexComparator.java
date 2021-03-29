package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.tools.data.structures.FixedArrayQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ThreadedHexComparator {

    /**
     * Don't let anyone instantiate this class
     */
    private ThreadedHexComparator(){}

    public static HexGrid evaluate(Maze fullMaze, HexGrid grid){
        int iterations = fullMaze.hexport().getSpan() - grid.hexport().getSpan();
        ArrayList<Integer> columnList = IntStream.range(0, iterations + 1)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        ForkJoinPool mazePool = new ForkJoinPool(2);
        ComparatorThread task = new ComparatorThread(fullMaze, grid, columnList);
        return mazePool.invoke(task);
    }
}

class ComparatorThread extends RecursiveTask<HexGrid> {
    private static final BinaryOperator<HexGrid> DISPOSE_NULL_RESULT =
            (valOne, valTwo) -> valOne != null ? valOne : valTwo;

    private final Maze fullMaze;
    private final HexGrid grid;
    private final ArrayList<Integer> columnList;

    public ComparatorThread(Maze fullMaze, HexGrid grid, ArrayList<Integer> columnList){
        this.fullMaze = fullMaze;
        this.grid = grid;
        this.columnList = columnList;
    }

    @Override
    protected HexGrid compute() {
        if (columnList.size() != 1){
            ArrayList<ArrayList<Integer>> splitList = splitList(columnList);
            ComparatorThread taskOne = new ComparatorThread(fullMaze, grid, splitList.get(0));
            ComparatorThread taskTwo = new ComparatorThread(fullMaze, grid, splitList.get(1));

            taskOne.fork();
            taskTwo.fork();

            return DISPOSE_NULL_RESULT.apply(taskOne.join(), taskTwo.join());
        } else return sequentialWork(fullMaze, grid, columnList.get(0));
    }

    private ArrayList<ArrayList<Integer>> splitList(ArrayList<Integer> list){
        int split = list.size() / 2;
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();
        output.add(new ArrayList<>(list.subList(0, split)));
        output.add(new ArrayList<>(list.subList(split, list.size())));
        return output;
    }

    private HexGrid sequentialWork(Maze fullMaze, HexGrid grid, int startColumn){
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> columns =
                getColumns(fullMaze, grid.hexport().getSpan(), startColumn);
        int[] startPositions = calculateStartPositions(columns);
        return runColumns(columns, grid, startPositions);
    }

    private FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> getColumns(Maze fullMaze,
                                                                     int iteratorSize, int startColumn){
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> strippedMaze = fullMaze.exportTo2DQueue();
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

    private HexGrid runColumns(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> columns, HexGrid grid, int[] startPositions){
        final int hexagonalSideLength = (startPositions.length + 1) / 2;
        final int[] travelDistances = HexComparator.pingRequest(hexagonalSideLength);
        int[] endPositions = addArrays(travelDistances, startPositions);

        while(notDone(columns, endPositions)){
            Hex currentIterator = new Hex(retrieveHexagon(startPositions, travelDistances, endPositions, columns),
                    hexagonalSideLength);
            int rotations = HexComparator.fullRotationCompare(grid, currentIterator);
            if (rotations != -1)
                return new HexGrid(currentIterator, rotations);

            HexComparator.incrementArray(startPositions);
            HexComparator.incrementArray(endPositions);
        }

        return null;
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
