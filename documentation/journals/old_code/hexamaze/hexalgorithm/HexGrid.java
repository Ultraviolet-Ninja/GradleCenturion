package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure.HexNode;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.queue.BufferedQueue;
import bomb.tools.data.structures.ring.ArrayRing;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * This class creates a representation of what hte Defuser sees on the Bomb.
 * It encapsulates a Hex class and contains data that's appropriate for that puzzle that
 * wouldn't be needed for all Hex objects
 */
public class HexGrid extends AbstractHexagon {
    public static final byte STANDARD_SIDE_LENGTH = 4;

    private final ArrayRing<Color> colorRing;

    /**
     * Initializes a Hex object with a side length of 4, representing what the Defuser sees on thr bomb
     */
    public HexGrid() {
        super(new HexagonDataStructure(STANDARD_SIDE_LENGTH));
        colorRing = new ArrayRing<>(Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.PINK);
    }

    /**
     * @param grid            The Hex of side length of 4 to wrap in a HexGrid
     * @param neededRotations The number of rotations the Hexagon needs to be the correct orientation
     * @throws IllegalArgumentException The side length of the given Hexagon isn't 4
     */
    public HexGrid(HexagonDataStructure grid, int neededRotations) throws IllegalArgumentException {
        if (grid.getSideLength() != STANDARD_SIDE_LENGTH)
            throw new IllegalArgumentException("Grid doesn't have a side length of 4");

        hexagon = grid;
        colorRing = new ArrayRing<>(Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.PINK);
        for (int i = 0; i < neededRotations; i++) rotateColorOrder();
    }

    public HexGrid(HexagonDataStructure grid) {
        this(grid, 0);
    }

    /**
     * Fills an ArrayList with HexNodes from one of HexShapes
     *
     * @param shapeList The List of shapes to fill the HexGrid
     */
    public void fillWithShapes(List<HexNodeProperties.HexShape> shapeList) {
        List<HexNode> nodeList = new ArrayList<>();

        for (HexNodeProperties.HexShape shape : shapeList)
            nodeList.add(new HexNode(shape, null));
        hexagon.readInNodeList(nodeList);
    }

    public void rotate() {
        hexagon.rotate();
        rotateColorOrder();
    }

    /**
     * Rotates the internal Hex object as well as re-orders the color array to show
     * which one is in the top left edge
     */
    public void rotateColorOrder() {
        colorRing.rotateCounterClockwise();
    }

    public ArrayRing<Color> getRing() {
        return colorRing;
    }

    public void addWallsToHexagon(String wallHash) {
        String[] splits = wallHash.split(":");

        for (int i = 0; i < Integer.parseInt(splits[1]); i++)
            rotateColorOrder();

        int index = 0;
        List<HexNode> nodeList = hexagon.exportToList();
        for (HexNode node : nodeList) {
            node.recreateWallsFromHash(splits[0].charAt(index++));
        }
    }

    /**
     * Gets the HexNode from a specific set of coordinates
     *
     * @param pair The (x,y) combination to get the HexNode at
     * @return The result HexNode
     */
    public HexNode retrieveNode(Coordinates pair) {
        if (pair.x() < 0 || pair.x() >= hexagon.getSpan()) return null;
        BufferedQueue<HexNode> column = exportTo2DQueue().get(pair.x());

        if (pair.y() < 0 || pair.y() >= column.getCapacity()) return null;
        return column.get(pair.y());
    }

    public void resetColorRing() {
        while (colorRing.getHeadData() != Color.RED)
            rotateColorOrder();
    }

    public List<String> createHashStrings() {
        List<String> outputs = new ArrayList<>(2);
        StringBuilder shapeHash = new StringBuilder(), wallHash = new StringBuilder();

        for (BufferedQueue<HexNode> column : hexagon) {
            for (HexNode node : column) {
                shapeHash.append(node.getShapeHash());
                wallHash.append(node.getWallHash());
            }
        }
        wallHash.append(":").append(colorRing.findRelativeIndex(Color.RED));

        outputs.add(shapeHash.toString());
        outputs.add(wallHash.toString());
        return outputs;
    }
}
