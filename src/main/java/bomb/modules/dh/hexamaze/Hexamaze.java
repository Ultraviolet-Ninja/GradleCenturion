package bomb.modules.dh.hexamaze;

import bomb.Widget;
import bomb.components.hex.HexMazePanel;
import bomb.tools.Coordinates;
import bomb.modules.dh.hexamaze.hexalgorithm.*;
import bomb.modules.dh.hexamaze.HexTraits.HexShape;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static bomb.modules.dh.hexamaze.hexalgorithm.Hex.nodalArea;

public class Hexamaze extends Widget{
    private final static ArrayList<HexShape> hexShapeTracker = new ArrayList<>();
    private static Color pegFillSelector = null, currentPegColor = null;
    private static HexShape currentSelector = null;
    private static int currentPegLocation = -1;
    private static Maze maze;
    private static HexGrid grid;

    /**
     * Sets up the variables for the Hexamaze. This includes setting up the full maze, the defuser's grid
     * and an ArrayList representation of the grid
     */
    public static void setupVariables(){
        setToNull();
        grid = new HexGrid();
        try{
            maze = new Maze();
        } catch (IOException ioex){
            ioex.printStackTrace();
        }
    }

    /**
     * Sets all elements of the representation to null
     */
    private static void setToNull(){
        for (int i = 0; i < nodalArea(4); i++) hexShapeTracker.add(null);
    }

    /**
     * Sets the selector to the shape corresponding ToggleButton
     *
     * @param button The name of the currently toggled ToggleButton
     */
    public static void setShapeSelector(String button){
        switch (button){
            case "Blank": currentSelector = null; break;
            case "Circle": currentSelector = HexShape.Circle; break;
            case "Hexagon": currentSelector = HexShape.Hexagon; break;
            case "Left Triangle": currentSelector = HexShape.LeftTriangle; break;
            case "Right Triangle": currentSelector = HexShape.RightTriangle; break;
            case "Up Triangle": currentSelector = HexShape.UpTriangle; break;
            default: currentSelector = HexShape.DownTriangle;
        }
    }

    public static void setPegFillSelector(Color fillColor){
        pegFillSelector = fillColor;
    }

    /**
     * Finds the HexMazePanel that's being hovered over to be filled with the currentShapeSelector
     *
     * @param panelArray The array of HexMazePanels
     */
    public static void setShapeFill(ArrayList<HexMazePanel> panelArray){
        for (int i = 0; i < panelArray.size(); i++){
            if (panelArray.get(i).isHover()){
                toFill(panelArray.get(i), i);
                i = panelArray.size();
            }
        }
    }

    /**
     * Sets the given HexMazePanel
     *
     * @param panel The current HexMazePanel
     * @param index Where it'll show up in array representation
     * @throws IllegalArgumentException
     */
    private static void toFill(HexMazePanel panel, int index) throws IllegalArgumentException{
        panel.setup(new Hex.HexNode(currentSelector, null));
        hexShapeTracker.set(index, currentSelector);
    }

    public static void setPegFillSelector(ArrayList<HexMazePanel> panelArray){
        for (int i = 0; i < panelArray.size(); i++){
            if (panelArray.get(i).isHover()){
                if (currentPegLocation != -1)
                    deselectPreviousFill(panelArray.get(currentPegLocation));
                toColorFill(panelArray.get(i));
                currentPegLocation = i;
            }
        }
    }

    private static void toColorFill(HexMazePanel panel){
        currentPegColor = pegFillSelector;
        panel.fillPeg(currentPegColor);
    }

    private static void deselectPreviousFill(HexMazePanel panel){
        panel.fillPeg(new Color(0.776, 0.766, 0.776, 1.0));
    }

    /**
     * Fills the HexGrid with the array and sends it to the HexComparator to find the section of the maze
     *
     * @param panelArray The array of HexMazePanels
     * @throws IllegalArgumentException
     */
    public static void compareToFullMaze(ArrayList<HexMazePanel> panelArray) throws IllegalArgumentException{
        grid.fillWithShapes(hexShapeTracker);
        MazeRunner.getPegInformation(currentPegColor, currentPegLocation);
        HexGrid result = HexComparator.evaluate(maze, grid);
        if(result != null) decipherResults(result, panelArray);
        else throw new IllegalArgumentException("No resulting maze was found");
    }

    /**
     * Outputs the discovered location to the HexMazePanels to show the wall locations
     *
     * @param hexGrid The discovered location of the HexGrid
     * @param panelArray The array of HexMazePanels
     */
    private static void decipherResults(HexGrid hexGrid, ArrayList<HexMazePanel> panelArray){
        clearPreviousLines(panelArray);
        LinkedList<Coordinates> exitOrder = MazeRunner.runMaze(hexGrid);
        ArrayList<Hex.HexNode> stream = hexGrid.hexport().exportToList();
        for (int i = 0; i < stream.size(); i++) panelArray.get(i).setup(stream.get(i));
        if (exitOrder != null) HexPanelFiller.fillPanels(exitOrder, panelArray, currentPegColor, grid.sideLength());
//        else System.err.println("We found nothing");
    }

    private static void clearPreviousLines(ArrayList<HexMazePanel> panelArray){
        for (HexMazePanel hexMazePanel : panelArray)
            hexMazePanel.makeWallsTransparent();
    }
}
