package bomb;

import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TrinarySwitch;
import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.pattern.facade.MaterialFacade;
import bomb.tools.pattern.factory.TextFormatterFactory;
import bomb.tools.pattern.factory.WidgetEventFactory;
import bomb.tools.pattern.observer.ObserverHub;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;

import static bomb.Widget.INDICATOR_ARRAY;
import static bomb.enumerations.Port.DVI;
import static bomb.enumerations.Port.PARALLEL;
import static bomb.enumerations.Port.PS2;
import static bomb.enumerations.Port.RCA;
import static bomb.enumerations.Port.RJ45;
import static bomb.enumerations.Port.SERIAL;
import static bomb.enumerations.TrinarySwitch.OFF;
import static bomb.enumerations.TrinarySwitch.ON;
import static bomb.enumerations.TrinarySwitch.UNKNOWN;
import static bomb.tools.filter.RegexFilter.SERIAL_CODE_PATTERN;
import static bomb.tools.pattern.facade.FacadeFX.GET_TOGGLE_NAME;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.FORGET_ME_NOT_TOGGLE;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.RESET;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.SOUVENIR_TOGGLE;

public final class WidgetController {
    @FXML
    private MFXSlider dviPortSlider, parallelPortSlider, psPortSlider, rjPortSlider,
            serialPortSlider, rcaPortSlider;

    @FXML
    private MFXSlider doubleABatteries, dBatteries, batteryHolders, portPlates;

    @FXML
    private MFXToggleButton forgetMeNot, souvenir;

    @FXML
    private MFXTextField serialCodeField, numberOfMinutesField, numberOfModulesField;

    @FXML
    private ToggleGroup bobGroup, carGroup, clrGroup, frkGroup, frqGroup, indGroup,
            msaGroup, nsaGroup, sigGroup, sndGroup, trnGroup;

    public void initialize() {
        injectTextFormatter();
        initializePortSliderEvent();
        initializeOtherSliderEvent();
        widgetPageReset();
        setTextFieldNumbersOnly();
        createIndicatorToggleButtonEvent();
    }

    private void initializeOtherSliderEvent() {
        doubleABatteries.setOnMouseClicked(event -> Widget.setDoubleAs((int) doubleABatteries.getValue()));
        dBatteries.setOnMouseClicked(event -> Widget.setDBatteries((int) dBatteries.getValue()));
        batteryHolders.setOnMouseClicked(event -> Widget.setNumHolders((int) batteryHolders.getValue()));
        portPlates.setOnMouseClicked(event -> Widget.setNumberOfPlates((int) portPlates.getValue()));
    }

    private void initializePortSliderEvent() {
        dviPortSlider.setOnMouseClicked(createPortSliderEvent(DVI));
        parallelPortSlider.setOnMouseClicked(createPortSliderEvent(PARALLEL));
        psPortSlider.setOnMouseClicked(createPortSliderEvent(PS2));
        rjPortSlider.setOnMouseClicked(createPortSliderEvent(RJ45));
        serialPortSlider.setOnMouseClicked(createPortSliderEvent(SERIAL));
        rcaPortSlider.setOnMouseClicked(createPortSliderEvent(RCA));
    }

    private static EventHandler<MouseEvent> createPortSliderEvent(Port port) {
        return event -> {
            MFXSlider source = (MFXSlider) event.getSource();
            Widget.setPortValue(port, (int) source.getValue());
        };
    }

    private void setTextFieldNumbersOnly() {
        Consumer<MFXTextField> handler = WidgetEventFactory.createNumbersOnlyTextArea();
        numberOfMinutesField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && !numberOfMinutesField.getText().isEmpty()) {
                handler.accept(numberOfMinutesField);
            }
        });
        numberOfModulesField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && !numberOfModulesField.getText().isEmpty()) {
                handler.accept(numberOfModulesField);
            }
        });
    }

    private void injectTextFormatter() {
        serialCodeField.setTextFormatter(TextFormatterFactory.createSerialCodeFormatter());
        numberOfMinutesField.setTextFormatter(TextFormatterFactory.createNumbersOnlyFormatter());
        numberOfModulesField.setTextFormatter(TextFormatterFactory.createNumbersOnlyFormatter());
    }

    private void createIndicatorToggleButtonEvent() {
        ToggleGroup[] groupArray = {bobGroup, carGroup, clrGroup, frkGroup, frqGroup, indGroup, msaGroup, nsaGroup,
                sigGroup, sndGroup, trnGroup};
        for (int i = 0; i < INDICATOR_ARRAY.length; i++) {
            Indicator currentIndicator = INDICATOR_ARRAY[i];
            ToggleGroup currentGroup = groupArray[i];
            groupArray[i].getToggles()
                    .forEach(toggle -> ((ToggleButton) toggle).setOnAction(
                            event -> indicatorAction(currentIndicator, currentGroup)
                    ));
        }
    }

    private static void indicatorAction(Indicator indicator, ToggleGroup group) {
        TrinarySwitch state = determineState(group.getSelectedToggle());
        Widget.setIndicator(state, indicator);
    }

    private static TrinarySwitch determineState(Toggle selected) {
        if (selected == null) return UNKNOWN;
        return GET_TOGGLE_NAME.apply(selected).equals("Lit") ? ON : OFF;
    }

    @FXML
    private void detectSerialCodeAreaChange() {
        String serialCode = serialCodeField.getText();
        if (serialCode.matches(SERIAL_CODE_PATTERN.getOriginalPattern())) {
            Widget.setSerialCode(serialCode);
        } else if (serialCode.length() == 6) {
            FacadeFX.setAlert(Alert.AlertType.INFORMATION, """
                    Given code doesn't meet serial code specs
                    (4th and 5th characters are letters, the 3rd and 6th are numbers)""");
        }
    }

    @FXML
    private void souvenirToggle() {
        Widget.setIsSouvenirActive(souvenir.isSelected());
        ObserverHub.updateAtIndex(SOUVENIR_TOGGLE);
    }

    @FXML
    private void forgetMeToggle() {
        Widget.setIsForgetMeNotActive(forgetMeNot.isSelected());
        ObserverHub.updateAtIndex(FORGET_ME_NOT_TOGGLE);
    }

    @FXML
    private void fullBombReset() {
        widgetPageReset();
        forgetMeNot.setSelected(false);
        souvenir.setSelected(false);
        forgetMeToggle();
        souvenirToggle();
        unselectPortToggles();
        Widget.resetProperties();
        ObserverHub.updateAtIndex(RESET);
    }

    private void widgetPageReset() {
        portSliderReset();
        everythingElseReset();
        textAreaReset();
    }

    private void portSliderReset() {
        MaterialFacade.resetSliderValues(
                dviPortSlider, parallelPortSlider, psPortSlider,
                rjPortSlider, serialPortSlider, rcaPortSlider
        );
    }

    private void textAreaReset() {
        FacadeFX.clearMultipleTextFields(serialCodeField, numberOfMinutesField, numberOfModulesField);
    }

    private void unselectPortToggles() {
        FacadeFX.resetToggleGroups(
                bobGroup, carGroup, clrGroup, frkGroup, frqGroup, indGroup,
                msaGroup, nsaGroup, sigGroup, sndGroup, trnGroup
        );
    }

    private void everythingElseReset() {
        MaterialFacade.resetSliderValues(doubleABatteries, dBatteries, batteryHolders, portPlates);
    }
}
