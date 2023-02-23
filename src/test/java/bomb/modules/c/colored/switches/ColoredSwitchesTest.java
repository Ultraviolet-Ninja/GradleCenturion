package bomb.modules.c.colored.switches;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static bomb.modules.c.colored.switches.SwitchColor.BLUE;
import static bomb.modules.c.colored.switches.SwitchColor.CYAN;
import static bomb.modules.c.colored.switches.SwitchColor.GREEN;
import static bomb.modules.c.colored.switches.SwitchColor.NEUTRAL;
import static bomb.modules.c.colored.switches.SwitchColor.ORANGE;
import static bomb.modules.c.colored.switches.SwitchColor.RED;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ColoredSwitchesTest {
    @BeforeMethod
    public void setUp() {
        ColoredSwitches.reset();
    }

    @DataProvider
    public Object[][] preemptiveMoveExceptionTestProvider() {
        return new Byte[][]{
                {-1}, {32}
        };
    }

    @Test(dataProvider = "preemptiveMoveExceptionTestProvider",
            expectedExceptions = IllegalArgumentException.class)
    public void preemptiveMoveTest(byte startingState) {
        ColoredSwitches.producePreemptiveMoveList(startingState);
    }

    @DataProvider
    public Object[][] finalMoveListExceptionTestProvider() {
        return new Object[][]{
                {new SwitchColor[]{RED, CYAN, GREEN, BLUE}, -1},
                {new SwitchColor[]{RED, CYAN, GREEN, BLUE}, 0},
                {new SwitchColor[]{RED, CYAN, GREEN, BLUE, RED}, 0},
                {new SwitchColor[]{RED, CYAN, GREEN, BLUE, NEUTRAL}, 0}
        };
    }

    @Test(dataProvider = "finalMoveListExceptionTestProvider",
            expectedExceptions = {IllegalStateException.class, IllegalArgumentException.class})
    public void finalMoveListExceptionTest(SwitchColor[] switchArray, int desiredState) {
        ColoredSwitches.produceFinalMoveList(switchArray, (byte) desiredState);
    }

    @DataProvider
    public Object[][] validPreemptiveMoveTestProvider() {
        return new Object[][]{
                {0, new String[]{"1", "5", "2"}}, {31, new String[]{"3", "1", "2"}},
                {7, new String[]{"2", "1", "3"}}
        };
    }

    @Test(dataProvider = "validPreemptiveMoveTestProvider")
    public void validPreemptiveMoveTest(int startingState, String[] expectedResults) {
        List<String> converted = Arrays.asList(expectedResults);

        assertEquals(converted, ColoredSwitches.producePreemptiveMoveList((byte) startingState));
    }

    @DataProvider
    public Object[][] fullMoveListTestProvider() {
        return new Object[][] {
                {
                    28, new String[]{"5", "5", "5"}, new SwitchColor[]{GREEN, BLUE, ORANGE, RED, RED},
                        2, new String[]{"4", "3", "5", "1", "2"}
                },
                {
                    21, new String[]{"5", "4", "5"}, new SwitchColor[]{GREEN, RED, GREEN, BLUE, BLUE},
                        1, new String[]{"4", "5", "4", "3", "5", "4", "2", "1", "2"}
                },
                {
                        20, new String[]{"4", "5", "4"}, new SwitchColor[]{ORANGE, ORANGE, CYAN, ORANGE, CYAN},
                        6, new String[]{"5", "3", "5", "2", "4", "5", "2", "1", "3"}
                }
        };
    }

    @Test(dataProvider = "fullMoveListTestProvider")
    public void fullMoveListTest(int startingState, String[] preemptiveMoveList, SwitchColor[] startingColors,
                                 int desiredState, String[] expectedShortestPath) {
        assertEquals(
                ColoredSwitches.producePreemptiveMoveList((byte) startingState),
                Arrays.asList(preemptiveMoveList)
        );

        assertTrue(ColoredSwitches.isFirstStepDone());

        assertEquals(
                ColoredSwitches.produceFinalMoveList(startingColors, (byte) desiredState),
                Arrays.asList(expectedShortestPath)
        );
    }
}
