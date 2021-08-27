package bomb;


import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TrinarySwitch;
import bomb.modules.ab.blind_alley.BlindAlley;
import bomb.modules.dh.forget_me.ForgetMeNot;

import java.util.function.Predicate;

import static bomb.enumerations.TrinarySwitch.OFF;
import static bomb.enumerations.TrinarySwitch.ON;
import static bomb.enumerations.TrinarySwitch.UNKNOWN;
import static bomb.tools.filter.Filter.CHAR_FILTER;
import static bomb.tools.filter.Filter.NUMBER_PATTERN;
import static bomb.tools.filter.Filter.SERIAL_CODE_PATTERN;
import static bomb.tools.filter.Filter.VOWEL_FILTER;
import static bomb.tools.filter.Filter.ultimateFilter;

/**
 * Widget class carries all the important widgets of the current bomb.
 * This class is extended by the Module classes, and all bomb widgets are accessible by those classes,
 * as well as the MainController to add/subtract to the widgets.
 */
public class Widget {
    protected static boolean isSouvenirActive = false, isForgetMeNotActive = false;
    protected static int numDoubleAs = 0,
            numDBatteries = 0,
            numHolders = 0,
            numModules = 0,
            numPlates = 0,
            numStartingMin = 0;
    protected static String serialCode = "", twoFactor = "";
    protected static Indicator[] indicatorArray = Indicator.values();

    private static int[] portArray = {0,0,0,0,0,0};
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
        if (isForgetMeNotActive) ForgetMeNot.updateLargestValueInSerial();
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
    public static void setIndicator(TrinarySwitch state, Indicator which){
        indicatorArray[which.ordinal()].setState(state);
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
    public static void setNumberOfPlates(int plates) {
        if (plates >= 0) {
            numPlates = plates;
        }
    }

    /**
     * Stores the current Two-Factor Authentication
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
    public static void setIsSouvenirActive(boolean set){
        isSouvenirActive = set;
    }

    /**
     * Sets whether the 'Forget Me Not' module is active on the Bomb
     *
     * @param set The toggle
     */
    public static void setIsForgetMeNotActive(boolean set){
        isForgetMeNotActive = set;
        updates();
    }

    /**
     * @param which The port to add to
     * @param newValue The value that will overwrite the array location
     */
    public static void setPortValue(Port which, int newValue){
        portArray[which.ordinal()] = newValue;
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
     * @param indicators The array of possible Indicators
     * @return True if any Indicator is found
     */
    public static boolean hasFollowingIndicators(Indicator...indicators){
        for (Indicator current : indicators){
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
        return indicatorArray[ind.ordinal()].getState() == ON;
    }

    /**
     * Checks to see if a specified unlit Indicator is on the bomb
     *
     * @param ind The Indicator to check
     * @return True if the unlit Indicator is found
     */
    public static boolean hasUnlitIndicator(Indicator ind){
        return indicatorArray[ind.ordinal()].getState() == OFF;
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
    public static int countLettersInSerialCode(){
        return ultimateFilter(serialCode, CHAR_FILTER).length();
    }

    /**
     * Counts the number of numbers that appear in the Serial Code
     *
     * @return The number of numbers
     */
    public static int countNumbersInSerialCode(){
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
        return portArray[port.ordinal()] > howMany;
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
    public static int getPortQuantity(Port which){
        return portArray[which.ordinal()];
    }

    /**
     * Counts the number of all ports on the bomb
     *
     * @return The total number of ports
     */
    public static int getTotalPorts(){
        int counter = 0;
        for (int num : portArray) counter += num;
        return counter;
    }

    /**
     *
     *
     * @return The number of port types on the bomb
     */
    public static int getPortTypes(){
        int counter = 0;
        for (int type : portArray){
            if (type > 0)
                counter++;
        }
        return counter;
    }

    public static boolean getIsForgetMeNotActive(){
        return isForgetMeNotActive;
    }

    public static boolean getIsSouvenirActive(){
        return isSouvenirActive;
    }

    /**
     * Counts all indicators, whether lit, unlit or all if specified
     *
     * @param filter Indicates what indicators should be counted, whether ON, OFF or both
     * @return The number of indicators
     */
    public static int countIndicators(IndicatorFilter filter){
        int counter = 0;
        for (Indicator indicator : indicatorArray) {
            if (filter.test(indicator.getState())) counter++;
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

    public static int getNumPlates() {
        return numPlates;
    }

    public static String getTwoFactor() {
        return twoFactor;
    }

    /**
     * Tests to see if a specified port exists on the current bomb
     *
     * @param port The port to test
     * @return True if the port is present somewhere on the Bomb
     */
    public static boolean portExists(Port port){
        return portArray[port.ordinal()] > 0;
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
        portArray = new int[]{0,0,0,0,0,0};
        setAllUnknown();
    }

    private static void setAllUnknown(){
        for (Indicator ind : indicatorArray)
            ind.setState(UNKNOWN);
    }

    public enum IndicatorFilter {
        LIT(state -> state == ON), UNLIT(state -> state == OFF), ALL(state -> state != UNKNOWN);

        private final Predicate<TrinarySwitch> condition;

        IndicatorFilter(Predicate<TrinarySwitch> condition){
            this.condition = condition;
        }

        public boolean test(TrinarySwitch state){
            return condition.test(state);
        }
    }
}
