package bomb.modules.s.simon.screams;

import bomb.Widget;
import bomb.modules.s.simon.SimonColors.ScreamColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimonScreams extends Widget {
    private static final int MAX_OUTPUT_RULES = 6;
    private static final List<Integer> CURRENT_OUTPUT_NUMBERS;
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

    private static boolean initialized;
    private static int stage;
    private static Star lightOrder;

    static {
        initialized = false;
        stage = 0;
        CURRENT_OUTPUT_NUMBERS = new ArrayList<>(MAX_OUTPUT_RULES);
    }

    /**
     * Sets up the Star object and the output conditions for all stages of Simon Screams
     *
     * @param order - The order of the colors of in the star in clockwise order
     * @throws IllegalArgumentException - The serial code isn't 6 characters long OR
     *                                  The array is not 6 elements long
     */
    public static void initialize(@NotNull ScreamColor[] order) throws IllegalArgumentException {
        checkSerialCode();
        initialized = true;
        setOutputRules();
        lightOrder = new Star(order);
    }

    /**
     * Finds the colors to press for the current stage
     *
     * @param flashingOrder - The flashing order of the star in is current stage
     * @return - The resulting colors to pressed
     * @throws IllegalArgumentException - The init() method wasn't called first
     */
    public static String nextSolve(@NotNull ScreamColor[] flashingOrder) throws IllegalArgumentException {
        if (flashingOrder.length == 0)
            throw new IllegalArgumentException("No colors were selected");
        if (!initialized) throw new IllegalArgumentException("Initialization wasn't started");
        String letter = String.valueOf(getStringLetter(flashingOrder));
        return findColors(Letters.valueOf(letter));
    }

    /**
     * Determines the row for the categories 2D array
     *
     * @param flashingOrder - The column to determine the letter set
     * @return - The letter determined by the correct array index and correct stage
     */
    private static char getStringLetter(ScreamColor[] flashingOrder) {
        if (lightOrder.threeAdjacencyRule(flashingOrder)) return extractCategory(flashingOrder[stage], 0);
        if (lightOrder.oneTwoOneRule(flashingOrder)) return extractCategory(flashingOrder[stage], 1);
        if (lightOrder.primaryRule(flashingOrder)) return extractCategory(flashingOrder[stage], 2);
        if (lightOrder.complementRule(flashingOrder)) return extractCategory(flashingOrder[stage], 3);
        if (lightOrder.twoAdjacencyRule(flashingOrder)) return extractCategory(flashingOrder[stage], 4);
        return extractCategory(flashingOrder[stage], 5);
    }

    /**
     * Finds the letter to help determine the column of colors to press from categories 2D array
     *
     * @param stageColor  - The column to determine the letter set
     * @param correctRule - The row to determine the letter set
     * @return - The letter determined by the correct array index and correct stage
     */
    private static char extractCategory(ScreamColor stageColor, int correctRule) {
        return CATEGORIES[correctRule][stageColor.ordinal()].charAt(stage++);
    }

    /**
     * Lists the colors to be pressed for this stage determined by the currentOutputNumbers and
     * the letter from previously calculations
     *
     * @param current - The column to be used with the 2D array
     * @return - The colors that should be pressed
     */
    private static String findColors(Letters current) {
        return CURRENT_OUTPUT_NUMBERS.stream()
                .map(index -> RESULTING_COLORS[index][current.ordinal()])
                .collect(Collectors.joining(","));
    }

    /**
     * Keeps track of the edgework that applies to the output colors
     */
    private static void setOutputRules() {
        if (countIndicators(IndicatorFilter.ALL_PRESENT) >= 3) CURRENT_OUTPUT_NUMBERS.add(0);
        if (calculateTotalPorts() >= 3) CURRENT_OUTPUT_NUMBERS.add(1);
        if (countNumbersInSerialCode() >= 3) CURRENT_OUTPUT_NUMBERS.add(2);
        if (countLettersInSerialCode() >= 3) CURRENT_OUTPUT_NUMBERS.add(3);
        if (getAllBatteries() >= 3) CURRENT_OUTPUT_NUMBERS.add(4);
        if (getNumHolders() >= 3) CURRENT_OUTPUT_NUMBERS.add(5);
    }

    public static int getStage() {
        return stage;
    }

    public static void resetLastStage() {
        if (stage != 0) stage--;
    }

    public static void reset() {
        stage = 0;
        initialized = false;
        lightOrder = null;
        CURRENT_OUTPUT_NUMBERS.clear();
    }

    private enum Letters {
        A, C, D, E, F, H
    }
}