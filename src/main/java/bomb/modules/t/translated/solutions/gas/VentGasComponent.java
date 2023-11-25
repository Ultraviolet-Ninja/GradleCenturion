package bomb.modules.t.translated.solutions.gas;

import bomb.abstractions.Resettable;
import bomb.modules.t.translated.TranslationComponent;
import bomb.modules.t.translated.TranslationResults;
import bomb.tools.pattern.facade.MaterialFacade;
import io.github.palexdev.materialfx.controls.MFXLabel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

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
    public void setContent(TranslationResults results) {
        yesLabel.setText(
                results.hasResponseYes() ?
                        results.ventGasResponseYes() :
                        ""
        );

        noLabel.setText(
                results.hasResponseNo() ?
                        results.ventGasResponseNo() :
                        ""
        );
    }
}
