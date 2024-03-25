package bomb.modules.ab.astrology;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

import java.util.Arrays;

public final class AstrologyController implements Resettable {
    private final AstrologySymbol[] astrologySymbolBuffer;

    @FXML
    private MFXButton resetButton;

    @FXML
    private MFXTextField omenTextField;

    @FXML
    private ToggleGroup elementGroup, celestialGroup, zodiacGroup;

    public AstrologyController() {
        astrologySymbolBuffer = new AstrologySymbol[3];
    }

    @FXML
    private void determineElement() {
        enableResetButton();
        setBufferLocation(elementGroup, AstrologySymbol.getElementalSymbols(), Astrology.ELEMENT_INDEX);
    }

    @FXML
    private void determineCelestial() {
        enableResetButton();
        setBufferLocation(celestialGroup, AstrologySymbol.getCelestialSymbols(), Astrology.CELESTIAL_INDEX);
    }

    @FXML
    private void determineZodiac() {
        enableResetButton();
        setBufferLocation(zodiacGroup, AstrologySymbol.getZodiacSymbols(), Astrology.ZODIAC_INDEX);
    }

    private void enableResetButton() {
        if (resetButton.isDisable())
            FacadeFX.enable(resetButton);
    }

    private void setBufferLocation(ToggleGroup currentGroup, AstrologySymbol[] searchArray, int index) {
        if (currentGroup.getSelectedToggle() == null) {
            astrologySymbolBuffer[index] = null;
            disableResetButton();
            return;
        }

        String toggleName = FacadeFX.getToggleName(currentGroup).toUpperCase();
        for (int i = 0; i < searchArray.length; i++) {
            if (toggleName.equals(searchArray[i].name())) {
                astrologySymbolBuffer[index] = searchArray[i];
                i = searchArray.length;
            }
        }

        if (astrologySymbolBuffer[Astrology.ELEMENT_INDEX] != null &&
                astrologySymbolBuffer[Astrology.CELESTIAL_INDEX] != null &&
                astrologySymbolBuffer[Astrology.ZODIAC_INDEX] != null)
            processBuffer();
    }

    private void disableResetButton() {
        resetButton.setDisable(
                astrologySymbolBuffer[Astrology.ELEMENT_INDEX] == null &&
                        astrologySymbolBuffer[Astrology.CELESTIAL_INDEX] == null &&
                        astrologySymbolBuffer[Astrology.ZODIAC_INDEX] == null
        );
    }

    private void processBuffer() {
        try {
            omenTextField.setText(Astrology.calculate(astrologySymbolBuffer));
        } catch (IllegalArgumentException illegal) {
            FacadeFX.setAlert(illegal.getMessage());
        }
    }

    @FXML
    private void resetModule() {
        Arrays.fill(astrologySymbolBuffer, null);
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
