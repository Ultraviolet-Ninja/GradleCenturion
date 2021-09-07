package bomb.modules.m.microcontroller;

import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.modules.m.microcontroller.chip.AbstractController;
import bomb.tools.filter.Regex;
import javafx.scene.paint.Color;

import java.util.List;

import static bomb.tools.filter.Filter.NUMBER_PATTERN;
import static bomb.tools.filter.Filter.ultimateFilter;

public class MicroController extends Widget {
    private static final String THIRD_CONDITION_REGEX = "[clrx18]";

    private static AbstractController instance;

    public static void setInternalController(AbstractController controller) {
        instance = controller;
    }

    public static List<Color> getPinColors(String moduleSerialNumbers) throws IllegalArgumentException {
        validateInput();
        if (containsRequiredNumbers(moduleSerialNumbers))
            return instance.traversePins(0);
        else if (hasLitIndicator(Indicator.SIG) || portExists(Port.RJ45))
            return instance.traversePins(1);
        else if (ultimateFilter(serialCode, new Regex(THIRD_CONDITION_REGEX)).length() > 0)
            return instance.traversePins(2);
        else if (numbersMatch(moduleSerialNumbers))
            return instance.traversePins(3);
        return instance.traversePins(4);
    }

    private static void validateInput() {
        serialCodeChecker();
        if (instance == null)
            throw new IllegalArgumentException("Controller Type wasn't set");
    }

    private static boolean containsRequiredNumbers(String moduleSerialNumbers) {
        NUMBER_PATTERN.loadText(moduleSerialNumbers);
        String numbers = NUMBER_PATTERN.createFilteredString();
        return numbers.contains("1") || numbers.contains("4");
    }

    private static boolean numbersMatch(String moduleSerialNumbers) {
        String lastNum = moduleSerialNumbers.substring(moduleSerialNumbers.length() - 1);
        return lastNum.contains(String.valueOf(getAllBatteries()));
    }
}
