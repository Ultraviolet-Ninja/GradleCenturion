package bomb.modules.dh.hexamaze.hexalgorithm.maze_finding;

import bomb.modules.dh.hexamaze.hexalgorithm.AbstractHexagon;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure.HexNode;
import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import bomb.tools.data.structures.BufferedQueue;

public class HexComparator {

    private HexComparator(){}

    public static HexGrid findSubsection(Maze fullMaze, HexGrid grid) throws IllegalArgumentException {
        if (grid.sideLength() < fullMaze.exportTo2DQueue().cap())
            return browseFullMaze(fullMaze, grid);
        return null;
    }

    private static HexGrid browseFullMaze(Maze fullMaze, HexGrid grid) throws IllegalArgumentException {
        for (int startColumn = 0; startColumn < fullMaze.getSpan() - grid.getSpan(); startColumn++){
            BufferedQueue<BufferedQueue<HexNode>> columnSet = accessColumnSet(fullMaze, startColumn, grid.getSpan());
            int[] startingLocations = calculateStartPositions(columnSet);
            HexGrid result = runColumnSet(grid, columnSet, startingLocations);
            if (result != null) return result;
        }
        return null;
    }

    private static BufferedQueue<BufferedQueue<HexNode>> accessColumnSet
            (Maze fullMaze, int startColumn, int gridSpan){
        BufferedQueue<BufferedQueue<HexNode>> outputColumns = new BufferedQueue<>(gridSpan);
        BufferedQueue<BufferedQueue<HexNode>> internalArrays = fullMaze.exportTo2DQueue();
        for (int index = startColumn; index < startColumn + gridSpan; index++){
            outputColumns.add(deepCopyList(internalArrays.get(index)));
        }
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

    private static HexGrid runColumnSet(HexGrid grid, BufferedQueue<BufferedQueue<HexNode>> columnSet,
                                        int[] startingLocations){
        final int[] travelDistances = AbstractHexagon.calculateColumnLengths(grid.sideLength());
        int[] endPositions = addArrays(travelDistances, startingLocations);
        while(notDone(columnSet, endPositions)){
            HexagonDataStructure currentIterator = new HexagonDataStructure(
                    retrieveHexagon(startingLocations, travelDistances, endPositions, columnSet), grid.sideLength());
            int rotations = fullRotationCompare(grid, currentIterator);
            if (rotations != -1)
                return new HexGrid(currentIterator, rotations);
            incrementArray(startingLocations);
            incrementArray(endPositions);
        }
        return null;
    }

    private static BufferedQueue<BufferedQueue<HexNode>> retrieveHexagon
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

    private static int fullRotationCompare(HexGrid grid, HexagonDataStructure copy) throws IllegalArgumentException{
        final int fullHexagonRotation = 6;
        boolean isMatch = false;
        int rotationCounter = 0;
        while (rotationCounter < fullHexagonRotation && !isMatch){
            isMatch = grid.hexport().hashString().equals(copy.hashString());
            if (!isMatch){
                rotationCounter++;
                copy.rotate();
            }
        }
        return rotationCounter != fullHexagonRotation ? rotationCounter : -1;
    }

    private static boolean notDone(BufferedQueue<BufferedQueue<HexNode>> columns, int[] endPositions){
        for (int i = 0; i < columns.cap(); i++)
            if (endPositions[i] > columns.get(i).cap()) return false;
        return true;
    }

    private static int[] addArrays(int[] arrayOne, int[] arrayTwo){
        int[] output = new int[arrayOne.length];
        for (int i = 0; i < arrayOne.length; i++)
            output[i] = arrayOne[i] + arrayTwo[i];
        return output;
    }

    private static void incrementArray(int[] array) {
        for (int idx = 0; idx < array.length; idx++) array[idx] += 1;
    }
}
