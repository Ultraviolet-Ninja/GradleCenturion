package bomb.modules.m.microcontroller;

import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.MAGENTA;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;
import static javafx.scene.paint.Color.YELLOW;

public enum Pin {
    VCC(YELLOW, YELLOW, RED, RED, GREEN), AIN(MAGENTA, RED, MAGENTA, BLUE, RED),
    DIN(GREEN, MAGENTA, GREEN, YELLOW, YELLOW), PWM(BLUE, GREEN, BLUE, GREEN, BLUE),
    RST(RED, BLUE, YELLOW, MAGENTA, MAGENTA), GND(WHITE);

    private final Color[] colors;

    Pin(Color... colors) {
        this.colors = colors;
    }

    public Color getColorAtIndex(int index) {
        return colors[index % colors.length];
    }
}
