package bomb.modules.c.colored_switches;

import bomb.abstractions.Resettable;
import bomb.tools.data.structures.ring.ReadOnlyRing;
import com.jfoenix.controls.JFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

import static bomb.modules.c.colored_switches.SwitchColor.BLUE;
import static bomb.modules.c.colored_switches.SwitchColor.CYAN;
import static bomb.modules.c.colored_switches.SwitchColor.GREEN;
import static bomb.modules.c.colored_switches.SwitchColor.ORANGE;
import static bomb.modules.c.colored_switches.SwitchColor.PURPLE;
import static bomb.modules.c.colored_switches.SwitchColor.RED;

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

    public ColoredSwitchController() {

    }

    public void initialize() {

    }

    private ReadOnlyRing<SwitchColor> createColorCycle() {
        return new ReadOnlyRing<>(RED, ORANGE, GREEN, CYAN, BLUE, PURPLE);
    }

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
