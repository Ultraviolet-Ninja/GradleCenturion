package bomb.modules.s.souvenir;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

public class SouvenirController {
    @FXML
    private Tab souvenirTab;

    @FXML
    private TextArea souvenirOutput;

    @FXML
    private void streamOut(){
        String temp = Souvenir.flush();
        if (souvenirTab.isSelected() && temp != null)
            souvenirOutput.setText(temp);
    }
}
