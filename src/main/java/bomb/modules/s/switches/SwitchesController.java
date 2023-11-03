package bomb.modules.s.switches;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import com.jfoenix.controls.JFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.FXML;

import java.util.List;
import java.util.function.IntUnaryOperator;

import static bomb.modules.s.switches.Switches.BIT_LENGTH;
import static bomb.tools.string.StringFormat.ARROW;

public final class SwitchesController implements Resettable {
    private static final IntUnaryOperator SHIFT_BIT = index -> 1 << (BIT_LENGTH - index - 1);

    @FXML
    private JFXRadioButton firstLight, secondLight, thirdLight, fourthLight, fifthLight;

    @FXML
    private MFXToggleButton firstSwitch, secondSwitch, thirdSwitch, fourthSwitch, fifthSwitch;

    @FXML
    private MFXTextField outputField;

    @FXML
    private void submitStates() {
        List<JFXRadioButton> allLights = getAllLights();
        List<MFXToggleButton> allSwitches = getAllSwitches();
        byte startingState = 0;
        byte desiredState = 0;

        for (int i = 0; i < BIT_LENGTH; i++) {
            if (allSwitches.get(i).isSelected())
                startingState |= SHIFT_BIT.applyAsInt(i);
            if (allLights.get(i).isSelected())
                desiredState |= SHIFT_BIT.applyAsInt(i);
        }

        try {
            List<String> outputList = Switches.produceMoveList(startingState, desiredState);
            sendToOutputField(outputList);
        } catch (IllegalArgumentException illegal) {
            FacadeFX.setAlert(illegal.getMessage());
            reset();
        }
    }

    private void sendToOutputField(List<String> outputList) {
        String outputText = String.join(ARROW, outputList);
        outputField.setText(outputText);
    }

    private List<JFXRadioButton> getAllLights() {
        return List.of(firstLight, secondLight, thirdLight, fourthLight, fifthLight);
    }

    private List<MFXToggleButton> getAllSwitches() {
        return List.of(firstSwitch, secondSwitch, thirdSwitch, fourthSwitch, fifthSwitch);
    }

    @Override
    public void reset() {
        FacadeFX.clearText(outputField);
        getAllLights().forEach(button -> button.setSelected(false));
        getAllSwitches().forEach(button -> button.setSelected(false));
    }
}
