package bomb.modules.s.square.button;

import bomb.Widget;
import bomb.annotation.DisplayComponent;
import bomb.tools.number.MathUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Arrays;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static bomb.Widget.IndicatorFilter.LIT;
import static bomb.Widget.IndicatorFilter.UNLIT;
import static bomb.enumerations.ButtonResult.HOLD;
import static bomb.enumerations.ButtonResult.TAP;
import static bomb.tools.filter.RegexFilter.NUMBER_PATTERN;
import static bomb.tools.filter.RegexFilter.filter;
import static bomb.tools.string.StringFormat.FIRST_LETTER_CAPITAL;
import static java.util.stream.Collectors.joining;

@DisplayComponent(resource = "square_button.fxml", buttonLinkerName = "Square Button")
public final class SquareButton extends Widget {
    //Button colors
    public static final int BLUE = 0, YELLOW = 1, DARK_GRAY = 2, WHITE = 3;
    //Held button light colors
    public static final int ORANGE = 0, GREEN = 1, CYAN = 2;

    private static final Set<String> COLOR_WORDS = Set.of("Purple", "Indigo", "Maroon", "Jade");

    public static @NotNull String solve(@Range(from = BLUE, to = WHITE) int buttonColor,
                                        @NotNull String buttonText) throws IllegalArgumentException {
        checkSerialCode();
        validateButtonColor(buttonColor);
        var titleCaseText = FIRST_LETTER_CAPITAL.apply(buttonText);

//        if (buttonColor == BLUE && numDoubleAs > numDBatteries)//2
//            return HOLD.toString();
//
//        if (buttonColor <= YELLOW) {//3
//            if (matchesGreatestSerialCodeNumber(titleCaseText)) {//4
//                return TAP.toString();
//            } else if (COLOR_WORDS.contains(titleCaseText)) {//5
//                return HOLD.toString();
//            }
//        }

        switch (buttonColor) {
            case BLUE:
                if (numDoubleAs > numDBatteries) {
                    return HOLD.toString();
                }
            case YELLOW:
                if (matchesGreatestSerialCodeNumber(titleCaseText)) {
                    return TAP.toString();
                } else if (COLOR_WORDS.contains(titleCaseText)) {
                    return HOLD.toString();
                }
        }

        if (titleCaseText.isEmpty())//6
            return TAP + " when the two seconds digits on the timer match";

        return (buttonColor != DARK_GRAY && titleCaseText.length() > countIndicators(LIT)) ||//8
                (countIndicators(UNLIT) >= 2 && hasVowelInSerialCode()) ?//10
                TAP.toString() :
                HOLD.toString();
    }

    private static void validateButtonColor(int buttonColor) throws IllegalArgumentException {
        if (buttonColor < BLUE || buttonColor > WHITE)
            throw new IllegalArgumentException("Invalid button color");
    }

    private static boolean matchesGreatestSerialCodeNumber(String buttonText) {
        var numbersInCode = filter(serialCode, NUMBER_PATTERN).split("");
        return Arrays.stream(numbersInCode)
                .map(Integer::parseInt)
                .max(Integer::compareTo)
                .map(maxNumber -> maxNumber == buttonText.length())
                .orElse(false);
    }

    @SuppressWarnings("ConstantValue")
    public static @NotNull String solveForHeldButton(boolean isFlashing, @Range(from = ORANGE, to = CYAN) int lightColor)
            throws IllegalArgumentException {
        if (lightColor < ORANGE || lightColor > CYAN)
            throw new IllegalArgumentException("Invalid light color");

        return isFlashing ?
                handleFlashingLight(lightColor) :
                handleSolidLight(lightColor);
    }

    private static String handleFlashingLight(int lightColor){
        if (lightColor == ORANGE)
            return "Release when the number of seconds is 0 or prime\nPossible number combos: 0, " +
                    generatePrimeSeconds();
        if (lightColor == GREEN)
            return "Release one second after two seconds digits add up to a multiple 4\n" +
                    "Possible number combos: " + possibleCombinations(sum -> sum % 4 == 3);

        return "Release when the number of seconds remaining is a multiple of 7\n"
                + generateMultiplesOfSeven();
    }

    private static String handleSolidLight(int lightColor) {
        var stringBuilder = new StringBuilder("Release when the two seconds digits add up to ");
        IntPredicate numberToSum;

        switch (lightColor) {
            case ORANGE -> {
                stringBuilder.append("3 or 13");
                numberToSum = sum -> sum == 3 || sum == 13;
            }
            case GREEN -> {
                stringBuilder.append('5');
                numberToSum = sum -> sum == 5;
            }
            default -> {
                stringBuilder.append('7');
                numberToSum = sum -> sum == 7;
            }
        }

        return stringBuilder.append("\nPossible number combos: ")
                .append(possibleCombinations(numberToSum))
                .toString();
    }

    private static String possibleCombinations(IntPredicate numberToSum) {
        return IntStream.range(0, 6)
                .flatMap(i -> IntStream.range(0, 10).map(j -> i + j))
                .filter(numberToSum)
                .mapToObj(String::valueOf)
                .collect(joining(", "));
    }

    private static String generatePrimeSeconds() {
        return IntStream.range(2, 60)
                .filter(MathUtils::isPrime)
                .mapToObj(String::valueOf)
                .collect(joining(", "));
    }

    private static String generateMultiplesOfSeven() {
        if (numStartingMinutes == 0) {
            return """
                    Number of starting minutes not set.
                    Cannot discern valid multiples of 7""";
        }

        return "Possible combos: " + IntStream.rangeClosed(1, numStartingMinutes * 60)
                .filter(second -> second % 7 == 0)
                .mapToObj(second -> (second/60) + ":" + String.format("%02d", second % 60))
                .collect(joining(", "));
    }
}
