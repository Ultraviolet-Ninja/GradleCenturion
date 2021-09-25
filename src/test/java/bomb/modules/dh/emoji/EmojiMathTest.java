package bomb.modules.dh.emoji;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class EmojiMathTest {
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void invalidityTest() {
        EmojiMath.calculate(":||+):");
    }

    @DataProvider
    public Object[][] trainingVideoTestProvider() {
        return new Object[][]{
                {-1, "=)(=-=):|"}, {95, ":|:|+(="}, {189, "|:=)+|:)="}, {-10, ":(:)-)::)"}
        };
    }

    @Test(dataProvider = "trainingVideoTestProvider")
    public void trainingVideoTest(int expected, String equation) {
        assertEquals(EmojiMath.calculate(equation), expected);
    }

    @DataProvider
    public Object[][] theGreatBerateSimulationProvider() {
        return new Object[][]{
                {-54, ")=:)-:|:("}, {120, ":((=+(=)="}, {144, "(=:(+(=:)"}
        };
    }

    @Test(dataProvider = "theGreatBerateSimulationProvider")
    public void theGreatBerateTest(int expected, String equation) {
        assertEquals(EmojiMath.calculate(equation), expected);
    }
}
