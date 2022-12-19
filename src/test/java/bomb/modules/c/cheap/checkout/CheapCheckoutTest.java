package bomb.modules.c.cheap.checkout;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.List;

import static bomb.modules.c.cheap.checkout.CheckoutItem.BANANAS;
import static bomb.modules.c.cheap.checkout.CheckoutItem.CHEESE;
import static bomb.modules.c.cheap.checkout.CheckoutItem.CHOCOLATE_BAR;
import static bomb.modules.c.cheap.checkout.CheckoutItem.CHOCOLATE_MILK;
import static bomb.modules.c.cheap.checkout.CheckoutItem.COFFEE_BEANS;
import static bomb.modules.c.cheap.checkout.CheckoutItem.COOKIES;
import static bomb.modules.c.cheap.checkout.CheckoutItem.DEODORANT;
import static bomb.modules.c.cheap.checkout.CheckoutItem.GUM;
import static bomb.modules.c.cheap.checkout.CheckoutItem.HONEY;
import static bomb.modules.c.cheap.checkout.CheckoutItem.ORANGES;
import static bomb.modules.c.cheap.checkout.CheckoutItem.STEAK;
import static bomb.modules.c.cheap.checkout.CheckoutItem.TEA;
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
        double givenCash = 25.00;

        return new Object[][]{
                {items, SUNDAY, weights, givenCash, "$30.53 Must get more money from customer"},
                {items, MONDAY, weights, givenCash, "$24.52"},
                {items, TUESDAY, weights, givenCash, "$43.23 Must get more money from customer"},
                {items, WEDNESDAY, weights, givenCash, "$43.19 Must get more money from customer"},
                {items, THURSDAY, weights, givenCash, "$19.68"},
                {items, FRIDAY, weights, givenCash, "$26.43 Must get more money from customer"},
                {items, SATURDAY, weights, givenCash, "$23.34"}
        };
    }

    @Test(dataProvider = "allWeekTestProvider")
    public void allWeekTest(List<CheckoutItem> items, DayOfWeek dayOfWeek, double[] perPoundWeights,
                            double givenCash, String expectedText) {
        String results = CheapCheckout.calculateTotalPrice(items, dayOfWeek, perPoundWeights, givenCash);

        assertEquals(results, expectedText);
    }

    @AfterTest
    public void verifyReset() {
        EnumSet.allOf(CheckoutItem.class)
                .forEach(item -> assertEquals(item.getBasePrice(), item.getCurrentPiece()));
    }
}
