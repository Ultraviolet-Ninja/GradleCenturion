package bomb.modules.s.switches;

import bomb.abstractions.Resettable;
import com.jfoenix.controls.JFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.FXML;

public class SwitchesController implements Resettable {
    @FXML
    private JFXRadioButton firstLight, secondLight, thirdLight, fourthLight, fifthLight;

    @FXML
    private MFXToggleButton firstSwitch, secondSwitch, thirdSwitch, fourthSwitch, fifthSwitch;

    @FXML
    private MFXTextField outputField;

    @Override
    public void reset() {

    }
}
