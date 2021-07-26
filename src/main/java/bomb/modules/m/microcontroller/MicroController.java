package bomb.modules.m.microcontroller;

import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.modules.m.microcontroller.chip.AbstractController;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static bomb.tools.filter.Filter.NUMBER_PATTERN;
import static bomb.tools.filter.Mechanics.ultimateFilter;

public class MicroController extends Widget {
    private static final String[] THIRD_CONDITION = new String[]{"c", "l", "r", "x", "1", "8"};

    private static AbstractController instance;

    public static void setController(AbstractController controller){
        instance = controller;
    }

    public static ArrayList<Color> getPinColors(String serialNumbers){
        if (containsRequiredNumbers(serialNumbers))
            return instance.traversePins(0);
        else if (hasLitIndicator(Indicator.SIG) || portExists(Port.RJ45))
            return instance.traversePins(1);
        else if (ultimateFilter(serialCode, THIRD_CONDITION).length() > 0)
            return instance.traversePins(2);
        else if (numbersMatch(serialNumbers))
            return instance.traversePins(3);
        return instance.traversePins(4);
    }

    private static boolean containsRequiredNumbers(String serialNumbers){
        NUMBER_PATTERN.loadText(serialNumbers);
        String numbers = NUMBER_PATTERN.toNewString();
        return numbers.contains("1") || numbers.contains("4");
    }

    private static boolean numbersMatch(String serialNumbers){
        String lastNum = serialNumbers.substring(serialNumbers.length() - 1);
        return lastNum.contains(String.valueOf(getAllBatteries()));
    }
}
