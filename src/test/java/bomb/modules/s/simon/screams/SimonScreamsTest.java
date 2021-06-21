package bomb.modules.s.simon.screams;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.Ports;
import bomb.enumerations.TriState;
import bomb.modules.s.simon.Simon.Screams;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.modules.s.simon.Simon.Screams.*;
import static org.testng.Assert.assertEquals;

public class SimonScreamsTest {
    private static final String MORE_LETTERS = "akwj43", MORE_NUMBERS = "1259wi";

    private void testReset(){
        Widget.resetProperties();
        SimonScreams.reset();
    }

    @DataProvider
    public Object[][] initMethodExceptionProvider(){
        testReset();
        ConditionSetter empty = () -> {};
        ConditionSetter fillSerial = () -> Widget.setSerialCode(MORE_LETTERS);
        return new Object[][] {
                {empty, new Screams[]{RED, YELLOW, ORANGE, GREEN, PURPLE, BLUE}}, //No Serial Code
                {fillSerial, new Screams[]{RED, YELLOW, ORANGE, GREEN, PURPLE}}, //Only 5 elements
                {empty, new Screams[]{RED, YELLOW, ORANGE, GREEN, PURPLE, GREEN}} //GREEN is repeated
        };
    }

    @Test(dataProvider = "initMethodExceptionProvider", expectedExceptions = IllegalArgumentException.class)
    public void initMethodExceptionTest(ConditionSetter setter, Screams[] arr){
        setter.setCondition();

        SimonScreams.init(arr);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void nextSolveExceptionTest(){
        testReset();

        SimonScreams.nextSolve(null);
    }

    @DataProvider
    public Object[][] trainingVideoProviderOne(){
        testReset();
        setUpOne();
        return new Object[][] {
                {new Screams[]{BLUE, ORANGE, BLUE}, "Purple,Green"},
                {new Screams[]{BLUE, ORANGE, BLUE, GREEN}, "Yellow,Blue"},
                {new Screams[]{BLUE, ORANGE, BLUE, GREEN, RED}, "Red,Orange"}
        };
    }


    @Test(dataProvider = "trainingVideoProviderOne")
    public void trainingVideoTestOne(Screams[] flashOrder, String expected){
        assertEquals(SimonScreams.nextSolve(flashOrder), expected);
    }

    private void setUpOne(){
        Widget.setDBatteries(1);
        Widget.setNumHolders(1);
        Widget.setIndicator(TriState.ON, Indicator.SND);
        Widget.setIndicator(TriState.OFF, Indicator.TRN);
        Widget.setSerialCode("zr6dt6");
        Widget.setPlates(1);
        Widget.addPort(Ports.RCA);
        Widget.addPort(Ports.RJ45);
        Widget.addPort(Ports.DVI);
        Widget.addPort(Ports.PS2);
        SimonScreams.init(new Screams[]{PURPLE, ORANGE, RED, GREEN, BLUE, YELLOW});
    }

    @DataProvider
    public Object[][] trainingVideoProviderTwo(){
        testReset();
        setUpTwo();
        return new Object[][] {
                {new Screams[]{BLUE, ORANGE, PURPLE, ORANGE, BLUE}, "Blue,Purple"},
                {new Screams[]{BLUE, ORANGE, PURPLE, ORANGE, BLUE, GREEN}, "Blue,Purple"},
                {new Screams[]{BLUE, ORANGE, PURPLE, ORANGE, BLUE, GREEN, BLUE, ORANGE}, "Green,Orange"}
        };
    }

    @Test(dataProvider = "trainingVideoProviderTwo")
    public void trainingVideoTestTwo(Screams[] flashOrder, String expected){
        assertEquals(SimonScreams.nextSolve(flashOrder), expected);
    }

    private void setUpTwo(){
        Widget.setDBatteries(1);
        Widget.setNumHolders(1);
        Widget.setIndicator(TriState.ON, Indicator.BOB);
        Widget.setIndicator(TriState.OFF, Indicator.TRN);
        Widget.setIndicator(TriState.OFF, Indicator.FRK);
        Widget.setSerialCode("kn8rx2");
        SimonScreams.init(new Screams[]{YELLOW, BLUE, GREEN, RED, PURPLE, ORANGE});
    }

    @AfterClass
    public void tearDown(){
        Widget.resetProperties();
    }
}
