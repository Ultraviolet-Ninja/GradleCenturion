package bomb.modules.dh.hexamaze.hexalgorithm.maze_finding;

import bomb.modules.dh.hexamaze.hexalgorithm.AbstractHexagon;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import bomb.tools.data.structures.BufferedQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.BinaryOperator;

@Deprecated
public class ThreadedHexComparator {

    /**
     * Don't let anyone instantiate this class
     */
    private ThreadedHexComparator(){}

    public static HexGrid evaluate(Maze fullMaze, HexGrid grid){
        int iterations = fullMaze.getSpan() - grid.getSpan();
        ArrayList<Integer> columnList = new ArrayList<>();
        for (int i = 0; i <= iterations; i++)
            columnList.add(i);
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
        BufferedQueue<BufferedQueue<HexagonDataStructure.HexNode>> columns =
                getColumns(fullMaze, grid.getSpan(), startColumn);
        int[] startPositions = calculateStartPositions(columns);
        return runColumns(columns, grid, startPositions);
    }

    private BufferedQueue<BufferedQueue<HexagonDataStructure.HexNode>> getColumns(Maze fullMaze,
                                                                                  int iteratorSize, int startColumn){
        BufferedQueue<BufferedQueue<HexagonDataStructure.HexNode>> strippedMaze = fullMaze.exportTo2DQueue();
        BufferedQueue<BufferedQueue<HexagonDataStructure.HexNode>> outputColumns = new BufferedQueue<>(iteratorSize);
        for (int i = startColumn; i < iteratorSize + startColumn; i++)
            outputColumns.add(OldHexComparator.deepCopyList(strippedMaze.get(i)));

        return outputColumns;
    }

    private int[] calculateStartPositions(BufferedQueue<BufferedQueue<HexagonDataStructure.HexNode>> columns){
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

    private HexGrid runColumns(BufferedQueue<BufferedQueue<HexagonDataStructure.HexNode>> columns, HexGrid grid, int[] startPositions){
        final int hexagonalSideLength = (startPositions.length + 1) / 2;
        final int[] travelDistances = AbstractHexagon.calculateColumnLengths(hexagonalSideLength);
        int[] endPositions = addArrays(travelDistances, startPositions);

        while(notDone(columns, endPositions)){
            HexagonDataStructure currentIterator = new HexagonDataStructure(retrieveHexagon(startPositions, travelDistances, endPositions, columns),
                    hexagonalSideLength);
            int rotations = OldHexComparator.fullRotationCompare(grid, currentIterator);
            if (rotations != -1)
                return new HexGrid(currentIterator, rotations);

            OldHexComparator.incrementArray(startPositions);
            OldHexComparator.incrementArray(endPositions);
        }

        return null;
    }

    private boolean notDone(BufferedQueue<BufferedQueue<HexagonDataStructure.HexNode>> columns, int[] endPositions){
        for (int i = 0; i < columns.cap(); i++)
            if (endPositions[i] > columns.get(i).cap()) return false;
        return true;
    }

    private BufferedQueue<BufferedQueue<HexagonDataStructure.HexNode>> retrieveHexagon
            (int[] startPositions, int[] travelDistances,int[] endPositions,
             BufferedQueue<BufferedQueue<HexagonDataStructure.HexNode>> columns){
        BufferedQueue<BufferedQueue<HexagonDataStructure.HexNode>> hexagon = new BufferedQueue<>(travelDistances.length);
        for (int i = 0; i < startPositions.length; i++){
            BufferedQueue<HexagonDataStructure.HexNode> inputColumn = new BufferedQueue<>(travelDistances[i]);
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
