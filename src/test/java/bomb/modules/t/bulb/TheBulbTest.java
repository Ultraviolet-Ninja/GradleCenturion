package bomb.modules.t.bulb;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.TrinarySwitch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static bomb.modules.t.bulb.Bulb.Color.BLUE;
import static bomb.modules.t.bulb.Bulb.Color.GREEN;
import static bomb.modules.t.bulb.Bulb.Color.PURPLE;
import static bomb.modules.t.bulb.Bulb.Color.RED;
import static bomb.modules.t.bulb.Bulb.Color.WHITE;
import static bomb.modules.t.bulb.Bulb.Color.YELLOW;
import static bomb.modules.t.bulb.Bulb.Light.OFF;
import static bomb.modules.t.bulb.Bulb.Light.ON;
import static bomb.modules.t.bulb.Bulb.Opacity.OPAQUE;
import static bomb.modules.t.bulb.Bulb.Opacity.TRANSLUCENT;
import static bomb.modules.t.bulb.Bulb.Position.SCREWED;
import static bomb.modules.t.bulb.Bulb.Position.UNSCREWED;
import static bomb.modules.t.bulb.TheBulb.PRESS_I;
import static bomb.modules.t.bulb.TheBulb.PRESS_O;
import static bomb.modules.t.bulb.TheBulb.SCREW;
import static bomb.modules.t.bulb.TheBulb.UNSCREW;
import static org.testng.Assert.assertEquals;

public class TheBulbTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void exceptionTest() {
        Bulb testBulb = new Bulb();
        testBulb.setPosition(UNSCREWED);
        testBulb.setOpacity(OPAQUE);
        testBulb.setLight(OFF);
        testBulb.setColor(RED);

        TheBulb.solve(testBulb);
    }

    @DataProvider
    public Object[][] trainingVideoTestProvider() {
        ConditionSetter trainingVideoConditions = () -> {
            Widget.setIndicator(TrinarySwitch.OFF, Indicator.BOB);
            Widget.setIndicator(TrinarySwitch.ON, Indicator.FRQ);
            Widget.setIndicator(TrinarySwitch.OFF, Indicator.FRK);
            Widget.setIndicator(TrinarySwitch.ON, Indicator.NSA);
        };

        ConditionSetter secondTrainingVideoConditions = () -> Widget.setIndicator(TrinarySwitch.ON, Indicator.SIG);

        ConditionSetter thirdTrainingVideoConditions = () -> {
            Widget.setIndicator(TrinarySwitch.ON, Indicator.IND);
            Widget.setIndicator(TrinarySwitch.OFF, Indicator.FRK);
            Widget.setIndicator(TrinarySwitch.OFF, Indicator.NSA);
        };

        return new Object[][]{
                {trainingVideoConditions, ON, GREEN, TRANSLUCENT, new String[]{PRESS_I, UNSCREW, PRESS_I, PRESS_O, SCREW}},
                {secondTrainingVideoConditions, OFF, BLUE, OPAQUE, new String[]{UNSCREW, PRESS_O, PRESS_O, PRESS_O, SCREW}},
                {thirdTrainingVideoConditions, ON, BLUE, TRANSLUCENT, new String[]{PRESS_I, UNSCREW, PRESS_O, PRESS_O, SCREW}}
        };
    }

    @Test(dataProvider = "trainingVideoTestProvider")
    public void trainingVideoTest(ConditionSetter bombConditions, Bulb.Light inputLight, Bulb.Color inputColor, Bulb.Opacity inputOpacity, String[] expectedResults) {
        bombConditions.setCondition();

        Bulb testBulb = new Bulb();
        testBulb.setPosition(SCREWED);
        testBulb.setOpacity(inputOpacity);
        testBulb.setLight(inputLight);
        testBulb.setColor(inputColor);

        List<String> convertedResults = Arrays.asList(expectedResults);

        assertEquals(TheBulb.solve(testBulb), convertedResults);
    }

    @DataProvider
    public Object[][] writtenTestProvider() {
        ConditionSetter testConditions = () -> Widget.setIndicator(TrinarySwitch.OFF, Indicator.FRK);
        ConditionSetter secondTestConditions = () -> Widget.setIndicator(TrinarySwitch.OFF, Indicator.CAR);
        ConditionSetter emptyCondition = () -> {};

        return new Object[][]{
                {testConditions, ON, YELLOW, OPAQUE, new String[]{PRESS_O, UNSCREW, PRESS_O, PRESS_I, SCREW}},
                {testConditions, ON, WHITE, OPAQUE, new String[]{PRESS_O, UNSCREW, PRESS_I, PRESS_O, SCREW}},
                {secondTestConditions, OFF, YELLOW, TRANSLUCENT, new String[]{UNSCREW, PRESS_I, PRESS_O, PRESS_I, SCREW}},
                {secondTestConditions, OFF, BLUE, OPAQUE, new String[]{UNSCREW, PRESS_I, PRESS_I, PRESS_I, SCREW}},
                {emptyCondition, OFF, PURPLE, TRANSLUCENT, new String[]{UNSCREW, PRESS_O, PRESS_I, PRESS_O, SCREW}}
        };
    }

    @Test(dataProvider = "writtenTestProvider")
    public void writtenTest(ConditionSetter bombConditions, Bulb.Light inputLight, Bulb.Color inputColor, Bulb.Opacity inputOpacity, String[] expectedResults) {
        bombConditions.setCondition();

        Bulb testBulb = new Bulb();
        testBulb.setPosition(SCREWED);
        testBulb.setOpacity(inputOpacity);
        testBulb.setLight(inputLight);
        testBulb.setColor(inputColor);

        List<String> convertedResults = Arrays.asList(expectedResults);

        assertEquals(TheBulb.solve(testBulb), convertedResults);
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
