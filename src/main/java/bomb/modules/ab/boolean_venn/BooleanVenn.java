package bomb.modules.ab.boolean_venn;

import bomb.Widget;
import bomb.tools.filter.Regex;
import bomb.tools.logic.LogicOperator;

import static bomb.tools.filter.Filter.LOGIC_REGEX;
import static bomb.tools.filter.Filter.LOGIC_SYMBOL_FILTER;
import static bomb.tools.filter.Filter.ultimateFilter;
import static bomb.tools.logic.LogicOperator.AND;
import static bomb.tools.logic.LogicOperator.IMPLIED_BY;
import static bomb.tools.logic.LogicOperator.IMPLIES;
import static bomb.tools.logic.LogicOperator.NAND;
import static bomb.tools.logic.LogicOperator.NOR;
import static bomb.tools.logic.LogicOperator.OR;
import static bomb.tools.logic.LogicOperator.XNOR;
import static bomb.tools.logic.LogicOperator.XOR;

/**
 * This class deals with the Boolean Venn Diagram module.
 * The concept is based on the basics of Discrete Mathematics and uses basic boolean operators
 * to determine the state of each section of the triple venn diagram, green being true and red for false.
 */
public class BooleanVenn extends Widget {
    private static final boolean[][] TEST_CASES = {
            {false, false, false},
            {false, false, true},
            {false, true, false},
            {true, false, false},
            {false, true, true},
            {true, false, true},
            {true, true, false},
            {true, true, true}};
    private static final byte A = 0, B = 1, C = 2;

    private static final Regex AB_PRIORITY = new Regex("\\(A" + LOGIC_REGEX + "B\\)" + LOGIC_REGEX + "C"),
            BC_PRIORITY = new Regex("A" + LOGIC_REGEX + "\\(B" + LOGIC_REGEX + "C\\)");

    /**
     * Turns the String operation into a String code for the Venn Diagram to decode by choosing
     * the correct method depending on which side of the operation has the priority, that being
     * the operation between parenthesis
     *
     * @param operation The operation that the Defuser sees on the bomb
     * @return A String code that represents the state of each Venn Diagram section
     * The output order is not, c, b, a, bc, ac, ab, all
     * @throws IllegalArgumentException Format mismatch for the input equation
     */
    public static String resultCode(String operation) throws IllegalArgumentException {
        if (operation.isEmpty()) throw new IllegalArgumentException("Cannot have empty String");
        return checkFormat(operation) ?
                interpretAB(operation) :
                interpretBC(operation);
    }

    /**
     * Checks the formatting of the equation to see if it fits either of (AB)C or A(BC)
     * with logic symbols in between AB and BC
     *
     * @param equation The valid or invalid equation
     * @return Whether the equation matches the
     * @throws IllegalArgumentException Format mismatch for the input equation
     */
    private static boolean checkFormat(String equation) throws IllegalArgumentException {
        String abPriority = ultimateFilter(equation, AB_PRIORITY);
        String bcPriority = ultimateFilter(equation, BC_PRIORITY);

        if (XNOR.test(abPriority.isEmpty(), bcPriority.isEmpty()))
            throw new IllegalArgumentException("Format mismatch!!");
        return !abPriority.isEmpty();
    }

    /**
     * Interprets (AB)C
     *
     * @param operation The appropriate equation
     * @return A String code that represents the state of each Venn Diagram section
     * The output order is not, c, b, a, bc, ac, ab, all
     */
    private static String interpretAB(String operation) {
        String logicSymbols = ultimateFilter(operation, LOGIC_SYMBOL_FILTER);
        StringBuilder builder = new StringBuilder();
        boolean[] priorityCases = priorityOutputs(logicSymbols.substring(0, 1), A + B);

        for (int i = 0; i < TEST_CASES.length; i++)
            builder.append(outsideOutputs(logicSymbols.substring(1), priorityCases[i], TEST_CASES[i][C]));
        return builder.toString();
    }

    /**
     * Interprets A(BC)
     *
     * @param operation The appropriate equation
     * @return A String code that represents the state of each Venn Diagram section
     * The output order is not, c, b, a, bc, ac, ab, all
     */
    private static String interpretBC(String operation) {
        String logicSymbols = ultimateFilter(operation, LOGIC_SYMBOL_FILTER);
        StringBuilder builder = new StringBuilder();
        boolean[] priorityCases = priorityOutputs(logicSymbols.substring(1), B + C);

        for (int i = 0; i < TEST_CASES.length; i++)
            builder.append(outsideOutputs(logicSymbols.substring(0, 1), TEST_CASES[i][A], priorityCases[i]));
        return builder.toString();
    }

    /**
     * Performs the operation on the two variables inside the original equation's ()
     * and returns the outputs from those test cases
     *
     * @param func        The logic selector
     * @param priorityNum The determining number to reflect whether the method call came from ab or bc
     * @return A set of booleans that reflect all test cases possible for the operation inside the ()
     */
    private static boolean[] priorityOutputs(String func, int priorityNum) {
        boolean[] out = new boolean[TEST_CASES.length];
        if (priorityNum == 1) {
            for (int i = 0; i < TEST_CASES.length; i++)
                out[i] = functionMap(func, TEST_CASES[i][A], TEST_CASES[i][B]);
        } else {
            for (int i = 0; i < TEST_CASES.length; i++)
                out[i] = functionMap(func, TEST_CASES[i][B], TEST_CASES[i][C]);
        }
        return out;
    }

    /**
     * Returns 0 or 1 based on the boolean operation that gets past in
     *
     * @param func The logic selector
     * @param x    1st bit
     * @param y    2nd bit
     * @return 1 or 0 based on their respective booleans
     */
    private static String outsideOutputs(String func, boolean x, boolean y) {
        return functionMap(func, x, y) ? "1" : "0";
    }

    /**
     * Selects the bitwise operation to be executed
     *
     * @param func The number selector
     * @param x    1st bit
     * @param y    2nd bit
     * @return The result of the operation
     */
    private static boolean functionMap(String func, boolean x, boolean y) {
        LogicOperator operator = switch (func) {
            case "∧" -> AND;
            case "∨" -> OR;
            case "↓" -> NOR;
            case "⊻" -> XOR;
            case "|" -> NAND;
            case "↔" -> XNOR;
            case "→" -> IMPLIES;
            default -> IMPLIED_BY;
        };

        return operator.test(x, y);
    }
}
