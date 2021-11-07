package bomb.modules.ab.bitwise;

import bomb.abstractions.Resettable;
import bomb.tools.event.HoverHandler;
import bomb.tools.pattern.facade.FacadeFX;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.function.Consumer;

import static bomb.tools.pattern.facade.FacadeFX.BUTTON_NAME_FROM_EVENT;

public class BitwiseController implements Resettable {
    @FXML
    private MFXButton andButton, orButton, xorButton, notButton;

    @FXML
    private MFXTextField byteTextField;

    public void initialize() {
        FacadeFX.bindHandlerToButtons(new HoverHandler<>(createButtonAction()),
                andButton, notButton, xorButton, orButton);
    }

    private Consumer<ActionEvent> createButtonAction() {
        return event -> {
            String buttonText = BUTTON_NAME_FROM_EVENT.apply(event);
            BitwiseOps source = BitwiseOps.valueOf(buttonText.toUpperCase());
            try {
                byteTextField.setText(Bitwise.getByte(source));
            } catch (IllegalArgumentException illegal) {
                FacadeFX.setAlert(Alert.AlertType.INFORMATION, illegal.getMessage(),
                        "Something's empty", "Do more edge work");
            }
        };
    }

    @Override
    public void reset() {
        FacadeFX.clearText(byteTextField);
    }
}
