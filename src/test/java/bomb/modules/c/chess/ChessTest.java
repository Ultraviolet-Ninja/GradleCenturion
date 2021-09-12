package bomb.modules.c.chess;

import bomb.Widget;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;

public class ChessTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] exceptionTestProvider() {
        return new Object[][]{
                {"", new String[]{}}, {"e60xa6", new String[]{"", "a", "b", "c", "d", "e"}},
                {"e60xa6", new String[]{"a-5", "a-5", "b-3", "c-2", "d-3", "e-6"}},
                {"e60xa6", new String[]{"a-5", "a-4", "b-3", "c-2", "d-3", "e-7"}}
        };
    }

    @Test(dataProvider = "exceptionTestProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(String serialCode, String[] input) {
        Widget.setSerialCode(serialCode);

        Chess.solve(Arrays.asList(input));
    }

    @DataProvider
    public Object[][] trainingVideoTestProvider() {
        return new Object[][]{
                {"e60xa6", new String[]{"c-5", "d-5", "a-1", "f-3", "d-1", "E-4"}, "E-6"}
        };
    }

    @Test(dataProvider = "trainingVideoTestProvider")
    public void trainingVideoTest(String serialCode, String[] input, String expectedResult) {
        Widget.setSerialCode(serialCode);

        assertEquals(Chess.solve(Arrays.asList(input)), expectedResult);
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
