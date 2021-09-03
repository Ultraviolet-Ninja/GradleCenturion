package bomb.modules.s.switches;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SwitchesTest {
    @DataProvider
    public Object[][] exceptionTestDataProvider() {
        return new Byte[][]{
                {11, 0}, {0, 11}, {1, 1}, {32, 0}
        };
    }

    @Test(dataProvider = "exceptionTestDataProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(Byte startingState, Byte desiredState) {
        Switches.produceMoveList(startingState, desiredState);
    }

    @DataProvider
    public Object[][] trainingVideoProvider() {
        return new Object[][]{
                {}, {}
        };
    }

    @Test(dataProvider = "trainingVideoProvider")
    public void trainingVideoTest() {

    }
}
