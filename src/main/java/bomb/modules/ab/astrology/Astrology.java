package bomb.modules.ab.astrology;

import bomb.Widget;
import bomb.tools.filter.Regex;

import java.util.regex.Pattern;

import static bomb.tools.filter.Filter.CHAR_FILTER;
import static bomb.tools.filter.Filter.ultimateFilter;

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
     * @throws IllegalArgumentException Serial code is empty or there's an issue with the amount of symbols
     */
    public static String calculate(AstroSymbol... symbols) throws IllegalArgumentException {
        serialCodeChecker();
        checkThreeTypes(symbols);
        AstroSymbol[] sortSymbols = sort(symbols);

        int first = ELEMENT_CELESTIAL_GRID[sortSymbols[ELEMENT_INDEX].getIdx()][sortSymbols[CELESTIAL_INDEX].getIdx()],
                second = ELEMENT_ZODIAC_GRID[sortSymbols[ELEMENT_INDEX].getIdx()][sortSymbols[ZODIAC_INDEX].getIdx()],
                third = CELESTIAL_ZODIAC_GRID[sortSymbols[CELESTIAL_INDEX].getIdx()][sortSymbols[ZODIAC_INDEX].getIdx()];

        int results = first + second + third;
        results = checkMatchingSerialLetters(results, sortSymbols);

        return (results > 0) ?
                (GOOD_OMEN + results) :
                ((results == 0) ?
                        NO_OMEN :
                        (POOR_OMEN + Math.abs(results)));
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
    private static int checkMatchingSerialLetters(int initialVal, AstroSymbol[] symbols)
            throws IllegalArgumentException {
        String letters = ultimateFilter(serialCode, CHAR_FILTER);
        for (AstroSymbol symbol : symbols) {
            Regex checker = new Regex("[" + letters + "]", symbol.name(), Pattern.CASE_INSENSITIVE);
            if (checker.findAllMatches().isEmpty()) initialVal--;
            else initialVal++;
        }
        return initialVal;
    }

    private static void checkThreeTypes(AstroSymbol[] symbols) throws IllegalArgumentException {
        if (symbols.length != EXPECTED_SIZE)
            throw new IllegalArgumentException("Astrology input size should be " + EXPECTED_SIZE);
        containsOneOfEach(symbols);
    }

    private static void containsOneOfEach(AstroSymbol[] symbols) throws IllegalArgumentException {
        containsOneCelestial(symbols);
        containsOneElement(symbols);
        containsOneZodiac(symbols);
    }

    private static void containsOneCelestial(AstroSymbol[] symbols) {
        int count = 0;
        for (AstroSymbol symbol : symbols)
            if (isCelestial(symbol)) count++;
        if (count > 1) throw new IllegalArgumentException("Only one celestial body allowed");
    }

    private static void containsOneElement(AstroSymbol[] symbols) {
        int count = 0;
        for (AstroSymbol symbol : symbols)
            if (isElemental(symbol)) count++;
        if (count > 1) throw new IllegalArgumentException("Only one element allowed");
    }

    private static void containsOneZodiac(AstroSymbol[] symbols) {
        int count = 0;
        for (AstroSymbol symbol : symbols)
            if (isZodiac(symbol)) count++;
        if (count > 1) throw new IllegalArgumentException("Only one zodiac allowed");
    }

    private static AstroSymbol[] sort(AstroSymbol[] symbols) {
        AstroSymbol[] output = new AstroSymbol[symbols.length];
        for (AstroSymbol symbol : symbols) {
            if (isElemental(symbol)) output[ELEMENT_INDEX] = symbol;
            else if (isCelestial(symbol)) output[CELESTIAL_INDEX] = symbol;
            else if (isZodiac(symbol)) output[ZODIAC_INDEX] = symbol;
        }
        return output;
    }

    private static boolean isZodiac(AstroSymbol symbol) {
        return switch (symbol) {
            case ARIES, TAURUS, GEMINI, CANCER, LEO, VIRGO, LIBRA, SCORPIO, SAGITTARIUS, CAPRICORN, AQUARIUS, PISCES -> true;
            default -> false;
        };
    }

    private static boolean isCelestial(AstroSymbol symbol) {
        return switch (symbol) {
            case SUN, MOON, MERCURY, VENUS, MARS, JUPITER, SATURN, URANUS, NEPTUNE, PLUTO -> true;
            default -> false;
        };
    }

    private static boolean isElemental(AstroSymbol symbol) {
        return switch (symbol) {
            case FIRE, EARTH, AIR, WATER -> true;
            default -> false;
        };
    }
}
