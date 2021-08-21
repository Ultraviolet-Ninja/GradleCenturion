package bomb.modules.t.two_bit;

import bomb.modules.s.souvenir.Souvenir;
import bomb.Widget;
import bomb.enumerations.Port;

import static bomb.modules.t.two_bit.TwoBitState.FIRST_QUERY;
import static bomb.tools.filter.Filter.CHAR_FILTER;
import static bomb.tools.filter.Filter.NUMBER_PATTERN;
import static bomb.tools.filter.Filter.ultimateFilter;

//TODO - Probably change var names, finish Javadocs and break down a few methods

/**
 *
 */
public class TwoBit extends Widget {
    public static final String QUERY = "Query: ", SUBMIT = "Submit: ";

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
            {"kt", "ct", "zz", "vg", "gd", "cp", "be", "zt", "vk", "dc"}};

    private static byte stage = 2;
    private static TwoBitState currentState = FIRST_QUERY;

    /**
     * @return
     * @throws IllegalArgumentException
     */
    public static String initialCode() throws IllegalArgumentException {
        serialCodeChecker();
        String first = ultimateFilter(serialCode, CHAR_FILTER).toLowerCase();
        int alphabetBaseValue = !first.isEmpty() ?
                first.charAt(0) - LETTER_TO_NUMBER_CONVERTER :
                0;
        String numbersInSerialCode = ultimateFilter(serialCode, NUMBER_PATTERN);
        alphabetBaseValue += getAllBatteries() * Integer.parseInt(numbersInSerialCode.substring(numbersInSerialCode.length() - 1));

        if (getPort(Port.RCA) > 0 && getPort(Port.RJ45) == 0) alphabetBaseValue *= 2;

        int[] bits = translateToBitCoordinates(String.valueOf(alphabetBaseValue % 100));
        if (isSouvenirActive)
            Souvenir.addRelic("TwoBit Initial Query", CODE_GRID[bits[0]][bits[1]]);
        return CODE_GRID[bits[0]][bits[1]];
    }

    /**
     * @param code
     * @return
     * @throws IllegalArgumentException
     */
    public static String nextCode(String code) throws IllegalArgumentException {
        String newCode = ultimateFilter(code, NUMBER_PATTERN);
        if (newCode.length() != 2) {
            throw new IllegalArgumentException(
                    "The provided code is not 2 numbers long: " + code + "(NUMBERS ONLY)"
            );
        }
        int[] coords = translateToBitCoordinates(newCode);
        if (stage != 4) {
            if (isSouvenirActive)
                Souvenir.addRelic("TwoBit" + ordinal(stage) + " Query", newCode + " - "
                        + CODE_GRID[coords[0]][coords[1]]);
            stage++;
            return QUERY + CODE_GRID[coords[0]][coords[1]];
        }
        stage = 2;
        return SUBMIT + CODE_GRID[coords[0]][coords[1]];
    }

    /**
     * @param code
     * @return
     */
    private static int[] translateToBitCoordinates(String code) {
        int[] codeOut = new int[2];
        if (code.length() == 1) {
            codeOut[1] = Integer.parseInt(code);
        } else {
            codeOut[0] = Integer.parseInt(code.substring(0, 1));
            codeOut[1] = Integer.parseInt(code.substring(1));
        }
        return codeOut;
    }

    /**
     * @param num
     * @return
     */
    private static String ordinal(int num) {
        return (num == 2) ? "2nd" : "3rd";
    }

    public static void resetStage() {
        stage = 2;
    }
}