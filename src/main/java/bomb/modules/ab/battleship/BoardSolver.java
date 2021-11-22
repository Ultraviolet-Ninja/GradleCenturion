package bomb.modules.ab.battleship;

import java.util.List;
import java.util.function.BiConsumer;

import static bomb.modules.ab.battleship.Ocean.BOARD_LENGTH;

public class BoardSolver {
    public static void solve(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        recalculateCounters(ocean, rowCounters, columnCounters);

        do {
            setClearTiles(ocean, rowCounters, columnCounters);

            recalculateCounters(ocean, rowCounters, columnCounters);
        } while (ocean.hasUnknownTile() && false);
    }

    private static void recalculateCounters(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            rowCounters[i] = recalculateSingleValue(ocean.getRow(i), rowCounters[i]);
            columnCounters[i] = recalculateSingleValue(ocean.getColumn(i), columnCounters[i]);
        }
    }

    private static int recalculateSingleValue(List<Tile> section, int originalValue) {
        int foundShips = (int) section.stream()
                .filter(tile -> tile == Tile.SHIP)
                .count();
        return originalValue - foundShips;
    }

    private static void setClearTiles(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        BiConsumer<Ocean, int[]> rowAction = (currentOcean, coordinates) -> {
            if (ocean.getTileState(coordinates[0], coordinates[1]) == Tile.UNKNOWN) {
                ocean.setTileState(coordinates[0], coordinates[1], Tile.CLEAR);
            }
        };
        BiConsumer<Ocean, int[]> columnAction = (currentOcean, coordinates) -> {
            if (ocean.getTileState(coordinates[1], coordinates[0]) == Tile.UNKNOWN) {
                ocean.setTileState(coordinates[1], coordinates[0], Tile.CLEAR);
            }
        };

        setClearAlley(ocean, rowCounters, rowAction);
        setClearAlley(ocean, columnCounters, columnAction);
    }

    private static void setClearAlley(Ocean ocean, int[] counters, BiConsumer<Ocean, int[]> action) {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            if (counters[i] == 0) {
                for (int j = 0; j < BOARD_LENGTH; j++) {
                    int[] coordinates = new int[]{i, j};
                    action.accept(ocean, coordinates);
                }
            }
        }
    }
}
