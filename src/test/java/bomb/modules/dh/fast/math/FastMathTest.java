package bomb.modules.dh.fast.math;

import bomb.ConditionSetter;
import bomb.Widget;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.ConditionSetter.EMPTY_SETTER;
import static bomb.enumerations.Indicator.MSA;
import static bomb.enumerations.Port.PARALLEL;
import static bomb.enumerations.Port.RJ45;
import static bomb.enumerations.Port.SERIAL;
import static bomb.enumerations.TrinarySwitch.ON;
import static org.testng.Assert.assertEquals;

public class FastMathTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] exceptionTestProvider() {
        ConditionSetter setSerialCode = () -> Widget.setSerialCode("fr4op2");
        return new Object[][]{
                {EMPTY_SETTER, ""}, {EMPTY_SETTER, "AZ"}, {setSerialCode, "AY"}
        };
    }

    @Test(dataProvider = "exceptionTestProvider",
            expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(ConditionSetter setter, String input) {
        setter.setCondition();
        FastMath.solve(input);
    }

    @DataProvider
    public Object[][] allPreconditionTestProvider() {
        return new String[][]{
                {"40", "zz"}, {"81", "xS"}, {"22", "KK"}
        };
    }

    @Test(dataProvider = "allPreconditionTestProvider")
    public void allPreconditionTest(String expected, String input) {
        Widget.setPortValue(SERIAL, 1);
        Widget.setPortValue(RJ45, 1);
        Widget.setIndicator(ON, MSA);
        Widget.setDBatteries(4);
        Widget.setSerialCode("fr4op2"); //In total, adds 41 to the count

        assertEquals(FastMath.solve(input), expected);
    }

    @DataProvider
    public Object[][] belowZeroTestProvider() {
        return new String[][]{
                {"41", "ab"}, {"30", "Dg"}, {"43", "BX"}
        };
    }

    @Test(dataProvider = "belowZeroTestProvider")
    public void belowZeroTest(String expected, String input) {
        Widget.setSerialCode("fr4op2");
        Widget.setDBatteries(4);//In total, adds -20 to the count

        assertEquals(FastMath.solve(input), expected);
    }

    @DataProvider
    public Object[][] noPreconditionTestProvider() {
        return new String[][]{
                {"25", "aa"}, {"40", "xS"}, {"14", "KT"}
        };
    }

    @Test(dataProvider = "noPreconditionTestProvider")
    public void noPreconditionTest(String expected, String input) {
        Widget.setSerialCode("nr4op2");

        assertEquals(FastMath.solve(input), expected);
    }

    @Test
    public void zeroWithOneDigitTest() {
        Widget.setSerialCode("l33vi5");
        Widget.setPortValue(PARALLEL, 2);
        Widget.setPortValue(SERIAL, 2);
        Widget.setNumberOfPlates(3);

        assertEquals(FastMath.solve("ZE"), "06");
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
