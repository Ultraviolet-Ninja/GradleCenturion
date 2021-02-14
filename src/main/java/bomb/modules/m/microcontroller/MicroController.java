package bomb.modules.m.microcontroller;

import bomb.Widget;
import bomb.enumerations.Indicators;
import bomb.enumerations.Ports;
import bomb.modules.m.microcontroller.chip.AbstractController;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static bomb.tools.Mechanics.NUMBER_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;

public class MicroController extends Widget {
    private static final String[] THIRD_CONDITION = new String[]{"c", "l", "r", "x", "1", "8"};

    private static AbstractController instance;

    public static void setController(AbstractController controller){
        instance = controller;
    }

    public static ArrayList<Color> getPinColors(String serialNumbers){
        if (containsRequiredNumbers(serialNumbers))
            return instance.traversePins(0);
        else if (hasLitIndicator(Indicators.SIG) || portExists(Ports.RJ45))
            return instance.traversePins(1);
        else if (ultimateFilter(serialCode, THIRD_CONDITION).length() > 0)
            return instance.traversePins(2);
        else if (numbersMatch(serialNumbers))
            return instance.traversePins(3);
        else return instance.traversePins(4);
    }

    private static boolean containsRequiredNumbers(String serialNumbers){
        String numbers = ultimateFilter(serialNumbers, NUMBER_REGEX);
        return numbers.contains("1") || numbers.contains("4");
    }

    private static boolean numbersMatch(String serialNumbers){
        String lastNum = serialNumbers.substring(serialNumbers.length() - 1);
        return lastNum.contains(String.valueOf(getAllBatteries()));
    }
}
