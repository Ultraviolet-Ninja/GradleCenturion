package bomb.modules.ab.astrology;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;

import java.util.Arrays;

public class AstrologyController implements Resettable {
    private enum AstrologyIndex {
        ELEMENTAL_INDEX, CELESTIAL_INDEX, ZODIAC_INDEX
    }

    private final AstroSymbol[] astroSymbolBuffer;

    @FXML private JFXButton resetButton;

    @FXML private JFXTextField omenTextField;

    @FXML private ToggleGroup elementGroup, celestialGroup, zodiacGroup;

    public AstrologyController() {
        astroSymbolBuffer = new AstroSymbol[3];
    }

    @FXML
    private void determineElement() {
        enableResetButton();
        setBufferLocation(elementGroup, AstroSymbol.getElementalSymbols(), AstrologyIndex.ELEMENTAL_INDEX);
    }

    @FXML
    private void determineCelestial() {
        enableResetButton();
        setBufferLocation(celestialGroup, AstroSymbol.getCelestialSymbols(), AstrologyIndex.CELESTIAL_INDEX);
    }

    @FXML
    private void determineZodiac() {
        enableResetButton();
        setBufferLocation(zodiacGroup, AstroSymbol.getZodiacSymbols(), AstrologyIndex.ZODIAC_INDEX);
    }

    private void enableResetButton() {
        if (resetButton.isDisable())
            FacadeFX.enable(resetButton);
    }

    private void setBufferLocation(ToggleGroup currentGroup, AstroSymbol[] searchArray, AstrologyIndex index) {
        if (currentGroup.getSelectedToggle() == null) {
            astroSymbolBuffer[index.ordinal()] = null;
            disableResetButton();
            return;
        }

        String toggleName = FacadeFX.getToggleName(currentGroup).toUpperCase();
        for (int i = 0; i < searchArray.length; i++) {
            if (toggleName.equals(searchArray[i].name())) {
                astroSymbolBuffer[index.ordinal()] = searchArray[i];
                i = searchArray.length;
            }
        }

        if (astroSymbolBuffer[AstrologyIndex.ELEMENTAL_INDEX.ordinal()] != null &&
                astroSymbolBuffer[AstrologyIndex.CELESTIAL_INDEX.ordinal()] != null &&
                astroSymbolBuffer[AstrologyIndex.ZODIAC_INDEX.ordinal()] != null)
            processBuffer();
    }

    private void disableResetButton() {
        resetButton.setDisable(
                astroSymbolBuffer[AstrologyIndex.ELEMENTAL_INDEX.ordinal()] == null &&
                        astroSymbolBuffer[AstrologyIndex.CELESTIAL_INDEX.ordinal()] == null &&
                        astroSymbolBuffer[AstrologyIndex.ZODIAC_INDEX.ordinal()] == null
        );
    }

    private void processBuffer() {
        try {
            omenTextField.setText(Astrology.calculate(
                    astroSymbolBuffer[0], astroSymbolBuffer[1], astroSymbolBuffer[2])
            );
        } catch (IllegalArgumentException illegal){
            FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage());
        }
    }

    @FXML
    private void resetModule() {
        Arrays.fill(astroSymbolBuffer, null);
        FacadeFX.disable(resetButton);
        FacadeFX.clearText(omenTextField);
        FacadeFX.resetToggleGroup(elementGroup);
        FacadeFX.resetToggleGroup(celestialGroup);
        FacadeFX.resetToggleGroup(zodiacGroup);
    }

    @Override
    public void reset() {
        resetModule();
    }
}
