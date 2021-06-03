package bomb.modules.s.simon.screams;

import bomb.Widget;
import bomb.modules.s.simon.Simon;
import bomb.tools.OldStar;

import java.util.ArrayList;

/**
 *
 */
public class SimonScreams extends Widget {
    private static boolean initialized = false;
    private static int stage = 0;
    private static final ArrayList<Integer> CURRENT_OUTPUT_NUMBERS = new ArrayList<>();
    private static OldStar currentScreams;

    private static final String[][]
            // The output colors that depend on the edgework conditions and enum Letter
            RESULTING_COLORS = {{"Yellow", "Orange", "Green", "Red", "Blue", "Purple"},
            {"Purple", "Yellow", "Red", "Blue", "Orange", "Green"},
            {"Orange", "Green", "Blue", "Purple", "Red", "Yellow"},
            {"Green", "Blue", "Orange", "Yellow", "Purple", "Red"},
            {"Red", "Purple", "Yellow", "Orange", "Green", "Blue"},
            {"Blue", "Red", "Purple", "Green", "Yellow", "Orange"}},

            // The letter sets that determine the column of colors to use in resultingColors 2D array
            CATEGORIES = {{"FFC", "CEH", "HAF", "ECD", "DDE", "AHA"}, {"AHF", "DFC", "ECH", "CDE", "FEA", "HAD"},
                    {"DED", "ECF", "FHE", "HAA", "AFH", "CDC"}, {"HCE", "ADA", "CFD", "DHH", "EAC", "FEF"},
                    {"CAH", "FHD", "DDA", "AEC", "HCF", "EFE"}, {"EDA", "HAE", "AEC", "FFF", "CHD", "DCH"}};

    /**
     * Sets up the Star object and the output conditions for all stages of Simon Screams
     *
     * @param order - The order of the colors of in the star in clockwise order
     * @throws IllegalArgumentException - The serial code isn't 6 characters long OR
     *                                      The array is not 6 elements long
     */
    public static void init(Simon.Screams[] order) throws IllegalArgumentException{
        serialCodeChecker();
        initialized = true;
        outputConditions();
        currentScreams = new OldStar(order);
    }

    /**
     * Finds the colors to press for the current stage
     *
     * @param flashingOrder - The flashing order of the star in is current stage
     * @return - The resulting colors to pressed
     * @throws IllegalArgumentException - The init() method wasn't called first
     */
    public static String nextSolve(Simon.Screams[] flashingOrder) throws IllegalArgumentException{
        if (initialized) {
            return findColors(Letters.getFromChar(getStringLetter(flashingOrder)));
        } else throw new IllegalArgumentException("Initialization wasn't started");
    }

    /**
     * Determines the row for the categories 2D array
     *
     * @param flashingOrder - The column to determine the letter set
     * @return - The letter determined by the correct array index and correct stage
     */
    private static char getStringLetter(Simon.Screams[] flashingOrder){
        if (currentScreams.threeAdjacency(flashingOrder)) return extractCategory(flashingOrder[stage], 0);
        else if (currentScreams.oneTwoOne(flashingOrder)) return extractCategory(flashingOrder[stage], 1);
        else if (currentScreams.primaryRule(flashingOrder)) return extractCategory(flashingOrder[stage], 2);
        else if (currentScreams.complementRule(flashingOrder)) return extractCategory(flashingOrder[stage], 3);
        else if (currentScreams.twoAdjacency(flashingOrder)) return extractCategory(flashingOrder[stage], 4);
        else return extractCategory(flashingOrder[stage], 5);
    }

    /**
     * Finds the letter to help determine the column of colors to press from categories 2D array
     *
     * @param stageColor - The column to determine the letter set
     * @param correctRule - The row to determine the letter set
     * @return - The letter determined by the correct array index and correct stage
     */
    private static char extractCategory(Simon.Screams stageColor, int correctRule){
        return CATEGORIES[correctRule][stageColor.ordinal()].charAt(stage++);
    }

    /**
     * Lists the colors to be pressed for this stage determined by the currentOutputNumbers and
     * the letter from previously calculations
     *
     * @param current - The column to be used with the 2D array
     * @return - The colors that should be pressed
     */
    private static String findColors(Letters current){
        StringBuilder builder = new StringBuilder();
        for (Integer num : CURRENT_OUTPUT_NUMBERS){
            builder.append(RESULTING_COLORS[num][current.ordinal()]).append(",");
        }
        return builder.substring(0,builder.toString().length()-1);
    }

    /**
     * Keeps track of the edgework that applies to the output colors
     *
     * @throws IllegalArgumentException - There's a problem with the edgework for the bomb
     */
    private static void outputConditions() throws IllegalArgumentException{
        if (countIndicators(true, true) >= 3) CURRENT_OUTPUT_NUMBERS.add(0);
        if (getTotalPorts() >= 3) CURRENT_OUTPUT_NUMBERS.add(1);
        if (serialCodeNumbers() >= 3) CURRENT_OUTPUT_NUMBERS.add(2);
        if (serialCodeLetters() >= 3) CURRENT_OUTPUT_NUMBERS.add(3);
        if (getAllBatteries() >= 3) CURRENT_OUTPUT_NUMBERS.add(4);
        if (getNumHolders() >= 3) CURRENT_OUTPUT_NUMBERS.add(5);
        if (CURRENT_OUTPUT_NUMBERS.size() == 0)
            throw new IllegalArgumentException("Something is SERIOUSLY WRONG with the edgework");
    }

    /**
     * Will disable the module if it's finished the 3rd stage
     *
     * @return - if the current stage is 2 or not
     */
    public static boolean disableMod(){
        if (stage==2) initialized = false;
        return stage==2;
    }

    private enum Letters {
        A, C, D, E, F, H;

        public static Letters getFromChar(char letter){
            String sample = String.valueOf(letter);
            for (Letters let : Letters.values()){
                if (let.name().equals(sample)) return let;
            }
            return null;
        }
    }
}