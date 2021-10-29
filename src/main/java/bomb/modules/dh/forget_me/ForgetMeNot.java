package bomb.modules.dh.forget_me;

import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.tools.filter.Filter;
import bomb.tools.filter.Regex;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntUnaryOperator;

import static bomb.Widget.IndicatorFilter.LIT;
import static bomb.Widget.IndicatorFilter.UNLIT;

public class ForgetMeNot extends Widget {
    private static final IntUnaryOperator LEAST_SIG_DIGIT = num -> num % 10;
    private static final IntUnaryOperator MOST_SIG_DIGIT =
            num -> (int) (num / Math.pow(10, Math.floor(Math.log10(num))));
    private static final List<Byte> FINAL_CODE = new ArrayList<>();

    private static byte largestSerialCodeNumber = -1;

    public static void add(int stageNumber) throws IllegalStateException {
        if (!isForgetMeNotActive)
            throw new IllegalStateException("Forget Me Not wasn't activated");

        if (largestSerialCodeNumber == -1)
            throw new IllegalStateException("The serial code was not set");

        if (numModules == 0)
            throw new IllegalArgumentException("Need to set the number of modules for this to work");

        FINAL_CODE.add(createNextNumber(stageNumber));
    }

    private static byte createNextNumber(int stageNumber) {
        if (FINAL_CODE.isEmpty())
            return (byte) LEAST_SIG_DIGIT.applyAsInt(createFirstNumber(stageNumber));

        if (FINAL_CODE.size() == 1)
            return (byte) LEAST_SIG_DIGIT.applyAsInt(createSecondNumber(stageNumber));

        return (byte) LEAST_SIG_DIGIT.applyAsInt(createSucceedingNumber(stageNumber));
    }

    private static int createFirstNumber(int stageNumber) {
        if (hasUnlitIndicator(Indicator.CAR))
            return stageNumber + 2;

        if (countIndicators(UNLIT) > countIndicators(LIT))
            return stageNumber + 7;

        if (countIndicators(UNLIT) == 0)
            return stageNumber + countIndicators(LIT);

        return stageNumber + getSerialCodeLastDigit();
    }

    private static int createSecondNumber(int stageNumber) {
        if (portExists(Port.SERIAL) && countNumbersInSerialCode() > 2)
            return stageNumber + largestSerialCodeNumber;

        return stageNumber + FINAL_CODE.get(0) +
                ((FINAL_CODE.get(0) % 2 == 0) ? 1 : -1);
    }

    private static int createSucceedingNumber(int stageNumber) {
        int length = FINAL_CODE.size();
        if (FINAL_CODE.get(length - 1) == 0 || FINAL_CODE.get(length - 2) == 0)
            return stageNumber + largestSerialCodeNumber;

        if (bothPreviousNumbersAreEven())
            return stageNumber + smallestOddDigitInSerialCode();

        return stageNumber + MOST_SIG_DIGIT.applyAsInt(
                FINAL_CODE.get(length - 1) + FINAL_CODE.get(length - 2)
        );
    }

    private static boolean bothPreviousNumbersAreEven() {
        int length = FINAL_CODE.size();
        return FINAL_CODE.get(length - 1) % 2 == 0 && FINAL_CODE.get(length - 2) % 2 == 0;
    }

    private static int smallestOddDigitInSerialCode() {
        int compare = 10;
        Regex singleNumberRegex = new Regex("\\d", serialCode);
        for (String num : singleNumberRegex) {
            if (Integer.parseInt(num) < compare)
                compare = Integer.parseInt(num);
        }
        return (compare % 2 == 1) ? compare : 9;
    }

    public static void updateLargestValueInSerial() {
        Filter.SERIAL_CODE_PATTERN.loadText(serialCode);
        if (!Filter.SERIAL_CODE_PATTERN.matchesRegex()) {
            largestSerialCodeNumber = -1;
            return;
        }

        Regex singleNumberRegex = new Regex("\\d", serialCode);
        //TODO
        if (!singleNumberRegex.findAllMatches().isEmpty()) {
            for (String num : singleNumberRegex) {
                if (Integer.parseInt(num) > largestSerialCodeNumber)
                    largestSerialCodeNumber = Byte.parseByte(num);
            }
        }
    }

    public static void undoLastStage() {
        if (FINAL_CODE.size() != 0) {
            FINAL_CODE.remove(FINAL_CODE.size() - 1);
        }
    }

    public static String stringifyFinalCode() {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= FINAL_CODE.size(); i++) {
            sb.append(FINAL_CODE.get(i - 1));
            if (i % 3 == 0 && i != FINAL_CODE.size())
                sb.append("-");
        }
        return sb.toString();
    }

    public static int getStage() {
        return FINAL_CODE.size();
    }

    public static void reset() {
        FINAL_CODE.clear();
        updateLargestValueInSerial();
    }
}
