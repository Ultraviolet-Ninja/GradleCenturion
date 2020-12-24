package bomb.modules.ab;

import bomb.Widget;

import static bomb.tools.Mechanics.ultimateFilter;

/**
 * This class deals with the Boolean Venn Diagram module.
 * The concept is based on the basics of Discrete Mathematics and uses basic boolean operators
 * to determine the state of each section of the triple venn diagram, green being true and red for false.
 */
public class BooleanVenn extends Widget {
    private static final boolean[][] testCases = {
            {false, false, false},
            {false, false, true},
            {false, true, false},
            {true, false, false},
            {false, true, true},
            {true, false, true},
            {true, true, false},
            {true, true, true}};
    private static final int A = 0, B = 1, C = 2;

    /**
     * Turns the String operation into a String code for the Venn Diagram to decode by choosing
     * the correct method depending on which side of the operation has the priority, that being
     * the operation between parenthesis
     *
     * @param operation The operation that the defuser sees on the bomb
     * @return A String code that represents the state of each Venn Diagram section
     */
    public static String resultCode(String operation){
        return (operation.charAt(0) != 'A')?abPriority(operation):bcPriority(operation);
    }

    /**
     * Performs the operation between A and B first, then adds the operation for C
     *
     * @param op The operation that the defuser sees on the bomb
     * @return A String code that represents the state of each Venn Diagram section
     */
    private static String abPriority(String op){
        String[] parts = op.split("\\)");
        StringBuilder builder = new StringBuilder();

        int[] functions = new int[]{detect(parts[0]), detect(parts[1])};
        boolean[] priorityCases = priorityOutputs(functions[0], A+B);

        for (int i = 0; i < testCases.length; i++)
            builder.append(outsideOutputs(functions[1], priorityCases[i], testCases[i][C]));
        return builder.toString();
    }

    /**
     * Performs the operation between B and C first, then adds A
     *
     * @param op The operation that the defuser sees on the bomb
     * @return A String code that represents the state of each Venn Diagram section
     */
    private static String bcPriority(String op){
        String[] parts = op.split("\\(");
        StringBuilder builder = new StringBuilder();

        int[] functions = new int[]{detect(parts[1]), detect(parts[0])};
        boolean[] priorityCases = priorityOutputs(functions[0], B+C);

        for (int i = 0; i < testCases.length; i++)
            builder.append(outsideOutputs(functions[1], testCases[i][A], priorityCases[i]));
        return builder.toString();
    }

    /**
     * Outputs the number selector based on the symbol that resembles a bitwise operator
     *
     * @param part The string that contains the operation
     * @return The number selector
     */
    private static int detect(String part){
        part = ultimateFilter(part, "∧", "∨", "↓", "⊻", "←", "→", "↔", "|");

        switch (part){
            case "∧":return 0; // And
            case "∨": return 1; // Or
            case "↓": return 2; // Nor
            case "⊻": return 3; // Xor
            case "|": return 4; // Nand
            case "↔": return 5; // Xnor
            case "→": return 6; // Implies
            default: return 7; // Implied By
        }
    }

    /**
     * Performs the operation on the two variables inside the original equation's ()
     * and returns the outputs from those test cases
     *
     * @param func The number selector
     * @param priorityNum The determining number to reflect whether the method call came from ab or bc
     * @return A set of booleans that reflect all test cases possible for the operation inside the ()
     */
    private static boolean[] priorityOutputs(int func, int priorityNum) {
        boolean[] out = new boolean[8];
        if (priorityNum == 1){
            for (int i = 0; i < testCases.length; i++)
                out[i] = functionMap(func, testCases[i][A], testCases[i][B]);
        } else {
            for (int i = 0; i < testCases.length; i++)
                out[i] = functionMap(func, testCases[i][B], testCases[i][C]);
        }
        return out;
    }

    /**
     * Returns 0 or 1 based on the boolean operation that gets passed in
     *
     * @param func The number selector
     * @param x 1st bit
     * @param y 2nd bit
     * @return 1 or 0 based on their respective booleans
     */
    private static String outsideOutputs(int func, boolean x, boolean y){
        return functionMap(func, x, y)?"1":"0";
    }

    /**
     * Selects the bitwise operation to be executed
     *
     * @param func The number selector
     * @param x 1st bit
     * @param y 2nd bit
     * @return The result of the operation
     */
    private static boolean functionMap(int func, boolean x, boolean y){
        switch (func){
            case 0: return and(x,y);
            case 1: return or(x,y);
            case 2: return nor(x,y);
            case 3: return xor(x,y);
            case 4: return nand(x,y);
            case 5: return xnor(x,y);
            case 6: return implies(x,y);
            default: return impliedBy(x,y);
        }
    }

    /**
     * Bitwise And
     *
     * @param x 1st bit
     * @param y 2nd bit
     * @return Result of the and operation
     */
    private static boolean and(boolean x, boolean y){
        return x && y;
    }

    /**
     * Bitwise Nand
     *
     * @param x 1st bit
     * @param y 2nd bit
     * @return Result of the nand operation
     */
    private static boolean nand(boolean x, boolean y){
        return !and(x, y);
    }

    /**
     * Bitwise Or
     *
     * @param x 1st bit
     * @param y 2nd bit
     * @return Result of the or operation
     */
    private static boolean or(boolean x, boolean y){
        return x || y;
    }

    /**
     * Bitwise Nor
     *
     * @param x - 1st bit
     * @param y - 2nd bit
     * @return - Result of the nor operation
     */
    private static boolean nor(boolean x, boolean y){
        return !or(x,y);
    }

    /**
     * Bitwise Xor
     *
     * @param x 1st bit
     * @param y 2nd bit
     * @return Result of the xor operation
     */
    private static boolean xor(boolean x, boolean y){
        return (x && !y) || (!x && y);
    }

    /**
     * Bitwise Xnor
     *
     * @param x 1st bit
     * @param y 2nd bit
     * @return Result of the xnor operation
     */
    private static boolean xnor(boolean x, boolean y){
        return !xor(x,y);
    }

    /**
     * Bitwise Implies follows this order...
     * False -> False - True
     * False -> True - True
     * True -> False - False
     * True -> True - True
     *
     * @param x 1st bit
     * @param y 2nd bit
     * @return Result of the implies operation
     */
    private static boolean implies(boolean x, boolean y){
        return !(x && !y);
    }

    /**
     * Bitwise Implied By follows this order...
     * False <- False - True
     * False <- True - False
     * True <- False - True
     * True <- True - True
     *
     * @param x 1st bit
     * @param y 2nd bit
     * @return Result of the implied by operation
     */
    private static boolean impliedBy(boolean x, boolean y){
        return !(!x && y);
    }
}
