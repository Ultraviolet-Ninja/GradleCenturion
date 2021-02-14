package bomb.modules.s.simon.screams;

import bomb.modules.s.simon.Simon;
import bomb.interfaces.Index;
import bomb.tools.Star;
import bomb.Widget;
import java.util.ArrayList;

/**
 *
 */
public class SimonScreams extends Widget {
    private static boolean initialized = false;
    private static int stage = 0;
    private static final ArrayList<Integer> currentOutputNumbers = new ArrayList<>();
    private static Star currentScreams;

    private static final String[][]
            // The output colors that depend on the edgework conditions and enum Letter
            resultingColors = {{"Yellow", "Orange", "Green", "Red", "Blue", "Purple"},
            {"Purple", "Yellow", "Red", "Blue", "Orange", "Green"},
            {"Orange", "Green", "Blue", "Purple", "Red", "Yellow"},
            {"Green", "Blue", "Orange", "Yellow", "Purple", "Red"},
            {"Red", "Purple", "Yellow", "Orange", "Green", "Blue"},
            {"Blue", "Red", "Purple", "Green", "Yellow", "Orange"}},

            // The letter sets that determine the column of colors to use in resultingColors 2D array
            categories = {{"FFC", "CEH", "HAF", "ECD", "DDE", "AHA"}, {"AHF", "DFC", "ECH", "CDE", "FEA", "HAD"},
                    {"DED", "ECF", "FHE", "HAA", "AFH", "CDC"}, {"HCE", "ADA", "CFD", "DHH", "EAC", "FEF"},
                    {"CAH", "FHD", "DDA", "AEC", "HCF", "EFE"}, {"EDA", "HAE", "AEC", "FFF", "CHD", "DCH"}};

    /**
     * Sets up the Star object and the output conditions for all stages of Simon Screams
     *
     * @param order - The order of the colors of in the star in clockwise order
     * @throws IllegalArgumentException - The serial code isn't 6 characters long
     */
    public static void init(Simon.Screams[] order) throws IllegalArgumentException{
        if (serialCode.length() == 6){
            initialized = true;
            outputConditions();
            currentScreams = new Star(order);
        } else throw new IllegalArgumentException("The serial code isn't 6 characters long");
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
            switch (getStringLetter(flashingOrder)) {
                case 'A': return findColors(Letters.A);
                case 'C': return findColors(Letters.C);
                case 'D': return findColors(Letters.D);
                case 'E': return findColors(Letters.E);
                case 'F': return findColors(Letters.F);
                default: return findColors(Letters.H);
            }
        } else throw new IllegalArgumentException("Initialization wasn't triggers first");
    }

    /**
     * Determines the row for the categories 2D array
     *
     * @param flashingOrder - The column to determine the letter set
     * @return - The letter determined by the correct array index and correct stage
     */
    private static char getStringLetter(Simon.Screams[] flashingOrder){
        if (currentScreams.threeAdjacency(flashingOrder)) return extract(flashingOrder[stage], 0);
        else if (currentScreams.oneTwoOne(flashingOrder)) return extract(flashingOrder[stage], 1);
        else if (currentScreams.primaryRule(flashingOrder)) return extract(flashingOrder[stage], 2);
        else if (currentScreams.complementRule(flashingOrder)) return extract(flashingOrder[stage], 3);
        else if (currentScreams.twoAdjacency(flashingOrder)) return extract(flashingOrder[stage], 4);
        else return extract(flashingOrder[stage], 5);
    }

    /**
     * Finds the letter to help determine the column of colors to press from categories 2D array
     *
     * @param stageColor - The column to determine the letter set
     * @param correctRule - The row to determine the letter set
     * @return - The letter determined by the correct array index and correct stage
     */
    private static char extract(Simon.Screams stageColor, int correctRule){
        return categories[correctRule][stageColor.getIdx()].charAt(stage++);
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
        for (Integer num : currentOutputNumbers){
            builder.append(resultingColors[num][current.getIdx()]).append(",");
        }
        return builder.substring(0,builder.toString().length()-1);
    }

    /**
     * Keeps track of the edgework that applies to the output colors
     *
     * @throws IllegalArgumentException - There's a problem with the edgework for the bomb
     */
    private static void outputConditions() throws IllegalArgumentException{
        if (countIndicators(true, true) >= 3) currentOutputNumbers.add(0);
        if (getTotalPorts() >= 3) currentOutputNumbers.add(1);
        if (serialCodeNumbers() >= 3) currentOutputNumbers.add(2);
        if (serialCodeLetters() >= 3) currentOutputNumbers.add(3);
        if (getAllBatteries() >= 3) currentOutputNumbers.add(4);
        if (getNumHolders() >= 3) currentOutputNumbers.add(5);
        if (currentOutputNumbers.size() == 0)
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

    private enum Letters implements Index {
        A(0), C(1), D(2), E(3), F(4), H(5);

        private final int idx;

        Letters(int idx){
            this.idx = idx;
        }

        @Override
        public int getIdx() {
            return idx;
        }
    }
}