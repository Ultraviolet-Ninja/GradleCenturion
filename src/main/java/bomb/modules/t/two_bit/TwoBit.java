package bomb.modules.t.two_bit;

import bomb.Widget;
import bomb.modules.s.souvenir.Souvenir;
import org.jetbrains.annotations.NotNull;

import static bomb.enumerations.Port.RCA;
import static bomb.enumerations.Port.RJ45;
import static bomb.modules.t.two_bit.TwoBitState.SECOND_QUERY;
import static bomb.modules.t.two_bit.TwoBitState.SUBMIT;
import static bomb.tools.filter.RegexFilter.CHAR_FILTER;
import static bomb.tools.filter.RegexFilter.NUMBER_PATTERN;
import static bomb.tools.filter.RegexFilter.filter;
import static bomb.tools.string.StringFormat.createOrdinalNumber;

/**
 *
 */
public class TwoBit extends Widget {
    public static final String QUERY_TEXT = "Query: ", SUBMIT_TEXT = "Submit: ";

    private static final char LETTER_TO_NUMBER_CONVERTER = '`';
    private static final String[][] CODE_GRID = {
            {"kb", "dk", "gv", "tk", "pv", "kp", "bv", "vt", "pz", "dt"},
            {"ee", "zk", "ke", "ck", "zp", "pp", "tp", "tg", "pd", "pt"},
            {"tz", "eb", "ec", "cc", "cz", "zv", "cv", "gc", "bt", "gt"},
            {"bz", "pk", "kz", "kg", "vd", "ce", "vb", "kd", "gg", "dg"},
            {"pb", "vv", "ge", "kv", "dz", "pe", "db", "cd", "td", "cb"},
            {"gb", "tv", "kk", "bg", "bp", "vp", "ep", "tt", "ed", "zg"},
            {"de", "dd", "ev", "te", "zd", "bb", "pc", "bd", "kc", "zb"},
            {"eg", "bc", "tc", "ze", "zc", "gp", "et", "vc", "tb", "vz"},
            {"ez", "ek", "dv", "cg", "ve", "dp", "bk", "pg", "gk", "gz"},
            {"kt", "ct", "zz", "vg", "gd", "cp", "be", "zt", "vk", "dc"}
    };

    private static TwoBitState currentState = SECOND_QUERY;

    /**
     * Determines the initial code for the module.
     *
     * @return The value in the {@link TwoBit#CODE_GRID}
     * @throws IllegalArgumentException The serial code was not set
     */
    public static String initialCode() throws IllegalArgumentException {
        checkSerialCode();
        String numbersInSerialCode = filter(serialCode, NUMBER_PATTERN);
        String first = filter(serialCode, CHAR_FILTER).toLowerCase();
        int alphabetBaseValue = !first.isEmpty() ?
                first.charAt(0) - LETTER_TO_NUMBER_CONVERTER :
                0;

        alphabetBaseValue += getAllBatteries() * Integer.parseInt(
                numbersInSerialCode.substring(numbersInSerialCode.length() - 1)
        );

        if (getPortQuantity(RCA) > 0 && getPortQuantity(RJ45) == 0) alphabetBaseValue *= 2;

        int[] bits = translateToBitCoordinates(String.valueOf(alphabetBaseValue % 100));

        if (isSouvenirActive)
            Souvenir.addRelic("TwoBit Initial Query", CODE_GRID[bits[0]][bits[1]]);

        currentState = SECOND_QUERY;
        return CODE_GRID[bits[0]][bits[1]];
    }

    /**
     * Gets the next code in the {@link TwoBit#CODE_GRID}
     *
     * @param code The next number code received from the Defuser
     * @return The next letter code along with a Query or Submit phrase
     * @throws IllegalArgumentException The given input was not 2 numbers
     */
    public static String nextCode(@NotNull String code) throws IllegalArgumentException {
        String newCode = filter(code, NUMBER_PATTERN);
        validateNextCode(code, newCode);
        int[] coords = translateToBitCoordinates(newCode);

        if (currentState == SUBMIT) {
            currentState = currentState.nextState();
            return SUBMIT_TEXT + CODE_GRID[coords[0]][coords[1]];
        }

        if (isSouvenirActive) {
            Souvenir.addRelic(
                    String.format("TwoBit %s Query", createOrdinalNumber(currentState.ordinal())),
                    String.format("%s - %s", newCode, CODE_GRID[coords[0]][coords[1]])
            );
        }
        currentState = currentState.nextState();
        return QUERY_TEXT + CODE_GRID[coords[0]][coords[1]];
    }

    private static void validateNextCode(String code, String newCode) {
        if (newCode.length() != 2) {
            throw new IllegalArgumentException(
                    "The provided code is not 2 numbers long: " + code + "(NUMBERS ONLY)"
            );
        }
    }

    private static int[] translateToBitCoordinates(String code) {
        int[] codeOut = new int[2];
        if (code.length() == 1) {
            codeOut[1] = Integer.parseInt(code);
            return codeOut;
        }

        codeOut[0] = Integer.parseInt(code.substring(0, 1));
        codeOut[1] = Integer.parseInt(code.substring(1));

        return codeOut;
    }

    public static void resetStage() {
        currentState = SECOND_QUERY;
    }
}