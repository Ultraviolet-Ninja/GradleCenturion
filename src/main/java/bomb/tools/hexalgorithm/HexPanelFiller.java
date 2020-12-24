package bomb.tools.hexalgorithm;

import bomb.components.hex.HexMazePanel;
import bomb.tools.Coordinates;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;

public class HexPanelFiller {

    private HexPanelFiller(){}

    public static void fillPanels
            (LinkedList<Coordinates> directionSet, ArrayList<HexMazePanel> panelArray,
             Color toFill, int hexagonSideLength){
        //directionSet.removeLast(); //TODO can be taken out if I want
        for (HexMazePanel panel : panelArray) panel.resetHexagonalFill();
        for (Coordinates coordinates : directionSet){
            panelArray.get(coordinateToIndex(coordinates, hexagonSideLength)).setHexagonalFill(toFill);
        }
    }

    private static int coordinateToIndex(Coordinates set, int sideLength){
        int counter = 0;
        int[] coordinates = set.getCoords(),
                pings = HexComparator.pingRequest(sideLength);
        for (int i = 0; i < coordinates[0]; i++) counter += pings[i];
        return counter + coordinates[1];
    }
}
