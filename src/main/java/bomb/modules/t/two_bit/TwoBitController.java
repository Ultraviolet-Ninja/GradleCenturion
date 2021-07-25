package bomb.modules.t.two_bit;

import bomb.abstractions.Resettable;
import bomb.tools.facade.FacadeFX;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;

public class TwoBitController implements Resettable {
    @FXML
    private JFXButton nextCode;

    @FXML
    private JFXTextField query, numberCode, cmdLine;

    public void initialize(){
        numberCode.setTextFormatter(createTwoBitTextFormatter());
    }

    private TextFormatter<String> createTwoBitTextFormatter(){
        return new TextFormatter<>(change -> {
            if (!change.isContentChange()) return change;

            String text = change.getControlNewText();
            if (text.isEmpty() || text.matches("\\b\\d{1,2}\\b"))
                return change;

            return null;
        });
    }

    @FXML
    private void getInitialCode(){
        try {
            query.setText(TwoBit.initialCode());
            FacadeFX.enableMultiple(numberCode, cmdLine, nextCode);
        } catch (IllegalArgumentException illegal){
            FacadeFX.setAlert(Alert.AlertType.WARNING,"You need to set the Serial Code");
        } catch (StringIndexOutOfBoundsException incomplete){
            FacadeFX.setAlert(Alert.AlertType.ERROR, "The Serial Code is incomplete");
        }
    }

    @FXML
    private void getNextCode(){
        try {
            cmdLine.setText(TwoBit.nextCode(numberCode.getText()));
            FacadeFX.clearText(numberCode);
        } catch (IllegalArgumentException illegal){
            FacadeFX.setAlert(Alert.AlertType.ERROR, "That wasn't two bits");
        }
    }

    @Override
    public void reset() {
        FacadeFX.toggleNodes(true, numberCode, cmdLine, nextCode);
        FacadeFX.clearMultipleTextFields(query, numberCode, cmdLine);
    }
}
