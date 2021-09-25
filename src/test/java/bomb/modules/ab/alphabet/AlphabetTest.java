package bomb.modules.ab.alphabet;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class AlphabetTest {

    @DataProvider
    public Object[][] exceptionTestProvider() {
        return new Object[][]{
                {"A"}, {"AABC"}
        };
    }

    @Test(dataProvider = "exceptionTestProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(String exceptionInput) {
        Alphabet.order(exceptionInput);
    }

    @DataProvider
    public Object[][] fourLetterTestProvider() {
        return new Object[][]{
                {"JQXZ", "XZQJ"}, {"OKBV", "KOVB"}, {"PQJS", "QJPS"}, {"IRNM", "RINM"},
                {"QYDX", "YDXQ"}, {"ARGF", "FGAR"}
        };
    }

    @Test(dataProvider = "fourLetterTestProvider")
    public void fourLetterTest(String expected, String testPhrase) {
        assertEquals(Alphabet.order(testPhrase), expected);
    }

    @DataProvider
    public Object[][] theGreatBerateSimulationProvider() {
        return new Object[][]{
                {"ZNYL", "ZYNL"}, {"YKQV", "QYVK"}, {"ACHZ", "HZAC"}
        };
    }

    @Test(dataProvider = "theGreatBerateSimulationProvider")
    public void theGreatBerate(String expected, String testPhrase) {
        assertEquals(Alphabet.order(testPhrase), expected);
    }
}
