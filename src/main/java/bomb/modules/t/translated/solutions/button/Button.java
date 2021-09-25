package bomb.modules.t.translated.solutions.button;

import bomb.Widget;
import bomb.enumerations.Indicator;

import static bomb.modules.t.translated.solutions.button.ButtonProperty.ABORT;
import static bomb.modules.t.translated.solutions.button.ButtonProperty.BLUE;
import static bomb.modules.t.translated.solutions.button.ButtonProperty.DETONATE;
import static bomb.modules.t.translated.solutions.button.ButtonProperty.RED;
import static bomb.modules.t.translated.solutions.button.ButtonProperty.YELLOW;

/**
 * Button class deals with a button module
 */
public class Button extends Widget {
    static final byte COLOR_INDEX = 0, LABEL_INDEX = 1;

    private static final String HOLD = "Hold", TAP = "Tap";

    /**
     * Sorts through the conditions of the current bomb and tells either to hold or tap the button
     *
     * @param properties - Takes in 2 properties, one color and one label
     *                   and determines what to do with the button.
     * @return - The String instruction of what to do
     */
    public static String evaluate(ButtonProperty[] properties) {
        boolean shouldTapButton;
        validateColor(properties[COLOR_INDEX]);
        validateLabel(properties[LABEL_INDEX]);

        if (properties[COLOR_INDEX] == BLUE && properties[LABEL_INDEX] == ABORT)
            shouldTapButton = false;
        else if (getAllBatteries() > 1 && properties[LABEL_INDEX] == DETONATE)
            shouldTapButton = true;
        else if (hasLitIndicator(Indicator.CAR))
            shouldTapButton = false;
        else if (getAllBatteries() > 2 && hasLitIndicator(Indicator.FRK))
            shouldTapButton = true;
        else if (properties[COLOR_INDEX] == YELLOW)
            shouldTapButton = false;
        else
            shouldTapButton = properties[COLOR_INDEX] == RED && properties[LABEL_INDEX] == ButtonProperty.HOLD;

        return shouldTapButton ? TAP : HOLD;
    }

    private static void validateColor(ButtonProperty colorInstance) {
        switch (colorInstance) {
            case DETONATE, ABORT, HOLD, PRESS -> throw new IllegalArgumentException("Invalid color");
        }
    }

    private static void validateLabel(ButtonProperty labelInstance) {
        switch (labelInstance) {
            case RED, YELLOW, BLUE, WHITE -> throw new IllegalArgumentException("Invalid Label");
        }
    }
}
