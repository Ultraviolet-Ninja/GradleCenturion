package bomb.modules.ab.blind_alley;

import bomb.Widget;

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
        alleyCat[0][0] = convertToBool(hasUnlitIndicator(BOB)) + convertToBool(hasLitIndicator(CAR)) +
                convertToBool(hasLitIndicator(IND)) + convertToBool(getNumHolders() % 2 == 0);
    }

    private static void topMid() {
        alleyCat[0][1] = convertToBool(hasUnlitIndicator(NSA)) + convertToBool(hasLitIndicator(FRK)) +
                convertToBool(hasUnlitIndicator(CAR)) + convertToBool(doesPortExists(RJ45));
    }

    private static void left() {
        alleyCat[1][0] = convertToBool(hasUnlitIndicator(FRQ)) + convertToBool(hasUnlitIndicator(IND)) +
                convertToBool(hasUnlitIndicator(TRN)) + convertToBool(doesPortExists(DVI));
    }

    private static void middle() {
        alleyCat[1][1] = convertToBool(hasUnlitIndicator(SIG)) + convertToBool(hasUnlitIndicator(SND)) +
                convertToBool(hasLitIndicator(NSA)) + convertToBool(getAllBatteries() % 2 == 0);
    }

    private static void right() {
        alleyCat[1][2] = convertToBool(hasLitIndicator(BOB)) + convertToBool(hasLitIndicator(CLR)) +
                convertToBool(doesPortExists(PS2)) + convertToBool(doesPortExists(SERIAL));
    }

    private static void bottomLeft() {
        alleyCat[2][0] = convertToBool(hasLitIndicator(FRQ)) + convertToBool(hasLitIndicator(SIG)) +
                convertToBool(hasLitIndicator(TRN)) + convertToBool(hasEvenNumberInSerialCode());
    }

    private static void bottomMid() {
        alleyCat[2][1] = convertToBool(hasUnlitIndicator(FRK)) + convertToBool(hasLitIndicator(MSA)) +
                convertToBool(doesPortExists(PARALLEL)) + convertToBool(hasVowelInSerialCode());
    }

    private static void bottomRight() {
        alleyCat[2][2] = convertToBool(hasUnlitIndicator(CLR)) + convertToBool(hasUnlitIndicator(MSA)) +
                convertToBool(hasLitIndicator(SND)) + convertToBool(doesPortExists(RCA));
    }

    private static int convertToBool(boolean bool) {
        return bool ? 1 : 0;
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
