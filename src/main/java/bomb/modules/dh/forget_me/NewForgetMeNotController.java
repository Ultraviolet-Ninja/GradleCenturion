package bomb.modules.dh.forget_me;

import bomb.abstractions.Resettable;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

public class NewForgetMeNotController implements Resettable {
    private static final double COMPLETION_PERCENTAGE = 0.9;

    @FXML private JFXTextArea outputArea;

    @FXML private JFXTextField confirmationField;

    public void initialize(){

    }

    @FXML
    private void addNextNumber(){

        synchronizeStageNumber();
    }

    @FXML
    private void undoLastStage(){
        NewForgetMeNot.undoLastStage();
        synchronizeStageNumber();
    }

    @FXML
    private void stringify(){

    }

    private void synchronizeStageNumber(){

    }

    @Override
    public void reset() {
        NewForgetMeNot.reset();
    }
}
