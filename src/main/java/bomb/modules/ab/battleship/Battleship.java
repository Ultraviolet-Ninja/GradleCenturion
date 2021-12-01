package bomb.modules.ab.battleship;

import bomb.Widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static bomb.modules.ab.battleship.Tile.RADAR;
import static bomb.modules.ab.battleship.Tile.UNKNOWN;
import static bomb.modules.ab.battleship.solve.BoardSolver.solve;
import static bomb.tools.filter.Filter.CHAR_FILTER;
import static bomb.tools.filter.Filter.NUMBER_PATTERN;
import static bomb.tools.filter.Filter.ultimateFilter;
import static java.util.Arrays.stream;

public class Battleship extends Widget {
    private static final char CHAR_LETTER_TO_INT = 'a', CHAR_NUMBER_TO_INT = '1';

    private static Ocean ocean;
    private static int[] rowCounters, columnCounters;
    private static int numberOfRadarSpots;

    static {
        reset();
    }

    public static Set<String> calculateRadarPositions() throws IllegalArgumentException {
        checkSerialCode();
        Set<String> output = new TreeSet<>(calculateSerialCodeCoordinates());
        output.add(calculateEdgeworkCoordinates());
        numberOfRadarSpots = output.size();
        return output;
    }

    private static List<String> calculateSerialCodeCoordinates() {
        List<String> output = new ArrayList<>();
        String lettersInCode = ultimateFilter(serialCode, CHAR_FILTER).toLowerCase();
        String numbersInCode = ultimateFilter(serialCode, NUMBER_PATTERN);

        while (!lettersInCode.isEmpty() && !numbersInCode.isEmpty()) {
            output.add(calculateSingleSerialCodeCoordinates(
                    lettersInCode.charAt(0),
                    numbersInCode.charAt(0)
            ));
            lettersInCode = lettersInCode.substring(1);
            numbersInCode = numbersInCode.substring(1);
        }

        return output;
    }

    private static String calculateSingleSerialCodeCoordinates(char letter, char number) {
        int startingRow = (letter - CHAR_LETTER_TO_INT) % Ocean.BOARD_LENGTH;
        int startingColumn = (number - CHAR_NUMBER_TO_INT) % Ocean.BOARD_LENGTH;

        if (startingColumn < 0)
            startingColumn += Ocean.BOARD_LENGTH;

        setTileAsRadar(startingColumn, startingRow);
        return offsetChar(CHAR_LETTER_TO_INT, startingRow) +
                offsetChar(CHAR_NUMBER_TO_INT, startingColumn);
    }

    private static String calculateEdgeworkCoordinates() {
        int startingRow = calculateTotalPorts() % Ocean.BOARD_LENGTH - 1;
        int startingColumn =
                (countIndicators(IndicatorFilter.ALL_PRESENT) + getAllBatteries() - 1) % Ocean.BOARD_LENGTH;

        setTileAsRadar(startingColumn, startingRow);
        return offsetChar(CHAR_LETTER_TO_INT, startingRow) +
                offsetChar(CHAR_NUMBER_TO_INT, startingColumn);
    }

    private static void setTileAsRadar(int x, int y) {
        ocean.setTileState(x, y, RADAR);
    }

    private static String offsetChar(char letter, int offset) {
        return String.valueOf((char) (letter + offset));
    }

    public static void setRowCounters(int[] rowCounters) {
        if (validateArray(rowCounters))
            Battleship.rowCounters = rowCounters;
    }

    public static void setColumnCounters(int[] columnCounters) {
        if (validateArray(columnCounters))
            Battleship.columnCounters = columnCounters;
    }

    private static boolean validateArray(int[] array) {
        if (array.length != Ocean.BOARD_LENGTH)
            return false;

        for (int number : array) {
            if (number < 0 || number >= Ocean.BOARD_LENGTH)
                return false;
        }
        return true;
    }

    public static void confirmRadarSpots(Tile... tiles) throws IllegalArgumentException {
        validateConfirmedTiles(tiles);
        ocean.removeRadarSpots(tiles);
        numberOfRadarSpots = -1;
    }

    private static void validateConfirmedTiles(Tile[] tiles) throws IllegalArgumentException {
        if (numberOfRadarSpots == -1)
            throw new IllegalArgumentException("Radar spots were not identified");
        if (tiles.length != numberOfRadarSpots)
            throw new IllegalArgumentException("Size mismatch");
    }

    public static Ocean solveOcean() {
        validateEssentialData();
        solve(ocean, rowCounters, columnCounters);
        return ocean;
    }

    private static void validateEssentialData() throws IllegalArgumentException {
        String errorMessage = "";
        int[] counters = ocean.countByTile();

        if (counters[UNKNOWN.ordinal()] == Math.pow(Ocean.BOARD_LENGTH, 2)) {
            //Check if the board is full of UNKNOWN tiles
            errorMessage = "Please use the Radar first";
        } else if (counters[RADAR.ordinal()] > 0) {
            //Are there any more RADAR tiles
            errorMessage = "Board still contains Radar spots";
        } else if (columnCounters == null || rowCounters == null) {
            errorMessage = "Initial rows and columns need to be set";
        } else if (!Ship.areQuantitiesSet()) {
            errorMessage = "The number of Ships has not been set";
        }

        if (!errorMessage.isEmpty())
            throw new IllegalArgumentException(errorMessage);
        confirmShipSpaceOccupancy();
    }

    private static void confirmShipSpaceOccupancy() throws IllegalArgumentException {
        int columnSpaces = stream(columnCounters).sum();
        int rowSpaces = stream(rowCounters).sum();
        int numberOfShipSpaces = Ship.getNumberOfShipSpaces();

        if (columnSpaces == rowSpaces && columnSpaces == numberOfShipSpaces)
            return;

        String errorMessage = String.format("""
                Values don't match.
                Column Spaces: %d
                Row Spaces: %d
                Ship Spaces: %d
                """, columnSpaces, rowSpaces, numberOfShipSpaces);

        throw new IllegalArgumentException(errorMessage);
    }

    public static Ocean getOcean() {
        return ocean;
    }

    public static void reset() {
        ocean = new Ocean();
        rowCounters = null;
        columnCounters = null;
        numberOfRadarSpots = -1;
        Ship.clearAllQuantities();
    }
}
