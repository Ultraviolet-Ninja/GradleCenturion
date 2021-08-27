package bomb.modules.ab.astrology;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;

import java.util.Arrays;

public class AstrologyController implements Resettable {
    private final AstroSymbol[] astroSymbolBuffer;

    @FXML private MFXButton resetButton;

    @FXML private MFXTextField omenTextField;

    @FXML private ToggleGroup elementGroup, celestialGroup, zodiacGroup;

    public AstrologyController() {
        astroSymbolBuffer = new AstroSymbol[3];
    }

    @FXML
    private void determineElement() {
        enableResetButton();
        setBufferLocation(elementGroup, AstroSymbol.getElementalSymbols(), Astrology.ELEMENT_INDEX);
    }

    @FXML
    private void determineCelestial() {
        enableResetButton();
        setBufferLocation(celestialGroup, AstroSymbol.getCelestialSymbols(), Astrology.CELESTIAL_INDEX);
    }

    @FXML
    private void determineZodiac() {
        enableResetButton();
        setBufferLocation(zodiacGroup, AstroSymbol.getZodiacSymbols(), Astrology.ZODIAC_INDEX);
    }

    private void enableResetButton() {
        if (resetButton.isDisable())
            FacadeFX.enable(resetButton);
    }

    private void setBufferLocation(ToggleGroup currentGroup, AstroSymbol[] searchArray, int index) {
        if (currentGroup.getSelectedToggle() == null) {
            astroSymbolBuffer[index] = null;
            disableResetButton();
            return;
        }

        String toggleName = FacadeFX.getToggleName(currentGroup).toUpperCase();
        for (int i = 0; i < searchArray.length; i++) {
            if (toggleName.equals(searchArray[i].name())) {
                astroSymbolBuffer[index] = searchArray[i];
                i = searchArray.length;
            }
        }

        if (astroSymbolBuffer[ELEMENTAL_INDEX] != null && astroSymbolBuffer[CELESTIAL_INDEX] != null &&
                astroSymbolBuffer[ZODIAC_INDEX] != null)
            processBuffer();
    }

    private void disableResetButton() {
        resetButton.setDisable(
                astroSymbolBuffer[ELEMENTAL_INDEX] == null && astroSymbolBuffer[CELESTIAL_INDEX] == null &&
                        astroSymbolBuffer[ZODIAC_INDEX] == null
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
