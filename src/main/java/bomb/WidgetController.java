package bomb;

import bomb.enumerations.Indicator;
import bomb.enumerations.TriState;
import bomb.tools.FacadeFX;
import bomb.tools.HoverHandler;
import bomb.tools.observer.ObserverHub;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import static bomb.enumerations.TriState.*;
import static bomb.tools.Filter.ALL_CHAR_FILTER;
import static bomb.tools.Filter.NUMBER_PATTERN;

public class WidgetController {
    private static final String ENABLE_STYLE = "-fx-background-color: forestgreen; -fx-text-fill: black",
            DISABLE_STYLE = "-fx-background-color: crimson; -fx-text-fill: seashell";

    @FXML
    private Button dviUp, parallelUp, psUp, rjUp, serialUp, rcaUp,
            dviDown, parallelDown, psDown, rjDown, serialDown, rcaDown;

    @FXML
    private Label bob, car, clr, frk, frq, ind, msa, nsa, sig, snd, trn,
            aLabel, dLabel, holderLabel, plateLabel, minuteLabel, moduleLabel;

    @FXML
    private TextField dvi, parallel, ps, rj, serial, rca,
            doubleAField, dBatField, holderField, plateField, minField, modField, serialField;

    @FXML
    private ToggleButton souvenir, forgetMeNot;

    @FXML
    private ToggleGroup bobGroup, carGroup, clrGroup, frkGroup, frqGroup, indGroup, msaGroup,
            nsaGroup, sigGroup, sndGroup, trnGroup;


    public void initialize(){
        WidgetSupporter support = new WidgetSupporter(new TextField[]{dvi, parallel, ps, rj, serial, rca});
        HoverHandler<ActionEvent> portHandler = new HoverHandler<>(support.portAction());
        FacadeFX.bindHandlerToButtons(portHandler, dviUp, dviDown, parallelUp, parallelDown, psUp, psDown);
        FacadeFX.bindHandlerToButtons(portHandler, rjUp, rjDown, serialDown, serialUp, rcaUp, rcaDown);
    }

    //<editor-fold desc="To Fix">
    @FXML
    private void bobGroup(){
        indicatorAction(Indicator.BOB, bobGroup, bob);
    }

    @FXML
    private void carGroup(){
        indicatorAction(Indicator.CAR, carGroup, car);
    }

    @FXML
    private void clrGroup(){
        indicatorAction(Indicator.CLR, clrGroup, clr);
    }

    @FXML
    private void frkGroup(){
        indicatorAction(Indicator.FRK, frkGroup, frk);
    }

    @FXML
    private void frqGroup(){
        indicatorAction(Indicator.FRQ, frqGroup, frq);
    }

    @FXML
    private void indGroup(){
        indicatorAction(Indicator.IND, indGroup, ind);
    }

    @FXML
    private void msaGroup(){
        indicatorAction(Indicator.MSA, msaGroup, msa);
    }

    @FXML
    private void nsaGroup(){
        indicatorAction(Indicator.NSA, nsaGroup, nsa);
    }

    @FXML
    private void sigGroup(){
        indicatorAction(Indicator.SIG, sigGroup, sig);
    }

    @FXML
    private void sndGroup(){
        indicatorAction(Indicator.SND, sndGroup, snd);
    }

    @FXML
    private void trnGroup(){
        indicatorAction(Indicator.TRN, trnGroup, trn);
    }

    private void indicatorAction(Indicator indicator, ToggleGroup group, Label label){
        TriState state = determineState(group.getSelectedToggle());
        Widget.setIndicator(state, indicator);
        label.setStyle(state.getLabel());
    }

    private TriState determineState(Toggle selected){
        if (selected == null) return UNKNOWN;
        return ((ToggleButton)selected).getText().equals("Lit") ? ON : OFF;
    }
    //</editor-fold>

    @FXML
    private void doubleAInfo() {
        bomb.Widget.setDoubleAs(info(doubleAField, aLabel));
    }

    @FXML
    private void dBatInfo() {
        bomb.Widget.setDBatteries(info(dBatField, dLabel));
    }

    @FXML
    private void holderInfo() {
        bomb.Widget.setNumHolders(info(holderField, holderLabel));
    }

    @FXML
    private void plateInfo() {
        bomb.Widget.setPlates(info(plateField, plateLabel));
    }

    @FXML
    private void minuteInfo() {
        bomb.Widget.setStartTime(info(minField, minuteLabel));
    }

    @FXML
    private void modInfo() {
        bomb.Widget.setNumModules(info(modField, moduleLabel));
    }

    private int info(TextField currField, Label currLab) {
        NUMBER_PATTERN.loadText(currField.getText());
        String sample = NUMBER_PATTERN.toNewString();
        if (!sample.isEmpty()) {
            currLab.setText(sample + addNoun(currLab));
            return Integer.parseInt(sample);
        }
        currField.setText("");
        return -1;
    }

    private String addNoun(Label currLab){
        if (currLab == moduleLabel)
            return " Module(s)";
        else if (currLab == minuteLabel)
            return " Minute(s)";
        else if (currLab == plateLabel)
            return " Plate(s)";
        else if (currLab == aLabel)
            return " A Bats";
        else if (currLab == dLabel)
            return " D's";
        return " Holder(s)";
    }

    @FXML
    private void serialCode() {
        ALL_CHAR_FILTER.loadText(serialField.getText());
        bomb.Widget.setSerialCode(ALL_CHAR_FILTER.toNewString());
        if (ALL_CHAR_FILTER.findAllMatches().size() == 6)
            serialField.setDisable(true);
    }

    @FXML
    private void souvenirToggle() {
        bomb.Widget.setSouvenir(souvenir.isSelected());
        ObserverHub.updateAtIndex(ObserverHub.SOUVENIR_INDEX);
        souvenir.setStyle(setStyle(souvenir.isSelected()));
        souvenir.setText("Souvenir" + setEnableText(souvenir.isSelected()));
    }

    @FXML
    private void forgetMeToggle() {
        bomb.Widget.setForgetMeNot(forgetMeNot.isSelected());
        ObserverHub.updateAtIndex(ObserverHub.FORGET_ME_INDEX);
        forgetMeNot.setStyle(setStyle(forgetMeNot.isSelected()));
        forgetMeNot.setText("Forget Me Not" + setEnableText(forgetMeNot.isSelected()));
    }

    private String setStyle(boolean condition){
        return condition ? ENABLE_STYLE : DISABLE_STYLE;
    }

    private String setEnableText(boolean condition){
        return ": " + (condition ? "Enabled" : "Disabled");
    }

    @FXML
    public void reset() {
        allUnknown(bob, car, clr, frk, frq, ind, msa, nsa, sig, snd, trn);
        clearGroups(bobGroup, carGroup, clrGroup, frkGroup, frqGroup, indGroup, msaGroup, nsaGroup,
                sigGroup, sndGroup, trnGroup);
        resetTexts();
        Widget.resetProperties();
        forgetMeToggle();
        souvenirToggle();
        serialField.setDisable(false);
    }

    private void clearGroups(ToggleGroup ... groups){
        for (ToggleGroup group : groups){
            if (group.getSelectedToggle() != null)
                group.getSelectedToggle().setSelected(false);
        }
    }

    private void allUnknown(Label ... labels) {
        for (Label label : labels)
            label.setStyle(UNKNOWN.getLabel());
    }

    private void resetTexts() {
        dvi.setText("0");
        parallel.setText("0");
        ps.setText("0");
        rj.setText("0");
        serial.setText("0");
        rca.setText("0");
        FacadeFX.clearMultipleLabels(aLabel, dLabel, holderLabel, plateLabel, minuteLabel, moduleLabel);
        FacadeFX.clearMultipleTextFields(serialField, doubleAField, dBatField, holderField,
                modField, minField, plateField);
    }
}
