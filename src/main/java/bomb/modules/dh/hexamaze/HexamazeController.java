package bomb.modules.dh.hexamaze;

import bomb.abstractions.Resettable;
import bomb.components.hex.HexTile;
import bomb.components.hex.MazeComponent;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexagonalPlane;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.queue.BufferedQueue;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import org.javatuples.Quartet;

import java.util.List;
import java.util.Map;

import static bomb.components.hex.HexTile.DEFAULT_BACKGROUND_COLOR;
import static bomb.modules.dh.hexamaze.Hexamaze.COLOR_MAP;
import static bomb.tools.pattern.facade.FacadeFX.GET_TOGGLE_NAME;
import static javafx.scene.paint.Color.RED;

public class HexamazeController implements Resettable {
    @FXML
    private Label exitDirectionLabel;

    @FXML
    private MazeComponent mazeComponent;

    @FXML
    private RadioButton redButton, yellowButton, greenButton, cyanButton, blueButton, pinkButton;

    @FXML
    private ToggleGroup hexGroup, hexColorGroup;

    @FXML
    private void setShape() {
        String shape = GET_TOGGLE_NAME.apply(hexGroup.getSelectedToggle());
        mazeComponent.setShapeSelection(shape);
        boolean disable = !shape.equals("Peg");
        redButton.setDisable(disable);
        yellowButton.setDisable(disable);
        greenButton.setDisable(disable);
        cyanButton.setDisable(disable);
        blueButton.setDisable(disable);
        pinkButton.setDisable(disable);
    }

    @FXML
    private void setPegFill() {
        String color = GET_TOGGLE_NAME.apply(hexColorGroup.getSelectedToggle());
        mazeComponent.setColorSelection(color);
    }

    @FXML
    private void solveMaze() {
        try {
            List<HexTile> hexTileList = mazeComponent.createTileList();
            List<HexNode> nodeList = hexTileList.stream()
                    .map(HexTile::getInternalNode)
                    .toList();

            Quartet<Grid, String, Integer, List<Coordinates>> exitInfo = Hexamaze.solve(nodeList);
            setHexTileWalls(hexTileList, exitInfo.getValue0());
            String exitDirectionText = exitInfo.getValue1();
            if (exitDirectionText != null) {
                exitDirectionLabel.setText(
                        exitDirectionText.isEmpty() ?
                                exitDirectionText :
                                "Exit out of the " + exitDirectionText + " side"
                );

                fillHexTiles(hexTileList, exitInfo.getValue3(), exitInfo.getValue2());
            }
        } catch (IllegalArgumentException illegal) {
            FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage());
        }
    }

    private static void setHexTileWalls(List<HexTile> tileList, Grid found) {
        List<HexNode> foundNodes = found.getHexagon().asList();

        int size = foundNodes.size();
        for (int i = 0; i < size; i++) {
            tileList.get(i).setInternalNode(foundNodes.get(i));
        }
    }

    private static void fillHexTiles(List<HexTile> tileList, List<Coordinates> coordinatesList, int colorValue) {
        Color color = RED;

        for (Map.Entry<Color, Integer> entry : COLOR_MAP.entrySet()) {
            if (entry.getValue() == colorValue)
                color = entry.getKey();
        }

        for (HexTile hexTile : tileList)
            hexTile.setBackgroundFill(DEFAULT_BACKGROUND_COLOR);

        BufferedQueue<BufferedQueue<HexTile>> tileQueues = HexagonalPlane.convertFromList(tileList);

        Color finalColor = color;
        coordinatesList.stream()
                .map(c -> tileQueues.get(c.x()).get(c.y()))
                .forEach(tile -> tile.setBackgroundFill(finalColor));
    }

    @Override
    public void reset() {
        mazeComponent.reset();
        FacadeFX.clearText(exitDirectionLabel);
        FacadeFX.resetToggleGroups(hexGroup, hexColorGroup);
        FacadeFX.disableMultiple(redButton, yellowButton, greenButton, cyanButton, blueButton, pinkButton);
    }
}
