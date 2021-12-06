package bomb.modules.s.switches;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import com.jfoenix.controls.JFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.List;

import static bomb.modules.s.switches.Switches.BIT_LENGTH;
import static bomb.tools.string.StringFormat.ARROW;

public class SwitchesController implements Resettable {
    @FXML
    private JFXRadioButton firstLight, secondLight, thirdLight, fourthLight, fifthLight;

    @FXML
    private MFXToggleButton firstSwitch, secondSwitch, thirdSwitch, fourthSwitch, fifthSwitch;

    @FXML
    private MFXTextField outputField;

    @FXML
    private void submitStates() {
        JFXRadioButton[] allLights = getAllLights();
        MFXToggleButton[] allSwitches = getAllSwitches();
        byte startingState = 0;
        byte desiredState = 0;

        for (int i = 0; i < BIT_LENGTH; i++) {
            if (allSwitches[i].isSelected())
                startingState |= 1 << (BIT_LENGTH - i - 1);
            if (allLights[i].isSelected())
                desiredState |= 1 << (BIT_LENGTH - i - 1);
        }

        try {
            List<String> outputList = Switches.produceMoveList(startingState, desiredState);
            sendToOutputField(outputList);
        } catch (IllegalArgumentException illegal) {
            FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage());
            reset();
        }
    }

    private void sendToOutputField(List<String> outputList) {
        String outputText = String.join(ARROW, outputList);
        outputField.setText(outputText);
    }

    private JFXRadioButton[] getAllLights() {
        return new JFXRadioButton[]{firstLight, secondLight, thirdLight, fourthLight, fifthLight};
    }

    private MFXToggleButton[] getAllSwitches() {
        return new MFXToggleButton[]{firstSwitch, secondSwitch, thirdSwitch, fourthSwitch, fifthSwitch};
    }

    @Override
    public void reset() {
        FacadeFX.clearText(outputField);
        FacadeFX.setToggleButtonsUnselected(getAllSwitches());
        FacadeFX.setToggleButtonsUnselected(getAllLights());
    }
}
