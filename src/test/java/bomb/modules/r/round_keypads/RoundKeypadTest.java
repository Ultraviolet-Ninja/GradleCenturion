package bomb.modules.r.round_keypads;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.modules.r.round_keypads.Keypad.A_T;
import static bomb.modules.r.round_keypads.Keypad.COPYRIGHT;
import static bomb.modules.r.round_keypads.Keypad.PSI1;
import static bomb.modules.r.round_keypads.Keypad.PUZZLE;
import static bomb.modules.r.round_keypads.Keypad.SPANISH_QUESTION1;
import static bomb.modules.r.round_keypads.Keypad.SPANISH_QUESTION2;

public class RoundKeypadTest {
    @BeforeMethod
    public void setup() {
        RoundKeypads.reset();
    }

    @Test
    public void noColumnHighlightTest() {
        Assert.assertEquals(RoundKeypads.determineBadColumn(), -1);
    }

    @DataProvider
    public Object[][] columnTestProvider() {
        return new Object[][]{
                {A_T, 0}, {SPANISH_QUESTION1, 1}, {COPYRIGHT, 2}, {SPANISH_QUESTION2, 3},
                {PSI1, 4}, {PUZZLE, 5}
        };
    }

    @Test(dataProvider = "columnTestProvider")
    public void columnHighlightTest(Keypad keyToChange, int expectedColumn) {
        keyToChange.setFlag(true);
        Assert.assertEquals(RoundKeypads.determineBadColumn(), expectedColumn);
    }

    @Test
    public void lastColumnPriority() {
        PUZZLE.setFlag(true);
        PSI1.setFlag(true);
        Assert.assertEquals(RoundKeypads.determineBadColumn(), 5);
    }

    @AfterClass
    public void tearDown() {
        RoundKeypads.reset();
    }
}
