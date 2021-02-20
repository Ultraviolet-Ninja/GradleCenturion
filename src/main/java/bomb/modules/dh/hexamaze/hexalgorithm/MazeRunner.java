package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.modules.dh.hexamaze.HexTraits;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.FixedArrayQueue;
import bomb.tools.data.structures.MapStack;
import javafx.scene.paint.Color;

import java.util.LinkedList;

/**
 *
 */
public class MazeRunner {
    private static Color currentPegColor;
    private static Coordinates currentLocation;
    private static MapStack<Coordinates, Integer> historyStack;

    private static final Coordinates MOVE_NORTH_WEST = new Coordinates(-1, -1),
            MOVE_NORTH = new Coordinates(0, -1), MOVE_NORTH_EAST = new Coordinates(1, 0),
            MOVE_SOUTH_EAST = new Coordinates(1, 1), MOVE_SOUTH = new Coordinates(0, 1),
            MOVE_SOUTH_WEST = new Coordinates(-1, 0);

    /**
     * Don't let anyone instantiate this class
     */
    private MazeRunner(){}

    public static void getPegInformation(Color pegColor, int location){
        currentPegColor = pegColor;
        startingPointCalculator(location);
    }

    private static void startingPointCalculator(int location){
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

    public static LinkedList<Coordinates> runMaze(HexGrid grid){
        if (currentPegColor == null) return null;
        int sideToExit = grid.getRing().findIndex(currentPegColor);
        HexGrid gatedGridCopy = gateOffExits(new HexGrid(grid), sideToExit);
        historyStack = new MapStack<>();
        return decidePath(gatedGridCopy, sideToExit);
    }

    private static HexGrid gateOffExits(HexGrid copiedGrid, int sideToExit){
        HexTraits.HexWall[] allWalls = HexTraits.HexWall.values();
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> extraction = copiedGrid.exportTo2DQueue();
        if (sideToExit != 0)
            gateOffTopLeftSide(extraction, allWalls, sideToExit);
        if (sideToExit != 1)
            gateOffTopRightSide(extraction, allWalls, sideToExit);
        if (sideToExit != 2)
            gateOffRightSide(extraction, allWalls, sideToExit);
        if (sideToExit != 3)
            gateOffBottomRightSide(extraction, allWalls, sideToExit);
        if (sideToExit != 4)
            gateOffBottomLeftSide(extraction, allWalls, sideToExit);
        if (sideToExit != 5)
            gateOffLeftSide(extraction, allWalls, sideToExit);
        return copiedGrid;
    }

    private static void gateOffTopLeftSide(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> extraction,
                                           HexTraits.HexWall[] allWalls, int sideToExit){
        /* (0,0) (1,0) (2,0) (3,0)
         * Walls 0 and 1
         */
        for (int i = 0; i <= extraction.cap() / 2; i++) {
            if (i != 0 || sideToExit != 5)
                extraction.get(i).get(0).addWalls(allWalls[0]);
            if (i != extraction.cap() - 1 || sideToExit != 1)
                extraction.get(i).get(0).addWalls(allWalls[1]);
        }
    }

    private static void gateOffTopRightSide(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> extraction,
                                            HexTraits.HexWall[] allWalls, int sideToExit){
        /* (3,0) (4,0) (5,0) (6,0)
         * Walls 1 and 2
         */
        for (int i = extraction.cap() / 2; i < extraction.cap(); i++) {
            if (i != extraction.cap() / 2 || sideToExit != 0)
                extraction.get(i).get(0).addWalls(allWalls[1]);
            if (i != extraction.cap() - 1 || sideToExit != 2)
                extraction.get(i).get(0).addWalls(allWalls[2]);
        }
    }

    private static void gateOffRightSide(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> extraction,
                                         HexTraits.HexWall[] allWalls, int sideToExit){
        /* (6,0) (6,1) (6,2) (6,3)
         * Walls 2 and 5
         */
        int lastIndex = extraction.cap() - 1;
        for (int i = 0; i <= extraction.cap() / 2; i++) {
            if (i != 0 || sideToExit != 1)
                extraction.get(lastIndex).get(i).addWalls(allWalls[2]);
            if (i != extraction.cap() / 2 - 1 || sideToExit != 3)
                extraction.get(lastIndex).get(i).addWalls(allWalls[5]);
        }
    }

    private static void gateOffBottomRightSide(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> extraction,
                                               HexTraits.HexWall[] allWalls, int sideToExit){
        /* (3,6) (4,5) (5,4) (6,3)
         * Walls 4 and 5
         */
        for (int i = extraction.cap() / 2; i < extraction.cap(); i++) {
            int lastIndex = extraction.get(i).cap() - 1;
            if (i != extraction.cap() / 2 || sideToExit != 2)
                extraction.get(i).get(lastIndex).addWalls(allWalls[4]);
            if (i != extraction.cap() - 1 || sideToExit != 4)
                extraction.get(i).get(lastIndex).addWalls(allWalls[5]);
        }
    }

    private static void gateOffBottomLeftSide(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> extraction,
                                              HexTraits.HexWall[] allWalls, int sideToExit){
        /* (0,3) (1,4) (2,5) (3,6)
         * Walls 3 and 4
         */
        for (int i = 0; i <= extraction.cap() / 2; i++){
            int lastIndex = extraction.get(i).cap() - 1;
            if (i != 0 || sideToExit != 5)
                extraction.get(i).get(lastIndex).addWalls(allWalls[3]);
            if (i != extraction.cap() / 2 - 1 || sideToExit != 3)
                extraction.get(i).get(lastIndex).addWalls(allWalls[4]);
        }
    }

    private static void gateOffLeftSide(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> extraction,
                                        HexTraits.HexWall[] allWalls, int sideToExit){
        /* (0,0) (0,1) (0,2) (0,3)
         * Walls 0 and 3
         */
        for (int i = 0; i < extraction.get(0).cap(); i++) {
            if (i != 0 || sideToExit != 0)
                extraction.get(0).get(i).addWalls(allWalls[0]);
            if (i != extraction.get(0).cap() - 1 || sideToExit != 5)
                extraction.get(0).get(i).addWalls(allWalls[3]);
        }
    }

    private static LinkedList<Coordinates> decidePath(HexGrid gatedGrid, final int sideToExit){
        switch (sideToExit){
            case 0: return findExit(gatedGrid, "013245");
            case 1: return findExit(gatedGrid, "215403");
            case 2: return findExit(gatedGrid, "");
            case 3: return findExit(gatedGrid, "542310");
            case 4: return findExit(gatedGrid, "340512");
            default: return findExit(gatedGrid, "");
        }
    }

    /*
     * Basic Functionality
     * Checks walls in a specific order
     * Moves down the first one with an opening
     * Runs that path until a solution is found or all roads lead to dead ends
     */

    private static LinkedList<Coordinates> findExit(HexGrid gatedGrid, String wallOrder){
        Hex.HexNode playerPosition = gatedGrid.retrieveNode(currentLocation);
        historyStack.push(new Coordinates(currentLocation),
                playerPosition.checkExits() + 1);
        return traverseStartingNode(gatedGrid, wallOrder);
    }

    private static LinkedList<Coordinates> traverseStartingNode
            (HexGrid gatedGrid, final String wallOrder){
        Hex.HexNode playerPosition = gatedGrid.retrieveNode(currentLocation);
        LinkedList<Coordinates> exitOrder = null;
        String copyOrder = wallOrder;
        do{
            boolean traversedPath = false;
            String directionCheck = takeFirstChar(copyOrder);
            if (playerPosition.isPathClear(Integer.parseInt(directionCheck))) {
                documentMovement(gatedGrid, directionCheck);
                exitOrder = traverseMaze(gatedGrid, wallOrder, getBackTrackingMove(directionCheck));
                traversedPath = true;
            }

            if(exitOrder != null) copyOrder = "";
            else {
                copyOrder = copyOrder.replace(directionCheck, "");
                currentLocation = resetToStartPosition();
                if (traversedPath) decrementExits();
            }
        } while(!copyOrder.isEmpty());
        return exitOrder;
    }

    private static LinkedList<Coordinates> traverseMaze(HexGrid gatedGrid, final String wallOrder, Coordinates backTrackMove){
        boolean loopFlag;
        Hex.HexNode playerPosition = gatedGrid.retrieveNode(currentLocation);
        LinkedList<Coordinates> exitOrder = null;
        String copyOrder = wallOrder;

        do {
            boolean traversedPath = false;
            if (historyStack.getTopValue() == 0) {
                historyStack.pop();
                return null;
            }

            String directionCheck = takeFirstChar(copyOrder);
            if (playerPosition.isPathClear(Integer.parseInt(directionCheck))) {
                try {
                    documentMovement(gatedGrid, directionCheck);
                } catch (ArrayIndexOutOfBoundsException e){
//                    historyStack.push(new Coordinates(currentLocation), 0);
                    return historyStack.exportKeys();
                }
                exitOrder = traverseMaze(gatedGrid, wallOrder, getBackTrackingMove(directionCheck));
                traversedPath = true;
            }

            if (exitOrder != null) return exitOrder;
            copyOrder = copyOrder.replace(directionCheck, "");
            if (traversedPath) decrementExits();
            loopFlag = backTrackMove == getBackTrackingMove(takeFirstChar(copyOrder));
            if (!loopFlag && copyOrder.length() > 1) copyOrder = putMoveToEnd(copyOrder);
        } while(loopFlag || copyOrder.length() > 1);
        return null;
    }

    private static void documentMovement(HexGrid gatedGrid, String direction){
        movePosition(direction);
        addToHistory(gatedGrid);
    }

    private static void addToHistory(HexGrid gatedGrid){
        Hex.HexNode currentPosition = gatedGrid.retrieveNode(currentLocation);
        historyStack.push(new Coordinates(currentLocation),
                currentPosition.checkExits());
    }

    private static void movePosition(String direction){
        currentLocation.alterCurrentCoords(getCoordinatesFromDir(direction));
    }

    private static Coordinates getBackTrackingMove(String direction){
        int oppositeDirection = 5 - Integer.parseInt(direction);
        return getCoordinatesFromDir(String.valueOf(oppositeDirection));
    }

    private static String putMoveToEnd(String operationOrder){
        String first = operationOrder.substring(0, 1);
        return operationOrder.substring(1) + first;
    }

    private static Coordinates getCoordinatesFromDir(String direction){
        switch (direction){
            case "0": return MOVE_NORTH_WEST;
            case "1": return MOVE_NORTH;
            case "2": return MOVE_NORTH_EAST;
            case "3": return MOVE_SOUTH_WEST;
            case "4": return MOVE_SOUTH;
            default: return MOVE_SOUTH_EAST;
        }
    }

    private static Coordinates resetToStartPosition(){
        while (historyStack.size() != 1) historyStack.pop();
        return new Coordinates(historyStack.getBottomKey());
    }

    private static void decrementExits(){
        historyStack.setTopValue(historyStack.getTopValue() - 1);
    }

    private static String takeFirstChar(String in){
        return String.valueOf(in.charAt(0));
    }
}
