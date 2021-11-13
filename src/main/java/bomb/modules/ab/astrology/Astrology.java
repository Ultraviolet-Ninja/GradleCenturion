package bomb.modules.ab.astrology;

import bomb.Widget;
import bomb.tools.filter.Regex;

import java.util.Arrays;
import java.util.EnumSet;

import static bomb.modules.ab.astrology.AstrologySymbol.ARIES;
import static bomb.modules.ab.astrology.AstrologySymbol.PISCES;
import static bomb.modules.ab.astrology.AstrologySymbol.PLUTO;
import static bomb.modules.ab.astrology.AstrologySymbol.SUN;
import static bomb.tools.filter.Filter.CHAR_FILTER;
import static bomb.tools.filter.Filter.ultimateFilter;
import static bomb.tools.filter.Regex.CREATE_INSENSITIVE_SET;

/**
 * This class deals with the Astrology module. This module displays three different astrological symbols
 * that pertain to a particular index in the 3 2D arrays. These symbols will point to a particular number in
 * each array that'll be added up to determine the Omen that needs to be pressed.
 * (Good if positive, Bad if negative or No Omen if 0)
 * The quantity of the number is used to determine when to press the button.
 * (i.e. Bad Omen at 5 means that you need to press "Bad Omen" when the bomb timer has a 5 in it)
 */
public class Astrology extends Widget {
    public static final byte ELEMENT_INDEX = 0, CELESTIAL_INDEX = 1, ZODIAC_INDEX = 2, EXPECTED_SIZE = 3;
    public static final String GOOD_OMEN = "Good Omen at ", POOR_OMEN = "Poor Omen at ", NO_OMEN = "No Omen";

    private static final byte[][] ELEMENT_CELESTIAL_GRID = {
            {0, 0, 1, -1, 0, 1, -2, 2, 0, -1}, {-2, 0, -1, 0, 2, 0, -2, 2, 0, 1},
            {-1, -1, 0, -1, 1, 2, 0, 2, 1, -2}, {-1, 2, -1, 0, -2, -1, 0, 2, -2, 2}
    },

    ELEMENT_ZODIAC_GRID = {
            {1, 0, -1, 0, 0, 2, 2, 0, 1, 0, 1, 0}, {2, 2, -1, 2, -1, -1, -2, 1, 2, 0, 0, 2},
            {-2, -1, 0, 0, 1, 0, 1, 2, -1, -2, 1, 1}, {1, 1, -2, -2, 2, 0, -1, 1, 0, 0, -1, -1}
    },

    CELESTIAL_ZODIAC_GRID = {
            {-1, -1, 2, 0, -1, 0, -1, 1, 0, 0, -2, -2}, {-2, 0, 1, 0, 2, 0, -1, 1, 2, 0, 1, 0},
            {-2, -2, -1, -1, 1, -1, 0, -2, 0, 0, -1, 1}, {-2, 2, -2, 0, 0, 1, -1, 0, 2, -2, -1, 1},
            {-2, 0, -1, -2, -2, -2, -1, 1, 1, 1, 0, -1}, {-1, -2, 1, -1, 0, 0, 0, 1, 0, -1, 2, 0},
            {-1, -1, 0, 0, 1, 1, 0, 0, 0, 0, -1, -1}, {-1, 2, 0, 0, 1, -2, 1, 0, 2, -1, 1, 0},
            {1, 0, 2, 1, -1, 1, 1, 1, 0, -2, 2, 0}, {-1, 0, 0, -1, -2, 1, 2, 1, 1, 0, 0, -1}
    };

    /**
     * Calculates the phrase composed of the Omen to press and when to press it
     *
     * @param symbols The input symbols
     * @return The String command
     * @throws IllegalArgumentException Serial code is empty or there's an issue with the amount or type of symbols
     */
    public static String calculate(AstrologySymbol... symbols) throws IllegalArgumentException {
        checkSerialCode();
        checkInputHasAllThreeTypes(symbols);
        Arrays.sort(symbols);

        int first = ELEMENT_CELESTIAL_GRID[symbols[ELEMENT_INDEX].getIndex()][symbols[CELESTIAL_INDEX].getIndex()],
                second = ELEMENT_ZODIAC_GRID[symbols[ELEMENT_INDEX].getIndex()][symbols[ZODIAC_INDEX].getIndex()],
                third = CELESTIAL_ZODIAC_GRID[symbols[CELESTIAL_INDEX].getIndex()][symbols[ZODIAC_INDEX].getIndex()];

        int results = first + second + third;
        results = checkMatchingSerialCodeLetters(results, symbols);

        return (results == 0) ?
                NO_OMEN :
                ((results > 0) ? GOOD_OMEN : POOR_OMEN) + Math.abs(results);
    }

    /**
     * Checks the current set of symbols and compares them to letters in the Serial Code.
     * If the symbol has a letter that matches a letter in the Serial Code, it adds one to
     * the output value. If not, it'll subtract 1
     *
     * @param initialVal The output value
     * @param symbols    The set of AstroSymbols
     * @return The resulting output value
     * @throws IllegalArgumentException The Serial Code is empty
     */
    private static int checkMatchingSerialCodeLetters(int initialVal, AstrologySymbol[] symbols)
            throws IllegalArgumentException {
        String letters = ultimateFilter(serialCode, CHAR_FILTER);
        for (AstrologySymbol symbol : symbols) {
            Regex checker = CREATE_INSENSITIVE_SET.apply(letters);
            checker.loadText(symbol.name());

            if (!checker.hasMatch()) initialVal--;
            else initialVal++;
        }
        return initialVal;
    }

    private static void checkInputHasAllThreeTypes(AstrologySymbol[] symbols) throws IllegalArgumentException {
        if (symbols.length != EXPECTED_SIZE)
            throw new IllegalArgumentException("Astrology input size should be " + EXPECTED_SIZE);
        containsOneOfEach(symbols);
    }

    private static void containsOneOfEach(AstrologySymbol[] symbols) throws IllegalArgumentException {
        int zodiacCount = 0, celestialCount = 0, elementalCount = 0;

        EnumSet<AstrologySymbol> zodiacSet = EnumSet.range(ARIES, PISCES);
        EnumSet<AstrologySymbol> celestialSet = EnumSet.range(SUN, PLUTO);

        for (AstrologySymbol symbol : symbols) {
            if (zodiacSet.contains(symbol))
                zodiacCount++;
            else if (celestialSet.contains(symbol))
                celestialCount++;
            else
                elementalCount++;
        }

        if (zodiacCount != celestialCount && zodiacCount != elementalCount)
            throw new IllegalArgumentException("There are too many of one type of Astrology Symbol");
    }
}
