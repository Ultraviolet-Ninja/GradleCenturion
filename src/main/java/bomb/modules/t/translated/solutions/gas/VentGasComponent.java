package bomb.modules.t.translated.solutions.gas;

import bomb.abstractions.Resettable;
import bomb.modules.t.translated.solutions.TranslationComponent;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

import static bomb.modules.t.translated.LanguageCSVReader.LanguageRow.NO_ROW;
import static bomb.modules.t.translated.LanguageCSVReader.LanguageRow.YES_ROW;

public class VentGasComponent extends Pane implements Resettable, TranslationComponent {
    @FXML
    private Label yesLabel, noLabel;

    public VentGasComponent() {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vent_gas.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    @Override
    public void reset() {
        FacadeFX.clearMultipleLabels(yesLabel, noLabel);
    }

    @Override
    public void setContent(List<String> languageContent) {
        yesLabel.setText(languageContent.get(YES_ROW.getIndex()));
        noLabel.setText(languageContent.get(NO_ROW.getIndex()));
    }
}
