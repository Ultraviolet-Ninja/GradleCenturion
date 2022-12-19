package bomb;

import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TrinarySwitch;
import bomb.modules.ab.alphabet.Alphabet;
import bomb.modules.ab.astrology.Astrology;
import bomb.modules.ab.battleship.Battleship;
import bomb.modules.ab.blind.alley.BlindAlley;
import bomb.modules.ab.bitwise.Bitwise;
import bomb.modules.ab.bool.venn.diagram.BooleanVenn;
import bomb.modules.c.caesar.cipher.Caesar;
import bomb.modules.c.cheap.checkout.CheapCheckout;
import bomb.modules.c.chess.Chess;
import bomb.modules.c.chords.ChordQualities;
import bomb.modules.c.color.flash.ColorFlash;
import bomb.modules.dh.emoji.math.EmojiMath;
import bomb.modules.dh.fast.math.FastMath;
import bomb.modules.dh.fizzbuzz.FizzBuzz;
import bomb.modules.dh.forget.me.not.ForgetMeNot;
import bomb.modules.dh.hexamaze.Hexamaze;
import bomb.modules.il.ice.cream.IceCream;
import bomb.modules.il.laundry.Laundry;
import bomb.modules.il.led.encryption.LEDEncryption;
import bomb.modules.il.logic.Logic;
import bomb.modules.m.microcontroller.MicroController;
import bomb.modules.m.monsplode.MonslopeFight;
import bomb.modules.m.morsematics.Morsematics;
import bomb.modules.m.murder.Murder;
import bomb.modules.np.neutralization.Neutralization;
import bomb.modules.np.number.pad.NumberPad;
import bomb.modules.r.round.keypads.RoundKeypads;
import bomb.modules.s.seashells.Seashells;
import bomb.modules.s.semaphore.Semaphore;
import bomb.modules.s.shape.shift.ShapeShift;
import bomb.modules.s.simon.screams.SimonScreams;
import bomb.modules.s.simon.states.SimonStates;
import bomb.modules.s.souvenir.Souvenir;
import bomb.modules.s.square.button.SquareButton;
import bomb.modules.s.switches.Switches;
import bomb.modules.t.the.bulb.TheBulb;

import bomb.modules.t.translated.TranslationCenter;
import bomb.modules.t.translated.solutions.button.Button;
import bomb.modules.t.two.bit.TwoBit;
import bomb.modules.wz.word.search.WordSearch;
import bomb.modules.wz.zoo.Zoo;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

import static bomb.enumerations.TrinarySwitch.OFF;
import static bomb.enumerations.TrinarySwitch.ON;
import static bomb.enumerations.TrinarySwitch.UNKNOWN;
import static bomb.tools.filter.RegexFilter.CHAR_FILTER;
import static bomb.tools.filter.RegexFilter.EMPTY_FILTER_RESULTS;
import static bomb.tools.filter.RegexFilter.NUMBER_PATTERN;
import static bomb.tools.filter.RegexFilter.SERIAL_CODE_PATTERN;
import static bomb.tools.filter.RegexFilter.VOWEL_FILTER;
import static bomb.tools.filter.RegexFilter.filter;
import static java.util.Arrays.stream;

/**
 * Widget class carries all the important widgets of the current bomb.
 * This class is extended by the Module classes, and all bomb widgets are accessible by those classes,
 * as well as the MainController to add/subtract to the widgets.
 */
public sealed class Widget permits Alphabet, Astrology, Battleship, Bitwise, BlindAlley, BooleanVenn, Caesar,
        CheapCheckout, Chess, ChordQualities, ColorFlash, EmojiMath, FastMath, FizzBuzz, ForgetMeNot, Hexamaze,
        IceCream, Laundry, LEDEncryption, Logic, MicroController, MonslopeFight, Morsematics, Murder,
        Neutralization, NumberPad, RoundKeypads, Seashells, Semaphore, ShapeShift, SimonScreams, SimonStates, Souvenir,
        SquareButton, Switches, TheBulb, Button, TranslationCenter, TwoBit, WordSearch, Zoo {
    protected static boolean isSouvenirActive, isForgetMeNotActive;
    protected static int numDoubleAs, numDBatteries, numHolders, numModules, numPortPlates, numStartingMinutes;
    protected static String serialCode = "", twoFactor = "";

    protected static final Indicator[] INDICATOR_ARRAY = Indicator.values();

    private static int[] portArray = {0, 0, 0, 0, 0, 0};

    public static void setNumHolders(int numHolders) {
        if (numHolders >= 0) {
            Widget.numHolders = numHolders;
        }
    }

    public static void setStartTime(int startTime) {
        if (startTime >= 0)
            numStartingMinutes = startTime;
    }

    public static void setSerialCode(String serialCode) {
        Widget.serialCode = serialCode;
        updatesModules();
    }

    private static void updatesModules() {
        if (isForgetMeNotActive) ForgetMeNot.updateLargestValueInSerial();
    }

    public static void setDoubleAs(int doubleAs) {
        if (doubleAs >= 0) {
            numDoubleAs = doubleAs;
        }
    }

    public static void setDBatteries(int dBatteries) {
        if (dBatteries >= 0) {
            numDBatteries = dBatteries;
        }
    }

    public static void setIndicator(@NotNull TrinarySwitch state, @NotNull Indicator which) {
        INDICATOR_ARRAY[which.ordinal()].setState(state);
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

    public static void setTwoFactor(String twoFactor) {
        Widget.twoFactor = twoFactor;
    }

    public static void setIsSouvenirActive(boolean set) {
        isSouvenirActive = set;
    }

    public static void setIsForgetMeNotActive(boolean set) {
        isForgetMeNotActive = set;
        updatesModules();
    }

    public static void setPortValue(@NotNull Port which, int newValue) {
        portArray[which.ordinal()] = newValue;
    }

    public static int getNumHolders() {
        return numHolders;
    }

    public static int getNumModules() {
        return numModules;
    }

    public static int getPortQuantity(@NotNull Port which) {
        return portArray[which.ordinal()];
    }

    public static String getSerialCode() {
        return serialCode;
    }

    public static int countPortTypes() {
        return (int) stream(portArray)
                .filter(port -> port > 0)
                .count();
    }

    public static boolean getIsForgetMeNotActive() {
        return isForgetMeNotActive;
    }

    public static boolean getIsSouvenirActive() {
        return isSouvenirActive;
    }

    public static int getAllBatteries() {
        return numDBatteries + numDoubleAs;
    }

    public static int getNumPortPlates() {
        return numPortPlates;
    }

    public static String getTwoFactor() {
        return twoFactor;
    }

    protected static void checkSerialCode() throws IllegalArgumentException {
        SERIAL_CODE_PATTERN.loadText(serialCode);
        if (!SERIAL_CODE_PATTERN.matchesRegex())
            throw new IllegalArgumentException("""
                Serial Code is required
                Please check formatting on Widget page""");
    }

    protected static boolean hasEvenNumberInSerialCode() {
        String sample = filter(serialCode, NUMBER_PATTERN);

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
    public static int getSerialCodeLastDigit() {
        return Character.getNumericValue(serialCode.charAt(serialCode.length() - 1));
    }

    /**
     * Looks to if any listed Indicators are on the current bomb
     *
     * @param indicators The array of possible Indicators
     * @return True if any Indicator is found
     */
    protected static boolean hasFollowingIndicators(Indicator @NotNull ... indicators) {
        for (Indicator current : indicators) {
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
    public static boolean hasIndicator(@NotNull Indicator ind) {
        return hasLitIndicator(ind) || hasUnlitIndicator(ind);
    }

    /**
     * Checks to see if a specified lit Indicator is on the bomb
     *
     * @param ind The Indicator to check
     * @return True if the lit Indicator is found
     */
    protected static boolean hasLitIndicator(@NotNull Indicator ind) {
        return INDICATOR_ARRAY[ind.ordinal()].getState() == ON;
    }

    /**
     * Checks to see if a specified unlit Indicator is on the bomb
     *
     * @param ind The Indicator to check
     * @return True if the unlit Indicator is found
     */
    protected static boolean hasUnlitIndicator(@NotNull Indicator ind) {
        return INDICATOR_ARRAY[ind.ordinal()].getState() == OFF;
    }

    public static boolean hasVowelInSerialCode() {
        return !EMPTY_FILTER_RESULTS.test(serialCode, VOWEL_FILTER);
    }

    /**
     * Counts the number of letters that appear in the Serial Code
     *
     * @return The number of letters
     */
    public static int countLettersInSerialCode() {
        return filter(serialCode, CHAR_FILTER).length();
    }

    /**
     * Counts the number of numbers that appear in the Serial Code
     *
     * @return The number of numbers
     */
    public static int countNumbersInSerialCode() {
        return filter(serialCode, NUMBER_PATTERN).length();
    }

    /**
     * Checks to see if the bomb contains more that the required amount of a specified ports
     *
     * @param port    The port to check
     * @param howMany The required amount
     * @return True if the bomb contains more the required amount
     */
    protected static boolean hasMorePortsThanSpecified(@NotNull Port port, int howMany) {
        return portArray[port.ordinal()] > howMany;
    }

    public static int calculateTotalPorts() {
        return stream(portArray).sum();
    }

    protected static EnumSet<Indicator> getFilteredSetOfIndicators(IndicatorFilter filter) {
        EnumSet<Indicator> allIndicators = EnumSet.allOf(Indicator.class);

        List<Indicator> tempList = allIndicators.stream()
                .filter(indicator -> filter.test(indicator.getState()))
                .toList();

        return tempList.isEmpty() ?
                EnumSet.noneOf(Indicator.class) :
                EnumSet.copyOf(tempList);
    }

    public static boolean doesPortExists(@NotNull Port port) {
        return portArray[port.ordinal()] > 0;
    }

    /**
     * Counts all indicators, whether lit, unlit or all if specified
     *
     * @param filter Indicates what indicators should be counted, whether ON, OFF or both
     * @return The number of indicators
     */
    public static int countIndicators(IndicatorFilter filter) {
        return getFilteredSetOfIndicators(filter).size();
    }

    public static void resetProperties() {
        numDoubleAs = 0;
        numDBatteries = 0;
        numHolders = 0;
        numModules = 0;
        numPortPlates = 0;
        numStartingMinutes = 0;
        serialCode = "";
        twoFactor = "";
        portArray = new int[]{0, 0, 0, 0, 0, 0};

        for (Indicator ind : INDICATOR_ARRAY)
            ind.setState(UNKNOWN);
    }

    public enum IndicatorFilter implements Predicate<TrinarySwitch> {
        LIT {
            @Override
            public boolean test(TrinarySwitch state) {
                return state == ON;
            }
        }, UNLIT {
            @Override
            public boolean test(TrinarySwitch state) {
                return state == OFF;
            }
        }, ALL_PRESENT {
            @Override
            public boolean test(TrinarySwitch state) {
                return state != UNKNOWN;
            }
        }
    }
}
