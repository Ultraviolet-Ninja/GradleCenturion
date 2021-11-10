package bomb.modules.ab.bitwise;

import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;

import static bomb.Widget.IndicatorFilter.LIT;
import static bomb.Widget.IndicatorFilter.UNLIT;
import static bomb.tools.filter.Filter.NUMBER_PATTERN;
import static bomb.tools.filter.Filter.ultimateFilter;

/**
 * This class deals with the Bitwise Operators module. Bitwise Ops is a screen containing a simple 8-bit
 * number line with a specific boolean operator that the Defuser reads to the expert. The class evaluates
 * which bits will be 1 or 0 in the byte line.
 */
public class Bitwise extends Widget {

    /**
     * Turns the edgework conditions into a byte that the Defuser will input into the bomb module
     *
     * @param bit The operation that must be used on each pair of bits
     * @return The String of 1's and 0's that the Defuser must put into the module screen
     * @throws IllegalArgumentException - The serial code, number of timer minutes and modules
     *                                  are needed for this module to work
     */
    public static String getByte(BitwiseOperator bit) throws IllegalArgumentException {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++)
            builder.append(solve(i, bit));
        return builder.toString();
    }

    /**
     * Evaluates the resulting bitOp from the position, the needed operation and the edgework conditions
     *
     * @param sigBit Which bitOp in the byte the method will focus on
     * @param bitOp  The operation that must be used on each pair of bits
     * @return The value of the resulting bitOp
     * @throws IllegalArgumentException - The serial code is needed for this module to work
     */
    private static int solve(int sigBit, BitwiseOperator bitOp) throws IllegalArgumentException {
        boolean[] bits = switch (sigBit) {
            case 0 -> firstBits();
            case 1 -> secondBits();
            case 2 -> thirdBits();
            case 3 -> forthBits();
            case 4 -> fifthBits();
            case 5 -> sixthBits();
            case 6 -> seventhBits();
            default -> eighthBits();
        };
        return operator(bitOp, bits) ? 1 : 0;
    }

    /**
     * Executes a boolean operation on the 2 bits to determine the resulting bit
     *
     * @param bitOp The operation to be executed on the bits
     * @param bits  The pair of bits that being compared
     * @return The result from the operation
     */
    private static boolean operator(BitwiseOperator bitOp, boolean[] bits) {
        return switch (bitOp) {
            case OR -> bits[0] || bits[1];
            case AND -> bits[0] && bits[1];
            case XOR -> ((bits[0] && !bits[1]) || (!bits[0] && bits[1]));
            default -> !bits[0];
        };
    }

    private static boolean[] firstBits() {
        return new boolean[]{numDoubleAs == 0, numDBatteries > 0};
    }

    private static boolean[] secondBits() {
        return new boolean[]{hasMorePortsThanSpecified(Port.PARALLEL, 0), calculateTotalPorts() > 2};
    }

    private static boolean[] thirdBits() {
        return new boolean[]{hasLitIndicator(Indicator.NSA), numHolders > 1};
    }

    private static boolean[] forthBits() throws IllegalArgumentException {
        if (numModules == 0 || numStartingMinutes == 0) throw new IllegalArgumentException(
                "Number of starting modules and minutes should be filled!");
        return new boolean[]{numModules > numStartingMinutes, hasLitIndicator(Indicator.BOB)};
    }

    private static boolean[] fifthBits() {
        return new boolean[]{countIndicators(LIT) > 1, countIndicators(UNLIT) > 1};
    }

    private static boolean[] sixthBits() throws IllegalArgumentException {
        if (serialCode.isEmpty())
            throw new IllegalArgumentException("You need the serial code to solve Bitwise Ops!");
        String nums = ultimateFilter(serialCode, NUMBER_PATTERN);
        return new boolean[]{numModules % 3 == 0, nums.charAt(nums.length() - 1) % 2 == 1};
    }

    private static boolean[] seventhBits() {
        return new boolean[]{numDBatteries < 2, numModules % 2 == 0};
    }

    private static boolean[] eighthBits() {
        return new boolean[]{calculateTotalPorts() < 4, getAllBatteries() > 1};
    }
}
