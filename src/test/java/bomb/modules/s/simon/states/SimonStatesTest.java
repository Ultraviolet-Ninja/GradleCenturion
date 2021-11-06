package bomb.modules.s.simon.states;

import bomb.ConditionSetter;
import bomb.modules.s.simon.SimonColors.StateColor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.EnumSet;

import static bomb.modules.s.simon.SimonColors.StateColor.RED;

public class SimonStatesTest {
    @BeforeMethod
    public void setUp() {
        SimonStates.reset();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void setTopLeftButtonColorExceptionTest() {
        SimonStates.setTopLeftButtonColor(null);
    }

    @DataProvider
    public Object[][] calculateNextColorPressExceptionTestProvider() {
        ConditionSetter empty = () ->{};
        ConditionSetter setTopLeftButtonColor = () -> SimonStates.setTopLeftButtonColor(RED);

        return new Object[][]{
                {empty, null}, {empty, EnumSet.noneOf(StateColor.class)},
                {setTopLeftButtonColor, EnumSet.noneOf(StateColor.class)}
        };
    }

    @Test(dataProvider = "calculateNextColorPressExceptionTestProvider",
            expectedExceptions = {IllegalArgumentException.class, NullPointerException.class})
    public void calculateNextColorPressExceptionTest(ConditionSetter setter, EnumSet<StateColor> colorsFlashed) {
        setter.setCondition();

        SimonStates.calculateNextColorPress(colorsFlashed);
    }

    @AfterClass
    public void tearDown() {
        SimonStates.reset();
    }
}
