package bomb.modules.dh.hexamaze_redesign;

import bomb.abstractions.Resettable;
import bomb.components.hex.MazeComponent;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import static bomb.tools.pattern.facade.FacadeFX.GET_TOGGLE_NAME;

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
    }

    @FXML
    private void setPegFill() {
        String color = GET_TOGGLE_NAME.apply(hexColorGroup.getSelectedToggle());
        mazeComponent.setColorSelection(color);
    }

    @FXML
    private void solveMaze() {
        try {
            String exitDirectionText = Hexamaze.solve(mazeComponent.createTileList());
            exitDirectionLabel.setText("Exit out of the " + exitDirectionText + " side");
        } catch (IllegalArgumentException illegal) {
            FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage());
        }
    }

    @Override
    public void reset() {
        mazeComponent.reset();
        FacadeFX.clearText(exitDirectionLabel);
        FacadeFX.resetToggleGroups(hexGroup, hexColorGroup);
        FacadeFX.disableMultiple(redButton, yellowButton, greenButton, cyanButton, blueButton, pinkButton);
    }
}
