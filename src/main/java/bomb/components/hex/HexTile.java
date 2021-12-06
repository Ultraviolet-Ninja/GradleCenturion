package bomb.components.hex;

import bomb.abstractions.Resettable;
import bomb.modules.dh.hexamaze.hexalgorithm.HexNodeProperties.HexShape;
import bomb.modules.dh.hexamaze.hexalgorithm.HexNodeProperties.HexWall;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure.HexNode;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.util.EnumSet;

public class HexTile extends Pane implements Resettable {
    public static final Color DEFAULT_PEG_COLOR = new Color(0.65, 0.65, 0.65, 1.0);

    private static final Color DEFAULT_BACKGROUND_COLOR = new Color(0.0195, 0.0195, 0.0195, 1.0);

    private HexNode internalNode;

    @FXML
    private Line northWest, north, northEast, southEast, south, southWest;

    @FXML private Circle circle, peg;

    @FXML private Polygon hexagon, hexagonalFill, upTriangle, downTriangle, leftTriangle, rightTriangle;

    public HexTile() {
        super();
        internalNode = new HexNode(null, EnumSet.noneOf(HexWall.class));
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
        HexShape shape = internalNode.getFill();
        if (shape == null)
            return;

        switch (shape) {
            case Circle -> FacadeFX.setVisible(circle);
            case Hexagon -> FacadeFX.setVisible(hexagon);
            case UpTriangle -> FacadeFX.setVisible(upTriangle);
            case DownTriangle -> FacadeFX.setVisible(downTriangle);
            case LeftTriangle -> FacadeFX.setVisible(leftTriangle);
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
                case TopLeft -> FacadeFX.setVisible(northWest);
                case Top -> FacadeFX.setVisible(north);
                case TopRight -> FacadeFX.setVisible(northEast);
                case BottomLeft -> FacadeFX.setVisible(southWest);
                case Bottom -> FacadeFX.setVisible(south);
                default -> FacadeFX.setVisible(southEast);
            }
        }
    }

    public void setPegFill(Color color) {
        peg.setFill(color);
    }

    public void setShape(HexShape shape) {
        internalNode = new HexNode(shape, EnumSet.noneOf(HexWall.class));
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
        peg.setFill(DEFAULT_PEG_COLOR);
        hexagonalFill.setFill(DEFAULT_BACKGROUND_COLOR);
    }
}
