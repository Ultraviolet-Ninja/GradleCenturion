package bomb.modules.ab.blind_alley;

import bomb.Widget;

import static bomb.enumerations.Indicator.*;
import static bomb.enumerations.Port.*;

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
                convertToBool(hasUnlitIndicator(CAR)) + convertToBool(portExists(RJ45));
    }

    private static void left() {
        alleyCat[1][0] = convertToBool(hasUnlitIndicator(FRQ)) + convertToBool(hasUnlitIndicator(IND)) +
                convertToBool(hasUnlitIndicator(TRN)) + convertToBool(portExists(DVI));
    }

    private static void middle() {
        alleyCat[1][1] = convertToBool(hasUnlitIndicator(SIG)) + convertToBool(hasUnlitIndicator(SND)) +
                convertToBool(hasLitIndicator(NSA)) + convertToBool(getAllBatteries() % 2 == 0);
    }

    private static void right() {
        alleyCat[1][2] = convertToBool(hasLitIndicator(BOB)) + convertToBool(hasLitIndicator(CLR)) +
                convertToBool(portExists(PS2)) + convertToBool(portExists(SERIAL));
    }

    private static void bottomLeft() {
        alleyCat[2][0] = convertToBool(hasLitIndicator(FRQ)) + convertToBool(hasLitIndicator(SIG)) +
                convertToBool(hasLitIndicator(TRN)) + convertToBool(hasEven() == 0);
    }

    private static void bottomMid() {
        alleyCat[2][1] = convertToBool(hasUnlitIndicator(FRK)) + convertToBool(hasLitIndicator(MSA)) +
                convertToBool(portExists(PARALLEL)) + convertToBool(hasVowel());
    }

    private static void bottomRight() {
        alleyCat[2][2] = convertToBool(hasUnlitIndicator(CLR)) + convertToBool(hasUnlitIndicator(MSA)) +
                convertToBool(hasLitIndicator(SND)) + convertToBool(portExists(RCA));
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
