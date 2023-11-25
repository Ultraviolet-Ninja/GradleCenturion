package bomb.modules.t.the.bulb;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;

import java.util.List;

import static bomb.modules.t.the.bulb.BulbModel.Color.BLUE;
import static bomb.modules.t.the.bulb.BulbModel.Color.GREEN;
import static bomb.modules.t.the.bulb.BulbModel.Color.PURPLE;
import static bomb.modules.t.the.bulb.BulbModel.Color.RED;
import static bomb.modules.t.the.bulb.BulbModel.Color.WHITE;
import static bomb.modules.t.the.bulb.BulbModel.Color.YELLOW;
import static bomb.modules.t.the.bulb.BulbModel.Light.OFF;
import static bomb.modules.t.the.bulb.BulbModel.Light.ON;
import static bomb.modules.t.the.bulb.BulbModel.Opacity.OPAQUE;
import static bomb.modules.t.the.bulb.BulbModel.Opacity.TRANSLUCENT;
import static bomb.modules.t.the.bulb.BulbModel.Position.SCREWED;
import static bomb.tools.pattern.facade.FacadeFX.buildAlert;
import static bomb.tools.string.StringFormat.ARROW;

public final class TheBulbController implements Resettable {
    private static final String ERROR_TEXT = "Operation canceled.\nCannot continue";
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
        BulbModel input = new BulbModel();
        input.setColor(retrieveColor());
        input.setLight(retrieveLuminosity());
        input.setOpacity(retrieveOpacity());
        input.setPosition(SCREWED);

        outputToTextArea(TheBulb.solve(
                input,
                TheBulbController::checkIfLightIsOff,
                TheBulbController::checkFinalLightIsOn
        ));
    }

    private BulbModel.Color retrieveColor() {
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

    private BulbModel.Light retrieveLuminosity() {
        String resultingLight = FacadeFX.getToggleName(luminosityGroup);
        return resultingLight.equals("Lit") ? ON : OFF;
    }

    private BulbModel.Opacity retrieveOpacity() {
        String resultingOpacity = FacadeFX.getToggleName(opacityGroup);
        return resultingOpacity.equals("Opaque") ? OPAQUE : TRANSLUCENT;
    }

    private void outputToTextArea(List<String> results) {
        String outputText = String.join("\n", results);
        bulbResults.setText(outputText);
    }

    private static boolean checkIfLightIsOff(List<String> runningInstructions) {
        var alert = constructAlert(runningInstructions, "Did the Bulb turn off when you pressed I?");

        ButtonType no = new ButtonType("No"),
                yes = new ButtonType("Yes");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(yes, no);

        return alert.showAndWait()
                .map(buttonType -> buttonType == yes)
                .orElseThrow(() -> new IllegalStateException(ERROR_TEXT));
    }

    private static boolean checkFinalLightIsOn(List<String> allInstructions) {
        var alert = constructAlert(allInstructions, "Is the final bulb now on or off?");

        ButtonType on = new ButtonType("On"),
                off = new ButtonType("Off");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(on, off);

        return alert.showAndWait()
                .map(type -> type == on)
                .orElseThrow(() -> new IllegalStateException(ERROR_TEXT));
    }

    private static Alert constructAlert(List<String> instructions, String contextQuestion) {
        var formattedInstructions = String.join(ARROW, instructions);
        return buildAlert(
                Alert.AlertType.CONFIRMATION, contextQuestion,
                formattedInstructions, "Light Confirmation"
        );
    }

    @Override
    public void reset() {
        FacadeFX.disable(submitButton);
        FacadeFX.clearText(bulbResults);
        FacadeFX.resetToggleGroups(colorGroup, opacityGroup, luminosityGroup);
    }
}
