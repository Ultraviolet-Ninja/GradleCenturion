package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.modules.dh.hexamaze.hexalgorithm.HexNodeProperties.*;
import bomb.tools.data.structures.FixedArrayQueue;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Hex is a full interpretation of a hexagonal data structure that contains data on a given Hexamaze
 * using individual HexNodes to represent what a tile contains.
 */
public class HexagonDataStructure {
    /**
     * This class is the backing node to a given hexagon data structure.
     */
    public static final class HexNode {
        public ArrayList<HexWall> walls;
        public HexShape fill;

        /**
         * Initializes a HexNode with the important info:
         * Any combination of the 6 walls and a shape in the center
         *
         * @param hexShape   The shape within the HexNode
         * @param constructs Which walls are present in this HexNode
         */
        public HexNode(HexShape hexShape, ArrayList<HexWall> constructs) {
            walls = constructs;
            fill = hexShape;
        }

        public HexNode(HexNode toCopy) {
            walls = deepCopyWalls(toCopy.walls);
            fill = deepCopyShape(toCopy.fill);
        }

        private ArrayList<HexWall> deepCopyWalls(ArrayList<HexWall> constructs) {
            ArrayList<HexWall> newWalls = new ArrayList<>();
            for (HexWall construct : constructs) {
                switch (construct) {
                    case Top:
                        newWalls.add(HexWall.Top);
                        break;
                    case TopLeft:
                        newWalls.add(HexWall.TopLeft);
                        break;
                    case TopRight:
                        newWalls.add(HexWall.TopRight);
                        break;
                    case Bottom:
                        newWalls.add(HexWall.Bottom);
                        break;
                    case BottomLeft:
                        newWalls.add(HexWall.BottomLeft);
                        break;
                    default:
                        newWalls.add(HexWall.BottomRight);
                }
            }
            return newWalls;
        }

        private HexShape deepCopyShape(HexShape shape) {
            if (shape == null) return null;
            switch (shape) {
                case Circle:
                    return HexShape.Circle;
                case Hexagon:
                    return HexShape.Hexagon;
                case LeftTriangle:
                    return HexShape.LeftTriangle;
                case RightTriangle:
                    return HexShape.RightTriangle;
                case UpTriangle:
                    return HexShape.UpTriangle;
                default:
                    return HexShape.DownTriangle;
            }
        }

        /**
         * Checks whether a certain wall is contained within the HexNode
         *
         * @param wallTag The number associated with a particular wall to check
         * @return True or false, whether there was the specified wall
         */
        public boolean isPathClear(int wallTag) {
            for (HexWall testWall : walls)
                if (testWall.ordinal() == wallTag)
                    return false;
            return true;
        }

        @Override
        public boolean equals(Object obj){
            if (!(obj instanceof HexNode)) return false;
            return ((HexNode)obj).fill == this.fill && hasMatchingWalls(((HexNode)obj).walls);
        }

        private boolean hasMatchingWalls(ArrayList<HexWall> toCompare){
            if (this.walls.size() != toCompare.size()) return false;
            for (HexWall wall : toCompare){
                if (!this.walls.contains(wall)) return false;
            }
            return true;
        }

        public int getShapeHash(){
            return HexShape.shapeOrdinality(fill);
        }

        public String getWallHash(){
            Collections.sort(walls);
            return HexWall.toHash(walls);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder().append(fill != null ? fill.toString() : "null");
            sb.append("-");
            for (HexWall wall : this.walls)
                sb.append(wall.toString()).append(" ");
            return sb.toString();
        }
    }

    private FixedArrayQueue<FixedArrayQueue<HexNode>> hexagon;
    private final byte sideLength;
    private final int span;

    /**
     * Initializes a hexagonal storage system based on the side length given
     *
     * @param sideLength - the length of a side of the hexagon
     * @throws IllegalArgumentException - Signals that the specified length is too short
     */
    public HexagonDataStructure(int sideLength) throws IllegalArgumentException {
        if (sideLength < 128) {
            hexagon = listHexagon((byte) sideLength);
            this.sideLength = (byte) sideLength;
            span = calculateHexagonalSpan();
        } else throw new IllegalArgumentException("Side length was too large");
    }

    /**
     * Initializes a hexagonal storage system based on a given backing data structure
     * and its side length
     *
     * @param imports    The List of Lists backing structure
     * @param sideLength The matching side length
     */
    public HexagonDataStructure(FixedArrayQueue<FixedArrayQueue<HexNode>> imports, int sideLength) {
        hexagon = imports;
        this.sideLength = (byte) sideLength;
        span = calculateHexagonalSpan();
    }

    /**
     * Initializes a hexagonal storage system based on a simple ArrayList of HexNodes,
     * taking the size of the list before sending it to the stream method
     *
     * @param imports The Arraylist of HexNodes
     * @throws IllegalArgumentException The side length didn't given an integer value
     */
    public HexagonDataStructure(ArrayList<HexNode> imports) throws IllegalArgumentException {
        sideLength = nodalSideLength(imports.size());
        if (sideLength == 0)
            throw new IllegalArgumentException("Area given did not return an integer");
        readInNodeList(imports);
        span = calculateHexagonalSpan();
    }

    /**
     * Creates a FinalList of empty FinalLists. This will represent the hexagon
     *
     * @param sideLength The given side length of a hexagon
     * @return The FinalList of FinalLists
     */
    private FixedArrayQueue<FixedArrayQueue<HexNode>> listHexagon(byte sideLength) {
        FixedArrayQueue<FixedArrayQueue<HexNode>> hex;
        if (sideLength > 2) {
            //Initializing the horizontal size of the hex to be 2n-1
            hex = new FixedArrayQueue<>(2 * sideLength - 1);

            //Adding lists from the starting length to 2n-1
            for (int i = sideLength; i < sideLength * 2; i++)
                hex.add(new FixedArrayQueue<>(i));

            //Adding lists from 2n-2 to starting length
            for (int i = sideLength * 2 - 2; i >= sideLength; i--)
                hex.add(new FixedArrayQueue<>(i));
            return hex;
        } else throw new IllegalArgumentException("Size is too small");
    }

    /**
     * Streams in an ArrayList of HexNodes, converts it to the hexagon representation and
     * saves it in the internal hexagon
     *
     * @param stream The ArrayList of HexNodes
     */
    public void readInNodeList(ArrayList<HexNode> stream) {
        hexagon = interpretList(stream);
    }

    /**
     * Takes in an ArrayList of HexNodes and converts to a hexagonal representation
     *
     * @param newStream The ArrayList of HexNodes
     * @return A successfully streamed FinalList of FinalLists
     * @throws IllegalArgumentException Too few or too many HexNodes were given
     */
    private FixedArrayQueue<FixedArrayQueue<HexNode>> interpretList(ArrayList<HexNode> newStream)
            throws IllegalArgumentException {
        if (nodalSideLength(newStream.size()) == 0)
            throw new IllegalArgumentException("Too few nodes were sent: " + newStream.size());
        FixedArrayQueue<FixedArrayQueue<HexNode>> temp = listHexagon(sideLength);
        for (HexNode hexNode : newStream)
            if (!add(temp, hexNode)) throw new IllegalArgumentException("We have extra nodes being added");
        return temp;
    }

    /**
     * Adds one HexNode to the hexagon
     *
     * @param toFill The FinalList of FinalLists needing to be filled
     * @param toAdd  The hexNode to add
     * @return False if full
     */
    private boolean add(FixedArrayQueue<FixedArrayQueue<HexNode>> toFill, HexNode toAdd) {
        for (int i = 0; i < toFill.cap(); i++) {
            if (!toFill.get(i).full()) {
                toFill.get(i).add(toAdd);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the side length of the hexagon
     *
     * @return The length of one side of the hexagon
     */
    public int sideLength() {
        return sideLength;
    }

    /**
     * Returns the span of the columns
     *
     * @return The length of the columns
     */
    public int getSpan() {
        return span;
    }

    /**
     * Creates an ArrayList of HexNodes from the FinalList of FinalLists
     *
     * @return The ArrayList containing HexNodes
     */
    public ArrayList<HexNode> exportToList() {
        ArrayList<HexNode> output = new ArrayList<>();
        for (int i = 0; i < hexagon.cap(); i++)
            for (int j = 0; j < hexagon.get(i).cap(); j++)
                output.add(hexagon.get(i).get(j));
        return output;
    }

    /**
     * Rotates the entire hexagon 60° clockwise
     *
     * @throws IllegalArgumentException If extra nodes were being added to the new hexagon
     */
    public void rotate() throws IllegalArgumentException {
        ArrayList<HexNode> linearOrder = new ArrayList<>();
        //TODO - Needs to be broken down
        //For loop for the entire algorithm
        for (int a = 0; a < hexagon.cap(); a++) {
            //For loop for 1st half of the hexagon and its center
            for (int b = 0; b < sideLength; b++) {
                //If the row capacity - a is less than zero,
                // that means we can't draw from that FinalList row anymore
                if (hexagon.get(b).cap() - a > 0) {
                    int lastIndex = hexagon.get(b).cap() - 1;
                    linearOrder.add(hexagon.get(b).get(lastIndex - a));
                }
            }

            int rowCounter = sideLength;
            //If loop prevents the 2nd half from running on the first total algorithm run
            if (a != 0) {
                //For loop for 2nd half copies value from a and decreases for each row past Center
                for (int c = a; c > 0; c--) {
                    //If statement prevents excess instances from being copied over to the new hexagon
                    if (rowCounter < hexagon.cap()) {
                        int lastIndex = hexagon.get(rowCounter).cap() - c;
                        linearOrder.add(hexagon.get(rowCounter).get(lastIndex));
                        rowCounter++;
                    } else c = 0;
                }
            }
        }

        for (HexNode hexNode : linearOrder) {
            hexNode.walls = rotateWallPositions(hexNode.walls);
            hexNode.fill = rotateShape(hexNode.fill);
        }

        hexagon = interpretList(linearOrder);
    }

    /**
     * Rotates the walls of a given HexNode 60° (1 position)
     *
     * @param walls The array needing to be rotated
     * @return The newly rotated wall array
     */
    private ArrayList<HexWall> rotateWallPositions(ArrayList<HexWall> walls) {
        if (walls.get(0) == null) return null;
        ArrayList<HexWall> temp = new ArrayList<>();

        for (HexWall wall : walls) {
            switch (wall) {
                case TopLeft:
                    temp.add(HexWall.Top);
                    break;
                case Top:
                    temp.add(HexWall.TopRight);
                    break;
                case TopRight:
                    temp.add(HexWall.BottomRight);
                    break;
                case BottomRight:
                    temp.add(HexWall.Bottom);
                    break;
                case Bottom:
                    temp.add(HexWall.BottomLeft);
                    break;
                default:
                    temp.add(HexWall.TopLeft);
            }
        }
        return temp;
    }

    /**
     * Simulates a triangle being rotated 60° clockwise.
     * This has no barring on empty nodes or ones containing circles or hexagons.
     *
     * @param currentShape The shape inside the current HexNode
     * @return The HexShape needed after a rotation occurs
     */
    private HexShape rotateShape(HexShape currentShape) {
        if (currentShape == null) return null;
        if (currentShape == HexShape.Circle) return HexShape.Circle;
        if (currentShape == HexShape.Hexagon) return HexShape.Hexagon;

        switch (currentShape) {
            case UpTriangle:
                return HexShape.DownTriangle;
            case DownTriangle:
                return HexShape.UpTriangle;
            case LeftTriangle:
                return HexShape.RightTriangle;
            default:
                return HexShape.LeftTriangle;
        }
    }

    /**
     * Exports the FinalList of FinalLists of hexagon data
     *
     * @return The FinalList of FinalLists of HexNodes
     */
    public FixedArrayQueue<FixedArrayQueue<HexNode>> exportTo2DQueue() {
        return hexagon;
    }

    /**
     * Takes the letter from the text file and outputs the appropriate shape
     *
     * @param letter The character(s) noting the shape
     * @return The shape for the HexNode
     */
    public static HexShape decodeShape(String letter) {
        switch (letter.toLowerCase()) {
            case "c":
                return HexShape.Circle;
            case "h":
                return HexShape.Hexagon;
            case "lt":
                return HexShape.LeftTriangle;
            case "rt":
                return HexShape.RightTriangle;
            case "ut":
                return HexShape.UpTriangle;
            case "dt":
                return HexShape.DownTriangle;
            default:
                return null;
        }
    }

    /**
     * Takes in the numbers from the text file, adds the appropriate walls an array
     * and outputs the array when done
     *
     * @param numbers The string of numbers representing the walls
     * @return The array containing all existing walls in a HexNode
     */
    public static ArrayList<HexNodeProperties.HexWall> decodeWalls(String numbers) {
        ArrayList<HexNodeProperties.HexWall> constructs = new ArrayList<>(6);

        for (HexNodeProperties.HexWall index : HexNodeProperties.HexWall.values())
            if (numbers.contains(String.valueOf(index.ordinal())))
                constructs.add(index);
        return constructs;
    }

    /**
     * Finds the area of a hexagon based on a given side length
     * The equation: 3x^2 - 3x + 1
     *
     * @param sideLength Side length given
     * @return The area of the hexagon
     */
    public static int nodalArea(int sideLength) {
        return (int) (3 * Math.pow(sideLength, 2)) - (3 * sideLength) + 1;
    }

    /**
     * Finds the side length of a hexagon given its area.
     * Returns zero if the equation outputs a non-integer number
     * The equation: (3 + √(12*area - 3)) / 6
     *
     * @param area The area of a hexagon
     * @return The length of one side of a hexagon or 0
     */
    private byte nodalSideLength(int area) {
        double math = (3 + Math.sqrt(12 * area - 3)) / 6;
        if (notAnInteger(math))
            return 0;
        return (byte) math;
    }

    /**
     * Detects if the number given was an integer
     *
     * @param number The number to evaluate
     * @return True if an integer
     */
    private boolean notAnInteger(double number) {
        return number % 1 != 0.0;
    }

    private int calculateHexagonalSpan() {
        return (2 * sideLength) - 1;
    }

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < hexagon.cap(); x++){
            for (int y = 0; y < hexagon.get(x).cap(); y++){
                sb.append(hexagon.get(x).get(y).getShapeHash());
            }
        }
        return sb.hashCode();
    }
}
