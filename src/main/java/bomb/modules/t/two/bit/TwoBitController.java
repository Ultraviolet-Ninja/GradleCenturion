package bomb.modules.t.two.bit;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import static bomb.tools.pattern.factory.TextFormatterFactory.createTwoDigitTextFormatter;

public final class TwoBitController implements Resettable {
    @FXML
    private MFXButton nextCodeButton;

    @FXML
    private MFXTextField queryField, numberCodeField, nextCodeOutputField;

    public void initialize() {
        numberCodeField.setTextFormatter(createTwoDigitTextFormatter());
    }

    @FXML
    private void getInitialCode() {
        try {
            queryField.setText(TwoBit.initialCode());
            FacadeFX.enable(nextCodeButton);
        } catch (IllegalArgumentException illegal) {
            FacadeFX.setAlert(Alert.AlertType.WARNING, "You need to set the Serial Code");
        } catch (StringIndexOutOfBoundsException incomplete) {
            FacadeFX.setAlert(Alert.AlertType.ERROR, "The Serial Code is incomplete");
        }
    }

    @FXML
    private void getNextCode() {
        try {
            nextCodeOutputField.setText(TwoBit.nextCode(numberCodeField.getText()));
            FacadeFX.clearText(numberCodeField);
        } catch (IllegalArgumentException illegal) {
            FacadeFX.setAlert(Alert.AlertType.ERROR, "That wasn't two bits");
        }
    }

    @Override
    public void reset() {
        FacadeFX.disable(nextCodeButton);
        FacadeFX.clearMultipleTextFields(queryField, numberCodeField, nextCodeOutputField);
    }
}
