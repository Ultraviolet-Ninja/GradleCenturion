package bomb.modules.c.colored_switches;

import bomb.abstractions.Resettable;
import bomb.tools.data.structures.ring.ArrayRing;
import bomb.tools.pattern.facade.FacadeFX;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;

import java.util.List;

import static bomb.modules.c.colored_switches.SwitchColor.BLUE;
import static bomb.modules.c.colored_switches.SwitchColor.CYAN;
import static bomb.modules.c.colored_switches.SwitchColor.GREEN;
import static bomb.modules.c.colored_switches.SwitchColor.MAGENTA;
import static bomb.modules.c.colored_switches.SwitchColor.NEUTRAL;
import static bomb.modules.c.colored_switches.SwitchColor.ORANGE;
import static bomb.modules.c.colored_switches.SwitchColor.RED;
import static bomb.tools.string.StringFormat.ARROW;

public class ColoredSwitchController implements Resettable {
    private final ArrayRing<SwitchColor> firstButtonRing, secondButtonRing, thirdButtonRing, fourthButtonRing,
            fifthButtonRing;

    @FXML
    private JFXRadioButton firstOnLight, secondOnLight, thirdOnLight, fourthOnLight, fifthOnLight;

    @FXML
    private JFXRadioButton firstOffLight, secondOffLight, thirdOffLight, fourthOffLight, fifthOffLight;

    @FXML
    private JFXToggleButton colorModeButton;

    @FXML
    private MFXButton preemptiveMoveSubmitButton, finalMoveSubmitButton;

    @FXML
    private MFXToggleButton firstSwitch, secondSwitch, thirdSwitch, fourthSwitch, fifthSwitch;

    @FXML
    private ToggleGroup firstLightGroup, secondLightGroup, thirdLightGroup, fourthLightGroup, fifthLightGroup;

    @FXML
    private MFXTextField preemptiveOutputField, finalMoveOutputField;

    public ColoredSwitchController() {
        firstButtonRing = createColorCycle();
        secondButtonRing = createColorCycle();
        thirdButtonRing = createColorCycle();
        fourthButtonRing = createColorCycle();
        fifthButtonRing = createColorCycle();
    }

    private ArrayRing<SwitchColor> createColorCycle() {
        return new ArrayRing<>(NEUTRAL, RED, ORANGE, GREEN, CYAN, BLUE, MAGENTA);
    }

    public void initialize() {
        colorModeButton.setOnAction(setColorModeAction());
        setSwitchActions();
    }

    private EventHandler<ActionEvent> setColorModeAction() {
        return event -> {
            JFXToggleButton source = (JFXToggleButton) event.getSource();
            if (source.isSelected()) {
                source.setText("Color Selector Mode");
                FacadeFX.disableMultiple(preemptiveMoveSubmitButton, finalMoveSubmitButton);
                return;
            }
            source.setText("Normal Switch Flip");
            FacadeFX.enable(preemptiveMoveSubmitButton);
            if (ColoredSwitches.isFirstStepDone())
                FacadeFX.enable(finalMoveSubmitButton);
        };
    }

    private void setSwitchActions() {
        MFXToggleButton[] buttonArray = getAllSwitches();
        ArrayRing<SwitchColor>[] rings = getAssociatedRings();

        for (int i = 0; i < buttonArray.length; i++) {
            createSwitchAction(buttonArray[i], rings[i]);
        }
    }

    private void createSwitchAction(MFXToggleButton toggleSwitch, ArrayRing<SwitchColor> associatedRing) {
        EventHandler<ActionEvent> action = event -> {
            if (colorModeButton.isSelected()) {
                MFXToggleButton source = (MFXToggleButton) event.getSource();
                associatedRing.rotateClockwise();
                if (associatedRing.getHeadData() == NEUTRAL)
                    associatedRing.rotateClockwise();
                source.setId(associatedRing.getHeadData().getCssId());
                source.applyCss();
                source.setSelected(!source.isSelected());
                ColoredSwitches.reset();
                detectRadioButtonChanges();
            }
        };

        toggleSwitch.setOnAction(action);
    }

    private JFXRadioButton[] getOnLights() {
        return new JFXRadioButton[]{firstOnLight, secondOnLight, thirdOnLight, fourthOnLight, fifthOnLight};
    }

    private JFXRadioButton[] getOffLights() {
        return new JFXRadioButton[]{firstOffLight, secondOffLight, thirdOffLight, fourthOffLight, fifthOffLight};
    }

    private MFXToggleButton[] getAllSwitches() {
        return new MFXToggleButton[]{firstSwitch, secondSwitch, thirdSwitch, fourthSwitch, fifthSwitch};
    }

    @SuppressWarnings("unchecked")
    private ArrayRing<SwitchColor>[] getAssociatedRings() {
        return new ArrayRing[]{firstButtonRing, secondButtonRing, thirdButtonRing, fourthButtonRing,
                fifthButtonRing};
    }

    @FXML
    private void getPreemptiveMoveList() {
        byte startingState = 0;
        MFXToggleButton[] switches = getAllSwitches();
        for (int i = 0; i < switches.length; i++) {
            if (switches[i].isSelected())
                startingState |= 1 << (switches.length - 1 - i);
        }
        try {
            List<String> resultingSwitches = ColoredSwitches.producePreemptiveMoveList(startingState);
            sendToOutputField(preemptiveOutputField, resultingSwitches);
            detectRadioButtonChanges();
            enableFinalMoveButton();
        } catch (IllegalArgumentException illegal) {
            FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage());
        }
    }

    @FXML
    private void enableFinalMoveButton() {
        boolean allGroupsHaveSelectedToggle = firstLightGroup.getSelectedToggle() != null &&
                secondLightGroup.getSelectedToggle() != null && thirdLightGroup.getSelectedToggle() != null &&
                fourthLightGroup.getSelectedToggle() != null && fifthLightGroup.getSelectedToggle() != null;

        finalMoveSubmitButton.setDisable(!(allGroupsHaveSelectedToggle && ColoredSwitches.isFirstStepDone()));
    }

    @FXML
    private void getFinalMoveList() {
        byte desiredState = 0;
        JFXRadioButton[] onLights = getOnLights();
        for (int i = 0; i < onLights.length; i++) {
            if (onLights[i].isSelected())
                desiredState |= 1 << (onLights.length - 1 - i);
        }

        try {
            SwitchColor[] switchColorArray = getCurrentSwitchColors();
            List<String> resultingSwitches = ColoredSwitches.produceFinalMoveList(switchColorArray, desiredState);
            sendToOutputField(finalMoveOutputField, resultingSwitches);
        } catch (IllegalArgumentException | IllegalStateException illegal) {
            FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage());
        }
    }

    private SwitchColor[] getCurrentSwitchColors() {
        ArrayRing<SwitchColor>[] rings = getAssociatedRings();
        SwitchColor[] output = new SwitchColor[rings.length];

        for (int i = 0; i < rings.length; i++) {
            output[i] = rings[i].getHeadData();
        }

        return output;
    }

    private void sendToOutputField(MFXTextField field, List<String> outputList) {
        String output = String.join(ARROW, outputList);
        field.setText(output);
    }

    private void detectRadioButtonChanges() {
        if (ColoredSwitches.isFirstStepDone()) {
            FacadeFX.enableMultiple(getOnLights());
            FacadeFX.enableMultiple(getOffLights());
            return;
        }
        FacadeFX.disableMultiple(getOnLights());
        FacadeFX.disableMultiple(getOffLights());
    }

    @Override
    public void reset() {
        resetRings();
        resetSwitches();
        ColoredSwitches.reset();
        detectRadioButtonChanges();
        FacadeFX.disable(finalMoveSubmitButton);
        FacadeFX.clearMultipleTextFields(preemptiveOutputField, finalMoveOutputField);
        FacadeFX.unselectFromMultipleToggleGroup(firstLightGroup, secondLightGroup, thirdLightGroup, fourthLightGroup,
                fifthLightGroup);
    }

    private void resetSwitches() {
        MFXToggleButton[] buttons = getAllSwitches();
        FacadeFX.setToggleButtonsUnselected(buttons);
        for (MFXToggleButton button : buttons) {
            button.setId(NEUTRAL.getCssId());
            button.applyCss();
        }
    }

    private void resetRings() {
        for (ArrayRing<SwitchColor> ring : getAssociatedRings()) {
            ring.reset();
        }
    }
}
