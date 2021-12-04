package bomb.modules.t.bulb;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

import java.util.List;

import static bomb.modules.t.bulb.Bulb.Color.BLUE;
import static bomb.modules.t.bulb.Bulb.Color.GREEN;
import static bomb.modules.t.bulb.Bulb.Color.PURPLE;
import static bomb.modules.t.bulb.Bulb.Color.RED;
import static bomb.modules.t.bulb.Bulb.Color.WHITE;
import static bomb.modules.t.bulb.Bulb.Color.YELLOW;
import static bomb.modules.t.bulb.Bulb.Light.OFF;
import static bomb.modules.t.bulb.Bulb.Light.ON;
import static bomb.modules.t.bulb.Bulb.Opacity.OPAQUE;
import static bomb.modules.t.bulb.Bulb.Opacity.TRANSLUCENT;
import static bomb.modules.t.bulb.Bulb.Position.SCREWED;

public class TheBulbController implements Resettable {
    @FXML
    private ToggleGroup colorGroup, opacityGroup, luminosityGroup;

    @FXML
    private JFXButton submitButton;

    @FXML
    private JFXTextArea bulbResults;

    @FXML
    private void enableSubmitButton() {
        boolean isColorSelected = FacadeFX.hasSelectedToggle(colorGroup);
        boolean isOpacitySelected = FacadeFX.hasSelectedToggle(opacityGroup);
        boolean isLuminositySelected = FacadeFX.hasSelectedToggle(luminosityGroup);

        if (isColorSelected && isOpacitySelected && isLuminositySelected) {
            FacadeFX.enable(submitButton);
            return;
        }
        FacadeFX.disable(submitButton);
    }

    @FXML
    private void submitBulbInfo() {
        Bulb input = new Bulb();
        input.setColor(retrieveColor());
        input.setLight(retrieveLuminosity());
        input.setOpacity(retrieveOpacity());
        input.setPosition(SCREWED);

        outputToTextArea(TheBulb.solve(input));
    }

    private Bulb.Color retrieveColor() {
        String resultingColor = FacadeFX.getToggleName(colorGroup);
        return switch(resultingColor) {
            case "Red" -> RED;
            case "Yellow" -> YELLOW;
            case "Green" -> GREEN;
            case "Blue" -> BLUE;
            case "Pink" -> PURPLE;
            default -> WHITE;
        };
    }

    private Bulb.Light retrieveLuminosity() {
        String resultingLight = FacadeFX.getToggleName(luminosityGroup);
        return resultingLight.equals("Lit") ? ON : OFF;
    }

    private Bulb.Opacity retrieveOpacity() {
        String resultingOpacity = FacadeFX.getToggleName(opacityGroup);
        return resultingOpacity.equals("Opaque") ? OPAQUE : TRANSLUCENT;
    }

    private void outputToTextArea(List<String> results) {
        String outputText = String.join("\n", results);
        bulbResults.setText(outputText);
    }

    @Override
    public void reset() {
        FacadeFX.disable(submitButton);
        FacadeFX.clearText(bulbResults);
        FacadeFX.resetToggleGroups(colorGroup, opacityGroup, luminosityGroup);
    }
}
