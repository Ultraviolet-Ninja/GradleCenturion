package bomb.modules.t;

import bomb.modules.s.Souvenir;
import bomb.Widget;
import bomb.enumerations.Ports;

import static bomb.tools.Mechanics.*;

//TODO - Probably change var names, finish Javadocs and break down a few methods
/**
 *
 */
public class TwoBit extends Widget {
    private static final String[][] codes = {
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
    private static int stage = 2;

    /**
     *
     *
     * @return
     * @throws IllegalArgumentException
     */
    public static String initialCode() throws IllegalArgumentException{
        //TODO - Break down
        if (!serialCode.isEmpty()) {
            String first = ultimateFilter(serialCode, lowercaseRegex);
            int num = !first.isEmpty() ?
                    first.charAt(0) - 96 :
                    0;
            String numbers = ultimateFilter(serialCode, numberRegex);
            num += getAllBatteries() * Integer.parseInt(numbers.substring(numbers.length()-1));

            if (getPort(Ports.RCA) > 0 && getPort(Ports.RJ45) == 0) num *= 2;
            if (num > 99) num -= 100;

            int[] bits = translate(String.valueOf(num));
            if (Souvenir.getSet())
                Souvenir.addRelic("(TwoBit)1st Query", codes[bits[0]][bits[1]]);
            return codes[bits[0]][bits[1]];
        } else throw new IllegalArgumentException();
    }

    /**
     *
     *
     * @param code
     * @return
     * @throws IllegalArgumentException
     */
    public static String nextCode(String code) throws IllegalArgumentException{
        if (code.length() == 2) {
            int[] coords = translate(code);
            if (stage != 4) {
                if (Souvenir.getSet())
                    Souvenir.addRelic("(TwoBit)"+ ordinal(stage) + " Query", code + " - " +codes[coords[0]][coords[1]]);
                stage++;
                return "Query: " + codes[coords[0]][coords[1]];
            } else {
                stage = 2;
                return "Submit: " + codes[coords[0]][coords[1]];
            }
        } else throw new IllegalArgumentException();
    }

    /**
     *
     *
     * @param code
     * @return
     */
    private static int[] translate(String code){
        int[] codeOut = new int[2];
        if (code.length() == 1){
            codeOut[1] = Integer.parseInt(code);
        } else {
            codeOut[0] = Integer.parseInt(code.substring(0, 1));
            codeOut[1] = Integer.parseInt(code.substring(1));
        }
        return codeOut;
    }

    /**
     *
     *
     * @param num
     * @return
     */
    private static String ordinal(int num){
        return (num == 2)?"2nd":"3rd";
    }
}