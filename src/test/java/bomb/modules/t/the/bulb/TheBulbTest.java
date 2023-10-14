package bomb.modules.t.the.bulb;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.enumerations.TrinarySwitch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static bomb.ConditionSetter.EMPTY_SETTER;
import static bomb.enumerations.Indicator.BOB;
import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.FRK;
import static bomb.enumerations.Indicator.FRQ;
import static bomb.enumerations.Indicator.IND;
import static bomb.enumerations.Indicator.NSA;
import static bomb.enumerations.Indicator.SIG;
import static bomb.modules.t.the.bulb.BulbModel.Color.BLUE;
import static bomb.modules.t.the.bulb.BulbModel.Color.GREEN;
import static bomb.modules.t.the.bulb.BulbModel.Color.PURPLE;
import static bomb.modules.t.the.bulb.BulbModel.Color.WHITE;
import static bomb.modules.t.the.bulb.BulbModel.Color.YELLOW;
import static bomb.modules.t.the.bulb.BulbModel.Light.OFF;
import static bomb.modules.t.the.bulb.BulbModel.Light.ON;
import static bomb.modules.t.the.bulb.BulbModel.Opacity.OPAQUE;
import static bomb.modules.t.the.bulb.BulbModel.Opacity.TRANSLUCENT;
import static bomb.modules.t.the.bulb.BulbModel.Position.SCREWED;
import static bomb.modules.t.the.bulb.BulbModel.Position.UNSCREWED;
import static bomb.modules.t.the.bulb.TheBulb.PRESS_I;
import static bomb.modules.t.the.bulb.TheBulb.PRESS_O;
import static bomb.modules.t.the.bulb.TheBulb.SCREW;
import static bomb.modules.t.the.bulb.TheBulb.UNSCREW;
import static org.testng.Assert.assertEquals;

public class TheBulbTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] exceptionTestProvider() {
        return new Object[][] {
                {UNSCREWED, null, null, null}, {SCREWED, null, null, null},
                {SCREWED, ON, null, null}, {SCREWED, ON, PURPLE, null}
        };
    }

    @Test(dataProvider = "exceptionTestProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(BulbModel.Position position, BulbModel.Light inputLight,
                              BulbModel.Color inputColor, BulbModel.Opacity inputOpacity) {
        BulbModel testBulbModel = new BulbModel();
        testBulbModel.setPosition(position);
        testBulbModel.setOpacity(inputOpacity);
        testBulbModel.setLight(inputLight);
        testBulbModel.setColor(inputColor);

        TheBulb.solve(testBulbModel);
    }

    @DataProvider
    public Object[][] trainingVideoTestProvider() {
        ConditionSetter trainingVideoConditions = () -> {
            Widget.setIndicator(TrinarySwitch.OFF, BOB);
            Widget.setIndicator(TrinarySwitch.ON, FRQ);
            Widget.setIndicator(TrinarySwitch.OFF, FRK);
            Widget.setIndicator(TrinarySwitch.ON, NSA);
        };

        ConditionSetter secondTrainingVideoConditions = () -> Widget.setIndicator(TrinarySwitch.ON, SIG);

        ConditionSetter thirdTrainingVideoConditions = () -> {
            Widget.setIndicator(TrinarySwitch.ON, IND);
            Widget.setIndicator(TrinarySwitch.OFF, FRK);
            Widget.setIndicator(TrinarySwitch.OFF, NSA);
        };

        return new Object[][]{
                {trainingVideoConditions, ON, GREEN, TRANSLUCENT, new String[]{PRESS_I, UNSCREW, PRESS_I, PRESS_O, SCREW}},
                {secondTrainingVideoConditions, OFF, BLUE, OPAQUE, new String[]{UNSCREW, PRESS_O, PRESS_O, PRESS_O, SCREW}},
                {thirdTrainingVideoConditions, ON, BLUE, TRANSLUCENT, new String[]{PRESS_I, UNSCREW, PRESS_O, PRESS_O, SCREW}}
        };
    }

    @Test(dataProvider = "trainingVideoTestProvider")
    public void trainingVideoTest(ConditionSetter bombConditions, BulbModel.Light inputLight,
                                  BulbModel.Color inputColor, BulbModel.Opacity inputOpacity,
                                  String[] expectedResults) {
        bombConditions.setCondition();

        BulbModel testBulbModel = new BulbModel();
        testBulbModel.setPosition(SCREWED);
        testBulbModel.setOpacity(inputOpacity);
        testBulbModel.setLight(inputLight);
        testBulbModel.setColor(inputColor);

        List<String> convertedResults = Arrays.asList(expectedResults);

        assertEquals(TheBulb.solve(testBulbModel), convertedResults);
    }

    @DataProvider
    public Object[][] writtenTestProvider() {
        ConditionSetter testConditions = () -> Widget.setIndicator(TrinarySwitch.OFF, FRK);
        ConditionSetter secondTestConditions = () -> Widget.setIndicator(TrinarySwitch.OFF, CAR);

        return new Object[][]{
                {
                    testConditions, ON, YELLOW, OPAQUE,
                        new String[]{PRESS_O, UNSCREW, PRESS_O, PRESS_I, SCREW}
                },
                {
                    testConditions, ON, WHITE, OPAQUE,
                        new String[]{PRESS_O, UNSCREW, PRESS_I, PRESS_O, SCREW}
                },
                {
                    secondTestConditions, OFF, YELLOW, TRANSLUCENT,
                        new String[]{UNSCREW, PRESS_I, PRESS_O, PRESS_I, SCREW}
                },
                {
                    secondTestConditions, OFF, BLUE, OPAQUE,
                        new String[]{UNSCREW, PRESS_I, PRESS_I, PRESS_I, SCREW}
                },
                {
                    EMPTY_SETTER, OFF, PURPLE, TRANSLUCENT,
                        new String[]{UNSCREW, PRESS_O, PRESS_I, PRESS_O, SCREW}
                }
        };
    }

    @Test(dataProvider = "writtenTestProvider")
    public void writtenTest(ConditionSetter bombConditions, BulbModel.Light inputLight,
                            BulbModel.Color inputColor, BulbModel.Opacity inputOpacity,
                            String[] expectedResults) {
        bombConditions.setCondition();

        BulbModel testBulbModel = new BulbModel();
        testBulbModel.setPosition(SCREWED);
        testBulbModel.setOpacity(inputOpacity);
        testBulbModel.setLight(inputLight);
        testBulbModel.setColor(inputColor);

        List<String> convertedResults = Arrays.asList(expectedResults);

        assertEquals(TheBulb.solve(testBulbModel), convertedResults);
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
