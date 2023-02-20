package bomb.modules.ab.battleship;

import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.DARKRED;
import static javafx.scene.paint.Color.DODGERBLUE;
import static javafx.scene.paint.Color.FORESTGREEN;
import static javafx.scene.paint.Color.STEELBLUE;


public enum Tile {
    CLEAR(DODGERBLUE),
    SHIP(DARKRED),
    RADAR(FORESTGREEN),
    UNKNOWN(STEELBLUE);

    private final Color tileColor;

    Tile(Color tileColor) {
        this.tileColor = tileColor;
    }

    public Color getTileColor() {
        return tileColor;
    }
}
