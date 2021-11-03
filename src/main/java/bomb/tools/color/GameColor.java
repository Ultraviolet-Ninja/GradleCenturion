package bomb.tools.color;

import javafx.scene.paint.Color;

public enum GameColor {
    RED(Color.RED), ORANGE(Color.ORANGE), YELLOW(Color.YELLOW), GREEN(Color.GREEN), CYAN(Color.CYAN),
    BLUE(Color.BLUE), MAGENTA(Color.MAGENTA), WHITE(Color.WHITE), BLACK(Color.BLACK),
    GRAY(new Color(0.65, 0.65, 0.65, 1.0));

    private final Color color;

    GameColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public static GameColor getByIndex(int index) {
        return values()[index];
    }
}
