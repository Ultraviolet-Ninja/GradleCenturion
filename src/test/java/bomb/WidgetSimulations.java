package bomb;

import bomb.enumerations.Ports;

import static bomb.enumerations.Indicators.MSA;
import static bomb.enumerations.TriState.ON;
import static bomb.enumerations.Ports.*;

public class WidgetSimulations {
    public static final int CENTURION_MODULE_NUM = 101, CENTURION_START_TIME = 100,
            PRAETORIAN_START_TIME = 160, PRAETORIAN_MODULE_NUM = 161,
            SOUVENIR_ARMY_MODULE_NUM = 79, SOUVENIR_ARMY_START_TIME = 120;

    /**
     * The Great Berate's first Centurion widget details
     * https://www.youtube.com/watch?v=ixlWDV0CVmM&t=4537s
     */
    public static void theGreatBerate(){
        Widget.resetProperties();
        Widget.setSerialCode("th8zk9");
        Widget.addPort(Ports.PARALLEL);
        Widget.setPlates(1);
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
    public static void theGreatBerateTwo(){
        Widget.resetProperties();
        Widget.setPlates(2);
        Widget.setIndicator(ON, MSA);
        Widget.setNumHolders(2);
        Widget.setDBatteries(1);
        Widget.setDoubleAs(2);
        Widget.setSerialCode("h64gz8");
        Widget.addPort(SERIAL);
        Widget.addPort(PARALLEL);
        Widget.addPort(RCA);
        Widget.addPort(PS2);
        Widget.addPort(RJ45);
        Widget.addPort(DVI);
        centurionDefaults();
    }

    private static void centurionDefaults(){
        Widget.setStartTime(CENTURION_START_TIME);
        Widget.setNumModules(CENTURION_MODULE_NUM);
    }
}
