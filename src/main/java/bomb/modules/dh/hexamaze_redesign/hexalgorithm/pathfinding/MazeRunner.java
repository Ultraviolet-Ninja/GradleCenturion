package bomb.modules.dh.hexamaze_redesign.hexalgorithm.pathfinding;

import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexWall;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.queue.BufferedQueue;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.IntStream;

import static bomb.modules.dh.hexamaze.hexalgorithm.AbstractHexagon.calculateColumnLengths;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.Grid.GRID_SIDE_LENGTH;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexWall.BOTTOM;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexWall.BOTTOM_LEFT;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexWall.BOTTOM_RIGHT;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexWall.TOP;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexWall.TOP_LEFT;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexWall.TOP_RIGHT;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexagonDataStructure.CALCULATE_SPAN;
import static java.util.stream.Collectors.toList;

public class MazeRunner {
    //Movement Vectors
    private static final Coordinates MOVE_DOWN = new Coordinates(0, 1),
            MOVE_RIGHT = new Coordinates(1, 0),
            LEFT_SIDE_MOVE_DOWN_RIGHT = new Coordinates(1, 1),
            RIGHT_SIDE_MOVE_TOP_RIGHT = new Coordinates(1, -1);

    public static void runMaze(Grid grid) throws IllegalArgumentException {
        if (isOnlyOnePegSet(grid.getHexagon().getBufferedQueues()))
            throw new IllegalArgumentException("There isn't only one peg set for the grid");


    }

    private static List<Coordinates> getPossibleExits(Grid grid, int sideToExit) {
        List<Coordinates> output = switch (sideToExit) {
            case 0 -> getTopLeftSideExits();
            case 1 -> getTopRightSideExits();
            case 2 -> getRightSideExits();
            case 3 -> getBottomRightSideExits();
            case 4 -> getBottomLeftSideExits();
            default -> getLeftSideExits();
        };
        return filterBlockedExits(grid, output, sideToExit);
    }

    private static List<Coordinates> getTopLeftSideExits() {
        return IntStream.range(0, GRID_SIDE_LENGTH)
                .mapToObj(i -> new Coordinates(i, 0))
                .collect(toList());
    }

    private static List<Coordinates> getTopRightSideExits() {
        int gripSpan = CALCULATE_SPAN.applyAsInt(GRID_SIDE_LENGTH);

        return IntStream.range(GRID_SIDE_LENGTH - 1, gripSpan)
                .mapToObj(index -> new Coordinates(index, 0))
                .collect(toList());
    }

    private static List<Coordinates> getRightSideExits() {
        int[] columnCapacities = calculateColumnLengths(GRID_SIDE_LENGTH);
        int lastIndex = columnCapacities.length - 1;
        int finalColumnCapacity = columnCapacities[lastIndex];

        return IntStream.range(0, finalColumnCapacity)
                .mapToObj(i -> new Coordinates(lastIndex, i))
                .collect(toList());
    }

    private static List<Coordinates> getBottomRightSideExits() {
        int gridSpan = CALCULATE_SPAN.applyAsInt(GRID_SIDE_LENGTH);
        List<Coordinates> list = new ArrayList<>();
        int[] columnCapacities = calculateColumnLengths(GRID_SIDE_LENGTH);

        for (int i = GRID_SIDE_LENGTH - 1; i < gridSpan; i++) {
            list.add( new Coordinates(i, columnCapacities[i] - 1));
        }
        return list;
    }

    private static List<Coordinates> getBottomLeftSideExits() {
        List<Coordinates> output = new ArrayList<>();
        int[] columnCapacities = calculateColumnLengths(GRID_SIDE_LENGTH);

        for (int i = 0; i < GRID_SIDE_LENGTH; i++) {
            output.add(new Coordinates(i, columnCapacities[i] - 1));
        }
        return output;
    }

    private static List<Coordinates> getLeftSideExits() {
        List<Coordinates> output = new ArrayList<>();
        for (int i = 0; i < GRID_SIDE_LENGTH; i++) {
            output.add(new Coordinates(0, i));
        }
        return output;
    }

    private static List<Coordinates> filterBlockedExits(Grid grid, List<Coordinates> list, int sideToExit) {
        EnumSet<HexWall> wallsToFind = switch (sideToExit) {
            case 0 -> EnumSet.of(TOP_LEFT, TOP);
            case 1 -> EnumSet.of(TOP_RIGHT, TOP);
            case 2 -> EnumSet.of(TOP_RIGHT, BOTTOM_RIGHT);
            case 3 -> EnumSet.of(BOTTOM_RIGHT, BOTTOM);
            case 4 -> EnumSet.of(BOTTOM, BOTTOM_LEFT);
            default -> EnumSet.of(TOP_LEFT, BOTTOM_LEFT);
        };

        return list.stream()
                .filter(coordinates -> {
                    HexNode node = grid.getAtCoordinates(coordinates);
                    wallsToFind.removeAll(node.getWalls());
                    return wallsToFind.size() != 0;
                })
                .collect(toList());
    }

    private static boolean isOnlyOnePegSet(BufferedQueue<BufferedQueue<HexNode>> gridQueues) {
        int counter = 0;
        for (BufferedQueue<HexNode> queue : gridQueues) {
            for (HexNode node : queue) {
                if (node.getColor() != -1)
                    counter++;
            }
        }
        return counter == 1;
    }
}
