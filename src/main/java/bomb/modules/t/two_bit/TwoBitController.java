package bomb.modules.t.two_bit;

import bomb.interfaces.Resettable;
import bomb.tools.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static bomb.tools.Filter.NUMBER_PATTERN;
import static bomb.tools.Filter.ultimateFilter;

public class TwoBitController implements Resettable {
    @FXML
    private Button next;

    @FXML
    private TextField query, numberCode, cmdLine;

    @FXML
    private void initQueue(){
        try {
            query.setText(TwoBit.initialCode());
            FacadeFX.toggleNodes(false, numberCode, cmdLine, next);
        } catch (IllegalArgumentException illegal){
            FacadeFX.setAlert(Alert.AlertType.WARNING,"You need to set the Serial Code");
        } catch (StringIndexOutOfBoundsException incomplete){
            FacadeFX.setAlert(Alert.AlertType.ERROR, "The Serial Code is incomplete");
        }
    }

    @FXML
    private void nextCode(){
        try {
            cmdLine.setText(TwoBit.nextCode(ultimateFilter(numberCode.getText(), NUMBER_PATTERN)));
            numberCode.setText("");
        } catch (IllegalArgumentException illegal){
            FacadeFX.setAlert(Alert.AlertType.ERROR, "That wasn't two bits");
        }
    }

    @Override
    public void reset() {

    }
}
