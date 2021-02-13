package bomb.modules.ab.alphabet;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import static bomb.tools.Mechanics.LOWERCASE_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;

public class AlphabetController {
    @FXML
    private TextField alphabetOut;

    @FXML
    private void getAlphaField(){
        if (!alphabetOut.getText().isEmpty()){
            String text = ultimateFilter(alphabetOut.getText(), LOWERCASE_REGEX);
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
}

