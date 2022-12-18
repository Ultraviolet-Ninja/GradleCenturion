package bomb.modules.dh.fizzbuzz;

import bomb.Widget;
import bomb.enumerations.Port;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;

public final class FizzBuzz extends Widget {
    public static @NotNull String solve(@NotNull String numberText, @NotNull FizzBuzzColor color,
                                        @Range(from=0, to=2) int strikeCount) throws IllegalArgumentException {
        validateInput(numberText, color, strikeCount);
        List<Integer> numberList = new ArrayList<>(7);

        if (numHolders > 2)
            numberList.add(interpretFirstCondition(color));

        if (doesPortExists(Port.SERIAL) && doesPortExists(Port.PARALLEL))
            numberList.add(interpretSecondCondition(color));

        if (countNumbersInSerialCode() == 3)
            numberList.add(interpretThirdCondition(color));

        if (doesPortExists(Port.DVI) && doesPortExists(Port.RCA))
            numberList.add(interpretFourthCondition(color));

        if (strikeCount == 2)
            numberList.add(interpretFifthCondition(color));

        if (getAllBatteries() > 4)
            numberList.add(interpretSixthCondition(color));

        int offset = numberList.isEmpty() ?
                interpretNoneApplyCondition(color):
                numberList.stream()
                        .mapToInt(i -> i)
                        .sum();

        return calculateResults(numberText, offset);
    }

    private static void validateInput(String numberText, FizzBuzzColor color, int strikeCount) {
        if (numberText == null || !numberText.matches("\\d{7}")) {
            throw new IllegalArgumentException("Text input must be a 7 digit number");
        }

        if (color == null) {
            throw new IllegalArgumentException("FizzBuzz color cannot be null");
        }

        if (strikeCount < 0 || strikeCount > 2) {
            throw new IllegalArgumentException("Invalid strike count");
        }
    }

    private static int interpretFirstCondition(FizzBuzzColor color) {
        return switch(color) {
            case RED -> 7;
            case BLUE -> 2;
            case YELLOW -> 4;
            case GREEN -> 3;
            default -> 5;
        };
    }

    private static int interpretSecondCondition(FizzBuzzColor color) {
        return switch(color) {
            case RED -> 3;
            case BLUE -> 9;
            case YELLOW -> 2;
            case GREEN -> 4;
            default -> 8;
        };
    }

    private static int interpretThirdCondition(FizzBuzzColor color) {
        return switch(color) {
            case RED -> 4;
            case BLUE, YELLOW -> 8;
            case GREEN -> 5;
            default -> 2;
        };
    }

    private static int interpretFourthCondition(FizzBuzzColor color) {
        return switch(color) {
            case RED -> 2;
            case BLUE -> 7;
            case YELLOW -> 9;
            case GREEN -> 3;
            default -> 1;
        };
    }

    private static int interpretFifthCondition(FizzBuzzColor color) {
        return switch(color) {
            case RED, GREEN -> 6;
            case BLUE -> 1;
            case YELLOW -> 2;
            default -> 8;
        };
    }

    private static int interpretSixthCondition(FizzBuzzColor color) {
        return switch(color) {
            case RED -> 1;
            case BLUE, GREEN -> 2;
            case YELLOW -> 5;
            default -> 3;
        };
    }

    private static int interpretNoneApplyCondition(FizzBuzzColor color) {
        return switch(color) {
            case RED, YELLOW -> 3;
            case BLUE -> 8;
            case GREEN -> 1;
            default -> 4;
        };
    }

    private static String calculateResults(String text, int offset) {
        int newValue;
        offset %= 10;
        StringBuilder output = new StringBuilder();
        if (offset != 0) {
            StringBuilder newNumberString = new StringBuilder();
            for (char c : text.toCharArray()) {
                int numberValue = (Character.getNumericValue(c) + offset) % 10;
                newNumberString.append(numberValue);
            }
            newValue = Integer.parseInt(newNumberString.toString());
        } else {
            newValue = Integer.parseInt(text);
        }

        if (newValue % 3 == 0)
            output.append("FIZZ");
        if (newValue % 5 == 0)
            output.append("BUZZ");

        return output.isEmpty() ?
                "NUMBER" :
                output.toString();
    }

    public enum FizzBuzzColor {
        RED, YELLOW, GREEN, BLUE, WHITE
    }
}
