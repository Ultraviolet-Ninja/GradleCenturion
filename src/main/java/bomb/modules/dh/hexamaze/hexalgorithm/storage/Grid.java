package bomb.modules.dh.hexamaze.hexalgorithm.storage;

import bomb.tools.Coordinates;
import bomb.tools.data.structures.ring.ArrayRing;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.PlayerColor.BLUE;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.PlayerColor.CYAN;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.PlayerColor.GREEN;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.PlayerColor.NO_PLAYER;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.PlayerColor.PINK;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.PlayerColor.RED;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.PlayerColor.YELLOW;

public final class Grid extends AbstractHexagon implements Rotatable {
    public static final int GRID_SIDE_LENGTH = 4;

    private final ArrayRing<HexNode.PlayerColor> colorRing;

    public Grid() {
        super(new HexagonalPlane(GRID_SIDE_LENGTH));
        colorRing = new ArrayRing<>(RED, YELLOW, GREEN, CYAN, BLUE, PINK);
    }

    public Grid(@NotNull HexagonalPlane internalGrid, int neededRotations) {
        if (internalGrid.getSideLength() != GRID_SIDE_LENGTH)
            throw new IllegalArgumentException("Grid doesn't have required side length");

        hexagon = internalGrid;
        colorRing = new ArrayRing<>(RED, YELLOW, GREEN, CYAN, BLUE, PINK);
        colorRing.rotateCounterClockwise(neededRotations);
    }

    public Grid(HexagonalPlane grid) {
        this(grid, 0);
    }

    @Override
    public void rotate() {
        hexagon.rotate();
        colorRing.rotateCounterClockwise();
    }

    public ArrayRing<HexNode.PlayerColor> getColorRing() {
        return colorRing;
    }

    public int countPlayerPegs() {
        int counter = 0;
        for (var column : hexagon) {
            for (var node : column) {
                if (node.getPlayerColor() != NO_PLAYER) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public Optional<Coordinates> getStartingLocation() {
        int x = 0;
        for (var column : hexagon) {
            int y = 0;
            for (var node : column) {
                if (node.getPlayerColor() != NO_PLAYER) {
                    return Optional.of(new Coordinates(x, y));
                }
                y++;
            }
            x++;
        }

        return Optional.empty();
    }
}
