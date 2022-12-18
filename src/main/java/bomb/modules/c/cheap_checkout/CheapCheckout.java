package bomb.modules.c.cheap_checkout;

import bomb.Widget;
import bomb.annotation.Puzzle;
import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.util.List;
import java.util.function.ToDoubleFunction;

import static bomb.modules.c.cheap_checkout.CheckoutItem.Category.FRUIT;
import static bomb.modules.c.cheap_checkout.CheckoutItem.Category.SWEET;
import static bomb.tools.number.MathUtils.digitalRoot;
import static bomb.tools.number.MathUtils.roundToNPlaces;

@Puzzle(resource = "cheap_checkout.fxml", buttonLinkerName = "Cheap Checkout")
public final class CheapCheckout extends Widget {
    private static final double SUNDAY_ADDITION, THURSDAY_SALE, FRIDAY_MARK_UP, SATURDAY_SALE;
    private static final int REQUIRED_ITEM_COUNT, REQUIRED_WEIGHT_COUNT;
    private static final ToDoubleFunction<List<CheckoutItem>> TO_SUM;

    static {
        SUNDAY_ADDITION = 2.15;
        THURSDAY_SALE = 0.5;
        FRIDAY_MARK_UP = 1.25;
        SATURDAY_SALE = 0.65;

        REQUIRED_ITEM_COUNT = 6;
        REQUIRED_WEIGHT_COUNT = 2;

        TO_SUM = items -> items.stream()
                .mapToDouble(CheckoutItem::getCurrentPiece)
                .sum();
    }

    public static @NotNull String calculateTotalPrice(@NotNull List<CheckoutItem> items, @NotNull DayOfWeek dayOfWeek,
                                                      double[] perPoundWeights, double givenCash) throws IllegalArgumentException {
        validateInput(items, perPoundWeights);
        items.get(4).applyMultiplicand(perPoundWeights[0]);
        items.get(5).applyMultiplicand(perPoundWeights[1]);

        double total = calculateByDay(items, dayOfWeek);
        boolean needsMoreMoney = total > givenCash;

        CheckoutItem.resetAlteredItems(items);

        return String.format("$%.2f", total) + (needsMoreMoney ? " Must get more money from customer" : "");
    }

    private static void validateInput(List<CheckoutItem> items, double[] perPoundWeights)
            throws IllegalArgumentException {
        long distinct = items.stream().distinct().count();
        if (distinct != REQUIRED_ITEM_COUNT)
            throw new IllegalArgumentException("Must have 6 distinct items");
        if (perPoundWeights.length != REQUIRED_WEIGHT_COUNT)
            throw new IllegalArgumentException("Must have 2 weights for the 2 items that are \"by-the-pound\"");
        if (!(items.get(4).isByThePound() && items.get(5).isByThePound()))
            throw new IllegalArgumentException("The 5th and 6th items must be \"by-the-pound\"");

        for (int i = 0; i < 4; i++) {
            if (items.get(i).isByThePound())
                throw new IllegalArgumentException("Items 1-4 shouldn't be weighted");
        }
    }

    private static double calculateByDay(List<CheckoutItem> items, DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case SUNDAY -> calculateSpecialSunday(items);
            case MONDAY -> calculateMalleableMonday(items);
            case TUESDAY -> calculateTroublesomeTuesday(items);
            case WEDNESDAY -> calculateWackyWednesday(items);
            case THURSDAY -> calculateThrillingThursday(items);
            case FRIDAY -> calculateFruityFriday(items);
            default -> calculateSweetSaturday(items);
        };
    }

    private static double calculateSpecialSunday(List<CheckoutItem> items) {
        return items.stream()
                .peek(item -> {
                    if (!item.isByThePound() && item.name().contains("S"))
                        item.addToPrice(SUNDAY_ADDITION);
                })
                .mapToDouble(CheckoutItem::getCurrentPiece)
                .sum();
    }

    private static double calculateMalleableMonday(List<CheckoutItem> items) {
        //15% off
        double sale = 0.85;
        items.get(0).applyMultiplicand(sale);
        items.get(2).applyMultiplicand(sale);
        items.get(5).applyMultiplicand(sale);

        return TO_SUM.applyAsDouble(items);
    }

    private static double calculateTroublesomeTuesday(List<CheckoutItem> items) {
        int counter = 0;
        for (CheckoutItem item : items) {
            int digRoot = digitalRoot(item.getCurrentPiece());
            item.addToPrice(digRoot);
            if (++counter == 4)
                break;
        }

        return TO_SUM.applyAsDouble(items);
    }

    private static double calculateWackyWednesday(List<CheckoutItem> items) {
        return items.stream()
                .mapToDouble(CheckoutItem::getCurrentPiece)
                .map(value -> roundToNPlaces(value, 2))
                .map(CheapCheckout::swapMinMaxDigits)
                .sum();
    }

    private static double calculateThrillingThursday(List<CheckoutItem> items) {
        //Half off odd-indexed items
        int size = items.size();
        for (int i = 0; i < size; i += 2)
            items.get(i).applyMultiplicand(THURSDAY_SALE);

        return TO_SUM.applyAsDouble(items);
    }

    private static double calculateFruityFriday(List<CheckoutItem> items) {
        return items.stream()
                .peek(item -> {
                    if (item.matchesCategory(FRUIT))
                        item.applyMultiplicand(FRIDAY_MARK_UP);
                })
                .mapToDouble(CheckoutItem::getCurrentPiece)
                .sum();
    }

    private static double calculateSweetSaturday(List<CheckoutItem> items) {
        return items.stream()
                .peek(item -> {
                    if (item.matchesCategory(SWEET))
                        item.applyMultiplicand(SATURDAY_SALE);
                })
                .mapToDouble(CheckoutItem::getCurrentPiece)
                .sum();
    }

    private static double swapMinMaxDigits(double number) {
        number = roundToNPlaces(number, 2);
        StringBuilder digit = new StringBuilder().append(number);
        if (digit.length() == 3)
            digit.append(0);

        char[] letters = digit.toString().toCharArray();
        char min = findMinDigit(letters);
        char max = findMaxDigit(letters);
        StringBuilder output = new StringBuilder();

        for (char letter : letters) {
            if (letter == min) letter = max;
            else if (letter == max) letter = min;
            output.append(letter);
        }

        return Double.parseDouble(output.toString());
    }

    private static char findMinDigit(char[] letters) {
        char min = '9';
        for (char letter : letters) {
            if (letter < min && Character.isDigit(letter))
                min = letter;
        }
        return min;
    }

    private static char findMaxDigit(char[] letters) {
        char max = '0';
        for (char letter : letters) {
            if (letter > max)
                max = letter;
        }
        return max;
    }
}
