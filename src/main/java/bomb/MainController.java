package bomb;

import bomb.modules.dh.ForgetMeNot;
import bomb.modules.s.Souvenir;
import bomb.enumerations.*;
import bomb.interfaces.Reset;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static bomb.enumerations.TriState.*;
import static bomb.tools.Mechanics.NORMAL_CHAR_REGEX;
import static bomb.tools.Mechanics.NUMBER_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;

public class MainController implements Reset {
    private final ArrayList<Stage> noteWindows = new ArrayList<>(5);
    private int stageCounter = 1;
    private final ToggleGroup bobGroup = new ToggleGroup(),
            carGroup = new ToggleGroup(), clrGroup = new ToggleGroup(),
            frkGroup = new ToggleGroup(), frqGroup = new ToggleGroup(),
            indGroup = new ToggleGroup(), msaGroup = new ToggleGroup(),
            nsaGroup = new ToggleGroup(), sigGroup = new ToggleGroup(),
            sndGroup = new ToggleGroup(), trnGroup = new ToggleGroup();

    @FXML
    private Button
            dviUp, parallelUp, psUp, rjUp, serialUp, rcaUp,
            dviDown, parallelDown, psDown, rjDown, serialDown, rcaDown,
            one, two, three, four, five, six, seven, eight, nine, zero, flush, undoButton;

    @FXML
    private Label bob, car, clr, frk, frq, ind, msa, nsa, sig, snd, trn,
            aLabel, dLabel, holderLabel, plateLabel, minuteLabel, moduleLabel,
            currentStage;

    @FXML
    private Tab souvenirTab, forgetTab;

    @FXML
    private TextArea flushArea, souvenirOutput;

    @FXML
    private TextField dvi, parallel, ps, rj, serial, rca,
            doubleAField, dBatField, holderField, plateField, minField, modField, serialField,
            forgetMeOutput;

    @FXML
    private ToggleButton souvenir, forgetMeNot,
            bobLit, carLit, clrLit, frkLit, frqLit, indLit, msaLit, nsaLit, sigLit, sndLit, trnLit,
            bobUnlit, carUnlit, clrUnlit, frkUnlit, frqUnlit, indUnlit, msaUnlit, nsaUnlit, sigUnlit,
            sndUnlit, trnUnlit;

    public void initialize(){
        toggle(bobGroup, bobLit, bobUnlit);
        toggle(carGroup, carLit, carUnlit);
        toggle(clrGroup, clrLit, clrUnlit);
        toggle(frkGroup, frkLit, frkUnlit);
        toggle(frqGroup, frqLit, frqUnlit);
        toggle(indGroup, indLit, indUnlit);
        toggle(msaGroup, msaLit, msaUnlit);
        toggle(nsaGroup, nsaLit, nsaUnlit);
        toggle(sigGroup, sigLit, sigUnlit);
        toggle(sndGroup, sndLit, sndUnlit);
        toggle(trnGroup, trnLit, trnUnlit);
    }

    private void toggle(ToggleGroup select, ToggleButton...buttons){
        for (ToggleButton button : buttons){
            button.setToggleGroup(select);
        }
    }

    //Widget methods
    @FXML
    private void incrementPort(){
        if (dviUp.isHover()){
            Widget.addPort(Ports.DVI);
            updateField(dvi, Widget.getPort(Ports.DVI));
        } else if (parallelUp.isHover()){
            Widget.addPort(Ports.PARALLEL);
            updateField(parallel, Widget.getPort(Ports.PARALLEL));
        } else if (psUp.isHover()){
            Widget.addPort(Ports.PS2);
            updateField(ps, Widget.getPort(Ports.PS2));
        } else if (rjUp.isHover()){
            Widget.addPort(Ports.RJ45);
            updateField(rj, Widget.getPort(Ports.RJ45));
        } else if (serialUp.isHover()){
            Widget.addPort(Ports.SERIAL);
            updateField(serial, Widget.getPort(Ports.SERIAL));
        } else if (rcaUp.isHover()){
            Widget.addPort(Ports.RCA);
            updateField(rca, Widget.getPort(Ports.RCA));
        }
    }

    @FXML
    private void decrementPort(){
        if (dviDown.isHover()){
            Widget.subPort(Ports.DVI);
            updateField(dvi, Widget.getPort(Ports.DVI));
        } else if (parallelDown.isHover()){
            Widget.subPort(Ports.PARALLEL);
            updateField(parallel, Widget.getPort(Ports.PARALLEL));
        } else if (psDown.isHover()){
            Widget.subPort(Ports.PS2);
            updateField(ps, Widget.getPort(Ports.PS2));
        } else if (rjDown.isHover()){
            Widget.subPort(Ports.RJ45);
            updateField(rj, Widget.getPort(Ports.RJ45));
        } else if (serialDown.isHover()){
            Widget.subPort(Ports.SERIAL);
            updateField(serial, Widget.getPort(Ports.SERIAL));
        } else if (rcaDown.isHover()){
            Widget.subPort(Ports.RCA);
            updateField(rca, Widget.getPort(Ports.RCA));
        }
    }

    private void updateField(TextField field, int nextNum){
        field.setText(String.valueOf(nextNum));
    }

    @FXML
    private void setLit(){
        checkUnknown(buttonSetter(bobLit, carLit, clrLit, frkLit, frqLit, indLit, msaLit, nsaLit,
                sigLit, sndLit, trnLit, true));
    }

    @FXML
    private void setUnlit(){
        checkUnknown(buttonSetter(bobUnlit, carUnlit, clrUnlit, frkUnlit, frqUnlit, indUnlit,
                msaUnlit, nsaUnlit, sigUnlit, sndUnlit, trnUnlit, false));
    }

    private int buttonSetter(ToggleButton bob, ToggleButton car, ToggleButton clr, ToggleButton frk,
                              ToggleButton frq, ToggleButton ind, ToggleButton msa, ToggleButton nsa,
                              ToggleButton sig, ToggleButton snd, ToggleButton trn, boolean on){
        Label currentLabel = new Label();
        Indicators currentInd = Indicators.BOB;
        TriState currentState = on?ON:OFF;
        int updateCheck = -1;
        if (bob.isHover()){
            updateCheck = 0;
            currentLabel = this.bob;
        } else if (car.isHover()){
            updateCheck = 1;
            currentInd = Indicators.CAR;
            currentLabel = this.car;
        } else if (clr.isHover()){
            updateCheck = 2;
            currentInd = Indicators.CLR;
            currentLabel = this.clr;
        } else if (frk.isHover()){
            updateCheck = 3;
            currentInd = Indicators.FRK;
            currentLabel = this.frk;
        } else if (frq.isHover()){
            updateCheck = 4;
            currentInd = Indicators.FRQ;
            currentLabel = this.frq;
        } else if (ind.isHover()){
            updateCheck = 5;
            currentInd = Indicators.IND;
            currentLabel = this.ind;
        } else if (msa.isHover()){
            updateCheck = 6;
            currentInd = Indicators.MSA;
            currentLabel = this.msa;
        } else if (nsa.isHover()){
            updateCheck = 7;
            currentInd = Indicators.NSA;
            currentLabel = this.nsa;
        } else if (sig.isHover()){
            updateCheck = 8;
            currentInd = Indicators.SIG;
            currentLabel = this.sig;
        } else if (snd.isHover()){
            updateCheck = 9;
            currentInd = Indicators.SND;
            currentLabel = this.snd;
        } else if (trn.isHover()){
            updateCheck = 10;
            currentInd = Indicators.TRN;
            currentLabel = this.trn;
        }
        Widget.setIndicator(currentState, currentInd);
        currentLabel.setStyle(currentState.getLabel());
        return updateCheck;
    }

    private void checkUnknown(int check){
        switch (check){
            case 0: {
                if (unselectedGroup(bobLit, bobUnlit)) {
                    bob.setStyle(UNKNOWN.getLabel());
                    Widget.setIndicator(UNKNOWN, Indicators.BOB);
                }
            } break;
            case 1: {
                if (unselectedGroup(carLit, carUnlit)){
                    car.setStyle(UNKNOWN.getLabel());
                    Widget.setIndicator(UNKNOWN, Indicators.CAR);
                }
            } break;
            case 2: {
                if (unselectedGroup(clrLit, clrUnlit)){
                    clr.setStyle(UNKNOWN.getLabel());
                    Widget.setIndicator(UNKNOWN, Indicators.CLR);
                }
            } break;
            case 3: {
                if (unselectedGroup(frkLit, frkUnlit)){
                    frk.setStyle(UNKNOWN.getLabel());
                    Widget.setIndicator(UNKNOWN, Indicators.FRK);
                }
            } break;
            case 4: {
                if (unselectedGroup(frqLit, frqUnlit)){
                    frq.setStyle(UNKNOWN.getLabel());
                    Widget.setIndicator(UNKNOWN, Indicators.FRQ);
                }
            } break;
            case 5: {
                if (unselectedGroup(indLit, indUnlit)){
                    ind.setStyle(UNKNOWN.getLabel());
                    Widget.setIndicator(UNKNOWN, Indicators.IND);
                }
            } break;
            case 6: {
                if (unselectedGroup(msaLit, msaUnlit)){
                    msa.setStyle(UNKNOWN.getLabel());
                    Widget.setIndicator(UNKNOWN, Indicators.MSA);
                }
            } break;
            case 7: {
                if (unselectedGroup(nsaLit, nsaUnlit)){
                    nsa.setStyle(UNKNOWN.getLabel());
                    Widget.setIndicator(UNKNOWN, Indicators.NSA);
                }
            } break;
            case 8: {
                if (unselectedGroup(sigLit, sigUnlit)){
                    sig.setStyle(UNKNOWN.getLabel());
                    Widget.setIndicator(UNKNOWN, Indicators.SIG);
                }
            } break;
            case 9: {
                if (unselectedGroup(sndLit, sndUnlit)){
                    snd.setStyle(UNKNOWN.getLabel());
                    Widget.setIndicator(UNKNOWN, Indicators.SND);
                }
            } break;
            case 10: {
                if (unselectedGroup(trnLit, trnUnlit)){
                    trn.setStyle(UNKNOWN.getLabel());
                    Widget.setIndicator(UNKNOWN, Indicators.TRN);
                }
            } break;
        }
    }

    private boolean unselectedGroup(ToggleButton lit, ToggleButton unlit){
        return !lit.isSelected() && !unlit.isSelected();
    }

    @FXML
    private void doubleAInfo(){
        Widget.setDoubleAs(info(doubleAField, aLabel));
    }

    @FXML
    private void dBatInfo(){
        Widget.setDBatteries(info(dBatField, dLabel));
    }

    @FXML
    private void holderInfo(){
        Widget.setNumHolders(info(holderField, holderLabel));
    }

    @FXML
    private void plateInfo(){
        Widget.setPlates(info(plateField, plateLabel));
    }

    @FXML
    private void minuteInfo(){
        Widget.setStartTime(info(minField, minuteLabel));
    }

    @FXML
    private void modInfo(){
        Widget.setNumModules(info(modField, moduleLabel));
    }

    private int info(TextField currField, Label currLab){
        String sample = ultimateFilter(currField.getText(), NUMBER_REGEX);
        if (!sample.isEmpty()) {
            currLab.setText(sample + addNoun(currLab));
            return Integer.parseInt(sample);
        }
        currField.setText("");
        return -1;
    }

    private String addNoun(Label currLab){
        if (currLab == moduleLabel){
            return " Module(s)";
        } else if (currLab == minuteLabel){
            return " Minute(s)";
        } else if (currLab == plateLabel){
            return " Plate(s)";
        } else if (currLab == aLabel){
            return " A Bats";
        } else if (currLab == dLabel){
            return " D's";
        }
        return " Holder(s)";
    }

    @FXML
    private void serialCode(){
        Widget.setSerialCode(ultimateFilter(serialField.getText(), NORMAL_CHAR_REGEX));
        if (ultimateFilter(serialField.getText(), NORMAL_CHAR_REGEX).length() == 6)
            serialField.setDisable(true);
    }

    @FXML
    private void souvenirToggle(){
        Widget.setSouvenir(souvenir.isSelected());
        souvenirTab.setDisable(!souvenir.isSelected());
        if (souvenir.isSelected()){
            souvenir.setStyle("-fx-background-color: forestgreen; -fx-text-fill: black");
            souvenir.setText("Souvenir: Enabled");
        } else {
            souvenir.setStyle("-fx-background-color: crimson; -fx-text-fill: seashell");
            souvenir.setText("Souvenir: Disabled");
        }
    }

    @FXML
    private void forgetMeToggle(){
        Widget.setForgetMeNot(forgetMeNot.isSelected());
        forgetTab.setDisable(!forgetMeNot.isSelected());
        if (forgetMeNot.isSelected()){
            forgetMeNot.setStyle("-fx-background-color: forestgreen; -fx-text-fill: black");
            forgetMeNot.setText("Forget Me Not: Enabled");
        } else {
            forgetMeNot.setStyle("-fx-background-color: crimson; -fx-text-fill: seashell");
            forgetMeNot.setText("Forget Me Not: Disabled");
        }
    }

    @FXML @Override
    public void reset() {
        allFalse(bobLit, carLit, clrLit, frkLit, frqLit, indLit, msaLit, nsaLit, sigLit, sndLit);
        allFalse(bobUnlit, carUnlit, clrUnlit, frkUnlit, frqUnlit, indUnlit, msaUnlit, nsaUnlit, sigUnlit, sndUnlit);
        allUnknown();
        resetTexts();
        zeroValues();
        Widget.portZero(Ports.DVI);
        Widget.portZero(Ports.PARALLEL);
        Widget.portZero(Ports.PS2);
        Widget.portZero(Ports.RJ45);
        Widget.portZero(Ports.SERIAL);
        Widget.portZero(Ports.RCA);
        forgetMeNot.setSelected(false);
        souvenir.setSelected(false);
        souvenirTab.setDisable(true);
        forgetTab.setDisable(true);
        forgetMeToggle();
        souvenirToggle();
        stageCounter = 1;
        flushArea.setText("");
        forgetMeOutput.setText("");
        currentStage.setText("1");
        flush.setDisable(true);
        serialField.setDisable(false);
    }

    private void allFalse(ToggleButton bob, ToggleButton car, ToggleButton clr, ToggleButton frk, ToggleButton frq,
                          ToggleButton ind, ToggleButton msa, ToggleButton nsa, ToggleButton sig, ToggleButton snd)
    {
        bob.setSelected(false);
        car.setSelected(false);
        clr.setSelected(false);
        frk.setSelected(false);
        frq.setSelected(false);
        ind.setSelected(false);
        msa.setSelected(false);
        nsa.setSelected(false);
        sig.setSelected(false);
        snd.setSelected(false);
    }

    private void allUnknown(){
        TriState un = UNKNOWN;
        Widget.setIndicator(un, Indicators.BOB);
        Widget.setIndicator(un, Indicators.CAR);
        Widget.setIndicator(un, Indicators.CLR);
        Widget.setIndicator(un, Indicators.FRK);
        Widget.setIndicator(un, Indicators.FRQ);
        Widget.setIndicator(un, Indicators.IND);
        Widget.setIndicator(un, Indicators.MSA);
        Widget.setIndicator(un, Indicators.NSA);
        Widget.setIndicator(un, Indicators.SIG);
        Widget.setIndicator(un, Indicators.SND);
        Widget.setIndicator(un, Indicators.TRN);
        bob.setStyle(un.getLabel());
        car.setStyle(un.getLabel());
        clr.setStyle(un.getLabel());
        frk.setStyle(un.getLabel());
        frq.setStyle(un.getLabel());
        ind.setStyle(un.getLabel());
        msa.setStyle(un.getLabel());
        nsa.setStyle(un.getLabel());
        sig.setStyle(un.getLabel());
        snd.setStyle(un.getLabel());
        trn.setStyle(un.getLabel());
    }

    private void resetTexts(){
        dvi.setText("0");
        parallel.setText("0");
        ps.setText("0");
        rj.setText("0");
        serial.setText("0");
        rca.setText("0");
        aLabel.setText("");
        dLabel.setText("");
        holderLabel.setText("");
        plateLabel.setText("");
        minuteLabel.setText("");
        moduleLabel.setText("");
        serialField.setText("");
        doubleAField.setText("");
        dBatField.setText("");
        holderField.setText("");
        modField.setText("");
        minField.setText("");
        plateField.setText("");
    }

    private void zeroValues(){
        Widget.setPlates(0);
        Widget.setStartTime(0);
        Widget.setNumModules(0);
        Widget.setNumHolders(0);
        Widget.setDoubleAs(0);
        Widget.setDBatteries(0);
        Widget.setTwoFactor("");
        Widget.setSerialCode("");
    }

    @FXML
    private void addNoteWindow(){
        if (noteWindows.size() != 5) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../../resources/bomb/fxml/Note.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Extra Notes");
                stage.setScene(new Scene(root, 600, 400));
                noteWindows.add(stage);
                stage.show();
            } catch (IOException ioException){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("IO Exception");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Max Capacity reached");
            alert.showAndWait();
        }
    }

    //Forget Me Not methods
    @FXML
    private void nextNumber(){
        if (Widget.numModules != 0) {
            try {
                if (one.isHover())
                    addTo(one);
                else if (two.isHover())
                    addTo(two);
                else if (three.isHover())
                    addTo(three);
                else if (four.isHover())
                    addTo(four);
                else if (five.isHover())
                    addTo(five);
                else if (six.isHover())
                    addTo(six);
                else if (seven.isHover())
                    addTo(seven);
                else if (eight.isHover())
                    addTo(eight);
                else if (nine.isHover())
                    addTo(nine);
                else if (zero.isHover())
                    addTo(zero);
                undoButton.setDisable(false);
                writeNext();
            } catch (IllegalArgumentException illegal) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Serial Code");
                alert.setContentText(illegal.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Need to set the number of modules for this to work");
            alert.showAndWait();
        }
    }

    private void writeNext(){
        currentStage.setText(String.valueOf(++stageCounter));
        judge();
        maxCap();
    }

    private void addTo(Button button){
        int temp = Integer.parseInt(button.getText());
        ForgetMeNot.add(stageCounter, temp);
        forgetMeOutput.setText("Stage " + stageCounter + " was a " + temp);
    }

    private void judge(){
        if (stageCounter >= Widget.numModules*0.9) flush.setDisable(false);
    }

    private void maxCap(){
        if (stageCounter == Widget.numModules){
            one.setDisable(true);
            two.setDisable(true);
            three.setDisable(true);
            four.setDisable(true);
            five.setDisable(true);
            six.setDisable(true);
            seven.setDisable(true);
            eight.setDisable(true);
            nine.setDisable(true);
            zero.setDisable(true);
        }
    }

    @FXML
    private void undo(){
        if (stageCounter > 1) {
            ForgetMeNot.undo();
            forgetMeOutput.setText("Previous stage undone");
            currentStage.setText(String.valueOf(--stageCounter));
        }
        undoCheck();
    }

    private void undoCheck(){
        if (stageCounter == 1) undoButton.setDisable(true);
    }

    @FXML
    private void flushOut(){
        flushArea.setText(ForgetMeNot.flush());
        flush.setDisable(true);
        undoButton.setDisable(true);
        stageCounter = 1;
        reEnable();
    }

    private void reEnable(){
        one.setDisable(false);
        two.setDisable(false);
        three.setDisable(false);
        four.setDisable(false);
        five.setDisable(false);
        six.setDisable(false);
        seven.setDisable(false);
        eight.setDisable(false);
        nine.setDisable(false);
        zero.setDisable(false);
    }

    //Souvenir Method
    @FXML
    private void streamOut(){
        String temp = Souvenir.flush();
        if (souvenirTab.isSelected() && temp != null)
            souvenirOutput.setText(temp);
    }
}