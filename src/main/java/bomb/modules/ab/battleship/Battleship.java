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

    static {
        ocean = new Ocean();
    }

    public static Set<String> calculateRadarPositions() throws IllegalArgumentException {
        checkSerialCode();
        Set<String> output = new TreeSet<>(calculateSerialCodeCoordinates());
        output.add(calculateEdgeworkCoordinates());

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

        setRadar(startingRow, startingColumn);
        return offsetChar(charLetterToInt, startingRow) +
                offsetChar(charNumberToInt, startingColumn);
    }

    private static String calculateEdgeworkCoordinates() {
        final char charLetterToInt = 'a';
        final char charNumberToInt = '1';

        int startingRow = getTotalPorts() % Ocean.BOARD_LENGTH;
        int startingColumn = (countIndicators(IndicatorFilter.ALL) + getAllBatteries()) % Ocean.BOARD_LENGTH;

        setRadar(startingRow, startingColumn);
        return offsetChar(charLetterToInt, startingRow) +
                offsetChar(charNumberToInt, startingColumn);
    }

    private static void setRadar(int x, int y) {
        ocean.setTileState(x, y, Tile.RADAR);
    }

    private static String offsetChar(char letter, int offset) {
        return String.valueOf((char)(letter + offset));
    }

    public static Ocean getOcean() {
        return ocean;
    }

    public static void reset() {
        ocean = new Ocean();
    }
}
