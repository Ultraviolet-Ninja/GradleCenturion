package bomb.components.hex;

import bomb.abstractions.Resettable;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexShape;
import bomb.tools.event.HoverHandler;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.function.Consumer;

import static bomb.modules.dh.hexamaze.Hexamaze.PEG_COLOR;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexShape.CIRCLE;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexShape.DOWN_TRIANGLE;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexShape.HEXAGON;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexShape.LEFT_TRIANGLE;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexShape.RIGHT_TRIANGLE;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexShape.UP_TRIANGLE;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.CYAN;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.PINK;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.YELLOW;

public class MazeComponent extends Pane implements Resettable {
    private String shapeSelection, colorSelection;
    private HexTile playerLocation;

    @FXML
    private HexTile oneOne, oneTwo, oneThree, oneFour,
            twoOne, twoTwo, twoThree, twoFour, twoFive,
            threeOne, threeTwo, threeThree, threeFour, threeFive, threeSix,
            fourOne, fourTwo, fourThree, fourFour, fourFive, fourSix, fourSeven,
            fiveOne, fiveTwo, fiveThree, fiveFour, fiveFive, fiveSix,
            sixOne, sixTwo, sixThree, sixFour, sixFive,
            sevenOne, sevenTwo, sevenThree, sevenFour;

    public MazeComponent() {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("maze_component.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        FacadeFX.loadComponent(loader);

        shapeSelection = "";
        colorSelection = "";
    }

    public void initialize() {
        HoverHandler<MouseEvent> hoverHandler = new HoverHandler<>(createClickAction());
        createTileList().forEach(tile -> tile.setOnMouseClicked(hoverHandler));
    }

    private Consumer<MouseEvent> createClickAction() {
        return event -> {
            HexTile tile = (HexTile) event.getSource();
            if (shapeSelection.equals("Peg")) {
                tile.setPegFill(pickColor(colorSelection));
                if (playerLocation != null)
                    playerLocation.clearPeg();
                playerLocation = tile;
                return;
            }
            tile.setShape(pickShape(shapeSelection));
        };
    }

    private Color pickColor(String strColor) {
        return switch (strColor) {
            case "Red" -> RED;
            case "Yellow" -> YELLOW;
            case "Green" -> GREEN;
            case "Cyan" -> CYAN;
            case "Blue" -> BLUE;
            case "Pink" -> PINK;
            default -> PEG_COLOR;
        };
    }

    private HexShape pickShape(String shapeName) {
        return switch (shapeName) {
            case "Down Triangle" -> DOWN_TRIANGLE;
            case "Circle" -> CIRCLE;
            case "Hexagon" -> HEXAGON;
            case "Left Triangle" -> LEFT_TRIANGLE;
            case "Right Triangle" -> RIGHT_TRIANGLE;
            case "Up Triangle" -> UP_TRIANGLE;
            default -> null;
        };
    }

    public void setShapeSelection(String shapeSelection) {
        this.shapeSelection = shapeSelection;
    }

    public void setColorSelection(String colorSelection) {
        shapeSelection = "Peg";
        this.colorSelection = colorSelection;
    }

    public List<HexTile> createTileList() {
        return List.of(
                oneOne, oneTwo, oneThree, oneFour,
                twoOne, twoTwo, twoThree, twoFour, twoFive,
                threeOne, threeTwo, threeThree, threeFour, threeFive, threeSix,
                fourOne, fourTwo, fourThree, fourFour, fourFive, fourSix, fourSeven,
                fiveOne, fiveTwo, fiveThree, fiveFour, fiveFive, fiveSix,
                sixOne, sixTwo, sixThree, sixFour, sixFive,
                sevenOne, sevenTwo, sevenThree, sevenFour
        );
    }

    @Override
    public void reset() {
        List<HexTile> tiles = createTileList();
        tiles.forEach(Resettable::reset);
    }
}
