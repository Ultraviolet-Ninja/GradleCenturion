package bomb.modules.m.microcontroller;

import bomb.components.microcontroller.AbstractChip;
import bomb.components.microcontroller.EightPinController;
import bomb.components.microcontroller.SixPinController;
import bomb.components.microcontroller.TenPinController;
import bomb.abstractions.Resettable;
import bomb.modules.m.microcontroller.chip.AbstractController;
import bomb.modules.m.microcontroller.chip.CountdownController;
import bomb.modules.m.microcontroller.chip.DiodeController;
import bomb.modules.m.microcontroller.chip.ExplodeController;
import bomb.modules.m.microcontroller.chip.StrikeController;
import bomb.tools.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class MicroControllerController implements Resettable {
    private AbstractChip currentChip;
    private String controllerType = "", pinCount = "";

    @FXML
    private Button clearButton;

    @FXML
    private Pane chipBackground;

    @FXML
    private TextField serialInput;

    @FXML
    private ToggleGroup controllerGroup, pinCountGroup;

    @FXML
    private void setController(){
        controllerType = FacadeFX.getToggleName(controllerGroup);
        transferToMicro();
    }

    @FXML
    private void setPinCount(){
        pinCount = FacadeFX.getToggleName(pinCountGroup).replace("-Pin", "");
        setFrontEnd();
        transferToMicro();
    }

    @FXML
    private void trackTextField(){
        if (currentChip != null) currentChip.setChipSerialNum(serialInput.getText());
        if (serialInput.getText().length() == 2) {
            transferToMicro();
            FacadeFX.disable(serialInput);
        }
        FacadeFX.enable(clearButton);
    }

    @FXML
    private void clearText(){
        FacadeFX.clearText(serialInput);
        FacadeFX.enable(serialInput);
        FacadeFX.disable(clearButton);
    }

    private void setFrontEnd(){
        switch(pinCount){
            case "6":
                currentChip = new SixPinController();
                break;
            case "8":
                currentChip = new EightPinController();
                break;
            default:
               currentChip = new TenPinController();
        }
        chipBackground.getChildren().clear();
        chipBackground.getChildren().add(currentChip);
    }

    private void transferToMicro(){
        if (!(controllerType.isEmpty() || pinCount.isEmpty())) {
            MicroController.setController(getType());
            currentChip.setColors(MicroController.getPinColors(serialInput.getText()));
            currentChip.setChipType(controllerType);
        }
    }

    private AbstractController getType(){
        int pins = Integer.parseInt(pinCount);
        switch (controllerType){
            case "STRK": return new StrikeController(pins);
            case "LEDS": return new DiodeController(pins);
            case "EXPL": return new ExplodeController(pins);
            default: return new CountdownController(pins);
        }
    }

    @Override
    public void reset() {

    }
}
