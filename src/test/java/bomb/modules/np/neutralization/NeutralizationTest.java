package bomb.modules.np.neutralization;

import bomb.BombSimulations;
import bomb.ConditionSetter;
import bomb.Widget;
import javafx.scene.paint.Color;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.ConditionSetter.EMPTY_SETTER;
import static bomb.enumerations.Indicator.MSA;
import static bomb.enumerations.Indicator.NSA;
import static bomb.enumerations.Port.PARALLEL;
import static bomb.enumerations.Port.PS2;
import static bomb.enumerations.Port.RJ45;
import static bomb.enumerations.Port.SERIAL;
import static bomb.enumerations.TrinarySwitch.ON;
import static bomb.modules.np.neutralization.Chemical.Base.AMMONIA;
import static bomb.modules.np.neutralization.Chemical.Base.LITHIUM_HYDROXIDE;
import static bomb.modules.np.neutralization.Chemical.Base.POTASSIUM_HYDROXIDE;
import static bomb.modules.np.neutralization.Neutralization.FILTER_TEXT;
import static bomb.modules.np.neutralization.Neutralization.NO_FILTER_TEXT;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.CYAN;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.YELLOW;
import static org.testng.Assert.assertEquals;

public class NeutralizationTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] exceptionTestProvider() {
        ConditionSetter validSetup = this::setupOne;
        return new Object[][]{
                {EMPTY_SETTER, 0, RED}, {validSetup, 0, CYAN}
        };
    }

    @Test(dataProvider = "exceptionTestProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(ConditionSetter setter, int vol, Color acidColor) {
        setter.setCondition();
        Neutralization.titrate(vol, acidColor);
    }

    @Test
    public void trainingVideoTestOne() {
        setupOne();
        assertEqual(10, YELLOW, new String[]{"Ammonia", AMMONIA.getFormula(), "8", FILTER_TEXT});
    }

    private void setupOne() {
        Widget.setNumberOfPlates(1);
        Widget.setIndicator(ON, NSA);
        Widget.setPortValue(PARALLEL, 2);
        Widget.setPortValue(SERIAL, 1);
        Widget.setPortValue(PS2, 1);
        Widget.setPortValue(RJ45, 1);
        Widget.setDBatteries(1);
        Widget.setNumHolders(1);
        Widget.setSerialCode("2u3mr1");
    }

    @Test
    public void trainingVideoTestTwo() {
        setupTwo();
        assertEqual(20, BLUE, new String[]{"Lithium Hydroxide", LITHIUM_HYDROXIDE.getFormula(),
                "48", NO_FILTER_TEXT});
    }

    private void setupTwo() {
        Widget.setNumberOfPlates(3);
        Widget.setPortValue(PARALLEL, 2);
        Widget.setPortValue(SERIAL, 1);
        Widget.setSerialCode("ew7qw5");
        Widget.setIndicator(ON, MSA);
        Widget.setIndicator(ON, NSA);
    }

    @DataProvider
    public Object[][] theGreatBerateSimulationProvider() {
        ConditionSetter first = BombSimulations::theGreatBerateVideoOne;
        ConditionSetter second = BombSimulations::theGreatBerateVideoTwo;
        ConditionSetter third = BombSimulations::videoTwoTakeTwo;
        return new Object[][]{
                {first, 5, BLUE, new String[]{"Lithium Hydroxide", LITHIUM_HYDROXIDE.getFormula(),
                        "12", NO_FILTER_TEXT}},
                {second, 5, Color.RED, new String[]{"Lithium Hydroxide", LITHIUM_HYDROXIDE.getFormula(),
                        "4", FILTER_TEXT}},
                {third, 10, YELLOW, new String[]{"Potassium Hydroxide", POTASSIUM_HYDROXIDE.getFormula(),
                        "4", NO_FILTER_TEXT}}
        };
    }

    @Test(dataProvider = "theGreatBerateSimulationProvider")
    public void theGreatBerateTest(ConditionSetter setter, int vol, Color acidColor, String[] expectedArr) {
        setter.setCondition();
        assertEqual(vol, acidColor, expectedArr);
    }

    private void assertEqual(int volume, Color color, String[] expected) {
        String[] actual = Neutralization.titrate(volume, color).split("-");
        for (int i = 0; i < expected.length; i++)
            assertEquals(expected[i].toLowerCase(), actual[i].toLowerCase());
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
