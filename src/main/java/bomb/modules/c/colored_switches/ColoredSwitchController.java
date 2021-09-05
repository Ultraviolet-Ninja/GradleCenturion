package bomb.modules.c.colored_switches;

import bomb.abstractions.Resettable;
import com.jfoenix.controls.JFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

public class ColoredSwitchController implements Resettable {
    @FXML
    private JFXRadioButton firstLight, secondLight, thirdLight, fourthLight, fifthLight, sixthLight, seventhLight,
            eighthLight, ninthLight, tenthLight;

    @FXML
    private MFXToggleButton firstSwitch, secondSwitch, thirdSwitch, fourthSwitch, fifthSwitch;

    @FXML
    private ToggleGroup firstLightGroup, secondLightGroup, thirdLightGroup, fourthLightGroup, fifthLightGroup;

    @FXML
    private MFXTextField preemptiveOutputField, outputField;

    @Override
    public void reset() {

    }

    private JFXRadioButton[] getAllLights() {
        return new JFXRadioButton[]{firstLight, secondLight, thirdLight, fourthLight, fifthLight, sixthLight, seventhLight,
                eighthLight, ninthLight, tenthLight};
    }

    private MFXToggleButton[] getAllSwitches() {
        return new MFXToggleButton[]{firstSwitch, secondSwitch, thirdSwitch, fourthSwitch, fifthSwitch};
    }
}
