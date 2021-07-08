package bomb;

import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TriState;
import bomb.tools.FacadeFX;
import bomb.tools.Filter;
import bomb.tools.observer.ObserverHub;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;

import static bomb.enumerations.Port.*;
import static bomb.enumerations.TriState.*;
import static bomb.tools.observer.ObserverHub.ObserverIndex.*;

public class WidgetController {
    @FXML
    private JFXSlider dviPortSlider, parallelPortSlider, psPortSlider,
            rjPortSlider, serialPortSlider, rcaPortSlider;

    @FXML
    private JFXSlider doubleABatteries, dBatteries, batteryHolders, portPlates;

    @FXML
    private JFXToggleButton forgetMeNot, souvenir;

    @FXML
    private JFXTextArea serialCodeArea, numberOfMinutesArea, numberOfModulesArea;

    @FXML
    private ToggleGroup bobGroup, carGroup, clrGroup, frkGroup, frqGroup, indGroup, msaGroup,
            nsaGroup, sigGroup, sndGroup, trnGroup;

    public void initialize(){
        portSliderInitialize();
        widgetPageReset();
        setTextAreaNumbersOnly();
    }

    private void portSliderInitialize(){
        EventHandler<MouseEvent> handler = event -> {
            JFXSlider source = (JFXSlider) event.getSource();
            Widget.setPortValue(determineWhichSliderEvent(source), (int) source.getValue());
        };
        dviPortSlider.setOnMouseClicked(handler);
        parallelPortSlider.setOnMouseClicked(handler);
        psPortSlider.setOnMouseClicked(handler);
        rjPortSlider.setOnMouseClicked(handler);
        serialPortSlider.setOnMouseClicked(handler);
        rcaPortSlider.setOnMouseClicked(handler);
    }

    private Port determineWhichSliderEvent(JFXSlider source){
        if (source == dviPortSlider) return DVI;
        if (source == parallelPortSlider) return PARALLEL;
        if (source == psPortSlider) return PS2;
        if (source == rjPortSlider) return RJ45;
        if (source == serialPortSlider) return SERIAL;
        return RCA;
    }

    private void setTextAreaNumbersOnly(){
        Consumer<JFXTextArea> handler = WidgetEventFactory.createNumbersOnlyTextArea();
        numberOfMinutesArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && !numberOfMinutesArea.getText().isEmpty()){
                handler.accept(numberOfMinutesArea);
            }
        });
        numberOfModulesArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && !numberOfModulesArea.getText().isEmpty()){
                handler.accept(numberOfModulesArea);
            }
        });
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
        TriState state = determineState(group.getSelectedToggle());
        Widget.setIndicator(state, indicator);
    }

    private TriState determineState(Toggle selected){
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
        Widget.setPlates((int) portPlates.getValue());
    }

    @FXML
    private void detectSerialCodeAreaChange(){
        String serialCode = serialCodeArea.getText();
        Filter.SERIAL_CODE_PATTERN.loadText(serialCode);
        if (Filter.SERIAL_CODE_PATTERN.matchesRegex()){
            Widget.setSerialCode(serialCode);
            FacadeFX.disable(serialCodeArea);
        }
    }

    @FXML
    private void souvenirToggle() {
        Widget.setSouvenir(souvenir.isSelected());
        ObserverHub.updateAtIndex(SOUVENIR);
    }

    @FXML
    private void forgetMeToggle() {
        Widget.setForgetMeNot(forgetMeNot.isSelected());
        ObserverHub.updateAtIndex(FORGET_ME_NOT);
    }

    @FXML
    private void fullBombReset(){
        widgetPageReset();
        FacadeFX.enable(serialCodeArea);
        forgetMeNot.setSelected(false);
        souvenir.setSelected(false);
        forgetMeToggle();
        souvenirToggle();
        unselectPortToggles();
        Widget.resetProperties();
        ObserverHub.updateAtIndex(RESET);
    }

    private void widgetPageReset(){
        portSliderReset();
        everythingElseReset();
        textAreaReset();
    }

    private void portSliderReset(){
        FacadeFX.resetSliderValues(
                dviPortSlider, parallelPortSlider, psPortSlider,
                rjPortSlider, serialPortSlider, rcaPortSlider
        );
    }

    private void textAreaReset(){
        FacadeFX.clearMultipleTextFields(serialCodeArea, numberOfMinutesArea, numberOfModulesArea);
    }

    private void unselectPortToggles(){
        FacadeFX.unselectFromMultipleToggleGroup(
                bobGroup, carGroup, clrGroup, frkGroup, frqGroup, indGroup,
                msaGroup, nsaGroup, sigGroup, sndGroup, trnGroup
        );
    }

    private void everythingElseReset(){
        FacadeFX.resetSliderValues(doubleABatteries, dBatteries, batteryHolders, portPlates);
    }
}
