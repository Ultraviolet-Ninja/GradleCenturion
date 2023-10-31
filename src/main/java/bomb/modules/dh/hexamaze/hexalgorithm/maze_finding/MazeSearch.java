package bomb.modules.dh.hexamaze.hexalgorithm.maze_finding;

import bomb.modules.dh.hexamaze.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexShape;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexagonalPlane;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.Maze;
import bomb.tools.data.structures.queue.BufferedQueue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static bomb.modules.dh.hexamaze.hexalgorithm.storage.AbstractHexagon.calculateColumnLengthArray;

public final class MazeSearch {
    private static final int ROTATION_COUNT = 6;

    /**
     * Finds the smaller hexagon grid in the full maze so that output has the boundary
     * walls for each node.
     *
     * @param maze The full maze that we're iterating through
     * @param grid The smaller hexagon containing the shapes of each node
     * @return An optional of the found grid or empty if the entire maze has been
     * traversed and has not found the Grid with the current shape configuration
     */
    public static Optional<Grid> search(@NotNull Maze maze, @NotNull Grid grid) {
        int gridSpan = grid.getHexagon().getSpan();
        int lastIndex = maze.getHexagon().getSpan() - gridSpan;
        BufferedQueue<BufferedQueue<HexNode>> pillar;
        Grid output;

        for (int offset = -1; ++offset <= lastIndex;) {
            pillar = generatePillar(maze, gridSpan, offset);
            output = searchPillar(pillar, grid);
            if (output != null)
                return Optional.of(output);
        }
        return Optional.empty();
    }

    /**
     * Creates a pillar that fits the span of the smaller grid
     *
     * @param maze The full maze that we're iterating through
     * @param gridSpan How wide the smaller hexagon is
     * @param offset How many columns away from the first column
     * @return The columns that represent a slice of the full maze that fits the grid
     */
    private static BufferedQueue<BufferedQueue<HexNode>> generatePillar(Maze maze, int gridSpan, int offset) {
        var queues = maze.getHexagon().getBufferedQueues();
        BufferedQueue<BufferedQueue<HexNode>> output = new BufferedQueue<>(gridSpan);
        int stop = gridSpan + offset;

        for (int i = offset; i < stop; i++)
            output.add(deepCopyQueue(queues.get(i)));

        return output;
    }

    /**
     * Copies the information of each node in a queue so that the original information is not altered
     *
     * @param original The original queue
     * @return A full copy of the queue information
     */
    private static BufferedQueue<HexNode> deepCopyQueue(BufferedQueue<HexNode> original) {
        var output = new BufferedQueue<HexNode>(original.getCapacity());
        for (HexNode node : original)
            output.add(node.copy());
        return output;
    }

    /**
     * Searches an entire pillar of the full maze to find the Grid that matches the shapes
     * of the input Grid
     *
     * @param pillar The generated pillar coming from the full maze
     * @param grid The smaller hexagon containing the shapes of each node
     * @return Null if the grid couldn't be matched with any section of the pillar or
     * a valid grid containing shapes and wall information
     */
    private static Grid searchPillar(BufferedQueue<BufferedQueue<HexNode>> pillar, Grid grid) {
        HexagonalPlane originalGrid = grid.getHexagon();
        int gridSideLength = originalGrid.getSideLength();
        trimPillar(pillar);
        var copiedHexagon = createInitialCopy(pillar, gridSideLength);

        Grid output = compareFullRotation(originalGrid, copiedHexagon);

        while(output == null && pillarNotEmpty(pillar)) {
            moveToNextSegment(pillar, copiedHexagon);
            output = compareFullRotation(originalGrid, copiedHexagon);
        }

        return output;
    }

    /**
     * Determines if the pillar has no elements left in the set of queues
     *
     * @param pillar The set columns to pull information from
     * @return Whether both ends of the pillar are not empty
     */
    private static boolean pillarNotEmpty(BufferedQueue<BufferedQueue<HexNode>> pillar) {
        int firstSize = pillar.getFirst().size();
        int secondSize = pillar.getLast().size();
        return firstSize != 0 && secondSize != 0;
    }

    /**
     * Trims the pillar based on its location in the larger maze
     *
     * @param pillar The set of columns
     */
    private static void trimPillar(BufferedQueue<BufferedQueue<HexNode>> pillar) {
        int length = pillar.getCapacity();
        int start = 0;
        int end = length - 1;
        int[] capacityArray = pillar.stream()
                .mapToInt(BufferedQueue::getCapacity)
                .toArray();

        if (capacityArray[start] == capacityArray[end])
            return;

        int[] removalCounts = new int[length];
        while(start != end) {
            int change = capacityArray[start] - capacityArray[end];
            change >>= 1; //Divide by 2
            removalCounts[start++] = Math.max(change, 0);
            removalCounts[end--] = Math.max(-change, 0);
        }

        for (int i = -1; ++i < length;) {
            int removalCount = removalCounts[i];
            if (removalCount == 0) continue;

            var column = pillar.get(i);
            if (removalCount == 1) {
                column.removeFirst();
            } else {
                column.removeCount(removalCount);
            }
        }
    }

    /**
     * Creates the starting Grid from the top of the pillar
     *
     * @param pillar The generated set of columns
     * @param gridSideLength The side length of a grid to calculate
     * @return The queues that make up the underlying start Grid
     */
    private static HexagonalPlane createInitialCopy(
            BufferedQueue<BufferedQueue<HexNode>> pillar, int gridSideLength) {
        int[] columnLengths = calculateColumnLengthArray(gridSideLength);
        BufferedQueue<BufferedQueue<HexNode>> copiedGrid = new BufferedQueue<>(columnLengths.length);

        int index = 0;
        for (BufferedQueue<HexNode> column : pillar) {
            int removalCount = columnLengths[index++];
            BufferedQueue<HexNode> partition = new BufferedQueue<>(column.removeCount(removalCount));
            copiedGrid.add(partition);
        }

        return new HexagonalPlane(copiedGrid, gridSideLength);
    }

    /**
     * Compares the shapes between the original hexagon and the copied segment from the pillar
     * 6 times to see if the original shape order matches any of the rotation configurations
     * of the copied segment
     *
     * @param original The original grid from the user
     * @param copy The copied segment from the pillar of queues
     * @return Null if the grid is not found within the copy
     */
    private static Grid compareFullRotation(HexagonalPlane original, HexagonalPlane copy) {
        int rotation = 0;
        final var originalShapes = convertToHexShapes(original);
        do {
            if (originalShapes.equals(convertToHexShapes(copy)))
                return new Grid(copy, rotation);
            copy.rotate();
        } while(++rotation != ROTATION_COUNT);
        return null;
    }

    /**
     * Extracts the shape order from all the queues in the hexagon
     *
     * @param structure The underlying hexagonal structure from a Grid class
     * @return The list of shapes from the hexagon
     */
    private static List<HexShape> convertToHexShapes(HexagonalPlane structure) {
        return structure.getBufferedQueues()
                .stream()
                .flatMap(Collection::stream)
                .map(HexNode::getHexShape)
                .toList();
    }

    /**
     * Manipulates the underlying queues of the copy Hexagonal Plane to remove from the top layer
     * and add another set of nodes to the end of each queue
     *
     * @param pillar The queues that are being pulled from
     * @param copy The cloned hexagon
     */
    private static void moveToNextSegment(BufferedQueue<BufferedQueue<HexNode>> pillar, HexagonalPlane copy) {
        BufferedQueue<BufferedQueue<HexNode>> copiedQueues = copy.getBufferedQueues();
        for (var column : copiedQueues)
            column.removeFirst();

        int index = 0;
        for (var column : pillar) {
            HexNode nextNode = column.removeFirst();
            copiedQueues.get(index++).add(nextNode);
        }
    }
}
