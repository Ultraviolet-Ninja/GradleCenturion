package bomb.modules.dh.hexamaze_redesign;

import bomb.Widget;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.CYAN;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.MAGENTA;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.YELLOW;

public class Hexamaze extends Widget {
    public static final Map<Color, Integer> COLOR_MAP = new HashMap<>();
    public static final Color PEG_COLOR = new Color(0.65, 0.65, 0.65, 1.0);

    static {
        COLOR_MAP.put(PEG_COLOR, -1);
        COLOR_MAP.put(RED, 0);
        COLOR_MAP.put(YELLOW, 1);
        COLOR_MAP.put(GREEN, 2);
        COLOR_MAP.put(CYAN, 3);
        COLOR_MAP.put(BLUE, 4);
        COLOR_MAP.put(MAGENTA, 5);
    }


}
