package bomb;

import bomb.modules.ab.BlindAlley;
import bomb.modules.dh.ForgetMeNot;
import bomb.enumerations.Indicators;
import bomb.enumerations.Ports;
import bomb.enumerations.TriState;

import static bomb.tools.Mechanics.*;
import static bomb.enumerations.TriState.*;

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
    protected static Indicators[] list = Indicators.values();

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
        Widget.serialCode = ultimateFilter(serialCode, normalCharRegex);
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
    public static void setIndicator(TriState state, Indicators which){
        list[which.getIdx()].setProp(state);
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
     * Adds to the count a specified port, with a max of 9
     *
     * @param which The port to add on
     */
    public static void addPort(Ports which){
        if (ports[which.getIdx()] < 10){
            ports[which.getIdx()]++;
            BlindAlley.alleyUpdate();
        }
    }

    /**
     * Subtracts from the count of a specified port, with a minimum of 0
     *
     * @param which The port to subtract
     */
    public static void subPort(Ports which){
        if (ports[which.getIdx()] > 0){
            ports[which.getIdx()]--;
            BlindAlley.alleyUpdate();
        }
    }

    /**
     * Zeroes out the port count on a specified port
     *
     * @param which The port to zero out
     */
    public static void portZero(Ports which){
        ports[which.getIdx()] = 0;
        BlindAlley.alleyUpdate();
    }

    /**
     * Checks the Serial Code of an even number
     *
     * @return 0 for Yes, 1 for No, 2 for No Number
     */
    public static int hasEven(){
        String sample = ultimateFilter(serialCode, numberRegex);
        if (!sample.isEmpty()){
            for (char num : sample.toCharArray()){
                if ((int) num % 2 == 0){
                    return 0;
                }
            }
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
        String buffer = ultimateFilter(serialCode, numberRegex);
        return Integer.parseInt(buffer.substring(buffer.length()-1));
    }

    /**
     * Looks to if any listed Indicators are on the current bomb
     *
     * @param inds The array of possible Indicators
     * @return True if any Indicator is found
     */
    public static boolean hasFollowingInds(Indicators...inds){
        for (Indicators current : inds){
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
    public static boolean hasIndicator(Indicators ind){
        return hasLitIndicator(ind) || hasUnlitIndicator(ind);
    }

    /**
     * Checks to see if a specified lit Indicator is on the bomb
     *
     * @param ind The Indicator to check
     * @return True if the lit Indicator is found
     */
    public static boolean hasLitIndicator(Indicators ind){
        return list[ind.getIdx()].getProp() == ON;
    }

    /**
     * Checks to see if a specified unlit Indicator is on the bomb
     *
     * @param ind The Indicator to check
     * @return True if the unlit Indicator is found
     */
    public static boolean hasUnlitIndicator(Indicators ind){
        return list[ind.getIdx()].getProp() == OFF;
    }

    /**
     * Checks to see if the Serial Code has contains a vowel
     *
     * @return True if A,E,I,O, or U appear
     */
    public static boolean hasVowel(){
        return !ultimateFilter(serialCode, vowelRegex).isEmpty();
    }

    /**
     * Counts the number of letters that appear in the Serial Code
     *
     * @return The number of letters
     */
    public static int serialCodeLetters(){
        return ultimateFilter(serialCode, lowercaseRegex).length();
    }

    /**
     * Counts the number of numbers that appear in the Serial Code
     *
     * @return The number of numbers
     */
    public static int serialCodeNumbers(){
        return ultimateFilter(serialCode, numberRegex).length();
    }

    /**
     * Checks to see if the bomb contains more that the required amount of a specified ports
     *
     * @param port The port to check
     * @param howMany The required amount
     * @return True if the bomb contains more the required amount
     */
    public static boolean hasMoreThan(Ports port, int howMany){
        return ports[port.getIdx()] > howMany;
    }

    /**
     * Returns the number of battery holders
     *
     * @return The number of battery holders
     */
    public static int getNumHolders() {
        return numHolders;
    }

    /**
     * Returns the number of a specified port
     *
     * @param which The port to check
     * @return The number of that port
     */
    public static int getPort(Ports which){
        return ports[which.getIdx()];
    }

    /**
     * Counts the number of all ports on the bomb
     *
     * @return The total number of ports
     */
    public static int getTotalPorts(){
        int counter = 0;
        for (int num : ports){
            counter += num;
        }
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
        for (Indicators ind : list){
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
    public static boolean portExists(Ports port){
        return ports[port.getIdx()] > 0;
    }
}
