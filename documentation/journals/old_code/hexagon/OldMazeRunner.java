package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.tools.Coordinates;
import bomb.tools.data.structures.MapStack;
import javafx.scene.paint.Color;

import java.util.LinkedList;

@Deprecated
public class OldMazeRunner {
    private static Color currentPegColor;
    private static Coordinates currentLocation;
    private static MapStack<Coordinates, Integer> historyStack;

    //Movement Vectors
    private static final Coordinates NULL_MOVE = new Coordinates(0, 0),
            NORTH = new Coordinates(0, -1), SOUTH = new Coordinates(0, 1),
            LEFT_SIDE_NORTH_WEST = new Coordinates(-1, -1), LEFT_SIDE_NORTH_EAST = new Coordinates(1, 0),
            LEFT_SIDE_SOUTH_EAST = new Coordinates(1, 1), LEFT_SIDE_SOUTH_WEST = new Coordinates(-1, 0),
            RIGHT_SIDE_NORTH_WEST = LEFT_SIDE_SOUTH_WEST, RIGHT_SIDE_NORTH_EAST = new Coordinates(1, -1),
            RIGHT_SIDE_SOUTH_EAST = LEFT_SIDE_NORTH_EAST, RIGHT_SIDE_SOUTH_WEST = new Coordinates(-1, 1);

    //Orders of movement
    private static final String EXIT_NORTH_WEST = "013245", EXIT_NORTH_EAST = "215403", EXIT_EAST = "524103",
            EXIT_SOUTH_EAST = "542310", EXIT_SOUTH_WEST = "340512", EXIT_WEST = "031425";

    /**
     * Don't let anyone instantiate this class
     */
    private MazeRunner() {}

    public static void getPegInformation(Color pegColor, int location) {
        currentPegColor = pegColor;
        startingPointCalculator(location);
    }

    private static void startingPointCalculator(int location) {
        if (location < 4)
            currentLocation = new Coordinates(0, location);
        else if (location < 9)
            currentLocation = new Coordinates(1, location - 4);
        else if (location < 15)
            currentLocation = new Coordinates(2, location - 9);
        else if (location < 22)
            currentLocation = new Coordinates(3, location - 15);
        else if (location < 28)
            currentLocation = new Coordinates(4, location - 22);
        else if (location < 33)
            currentLocation = new Coordinates(5, location - 28);
        else
            currentLocation = new Coordinates(6, location - 33);
    }

    public static LinkedList<Coordinates> runMaze(HexGrid grid) {
        if (currentPegColor == null) return null;
        int sideToExit = grid.getRing().findIndex(currentPegColor);
        HexGrid gatedGridCopy = MazeFencer.gateOffExits(new HexGrid(grid), sideToExit);
        historyStack = new MapStack<>();
        return decidePath(gatedGridCopy, sideToExit);
    }

    private static LinkedList<Coordinates> decidePath(HexGrid gatedGrid, final int sideToExit) {
        switch (sideToExit) {
            case 0:
                return findExit(gatedGrid, EXIT_NORTH_WEST);
            case 1:
                return findExit(gatedGrid, EXIT_NORTH_EAST);
            case 2:
                return findExit(gatedGrid, EXIT_EAST);
            case 3:
                return findExit(gatedGrid, EXIT_SOUTH_EAST);
            case 4:
                return findExit(gatedGrid, EXIT_SOUTH_WEST);
            default:
                return findExit(gatedGrid, EXIT_WEST);
        }
    }

    private static LinkedList<Coordinates> findExit(HexGrid gatedGrid, final String wallOrder) {
        Hex.HexNode playerPosition = gatedGrid.retrieveNode(currentLocation);
        historyStack.push(new Coordinates(currentLocation),
                playerPosition.checkExits() + 1);
        return traverseStartingNode(gatedGrid, wallOrder);
    }

    private static LinkedList<Coordinates> traverseStartingNode
            (HexGrid gatedGrid, final String wallOrder) {
        Hex.HexNode playerPosition = gatedGrid.retrieveNode(currentLocation);
        LinkedList<Coordinates> exitStack = null;
        String copiedSearchOrder = wallOrder;
        do {
            boolean traversedPath = false;
            String directionCheck = takeFirstChar(copiedSearchOrder);
            if (playerPosition.isPathClear(Integer.parseInt(directionCheck))) {
                documentMovement(gatedGrid, directionCheck);
                exitStack = traverseMaze(gatedGrid, wallOrder, getBackTrackingMove(directionCheck, gatedGrid));
                traversedPath = true;
            }

            if (exitStack != null) copiedSearchOrder = "";
            else {
                copiedSearchOrder = copiedSearchOrder.replace(directionCheck, "");
                currentLocation = resetToStartPosition();
                if (traversedPath) decrementExits();
            }
        } while (!copiedSearchOrder.isEmpty());
        return exitStack;
    }

    private static LinkedList<Coordinates> traverseMaze
            (HexGrid gatedGrid, final String wallOrder, Coordinates backTrackMove) {
        boolean backTrackFlag, traversedPath;
        Hex.HexNode playerPosition = gatedGrid.retrieveNode(currentLocation);
        LinkedList<Coordinates> exitStack = null;
        String copiedSearchOrder = wallOrder;
        do {
            traversedPath = false;
            if (historyStack.getTopValue() == 0) {
                popMoveOff();
                return null;
            }

            String directionCheck = takeFirstChar(copiedSearchOrder);
            if (playerPosition.isPathClear(Integer.parseInt(directionCheck))) {
                try {
                    documentMovement(gatedGrid, directionCheck);
                } catch (ArrayIndexOutOfBoundsException e) {
//                    historyStack.push(new Coordinates(currentLocation), 0);
                    return historyStack.exportKeys();
                }
                exitStack = traverseMaze(gatedGrid, wallOrder, getBackTrackingMove(directionCheck, gatedGrid));
                traversedPath = true;
            }

            if (exitStack != null) return exitStack;
            copiedSearchOrder = copiedSearchOrder.replace(directionCheck, "");
            if (traversedPath) decrementExits();
            backTrackFlag = backTrackMatch(takeFirstChar(copiedSearchOrder), backTrackMove);

            if (backTrackFlag && copiedSearchOrder.length() > 1) copiedSearchOrder = putMoveToEnd(copiedSearchOrder);
        } while (backTrackFlag || copiedSearchOrder.length() > 1);
        return null;
    }

    private static boolean backTrackMatch(String direction, Coordinates backTrackMove) {
        boolean leftSide = backTrackMove == leftSideNextMove(direction),
                rightSide = backTrackMove == rightSideNextMove(direction),
                upOrDown = backTrackMove == upOrDown(direction); //TODO that's a problem
        return leftSide || rightSide || upOrDown;
    }

    private static void documentMovement(HexGrid gatedGrid, String direction) {
        moveForward(direction, gatedGrid);
        addToHistory(gatedGrid.retrieveNode(currentLocation).checkExits());
    }

    private static void addToHistory(int exits) {
        historyStack.push(new Coordinates(currentLocation), exits);
    }

    private static void moveForward(String direction, HexGrid gatedGrid) {
        if ("14".contains(direction))
            currentLocation.alterCurrentCoords(upOrDown(direction));
        else
            calculateNextMove(direction, gatedGrid);
    }

    private static void calculateNextMove(String direction, HexGrid gatedGrid) {
        currentLocation.alterCurrentCoords(calculateMove(direction, gatedGrid));
    }

    private static Coordinates getBackTrackingMove(String direction, HexGrid gatedGrid) {
        int oppositeDirection = 5 - Integer.parseInt(direction);
        return calculateMove(String.valueOf(oppositeDirection), gatedGrid);
    }

    private static Coordinates calculateMove(String direction, HexGrid gatedGrid){
        if ("14".contains(direction)) return upOrDown(direction);
        int x = currentLocation.x();
        if (x == gatedGrid.sideLength() - 1)
            return centerColumnNextMove(direction);
        else if (x < gatedGrid.sideLength() - 1)
            return leftSideNextMove(direction);
        else
            return rightSideNextMove(direction);
    }

    private static String putMoveToEnd(String operationOrder) {
        String first = operationOrder.substring(0, 1);
        return operationOrder.substring(1) + first;
    }

    private static Coordinates upOrDown(String direction) {
        if (direction.equals("1"))
            return NORTH;
        else if(direction.equals("4"))
            return SOUTH;
        return NULL_MOVE;
    }

    private static Coordinates centerColumnNextMove(String direction) {
        if ("03".contains(direction)) {
            return leftSideNextMove(direction);
        }
        return rightSideNextMove(direction);
    }

    private static Coordinates rightSideNextMove(String direction) {
        return getCoordinates(direction, RIGHT_SIDE_NORTH_WEST, RIGHT_SIDE_NORTH_EAST,
                RIGHT_SIDE_SOUTH_WEST, RIGHT_SIDE_SOUTH_EAST);
    }

    private static Coordinates leftSideNextMove(String direction) {
        return getCoordinates(direction, LEFT_SIDE_NORTH_WEST, LEFT_SIDE_NORTH_EAST,
                LEFT_SIDE_SOUTH_WEST, LEFT_SIDE_SOUTH_EAST);
    }

    private static Coordinates getCoordinates(String direction, Coordinates northWest, Coordinates northEast,
                                              Coordinates southWest, Coordinates southEast) {
        switch (direction) {
            case "0":
                return northWest;
            case "2":
                return northEast;
            case "3":
                return southWest;
            case "5":
                return southEast;
            default:
                return NULL_MOVE;
        }
    }

    private static void popMoveOff() {
        historyStack.pop();
        currentLocation = new Coordinates(historyStack.getTopKey());
    }

    private static Coordinates resetToStartPosition() {
        while (historyStack.size() != 1) historyStack.pop();
        return new Coordinates(historyStack.getBottomKey());
    }

    private static void decrementExits() {
        historyStack.setTopValue(historyStack.getTopValue() - 1);
    }

    private static String takeFirstChar(String in) {
        return String.valueOf(in.charAt(0));
    }
}
