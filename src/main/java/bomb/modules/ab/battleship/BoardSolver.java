package bomb.modules.ab.battleship;

import java.util.List;

public class BoardSolver {
    public static void solve(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        recalculateCounters(ocean, rowCounters, columnCounters);

    }

    private static void recalculateCounters(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        for (int i = 0; i < Ocean.BOARD_LENGTH; i++) {
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
}
