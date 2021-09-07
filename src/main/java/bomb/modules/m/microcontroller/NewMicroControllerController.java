package bomb.modules.m.microcontroller;

import bomb.abstractions.Resettable;
import bomb.components.microcontroller.AbstractChip;
import bomb.tools.pattern.factory.TextFormatterFactory;
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
    private MFXTextField moduleSerialCodeInput;

    @FXML
    private ToggleGroup controllerGroup, pinCountGroup;

    public void initialize() {
        //TODO This type of formatter might be wrong
        moduleSerialCodeInput.setTextFormatter(TextFormatterFactory.createTwoBitTextFormatter());
    }

    @Override
    public void reset() {

    }
}
