package bomb.modules.ab.alphabet;

import bomb.interfaces.Resettable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import static bomb.tools.Filter.CHAR_FILTER;
import static bomb.tools.Filter.ultimateFilter;


public class AlphabetController implements Resettable {
    @FXML
    private TextField alphabetOut;

    @FXML
    private void getAlphaField(){
        if (!alphabetOut.getText().isEmpty()){
            String text = ultimateFilter(alphabetOut.getText(), CHAR_FILTER);
            if (text.length() == 4){
                alphabetOut.setText(Alphabet.order(text));
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("That wasn't 4 valid characters there, bud");
                alert.show();
                alphabetOut.setText("");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("That field is empty there, bud");
            alert.show();
        }
    }

    @Override
    public void reset() {

    }
}

