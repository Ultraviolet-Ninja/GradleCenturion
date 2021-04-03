package bomb;

import bomb.enumerations.Indicators;
import bomb.enumerations.Ports;
import bomb.enumerations.TriState;
import bomb.tools.FacadeFX;
import bomb.tools.observer.ObserverHub;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import static bomb.enumerations.TriState.*;
import static bomb.tools.Mechanics.*;

public class WidgetController {
    private static final String ENABLE_STYLE = "-fx-background-color: forestgreen; -fx-text-fill: black",
            DISABLE_STYLE = "-fx-background-color: crimson; -fx-text-fill: seashell";

    @FXML
    private Button
            dviUp, parallelUp, psUp, rjUp, serialUp, rcaUp,
            dviDown, parallelDown, psDown, rjDown, serialDown, rcaDown;

    @FXML
    private Label bob, car, clr, frk, frq, ind, msa, nsa, sig, snd, trn,
            aLabel, dLabel, holderLabel, plateLabel, minuteLabel, moduleLabel;

    @FXML
    private TextField dvi, parallel, ps, rj, serial, rca,
            doubleAField, dBatField, holderField, plateField, minField, modField, serialField;

    @FXML
    private ToggleButton souvenir, forgetMeNot,
            bobLit, carLit, clrLit, frkLit, frqLit, indLit, msaLit, nsaLit, sigLit, sndLit, trnLit,
            bobUnlit, carUnlit, clrUnlit, frkUnlit, frqUnlit, indUnlit, msaUnlit, nsaUnlit, sigUnlit,
            sndUnlit, trnUnlit;

    @FXML
    private void incrementPort() {
        if (dviUp.isHover()) {
            bomb.Widget.addPort(Ports.DVI);
            updateField(dvi, bomb.Widget.getPort(Ports.DVI));
        } else if (parallelUp.isHover()) {
            bomb.Widget.addPort(Ports.PARALLEL);
            updateField(parallel, bomb.Widget.getPort(Ports.PARALLEL));
        } else if (psUp.isHover()) {
            bomb.Widget.addPort(Ports.PS2);
            updateField(ps, bomb.Widget.getPort(Ports.PS2));
        } else if (rjUp.isHover()) {
            bomb.Widget.addPort(Ports.RJ45);
            updateField(rj, bomb.Widget.getPort(Ports.RJ45));
        } else if (serialUp.isHover()) {
            bomb.Widget.addPort(Ports.SERIAL);
            updateField(serial, bomb.Widget.getPort(Ports.SERIAL));
        } else if (rcaUp.isHover()) {
            bomb.Widget.addPort(Ports.RCA);
            updateField(rca, bomb.Widget.getPort(Ports.RCA));
        }
    }

    @FXML
    private void decrementPort() {
        if (dviDown.isHover()) {
            bomb.Widget.subPort(Ports.DVI);
            updateField(dvi, bomb.Widget.getPort(Ports.DVI));
        } else if (parallelDown.isHover()) {
            bomb.Widget.subPort(Ports.PARALLEL);
            updateField(parallel, bomb.Widget.getPort(Ports.PARALLEL));
        } else if (psDown.isHover()) {
            bomb.Widget.subPort(Ports.PS2);
            updateField(ps, bomb.Widget.getPort(Ports.PS2));
        } else if (rjDown.isHover()) {
            bomb.Widget.subPort(Ports.RJ45);
            updateField(rj, bomb.Widget.getPort(Ports.RJ45));
        } else if (serialDown.isHover()) {
            bomb.Widget.subPort(Ports.SERIAL);
            updateField(serial, bomb.Widget.getPort(Ports.SERIAL));
        } else if (rcaDown.isHover()) {
            bomb.Widget.subPort(Ports.RCA);
            updateField(rca, bomb.Widget.getPort(Ports.RCA));
        }
    }

    private void updateField(TextField field, int nextNum) {
        field.setText(String.valueOf(nextNum));
    }
    //FIXME - Design a quicker system
    //<editor-fold desc="To Fix">
    @FXML
    private void setLit() {
        checkUnknown(buttonSetter(bobLit, carLit, clrLit, frkLit, frqLit, indLit, msaLit, nsaLit,
                sigLit, sndLit, trnLit, true));
    }

    @FXML
    private void setUnlit() {
        checkUnknown(buttonSetter(bobUnlit, carUnlit, clrUnlit, frkUnlit, frqUnlit, indUnlit,
                msaUnlit, nsaUnlit, sigUnlit, sndUnlit, trnUnlit, false));
    }

    private int buttonSetter(ToggleButton bob, ToggleButton car, ToggleButton clr, ToggleButton frk,
                             ToggleButton frq, ToggleButton ind, ToggleButton msa, ToggleButton nsa,
                             ToggleButton sig, ToggleButton snd, ToggleButton trn, boolean on) {
        Label currentLabel = new Label();
        Indicators currentInd = Indicators.BOB;
        TriState currentState = on ? ON : OFF;
        int updateCheck = -1;
        if (bob.isHover()) {
            updateCheck = 0;
            currentLabel = this.bob;
        } else if (car.isHover()) {
            updateCheck = 1;
            currentInd = Indicators.CAR;
            currentLabel = this.car;
        } else if (clr.isHover()) {
            updateCheck = 2;
            currentInd = Indicators.CLR;
            currentLabel = this.clr;
        } else if (frk.isHover()) {
            updateCheck = 3;
            currentInd = Indicators.FRK;
            currentLabel = this.frk;
        } else if (frq.isHover()) {
            updateCheck = 4;
            currentInd = Indicators.FRQ;
            currentLabel = this.frq;
        } else if (ind.isHover()) {
            updateCheck = 5;
            currentInd = Indicators.IND;
            currentLabel = this.ind;
        } else if (msa.isHover()) {
            updateCheck = 6;
            currentInd = Indicators.MSA;
            currentLabel = this.msa;
        } else if (nsa.isHover()) {
            updateCheck = 7;
            currentInd = Indicators.NSA;
            currentLabel = this.nsa;
        } else if (sig.isHover()) {
            updateCheck = 8;
            currentInd = Indicators.SIG;
            currentLabel = this.sig;
        } else if (snd.isHover()) {
            updateCheck = 9;
            currentInd = Indicators.SND;
            currentLabel = this.snd;
        } else if (trn.isHover()) {
            updateCheck = 10;
            currentInd = Indicators.TRN;
            currentLabel = this.trn;
        }
        bomb.Widget.setIndicator(currentState, currentInd);
        currentLabel.setStyle(currentState.getLabel());
        return updateCheck;
    }
    //</editor-fold>

    private void checkUnknown(int check) {
        switch (check) {
            case 0: {
                if (unselectedGroup(bobLit, bobUnlit)) {
                    bob.setStyle(UNKNOWN.getLabel());
                    bomb.Widget.setIndicator(UNKNOWN, Indicators.BOB);
                }
            }
            break;
            case 1: {
                if (unselectedGroup(carLit, carUnlit)) {
                    car.setStyle(UNKNOWN.getLabel());
                    bomb.Widget.setIndicator(UNKNOWN, Indicators.CAR);
                }
            }
            break;
            case 2: {
                if (unselectedGroup(clrLit, clrUnlit)) {
                    clr.setStyle(UNKNOWN.getLabel());
                    bomb.Widget.setIndicator(UNKNOWN, Indicators.CLR);
                }
            }
            break;
            case 3: {
                if (unselectedGroup(frkLit, frkUnlit)) {
                    frk.setStyle(UNKNOWN.getLabel());
                    bomb.Widget.setIndicator(UNKNOWN, Indicators.FRK);
                }
            }
            break;
            case 4: {
                if (unselectedGroup(frqLit, frqUnlit)) {
                    frq.setStyle(UNKNOWN.getLabel());
                    bomb.Widget.setIndicator(UNKNOWN, Indicators.FRQ);
                }
            }
            break;
            case 5: {
                if (unselectedGroup(indLit, indUnlit)) {
                    ind.setStyle(UNKNOWN.getLabel());
                    bomb.Widget.setIndicator(UNKNOWN, Indicators.IND);
                }
            }
            break;
            case 6: {
                if (unselectedGroup(msaLit, msaUnlit)) {
                    msa.setStyle(UNKNOWN.getLabel());
                    bomb.Widget.setIndicator(UNKNOWN, Indicators.MSA);
                }
            }
            break;
            case 7: {
                if (unselectedGroup(nsaLit, nsaUnlit)) {
                    nsa.setStyle(UNKNOWN.getLabel());
                    bomb.Widget.setIndicator(UNKNOWN, Indicators.NSA);
                }
            }
            break;
            case 8: {
                if (unselectedGroup(sigLit, sigUnlit)) {
                    sig.setStyle(UNKNOWN.getLabel());
                    bomb.Widget.setIndicator(UNKNOWN, Indicators.SIG);
                }
            }
            break;
            case 9: {
                if (unselectedGroup(sndLit, sndUnlit)) {
                    snd.setStyle(UNKNOWN.getLabel());
                    bomb.Widget.setIndicator(UNKNOWN, Indicators.SND);
                }
            }
            break;
            case 10: {
                if (unselectedGroup(trnLit, trnUnlit)) {
                    trn.setStyle(UNKNOWN.getLabel());
                    bomb.Widget.setIndicator(UNKNOWN, Indicators.TRN);
                }
            }
            break;
        }
    }

    private boolean unselectedGroup(ToggleButton lit, ToggleButton unlit) {
        return !lit.isSelected() && !unlit.isSelected();
    }

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
        String sample = ultimateFilter(currField.getText(), NUMBER_REGEX);
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
        bomb.Widget.setSerialCode(ultimateFilter(serialField.getText(), NORMAL_CHAR_REGEX));
        if (ultimateFilter(serialField.getText(), NORMAL_CHAR_REGEX).length() == 6)
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
        allFalse(bobLit, carLit, clrLit, frkLit, frqLit, indLit, msaLit, nsaLit, sigLit, sndLit);
        allFalse(bobUnlit, carUnlit, clrUnlit, frkUnlit, frqUnlit, indUnlit, msaUnlit, nsaUnlit, sigUnlit, sndUnlit);
        allUnknown();
        resetTexts();
        Widget.resetProperties();
        forgetMeToggle();
        souvenirToggle();
        serialField.setDisable(false);
    }

    private void allFalse(ToggleButton bob, ToggleButton car, ToggleButton clr, ToggleButton frk, ToggleButton frq,
                          ToggleButton ind, ToggleButton msa, ToggleButton nsa, ToggleButton sig, ToggleButton snd) {
        FacadeFX.toggleNodes(false, bob, car, clr, frk, frq, ind, msa, nsa, sig, snd);
    }

    private void allUnknown() {
        bob.setStyle(UNKNOWN.getLabel());
        car.setStyle(UNKNOWN.getLabel());
        clr.setStyle(UNKNOWN.getLabel());
        frk.setStyle(UNKNOWN.getLabel());
        frq.setStyle(UNKNOWN.getLabel());
        ind.setStyle(UNKNOWN.getLabel());
        msa.setStyle(UNKNOWN.getLabel());
        nsa.setStyle(UNKNOWN.getLabel());
        sig.setStyle(UNKNOWN.getLabel());
        snd.setStyle(UNKNOWN.getLabel());
        trn.setStyle(UNKNOWN.getLabel());
    }

    private void resetTexts() {
        dvi.setText("0");
        parallel.setText("0");
        ps.setText("0");
        rj.setText("0");
        serial.setText("0");
        rca.setText("0");
        FacadeFX.clearTextMultiple(aLabel, dLabel, holderLabel, plateLabel, minuteLabel, moduleLabel);
        FacadeFX.clearTextMultiple(serialField, doubleAField, dBatField, holderField,
                modField, minField, plateField);
    }
}
