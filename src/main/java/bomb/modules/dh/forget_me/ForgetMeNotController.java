package bomb.modules.dh.forget_me;

import bomb.Widget;
import bomb.abstractions.Resettable;
import bomb.tools.HoverHandler;
import bomb.tools.facade.FacadeFX;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.util.function.Consumer;

public class ForgetMeNotController implements Resettable {
    private static final double MODULE_COMPLETION_PERCENTAGE = 0.9;

    @FXML
    private JFXButton outputButton, undoButton, resetButton,
            one, two, three, four, five, six, seven, eight, nine, zero;

    @FXML
    private JFXTextField outputArea;

    @FXML
    private JFXTextField confirmationField;

    @FXML
    private Label stageCounter;

    public void initialize() {
        HoverHandler<ActionEvent> handler = new HoverHandler<>(createPressAction());
        FacadeFX.bindHandlerToButtons(handler, one, two, three, four, five, six, seven, eight, nine, zero);
    }

    private Consumer<ActionEvent> createPressAction() {
        return event -> {
            if (Widget.getNumModules() == 0) {
                FacadeFX.setAlert(Alert.AlertType.ERROR, "Need to set the number of modules for this to work");
                return;
            }
            try {
                addNextNumber((JFXButton) event.getSource());
                if (undoButton.isDisable()) FacadeFX.enable(undoButton);
                if (resetButton.isDisable()) FacadeFX.enable(resetButton);
                setButtonPrivileges();
            } catch (IllegalArgumentException illegal) {
                FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage(), "Serial Code", "");
            }
        };
    }

    private void addNextNumber(JFXButton button) {
        int extractedNumber = Integer.parseInt(button.getText());
        ForgetMeNot.add(extractedNumber);
        confirmationField.setText("Stage " + (ForgetMeNot.getStage() - 1) + " was a " + extractedNumber);
        synchronizeStageNumber();
    }

    private void setButtonPrivileges() {
        if (ForgetMeNot.getStage() - 1 >= Widget.getNumModules() * MODULE_COMPLETION_PERCENTAGE)
            FacadeFX.enable(outputButton);

        if (ForgetMeNot.getStage() - 1 == Widget.getNumModules())
            FacadeFX.disableMultiple(one, two, three, four, five, six, seven, eight, nine, zero);
    }

    @FXML
    private void undoLastStage() {
        ForgetMeNot.undoLastStage();
        synchronizeStageNumber();
        confirmationField.setText("Undid Last Stage");
        if (ForgetMeNot.getStage() == 0)
            FacadeFX.disableMultiple(resetButton, undoButton, outputButton);

        if (ForgetMeNot.getStage() - 1 < Widget.getNumModules() * MODULE_COMPLETION_PERCENTAGE)
            FacadeFX.disable(outputButton);
    }

    @FXML
    private void stringifyOutput() {
        outputArea.setText(ForgetMeNot.stringifyFinalCode());
        FacadeFX.disableMultiple(outputButton, undoButton);
    }

    private void synchronizeStageNumber() {
        stageCounter.setText(String.valueOf(ForgetMeNot.getStage()));
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
