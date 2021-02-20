package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.tools.data.structures.FixedArrayQueue;

import java.util.ArrayList;

/**
 * The class contains the necessary methods to compare the defuser vision (HexGrid) to the full Maze
 * and find a section that matches the HexGrid based on the defuser's shape locations.
 * The methods use for loops to simulate the stencil from the interactive Hexamaze site assuming the
 * stencil starts in the top left, iterates down the first set of columns, moves over a row and repeats.
 */
public class HexComparator {

    /**
     * Don't let anyone instantiate this class
     */
    private HexComparator() {
    }

    /**
     * Scans the entire maze to see where the defuser's HexGrid is
     *
     * @param fullMaze The entire maze known to the expert only
     * @param grid     The defuser's vision
     * @return A hexagon containing the wall information for each node in the defuser's vision
     * @throws IllegalArgumentException Any Exception that appears
     */
    public static HexGrid evaluate(Maze fullMaze, HexGrid grid) throws IllegalArgumentException {
        return evaluateSize(fullMaze, grid);
    }

    /**
     * Checks that the HexGrid is smaller than the actual maze
     *
     * @param fullMaze The full maze
     * @param grid     The 2D iterator
     * @return The grid or null whether it's found
     * @throws IllegalArgumentException The side length exceeds the size the maze
     */
    private static HexGrid evaluateSize(Maze fullMaze, HexGrid grid) throws IllegalArgumentException {
        if (grid.sideLength() < fullMaze.exportTo2DQueue().cap())
            return solve(fullMaze, grid);
        throw new IllegalArgumentException("Side Length requested exceeds the size of the Maze");
    }

    /**
     * @param fullMaze The full maze
     * @param grid     The 2D iterator
     * @return The grid or null whether it's found
     * @throws IllegalArgumentException If there's a size difference between the two hexagons
     */
    private static HexGrid solve(Maze fullMaze, HexGrid grid) throws IllegalArgumentException {
        //TODO - Break down
        int[] spans = getSpans(fullMaze, grid);
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> tower;
        HexGrid match = null;
        for (int xOffset = 0; xOffset <= (spans[0] - spans[1]); xOffset++) {
            tower = getTower(fullMaze, spans[1], xOffset);
            tower = shaveProtocol(tower,
                    fullMaze.hexport().sideLength() - (grid.sideLength() + xOffset), grid);
            int[] pings = pingRequest(grid.sideLength());
            match = identifyMatch(new Hex(startHexagon(pings, tower), grid.sideLength()),
                    tower, grid, pings);
            if (match != null) xOffset = spans[0];
        }
        return match;
    }

    /**
     * Returns the spans of the maze and the grid hexagons
     *
     * @param fullMaze The full maze
     * @param grid     The 2D iterator
     * @return The array of spans
     */
    private static int[] getSpans(Maze fullMaze, HexGrid grid) {
        return new int[]{fullMaze.hexport().getSpan(), grid.hexport().getSpan()};
    }

    /**
     * @param fullMaze The full maze
     * @param gridSpan The span of the grid
     * @param offset   The offset representing the column to start taking from
     * @return A section of the maze used for
     */
    private static FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> getTower(Maze fullMaze, int gridSpan, int offset) {
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> rawNodes = fullMaze.exportTo2DQueue(),
                outputTower = new FixedArrayQueue<>(gridSpan);
        //TODO - Streamable?
        for (int i = offset; i < (gridSpan + offset); i++)
            outputTower.add(deepCopyList(rawNodes.get(i)));
        return outputTower;
    }

    private static FixedArrayQueue<Hex.HexNode> deepCopyList(FixedArrayQueue<Hex.HexNode> input) {
        //TODO - Streamable?
        FixedArrayQueue<Hex.HexNode> output = new FixedArrayQueue<>(input.cap());
        for (int i = 0; i < output.cap(); i++)
            output.add(new Hex.HexNode(input.get(i)));
        return output;
    }

    /**
     * @param tower    The section of the full maze
     * @param shaveCap The number determining how much to shave off the top of certain lists
     * @param grid     The 2D iterator
     * @return The shaven hex tower
     */
    private static FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> shaveProtocol
    (FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> tower, int shaveCap, HexGrid grid) {
        return shaveCap < 0 ?
                firstHalfShave(tower, Math.abs(shaveCap), grid) :
                secondHalfShave(tower, shaveCap, grid);
    }

    /**
     * @param tower    The set of columns taken from the full maze
     * @param shaveCap The number determining how much to shave off the top of certain lists
     * @param grid     The 2D iterator
     * @return The trimmed down hexagonal tower
     */
    private static FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> firstHalfShave
    (FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> tower, int shaveCap, HexGrid grid) {
        int cap = absCap(shaveCap, grid);

        for (int col = 0; col < grid.sideLength() - 1; col++)
            tower.get(col).removeFromHead(calculateCap(cap, col, grid));
        return tower;
    }

    /**
     * @param cap      The number determining how much to shave off the top of certain lists
     * @param colIndex
     * @param grid     The 2D iterator
     * @return The number of elements to shave off the top of the lists
     */
    private static int calculateCap(int cap, int colIndex, HexGrid grid) {
        int min = Math.min(cap, grid.sideLength() - ++colIndex);
        return (min == 1 || min == 2) ? min : grid.sideLength() - colIndex;
    }

    /**
     * Trims down elements from the columns in the second half of the hexagon
     *
     * @param tower    The set of columns taken from the full maze
     * @param shaveCap The number determining how much to shave off the top of certain lists
     * @param grid     The 2D iterator
     * @return The trimmed down hexagonal tower
     */
    private static FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> secondHalfShave
    (FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> tower, int shaveCap, HexGrid grid) {
        int cap = absCap(shaveCap, grid), removalCounter = 1;
        for (int col = grid.sideLength(); col < grid.hexport().getSpan(); col++)
            tower.get(col).removeFromHead(Math.min(cap, removalCounter++));
        return tower;
    }

    /**
     * @param shaveCap
     * @param grid
     * @return
     */
    private static int absCap(int shaveCap, HexGrid grid) {
        int gridCap = grid.sideLength() - 1;
        return Math.min(gridCap, shaveCap);
    }

    /**
     * Creates an array of lengths corresponding to the columns of the 2D iterator
     *
     * @param sideLength The side length of the hexagon
     * @return An array of column lengths
     */
    static int[] pingRequest(int sideLength) {
        int counter = 0;
        int[] request = new int[2 * sideLength - 1];

        for (int i = sideLength; i < 2 * sideLength; i++)
            request[counter++] = i;


        for (int j = 2 * sideLength - 2; j >= sideLength; j--)
            request[counter++] = j;

        return request;
    }

    /**
     * @param pings
     * @param tower
     * @return
     */
    private static FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> startHexagon
    (int[] pings, FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> tower) {
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> outputHexagon = new FixedArrayQueue<>(pings.length);
        for (int x = 0; x < pings.length; x++) {
            FixedArrayQueue<Hex.HexNode> inputColumn = new FixedArrayQueue<>(pings[x]);
            for (int y = 0; y < pings[x]; y++)
                inputColumn.add(tower.get(x).get(y));
            outputHexagon.add(inputColumn);
        }
        return outputHexagon;
    }

    /**
     * @param startingGrid The 2D iterator
     * @param tower
     * @param grid         The grid that the defuser sees
     * @param pings
     * @return
     * @throws IllegalArgumentException If there's a size difference between the two hexagons
     */
    private static HexGrid identifyMatch(Hex startingGrid, FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> tower,
                                         HexGrid grid, int[] pings) throws IllegalArgumentException {
        int rotations = fullRotationCompare(grid, startingGrid);
        if (rotations != -1)
            return new HexGrid(startingGrid, rotations);

        while (pings[grid.sideLength() - 1] != tower.get(grid.sideLength() - 1).size()) {
            startingGrid = nextTo(pings, startingGrid, tower);
            rotations = fullRotationCompare(grid, startingGrid);
            if (rotations != -1)
                return new HexGrid(startingGrid, rotations);
            incrementArray(pings);
        }

        return null;
    }


    /**
     * Compares the HexGrid to the Hex containing nodes copied from the full Maze.
     * It does this 6 times, once for each time rotated
     *
     * @param grid The original defuser grid
     * @param copy The maze-copies set of HexNodes
     * @return True for a match, false for no match
     * @throws IllegalArgumentException If there's a size difference between the two hexagons
     */
    private static int fullRotationCompare(HexGrid grid, Hex copy) throws IllegalArgumentException {
        boolean result = false;
        int counter = 0;
        while (counter < 6 && !result) {
            result = compare(grid, copy);
            if (!result) {
                counter++;
                copy.rotate(); //The copy has to fit the grid
            }
        }
        return counter != 6 ? counter : -1;
    }

    /**
     * Compares the HexGrid to the Hex containing nodes copied from the full Maze once.
     *
     * @param grid The original defuser grid
     * @param copy The maze-copies set of HexNodes
     * @return True for a match, false for no match
     * @throws IllegalArgumentException If there's a size difference between the two hexagons
     */
    private static boolean compare(HexGrid grid, Hex copy) throws IllegalArgumentException {
        ArrayList<Hex.HexNode> gridArray = grid.hexport().exportToList(),
                copyArray = copy.exportToList();
        if (gridArray.size() != copyArray.size())
            throw new IllegalArgumentException("Size Difference in compare()");

        //TODO - Streamable?
        for (int i = 0; i < gridArray.size(); i++)
            if (gridArray.get(i).fill != copyArray.get(i).fill) return false;
        return true;
    }

    /**
     * Rotates the resulting Hex to be in the correct position
     *
     * @param copy      The Hexagon duplicate
     * @param rotations The number of rotations the Hexagon needs to go through
     * @return The rotated hexagon
     */
    private static Hex rotateTo(Hex copy, int rotations) {
        for (int i = 0; i < rotations; i++) copy.rotate();
        return copy;
    }

    /**
     * Increments each number in the array
     *
     * @param array The array of integers
     */
    private static void incrementArray(int[] array) {
        for (int idx = 0; idx < array.length; idx++) array[idx] += 1;
    }

    /**
     * Cuts off the first elements of the Hexagon and adds next set
     *
     * @param pings The indices of the next set
     * @param grid  The previous Hexagon
     * @param tower The full columns to take from
     * @return The next Hexagon to test
     */
    private static Hex nextTo(int[] pings, Hex grid, FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> tower) {
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> rawHex = grid.exportTo2DQueue();
        shaveTop(rawHex);
        appendTo(pings, rawHex, tower);
        return new Hex(rawHex, grid.sideLength());
    }

    /**
     * Cuts off the first elements of each column
     *
     * @param grid The previous Hexagon
     */
    private static void shaveTop(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> grid) {
        //TODO - Streamable?
        for (int i = 0; i < grid.cap(); i++)
            grid.get(i).removeFromHead(1);
    }

    /**
     * Adds the next set of elements to the columns
     *
     * @param pings The locations of the next elements to access
     * @param grid  The previous Hexagon
     * @param tower The set of columns to take from
     */
    private static void appendTo(int[] pings, FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> grid,
                                 FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> tower) {
        //TODO - Streamable?
        for (int i = 0; i < grid.size(); i++)
            grid.get(i).add(tower.get(i).get(pings[i]));
    }
}