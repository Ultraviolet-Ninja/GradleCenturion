package bomb.modules.ab.battleship;

import bomb.Widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static bomb.tools.filter.Filter.CHAR_FILTER;
import static bomb.tools.filter.Filter.NUMBER_PATTERN;
import static bomb.tools.filter.Filter.ultimateFilter;

public class Battleship extends Widget {
    private static Ocean ocean;
    private static int[] rowCounters, columnCounters;
    private static int numberOfRadarSpots;

    static {
        ocean = new Ocean();
        rowCounters = null;
        columnCounters = null;
        numberOfRadarSpots = -1;
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
        final char charLetterToInt = 'a';
        final char charNumberToInt = '1';

        int startingRow = (letter - charLetterToInt) % Ocean.BOARD_LENGTH;
        int startingColumn = (number - charNumberToInt) % Ocean.BOARD_LENGTH;

        if (startingColumn < 0)
            startingColumn += Ocean.BOARD_LENGTH;

        setTileAsRadar(startingRow, startingColumn);
        return offsetChar(charLetterToInt, startingRow) +
                offsetChar(charNumberToInt, startingColumn);
    }

    private static String calculateEdgeworkCoordinates() {
        final char charLetterToInt = 'a';
        final char charNumberToInt = '1';

        int startingRow = getTotalPorts() % Ocean.BOARD_LENGTH;
        int startingColumn = (countIndicators(IndicatorFilter.ALL) + getAllBatteries()) % Ocean.BOARD_LENGTH;

        setTileAsRadar(startingRow, startingColumn);
        return offsetChar(charLetterToInt, startingRow) +
                offsetChar(charNumberToInt, startingColumn);
    }

    private static void setTileAsRadar(int x, int y) {
        ocean.setTileState(x, y, Tile.RADAR);
    }

    private static String offsetChar(char letter, int offset) {
        return String.valueOf((char)(letter + offset));
    }

    public static Ocean getOcean() {
        return ocean;
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


    }

    private static void validateConfirmedTiles(Tile[] tiles) throws IllegalArgumentException {
        if (numberOfRadarSpots == -1)
            throw new IllegalArgumentException("Radar spots were not identified");
        if (tiles.length != numberOfRadarSpots)
            throw new IllegalArgumentException("Size mismatch");
    }

    public static void reset() {
        ocean = new Ocean();
        rowCounters = null;
        columnCounters = null;
        numberOfRadarSpots = -1;
    }
}
