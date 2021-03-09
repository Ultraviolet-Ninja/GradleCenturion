package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.tools.data.structures.FixedArrayQueue;

import java.util.ArrayList;
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
        ArrayList<Integer> columnList = IntStream.range(0, iterations)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        ForkJoinPool mazePool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
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
            ArrayList<ArrayList<Integer>> splitList = splitter(columnList);
            ComparatorThread taskOne = new ComparatorThread(fullMaze, grid, splitList.get(0));
            ComparatorThread taskTwo = new ComparatorThread(fullMaze, grid, splitList.get(1));

            taskOne.fork();
            taskTwo.fork();

            return DISPOSE_NULL_RESULT.apply(taskOne.join(), taskTwo.join());
        } else return sequentialWork(fullMaze, grid, columnList.get(0));
    }

    private ArrayList<ArrayList<Integer>> splitter(ArrayList<Integer> list){
        int split = list.size() / 2;
        int end = list.size() - 1;
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();
        output.add(new ArrayList<>(list.subList(0, split)));
        output.add(new ArrayList<>(list.subList(split, end)));
        return output;
    }

    private HexGrid sequentialWork(Maze fullMaze, HexGrid grid, int startColumn){
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> columns =
                getColumns(fullMaze, grid.hexport().getSpan(), startColumn);


        return null;
    }

    private FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> getColumns(Maze fullMaze,
                                                                     int iteratorSize, int startColumn){
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> strippedMaze = fullMaze.exportTo2DQueue();
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> outputColumns = new FixedArrayQueue<>(iteratorSize);
        for (int i = startColumn; i < iteratorSize + startColumn; i++)
            outputColumns.add(strippedMaze.get(i));

        return outputColumns;
    }

    private int[] calculateStartPositions(){
        return null;
    }
}
