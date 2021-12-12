package bomb.modules.dh.hexamaze_redesign.hexalgorithm.pathfinding;

import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexWall;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.queue.BufferedQueue;
import javafx.scene.paint.Color;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static bomb.modules.dh.hexamaze.hexalgorithm.AbstractHexagon.calculateColumnLengths;
import static bomb.modules.dh.hexamaze_redesign.Hexamaze.COLOR_MAP;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.Grid.GRID_SIDE_LENGTH;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexWall.BOTTOM;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexWall.BOTTOM_LEFT;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexWall.BOTTOM_RIGHT;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexWall.TOP;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexWall.TOP_LEFT;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexWall.TOP_RIGHT;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexagonalPlane.CALCULATE_SPAN;
import static java.util.stream.Collectors.toList;

public class ExitChecker {
    public static Pair<String, List<Coordinates>> findPossibleExits(Grid grid) throws IllegalArgumentException {
        int pegCount = countPegsOnGrid(grid.getHexagon().getBufferedQueues());
        if (pegCount == 0)
            return null;
        if (pegCount > 1)
            throw new IllegalArgumentException("Cannot have more than one peg on the board");

        int sideToExit = getSideToExit(grid);
        return getPossibleExits(grid, sideToExit);
    }

    private static Pair<String, List<Coordinates>> getPossibleExits(Grid grid, int sideToExit) {
        List<Coordinates> output = switch (sideToExit) {
            case 0 -> getTopLeftSideExits();
            case 1 -> getTopRightSideExits();
            case 2 -> getRightSideExits();
            case 3 -> getBottomRightSideExits();
            case 4 -> getBottomLeftSideExits();
            default -> getLeftSideExits();
        };
        String exitDirectionText = switch (sideToExit) {
            case 0 -> "Top Left";
            case 1 -> "Top Right";
            case 2 -> "Right";
            case 3 -> "Bottom Right";
            case 4 -> "Bottom Left";
            default -> "Left";
        };
        List<Coordinates> filteredOutput = filterBlockedExits(grid, output, sideToExit);
        return new Pair<>(exitDirectionText, filteredOutput);
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
                    return isOneWallClear(node, wallsToFind);
                })
                .collect(toList());
    }

    private static boolean isOneWallClear(HexNode node, EnumSet<HexWall> wallsToFind) {
        for (HexWall wall : wallsToFind) {
            if (!node.isPathBlocked(wall))
                return true;
        }
        return false;
    }

    private static int countPegsOnGrid(BufferedQueue<BufferedQueue<HexNode>> gridQueues) {
        int counter = 0;
        for (BufferedQueue<HexNode> queue : gridQueues) {
            for (HexNode node : queue) {
                if (node.getColor() != -1) counter++;
            }
        }
        return counter;
    }

    private static int getSideToExit(Grid grid) throws IllegalArgumentException {
        int pegValue = getPegValue(grid.getHexagon().getBufferedQueues());
        Color pegColor = getPegColor(pegValue);

        return grid.getColorRing()
                .findRelativeIndex(pegColor);
    }

    private static Color getPegColor(int pegValue) throws IllegalArgumentException {
        for (Map.Entry<Color, Integer> entry : COLOR_MAP.entrySet()) {
            if (entry.getValue() == pegValue)
                return entry.getKey();
        }
        throw new IllegalArgumentException();
    }

    private static int getPegValue(BufferedQueue<BufferedQueue<HexNode>> gridQueues)
            throws IllegalArgumentException {
        int pegValue;
        for (BufferedQueue<HexNode> queue : gridQueues) {
            for (HexNode node : queue) {
                pegValue = node.getColor();
                if (pegValue != -1)
                    return pegValue;
            }
        }
        throw new IllegalArgumentException();
    }
}
