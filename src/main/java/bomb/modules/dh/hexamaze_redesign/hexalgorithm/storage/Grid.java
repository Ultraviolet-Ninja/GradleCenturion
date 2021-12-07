package bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage;

import bomb.tools.data.structures.ring.ArrayRing;
import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.CYAN;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.PINK;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.YELLOW;

public class Grid extends AbstractHexagon {
    public static final int GRID_SIDE_LENGTH = 4;

    private final ArrayRing<Color> colorRing;

    public Grid() {
        super(new HexagonDataStructure(GRID_SIDE_LENGTH));
        colorRing = new ArrayRing<>(RED, YELLOW, GREEN, CYAN, BLUE, PINK);
    }

    public Grid(HexagonDataStructure internalGrid, int neededRotations) {
        if (internalGrid.getSideLength() != GRID_SIDE_LENGTH)
            throw new IllegalArgumentException("Grid doesn't have required side length");

        hexagon = internalGrid;
        colorRing = new ArrayRing<>(RED, YELLOW, GREEN, CYAN, BLUE, PINK);
        colorRing.rotateCounterClockwise(neededRotations);
    }

    public Grid(HexagonDataStructure grid) {
        this(grid, 0);
    }

    public void rotate() {
        hexagon.rotate();
        colorRing.rotateCounterClockwise();
    }

    public ArrayRing<Color> getColorRing() {
        return colorRing;
    }

    public void resetColorRing() {
        colorRing.setToIndex(RED);
    }
}
