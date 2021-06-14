package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.tools.Coordinates;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class MazeRunnerv2 {
    private static Color currentPegColor;
    private static Coordinates currentLocation;

    //Movement Vectors
    private static final Coordinates NULL_MOVE = new Coordinates(0, 0),
            NORTH = new Coordinates(0, -1), SOUTH = new Coordinates(0, 1),
            LEFT_SIDE_NORTH_WEST = new Coordinates(-1, -1), LEFT_SIDE_NORTH_EAST = new Coordinates(1, 0),
            LEFT_SIDE_SOUTH_EAST = new Coordinates(1, 1), LEFT_SIDE_SOUTH_WEST = new Coordinates(-1, 0),
            RIGHT_SIDE_NORTH_WEST = LEFT_SIDE_SOUTH_WEST, RIGHT_SIDE_NORTH_EAST = new Coordinates(1, -1),
            RIGHT_SIDE_SOUTH_EAST = LEFT_SIDE_NORTH_EAST, RIGHT_SIDE_SOUTH_WEST = new Coordinates(-1, 1);


    /**
     * Don't let anyone instantiate this class
     */
    private MazeRunnerv2() {}

    public static void getPegInformation(Color pegColor, int location, int gridSideLength) {
        currentPegColor = pegColor;
        startingPointCalculator(location, gridSideLength);
    }

    private static void startingPointCalculator(int location, int gridSideLength){
        boolean found = false;
        int[] columnLengths = AbstractHexagon.calculateColumnLengths(gridSideLength);

        for (int k = 0; !found && k < columnLengths.length; k++){
            location -= k;
            if (location < columnLengths[k]){
                found = true;
                currentLocation = new Coordinates(k, location);
            }
        }
        if (!found) throw new IllegalStateException("This should be impossible");
    }

    public static LinkedList<Coordinates> runMaze(HexGrid grid){
        if (currentPegColor == null) return null;
        int sideToExit = grid.getRing().findIndex(currentPegColor);
        return null;
    }
}
