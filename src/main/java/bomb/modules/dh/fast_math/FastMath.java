package bomb.modules.dh.fast_math;

import bomb.Widget;
import bomb.tools.filter.Regex;
import org.jetbrains.annotations.NotNull;

import static bomb.enumerations.Indicator.MSA;
import static bomb.enumerations.Port.RJ45;
import static bomb.enumerations.Port.SERIAL;
import static bomb.tools.filter.RegexFilter.EMPTY_FILTER_RESULTS;

public class FastMath extends Widget {
    private static final int[][] INTERNAL_GRID;

    /**
     * Turns the 2 letter combination of input turns it to a 2 number combination based on certain edgework
     * and what letters are set as input
     *
     * @param letters The 2 letters that must be input (Case insensitive)
     * @return The combination of numbers that the expert gives back to the defuser
     * @throws IllegalArgumentException If the input doesn't match specifications OR the serial code isn't given
     */
    public static @NotNull String solve(@NotNull String letters) throws IllegalArgumentException {
        checkSerialCode();
        letters = letters.toUpperCase();
        if (!letters.matches("[ABCDEGKNPSTXZ]{2}"))
            throw new IllegalArgumentException("Input 2 of the following letters: [A B C D E G K N P S T X Z]");

        int preconditions = evaluateEdgework();
        int leftNum = translateLetter(letters.charAt(0));
        int rightNum = translateLetter(letters.charAt(1));

        String outputValue = String.valueOf(postConditions(INTERNAL_GRID[leftNum][rightNum] + preconditions));
        return (outputValue.length() == 1 ? "0" : "") + outputValue;
    }

    private static int translateLetter(char letter) {
        return switch (letter) {
            case 'A' -> 0;
            case 'B' -> 1;
            case 'C' -> 2;
            case 'D' -> 3;
            case 'E' -> 4;
            case 'G' -> 5;
            case 'K' -> 6;
            case 'N' -> 7;
            case 'P' -> 8;
            case 'S' -> 9;
            case 'T' -> 10;
            case 'X' -> 11;
            default -> 12;
        };
    }

    private static int evaluateEdgework() {
        int output = hasLitIndicator(MSA) ? 20 : 0;
        output += doesPortExists(SERIAL) ? 14 : 0;
        output -= !EMPTY_FILTER_RESULTS.test(serialCode, new Regex("[fast]")) ? 5 : 0;
        output += doesPortExists(RJ45) ? 27 : 0;
        output -= getAllBatteries() > 3 ? 15 : 0;
        return output;
    }

    private static int postConditions(int num) {
        if (num > 100) return num % 100;
        if (num < 0) return num + 50;
        return num;
    }

    static {
        INTERNAL_GRID = new int[][]{
                {25, 11, 53, 97, 2, 42, 51, 97, 12, 86, 55, 73, 33},
                {54, 7, 32, 19, 84, 33, 27, 78, 26, 46, 9, 13, 58},
                {86, 37, 44, 1, 5, 26, 93, 49, 18, 69, 23, 40, 22},
                {54, 28, 77, 93, 11, 0, 35, 61, 27, 48, 13, 72, 80},
                {99, 36, 23, 95, 67, 5, 26, 17, 44, 60, 26, 41, 67},
                {74, 95, 3, 4, 56, 23, 54, 29, 52, 38, 10, 76, 98},
                {88, 46, 37, 96, 2, 52, 81, 37, 12, 70, 14, 36, 78},
                {54, 43, 12, 65, 94, 3, 47, 23, 16, 62, 73, 46, 21},
                {7, 33, 26, 1, 67, 26, 27, 77, 83, 14, 27, 93, 9},
                {63, 64, 94, 27, 48, 84, 33, 10, 16, 74, 43, 99, 4},
                {35, 39, 3, 25, 47, 62, 38, 45, 88, 48, 34, 31, 27},
                {67, 30, 27, 71, 9, 11, 44, 37, 18, 40, 32, 15, 78},
                {13, 23, 26, 85, 92, 12, 73, 56, 81, 7, 75, 47, 99}
        };
    }
}
