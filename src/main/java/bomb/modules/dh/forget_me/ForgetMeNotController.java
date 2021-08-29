package bomb.modules.dh.forget_me;

import bomb.Widget;
import bomb.abstractions.Resettable;
import bomb.tools.event.HoverHandler;
import bomb.tools.pattern.facade.FacadeFX;
import com.jfoenix.controls.JFXTextArea;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.util.function.Consumer;

public class ForgetMeNotController implements Resettable {
    @FXML
    private MFXButton outputButton, undoButton, resetButton,
            one, two, three, four, five, six, seven, eight, nine, zero;

    @FXML
    private JFXTextArea outputArea;

    @FXML
    private MFXTextField confirmationField;

    @FXML
    private Label stageCounter;

    public void initialize() {
        HoverHandler<ActionEvent> handler = new HoverHandler<>(createPressAction());
        FacadeFX.bindHandlerToButtons(handler, one, two, three, four, five, six, seven, eight, nine, zero);
    }

    private Consumer<ActionEvent> createPressAction() {
        return event -> {
            try {
                addNextNumber((MFXButton) event.getSource());
                if (undoButton.isDisable()) FacadeFX.enable(undoButton);
                if (resetButton.isDisable()) FacadeFX.enable(resetButton);
                setButtonPrivileges();
            } catch (IllegalArgumentException illegal) {
                FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage());
            } catch (IllegalStateException illegal) {
                FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage(), "Serial Code", "");
            }
        };
    }

    private void addNextNumber(MFXButton button) {
        int extractedNumber = Integer.parseInt(button.getText());
        ForgetMeNot.add(extractedNumber);
        confirmationField.setText("Stage " + ForgetMeNot.getStage() + " was a " + extractedNumber);
        synchronizeStageNumber();
    }

    private void setButtonPrivileges() {
        if (ForgetMeNot.getStage() > 2)
            FacadeFX.enable(outputButton);

        if (ForgetMeNot.getStage() >= Widget.getNumModules() - 2)
            FacadeFX.disableMultiple(one, two, three, four, five, six, seven, eight, nine, zero);
    }

    @FXML
    private void undoLastStage() {
        ForgetMeNot.undoLastStage();
        synchronizeStageNumber();
        confirmationField.setText("Undid Last Stage");
        if (ForgetMeNot.getStage() == 0)
            FacadeFX.disableMultiple(resetButton, undoButton, outputButton);

        if (ForgetMeNot.getStage() < 2)
            FacadeFX.disable(outputButton);

        if (ForgetMeNot.getStage() < Widget.getNumModules() - 2)
            FacadeFX.enableMultiple(one, two, three, four, five, six, seven, eight, nine, zero);
    }

    @FXML
    private void stringifyOutput() {
        outputArea.setText(ForgetMeNot.stringifyFinalCode());
    }

    private void synchronizeStageNumber() {
        String injectionText = String.valueOf(ForgetMeNot.getStage() + 1);
        if (ForgetMeNot.getStage() == Widget.getNumModules() - 2)
            injectionText = "-";
        stageCounter.setText(injectionText);
    }

    @FXML
    private void resetModule() {
        reset();
    }

    @Override
    public void reset() {
        ForgetMeNot.reset();
        synchronizeStageNumber();
        FacadeFX.disableMultiple(outputButton, undoButton, resetButton);
        FacadeFX.clearMultipleTextFields(outputArea, confirmationField);
        FacadeFX.enableMultiple(one, two, three, four, five, six, seven, eight, nine, zero);
    }
}
