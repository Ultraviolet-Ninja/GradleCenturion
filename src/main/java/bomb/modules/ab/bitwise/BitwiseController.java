package bomb.modules.ab.bitwise;

import bomb.abstractions.Resettable;
import bomb.tools.event.HoverHandler;
import bomb.tools.pattern.facade.FacadeFX;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.util.function.Consumer;

public class BitwiseController implements Resettable {
    @FXML private MFXButton bitAnd, bitOr, bitXor, bitNot;

    @FXML private MFXTextField byteTextField;

    public void initialize() {
        FacadeFX.bindHandlerToButtons(new HoverHandler<>(initAction()), bitAnd, bitNot, bitXor, bitOr);
    }

    private Consumer<ActionEvent> initAction() {
        return event -> {
            BitwiseOps source = BitwiseOps.valueOf(((Button) event.getSource()).getText().toUpperCase());
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
