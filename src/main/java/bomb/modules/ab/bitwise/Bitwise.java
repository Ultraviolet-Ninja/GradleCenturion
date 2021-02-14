package bomb.modules.ab.bitwise;

import bomb.Widget;
import bomb.enumerations.Indicators;
import bomb.enumerations.Ports;

import static bomb.tools.Mechanics.NUMBER_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;

/**
 * This class deals with the Bitwise Operators module. Bitwise Ops is a screen containing a simple 8-bit
 * number line with a specific boolean operator that the defuser reads to the expert. The class evaluates
 * which bits will be 1 or 0 in the byte line.
 */
public class Bitwise extends Widget {

    //TODO - Finish Javadocs
    /**
     * Turns the edgework conditions into a byte that the defuser will input into the bomb module
     *
     * @param bit The operation that must be used on each pair of bits
     * @return The String of 1's and 0's that the defuser must put into the module screen
     * @throws IllegalArgumentException - The serial code, number of timer minutes and modules
     * are needed for this module to work
     */
    public static String getByte(BitwiseOps bit) throws IllegalArgumentException{
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++)
            builder.append(solve(i, bit));
        return builder.toString();
    }

    /**
     * Evaluates the resulting bitOp from the position, the needed operation and the edgework conditions
     *
     * @param sigBit Which bitOp in the byte the method will focus on
     * @param bitOp The operation that must be used on each pair of bits
     * @return The value of the resulting bitOp
     * @throws IllegalArgumentException - The serial code is needed for this module to work
     */
    private static int solve(int sigBit, BitwiseOps bitOp) throws IllegalArgumentException{
        boolean[] bits;
        switch (sigBit) {
            case 0: bits = firstBits(); break;
            case 1: bits = secondBits(); break;
            case 2: bits = thirdBits(); break;
            case 3: bits = forthBits(); break;
            case 4: bits = fifthBits(); break;
            case 5: bits = sixthBits(); break;
            case 6: bits = seventhBits(); break;
            default: bits = eighthBits(); break;
        }
        return operator(bitOp, bits)?1:0;
    }

    /**
     * Executes a boolean operation on the 2 bits to determine the resulting bit
     *
     * @param bitOp The operation to be executed on the bits
     * @param bits
     * @return The result from the operation
     */
    private static boolean operator(BitwiseOps bitOp, boolean[] bits){
        switch (bitOp) {
            case OR: return bits[0] || bits[1];
            case AND: return bits[0] && bits[1];
            case XOR: return ((bits[0] && !bits[1])||(!bits[0] && bits[1]));
            default: return !bits[0];
        }
    }

    private static boolean[] firstBits(){
        return new boolean[]{numDoubleAs == 0, numDBatteries > 0};
    }

    private static boolean[] secondBits(){
        return new boolean[]{hasMoreThan(Ports.PARALLEL, 0), getTotalPorts() > 2};
    }

    private static boolean[] thirdBits(){
        return new boolean[]{hasLitIndicator(Indicators.NSA), numHolders > 1};
    }

    private static boolean[] forthBits() throws IllegalArgumentException{
        if (numModules == 0 || numStartingMin == 0) throw new IllegalArgumentException(
                "Number of starting modules and minutes should be filled!");
        return new boolean[]{numModules > numStartingMin, hasLitIndicator(Indicators.BOB)};
    }

    private static boolean[] fifthBits(){
        return new boolean[]{countIndicators(true, false) > 1, countIndicators(false, false) > 1};
    }

    private static boolean[] sixthBits() throws IllegalArgumentException{
        if (serialCode.isEmpty())
            throw new IllegalArgumentException("You need the serial code to solve Bitwise Ops!");
        String nums = ultimateFilter(serialCode, NUMBER_REGEX);
        return new boolean[]{numModules % 3 == 0, nums.charAt(nums.length() - 1) % 2 == 1};
    }

    private static boolean[] seventhBits(){
        return new boolean[]{numDBatteries < 2, numModules % 2 == 0};
    }

    private static boolean[] eighthBits(){
        return new boolean[]{getTotalPorts() < 4, getAllBatteries() > 1};
    }
}
