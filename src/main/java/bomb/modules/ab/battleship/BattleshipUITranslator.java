package bomb.modules.ab.battleship;

import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

public final class BattleshipUITranslator {
    public static void translateToFrontendGrid(@NotNull Rectangle[][] grid, @NotNull Ocean ocean) {
        for (int i = 0; i < grid.length; i++) {
            int length = grid[i].length;
            for (int j = 0; j < length; j++) {
                grid[i][j].setFill(ocean.getTileState(i, j).getTileColor());
            }
        }
    }
}
