package bomb.modules.ab.battleship;

import java.util.List;

import static bomb.modules.ab.battleship.Ocean.BOARD_LENGTH;
import static bomb.modules.ab.battleship.Tile.CLEAR;
import static bomb.modules.ab.battleship.Tile.SHIP;
import static bomb.modules.ab.battleship.Tile.UNKNOWN;

public class BoardSolver {
    public static void solve(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        do {
            setClearTiles(ocean, rowCounters, columnCounters);
            setDiagonalAdjacentShipTilesClear(ocean);
            guaranteeShipTile(ocean, rowCounters, columnCounters);
//            findLargestShip(ocean, rowCounters, columnCounters);
        } while (ocean.hasUnknownTile());
    }

    private static void setClearTiles(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        setClearRow(ocean, rowCounters);
        setClearColumn(ocean, columnCounters);
    }

    private static void setClearRow(Ocean ocean, int[] rowCounters) {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            int numberOfShips = countSpecificTile(ocean.getRow(i), SHIP);
            if (rowCounters[i] - numberOfShips == 0) {
                ocean.fillRowsWithTile(i, CLEAR);
            }
        }
    }

    private static void setClearColumn(Ocean ocean, int[] columnCounters) {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            int numberOfShips = countSpecificTile(ocean.getColumn(i), SHIP);
            if (columnCounters[i] - numberOfShips == 0) {
                ocean.fillColumnsWithTile(i, CLEAR);
            }
        }
    }

    private static void guaranteeShipTile(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        guaranteeShipTileRow(ocean, rowCounters);
        guaranteeShipTileColumn(ocean, columnCounters);
    }

    private static void guaranteeShipTileRow(Ocean ocean, int[] rowCounters) {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            if (rowCounters[i] > 0) {
                List<Tile> row = ocean.getRow(i);
                int numberOfUnknownTiles = countSpecificTile(row, UNKNOWN);
                int numberOfShips = countSpecificTile(row, SHIP);
                if (numberOfUnknownTiles + numberOfShips == rowCounters[i])
                    ocean.fillRowsWithTile(i, SHIP);
            }
        }
    }

    private static void guaranteeShipTileColumn(Ocean ocean, int[] columnCounters) {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            if (columnCounters[i] > 0) {
                List<Tile> column = ocean.getColumn(i);
                int numberOfUnknownTiles = countSpecificTile(column, UNKNOWN);
                int numberOfShips = countSpecificTile(column, SHIP);
                if (numberOfUnknownTiles + numberOfShips == columnCounters[i])
                    ocean.fillColumnsWithTile(i, SHIP);
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

                if (Ocean.isInBoardRange(newColumn, newRow))
                    ocean.setTileState(newColumn, newRow, CLEAR);
            }
        }
    }

    private static void findLargestShip(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        Ship ship = Ship.getCurrentLargestShip();
        if (ship == null)
            return;


    }

    private static int countSpecificTile(List<Tile> alley, Tile tileToFind) {
        return (int) alley.stream()
                .filter(tile -> tile == tileToFind)
                .count();
    }
}
