/*
 * Author: Ultraviolet-Ninja
 * Project: Bomb Defusal Manual for Keep Talking and Nobody Explodes [Vanilla]
 * Section: Button
 */

package bomb.modules.t.translated;

import bomb.abstractions.ButtonType;
import bomb.enumerations.Indicator;

/**
 * Button class deals with a button module
 */
public class Button extends TranslationCenter implements ButtonType {
    private static final byte COLOR_INDEX = 0, LABEL_INDEX = 1;

    /**
     * Sorts through the conditions of the current bomb and tells either to hold or tap the button
     *
     * @param properties - Takes in 2 properties, one color and one label
     *                   and determines what to do with the button.
     * @return - The String instruction of what to do
     */
    public static String evaluate(ButtonProperties[] properties) {
        boolean which;
        if (properties[COLOR_INDEX] == ButtonProperties.BLUE && properties[LABEL_INDEX] == ButtonProperties.ABORT)
            which = false;
        else if (getAllBatteries() > 1 && properties[LABEL_INDEX] == ButtonProperties.DETONATE)
            which = true;
        else if (hasLitIndicator(Indicator.CAR))
            which = false;
        else if (getAllBatteries() > 2 && hasLitIndicator(Indicator.FRK))
            which = true;
        else if (properties[COLOR_INDEX] == ButtonProperties.YELLOW)
            which = false;
        else
            which = properties[COLOR_INDEX] == ButtonProperties.RED && properties[LABEL_INDEX] == ButtonProperties.HOLD;

        return which ? TAP : HOLD;
    }
}
