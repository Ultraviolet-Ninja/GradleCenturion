package bomb.components.hex;

import bomb.tools.hexalgorithm.Hex;
import bomb.enumerations.HexTraits;
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
            peg.setFill(new Color(0.776,0.776,0.776,1.0));
        } catch (IOException ioex){
            ioex.printStackTrace();
        }
    }

    public void fillPeg(Color toFill){
        peg.setFill(toFill);
    }

    public void setup(Hex.HexNode currentNode) {
        setupLines(currentNode.walls);
        setupShape(currentNode.fill);
    }

    private void setupShape(HexTraits.HexShape shape){
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
            disappear(circle);
            disappear(hexagon);
            disappear(leftTriangle);
            disappear(rightTriangle);
            disappear(upTriangle);
            disappear(downTriangle);
        }
    }

    private void exclusiveShapeSet(Shape shape){
        xorAppear(shape);
        xorDisappear(shape);
    }

    private void xorAppear(Shape shape){
        if (shape == circle)
            appear(circle);
        else if (shape == hexagon)
            appear(hexagon);
        else if (shape == leftTriangle)
            appear(leftTriangle);
        else if (shape == rightTriangle)
            appear(rightTriangle);
        else if (shape == upTriangle)
            appear(upTriangle);
        else if (shape == downTriangle)
            appear(downTriangle);
    }

    private void xorDisappear(Shape shape){
        Shape[] array = createShapeArray();
        for (Shape current : array){
            if (current != shape)
                disappear(current);
        }
    }

    private void setupLines(ArrayList<HexTraits.HexWall> walls){
        String remove = "123456";
        if (walls != null) {
            for (HexTraits.HexWall wall : walls) {
                switch (wall.getIdx()) {
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
        appear(line);
        return string.replace(remove, "");
    }

    private void removeLines(String string){
        if (string.contains("1")) disappear(northWest);
        if (string.contains("2")) disappear(north);
        if (string.contains("3")) disappear(northEast);
        if (string.contains("4")) disappear(southWest);
        if (string.contains("5")) disappear(south);
        if (string.contains("6")) disappear(southEast);
    }

    private void appear(Shape shape){
        shape.setOpacity(1);
    }

    private void disappear(Shape shape){
        shape.setOpacity(0);
    }

    public void makeWallsTransparent(){
        disappear(northWest);
        disappear(north);
        disappear(northEast);
        disappear(southEast);
        disappear(south);
        disappear(southWest);
    }

    private Shape[] createShapeArray(){
        return new Shape[]{circle, hexagon, leftTriangle, rightTriangle, upTriangle, downTriangle};
    }

    public void setHexagonalFill(Color color){
        hexagonalFill.setFill(color);
    }

    public void resetHexagonalFill(){
        hexagonalFill.setFill(new Color(0, 0, 0, 0));
    }
}
