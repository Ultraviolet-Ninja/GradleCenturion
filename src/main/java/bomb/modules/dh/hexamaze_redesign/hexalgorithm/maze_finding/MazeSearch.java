package bomb.modules.dh.hexamaze_redesign.hexalgorithm.maze_finding;

import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexShape;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexagonDataStructure;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.Maze;
import bomb.tools.data.structures.queue.BufferedQueue;

import java.util.Collection;
import java.util.List;

import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.AbstractHexagon.calculateColumnLengthStream;
import static java.util.stream.Collectors.toList;

public class MazeSearch {
    public static Grid search(Maze maze, Grid grid) {
        int gridSpan = grid.getHexagon().getSpan();
        int lastIndex = maze.getHexagon().getSpan() - gridSpan;
        BufferedQueue<BufferedQueue<HexNode>> pillar;
        Grid output;

        for (int offset = -1; ++offset <= lastIndex;) {
            pillar = generatePillar(maze, gridSpan, offset);
            output = searchPillar(pillar, grid);
            if (output != null)
                return output;
        }
        return null;
    }

    private static BufferedQueue<BufferedQueue<HexNode>> generatePillar(Maze maze, int gridSpan, int offset) {
        BufferedQueue<BufferedQueue<HexNode>> queues = maze.getHexagon().getBufferedQueues();
        BufferedQueue<BufferedQueue<HexNode>> output = new BufferedQueue<>(gridSpan);
        int stop = gridSpan + offset;

        for (int i = offset; i < stop; i++)
            output.add(deepCopyQueue(queues.get(i)));

        return output;
    }

    private static BufferedQueue<HexNode> deepCopyQueue(BufferedQueue<HexNode> original) {
        BufferedQueue<HexNode> output = new BufferedQueue<>(original.getCapacity());
        for (HexNode node : original)
            output.add(new HexNode(node));
        return output;
    }

    private static Grid searchPillar(BufferedQueue<BufferedQueue<HexNode>> pillar, Grid grid) {
        int gridSideLength = grid.getHexagon().getSideLength();
        trimPillar(pillar);
        HexagonDataStructure copiedHexagon = new HexagonDataStructure(
                createInitialCopy(pillar, gridSideLength), gridSideLength);
        HexagonDataStructure originalGrid = grid.getHexagon();

        Grid output = compareFullRotation(originalGrid, copiedHexagon);

        while(output == null && isNotColumnEmpty(pillar)) {
            moveToNextSegment(pillar, copiedHexagon);
            output = compareFullRotation(originalGrid, copiedHexagon);
        }

        return output;
    }

    private static boolean isNotColumnEmpty(BufferedQueue<BufferedQueue<HexNode>> pillar) {
        int firstSize = pillar.get(0).size();
        int secondSize = pillar.get(pillar.getCapacity() - 1).size();
        return firstSize != 0 && secondSize != 0;
    }

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
            change /= 2;
            removalCounts[start++] = Math.max(change, 0);
            removalCounts[end--] = Math.max(-change, 0);
        }

        for (int i = -1; ++i < length;) {
            int removalCount = removalCounts[i];
            if (removalCount == 0) continue;

            BufferedQueue<HexNode> column = pillar.get(i);
            if (removalCount == 1) {
                column.removeFirst();
            } else {
                column.removeCount(removalCount);
            }
        }
    }

    private static BufferedQueue<BufferedQueue<HexNode>> createInitialCopy(
            BufferedQueue<BufferedQueue<HexNode>> pillar, int gridSideLength) {
        int[] columnLengths = calculateColumnLengthStream(gridSideLength);
        BufferedQueue<BufferedQueue<HexNode>> copiedGrid = new BufferedQueue<>(columnLengths.length);

        int index = 0;
        for (BufferedQueue<HexNode> column : pillar) {
            int removalCount = columnLengths[index++];
            BufferedQueue<HexNode> partition = new BufferedQueue<>(column.removeCount(removalCount));
            copiedGrid.add(partition);
        }

        return copiedGrid;
    }

    private static Grid compareFullRotation(HexagonDataStructure original, HexagonDataStructure copy) {
        int rotation = 0;
        final List<HexShape> originalShapes = convertToHexShapes(original);
        do {
            if (originalShapes.equals(convertToHexShapes(copy)))
                return new Grid(copy, rotation);
            copy.rotate();
        } while(++rotation != 6);//Full rotation
        return null;
    }

    private static List<HexShape> convertToHexShapes(HexagonDataStructure structure) {
        return structure.getBufferedQueues()
                .stream()
                .flatMap(Collection::stream)
                .map(HexNode::getHexShape)
                .collect(toList());
    }

    private static void moveToNextSegment(BufferedQueue<BufferedQueue<HexNode>> pillar,
                                          HexagonDataStructure copy) {
        BufferedQueue<BufferedQueue<HexNode>> copiedQueues = copy.getBufferedQueues();
        for (BufferedQueue<HexNode> column : copiedQueues)
            column.removeFirst();

        int index = 0;
        for (BufferedQueue<HexNode> column : pillar) {
            HexNode nextNode = column.removeFirst();
            copiedQueues.get(index++).add(nextNode);
        }
    }
}
