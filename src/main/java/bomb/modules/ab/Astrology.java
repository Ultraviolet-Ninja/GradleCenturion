package bomb.modules.ab;

import bomb.Widget;
import bomb.enumerations.AstroSymbols;

import static bomb.tools.Mechanics.lowercaseRegex;
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
     * @param element The current elemental symbol
     * @param celestialBody The current celestial body symbol
     * @param zodiac The current Zodiac symbol
     * @return The String command
     */
    public static String calculate(AstroSymbols element, AstroSymbols celestialBody, AstroSymbols zodiac){
        int first = stage1[element.getIdx()][celestialBody.getIdx()],
                second = stage2[element.getIdx()][zodiac.getIdx()],
                third = stage3[celestialBody.getIdx()][zodiac.getIdx()];

        int results = first + second + third;
        results = checkSerial(results, element, celestialBody, zodiac);

        return results > 0 ? "Good Omen at " + results :
                (results == 0 ? "No Omen" :
                        "Bad Omen at " + Math.abs(results));
    }

    /**
     * Checks the current set of symbols and compares them to letters in the Serial Code.
     * If the symbol has a letter that matches a letter in the Serial Code, it adds one to
     * the output value. If not, it'll subtract 1
     *
     * @param in The output value
     * @param symbols The set of AstroSymbols
     * @return The resulting output value
     * @throws IllegalArgumentException The Serial Code is empty
     */
    private static int checkSerial(int in, AstroSymbols...symbols) throws IllegalArgumentException{
        if (serialCode.isEmpty()) throw new IllegalArgumentException("Serial Code is required");

        String letters = ultimateFilter(serialCode, lowercaseRegex);
        boolean skip;
        for (AstroSymbols symbol : symbols){
            skip = false;
            for (char letter : letters.toCharArray()){
                if (symbol.name().toLowerCase().indexOf(letter) != -1 && !skip){
                    in++;
                    skip = true;
                }
            }
            if (!skip) in--;
        }
        return in;
    }
}
