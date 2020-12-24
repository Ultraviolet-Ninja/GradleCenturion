/*
 * Author: Ultraviolet-Ninja
 * Project: Bomb Defusal Manual for Keep Talking and Nobody Explodes [Vanilla]
 * Section: Button
 */

package bomb.modules.t.translated;

import bomb.enumerations.Indicators;
import bomb.enumerations.TheButton;

/**
 * Button class deals with a button module
 */
public class Button extends TranslationCenter {
    private static final String hold = "Hold",
            tap = "Tap";

    /**
     * evaluate() sorts through the conditions of the current bomb and tells either to hold or tap the button
     *
     * @param properties - Takes in 2 properties, one color and one label
     *                   and determines what to do with the button.
     * @return - The String instruction of what to do
     */
    public static String evaluate(TheButton[] properties){
        boolean which;
        if (properties[0] == TheButton.BLUE && properties[1] == TheButton.ABORT)
            which = false;
        else if (getAllBatteries() > 1 && properties[1] == TheButton.DETONATE)
            which = true;
        else if (hasLitIndicator(Indicators.CAR))
            which = false;
        else if (getAllBatteries() > 2 && hasLitIndicator(Indicators.FRK))
            which = true;
        else if (properties[0] == TheButton.YELLOW)
            which = false;
        else which = properties[0] == TheButton.RED && properties[1] == TheButton.HOLD;

        return which ? tap : hold;
    }
}
