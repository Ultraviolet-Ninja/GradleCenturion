package bomb.modules.t.translated.solutions.gas;

import bomb.abstractions.Resettable;
import bomb.modules.t.translated.TranslationComponent;
import bomb.tools.pattern.facade.MaterialFacade;
import io.github.palexdev.materialfx.controls.MFXLabel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.util.List;

import static bomb.modules.t.translated.LanguageCSVReader.LanguageRow.NO_ROW;
import static bomb.modules.t.translated.LanguageCSVReader.LanguageRow.YES_ROW;
import static bomb.tools.pattern.facade.FacadeFX.loadComponent;

public final class VentGasComponent extends Pane implements Resettable, TranslationComponent {
    @FXML
    private MFXLabel yesLabel, noLabel;

    public VentGasComponent() {
        super();
        var loader = new FXMLLoader(getClass().getResource("vent_gas.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loadComponent("Vent Gas", loader);
    }

    @Override
    public void reset() {
        MaterialFacade.clearMultipleLabels(yesLabel, noLabel);
    }

    @Override
    public void setContent(List<String> languageContent) {
        yesLabel.setText(languageContent.get(YES_ROW.getRowIndex()));
        noLabel.setText(languageContent.get(NO_ROW.getRowIndex()));
    }
}
