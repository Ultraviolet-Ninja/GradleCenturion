package bomb.modules.dh.hexamaze.hexalgorithm.maze_finding;

import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure.HexNode;
import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import bomb.tools.data.structures.FixedArrayQueue;

import java.util.Arrays;

public class HexComparatorV2 {

    private HexComparatorV2(){}

    public static HexGrid findSubsection(Maze fullMaze, HexGrid grid) throws IllegalArgumentException {
        if (grid.sideLength() < fullMaze.exportTo2DQueue().cap())
            return browseFullMaze(fullMaze, grid);
        return null;
    }

    private static HexGrid browseFullMaze(Maze fullMaze, HexGrid grid) throws IllegalArgumentException {
        for (int startColumn = 0; startColumn < fullMaze.getSpan() - grid.getSpan(); startColumn++){
            FixedArrayQueue<FixedArrayQueue<HexNode>> columnSet =
                    accessColumnSet(fullMaze, startColumn, grid.getSpan());
            HexGrid result = null;
            if (result != null) return result;
        }
        return null;
    }

    private static FixedArrayQueue<FixedArrayQueue<HexNode>> accessColumnSet
            (Maze fullMaze, int startColumn, int gridSpan){
        FixedArrayQueue<FixedArrayQueue<HexNode>> outputColumns = new FixedArrayQueue<>(gridSpan);
        FixedArrayQueue<FixedArrayQueue<HexNode>> internalArrays = fullMaze.exportTo2DQueue();
        for (int index = startColumn; index < startColumn + gridSpan; index++){
            outputColumns.add(deepCopyList(internalArrays.get(index)));
        }
        return outputColumns;
    }

    private static FixedArrayQueue<HexNode> deepCopyList(FixedArrayQueue<HexNode> input) {
        FixedArrayQueue<HexNode> output = new FixedArrayQueue<>(input.cap());
        for (int i = 0; i < output.cap(); i++)
            output.add(new HexNode(input.get(i)));
        return output;
    }

    private HexGrid runColumnSet(FixedArrayQueue<FixedArrayQueue<HexNode>> columnSet){
        int[] startingLocations = calculateStartPositions(columnSet);
        return null;
    }

    private int[] calculateStartPositions(FixedArrayQueue<FixedArrayQueue<HexNode>> columns){
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
}
