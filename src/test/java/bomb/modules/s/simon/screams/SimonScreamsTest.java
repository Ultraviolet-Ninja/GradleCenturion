package bomb.modules.s.simon.screams;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TrinaryState;
import bomb.modules.s.simon.SimonColors.Screams;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.modules.s.simon.SimonColors.Screams.*;
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

        SimonScreams.initialize(arr);
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
                {new Screams[]{BLUE, ORANGE, BLUE}, "Purple,Green"}, //Primary Rule
                {new Screams[]{BLUE, ORANGE, BLUE, GREEN}, "Yellow,Blue"}, //Primary Rule
                {new Screams[]{BLUE, ORANGE, BLUE, GREEN, RED}, "Red,Orange"} //Otherwise Rule
        };
    }


    @Test(dataProvider = "trainingVideoProviderOne")
    public void trainingVideoTestOne(Screams[] flashOrder, String expected){
        assertEquals(SimonScreams.nextSolve(flashOrder), expected);
    }

    private void setUpOne(){
        Widget.setDBatteries(1);
        Widget.setNumHolders(1);
        Widget.setIndicator(TrinaryState.ON, Indicator.SND);
        Widget.setIndicator(TrinaryState.OFF, Indicator.TRN);
        Widget.setSerialCode("zr6dt6");
        Widget.setNumberOfPlates(1);
        Widget.setPortValue(Port.RCA,1);
        Widget.setPortValue(Port.RJ45,1);
        Widget.setPortValue(Port.DVI,1);
        Widget.setPortValue(Port.PS2,1);
        SimonScreams.initialize(new Screams[]{PURPLE, ORANGE, RED, GREEN, BLUE, YELLOW});
    }

    @DataProvider
    public Object[][] trainingVideoProviderTwo(){
        testReset();
        setUpTwo();
        return new Object[][] {
                {new Screams[]{BLUE, ORANGE, PURPLE, ORANGE, BLUE}, "Blue,Purple"}, //One Two One Rule
                {new Screams[]{BLUE, ORANGE, PURPLE, ORANGE, BLUE, GREEN}, "Blue,Purple"}, //One Two One Rule
                {new Screams[]{BLUE, ORANGE, PURPLE, ORANGE, BLUE, GREEN, BLUE, ORANGE}, "Green,Orange"}//One Two One Rule
        };
    }

    @Test(dataProvider = "trainingVideoProviderTwo")
    public void trainingVideoTestTwo(Screams[] flashOrder, String expected){
        assertEquals(SimonScreams.nextSolve(flashOrder), expected);
    }

    private void setUpTwo(){
        Widget.setDBatteries(1);
        Widget.setNumHolders(1);
        Widget.setIndicator(TrinaryState.ON, Indicator.BOB);
        Widget.setIndicator(TrinaryState.OFF, Indicator.TRN);
        Widget.setIndicator(TrinaryState.OFF, Indicator.FRK);
        Widget.setSerialCode("kn8rx2");
        SimonScreams.initialize(new Screams[]{YELLOW, BLUE, GREEN, RED, PURPLE, ORANGE});
    }

    @AfterClass
    public void tearDown(){
        Widget.resetProperties();
    }
}
