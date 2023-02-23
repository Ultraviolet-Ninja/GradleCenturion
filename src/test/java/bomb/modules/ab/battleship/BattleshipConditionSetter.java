package bomb.modules.ab.battleship;

import bomb.Widget;

import static bomb.enumerations.Indicator.BOB;
import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.FRQ;
import static bomb.enumerations.Indicator.SIG;
import static bomb.enumerations.Port.PARALLEL;
import static bomb.enumerations.Port.SERIAL;
import static bomb.enumerations.TrinarySwitch.OFF;
import static bomb.enumerations.TrinarySwitch.ON;

public class BattleshipConditionSetter {
    static void setVideoEdgeworkVersionOne() {
        Widget.setSerialCode("ZB6HA2");
        Widget.setDBatteries(2);
        Widget.setDoubleAs(2);
        Widget.setNumHolders(3);
        Widget.setIndicator(ON, FRQ);
        Widget.setNumberOfPlates(1);
        Widget.setPortValue(PARALLEL, 1);
        Widget.setPortValue(SERIAL, 1);
    }

    static void setVideoEdgeworkVersionTwo() {
        Widget.setSerialCode("9X3VS6");
        Widget.setIndicator(OFF, BOB);
        Widget.setIndicator(OFF, FRQ);
        Widget.setIndicator(OFF, CAR);
        Widget.setIndicator(ON, SIG);
        Widget.setDoubleAs(2);
        Widget.setNumHolders(1);
    }
}
