package bomb;

import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TrinarySwitch;
import bomb.tools.pattern.factory.TextFormatterFactory;
import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.filter.Filter;
import bomb.tools.pattern.factory.WidgetEventFactory;
import bomb.tools.pattern.observer.ObserverHub;
import com.jfoenix.controls.JFXSlider;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;

import static bomb.enumerations.Port.*;
import static bomb.enumerations.TrinarySwitch.*;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.*;

public class WidgetController {
    @FXML
    private JFXSlider dviPortSlider, parallelPortSlider, psPortSlider,
            rjPortSlider, serialPortSlider, rcaPortSlider;

    @FXML
    private JFXSlider doubleABatteries, dBatteries, batteryHolders, portPlates;

    @FXML
    private JFXToggleButton forgetMeNot, souvenir;

    @FXML
    private MFXTextField serialCodeField, numberOfMinutesField, numberOfModulesField;

    @FXML
    private ToggleGroup bobGroup, carGroup, clrGroup, frkGroup, frqGroup, indGroup, msaGroup,
            nsaGroup, sigGroup, sndGroup, trnGroup;

    public void initialize() {
        injectTextFormatter();
        portSliderInitialize();
        widgetPageReset();
        setTextAreaNumbersOnly();
    }

    private void initializeOtherSliderEvent() {
        doubleABatteries.setOnMouseClicked(event -> Widget.setDoubleAs((int) doubleABatteries.getValue()));
        dBatteries.setOnMouseClicked(event -> Widget.setDoubleAs((int) dBatteries.getValue()));
        batteryHolders.setOnMouseClicked(event -> Widget.setDoubleAs((int) batteryHolders.getValue()));
        portPlates.setOnMouseClicked(event -> Widget.setDoubleAs((int) portPlates.getValue()));
    }

    private void initializePortSliderEvent() {
        dviPortSlider.setOnMouseClicked(createPortSliderEvent(DVI));
        parallelPortSlider.setOnMouseClicked(createPortSliderEvent(PARALLEL));
        psPortSlider.setOnMouseClicked(createPortSliderEvent(PS2));
        rjPortSlider.setOnMouseClicked(createPortSliderEvent(RJ45));
        serialPortSlider.setOnMouseClicked(createPortSliderEvent(SERIAL));
        rcaPortSlider.setOnMouseClicked(createPortSliderEvent(RCA));
    }

    private EventHandler<MouseEvent> createPortSliderEvent(Port port) {
        return event -> {
            JFXSlider source = (JFXSlider) event.getSource();
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

    @FXML
    private void bobGroup(){
        indicatorAction(Indicator.BOB, bobGroup);
    }

    @FXML
    private void carGroup(){
        indicatorAction(Indicator.CAR, carGroup);
    }

    @FXML
    private void clrGroup(){
        indicatorAction(Indicator.CLR, clrGroup);
    }

    @FXML
    private void frkGroup(){
        indicatorAction(Indicator.FRK, frkGroup);
    }

    @FXML
    private void frqGroup(){
        indicatorAction(Indicator.FRQ, frqGroup);
    }

    @FXML
    private void indGroup(){
        indicatorAction(Indicator.IND, indGroup);
    }

    @FXML
    private void msaGroup(){
        indicatorAction(Indicator.MSA, msaGroup);
    }

    @FXML
    private void nsaGroup(){
        indicatorAction(Indicator.NSA, nsaGroup);
    }

    @FXML
    private void sigGroup(){
        indicatorAction(Indicator.SIG, sigGroup);
    }

    @FXML
    private void sndGroup(){
        indicatorAction(Indicator.SND, sndGroup);
    }

    @FXML
    private void trnGroup(){
        indicatorAction(Indicator.TRN, trnGroup);
    }

    private void indicatorAction(Indicator indicator, ToggleGroup group){
        TrinarySwitch state = determineState(group.getSelectedToggle());
        Widget.setIndicator(state, indicator);
    }

    private TrinarySwitch determineState(Toggle selected){
        if (selected == null) return UNKNOWN;
        return ((ToggleButton)selected).getText().equals("Lit") ? ON : OFF;
    }

    @FXML
    private void doubleABatterySliderChange(){
        Widget.setDoubleAs((int) doubleABatteries.getValue());
    }

    @FXML
    private void dBatterySliderChange(){
        Widget.setDBatteries((int) dBatteries.getValue());
    }

    @FXML
    private void batteryHolderSliderChange(){
        Widget.setNumHolders((int) batteryHolders.getValue());
    }

    @FXML
    private void portPlateSliderChange(){
        Widget.setNumberOfPlates((int) portPlates.getValue());
    }

    @FXML
    private void detectSerialCodeAreaChange() {
        String serialCode = serialCodeField.getText();
        Filter.SERIAL_CODE_PATTERN.loadText(serialCode);
        if (Filter.SERIAL_CODE_PATTERN.matchesRegex()) {
            Widget.setSerialCode(serialCode);
            FacadeFX.disable(serialCodeField);
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
        FacadeFX.enable(serialCodeField);
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
        FacadeFX.resetSliderValues(
                dviPortSlider, parallelPortSlider, psPortSlider,
                rjPortSlider, serialPortSlider, rcaPortSlider
        );
    }

    private void textAreaReset() {
        FacadeFX.clearMultipleTextFields(serialCodeField, numberOfMinutesField, numberOfModulesField);
    }

    private void unselectPortToggles() {
        FacadeFX.unselectFromMultipleToggleGroup(
                bobGroup, carGroup, clrGroup, frkGroup, frqGroup, indGroup,
                msaGroup, nsaGroup, sigGroup, sndGroup, trnGroup
        );
    }

    private void everythingElseReset() {
        FacadeFX.resetSliderValues(doubleABatteries, dBatteries, batteryHolders, portPlates);
    }
}
