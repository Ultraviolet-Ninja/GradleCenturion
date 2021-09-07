package bomb.modules.m.microcontroller;

import bomb.abstractions.Resettable;
import bomb.components.microcontroller.AbstractChipComponent;
import bomb.components.microcontroller.EightPinController;
import bomb.components.microcontroller.SixPinController;
import bomb.components.microcontroller.TenPinController;
import bomb.modules.m.microcontroller.chip.AbstractController;
import bomb.modules.m.microcontroller.chip.CountdownController;
import bomb.modules.m.microcontroller.chip.DiodeController;
import bomb.modules.m.microcontroller.chip.ExplodeController;
import bomb.modules.m.microcontroller.chip.StrikeController;
import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.pattern.factory.TextFormatterFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class MicroControllerController implements Resettable {
    @FXML
    private MFXButton submitButton;

    @FXML
    private Pane chipBackground;

    @FXML
    private MFXTextField moduleSerialCodeInput;

    @FXML
    private ToggleGroup controllerGroup, pinCountGroup;

    public void initialize() {
        moduleSerialCodeInput.setTextFormatter(TextFormatterFactory.createTwoDigitTextFormatter());
    }

    @FXML
    private void toggleSubmitButton() {
        boolean isControllerSelected = controllerGroup.getSelectedToggle() != null;
        boolean isPinCountSelected = pinCountGroup.getSelectedToggle() != null;
        boolean areTwoDigitEntered = moduleSerialCodeInput.getText().length() == 2;

        if (isControllerSelected && isPinCountSelected && areTwoDigitEntered) {
            FacadeFX.enable(submitButton);
            return;
        }
        FacadeFX.disable(submitButton);
        chipBackground.getChildren().clear();
    }

    @FXML
    private void submitInfo() {
        int pinCount = getPinCount();
        AbstractChipComponent currentChip = createFrontEndChip(pinCount);
        AbstractController internalController = getControllerType(pinCount);
        MicroController.setInternalController(internalController);

        String moduleSerialNumbers = moduleSerialCodeInput.getText();

        try {
            currentChip.setColors(MicroController.getPinColors(moduleSerialNumbers));
            currentChip.setChipType(internalController.acronym);
            chipBackground.getChildren().clear();
            chipBackground.getChildren().add(currentChip);
        } catch (IllegalArgumentException illegal) {
            FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage());
        }
    }

    private int getPinCount() {
        String count = FacadeFX.getToggleName(pinCountGroup).replace("-Pin", "");
        return Integer.parseInt(count);
    }

    private AbstractController getControllerType(int pinCount) {
        String controllerType = FacadeFX.getToggleName(controllerGroup);
        return switch (controllerType) {
            case StrikeController.ACRONYM -> new StrikeController(pinCount);
            case DiodeController.ACRONYM -> new DiodeController(pinCount);
            case ExplodeController.ACRONYM -> new ExplodeController(pinCount);
            default -> new CountdownController(pinCount);
        };
    }

    private AbstractChipComponent createFrontEndChip(int pinCount) {
        return switch (pinCount) {
            case SixPinController.PIN_COUNT -> new SixPinController();
            case EightPinController.PIN_COUNT -> new EightPinController();
            default -> new TenPinController();
        };
    }

    @Override
    public void reset() {
        chipBackground.getChildren().clear();
        FacadeFX.resetToggleGroup(controllerGroup);
        FacadeFX.resetToggleGroup(pinCountGroup);
        FacadeFX.clearText(moduleSerialCodeInput);
    }
}
