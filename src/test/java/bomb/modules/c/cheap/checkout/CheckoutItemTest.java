package bomb.modules.c.cheap.checkout;

import org.testng.annotations.Test;

import java.util.EnumSet;

import static bomb.modules.c.cheap.checkout.CheckoutItem.BANANAS;
import static bomb.modules.c.cheap.checkout.CheckoutItem.BROCCOLI;
import static bomb.modules.c.cheap.checkout.CheckoutItem.CHICKEN;
import static bomb.modules.c.cheap.checkout.CheckoutItem.GRAPEFRUIT;
import static bomb.modules.c.cheap.checkout.CheckoutItem.LEMONS;
import static bomb.modules.c.cheap.checkout.CheckoutItem.LETTUCE;
import static bomb.modules.c.cheap.checkout.CheckoutItem.ORANGES;
import static bomb.modules.c.cheap.checkout.CheckoutItem.PASTA_SAUCE;
import static bomb.modules.c.cheap.checkout.CheckoutItem.PEANUT_BUTTER;
import static bomb.modules.c.cheap.checkout.CheckoutItem.PORK;
import static bomb.modules.c.cheap.checkout.CheckoutItem.POTATOES;
import static bomb.modules.c.cheap.checkout.CheckoutItem.STEAK;
import static bomb.modules.c.cheap.checkout.CheckoutItem.TOMATOES;
import static bomb.modules.c.cheap.checkout.CheckoutItem.TURKEY;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CheckoutItemTest {
    @Test
    public void byThePoundValidationTest() {
        EnumSet<CheckoutItem> allByThePoundItems = EnumSet.of(
                BANANAS, BROCCOLI, CHICKEN, GRAPEFRUIT, LEMONS, LETTUCE, ORANGES, PORK, POTATOES,
                STEAK, TOMATOES, TURKEY
        );

        allByThePoundItems.forEach(item -> assertTrue(item.isByThePound()));
    }

    @Test
    public void otherProteinAndVegetableTest() {
        EnumSet.of(PASTA_SAUCE, PEANUT_BUTTER).forEach(item -> assertFalse(item.isByThePound()));
    }

    @Test
    public void fixedPriceItemTest() {
        EnumSet<CheckoutItem> fixedPriceItems = EnumSet.complementOf(EnumSet.of(
                BANANAS, BROCCOLI, CHICKEN, GRAPEFRUIT, LEMONS, LETTUCE, ORANGES, PORK, POTATOES,
                STEAK, TOMATOES, TURKEY
        ));

        fixedPriceItems.forEach(item -> assertFalse(item.isByThePound()));
    }
}
