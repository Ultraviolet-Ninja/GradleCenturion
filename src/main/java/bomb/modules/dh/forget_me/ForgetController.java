package bomb.modules.dh.forget_me;

import bomb.Widget;
import bomb.tools.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ForgetController {
    private static final double COMPLETION_PERCENTAGE = 0.9;

    private int stageCounter = 1;

    @FXML
    private Button one, two, three, four, five, six, seven, eight, nine, zero, flush, undoButton;

    @FXML
    private Label currentStage;

    @FXML
    private TextArea flushArea;

    @FXML
    private TextField forgetMeOutput;

    @FXML
    private void nextNumber(){
        if (Widget.getNumModules() != 0) {
            try {
                if (one.isHover())
                    addTo(one);
                else if (two.isHover())
                    addTo(two);
                else if (three.isHover())
                    addTo(three);
                else if (four.isHover())
                    addTo(four);
                else if (five.isHover())
                    addTo(five);
                else if (six.isHover())
                    addTo(six);
                else if (seven.isHover())
                    addTo(seven);
                else if (eight.isHover())
                    addTo(eight);
                else if (nine.isHover())
                    addTo(nine);
                else if (zero.isHover())
                    addTo(zero);
                undoButton.setDisable(false);
                writeNext();
            } catch (IllegalArgumentException illegal) {
                FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage(), "Serial Code", "");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Need to set the number of modules for this to work");
            alert.showAndWait();
        }
    }

    private void writeNext(){
        currentStage.setText(String.valueOf(++stageCounter));
        judge();
        maxCap();
    }

    private void addTo(Button button){
        int temp = Integer.parseInt(button.getText());
        ForgetMeNot.add(stageCounter, temp);
        forgetMeOutput.setText("Stage " + stageCounter + " was a " + temp);
    }

    private void judge(){
        if (stageCounter >= Widget.getNumModules() * COMPLETION_PERCENTAGE) flush.setDisable(false);
    }

    private void maxCap(){
        if (stageCounter == Widget.getNumModules())
            FacadeFX.toggleNodes(true, one, two, three, four, five, six, seven, eight, nine, zero);
    }

    @FXML
    private void undo(){
        if (stageCounter > 1) {
            ForgetMeNot.undo();
            forgetMeOutput.setText("Previous stage undone");
            currentStage.setText(String.valueOf(--stageCounter));
        }
        undoCheck();
    }

    private void undoCheck(){
        if (stageCounter == 1) undoButton.setDisable(true);
    }

    @FXML
    private void flushOut(){
        flushArea.setText(ForgetMeNot.flush());
        FacadeFX.toggleNodes(true, undoButton, flush);
        stageCounter = 1;
        reEnable();
    }

    private void reEnable(){
        FacadeFX.toggleNodes(false, one, two, three, four, five, six, seven, eight, nine, zero);
    }
}
