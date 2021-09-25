package bomb.modules.t.translated.solutions.button;

import bomb.Widget;
import bomb.enumerations.Indicator;

import static bomb.modules.t.translated.solutions.button.ButtonProperties.ABORT;
import static bomb.modules.t.translated.solutions.button.ButtonProperties.BLUE;
import static bomb.modules.t.translated.solutions.button.ButtonProperties.DETONATE;
import static bomb.modules.t.translated.solutions.button.ButtonProperties.RED;
import static bomb.modules.t.translated.solutions.button.ButtonProperties.YELLOW;

/**
 * Button class deals with a button module
 */
public class Button extends Widget {
    public static final byte COLOR_INDEX = 0, LABEL_INDEX = 1;

    private static final String HOLD = "Hold", TAP = "Tap";

    /**
     * Sorts through the conditions of the current bomb and tells either to hold or tap the button
     *
     * @param properties - Takes in 2 properties, one color and one label
     *                   and determines what to do with the button.
     * @return - The String instruction of what to do
     */
    public static String evaluate(ButtonProperties[] properties) {
        boolean which;
        if (properties[COLOR_INDEX] == BLUE && properties[LABEL_INDEX] == ABORT)
            which = false;
        else if (getAllBatteries() > 1 && properties[LABEL_INDEX] == DETONATE)
            which = true;
        else if (hasLitIndicator(Indicator.CAR))
            which = false;
        else if (getAllBatteries() > 2 && hasLitIndicator(Indicator.FRK))
            which = true;
        else if (properties[COLOR_INDEX] == YELLOW)
            which = false;
        else
            which = properties[COLOR_INDEX] == RED && properties[LABEL_INDEX] == ButtonProperties.HOLD;

        return which ? TAP : HOLD;
    }
}
