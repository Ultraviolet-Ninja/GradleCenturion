package bomb.modules.np.neutralization;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.WidgetSimulations;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TriState;
import javafx.scene.paint.Color;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.modules.np.neutralization.Chemical.Base.Ammonia;
import static bomb.modules.np.neutralization.Chemical.Base.Lithium_Hydroxide;
import static bomb.modules.np.neutralization.Chemical.Base.Potassium_Hydroxide;
import static bomb.modules.np.neutralization.Neutralization.FILTER;
import static bomb.modules.np.neutralization.Neutralization.NO_FILTER;
import static org.testng.Assert.assertEquals;

public class NeutralizationTest {
    @BeforeMethod
    public void setUp(){
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] exceptionProvider(){
        ConditionSetter empty = () -> {};
        ConditionSetter validSetup = this::setupOne;
        return new Object[][]{
                {empty, 0, Color.RED}, {validSetup, 0, Color.CYAN}
        };
    }

    @Test(dataProvider = "exceptionProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(ConditionSetter setter, int vol, Color acidColor){
        setter.setCondition();
        Neutralization.titrate(vol, acidColor);
    }

    @Test
    public void trainingVideoTestOne(){
        setupOne();
        assertEqual(10, Color.YELLOW, new String[]{"Ammonia", Ammonia.getFormula(), "8", FILTER});
    }

    private void setupOne(){
        Widget.setPlates(1);Widget.setIndicator(TriState.ON, Indicator.NSA);
        Widget.setPortValue(Port.PARALLEL,2);
        Widget.setPortValue(Port.SERIAL,1);
        Widget.setPortValue(Port.PS2,1);
        Widget.setPortValue(Port.RJ45,1);
        Widget.setDBatteries(1);
        Widget.setNumHolders(1);
        Widget.setSerialCode("2u3mr1");
    }

    @Test
    public void trainingVideoTestTwo(){
        setupTwo();
        assertEqual(20, Color.BLUE, new String[]{"Lithium Hydroxide", Lithium_Hydroxide.getFormula(),
                "48", NO_FILTER});
    }

    private void setupTwo(){
        Widget.setPlates(3);
        Widget.setPortValue(Port.PARALLEL,2);
        Widget.setPortValue(Port.SERIAL,1);
        Widget.setSerialCode("ew7qw5");
        Widget.setIndicator(TriState.ON, Indicator.MSA);
        Widget.setIndicator(TriState.ON, Indicator.NSA);
    }

    @DataProvider
    public Object[][] theGreatBerateProvider(){
        ConditionSetter first = WidgetSimulations::theGreatBerate;
        ConditionSetter second = WidgetSimulations::theGreatBerateTwo;
        ConditionSetter third = WidgetSimulations::partTwoTakeTwo;
        return new Object[][]{
                {first, 5, Color.BLUE, new String[]{"Lithium Hydroxide", Lithium_Hydroxide.getFormula(),
                        "12", NO_FILTER}},
                {second, 5, Color.RED, new String[]{"Lithium Hydroxide", Lithium_Hydroxide.getFormula(),
                        "4", FILTER}},
                {third, 10, Color.YELLOW, new String[]{"Potassium Hydroxide", Potassium_Hydroxide.getFormula(),
                        "4", NO_FILTER}}
        };
    }

    @Test(dataProvider = "theGreatBerateProvider")
    public void theGreatBerateTest(ConditionSetter setter, int vol, Color acidColor, String[] expectedArr){
        setter.setCondition();
        assertEqual(vol, acidColor, expectedArr);
    }

    private void assertEqual(int volume, Color color, String[] expected){
        String[] actual = Neutralization.titrate(volume, color).split("-");
        for (int i = 0; i < expected.length; i++)
            assertEquals(expected[i], actual[i]);
    }

    @AfterClass
    public void tearDown(){
        Widget.resetProperties();
    }
}
