package bomb.modules.ab;

import bomb.Widget;
import bomb.enumerations.AstroSymbols;

import static bomb.tools.Mechanics.LOWERCASE_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;

/**
 * This class deals with the Astrology module. This module displays three different astrological symbols
 * that pertain to a particular index in the 3 2D arrays. These symbols will point to a particular number in
 * each array that'll be added up to determine the Omen that needs to be pressed.
 * (Good if positive, Bad if negative or No Omen if 0)
 * The quantity of the number is used to determine when to press the button.
 * (i.e. Bad Omen at 5 means that you need to press "Bad Omen" when the bomb timer has a 5 in it)
 */
public class Astrology extends Widget {
    public static final int ELEMENT_INDEX = 0, CELESTIAL_INDEX = 1, ZODIAC_INDEX = 2, EXPECTED_SIZE = 3;
    public static final String GOOD_OMEN = "Good Omen at ", POOR_OMEN = "Poor Omen at ", NO_OMEN = "No Omen";

    private static final int[][] stage1 = {{0,0,1,-1,0,1,-2,2,0,-1}, {-2,0,-1,0,2,0,-2,2,0,1},
            {-1,-1,0,-1,1,2,0,2,1,-2}, {-1,2,-1,0,-2,-1,0,2,-2,2}},

            stage2 = {{1,0,-1,0,0,2,2,0,1,0,1,0}, {2,2,-1,2,-1,-1,-2,1,2,0,0,2},
                    {-2,-1,0,0,1,0,1,2,-1,-2,1,1}, {1,1,-2,-2,2,0,-1,1,0,0,-1,-1}},

            stage3 = {{-1,-1,2,0,-1,0,-1,1,0,0,-2,-2}, {-2,0,1,0,2,0,-1,1,2,0,1,0},
                    {-2,-2,-1,-1,1,-1,0,-2,0,0,-1,1}, {-2,2,-2,0,0,1,-1,0,2,-2,-1,1},
                    {-2,0,-1,-2,-2,-2,-1,1,1,1,0,-1}, {-1,-2,1,-1,0,0,0,1,0,-1,2,0},
                    {-1,-1,0,0,1,1,0,0,0,0,-1,-1}, {-1,2,0,0,1,-2,1,0,2,-1,1,0},
                    {1,0,2,1,-1,1,1,1,0,-2,2,0}, {-1,0,0,-1,-2,1,2,1,1,0,0,-1}};

    /**
     * Calculates the phrase composed of the Omen to press and when to press it
     *
     * @param symbols The input symbols
     * @throws IllegalArgumentException Serial code is empty or there's an issue with the amount of symbols
     * @return The String command
     */
    public static String calculate(AstroSymbols ... symbols)
            throws IllegalArgumentException{
        checkThreeTypes(symbols);
        AstroSymbols[] sortSymbols = sort(symbols);

        int first = stage1[sortSymbols[ELEMENT_INDEX].getIdx()][sortSymbols[CELESTIAL_INDEX].getIdx()],
                second = stage2[sortSymbols[ELEMENT_INDEX].getIdx()][sortSymbols[ZODIAC_INDEX].getIdx()],
                third = stage3[sortSymbols[CELESTIAL_INDEX].getIdx()][sortSymbols[ZODIAC_INDEX].getIdx()];

        int results = first + second + third;
        results = checkMatchingSerialLetters(results, sortSymbols);

        return results > 0 ?
                GOOD_OMEN + results :
                (results == 0 ?
                        NO_OMEN :
                        POOR_OMEN + Math.abs(results));
    }

    /**
     * Checks the current set of symbols and compares them to letters in the Serial Code.
     * If the symbol has a letter that matches a letter in the Serial Code, it adds one to
     * the output value. If not, it'll subtract 1
     *
     * @param initialVal The output value
     * @param symbols The set of AstroSymbols
     * @return The resulting output value
     * @throws IllegalArgumentException The Serial Code is empty
     */
    private static int checkMatchingSerialLetters(int initialVal, AstroSymbols[] symbols)
            throws IllegalArgumentException{
        if (serialCode.isEmpty()) throw new IllegalArgumentException("Serial Code is required");

        String letters = ultimateFilter(serialCode, LOWERCASE_REGEX);
        for (AstroSymbols symbol : symbols){
            if (ultimateFilter(symbol.name(), letters).isEmpty()) initialVal--;
            else initialVal++;
        }
        return initialVal;
    }

    private static void checkThreeTypes(AstroSymbols[] symbols) throws IllegalArgumentException{
        if (symbols.length != EXPECTED_SIZE)
            throw new IllegalArgumentException("Astrology input size should be " + EXPECTED_SIZE);
        containsOneOfEach(symbols);
    }

    private static void containsOneOfEach(AstroSymbols[] symbols) throws IllegalArgumentException{
        containsOneCelestial(symbols);
        containsOneElement(symbols);
        containsOneZodiac(symbols);
    }

    private static void containsOneCelestial(AstroSymbols[] symbols){
        int count = 0;
        for (AstroSymbols symbol : symbols)
            if (isCelestial(symbol)) count++;
        if (count > 1) throw new IllegalArgumentException("Only one celestial body allowed");
    }

    private static void containsOneElement(AstroSymbols[] symbols){
        int count = 0;
        for (AstroSymbols symbol : symbols)
            if (isElemental(symbol)) count++;
        if (count > 1) throw new IllegalArgumentException("Only one element allowed");
    }

    private static void containsOneZodiac(AstroSymbols[] symbols){
        int count = 0;
        for (AstroSymbols symbol : symbols)
            if (isZodiac(symbol)) count++;
        if (count > 1) throw new IllegalArgumentException("Only one zodiac allowed");
    }

    private static AstroSymbols[] sort(AstroSymbols[] symbols){
        AstroSymbols[] output = new AstroSymbols[symbols.length];
        for (AstroSymbols symbol : symbols){
            if (isElemental(symbol)) output[ELEMENT_INDEX] = symbol;
            else if (isCelestial(symbol)) output[CELESTIAL_INDEX] = symbol;
            else if (isZodiac(symbol)) output[ZODIAC_INDEX] = symbol;
        }
        return output;
    }

    private static boolean isZodiac(AstroSymbols symbol){
        switch (symbol){
            case ARIES:
            case TAURUS:
            case GEMINI:
            case CANCER:
            case LEO:
            case VIRGO:
            case LIBRA:
            case SCORPIO:
            case SAGITTARIUS:
            case CAPRICORN:
            case AQUARIUS:
            case PISCES:
                return true;
        }
        return false;
    }

    private static boolean isCelestial(AstroSymbols symbol){
        switch (symbol){
            case SUN:
            case MOON:
            case MERCURY:
            case VENUS:
            case MARS:
            case JUPITER:
            case SATURN:
            case URANUS:
            case NEPTUNE:
            case PLUTO:
                return true;
        }
        return false;
    }

    private static boolean isElemental(AstroSymbols symbol){
        switch (symbol){
            case FIRE:
            case EARTH:
            case AIR:
            case WATER:
                return true;
        }
        return false;
    }
}
