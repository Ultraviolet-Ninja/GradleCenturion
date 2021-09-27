package bomb.modules.t.translated.solutions.button;

import bomb.ConditionSetter;
import bomb.Widget;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.FRK;
import static bomb.enumerations.TrinarySwitch.ON;
import static bomb.modules.t.translated.solutions.button.Button.HOLD;
import static bomb.modules.t.translated.solutions.button.Button.TAP;
import static org.testng.Assert.assertEquals;

public class ButtonTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] exceptionTestProvider() {
        return new Object[][]{
                new ButtonProperty[]{null, null}, new ButtonProperty[]{ButtonProperty.RED, ButtonProperty.RED},
                new ButtonProperty[]{ButtonProperty.HOLD, ButtonProperty.HOLD},
                new ButtonProperty[]{ButtonProperty.RED, ButtonProperty.RED, ButtonProperty.HOLD}
        };
    }

    @Test(dataProvider = "exceptionTestProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(ButtonProperty[] properties) {
        Button.evaluate(properties);
    }

    @DataProvider
    public Object[][] validInputTestProvider() {
        ConditionSetter noAction = () -> {};
        ConditionSetter setFrkCondition = () -> {
            Widget.setIndicator(ON, FRK);
            Widget.setDoubleAs(4);
        };

        ConditionSetter setCarCondition = () -> Widget.setIndicator(ON, CAR);
        ConditionSetter setDetonateCondition = () -> Widget.setDoubleAs(2);

        return new Object[][]{
                {setDetonateCondition, new ButtonProperty[]{ButtonProperty.RED, ButtonProperty.DETONATE}, TAP},
                {noAction, new ButtonProperty[]{ButtonProperty.RED, ButtonProperty.DETONATE}, HOLD},
                {noAction, new ButtonProperty[]{ButtonProperty.RED, ButtonProperty.HOLD}, TAP},
                {noAction, new ButtonProperty[]{ButtonProperty.BLUE, ButtonProperty.ABORT}, HOLD},
                {setCarCondition, new ButtonProperty[]{ButtonProperty.WHITE, ButtonProperty.PRESS}, HOLD},
                {noAction, new ButtonProperty[]{ButtonProperty.YELLOW, ButtonProperty.ABORT}, HOLD},
                {setFrkCondition, new ButtonProperty[]{ButtonProperty.YELLOW, ButtonProperty.ABORT}, TAP}
        };
    }

    @Test(dataProvider = "validInputTestProvider")
    public void validInputTest(ConditionSetter setter, ButtonProperty[] properties, String expected) {
        setter.setCondition();

        assertEquals(Button.evaluate(properties), expected);
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
