package bomb;

import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TriState;
import bomb.modules.ab.blind_alley.BlindAlley;
import bomb.modules.dh.forget_me.ForgetMeNot;

import static bomb.enumerations.TriState.*;
import static bomb.tools.Filter.*;

/**
 * Widget class carries all the important widgets of the current bomb.
 * This class is extended by the Module classes, and all bomb widgets are accessible by those classes,
 * as well as the MainController to add/subtract to the widgets.
 */
public class Widget {
    protected static boolean souvenir = false, forgetMeNot = false;
    protected static int numDoubleAs = 0,
            numDBatteries = 0,
            numHolders = 0,
            numModules = 0,
            numPlates = 0,
            numStartingMin = 0;
    protected static String serialCode = "", twoFactor = "";
    protected static int[] ports = {0,0,0,0,0,0};
    protected static Indicator[] list = Indicator.values();

    /**
     * Sets the number of battery holders on the bomb
     *
     * @param numHolders The given number
     */
    public static void setNumHolders(int numHolders) {
        if (numHolders >= 0) {
            Widget.numHolders = numHolders;
            BlindAlley.alleyUpdate();
        }
    }

    /**
     * Sets the number of minutes on the bomb
     *
     * @param startTime The given number
     */
    public static void setStartTime(int startTime){
        if (startTime >= 0){
            numStartingMin = startTime;
        }
    }

    /**
     * Sets the Serial Code
     *
     * @param serialCode The given code
     */
    public static void setSerialCode(String serialCode) {
        Widget.serialCode = serialCode;
        updates();
    }

    /**
     * Records updates for Blind Alley and Forget Me Not that occur while doing edgework
     */
    private static void updates(){
        BlindAlley.alleyUpdate();
        ForgetMeNot.updateGreatest();
    }

    /**
     * Sets the number of double A's on the bomb
     *
     * @param doubleAs The given number
     */
    public static void setDoubleAs(int doubleAs) {
        if (doubleAs >= 0) {
            numDoubleAs = doubleAs;
            BlindAlley.alleyUpdate();
        }
    }

    /**
     * Sets the number of D batteries on the bomb
     *
     * @param dBatteries The given number
     */
    public static void setDBatteries(int dBatteries){
        if (dBatteries >= 0) {
            numDBatteries = dBatteries;
            BlindAlley.alleyUpdate();
        }
    }

    /**
     * Sets a specified Indicator with a specified state
     *
     * @param state The state to give the Indicator
     * @param which The Indicator to change
     */
    public static void setIndicator(TriState state, Indicator which){
        list[which.ordinal()].setProp(state);
        BlindAlley.alleyUpdate();
    }

    /**
     * Sets the number of modules on the bomb
     *
     * @param numModules The given number
     */
    public static void setNumModules(int numModules) {
        if (numModules >= 0) {
            Widget.numModules = numModules;
        }
    }

    /**
     * Sets the number of port plates on the bomb
     *
     * @param plates The given number
     */
    public static void setPlates(int plates){
        if (plates >= 0){
            numPlates = plates;
        }
    }

    /**
     * Stores the current Two Factor Authentication
     *
     * @param twoFactor The String containing the 2-Factor
     */
    public static void setTwoFactor(String twoFactor){
        Widget.twoFactor = twoFactor;
    }

    /**
     * Sets whether the Souvenir module is active on the Bomb
     *
     * @param set The toggle
     */
    public static void setSouvenir(boolean set){
        souvenir = set;
    }

    /**
     * Sets whether the Forget Me Not module is active on the Bomb
     *
     * @param set The toggle
     */
    public static void setForgetMeNot(boolean set){
        forgetMeNot = set;
    }

    /**
     * @param which The port to add to
     * @param newValue The value that will overwrite the array location
     */
    public static void setPortValue(Port which, int newValue){
        ports[which.ordinal()] = newValue;
        BlindAlley.alleyUpdate();
    }

    /**
     * Checks the Serial Code of an even number
     *
     * @return 0 for Yes, 1 for No, 2 for No Number
     */
    public static int hasEven(){
        //TODO - Might need to rename, hasEven sends the wrong message,
        // probably by adding even and odd number regexes
        String sample = ultimateFilter(serialCode, NUMBER_PATTERN);
        if (!sample.isEmpty()){
            for (char num : sample.toCharArray())
                if ((int) num % 2 == 0) return 0;
            return 1;
        }
        return 2;
    }

    /**
     * Finds the last number in the Serial Code
     *
     * @return An int of the last digit from a String
     */
    public static int lastDigit(){
        String buffer = ultimateFilter(serialCode, NUMBER_PATTERN);
        return Integer.parseInt(buffer.substring(buffer.length()-1));
    }

    /**
     * Looks to if any listed Indicators are on the current bomb
     *
     * @param inds The array of possible Indicators
     * @return True if any Indicator is found
     */
    public static boolean hasFollowingInds(Indicator...inds){
        for (Indicator current : inds){
            if (hasIndicator(current)) return true;
        }
        return false;
    }

    /**
     * Checks to see if a specified Indicator is on the bomb, whether lit or unlit
     *
     * @param ind The Indicator to check
     * @return True if the Indicator is found
     */
    public static boolean hasIndicator(Indicator ind){
        return hasLitIndicator(ind) || hasUnlitIndicator(ind);
    }

    /**
     * Checks to see if a specified lit Indicator is on the bomb
     *
     * @param ind The Indicator to check
     * @return True if the lit Indicator is found
     */
    public static boolean hasLitIndicator(Indicator ind){
        return list[ind.ordinal()].getProp() == ON;
    }

    /**
     * Checks to see if a specified unlit Indicator is on the bomb
     *
     * @param ind The Indicator to check
     * @return True if the unlit Indicator is found
     */
    public static boolean hasUnlitIndicator(Indicator ind){
        return list[ind.ordinal()].getProp() == OFF;
    }

    /**
     * Checks to see if the Serial Code has contains a vowel
     *
     * @return True if A,E,I,O, or U appear
     */
    public static boolean hasVowel(){
        return !ultimateFilter(serialCode, VOWEL_FILTER).isEmpty();
    }

    /**
     * Counts the number of letters that appear in the Serial Code
     *
     * @return The number of letters
     */
    public static int serialCodeLetters(){
        return ultimateFilter(serialCode, CHAR_FILTER).length();
    }

    /**
     * Counts the number of numbers that appear in the Serial Code
     *
     * @return The number of numbers
     */
    public static int serialCodeNumbers(){
        return ultimateFilter(serialCode, NUMBER_PATTERN).length();
    }

    /**
     * Checks to see if the bomb contains more that the required amount of a specified ports
     *
     * @param port The port to check
     * @param howMany The required amount
     * @return True if the bomb contains more the required amount
     */
    public static boolean hasMoreThan(Port port, int howMany){
        return ports[port.ordinal()] > howMany;
    }

    /**
     * Returns the number of battery holders
     *
     * @return The number of battery holders
     */
    public static int getNumHolders() {
        return numHolders;
    }

    public static int getNumModules(){
        return numModules;
    }

    /**
     * Returns the number of a specified port
     *
     * @param which The port to check
     * @return The number of that port
     */
    public static int getPort(Port which){
        return ports[which.ordinal()];
    }

    /**
     * Counts the number of all ports on the bomb
     *
     * @return The total number of ports
     */
    public static int getTotalPorts(){
        int counter = 0;
        for (int num : ports) counter += num;
        return counter;
    }

    /**
     *
     *
     * @return The number of port types on the bomb
     */
    public static int getPortTypes(){
        int counter = 0;
        for (int type : ports){
            if (type > 0)
                counter++;
        }
        return counter;
    }

    public static boolean getForgetMeNot(){
        return forgetMeNot;
    }

    public static boolean getSouvenir(){
        return souvenir;
    }

    /**
     * Counts all indicators, whether lit, unlit or all if specified
     *
     * @param lit Whether the lit or unlit indicators should be counted
     * @param all Whether all indicators should be counted
     * @return The number of indicators
     */
    public static int countIndicators(boolean lit, boolean all){
        int counter = 0;
        TriState current = lit?ON:OFF;
        for (Indicator ind : list){
            if (ind.getProp() == current && !all || (ind.getProp() != UNKNOWN && all))
                counter++;
        }
        return counter;
    }

    /**
     * Returns the sum of double A and D batteries
     *
     * @return The sum of all batteries
     */
    public static int getAllBatteries(){
        return numDBatteries + numDoubleAs;
    }

    /**
     * Tests to see if a specified port exists on the current bomb
     *
     * @param port The port to test
     * @return True if the port is present somewhere on the Bomb
     */
    public static boolean portExists(Port port){
        return ports[port.ordinal()] > 0;
    }

    public static void serialCodeChecker(){
        SERIAL_CODE_PATTERN.loadText(serialCode);
        if (!SERIAL_CODE_PATTERN.matchesRegex()) throw new IllegalArgumentException("Serial Code is required");
    }

    public static void resetProperties(){
        numDoubleAs = 0;
        numDBatteries = 0;
        numHolders = 0;
        numModules = 0;
        numPlates = 0;
        numStartingMin = 0;
        serialCode = "";
        twoFactor = "";
        ports = new int[]{0,0,0,0,0,0};
        setAllUnknown();
    }

    private static void setAllUnknown(){
        for (Indicator ind : list)
            ind.setProp(UNKNOWN);
    }
}
