package bomb.modules.ab.battleship;

import java.util.List;
import java.util.function.BiConsumer;

import static bomb.modules.ab.battleship.Ocean.BOARD_LENGTH;
import static bomb.modules.ab.battleship.Tile.CLEAR;
import static bomb.modules.ab.battleship.Tile.SHIP;
import static bomb.modules.ab.battleship.Tile.UNKNOWN;

public class BoardSolver {
    public static void solve(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        recalculateCounters(ocean, rowCounters, columnCounters);

        do {
            setClearTiles(ocean, rowCounters, columnCounters);
            setDiagonalAdjacentShipTilesClear(ocean);
            findLargestShip(ocean, rowCounters, columnCounters);
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
                .filter(tile -> tile == SHIP)
                .count();
        return originalValue - foundShips;
    }

    private static void setClearTiles(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        BiConsumer<Ocean, int[]> rowAction = (currentOcean, coordinates) -> {
            if (ocean.getTileState(coordinates[0], coordinates[1]) == UNKNOWN) {
                ocean.setTileState(coordinates[0], coordinates[1], CLEAR);
            }
        };
        BiConsumer<Ocean, int[]> columnAction = (currentOcean, coordinates) -> {
            if (ocean.getTileState(coordinates[1], coordinates[0]) == UNKNOWN) {
                ocean.setTileState(coordinates[1], coordinates[0], CLEAR);
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

    private static void setDiagonalAdjacentShipTilesClear(Ocean ocean) {
        for (int column = 0; column < BOARD_LENGTH; column++) {
            for (int row = 0; row < BOARD_LENGTH; row++) {
                if (ocean.getTileState(column, row) == SHIP)
                    setForOneShipTile(ocean, column, row);
            }
        }
    }

    private static void setForOneShipTile(Ocean ocean, int column, int row) {
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                int newColumn = column + i;
                int newRow = row + j;
                if (isInRange(newColumn, newRow)) {
                    ocean.setTileState(newColumn, newRow, CLEAR);
                }
            }
        }
    }

    private static boolean isInRange(int x, int y) {
        return x >= 0 && x < BOARD_LENGTH &&
                y >= 0 && y < BOARD_LENGTH;
    }

    private static void findLargestShip(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        Ship currentLargest = Ship.getCurrentLargestShip();
        if (currentLargest == null)
            return;




    }
}
