package bomb.modules.t.translated.solutions.gas;

import bomb.abstractions.Resettable;
import bomb.modules.t.translated.solutions.TranslationComponent;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class VentGasComponent implements Resettable, TranslationComponent {
    @FXML
    private Label yesLabel, noLabel;

    public void initialize() {

    }

    @Override
    public void reset() {
        FacadeFX.clearMultipleLabels(yesLabel, noLabel);
    }

    @Override
    public void setContent(List<String> languageContent) {

    }
}
