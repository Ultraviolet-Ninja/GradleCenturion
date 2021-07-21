package bomb.modules.s.souvenir;

import bomb.abstractions.Resettable;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

public class OldSouvenirController implements Resettable {
    @FXML
    private Tab souvenirTab;

    @FXML
    private TextArea souvenirOutput;

    @FXML
    private void streamOut(){
        String temp = OldSouvenir.flush();
        if (souvenirTab.isSelected() && temp != null)
            souvenirOutput.setText(temp);
    }

    @Override
    public void reset() {

    }
}
