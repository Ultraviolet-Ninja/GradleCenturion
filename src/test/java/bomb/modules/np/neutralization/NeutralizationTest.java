package bomb.modules.np.neutralization;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.BombSimulations;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TrinarySwitch;
import javafx.scene.paint.Color;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.modules.np.neutralization.Chemical.Base.Ammonia;
import static bomb.modules.np.neutralization.Chemical.Base.Lithium_Hydroxide;
import static bomb.modules.np.neutralization.Chemical.Base.Potassium_Hydroxide;
import static bomb.modules.np.neutralization.Neutralization.FILTER_TEXT;
import static bomb.modules.np.neutralization.Neutralization.NO_FILTER_TEXT;
import static org.testng.Assert.assertEquals;

public class NeutralizationTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] exceptionTestProvider() {
        ConditionSetter empty = () -> {
        };
        ConditionSetter validSetup = this::setupOne;
        return new Object[][]{
                {empty, 0, Color.RED}, {validSetup, 0, Color.CYAN}
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
        assertEqual(10, Color.YELLOW, new String[]{"Ammonia", Ammonia.getFormula(), "8", FILTER_TEXT});
    }

    private void setupOne() {
        Widget.setNumberOfPlates(1);
        Widget.setIndicator(TrinarySwitch.ON, Indicator.NSA);
        Widget.setPortValue(Port.PARALLEL, 2);
        Widget.setPortValue(Port.SERIAL, 1);
        Widget.setPortValue(Port.PS2, 1);
        Widget.setPortValue(Port.RJ45, 1);
        Widget.setDBatteries(1);
        Widget.setNumHolders(1);
        Widget.setSerialCode("2u3mr1");
    }

    @Test
    public void trainingVideoTestTwo() {
        setupTwo();
        assertEqual(20, Color.BLUE, new String[]{"Lithium Hydroxide", Lithium_Hydroxide.getFormula(),
                "48", NO_FILTER_TEXT});
    }

    private void setupTwo() {
        Widget.setNumberOfPlates(3);
        Widget.setPortValue(Port.PARALLEL, 2);
        Widget.setPortValue(Port.SERIAL, 1);
        Widget.setSerialCode("ew7qw5");
        Widget.setIndicator(TrinarySwitch.ON, Indicator.MSA);
        Widget.setIndicator(TrinarySwitch.ON, Indicator.NSA);
    }

    @DataProvider
    public Object[][] theGreatBerateSimulationProvider() {
        ConditionSetter first = BombSimulations::theGreatBerateVideoOne;
        ConditionSetter second = BombSimulations::theGreatBerateVideoTwo;
        ConditionSetter third = BombSimulations::videoTwoTakeTwo;
        return new Object[][]{
                {first, 5, Color.BLUE, new String[]{"Lithium Hydroxide", Lithium_Hydroxide.getFormula(),
                        "12", NO_FILTER_TEXT}},
                {second, 5, Color.RED, new String[]{"Lithium Hydroxide", Lithium_Hydroxide.getFormula(),
                        "4", FILTER_TEXT}},
                {third, 10, Color.YELLOW, new String[]{"Potassium Hydroxide", Potassium_Hydroxide.getFormula(),
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
            assertEquals(expected[i], actual[i]);
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
