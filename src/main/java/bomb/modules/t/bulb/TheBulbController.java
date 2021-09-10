package bomb.modules.t.bulb;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

public class TheBulbController implements Resettable {
    @FXML
    private TextArea bulbResults;

    @FXML
    private ToggleGroup colorGroup, opacityGroup, luminosityGroup;

    @FXML
    private JFXButton submitButton;

    @FXML
    private void enableSubmitButton() {
        boolean isColorSelected = FacadeFX.hasSelectedToggle(colorGroup);
        boolean isOpacitySelected = FacadeFX.hasSelectedToggle(opacityGroup);
        boolean isLuminositySelected = FacadeFX.hasSelectedToggle(luminosityGroup);

        if (isColorSelected && isOpacitySelected && isLuminositySelected) {
            FacadeFX.enable(submitButton);
            return;
        }
        FacadeFX.disable(submitButton);
    }

    @FXML
    private void submitBulbInfo() {

    }

    @Override
    public void reset() {
        FacadeFX.disable(submitButton);
        FacadeFX.clearText(bulbResults);
        FacadeFX.unselectFromMultipleToggleGroup(colorGroup, opacityGroup, luminosityGroup);
    }
}
