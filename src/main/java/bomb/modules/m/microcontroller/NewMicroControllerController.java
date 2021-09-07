package bomb.modules.m.microcontroller;

import bomb.abstractions.Resettable;
import bomb.components.microcontroller.AbstractChip;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class NewMicroControllerController implements Resettable {
    private AbstractChip currentChip;
    private String controllerType = "", pinCount = "";

    @FXML
    private MFXButton submitButton;

    @FXML
    private Pane chipBackground;

    @FXML
    private MFXTextField serialInput;

    @FXML
    private ToggleGroup controllerGroup, pinCountGroup;

    @Override
    public void reset() {

    }
}
