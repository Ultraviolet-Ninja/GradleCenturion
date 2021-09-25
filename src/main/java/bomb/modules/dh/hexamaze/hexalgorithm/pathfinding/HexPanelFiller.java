package bomb.modules.dh.hexamaze.hexalgorithm.pathfinding;

import bomb.components.hex.HexMazePanel;
import bomb.modules.dh.hexamaze.hexalgorithm.AbstractHexagon;
import bomb.tools.Coordinates;
import javafx.scene.paint.Color;

import java.util.List;

public class HexPanelFiller {

    private HexPanelFiller() {
    }

    public static void fillPanels(List<Coordinates> directionSet, List<HexMazePanel> panelArray,
                                  Color toFill, int hexagonSideLength) {
        for (HexMazePanel panel : panelArray) panel.resetHexagonalFill();
        for (Coordinates coordinates : directionSet) {
            panelArray.get(coordinateToIndex(coordinates, hexagonSideLength)).setHexagonalFill(toFill);
        }
    }

    private static int coordinateToIndex(Coordinates originalCoordinates, int sideLength) {
        int counter = 0;
        int[] columnLengths = AbstractHexagon.calculateColumnLengths(sideLength);
        for (int i = 0; i < originalCoordinates.getX(); i++) counter += columnLengths[i];
        return counter + originalCoordinates.getY();
    }
}
