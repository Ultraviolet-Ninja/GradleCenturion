package bomb.modules.c.cheap_checkout;

import bomb.abstractions.Resettable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.List;

import static bomb.modules.c.cheap_checkout.CheckoutItem.Category.CARE;
import static bomb.modules.c.cheap_checkout.CheckoutItem.Category.DAIRY;
import static bomb.modules.c.cheap_checkout.CheckoutItem.Category.FRUIT;
import static bomb.modules.c.cheap_checkout.CheckoutItem.Category.GRAIN;
import static bomb.modules.c.cheap_checkout.CheckoutItem.Category.OIL;
import static bomb.modules.c.cheap_checkout.CheckoutItem.Category.OTHER;
import static bomb.modules.c.cheap_checkout.CheckoutItem.Category.PROTEIN;
import static bomb.modules.c.cheap_checkout.CheckoutItem.Category.SWEET;
import static bomb.modules.c.cheap_checkout.CheckoutItem.Category.VEGETABLE;
import static bomb.modules.c.cheap_checkout.CheckoutItem.Category.WATER;

public enum CheckoutItem implements Resettable {
    BANANAS(FRUIT, 0.87), BROCCOLI(VEGETABLE, 1.39),
    CANDY_CANES(SWEET, 3.51), CANOLA_OIL(OIL, 2.28),
    CEREAL(GRAIN, 4.19), CHEESE(DAIRY, 4.49),
    CHICKEN(PROTEIN, 1.99), CHOCOLATE_BAR(SWEET, 2.10),
    CHOCOLATE_MILK(DAIRY, 5.68), COFFEE_BEANS(OTHER, 7.85),
    COOKIES(SWEET, 2.00), DEODORANT(CARE, 3.97),
    FRUIT_PUNCH(SWEET, 2.08), GRAPE_JELLY(SWEET, 2.98),
    GRAPEFRUIT(FRUIT, 1.08), GUM(SWEET, 1.12),
    HONEY(SWEET, 8.25), KETCHUP(OTHER, 3.59),
    LEMONS(FRUIT, 1.74), LETTUCE(VEGETABLE, 1.10),
    LOLLIPOPS(SWEET, 2.61), LOTION(CARE, 7.97),
    MAYONNAISE(OIL, 3.99), MINTS(SWEET, 6.39),
    MUSTARD(OTHER, 2.36), ORANGES(FRUIT, 0.80),
    PAPER_TOWELS(CARE, 9.46), PASTA_SAUCE(VEGETABLE, 2.30),
    PEANUT_BUTTER(PROTEIN, 5.00), PORK(PROTEIN, 4.14),
    POTATO_CHIPS(OIL, 3.25), POTATOES(VEGETABLE, 0.68),
    SHAMPOO(CARE, 4.98), SOCKS(OTHER, 6.97),
    SODA(SWEET, 2.05), SPAGHETTI(GRAIN, 2.92),
    STEAK(PROTEIN, 4.97), SUGAR(SWEET, 2.08),
    TEA(WATER, 2.35), TISSUES(CARE, 3.94),
    TOMATOES(FRUIT, 1.80), TOOTHPASTE(CARE, 2.50),
    TURKEY(PROTEIN, 2.98), WATER_BOTTLES(WATER, 9.37),
    WHITE_BREAD(GRAIN, 2.43), WHITE_MILK(DAIRY, 3.62);

    private final Category category;
    private final double basePrice;

    private double currentPrice;

    CheckoutItem(Category category, double basePrice) {
        this.category = category;
        this.basePrice = basePrice;
        currentPrice = basePrice;
    }

    public boolean isByThePound() {
        return this.category == FRUIT || (this.category == PROTEIN && this != PEANUT_BUTTER)
                || (this.category == VEGETABLE && this != PASTA_SAUCE);
    }

    @VisibleForTesting
    public double getBasePrice() {
        return basePrice;
    }

    public double getCurrentPiece() {
        return currentPrice;
    }

    public boolean matchesCategory(Category toCompare) {
        return category == toCompare;
    }

    public void addToPrice(double addition) {
        currentPrice += addition;
    }

    public void applyMultiplicand(double sale) {
        currentPrice *= sale;
    }

    @Override
    public void reset() {
        currentPrice = basePrice;
    }

    public static void resetAlteredItems(@NotNull List<CheckoutItem> alteredItems) {
        alteredItems.forEach(Resettable::reset);
    }

    public enum Category {
        DAIRY, FRUIT, VEGETABLE, GRAIN, PROTEIN, OIL, SWEET, CARE, WATER, OTHER
    }
}
