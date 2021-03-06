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
public class HexGrid extends AbstractHexagon{
    public static final int STANDARD_SIDE_LENGTH = 4;

    private final ReadOnlyRing<Color> colorRing;

    /**
     * Initializes a Hex object with a side length of 4, representing what the defuser sees on thr bomb
     */
    public HexGrid(){
        super(new HexagonDataStructure(STANDARD_SIDE_LENGTH));
        colorRing = new ReadOnlyRing<>(6);
        fillColorRing();
    }

    /**
     *
     *
     * @param grid The Hex of side length of 4 to wrap in a HexGrid
     * @param neededRotations The number of rotations the Hexagon needs to be the correct orientation
     * @throws IllegalArgumentException The side length of the given Hexagon isn't 4
     */
    public HexGrid(HexagonDataStructure grid, int neededRotations) throws IllegalArgumentException{
        if (grid.getSideLength() != STANDARD_SIDE_LENGTH)
            throw new IllegalArgumentException("Grid doesn't have a side length of 4");
        hexagon = grid;
        colorRing = new ReadOnlyRing<>(6);
        fillColorRing();
        for (int i = 0; i < neededRotations; i++) rotateColorOrder();
    }

    public HexGrid(HexagonDataStructure grid){
        this(grid, 0);
    }

    public HexGrid(HexGrid toCopy){
        hexagon = new HexagonDataStructure(copyNodes(toCopy.hexport()));
        colorRing = toCopy.colorRing;
    }

    /**
     * Fills an ArrayList of HexNodes from one of HexShapes
     *
     * @param shapeList The ArrayList of shapes to fill the HexGrid
     */
    public void fillWithShapes(ArrayList<HexNodeProperties.HexShape> shapeList){
        ArrayList<HexNode> nodeList = new ArrayList<>();
        for (HexNodeProperties.HexShape shape : shapeList)
            nodeList.add(new HexNode(shape, null));
        hexagon.readInNodeList(nodeList);
    }

    @Override
    public void rotate(){
        hexagon.rotate();
        rotateColorOrder();
    }

    /**
     * Rotates the internal Hex object as well as re-orders the color array to show
     * which one is in the top left edge
     */
    public void rotateColorOrder(){
        colorRing.rotateHeadCounter();
    }

    public ReadOnlyRing<Color> getRing(){
        return colorRing;
    }

    public void addWallsToHexagon(String wallHash){
        String[] splits = wallHash.split(":");
        for (int i = 0; i < Integer.parseInt(splits[1]); i++)
            rotateColorOrder();
        List<HexNode> stream = hexagon.exportToList();
        for (int i = 0; i < stream.size(); i++){
            stream.get(i).recreateWallsFromHash(splits[0].charAt(i));
        }
    }


    /**
     * Gets the HexNode from a specific set of coordinates
     *
     * @param pair The (x,y) combination to get the HexNode at
     * @return The result HexNode
     */
    public HexNode retrieveNode(Coordinates pair){
        int[] values = pair.getCoords();
        if (values[0] < 0 || values[0] >= hexagon.getSpan()) return null;
        BufferedQueue<HexNode> column = exportTo2DQueue().get(values[0]);

        if (values[1] < 0 || values[1] >= column.cap()) return null;
        return column.get(values[1]);
    }

    private List<HexNode> copyNodes(HexagonDataStructure hexagonDataStructure){
        List<HexNode> toNewHex = new ArrayList<>(),
                old = hexagonDataStructure.exportToList();
        for (HexNode hexNode : old)
            toNewHex.add(new HexagonDataStructure.HexNode(hexNode));
        return toNewHex;
    }

    private void fillColorRing(){
        colorRing.add(Color.RED);
        colorRing.add(Color.YELLOW);
        colorRing.add(Color.GREEN);
        colorRing.add(Color.CYAN);
        colorRing.add(Color.BLUE);
        colorRing.add(Color.PINK);
    }

    public void resetColorRing(){
        while (colorRing.getHeadData() != Color.RED)
            rotateColorOrder();
    }

    public ArrayList<String> hashStrings(){
        ArrayList<String> outputs = new ArrayList<>(2);
        StringBuilder shapeHash = new StringBuilder(), wallHash = new StringBuilder();
        BufferedQueue<BufferedQueue<HexNode>> queues = hexagon.exportTo2DQueue();

        for (int x = 0; x < queues.cap(); x++){
            for (int y = 0; y < queues.get(x).cap(); y++){
                shapeHash.append(queues.get(x).get(y).getShapeHash());
                wallHash.append(queues.get(x).get(y).getWallHash());
            }
        }
        wallHash.append(":").append(colorRing.findIndex(Color.RED));

        outputs.add(shapeHash.toString());
        outputs.add(wallHash.toString());
        return outputs;
    }
}
