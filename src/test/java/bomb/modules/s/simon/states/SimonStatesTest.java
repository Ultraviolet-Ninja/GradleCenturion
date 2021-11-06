package bomb.modules.s.simon.states;

import bomb.ConditionSetter;
import bomb.modules.s.simon.SimonColors.StateColor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.EnumSet;
import java.util.List;

import static bomb.BombSimulations.EMPTY_SETTER;
import static bomb.modules.s.simon.SimonColors.StateColor.BLUE;
import static bomb.modules.s.simon.SimonColors.StateColor.GREEN;
import static bomb.modules.s.simon.SimonColors.StateColor.RED;
import static bomb.modules.s.simon.SimonColors.StateColor.YELLOW;
import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

public class SimonStatesTest {
    @BeforeMethod
    public void setUp() {
        SimonStates.reset();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void setDominantColorExceptionTest() {
        SimonStates.setDominantColor(null);
    }

    @DataProvider
    public Object[][] calculateNextColorPressExceptionTestProvider() {
        ConditionSetter setDominantColor = () -> SimonStates.setDominantColor(RED);

        return new Object[][]{
                {EMPTY_SETTER, null}, {EMPTY_SETTER, EnumSet.noneOf(StateColor.class)},
                {setDominantColor, EnumSet.noneOf(StateColor.class)}
        };
    }

    @Test(dataProvider = "calculateNextColorPressExceptionTestProvider",
            expectedExceptions = {IllegalArgumentException.class, NullPointerException.class})
    public void calculateNextColorPressExceptionTest(ConditionSetter setter, EnumSet<StateColor> colorsFlashed) {
        setter.setCondition();

        SimonStates.calculateNextColorPress(colorsFlashed);
    }

    @DataProvider
    public Object[][] trainingVideoTestProvider() {
        return new Object[][]{
                {YELLOW, new StateColor[][]{{RED}, {RED, BLUE}, {RED}, {YELLOW, GREEN}},
                        new StateColor[]{RED, YELLOW, RED, GREEN}},
                {RED, new StateColor[][]{{BLUE}, {RED}, {RED, BLUE}, {GREEN, BLUE}},
                        new StateColor[]{BLUE, BLUE, BLUE, GREEN}},
                {YELLOW, new StateColor[][]{{BLUE}, {BLUE, RED, GREEN}, {YELLOW}, {YELLOW, GREEN, RED, BLUE}},
                        new StateColor[]{BLUE, YELLOW, YELLOW, GREEN}},
                {YELLOW, new StateColor[][]{{YELLOW, RED}, {GREEN, BLUE, RED}, {RED, GREEN}, {GREEN, BLUE}},
                        new StateColor[]{BLUE, YELLOW, BLUE, GREEN}}
        };
    }

    @Test(dataProvider = "trainingVideoTestProvider")
    public void trainingVideoTest(StateColor dominantColor, StateColor[][] stageInputs, StateColor[] expectedResults) {
        SimonStates.setDominantColor(dominantColor);

        List<StateColor> results = null;
        for (StateColor[] stage : stageInputs)
            results = SimonStates.calculateNextColorPress(convertToSet(stage));

        assertEquals(results, asList(expectedResults));
    }

    private EnumSet<StateColor> convertToSet(StateColor[] stage) {
        return EnumSet.copyOf(asList(stage));
    }

    @AfterClass
    public void tearDown() {
        SimonStates.reset();
    }
}
