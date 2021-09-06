package bomb.modules.s.switches;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import com.jfoenix.controls.JFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.List;

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

        for (int i = 0; i < Switches.BIT_LENGTH; i++) {
            if (allSwitches[i].isSelected())
                startingState |= 1 << i;
            if (allLights[i].isSelected())
                desiredState |= 1 << i;
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
        String arrow = " -> ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < outputList.size(); i++) {
            sb.append(outputList.get(i));
            if (i != outputList.size() - 1) sb.append(arrow);
        }
        outputField.setText(sb.toString());
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
