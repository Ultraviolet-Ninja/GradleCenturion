package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.modules.dh.hexamaze.HexTraits;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.FixedArrayQueue;
import bomb.tools.data.structures.FixedRing;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * This class creates a representation of what hte defuser sees on the Bomb.
 * It encapsulates a Hex class and contains data that's appropriate for that puzzle that
 * wouldn't be needed for all Hex objects
 */
public class HexGrid {
    private final Hex defuserVision;
    private final FixedRing<Color> colorRing;

    /**
     * Initializes a Hex object with a side length of 4, representing what the defuser sees on thr bomb
     */
    public HexGrid(){
        defuserVision = new Hex(4);
        colorRing = new FixedRing<>(6);
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
        defuserVision = grid;
        colorRing = new FixedRing<>(6);
        fillColorRing();
        for (int i = 0; i < neededRotations; i++) rotateColorOrder();
    }

    public HexGrid(HexGrid toCopy){
        defuserVision = new Hex(copyNodes(toCopy.hexport()));
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
        defuserVision.injectList(nodeList);
    }

    /**
     * Rotates the internal Hex object as well as re-orders the color array to show
     * which one is in the top left edge
     */
    public void rotateColorOrder(){
        colorRing.rotateHeadCounter();
    }

    public FixedRing<Color> getRing(){
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
     * Givers the side length of the HexGrid
     *
     * @return The side length of the internal Hex object
     */
    public int sideLength(){
        return defuserVision.sideLength();
    }

    /**
     * Exports the Hex object
     *
     * @return The defuser vision
     */
    public Hex hexport(){
        return defuserVision;
    }

    /**
     * Exports the list of lists representing the hexagon
     *
     * @return The Hex.export() method
     */
    public FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> exportTo2DQueue(){
        return defuserVision.exportTo2DQueue();
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
        return defuserVision.getCenterHexagonColumn();
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
}
