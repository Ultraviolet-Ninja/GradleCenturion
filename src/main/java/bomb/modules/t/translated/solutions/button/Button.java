package bomb.modules.t.translated.solutions.button;

import bomb.Widget;

import static bomb.enumerations.ButtonResult.HOLD;
import static bomb.enumerations.ButtonResult.TAP;
import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.FRK;
import static bomb.modules.t.translated.solutions.button.ButtonProperty.ABORT;
import static bomb.modules.t.translated.solutions.button.ButtonProperty.BLUE;
import static bomb.modules.t.translated.solutions.button.ButtonProperty.DETONATE;
import static bomb.modules.t.translated.solutions.button.ButtonProperty.RED;
import static bomb.modules.t.translated.solutions.button.ButtonProperty.WHITE;

/**
 * Button class deals with a button module
 */
public class Button extends Widget {
    public static final byte COLOR_INDEX = 0, LABEL_INDEX = 1;

    /**
     * Sorts through the conditions of the current bomb and tells either to hold or tap the button
     *
     * @param properties One color and one label and determines what to do with the button.
     * @return - The String instruction of what to do
     */
    public static String evaluate(ButtonProperty[] properties) throws IllegalArgumentException {
        boolean shouldTapButton;
        validateAllInput(properties);

        if (properties[LABEL_INDEX] == DETONATE)
            shouldTapButton = getAllBatteries() > 1;
        else if (properties[COLOR_INDEX] == RED && properties[LABEL_INDEX] == ButtonProperty.HOLD)
            shouldTapButton = true;
        else if ((properties[COLOR_INDEX] == BLUE && properties[LABEL_INDEX] == ABORT) ||
                (properties[COLOR_INDEX] == WHITE && hasLitIndicator(CAR)))
            shouldTapButton = false;
        else
            shouldTapButton = getAllBatteries() > 2 && hasLitIndicator(FRK);

        return "" + (shouldTapButton ? TAP : HOLD);
    }

    private static void validateAllInput(ButtonProperty[] properties) throws IllegalArgumentException {
        if (properties.length != 2)
            throw new IllegalArgumentException("Size mismatch");
        if (properties[COLOR_INDEX] == null || properties[LABEL_INDEX] == null)
            throw new IllegalArgumentException("Null instances are not allowed");
        validateColor(properties[COLOR_INDEX]);
        validateLabel(properties[LABEL_INDEX]);
    }

    private static void validateColor(ButtonProperty colorInstance) throws IllegalArgumentException {
        switch (colorInstance) {
            case DETONATE, ABORT, HOLD, PRESS -> throw new IllegalArgumentException("Invalid color");
        }
    }

    private static void validateLabel(ButtonProperty labelInstance) throws IllegalArgumentException {
        switch (labelInstance) {
            case RED, YELLOW, BLUE, WHITE -> throw new IllegalArgumentException("Invalid Label");
        }
    }
}
