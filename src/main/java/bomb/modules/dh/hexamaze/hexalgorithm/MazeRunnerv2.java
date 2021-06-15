package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.modules.dh.hexamaze.HexTraits;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.FixedArrayQueue;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static bomb.modules.dh.hexamaze.HexTraits.HexWall.*;

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
            if (location < columnLengths[k]){
                found = true;
                currentLocation = new Coordinates(k, location);
            }
            location -= columnLengths[k];
        }
        if (!found) throw new IllegalStateException("This should be impossible");
    }

    public static LinkedList<Coordinates> runMaze(HexGrid grid){
        if (currentPegColor == null) return null;
        int sideToExit = grid.getRing().findIndex(currentPegColor);
        List<Coordinates> possibleExits = getPossibleExits(grid, sideToExit);
        possibleExits = filterBlockedExits(grid, possibleExits, sideToExit);
        return null;
    }

    private static List<Coordinates> getPossibleExits(HexGrid grid, int sideToExit){
        switch (sideToExit){
            case 0:
                return getTopLeftSide(grid);
            case 1:
                return getTopRightSide(grid);
            case 2:
                return getRightSide(grid);
            case 3:
                return getBottomRightSide(grid);
            case 4:
                return getBottomLeftSide(grid);
            default:
                return getLeftSide(grid);
        }
    }

    private static List<Coordinates> getTopLeftSide(HexGrid grid){
        ArrayList<Coordinates> output = new ArrayList<>();
        for (int i = 0; i < grid.sideLength(); i++){
            output.add(new Coordinates(i, 0));
        }
        return output;
    }

    private static List<Coordinates> getTopRightSide(HexGrid grid){
        ArrayList<Coordinates> output = new ArrayList<>();
        for (int i = grid.sideLength() - 1; i < grid.getSpan(); i++){
            output.add(new Coordinates(i, 0));
        }
        return output;
    }

    private static List<Coordinates> getRightSide(HexGrid grid){
        ArrayList<Coordinates> output = new ArrayList<>();
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> internals = grid.exportTo2DQueue();
        int lastIndex = internals.cap() - 1;
        for (int i = 0; i < internals.get(lastIndex).cap(); i++){
            output.add(new Coordinates(lastIndex, i));
        }
        return output;
    }

    private static List<Coordinates> getBottomRightSide(HexGrid grid){
        ArrayList<Coordinates> output = new ArrayList<>();
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> internals = grid.exportTo2DQueue();
        for (int i = grid.sideLength() - 1; i < grid.getSpan(); i++){
            output.add(new Coordinates(i, internals.get(i).cap()-1));
        }
        return output;
    }

    private static List<Coordinates> getBottomLeftSide(HexGrid grid){
        ArrayList<Coordinates> output = new ArrayList<>();
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> internals = grid.exportTo2DQueue();
        for (int i = 0; i < grid.sideLength(); i++){
            output.add(new Coordinates(i, internals.get(i).cap()-1));
        }
        return output;
    }

    private static List<Coordinates> getLeftSide(HexGrid grid){
        ArrayList<Coordinates> output = new ArrayList<>();
        for (int i = 0; i < grid.sideLength(); i++){
            output.add(new Coordinates(0, i));
        }
        return output;
    }

    private static List<Coordinates> filterBlockedExits(HexGrid grid, List<Coordinates> list, int sideToExit){
        HexTraits.HexWall[] wallsToFind;
        switch(sideToExit){
            case 0:
                wallsToFind = new HexTraits.HexWall[]{TopLeft, Top};
                break;
            case 1:
                wallsToFind = new HexTraits.HexWall[]{TopRight, Top};
                break;
            case 2:
                wallsToFind = new HexTraits.HexWall[]{TopRight, BottomRight};
                break;
            case 3:
                wallsToFind = new HexTraits.HexWall[]{BottomRight, Bottom};
                break;
            case 4:
                wallsToFind = new HexTraits.HexWall[]{Bottom, BottomLeft};
                break;
            default:
                wallsToFind = new HexTraits.HexWall[]{TopLeft, BottomLeft};
        }
        return list.stream().filter(coords -> grid.retrieveNode(coords).isPathClear(wallsToFind[0].ordinal()) ||
                        grid.retrieveNode(coords).isPathClear(wallsToFind[1].ordinal()))
                .collect(Collectors.toList());
    }


}
