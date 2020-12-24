package bomb.modules.ab;

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
    private static final int[][] alleyCat = new int[3][3];

    /**
     * Updates all fields every time an appropriate change is made to the edgework
     */
    public static void alleyUpdate() {
        //TODO - Break down
        //Top Left
        alleyCat[0][0] = (hasUnlitIndicator(BOB) ? 1 : 0) + (hasLitIndicator(CAR) ? 1 : 0) +
                (hasLitIndicator(IND) ? 1 : 0) + ((getNumHolders() % 2 == 0) ? 1 : 0);
        //Top Middle
        alleyCat[0][1] = (hasUnlitIndicator(NSA) ? 1 : 0) + (hasLitIndicator(FRK) ? 1 : 0) +
                (hasUnlitIndicator(CAR) ? 1 : 0) + (portExists(Ports.RJ45) ? 1 : 0);
        //Middle Left
        alleyCat[1][0] = (hasUnlitIndicator(FRQ) ? 1 : 0) + (hasUnlitIndicator(IND) ? 1 : 0) +
                (hasUnlitIndicator(TRN) ? 1 : 0) + (portExists(Ports.DVI) ? 1 : 0);
        //True Center
        alleyCat[1][1] = (hasUnlitIndicator(SIG) ? 1 : 0) + (hasUnlitIndicator(SND) ? 1 : 0) +
                (hasLitIndicator(NSA) ? 1 : 0) + (getAllBatteries() % 2 == 0 ? 1 : 0);
        //Middle Right
        alleyCat[1][2] = (hasLitIndicator(BOB) ? 1 : 0) + (hasLitIndicator(CLR) ? 1 : 0) +
                (portExists(Ports.PS2) ? 1 : 0) + (portExists(Ports.SERIAL) ? 1 : 0);
        //Bottom Left
        alleyCat[2][0] = (hasLitIndicator(FRQ) ? 1 : 0) + (hasLitIndicator(SIG) ? 1 : 0) +
                (hasLitIndicator(TRN) ? 1 : 0) + (hasEven() == 0 ? 1 : 0);
        //Bottom Middle
        alleyCat[2][1] = (hasUnlitIndicator(FRK) ? 1 : 0) + (hasLitIndicator(MSA) ? 1 : 0) +
                (portExists(Ports.PARALLEL) ? 1 : 0) + (hasVowel() ? 1 : 0);
        //Bottom Right
        alleyCat[2][2] = (hasUnlitIndicator(CLR) ? 1 : 0) + (hasUnlitIndicator(MSA) ? 1 : 0) +
                (hasLitIndicator(SND) ? 1 : 0) + (portExists(Ports.RCA) ? 1 : 0);
    }

    /**
     * Exports the information of which button to press
     *
     * @return A 2D array with the information
     */
    public static int[][] getAlleyCat(){
        return alleyCat;
    }
}
