package bomb;

import bomb.enumerations.Indicator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.ConditionSetter.EMPTY_SETTER;
import static bomb.enumerations.Indicator.BOB;
import static bomb.enumerations.Indicator.MSA;
import static bomb.enumerations.Indicator.NSA;
import static bomb.enumerations.TrinarySwitch.OFF;
import static bomb.enumerations.TrinarySwitch.ON;
import static org.testng.Assert.assertEquals;

public class WidgetTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] twoFactorProvider() {
        return new Object[][]{
                {"o24ksa"}
        };
    }

    @Test(dataProvider = "twoFactorProvider")
    public void twoFactorTest(String val) {
        Widget.setTwoFactor(val);

        assertEquals(val, Widget.getTwoFactor());
    }

    @DataProvider
    public Object[][] booleanProvider() {
        return new Object[][]{
                {false, false}, {true, true}
        };
    }

    @Test(dataProvider = "booleanProvider")
    public void forgetMeNotTest(boolean val, boolean expected) {
        Widget.setIsForgetMeNotActive(val);

        assertEquals(Widget.getIsForgetMeNotActive(), expected);
    }

    @Test(dataProvider = "booleanProvider")
    public void souvenirTest(boolean val, boolean expected) {
        Widget.setIsSouvenirActive(val);

        assertEquals(Widget.getIsSouvenirActive(), expected);
    }

    @DataProvider
    public Object[][] negativeValueProvider() {
        return new Object[][]{
                {-1, 0}, {5, 5}
        };
    }

    @Test(dataProvider = "negativeValueProvider")
    public void negativePortPlateTest(int val, int expected) {
        Widget.setNumberOfPlates(val);

        assertEquals(Widget.getNumPortPlates(), expected);
    }

    @Test(dataProvider = "negativeValueProvider")
    public void negativeModNumberTest(int val, int expected) {
        Widget.setNumModules(val);

        assertEquals(Widget.getNumModules(), expected);
    }

    @Test(dataProvider = "negativeValueProvider")
    public void negativeHolderNumberTest(int val, int expected) {
        Widget.setNumHolders(val);

        assertEquals(Widget.getNumHolders(), expected);
    }

    @Test(dataProvider = "negativeValueProvider")
    public void negativeDBatteriesTest(int val, int expected) {
        Widget.setDBatteries(val);

        assertEquals(Widget.getAllBatteries(), expected);
    }

    @Test(dataProvider = "negativeValueProvider")
    public void negativeDoubleATest(int val, int expected) {
        Widget.setDoubleAs(val);

        assertEquals(Widget.getAllBatteries(), expected);
    }

    @DataProvider
    public Object[][] serialCodeProvider() {
        return new Object[][]{
                {"andkws", 6, 0}, {"124367", 0, 6}, {"19kwk4", 3, 3}
        };
    }

    @Test(dataProvider = "serialCodeProvider")
    public void serialCodeLengthTest(String input, int expectedLetterLength, int expectedNumberLength) {
        Widget.setSerialCode(input);

        assertEquals(Widget.countLettersInSerialCode(), expectedLetterLength);
        assertEquals(Widget.countNumbersInSerialCode(), expectedNumberLength);
    }

    @DataProvider
    public Object[][] indicatorProvider() {
        ConditionSetter trueSetter = () -> {
            Widget.setIndicator(ON, MSA);
            Widget.setIndicator(OFF, NSA);
        };

        return new Object[][]{
                {EMPTY_SETTER, BOB, false}, {trueSetter, MSA, true},
                {trueSetter, NSA, true}
        };
    }

    @Test(dataProvider = "indicatorProvider")
    public void containsIndicatorTest(ConditionSetter setter, Indicator ind, boolean expected) {
        setter.setCondition();

        assertEquals(Widget.hasFollowingIndicators(ind), expected);
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
