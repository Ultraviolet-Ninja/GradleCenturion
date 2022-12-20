package bomb.modules.dh.fizzbuzz;

import bomb.ConditionSetter;
import bomb.Widget;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.enumerations.Indicator.BOB;
import static bomb.enumerations.Indicator.IND;
import static bomb.enumerations.Port.DVI;
import static bomb.enumerations.Port.PS2;
import static bomb.enumerations.Port.RCA;
import static bomb.enumerations.Port.RJ45;
import static bomb.enumerations.Port.SERIAL;
import static bomb.enumerations.TrinarySwitch.OFF;
import static bomb.modules.dh.fizzbuzz.FizzBuzz.FizzBuzzColor.BLUE;
import static bomb.modules.dh.fizzbuzz.FizzBuzz.FizzBuzzColor.GREEN;
import static bomb.modules.dh.fizzbuzz.FizzBuzz.FizzBuzzColor.RED;
import static bomb.modules.dh.fizzbuzz.FizzBuzz.FizzBuzzColor.WHITE;
import static org.testng.Assert.assertEquals;

public class FizzBuzzTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] exceptionTestProvider() {
        String validNumber = "4193160";
        return new Object[][]{
                {null, null, 0}, {"", null, 0}, {"19205437", null, 0}, {"192u537", null, 0}, {"192(437", null, 0},
                {validNumber, null, 0}, {validNumber, WHITE, -1}, {validNumber, WHITE, 3}
        };
    }

    @Test(dataProvider = "exceptionTestProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(String inputText, FizzBuzz.FizzBuzzColor color, int strikeCount) {
        FizzBuzz.solve(inputText, color, strikeCount);
    }

    @DataProvider
    public Object[][] videoTestProvider() {
        ConditionSetter widgetSetupOne = () -> {
            Widget.setDoubleAs(8);
            Widget.setDBatteries(2);
            Widget.setNumHolders(6);

            Widget.setIndicator(OFF, IND);
            Widget.setIndicator(OFF, BOB);

            Widget.setSerialCode("3a3qb0");
        };

        ConditionSetter widgetSetupTwo = () -> {
            Widget.setPortValue(RCA, 1);
            Widget.setPortValue(DVI, 1);
            Widget.setPortValue(PS2, 1);
            Widget.setPortValue(SERIAL, 1);
            Widget.setPortValue(RJ45, 1);

            Widget.setNumHolders(3);
            Widget.setDBatteries(2);
            Widget.setDoubleAs(2);

            Widget.setSerialCode("0u1ea2");
        };


        return new Object[][]{
                {widgetSetupOne, "4193160", RED, 0, "NUMBER"},
                {widgetSetupOne, "1480918", BLUE, 0, "FIZZBUZZ"},
                {widgetSetupOne, "4124939", WHITE, 0, "NUMBER"},

                {widgetSetupTwo, "2185547", BLUE, 0, "NUMBER"},
                {widgetSetupTwo, "3905781",  RED, 0, "FIZZ"},
                {widgetSetupTwo, "1720785", GREEN, 0, "NUMBER"}
        };
    }

    @Test(dataProvider = "videoTestProvider")
    public void videoTest(ConditionSetter widgetSetup, String inputText, FizzBuzz.FizzBuzzColor color,
                          int strikeCount, String expectedOutput) {
        widgetSetup.setCondition();

        assertEquals(FizzBuzz.solve(inputText, color, strikeCount), expectedOutput);
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
