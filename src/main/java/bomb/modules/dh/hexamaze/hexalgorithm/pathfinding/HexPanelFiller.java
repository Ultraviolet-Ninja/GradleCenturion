package bomb.modules.dh.hexamaze.hexalgorithm.pathfinding;

import bomb.components.hex.HexMazePanel;
import bomb.modules.dh.hexamaze.hexalgorithm.AbstractHexagon;
import bomb.tools.Coordinates;
import javafx.scene.paint.Color;

import java.util.List;

public class HexPanelFiller {

    private HexPanelFiller(){}

    public static void fillPanels(List<Coordinates> directionSet, List<HexMazePanel> panelArray,
                                  Color toFill, int hexagonSideLength){
        for (HexMazePanel panel : panelArray) panel.resetHexagonalFill();
        for (Coordinates coordinates : directionSet){
            panelArray.get(coordinateToIndex(coordinates, hexagonSideLength)).setHexagonalFill(toFill);
        }
    }

    private static int coordinateToIndex(Coordinates set, int sideLength){
        int counter = 0;
        int[] coordinates = set.getCoords(),
                pings = AbstractHexagon.calculateColumnLengths(sideLength);
        for (int i = 0; i < coordinates[0]; i++) counter += pings[i];
        return counter + coordinates[1];
    }
}
