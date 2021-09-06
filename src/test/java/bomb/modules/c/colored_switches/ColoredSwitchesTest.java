package bomb.modules.c.colored_switches;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static bomb.modules.c.colored_switches.SwitchColor.BLUE;
import static bomb.modules.c.colored_switches.SwitchColor.CYAN;
import static bomb.modules.c.colored_switches.SwitchColor.GREEN;
import static bomb.modules.c.colored_switches.SwitchColor.RED;
import static org.testng.Assert.assertEquals;

public class ColoredSwitchesTest {
    @BeforeMethod
    public static void setUp() {
        ColoredSwitches.reset();
    }

    @DataProvider
    public Object[][] preemptiveMoveExceptionProvider() {
        return new Byte[][]{
                {-1}, {32}
        };
    }

    @Test(dataProvider = "preemptiveMoveExceptionProvider", expectedExceptions = IllegalArgumentException.class)
    public void preemptiveMoveTest(byte startingState) {
        ColoredSwitches.producePreemptiveMoveList(startingState);
    }

    @DataProvider
    public Object[][] finalMoveListExceptionProvider() {
        return new Object[][]{
                {new SwitchColor[]{RED, CYAN, GREEN, BLUE}, 0},
                {new SwitchColor[]{RED, CYAN, GREEN, BLUE, RED}, 0}
        };
    }

    @Test(dataProvider = "finalMoveListExceptionProvider", expectedExceptions = {IllegalStateException.class, IllegalArgumentException.class})
    public void finalMoveListExceptionTest(SwitchColor[] switchArray, int desiredState) {
        ColoredSwitches.produceFinalMoveList(switchArray, (byte) desiredState);
    }

    @DataProvider
    public Object[][] validPreemptiveMoveProvider() {
        return new Object[][]{
                {0, new String[]{"1", "5", "2"}}, {31, new String[]{"3", "1", "2"}}, {7, new String[]{"2", "1", "3"}}
        };
    }

    @Test(dataProvider = "validPreemptiveMoveProvider")
    public void validPreemptiveMoveTest(int startingState, String[] expectedResults) {
        List<String> converted = Arrays.asList(expectedResults);

        assertEquals(converted, ColoredSwitches.producePreemptiveMoveList((byte) startingState));
    }
}
