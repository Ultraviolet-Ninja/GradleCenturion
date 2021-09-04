package bomb.modules.ab.blind_alley;

import bomb.Widget;
import bomb.tools.Bit;

import static bomb.enumerations.Indicator.BOB;
import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.CLR;
import static bomb.enumerations.Indicator.FRK;
import static bomb.enumerations.Indicator.FRQ;
import static bomb.enumerations.Indicator.IND;
import static bomb.enumerations.Indicator.MSA;
import static bomb.enumerations.Indicator.NSA;
import static bomb.enumerations.Indicator.SIG;
import static bomb.enumerations.Indicator.SND;
import static bomb.enumerations.Indicator.TRN;
import static bomb.enumerations.Port.DVI;
import static bomb.enumerations.Port.PARALLEL;
import static bomb.enumerations.Port.PS2;
import static bomb.enumerations.Port.RCA;
import static bomb.enumerations.Port.RJ45;
import static bomb.enumerations.Port.SERIAL;

/**
 * This class works on the Blind Alley module, updating the internal 2-D array whenever
 * changes to specific widgets are made and exporting that info whenever the user displays
 * the 'Blind Alley' tab pane. Blind Alley in a widget-dependant module that require you to press
 * specific sections of the module based on the indicators, LED's, ports and batteries are on the bomb.
 */
public class BlindAlley extends Widget {
    private static int[][] alleyCat = new int[3][3];

    /**
     * Updates all fields every time an appropriate change is made to the edgework
     */
    public static void alleyUpdate() {
        topLeft();
        topMid();
        left();
        middle();
        right();
        bottomLeft();
        bottomMid();
        bottomRight();
    }

    private static void topLeft() {
        alleyCat[0][0] = Bit.convertBoolToInt(hasUnlitIndicator(BOB)) + Bit.convertBoolToInt(hasLitIndicator(CAR)) +
                Bit.convertBoolToInt(hasLitIndicator(IND)) + Bit.convertBoolToInt(getNumHolders() % 2 == 0);
    }

    private static void topMid() {
        alleyCat[0][1] = Bit.convertBoolToInt(hasUnlitIndicator(NSA)) + Bit.convertBoolToInt(hasLitIndicator(FRK)) +
                Bit.convertBoolToInt(hasUnlitIndicator(CAR)) + Bit.convertBoolToInt(portExists(RJ45));
    }

    private static void left() {
        alleyCat[1][0] = Bit.convertBoolToInt(hasUnlitIndicator(FRQ)) + Bit.convertBoolToInt(hasUnlitIndicator(IND)) +
                Bit.convertBoolToInt(hasUnlitIndicator(TRN)) + Bit.convertBoolToInt(portExists(DVI));
    }

    private static void middle() {
        alleyCat[1][1] = Bit.convertBoolToInt(hasUnlitIndicator(SIG)) + Bit.convertBoolToInt(hasUnlitIndicator(SND)) +
                Bit.convertBoolToInt(hasLitIndicator(NSA)) + Bit.convertBoolToInt(getAllBatteries() % 2 == 0);
    }

    private static void right() {
        alleyCat[1][2] = Bit.convertBoolToInt(hasLitIndicator(BOB)) + Bit.convertBoolToInt(hasLitIndicator(CLR)) +
                Bit.convertBoolToInt(portExists(PS2)) + Bit.convertBoolToInt(portExists(SERIAL));
    }

    private static void bottomLeft() {
        alleyCat[2][0] = Bit.convertBoolToInt(hasLitIndicator(FRQ)) + Bit.convertBoolToInt(hasLitIndicator(SIG)) +
                Bit.convertBoolToInt(hasLitIndicator(TRN)) + Bit.convertBoolToInt(hasEven() == 0);
    }

    private static void bottomMid() {
        alleyCat[2][1] = Bit.convertBoolToInt(hasUnlitIndicator(FRK)) + Bit.convertBoolToInt(hasLitIndicator(MSA)) +
                Bit.convertBoolToInt(portExists(PARALLEL)) + Bit.convertBoolToInt(hasVowel());
    }

    private static void bottomRight() {
        alleyCat[2][2] = Bit.convertBoolToInt(hasUnlitIndicator(CLR)) + Bit.convertBoolToInt(hasUnlitIndicator(MSA)) +
                Bit.convertBoolToInt(hasLitIndicator(SND)) + Bit.convertBoolToInt(portExists(RCA));
    }

    /**
     * Exports the information of which button to press
     *
     * @return A 2D array with the information
     */
    public static int[][] getAlleyCat() {
        return alleyCat;
    }

    public static void reset() {
        alleyCat = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    }
}
