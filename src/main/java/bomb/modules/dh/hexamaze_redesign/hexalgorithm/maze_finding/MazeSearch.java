package bomb.modules.dh.hexamaze_redesign.hexalgorithm.maze_finding;

import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexShape;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexagonDataStructure;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.Maze;
import bomb.tools.data.structures.queue.BufferedQueue;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.AbstractHexagon.calculateColumnLengthStream;
import static java.util.stream.Collectors.toList;

public class MazeSearch {
    public static Grid search(Maze maze, Grid grid) {
        int gridSpan = grid.getHexagon().getSpan();
        Stream<BufferedQueue<BufferedQueue<HexNode>>> pillarStream =
                generatePillarStream(maze, gridSpan);

        return pillarStream.map(pillar -> searchPillar(pillar, grid))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private static Stream<BufferedQueue<BufferedQueue<HexNode>>> generatePillarStream(Maze maze, int gridSpan) {
        int mazeSpan = maze.getHexagon().getSpan();
        return IntStream.rangeClosed(0, mazeSpan - gridSpan)
                .mapToObj(index -> generateSinglePillar(maze, gridSpan, index));
    }

    private static BufferedQueue<BufferedQueue<HexNode>> generateSinglePillar(Maze maze, int gridSpan, int offset) {
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
        if (pillar.get(start).getCapacity() == pillar.get(end).getCapacity())
            return;

        int[] removalCounts = new int[length];
        while(start != end) {
            int change = pillar.get(start).getCapacity() - pillar.get(end).getCapacity();
            change /= 2;
            removalCounts[start++] = Math.max(change, 0);
            removalCounts[end--] = Math.max(-change, 0);
        }

        for (int i = 0; i < length; i++) {
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
        BufferedQueue<BufferedQueue<HexNode>> copiedGrid = new BufferedQueue<>(pillar.getCapacity());

        for (int i = 0; i < columnLengths.length; i++) {
            BufferedQueue<HexNode> column = pillar.get(i);
            int removalCount = columnLengths[i];
            BufferedQueue<HexNode> partition = new BufferedQueue<>(column.removeCount(removalCount));
            copiedGrid.add(partition);
        }

        return copiedGrid;
    }

    private static Grid compareFullRotation(HexagonDataStructure original, HexagonDataStructure copy) {
        int rotation = 0;
        List<HexShape> originalShapes = convertToHexShapes(original);
        do {
            List<HexShape> copiedShapes = convertToHexShapes(copy);
            if (originalShapes.equals(copiedShapes))
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

        int length = pillar.getCapacity();
        for (int i = 0; i < length; i++) {
            HexNode nextNode = pillar.get(i).removeFirst();
            copiedQueues.get(i).add(nextNode);
        }
    }
}
