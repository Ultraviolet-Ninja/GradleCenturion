package bomb.modules.s.square.button;

import bomb.BombSimulations;
import bomb.ConditionSetter;
import bomb.Widget;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.ConditionSetter.EMPTY_SETTER;
import static bomb.enumerations.ButtonResult.HOLD;
import static bomb.enumerations.ButtonResult.TAP;
import static bomb.enumerations.Indicator.MSA;
import static bomb.enumerations.Indicator.NSA;
import static bomb.enumerations.TrinarySwitch.OFF;
import static bomb.modules.s.square.button.SquareButton.BLUE;
import static bomb.modules.s.square.button.SquareButton.CYAN;
import static bomb.modules.s.square.button.SquareButton.DARK_GRAY;
import static bomb.modules.s.square.button.SquareButton.GREEN;
import static bomb.modules.s.square.button.SquareButton.ORANGE;
import static bomb.modules.s.square.button.SquareButton.WHITE;
import static bomb.modules.s.square.button.SquareButton.YELLOW;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SquareButtonTest {
    private static final String VALID_SERIAL_CODE = "7H4WZ4";

    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] exceptionSolveTestProvider() {
        return new Object[][]{
                {"", 0, ""}, {VALID_SERIAL_CODE, -1, null}, {VALID_SERIAL_CODE, 5, null}
        };
    }

    @Test(dataProvider = "exceptionSolveTestProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionSolveTest(String serialCode, int buttonColor, String buttonText) {
        Widget.setSerialCode(serialCode);

        SquareButton.solve(buttonColor, buttonText);
    }

    @DataProvider
    public Object[][] exceptionSolveForHeldButtonTestProvider() {
        return new Object[][]{
                {-1}, {4}
        };
    }

    @Test(dataProvider = "exceptionSolveForHeldButtonTestProvider",
            expectedExceptions = IllegalArgumentException.class)
    public void exceptionSolveForHeldButtonTest(int invalidFlashColor) {
        SquareButton.solveForHeldButton(false, invalidFlashColor);
    }

    @Test
    public void firstBranchSolveTest() {
        Widget.setSerialCode(VALID_SERIAL_CODE);
        Widget.setDoubleAs(2);

        assertEquals(SquareButton.solve(BLUE, ""), HOLD.toString());
    }

    @Test
    public void secondBranchSolveTest() {
        Widget.setSerialCode(VALID_SERIAL_CODE);

        assertEquals(SquareButton.solve(BLUE, "Elevate"), TAP.toString());
        assertEquals(SquareButton.solve(YELLOW, "Elevate"), TAP.toString());
    }

    @Test
    public void thirdBranchSolveTest() {
        Widget.setSerialCode(VALID_SERIAL_CODE);

        assertEquals(SquareButton.solve(BLUE, "jade"), HOLD.toString());
        assertEquals(SquareButton.solve(YELLOW, "JADE"), HOLD.toString());
    }

    @Test
    public void fourthBranchSolveTest() {
        Widget.setSerialCode(VALID_SERIAL_CODE);

        assertEquals(SquareButton.solve(DARK_GRAY, ""),
                TAP + " when the two seconds digits on the timer match");
    }

    @Test
    public void fifthBranchSolveTest() {
        Widget.setSerialCode(VALID_SERIAL_CODE);

        assertEquals(SquareButton.solve(WHITE, "Maroon"), TAP.toString());
    }

    @Test
    public void sixthBranchSolveTest() {
        Widget.setSerialCode("7A4WZ4");
        Widget.setIndicator(OFF, NSA);
        Widget.setIndicator(OFF, MSA);

        assertEquals(SquareButton.solve(DARK_GRAY, "Maroon"), TAP.toString());
        assertEquals(SquareButton.solve(WHITE, "Maroon"), TAP.toString());
    }

    @Test
    public void seventhBranchSolveTest() {
        Widget.setSerialCode(VALID_SERIAL_CODE);

        assertEquals(SquareButton.solve(DARK_GRAY, "Maroon"), HOLD.toString());
    }

    @DataProvider
    public Object[][] solveForHeldButtonTestProvider() {
        return new Object[][]{
                {ORANGE, "0 or prime", "seconds digits add up to 3 or 13"},
                {GREEN, "one second after two seconds digits add up to a multiple 4", "seconds digits add up to 5"}
        };
    }

    @Test(dataProvider = "solveForHeldButtonTestProvider")
    public void solveForHeldButtonTest(int flashColor, String expectedFlashingResultContains,
                                       String expectedSolidColorResultContains) {
        assertTrue(
                SquareButton.solveForHeldButton(true, flashColor)
                        .contains(expectedFlashingResultContains)
        );

        assertTrue(
                SquareButton.solveForHeldButton(false, flashColor)
                        .contains(expectedSolidColorResultContains)
        );
    }

    @DataProvider
    public Object[][] solveForHeldButtonCyanTestProvider() {
        ConditionSetter startingMinuteSetter = BombSimulations::centurionDefaults;
        return new Object[][]{
                {EMPTY_SETTER, false, "two seconds digits add up to 7"},
                {EMPTY_SETTER, true, "Number of starting minutes not set.\n"},
                {startingMinuteSetter, true, "Possible combos: "}
        };
    }

    @Test(dataProvider = "solveForHeldButtonCyanTestProvider")
    public void solveForHeldButtonCyanTest(ConditionSetter startingMinuteSetter, boolean isFlashing,
                                           String expectedContains) {
        startingMinuteSetter.setCondition();

        assertTrue(
                SquareButton.solveForHeldButton(isFlashing, CYAN)
                        .contains(expectedContains)
        );
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
