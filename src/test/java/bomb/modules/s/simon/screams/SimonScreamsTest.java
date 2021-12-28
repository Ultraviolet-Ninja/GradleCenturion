package bomb.modules.s.simon.screams;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TrinarySwitch;
import bomb.modules.s.simon.SimonColors.ScreamColor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.ConditionSetter.EMPTY_SETTER;
import static bomb.modules.s.simon.SimonColors.ScreamColor.BLUE;
import static bomb.modules.s.simon.SimonColors.ScreamColor.GREEN;
import static bomb.modules.s.simon.SimonColors.ScreamColor.ORANGE;
import static bomb.modules.s.simon.SimonColors.ScreamColor.PURPLE;
import static bomb.modules.s.simon.SimonColors.ScreamColor.RED;
import static bomb.modules.s.simon.SimonColors.ScreamColor.YELLOW;
import static org.testng.Assert.assertEquals;

public class SimonScreamsTest {
    private static final String MORE_LETTERS = "akwj43";

    private void testReset() {
        Widget.resetProperties();
        SimonScreams.reset();
    }

    @DataProvider
    public Object[][] initializeMethodExceptionProvider() {
        testReset();
        ConditionSetter fillSerial = () -> Widget.setSerialCode(MORE_LETTERS);
        return new Object[][]{
                {EMPTY_SETTER, new ScreamColor[]{RED, YELLOW, ORANGE, GREEN, PURPLE, BLUE}}, //No Serial Code
                {fillSerial, new ScreamColor[]{RED, YELLOW, ORANGE, GREEN, PURPLE}}, //Only 5 elements
                {EMPTY_SETTER, new ScreamColor[]{RED, YELLOW, ORANGE, GREEN, PURPLE, GREEN}} //GREEN is repeated
        };
    }

    @Test(dataProvider = "initializeMethodExceptionProvider", expectedExceptions = IllegalArgumentException.class)
    public void initMethodExceptionTest(ConditionSetter setter, ScreamColor[] arr) {
        setter.setCondition();

        SimonScreams.initialize(arr);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void nextSolveExceptionTest() {
        testReset();

        SimonScreams.nextSolve(null);
    }

    @DataProvider
    public Object[][] trainingVideoTestProviderOne() {
        testReset();
        setUpOne();
        return new Object[][]{
                {new ScreamColor[]{BLUE, ORANGE, BLUE}, "Purple,Green"}, //Primary Rule
                {new ScreamColor[]{BLUE, ORANGE, BLUE, GREEN}, "Yellow,Blue"}, //Primary Rule
                {new ScreamColor[]{BLUE, ORANGE, BLUE, GREEN, RED}, "Red,Orange"} //Otherwise Rule
        };
    }


    @Test(dataProvider = "trainingVideoTestProviderOne")
    public void trainingVideoTestOne(ScreamColor[] flashOrder, String expected) {
        assertEquals(SimonScreams.nextSolve(flashOrder), expected);
    }

    private void setUpOne() {
        Widget.setDBatteries(1);
        Widget.setNumHolders(1);
        Widget.setIndicator(TrinarySwitch.ON, Indicator.SND);
        Widget.setIndicator(TrinarySwitch.OFF, Indicator.TRN);
        Widget.setSerialCode("zr6dt6");
        Widget.setNumberOfPlates(1);
        Widget.setPortValue(Port.RCA, 1);
        Widget.setPortValue(Port.RJ45, 1);
        Widget.setPortValue(Port.DVI, 1);
        Widget.setPortValue(Port.PS2, 1);
        SimonScreams.initialize(new ScreamColor[]{PURPLE, ORANGE, RED, GREEN, BLUE, YELLOW});
    }

    @DataProvider
    public Object[][] trainingVideoTestProviderTwo() {
        testReset();
        setUpTwo();
        return new Object[][]{
                {new ScreamColor[]{BLUE, ORANGE, PURPLE, ORANGE, BLUE}, "Blue,Purple"}, //One Two One Rule
                {new ScreamColor[]{BLUE, ORANGE, PURPLE, ORANGE, BLUE, GREEN}, "Blue,Purple"}, //One Two One Rule
                {new ScreamColor[]{BLUE, ORANGE, PURPLE, ORANGE, BLUE, GREEN, BLUE, ORANGE}, "Green,Orange"}//One Two One Rule
        };
    }

    @Test(dataProvider = "trainingVideoTestProviderTwo")
    public void trainingVideoTestTwo(ScreamColor[] flashOrder, String expected) {
        assertEquals(SimonScreams.nextSolve(flashOrder), expected);
    }

    private void setUpTwo() {
        Widget.setDBatteries(1);
        Widget.setNumHolders(1);
        Widget.setIndicator(TrinarySwitch.ON, Indicator.BOB);
        Widget.setIndicator(TrinarySwitch.OFF, Indicator.TRN);
        Widget.setIndicator(TrinarySwitch.OFF, Indicator.FRK);
        Widget.setSerialCode("kn8rx2");
        SimonScreams.initialize(new ScreamColor[]{YELLOW, BLUE, GREEN, RED, PURPLE, ORANGE});
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
