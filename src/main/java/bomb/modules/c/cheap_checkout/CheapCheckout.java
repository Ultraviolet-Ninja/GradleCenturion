package bomb.modules.c.cheap_checkout;

import bomb.Widget;
import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.util.List;
import java.util.function.ToDoubleFunction;

import static bomb.modules.c.cheap_checkout.CheckoutItem.Category.FRUIT;
import static bomb.modules.c.cheap_checkout.CheckoutItem.Category.SWEET;
import static bomb.tools.number.MathUtils.digitalRoot;

public class CheapCheckout extends Widget {
    private static final int REQUIRED_ITEM_COUNT = 6, REQUIRED_WEIGHT_COUNT = 2;
    private static final ToDoubleFunction<List<CheckoutItem>> TO_SUM = items -> items.stream()
            .mapToDouble(CheckoutItem::getCurrentPiece)
            .sum();

    public static String calculateTotalPrice(@NotNull List<CheckoutItem> items, @NotNull DayOfWeek dayOfWeek,
                                             double[] perPoundWeights, double givenCash) throws IllegalArgumentException {
        validateInput(items, perPoundWeights);
        items.get(4).applyMultiplicand(perPoundWeights[0]);
        items.get(5).applyMultiplicand(perPoundWeights[1]);

        double total = calculateByDay(items, dayOfWeek);
        boolean needsMoreMoney = total > givenCash;

        return String.format("$%.2f ", total) + (needsMoreMoney ? "Must get money change" : "");
    }

    private static void validateInput(List<CheckoutItem> items, double[] perPoundWeights)
            throws IllegalArgumentException {
        if (items.size() != REQUIRED_ITEM_COUNT)
            throw new IllegalArgumentException("Must have 6 items");
        if (perPoundWeights.length != REQUIRED_WEIGHT_COUNT)
            throw new IllegalArgumentException("Must have 2 weights for the 2 items that are \"by-the-pound\"");
        if (!(items.get(4).isByThePound() && items.get(5).isByThePound()))
            throw new IllegalArgumentException("The 4th and 5th items must be \"by-the-pound\"");
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
                        item.addToPrice(2.15);
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
            int digRoot = digitalRoot((int) item.getCurrentPiece());
            item.addToPrice(digRoot);
            if (++counter == 4)
                break;
        }

        return TO_SUM.applyAsDouble(items);
    }

    private static double calculateWackyWednesday(List<CheckoutItem> items) {
        return 0.0;
    }

    private static double calculateThrillingThursday(List<CheckoutItem> items) {
        int size = items.size();
        for (int i = 1; i < size; i += 2)
            items.get(i).applyMultiplicand(0.5);

        return TO_SUM.applyAsDouble(items);
    }

    private static double calculateFruityFriday(List<CheckoutItem> items) {
        return items.stream()
                .peek(item -> {
                    if (item.matchesCategory(FRUIT))
                        item.applyMultiplicand(1.25);
                })
                .mapToDouble(CheckoutItem::getCurrentPiece)
                .sum();
    }

    private static double calculateSweetSaturday(List<CheckoutItem> items) {
        return items.stream()
                .peek(item -> {
                    if (item.matchesCategory(SWEET))
                        item.applyMultiplicand(0.65);
                })
                .mapToDouble(CheckoutItem::getCurrentPiece)
                .sum();
    }
}
