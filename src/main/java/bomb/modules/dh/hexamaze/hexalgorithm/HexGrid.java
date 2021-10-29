package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure.HexNode;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.BufferedQueue;
import bomb.tools.data.structures.ring.ReadOnlyRing;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * This class creates a representation of what hte defuser sees on the Bomb.
 * It encapsulates a Hex class and contains data that's appropriate for that puzzle that
 * wouldn't be needed for all Hex objects
 */
public class HexGrid extends AbstractHexagon {
    public static final byte STANDARD_SIDE_LENGTH = 4;

    private final ReadOnlyRing<Color> colorRing;

    /**
     * Initializes a Hex object with a side length of 4, representing what the defuser sees on thr bomb
     */
    public HexGrid() {
        super(new HexagonDataStructure(STANDARD_SIDE_LENGTH));
        colorRing = new ReadOnlyRing<>(Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.PINK);
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
        colorRing = new ReadOnlyRing<>(Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.PINK);
        for (int i = 0; i < neededRotations; i++) rotateColorOrder();
    }

    public HexGrid(HexagonDataStructure grid) {
        this(grid, 0);
    }

    /**
     * Fills an ArrayList with HexNodes from one of HexShapes
     *
     * @param shapeList The ArrayList of shapes to fill the HexGrid
     */
    public void fillWithShapes(List<HexNodeProperties.HexShape> shapeList) {
        ArrayList<HexNode> nodeList = new ArrayList<>();
        for (HexNodeProperties.HexShape shape : shapeList)
            nodeList.add(new HexNode(shape, null));
        hexagon.readInNodeList(nodeList);
    }

    @Override
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

    public ReadOnlyRing<Color> getRing() {
        return colorRing;
    }

    public void addWallsToHexagon(String wallHash) {
        String[] splits = wallHash.split(":");
        for (int i = 0; i < Integer.parseInt(splits[1]); i++)
            rotateColorOrder();
        List<HexNode> stream = hexagon.exportToList();
        for (int i = 0; i < stream.size(); i++) {
            stream.get(i).recreateWallsFromHash(splits[0].charAt(i));
        }
    }


    /**
     * Gets the HexNode from a specific set of coordinates
     *
     * @param pair The (x,y) combination to get the HexNode at
     * @return The result HexNode
     */
    public HexNode retrieveNode(Coordinates pair) {
        if (pair.getX() < 0 || pair.getX() >= hexagon.getSpan()) return null;
        BufferedQueue<HexNode> column = exportTo2DQueue().get(pair.getX());

        if (pair.getY() < 0 || pair.getY() >= column.getCapacity()) return null;
        return column.get(pair.getY());
    }

    public void resetColorRing() {
        while (colorRing.getHeadData() != Color.RED)
            rotateColorOrder();
    }

    public List<String> createHashStrings() {
        List<String> outputs = new ArrayList<>(2);
        StringBuilder shapeHash = new StringBuilder(), wallHash = new StringBuilder();
        BufferedQueue<BufferedQueue<HexNode>> queues = hexagon.exportTo2DQueue();

        for (int x = 0; x < queues.getCapacity(); x++) {
            for (int y = 0; y < queues.get(x).getCapacity(); y++) {
                shapeHash.append(queues.get(x).get(y).getShapeHash());
                wallHash.append(queues.get(x).get(y).getWallHash());
            }
        }
        wallHash.append(":").append(colorRing.findRelativeIndex(Color.RED));

        outputs.add(shapeHash.toString());
        outputs.add(wallHash.toString());
        return outputs;
    }
}
