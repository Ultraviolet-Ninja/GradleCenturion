package bomb.modules.ab.battleship.solve;

import bomb.modules.ab.battleship.Ocean;
import bomb.modules.ab.battleship.Tile;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static bomb.modules.ab.battleship.Ocean.BOARD_LENGTH;
import static bomb.modules.ab.battleship.Tile.CLEAR;
import static bomb.modules.ab.battleship.Tile.SHIP;
import static bomb.modules.ab.battleship.Tile.UNKNOWN;

public final class BoardSolver {
    @SuppressWarnings("DataFlowIssue")
    public static Set<Ocean> solve(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        refineSearchSpace(ocean, rowCounters, columnCounters);

        int[] nextSpot = ocean.findNextUnknownTile();
        if (nextSpot == null) {
            return Collections.singleton(ocean);
        }

        Deque<Ocean> oceanGuesses = new ArrayDeque<>(generateNewGuesses(nextSpot, ocean, rowCounters, columnCounters));
        List<Ocean> permutationList = new ArrayList<>();

        while (!oceanGuesses.isEmpty()) {
            Ocean guess = oceanGuesses.poll();
            if (guess.hasUnknownTile()) {
                nextSpot = guess.findNextUnknownTile();

                oceanGuesses.addAll(generateNewGuesses(nextSpot, guess, rowCounters, columnCounters));
            } else {
                permutationList.add(guess);
            }
        }

        return permutationList.stream()
                .filter(permutation -> isCompleteBoard(permutation, rowCounters, columnCounters))
                .collect(Collectors.toSet());
    }

    private static List<Ocean> generateNewGuesses(int[] coordinates, Ocean ocean, int[] rowCounters, int[] columnCounters) {
        Ocean waterCopy = ocean.copy();
        Ocean shipCopy = ocean.copy();

        waterCopy.setTileState(coordinates[0], coordinates[1], CLEAR);
        shipCopy.setTileState(coordinates[0], coordinates[1], SHIP);

        return Stream.of(waterCopy, shipCopy)
                .filter(permutation -> !isInvalidBoardConfig(permutation, rowCounters, columnCounters))
                .toList();
    }

    private static boolean isInvalidBoardConfig(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        int[] currentRowShipCounts = ocean.countShipSpacesByRow();
        int[] currentColumnShipCounts = ocean.countShipSpacesByColumn();
        int[] currentRowWaterCounts = ocean.countClearSpacesByRow();
        int[] currentColumnWaterCounts = ocean.countClearSpacesByColumn();

        return containsTooManyShipSpaces(currentRowShipCounts, rowCounters) ||
                containsTooManyShipSpaces(currentColumnShipCounts, columnCounters) ||
                containsTooManyWaterSpaces(currentRowWaterCounts, rowCounters) ||
                containsTooManyWaterSpaces(currentColumnWaterCounts, columnCounters);
    }

    private static boolean containsTooManyShipSpaces(int[] currentValues, int[] actualValues) {
        return IntStream.range(0, currentValues.length)
                .anyMatch(i -> currentValues[i] > actualValues[i]);
    }

    private static boolean containsTooManyWaterSpaces(int[] currentValues, int[] actualValues) {
        return IntStream.range(0, currentValues.length)
                .anyMatch(i -> currentValues[i] > BOARD_LENGTH - actualValues[i]);
    }

    private static boolean isCompleteBoard(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        int[] currentRowShipCounts = ocean.countShipSpacesByRow();
        int[] currentColumnShipCounts = ocean.countShipSpacesByColumn();
        int[] currentRowWaterCounts = ocean.countClearSpacesByRow();
        int[] currentColumnWaterCounts = ocean.countClearSpacesByColumn();

        return Arrays.equals(currentRowShipCounts, rowCounters) &&
                Arrays.equals(currentColumnShipCounts, columnCounters) &&
                areWaterSpacesMatching(currentRowWaterCounts, rowCounters) &&
                areWaterSpacesMatching(currentColumnWaterCounts, columnCounters) &&
                ShipFinder.inspectShipsOnBoard(ocean) &&
                doShipDiagonalsComply(ocean);
    }

    private static boolean doShipDiagonalsComply(Ocean ocean) {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                if (ocean.isTileShipState(i, j) && areShipsDiagonallyTouching(ocean, i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean areShipsDiagonallyTouching(Ocean ocean, int x, int y) {
        return Arrays.stream(new int[][]{{-1,-1}, {-1,1}, {1,-1}, {1,1}})
                .peek(pair -> {
                    pair[0] += x;
                    pair[1] += y;
                })
                .filter(pair -> Ocean.isInBoardRange(pair[0], pair[1]))
                .anyMatch(pair -> !ocean.isTileClearState(pair[0], pair[1]));
    }

    private static boolean areWaterSpacesMatching(int[] waterSpaces, int[] shipCounters) {
        return IntStream.range(0, waterSpaces.length)
                .allMatch(i -> waterSpaces[i] == BOARD_LENGTH - shipCounters[i]);
    }

    private static void refineSearchSpace(Ocean ocean, int[] rowCounters, int[] columnCounters) {
        setClearTiles(ocean, rowCounters, columnCounters);
        setDiagonalAdjacentShipTilesClear(ocean);
        guaranteeShipTile(ocean, rowCounters, columnCounters);
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

    private static int countSpecificTile(List<Tile> alley, Tile tileToFind) {
        return (int) alley.stream()
                .filter(tile -> tile == tileToFind)
                .count();
    }
}
