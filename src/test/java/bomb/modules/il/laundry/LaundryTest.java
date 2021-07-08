package bomb.modules.il.laundry;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.WidgetSimulations;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TriState;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class LaundryTest {
    @BeforeMethod
    public void methodSetup(){
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] exceptionProvider(){
        ConditionSetter empty = () -> {};
        ConditionSetter setSerial = () -> Widget.setSerialCode("ajwf45");
        return new Object[][]{
                {empty, "1", "1"}, {setSerial, "", "1"}, {empty, "1", ""}, {empty, "1", "1"}
        };
    }

    @Test(dataProvider = "exceptionProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(ConditionSetter setter, String solved, String needy){
        setter.setCondition();
        Laundry.clean(solved, needy);
    }

    @DataProvider
    public Object[][] normalValueProvider(){
        ConditionSetter setFirst = this::setupOne;
        ConditionSetter setSecond = this::setupTwo;
        return new Object[][]{
                {setFirst, new String[]{"80F", "Low Heat", "300", "Non-Chlorine", "LEATHER - PEARL - CORSET"}, "0", "0"},
                {setSecond, new String[]{"120F", "Tumble", "No Steam", "No Tetrachlorethylene",
                        "POLYESTER - SAPPHIRE - SHIRT"}, "0", "0"}
        };
    }

    @Test(dataProvider = "normalValueProvider")
    public void normalValueTest(ConditionSetter setter, String[] expectedArr, String solved, String needy){
        setter.setCondition();
        assertContains(expectedArr, solved, needy);
    }

    private void setupOne(){
        Widget.setDBatteries(1);
        Widget.setDoubleAs(2);
        Widget.addPort(Port.SERIAL);
        Widget.setIndicator(TriState.ON, Indicator.NSA);
        Widget.setNumHolders(2);
        Widget.setSerialCode("g64dv1");
        Widget.setNumModules(11);
    }

    private void setupTwo(){
        Widget.setNumModules(11);
        Widget.setSerialCode("7h1iv1");
        Widget.setDBatteries(1);
        Widget.setNumHolders(1);
        Widget.setIndicator(TriState.ON, Indicator.NSA);
        Widget.setIndicator(TriState.OFF, Indicator.FRQ);
        Widget.setPlates(1);
        Widget.addPort(Port.PARALLEL);
    }

    @DataProvider
    public Object[][] theGreatBerateProvider(){
        ConditionSetter setFirst = WidgetSimulations::theGreatBerate;
        ConditionSetter setSecond = WidgetSimulations::theGreatBerateTwo;
        ConditionSetter setThird = WidgetSimulations::partTwoTakeTwo;
        return new Object[][]{
                {setSecond, new String[]{"105F", "Medium Heat", "110", "Wet Cleaning", "CORDUROY - MALINITE - SCARF"},
                        "0", "1"},
                {setThird, new String[]{"80F", "Medium Heat", "300", "No Tetrachlorethylene",
                        "LEATHER - MALINITE - CORSET"}, "0", "1"}
        };
    }

    @Test(dataProvider = "theGreatBerateProvider")
    public void theGreatBerate(ConditionSetter setter, String[] expectedArr, String solved, String needy){
        setter.setCondition();
        assertContains(expectedArr, solved, needy);
    }

    @Test
    public void thanksBobTest(){
        WidgetSimulations.thanksBobCenturion();
        String[] actual = Laundry.clean("0", "0");
        String[] expected = {"105F", "Don't Tumble Dry", "300", "Bleach", "CORDUROY - JADE - CORSET", Laundry.THANKS_BOB};

        if (actual.length != expected.length) fail("Size mismatch");
        else{
            for (int i = 0; i < actual.length; i++){
                assertTrue(actual[i].contains(expected[i]));
            }
        }
    }

    private void assertContains(String[] expected, String solved, String needy){
        String[] actual = Laundry.clean(solved, needy);
        for (int i = 0; i < expected.length; i++)
            assertTrue(actual[i].contains(expected[i]));
    }

    @AfterClass
    public void tearDown(){
        Widget.resetProperties();
    }
}
