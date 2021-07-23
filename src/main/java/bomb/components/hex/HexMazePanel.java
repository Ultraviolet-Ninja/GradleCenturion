package bomb.components.hex;

import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure;
import bomb.modules.dh.hexamaze.hexalgorithm.HexNodeProperties;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.util.ArrayList;

public class HexMazePanel extends Pane {
    public static final Color DEFAULT_PEG_COLOR = new Color(0.65,0.65,0.65, 1.0);

    private static final Color DEFAULT_BACKGROUND_COLOR = new Color(0.0195,0.0195,0.0195,1.0);

    @FXML
    private Line northWest, north, northEast, southEast, south, southWest;

    @FXML
    private Circle circle, peg;

    @FXML
    private Polygon hexagon, hexagonalFill, upTriangle, downTriangle, leftTriangle, rightTriangle;

    public HexMazePanel(){
        super();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("panel.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
            makeWallsTransparent();
            peg.setFill(DEFAULT_PEG_COLOR);
        } catch (IOException ioex){
            ioex.printStackTrace();
        }
    }

    public void fillPeg(Color toFill){
        peg.setFill(toFill);
    }

    public void setup(HexagonDataStructure.HexNode currentNode) {
        setupLines(currentNode.walls);
        setupShape(currentNode.fill);
    }

    private void setupShape(HexNodeProperties.HexShape shape){
        if(shape != null){
            switch (shape) {
                case Circle: exclusiveShapeSet(circle);
                    break;
                case Hexagon: exclusiveShapeSet(hexagon);
                    break;
                case LeftTriangle: exclusiveShapeSet(leftTriangle);
                    break;
                case RightTriangle: exclusiveShapeSet(rightTriangle);
                    break;
                case UpTriangle:exclusiveShapeSet(upTriangle);
                    break;
                default: exclusiveShapeSet(downTriangle);
                    break;
            }
        } else {
            setInvisible(circle);
            setInvisible(hexagon);
            setInvisible(leftTriangle);
            setInvisible(rightTriangle);
            setInvisible(upTriangle);
            setInvisible(downTriangle);
        }
    }

    private void exclusiveShapeSet(Shape shape){
        setExclusiveVisibility(shape);
        setExclusiveInvisibility(shape);
    }

    private void setExclusiveVisibility(Shape shape){
        if (shape == circle)
            setVisible(circle);
        else if (shape == hexagon)
            setVisible(hexagon);
        else if (shape == leftTriangle)
            setVisible(leftTriangle);
        else if (shape == rightTriangle)
            setVisible(rightTriangle);
        else if (shape == upTriangle)
            setVisible(upTriangle);
        else if (shape == downTriangle)
            setVisible(downTriangle);
    }

    private void setExclusiveInvisibility(Shape shape){
        Shape[] array = createShapeArray();
        for (Shape current : array){
            if (current != shape)
                setInvisible(current);
        }
    }

    private void setupLines(ArrayList<HexNodeProperties.HexWall> walls){
        String remove = "123456";
        if (walls != null) {
            for (HexNodeProperties.HexWall wall : walls) {
                switch (wall.ordinal()) {
                    case 0: remove = setLine(northWest, remove, "1"); break;
                    case 1: remove = setLine(north, remove, "2"); break;
                    case 2: remove = setLine(northEast, remove, "3"); break;
                    case 3: remove = setLine(southWest, remove, "4"); break;
                    case 4: remove = setLine(south, remove, "5"); break;
                    default: remove = setLine(southEast, remove, "6"); break;
                }
            }
            removeLines(remove);
        }
    }

    private String setLine(Line line, String string, String remove){
        setVisible(line);
        return string.replace(remove, "");
    }

    private void removeLines(String string){
        if (string.contains("1")) setInvisible(northWest);
        if (string.contains("2")) setInvisible(north);
        if (string.contains("3")) setInvisible(northEast);
        if (string.contains("4")) setInvisible(southWest);
        if (string.contains("5")) setInvisible(south);
        if (string.contains("6")) setInvisible(southEast);
    }

    private void setVisible(Shape shape){
        shape.setOpacity(1);
    }

    private void setInvisible(Shape shape){
        shape.setOpacity(0);
    }

    public void makeWallsTransparent(){
        setInvisible(northWest);
        setInvisible(north);
        setInvisible(northEast);
        setInvisible(southEast);
        setInvisible(south);
        setInvisible(southWest);
    }

    private Shape[] createShapeArray(){
        return new Shape[]{circle, hexagon, leftTriangle, rightTriangle, upTriangle, downTriangle};
    }

    public void setHexagonalFill(Color color){
        double newRed = (color.getRed() + DEFAULT_BACKGROUND_COLOR.getRed()) / 2;
        double newGreen = (color.getGreen() + DEFAULT_BACKGROUND_COLOR.getGreen()) / 2;
        double newBlue = (color.getBlue() + DEFAULT_BACKGROUND_COLOR.getBlue()) / 2;
        Color colorAverage = new Color(newRed, newGreen, newBlue, 1.0);
        hexagonalFill.setFill(colorAverage);
    }

    public void resetHexagonalFill(){
        hexagonalFill.setFill(DEFAULT_BACKGROUND_COLOR);
    }

    public void reset(){
        resetHexagonalFill();
        makeWallsTransparent();
        setExclusiveInvisibility(null);
    }
}
