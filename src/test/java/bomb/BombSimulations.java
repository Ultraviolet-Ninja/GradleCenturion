package bomb;

import static bomb.enumerations.Indicator.BOB;
import static bomb.enumerations.Indicator.FRK;
import static bomb.enumerations.Indicator.MSA;
import static bomb.enumerations.Indicator.SND;
import static bomb.enumerations.Port.DVI;
import static bomb.enumerations.Port.PARALLEL;
import static bomb.enumerations.Port.PS2;
import static bomb.enumerations.Port.RCA;
import static bomb.enumerations.Port.RJ45;
import static bomb.enumerations.Port.SERIAL;
import static bomb.enumerations.TrinarySwitch.OFF;
import static bomb.enumerations.TrinarySwitch.ON;

public class BombSimulations {
    public static final int CENTURION_MODULE_NUM = 101, CENTURION_START_TIME = 100,
            PRAETORIAN_START_TIME = 160, PRAETORIAN_MODULE_NUM = 161,
            SOUVENIR_ARMY_MODULE_NUM = 79, SOUVENIR_ARMY_START_TIME = 120;

    public static void thanksBobCenturion() {
        Widget.resetProperties();
        Widget.setDoubleAs(4);
        Widget.setNumHolders(2);
        Widget.setIndicator(ON, BOB);
        Widget.setSerialCode("ag42w5");
        centurionDefaults();
    }

    /**
     * The Great Berate's first Centurion widget details
     * https://www.youtube.com/watch?v=ixlWDV0CVmM&t=4537s
     */
    public static void theGreatBerateVideoOne() {
        Widget.resetProperties();
        Widget.setSerialCode("th8zk9");
        Widget.setPortValue(PARALLEL, 1);
        Widget.setNumberOfPlates(1);
        Widget.setDoubleAs(4);
        Widget.setDBatteries(1);
        Widget.setNumHolders(3);
        Widget.setIndicator(ON, MSA);
        centurionDefaults();
    }

    /**
     * The Great Berate's second Centurion widget details
     * https://www.youtube.com/watch?v=dAJp9nRgIbM
     */
    public static void theGreatBerateVideoTwo() {
        Widget.resetProperties();
        Widget.setNumberOfPlates(2);
        Widget.setIndicator(ON, MSA);
        Widget.setNumHolders(2);
        Widget.setDBatteries(1);
        Widget.setDoubleAs(2);
        Widget.setSerialCode("h64gz8");
        Widget.setPortValue(SERIAL, 1);
        Widget.setPortValue(PARALLEL, 1);
        Widget.setPortValue(RCA, 1);
        Widget.setPortValue(PS2, 1);
        Widget.setPortValue(RJ45, 1);
        Widget.setPortValue(DVI, 1);
        centurionDefaults();
    }

    public static void videoTwoTakeTwo() {
        Widget.resetProperties();
        Widget.setDBatteries(2);
        Widget.setNumHolders(2);
        Widget.setIndicator(ON, SND);
        Widget.setIndicator(OFF, FRK);
        Widget.setSerialCode("ah5nj9");
        Widget.setPortValue(RCA, 1);
        Widget.setNumberOfPlates(1);
        centurionDefaults();
    }

    public static void videoTwoTakeThree() {
        Widget.resetProperties();

        centurionDefaults();
    }

    public static void centurionDefaults() {
        Widget.setStartTime(CENTURION_START_TIME);
        Widget.setNumModules(CENTURION_MODULE_NUM);
    }
}
