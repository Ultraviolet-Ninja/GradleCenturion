package bomb.modules.c.cheap_checkout;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.util.List;

import static bomb.modules.c.cheap_checkout.CheckoutItem.BANANAS;
import static bomb.modules.c.cheap_checkout.CheckoutItem.CHEESE;
import static bomb.modules.c.cheap_checkout.CheckoutItem.CHOCOLATE_BAR;
import static bomb.modules.c.cheap_checkout.CheckoutItem.CHOCOLATE_MILK;
import static bomb.modules.c.cheap_checkout.CheckoutItem.COFFEE_BEANS;
import static bomb.modules.c.cheap_checkout.CheckoutItem.COOKIES;
import static bomb.modules.c.cheap_checkout.CheckoutItem.DEODORANT;
import static bomb.modules.c.cheap_checkout.CheckoutItem.GUM;
import static bomb.modules.c.cheap_checkout.CheckoutItem.HONEY;
import static bomb.modules.c.cheap_checkout.CheckoutItem.ORANGES;
import static bomb.modules.c.cheap_checkout.CheckoutItem.STEAK;
import static bomb.modules.c.cheap_checkout.CheckoutItem.TEA;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static org.testng.Assert.assertEquals;

public class CheapCheckoutTest {
    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Must have 6 distinct items")
    public void firstValidationTest() {
        CheapCheckout.calculateTotalPrice(List.of(STEAK, STEAK, STEAK, STEAK, STEAK, STEAK), MONDAY,
                new double[] {1.5, 1.5}, 15.00);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Must have 2 weights for the 2 items that are \"by-the-pound\"")
    public void secondValidationTest() {
        CheapCheckout.calculateTotalPrice(List.of(CHOCOLATE_MILK, COOKIES, DEODORANT, GUM, BANANAS, STEAK), MONDAY,
                new double[] {1.5, 1.5, 1.5}, 15.00);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "The 5th and 6th items must be \"by-the-pound\"")
    public void thirdValidationTest() {
        CheapCheckout.calculateTotalPrice(List.of(CHOCOLATE_MILK, COOKIES, DEODORANT, GUM, BANANAS, CHOCOLATE_BAR),
                MONDAY, new double[] {1.5, 1.5}, 15.00);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Items 1-4 shouldn't be weighted")
    public void fourthValidationTest() {
        CheapCheckout.calculateTotalPrice(List.of(CHOCOLATE_MILK, COOKIES, DEODORANT, ORANGES, BANANAS, STEAK),
                MONDAY, new double[] {1.5, 1.5}, 15.00);
    }

    @DataProvider
    public Object[][] allWeekTestProvider() {
        List<CheckoutItem> items = List.of(HONEY, COFFEE_BEANS, TEA, CHEESE, STEAK, ORANGES);
        double[] weights = {0.5, 1.0};

        return new Object[][]{
                {items, SUNDAY, weights, }, {items, MONDAY, weights, },
                {items, TUESDAY, weights, }, {items, WEDNESDAY, weights, },
                {items, THURSDAY, weights, }, {items, FRIDAY, weights, },
                {items, SATURDAY, weights, }
        };
    }

    @Test(dataProvider = "allWeekTestProvider", enabled = false)
    public void allWeekTest(List<CheckoutItem> items, DayOfWeek dayOfWeek, double[] perPoundWeights,
                            double givenCash, String expectedText) {
        String results = CheapCheckout.calculateTotalPrice(items, dayOfWeek, perPoundWeights, givenCash);

        assertEquals(results, expectedText);
    }
}
