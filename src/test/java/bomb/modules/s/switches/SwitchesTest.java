package bomb.modules.s.switches;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class SwitchesTest {
    @DataProvider
    public Object[][] exceptionTestDataProvider() {
        return new Byte[][]{
                {11, 0}, {0, 11}, {1, 1}, {32, 0}, {0, 32}
        };
    }

    @Test(dataProvider = "exceptionTestDataProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(Byte startingState, Byte desiredState) {
        Switches.produceMoveList(startingState, desiredState);
    }

    @DataProvider
    public Object[][] trainingVideoTestProvider() {
        return new Object[][]{
                {25, 2, new String[]{"1", "2", "5", "4"}}, {21, 9, new String[]{"1", "3", "2"}},
                {6, 31, new String[]{"3", "4", "5", "2", "1", "4", "3"}}, {31, 1, new String[]{"3", "4", "1", "2"}},
                {7, 8, new String[]{"3", "4", "5", "2"}}
        };
    }

    @Test(dataProvider = "trainingVideoTestProvider")
    public void trainingVideoTest(int startingState, int desiredState, String[] expectedResult) {
        List<String> converted = Arrays.asList(expectedResult);

        assertEquals(Switches.produceMoveList((byte) startingState, (byte) desiredState), converted);
    }
}
