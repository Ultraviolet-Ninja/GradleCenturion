package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.modules.dh.hexamaze.HexTraits;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.FixedArrayQueue;
import bomb.tools.data.structures.ring.ReadOnlyRing;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * This class creates a representation of what hte defuser sees on the Bomb.
 * It encapsulates a Hex class and contains data that's appropriate for that puzzle that
 * wouldn't be needed for all Hex objects
 */
public class HexGrid extends AbstractHexagon{
    private final ReadOnlyRing<Color> colorRing;

    /**
     * Initializes a Hex object with a side length of 4, representing what the defuser sees on thr bomb
     */
    public HexGrid(){
        super(new Hex(4));
        colorRing = new ReadOnlyRing<>(6);
        fillColorRing();
    }

    /**
     *
     *
     * @param grid The Hex of side length of 4 to wrap in a HexGrid
     * @param neededRotations
     * @throws IllegalArgumentException The side length of the given Hexagon isn't 4
     */
    public HexGrid(Hex grid, int neededRotations) throws IllegalArgumentException{
        if (grid.sideLength() != 4)
            throw new IllegalArgumentException("Grid doesn't have a side length of 4");
        hexagon = grid;
        colorRing = new ReadOnlyRing<>(6);
        fillColorRing();
        for (int i = 0; i < neededRotations; i++) rotateColorOrder();
    }

    public HexGrid(Hex grid){
        this(grid, 0);
    }

    public HexGrid(HexGrid toCopy){
        hexagon = new Hex(copyNodes(toCopy.hexport()));
        colorRing = toCopy.colorRing;
    }

    /**
     * Fills an ArrayList of HexNodes from one of HexShapes
     *
     * @param shapeList The ArrayList of shapes to fill the HexGrid
     */
    public void fillWithShapes(ArrayList<HexTraits.HexShape> shapeList){
        ArrayList<Hex.HexNode> nodeList = new ArrayList<>();
        for (HexTraits.HexShape shape : shapeList)
            nodeList.add(new Hex.HexNode(shape, null));
        hexagon.injectList(nodeList);
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

    /**
     *
     *
     * @return
     */
    public ArrayList<Color> getOrder() {
        return colorRing.toArrayList();
    }


    /**
     * Gets the HexNode from a specific set of coordinates
     *
     * @param pair
     * @return The result HexNode
     */
    public Hex.HexNode retrieveNode(Coordinates pair){
        int[] values = pair.getCoords();
        return exportTo2DQueue().get(values[0]).get(values[1]);
    }

    /**
     * Passes on the center column of a given hexagon
     *
     * @return The center column of a Hex object
     */
    public FixedArrayQueue<Hex.HexNode> getCenterColumn(){
        return hexagon.getCenterHexagonColumn();
    }

    private ArrayList<Hex.HexNode> copyNodes(Hex hex){
        ArrayList<Hex.HexNode> toNewHex = new ArrayList<>(),
                old = hex.exportToList();
        for (Hex.HexNode hexNode : old)
            toNewHex.add(new Hex.HexNode(hexNode));
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

    public ArrayList<String> hashStrings(){
        ArrayList<String> outputs = new ArrayList<>(2);
        StringBuilder shapeHash = new StringBuilder(), wallHash = new StringBuilder();
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> queues = hexagon.exportTo2DQueue();

        for (FixedArrayQueue<Hex.HexNode> queue : queues){
            for (Hex.HexNode node : queue){
                shapeHash.append(node.getShapeHash());
                wallHash.append(node.getWallHash());
            }
        }

        outputs.add(shapeHash.toString());
        outputs.add(wallHash.toString());
        return outputs;
    }
}
