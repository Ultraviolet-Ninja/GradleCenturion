package bomb.modules.dh.forget_me;

import bomb.Widget;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ForgetController {
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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Serial Code");
                alert.setContentText(illegal.getMessage());
                alert.showAndWait();
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
        if (stageCounter >= Widget.getNumModules() * 0.9) flush.setDisable(false);
    }

    private void maxCap(){
        if (stageCounter == Widget.getNumModules()){
            one.setDisable(true);
            two.setDisable(true);
            three.setDisable(true);
            four.setDisable(true);
            five.setDisable(true);
            six.setDisable(true);
            seven.setDisable(true);
            eight.setDisable(true);
            nine.setDisable(true);
            zero.setDisable(true);
        }
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
        flush.setDisable(true);
        undoButton.setDisable(true);
        stageCounter = 1;
        reEnable();
    }

    private void reEnable(){
        one.setDisable(false);
        two.setDisable(false);
        three.setDisable(false);
        four.setDisable(false);
        five.setDisable(false);
        six.setDisable(false);
        seven.setDisable(false);
        eight.setDisable(false);
        nine.setDisable(false);
        zero.setDisable(false);
    }
}
