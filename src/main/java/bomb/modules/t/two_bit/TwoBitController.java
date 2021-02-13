package bomb.modules.t.two_bit;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static bomb.tools.Mechanics.NUMBER_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;

public class TwoBitController {
    @FXML
    private Button next;

    @FXML
    private TextField query, numberCode, cmdLine;

    @FXML
    private void initQueue(){
        try {
            query.setText(TwoBit.initialCode());
            numberCode.setDisable(false);
            cmdLine.setDisable(false);
            next.setDisable(false);
        } catch (IllegalArgumentException illegal){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You need to set the Serial Code");
            alert.showAndWait();
        } catch (StringIndexOutOfBoundsException incomplete){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The Serial Code is incomplete");
            alert.showAndWait();
        }
    }

    @FXML
    private void nextCode(){
        try {
            cmdLine.setText(TwoBit.nextCode(ultimateFilter(numberCode.getText(), NUMBER_REGEX)));
            numberCode.setText("");
        } catch (IllegalArgumentException illegal){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("That wasn't two bits");
            alert.showAndWait();
        }
    }
}
