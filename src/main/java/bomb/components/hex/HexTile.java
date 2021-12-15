package bomb.components.hex;

import bomb.abstractions.Resettable;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexShape;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode.HexWall;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.util.EnumSet;

import static bomb.modules.dh.hexamaze_redesign.Hexamaze.COLOR_MAP;
import static bomb.modules.dh.hexamaze_redesign.Hexamaze.PEG_COLOR;

public class HexTile extends Pane implements Resettable {
    public static final Color DEFAULT_BACKGROUND_COLOR = new Color(0.0195, 0.0195, 0.0195, 1.0);

    private HexNode internalNode;

    @FXML
    private Line northWest, north, northEast, southEast, south, southWest;

    @FXML private Circle circle, peg;

    @FXML private Polygon hexagon, hexagonalFill, upTriangle, downTriangle, leftTriangle, rightTriangle;

    public HexTile() {
        super();
        internalNode = new HexNode(null, EnumSet.noneOf(HexNode.HexWall.class));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("panel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        FacadeFX.loadComponent(loader);
    }

    public HexNode getInternalNode() {
        return internalNode;
    }

    public void setInternalNode(HexNode node) {
        internalNode = node;
        scanShape();
        scanWalls();
    }

    private void scanShape() {
        FacadeFX.setNodesInvisible(circle, hexagon, upTriangle, downTriangle, leftTriangle, rightTriangle);
        HexNode.HexShape shape = internalNode.getHexShape();
        if (shape == null)
            return;

        switch (shape) {
            case CIRCLE -> FacadeFX.setVisible(circle);
            case HEXAGON -> FacadeFX.setVisible(hexagon);
            case UP_TRIANGLE -> FacadeFX.setVisible(upTriangle);
            case DOWN_TRIANGLE -> FacadeFX.setVisible(downTriangle);
            case LEFT_TRIANGLE -> FacadeFX.setVisible(leftTriangle);
            default -> FacadeFX.setVisible(rightTriangle);
        }
    }

    private void scanWalls() {
        FacadeFX.setNodesInvisible(northWest, north, northEast, southEast, south, southWest);
        EnumSet<HexWall> walls = internalNode.getWalls();
        if (walls == null || walls.isEmpty())
            return;

        for (HexWall wall : walls) {
            switch (wall) {
                case TOP_LEFT -> FacadeFX.setVisible(northWest);
                case TOP -> FacadeFX.setVisible(north);
                case TOP_RIGHT -> FacadeFX.setVisible(northEast);
                case BOTTOM_LEFT -> FacadeFX.setVisible(southWest);
                case BOTTOM -> FacadeFX.setVisible(south);
                default -> FacadeFX.setVisible(southEast);
            }
        }
    }

    public void setPegFill(Color color) {
        peg.setFill(color);
        setInternalNodeColor(color);
    }

    public void clearPeg() {
        peg.setFill(PEG_COLOR);
        internalNode.clearColor();
    }

    public void setShape(HexShape shape) {
        internalNode.setHexShape(shape);
        scanShape();
    }

    public void setBackgroundFill(Color color) {
        double newRed = (color.getRed() + DEFAULT_BACKGROUND_COLOR.getRed()) / 2;
        double newGreen = (color.getGreen() + DEFAULT_BACKGROUND_COLOR.getGreen()) / 2;
        double newBlue = (color.getBlue() + DEFAULT_BACKGROUND_COLOR.getBlue()) / 2;
        Color colorAverage = new Color(newRed, newGreen, newBlue, 1.0);
        hexagonalFill.setFill(colorAverage);
    }

    @Override
    public void reset() {
        internalNode = new HexNode(null, EnumSet.noneOf(HexWall.class));
        scanShape();
        scanWalls();
        peg.setFill(PEG_COLOR);
        hexagonalFill.setFill(DEFAULT_BACKGROUND_COLOR);
    }

    private void setInternalNodeColor(Color color) {
        internalNode.setColor(COLOR_MAP.get(color));
    }
}
