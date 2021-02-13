package bomb.modules.ab.blind_alley;

import bomb.enumerations.Ports;
import bomb.Widget;

import static bomb.enumerations.Indicators.*;

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

    private static void topLeft(){
        alleyCat[0][0] = convertBool(hasUnlitIndicator(BOB)) + convertBool(hasLitIndicator(CAR)) +
                convertBool(hasLitIndicator(IND)) + convertBool(getNumHolders() % 2 == 0);
    }

    private static void topMid(){
        alleyCat[0][1] = convertBool(hasUnlitIndicator(NSA)) + convertBool(hasLitIndicator(FRK)) +
                convertBool(hasUnlitIndicator(CAR)) + convertBool(portExists(Ports.RJ45));
    }

    private static void left(){
        alleyCat[1][0] = convertBool(hasUnlitIndicator(FRQ)) + convertBool(hasUnlitIndicator(IND)) +
                convertBool(hasUnlitIndicator(TRN)) + convertBool(portExists(Ports.DVI));
    }

    private static void middle(){
        alleyCat[1][1] = convertBool(hasUnlitIndicator(SIG)) + convertBool(hasUnlitIndicator(SND)) +
                convertBool(hasLitIndicator(NSA)) + convertBool(getAllBatteries() % 2 == 0);
    }

    private static void right(){
        alleyCat[1][2] = convertBool(hasLitIndicator(BOB)) + convertBool(hasLitIndicator(CLR)) +
                convertBool(portExists(Ports.PS2)) + convertBool(portExists(Ports.SERIAL));
    }

    private static void bottomLeft(){
        alleyCat[2][0] = convertBool(hasLitIndicator(FRQ)) + convertBool(hasLitIndicator(SIG)) +
                convertBool(hasLitIndicator(TRN)) + convertBool(hasEven() == 0);
    }

    private static void bottomMid(){
        alleyCat[2][1] = convertBool(hasUnlitIndicator(FRK)) + convertBool(hasLitIndicator(MSA)) +
                convertBool(portExists(Ports.PARALLEL)) + convertBool(hasVowel());
    }

    private static void bottomRight(){
        alleyCat[2][2] = convertBool(hasUnlitIndicator(CLR)) + convertBool(hasUnlitIndicator(MSA)) +
                convertBool(hasLitIndicator(SND)) + convertBool(portExists(Ports.RCA));
    }

    private static int convertBool(boolean bool){
        return bool ? 1 : 0;
    }

    /**
     * Exports the information of which button to press
     *
     * @return A 2D array with the information
     */
    public static int[][] getAlleyCat(){
        return alleyCat;
    }

    public static void reset(){
        alleyCat = new int[3][3];
    }
}
