package bomb.modules.c.caesar;

import bomb.ConditionSetter;
import bomb.Widget;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.NSA;
import static bomb.enumerations.Port.PARALLEL;
import static bomb.enumerations.TrinarySwitch.ON;
import static org.testng.Assert.assertEquals;

public class CaesarTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void validationTest() {
        Caesar.reshuffle("");
    }

    @DataProvider
    public Object[][] validInputTestProvider() {
        final String serialCode = "A65WU8";
        ConditionSetter firstBranchCondition = () -> {
            Widget.setSerialCode(serialCode);
            Widget.setIndicator(ON, NSA);
            Widget.setPortValue(PARALLEL, 1);
        };

        ConditionSetter secondBranchCondition = () -> {
            Widget.setSerialCode(serialCode);
            Widget.setIndicator(ON, NSA);
            Widget.setIndicator(ON, CAR);
        };

        ConditionSetter thirdBranchCondition = () -> {
            Widget.setSerialCode("BJ3WL1");
            Widget.setDoubleAs(2);
            Widget.setPortValue(PARALLEL, 1);
        };
        ConditionSetter fourthBranchCondition = () -> Widget.setSerialCode("A65WU7");

        String text = "ANWMDL";
        return new Object[][]{
                {firstBranchCondition, text, text}, {secondBranchCondition, text, "ZMVLCK"},
                {thirdBranchCondition, text, "YLUKBJ"}, {fourthBranchCondition, "ZAJRMF", "ABKSNG"}
        };
    }

    @Test(dataProvider = "validInputTestProvider")
    public void validInputTest(ConditionSetter widgetSetter, String input, String expected) {
        widgetSetter.setCondition();

        String result = Caesar.reshuffle(input);

        assertEquals(result, expected);
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
