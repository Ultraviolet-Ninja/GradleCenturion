package bomb.modules.dh.hexamaze.hexalgorithm.storage;

import bomb.tools.data.structures.ring.ArrayRing;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.CYAN;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.PINK;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.YELLOW;

public class Grid extends AbstractHexagon implements Rotatable {
    public static final int GRID_SIDE_LENGTH = 4;

    private final ArrayRing<Color> colorRing;

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

    public ArrayRing<Color> getColorRing() {
        return colorRing;
    }
}
