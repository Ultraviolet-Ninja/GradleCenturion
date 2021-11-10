package bomb;

import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TrinarySwitch;
import bomb.modules.ab.blind_alley.BlindAlley;
import bomb.modules.dh.forget_me.ForgetMeNot;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    protected static boolean isSouvenirActive, isForgetMeNotActive;
    protected static int numDoubleAs, numDBatteries, numHolders, numModules, numPortPlates, numStartingMinutes;
    protected static String serialCode = "", twoFactor = "";
    protected static final Indicator[] indicatorArray = Indicator.values();

    private static int[] portArray = {0,0,0,0,0,0};

    public static void setNumHolders(int numHolders) {
        if (numHolders >= 0) {
            Widget.numHolders = numHolders;
            BlindAlley.alleyUpdate();
        }
    }

    public static void setStartTime(int startTime){
        if (startTime >= 0)
            numStartingMinutes = startTime;
    }

    public static void setSerialCode(String serialCode) {
        Widget.serialCode = serialCode;
        updatesModules();
    }

    private static void updatesModules(){
        BlindAlley.alleyUpdate();
        if (isForgetMeNotActive) ForgetMeNot.updateLargestValueInSerial();
    }

    public static void setDoubleAs(int doubleAs) {
        if (doubleAs >= 0) {
            numDoubleAs = doubleAs;
            BlindAlley.alleyUpdate();
        }
    }

    public static void setDBatteries(int dBatteries){
        if (dBatteries >= 0) {
            numDBatteries = dBatteries;
            BlindAlley.alleyUpdate();
        }
    }

    public static void setIndicator(TrinarySwitch state, Indicator which){
        indicatorArray[which.ordinal()].setState(state);
        BlindAlley.alleyUpdate();
    }

    public static void setNumModules(int numModules) {
        if (numModules >= 0) {
            Widget.numModules = numModules;
        }
    }

    public static void setNumberOfPlates(int plates) {
        if (plates >= 0) {
            numPortPlates = plates;
        }
    }

    public static void setTwoFactor(String twoFactor){
        Widget.twoFactor = twoFactor;
    }

    public static void setIsSouvenirActive(boolean set){
        isSouvenirActive = set;
    }

    public static void setIsForgetMeNotActive(boolean set){
        isForgetMeNotActive = set;
        updatesModules();
    }

    public static void setPortValue(Port which, int newValue){
        portArray[which.ordinal()] = newValue;
        BlindAlley.alleyUpdate();
    }

    public static boolean hasEvenNumberInSerialCode(){
        String sample = ultimateFilter(serialCode, NUMBER_PATTERN);
        if (sample.isEmpty())
            return false;

        for (char numberChar : sample.toCharArray()) {
            if (numberChar % 2 == 0)
                return true;
        }
        return false;
    }

    /**
     * Finds the last number in the Serial Code
     *
     * @return An int of the last digit from a String
     */
    public static int getSerialCodeLastDigit(){
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

    public static boolean hasVowelInSerialCode(){
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
    public static boolean hasMorePortsThanSpecified(Port port, int howMany){
        return portArray[port.ordinal()] > howMany;
    }

    public static int getNumHolders() {
        return numHolders;
    }

    public static int getNumModules(){
        return numModules;
    }

    public static int getPortQuantity(Port which){
        return portArray[which.ordinal()];
    }

    public static int calculateTotalPorts(){
        return  Arrays.stream(portArray).sum();
    }

    public static int countPortTypes(){
        return (int) Arrays.stream(portArray)
                .filter(port -> port > 0)
                .count();
    }

    public static boolean getIsForgetMeNotActive(){
        return isForgetMeNotActive;
    }

    public static boolean getIsSouvenirActive(){
        return isSouvenirActive;
    }

    public static EnumSet<Indicator> getFilteredSetOfIndicators(IndicatorFilter filter) {
        EnumSet<Indicator> allIndicators = EnumSet.allOf(Indicator.class);

        List<Indicator> tempList =  allIndicators.stream()
                .filter(indicator -> filter.test(indicator.getState()))
                .collect(Collectors.toList());

        return tempList.isEmpty() ?
                EnumSet.noneOf(Indicator.class) :
                EnumSet.copyOf(tempList);
    }

    /**
     * Counts all indicators, whether lit, unlit or all if specified
     *
     * @param filter Indicates what indicators should be counted, whether ON, OFF or both
     * @return The number of indicators
     */
    public static int countIndicators(IndicatorFilter filter){
        return getFilteredSetOfIndicators(filter).size();
    }

    public static int getAllBatteries(){
        return numDBatteries + numDoubleAs;
    }

    public static int getNumPortPlates() {
        return numPortPlates;
    }

    public static String getTwoFactor() {
        return twoFactor;
    }

    public static boolean doesPortExists(Port port){
        return portArray[port.ordinal()] > 0;
    }

    public static void checkSerialCode(){
        SERIAL_CODE_PATTERN.loadText(serialCode);
        if (!SERIAL_CODE_PATTERN.matchesRegex()) throw new IllegalArgumentException("Serial Code is required");
    }

    public static void resetProperties(){
        numDoubleAs = 0;
        numDBatteries = 0;
        numHolders = 0;
        numModules = 0;
        numPortPlates = 0;
        numStartingMinutes = 0;
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
        LIT(state -> state == ON), UNLIT(state -> state == OFF), ALL_PRESENT(state -> state != UNKNOWN);

        private final Predicate<TrinarySwitch> condition;

        IndicatorFilter(Predicate<TrinarySwitch> condition){
            this.condition = condition;
        }

        public boolean test(TrinarySwitch state){
            return condition.test(state);
        }
    }
}
