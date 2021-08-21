package bomb.modules.dh.hexamaze;

import bomb.Widget;
import bomb.components.hex.HexMazePanel;
import bomb.modules.dh.hexamaze.hexalgorithm.HexNodeProperties.HexShape;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure.HexNode;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.maze_finding.HexHashLibrary;
import bomb.modules.dh.hexamaze.hexalgorithm.pathfinding.HexPanelFiller;
import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import bomb.modules.dh.hexamaze.hexalgorithm.pathfinding.MazeRunner;
import bomb.modules.s.souvenir.Souvenir;
import bomb.tools.Coordinates;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure.nodalArea;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.CYAN;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.YELLOW;

public class Hexamaze extends Widget{
    private final static List<HexShape> HEX_SHAPE_TRACKER;

    private static List<HexMazePanel> panelList;
    private static Color pegFillSelector, currentPegColor;
    private static HexShape currentSelector;
    private static int currentPegLocation;
    private static HexGrid grid;

    static {
        currentPegLocation = -1;
        HEX_SHAPE_TRACKER = new ArrayList<>();
        setToNull();
        try{
            HexHashLibrary.initialize(new Maze(), (2 * HexGrid.STANDARD_SIDE_LENGTH) - 1);
        } catch (IOException ioex){
            ioex.printStackTrace();
        }
    }

    /**
     * Sets all elements of the representation to null
     */
    private static void setToNull(){
        for (int i = 0; i < nodalArea(HexGrid.STANDARD_SIDE_LENGTH); i++)
            HEX_SHAPE_TRACKER.add(null);
    }

    /**
     * Sets up the variables for the Hexamaze. This includes setting up the full maze, the defuser's grid
     * and an ArrayList representation of the grid
     */
    public static void setupVariables(List<HexMazePanel> panelArray){
        grid = new HexGrid();
        panelList = panelArray;
    }

    /**
     * Sets the selector to the shape corresponding ToggleButton
     *
     * @param button The name of the currently toggled ToggleButton
     */
    public static void setShapeSelector(String button){
        switch (button){
            case "Erase": currentSelector = null; break;
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
        if (isSouvenirActive){
            Souvenir.addRelic("Hexamaze Pawn Color", getColorName(fillColor));
        }
    }

    private static String getColorName(Color color){
        if (color == RED) return "Red";
        if (color == YELLOW) return "Yellow";
        if (color == GREEN) return "Green";
        if (color == CYAN) return "Cyan";
        if (color == BLUE) return "Blue";
        return "Pink";
    }

    /**
     * Finds the HexMazePanel that's being hovered over to be filled with the currentShapeSelector
     */
    public static void setShapeFill(){
        for (int i = 0; i < panelList.size(); i++){
            if (panelList.get(i).isHover()){
                toFill(panelList.get(i), i);
                i = panelList.size();
            }
        }
    }

    /**
     * Sets the given HexMazePanel with the current Shape in the {@link Hexamaze currentSelector}
     *
     * @param panel The current HexMazePanel
     * @param index Where it'll show up in array representation
     */
    private static void toFill(HexMazePanel panel, int index) {
        panel.setup(new HexNode(currentSelector, null));
        HEX_SHAPE_TRACKER.set(index, currentSelector);
    }

    public static void setPegFillSelector(){
        for (int i = 0; i < panelList.size(); i++){
            if (panelList.get(i).isHover()){
                if (currentPegLocation != -1)
                    deselectPreviousFill(panelList.get(currentPegLocation));
                toColorFill(panelList.get(i));
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
     * @throws IllegalArgumentException No sub-maze was found in the maze
     */
    public static void compareToFullMaze() throws IllegalArgumentException{
        grid.fillWithShapes(HEX_SHAPE_TRACKER);
        grid.resetColorRing();
        MazeRunner.getPegInformation(currentPegColor, currentPegLocation, grid.sideLength());
        HexGrid result = HexHashLibrary.find(grid);
        if(result != null) decipherResults(result);
        else throw new IllegalArgumentException("No resulting maze was found");
    }

    /**
     * Outputs the discovered location to the HexMazePanels to show the wall locations
     *
     * @param hexGrid The discovered location of the HexGrid
     */
    private static void decipherResults(HexGrid hexGrid){
        clearPreviousLines();
        List<Coordinates> exitOrder = MazeRunner.runMaze(hexGrid);
        List<HexNode> stream = hexGrid.hexport().exportToList();
        for (int i = 0; i < stream.size(); i++) panelList.get(i).setup(stream.get(i));
        if (exitOrder != null) HexPanelFiller.fillPanels(exitOrder, panelList, currentPegColor, grid.sideLength());
    }

    private static void clearPreviousLines(){
        for (HexMazePanel hexMazePanel : panelList)
            hexMazePanel.makeWallsTransparent();
    }

    public static void resetHexPanelList(){
        for (HexMazePanel hexMazePanel : panelList) hexMazePanel.reset();
    }
}
