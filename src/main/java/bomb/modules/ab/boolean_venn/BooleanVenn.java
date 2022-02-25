package bomb.modules.ab.boolean_venn;

import bomb.Widget;
import bomb.tools.logic.LogicOperator;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import static bomb.tools.filter.RegexFilter.LOGIC_REGEX;
import static bomb.tools.filter.RegexFilter.LOGIC_SYMBOL_FILTER;
import static bomb.tools.filter.RegexFilter.filter;
import static bomb.tools.logic.LogicOperator.LOGIC_SYMBOL_TO_ENUM_MAP;
import static bomb.tools.logic.LogicOperator.XNOR;

/**
 * This class deals with the Boolean Venn Diagram module.
 * The concept is based on the basics of Discrete Mathematics and uses basic boolean operators
 * to determine the state of each section of the triple venn diagram, green being true and red for false.
 */
public class BooleanVenn extends Widget {
    private static final boolean[][] TEST_CASES;
    private static final byte A = 0, B = 1, C = 2;

    @Language("regexp")
    private static final String AB_PRIORITY_PATTERN, BC_PRIORITY_PATTERN;

    static {
        AB_PRIORITY_PATTERN = "\\(A" + LOGIC_REGEX + "B\\)" + LOGIC_REGEX + "C";
        BC_PRIORITY_PATTERN = "A" + LOGIC_REGEX + "\\(B" + LOGIC_REGEX + "C\\)";

        TEST_CASES = new boolean[][]{
                {false, false, false},
                {false, false, true},
                {false, true, false},
                {true, false, false},
                {false, true, true},
                {true, false, true},
                {true, true, false},
                {true, true, true}
        };
    }

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
    public static @NotNull String resultCode(@NotNull String operation) throws IllegalArgumentException {
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
        boolean doesMatchABPriority = equation.matches(AB_PRIORITY_PATTERN);

        if (XNOR.test(doesMatchABPriority, equation.matches(BC_PRIORITY_PATTERN)))
            throw new IllegalArgumentException("Format given does not match the format specified");
        return doesMatchABPriority;
    }

    /**
     * Interprets (AB)C
     *
     * @param operation The appropriate equation
     * @return A String code that represents the state of each Venn Diagram section
     * The output order is not, c, b, a, bc, ac, ab, all
     */
    private static String interpretAB(String operation) {
        String logicSymbols = filter(operation, LOGIC_SYMBOL_FILTER);
        StringBuilder builder = new StringBuilder();
        boolean[] priorityCases = priorityOutputs(logicSymbols.substring(0, 1), B);

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
        String logicSymbols = filter(operation, LOGIC_SYMBOL_FILTER);
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
        LogicOperator operator = LOGIC_SYMBOL_TO_ENUM_MAP.get(func);

        boolean isPriorityOnFirstTwo = priorityNum == B;
        int firstIndex = isPriorityOnFirstTwo ? A : B;
        int secondIndex = isPriorityOnFirstTwo ? B : C;

        for (int i = 0; i < TEST_CASES.length; i++) {
            out[i] = operator.test(TEST_CASES[i][firstIndex], TEST_CASES[i][secondIndex]);
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
    private static char outsideOutputs(String func, boolean x, boolean y) {
        return LOGIC_SYMBOL_TO_ENUM_MAP.get(func).test(x, y) ? '1' : '0';
    }
}
