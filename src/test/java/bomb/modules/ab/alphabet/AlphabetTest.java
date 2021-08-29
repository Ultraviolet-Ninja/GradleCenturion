package bomb.modules.ab.alphabet;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class AlphabetTest {

    @DataProvider
    public Object[] exceptionProvider() {
        return new Object[]{
                "A", "AABC"
        };
    }

    @Test(dataProvider = "exceptionProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(String exceptionInput) {
        Alphabet.order(exceptionInput);
    }

    @DataProvider
    public Object[][] fourLetterProvider() {
        return new Object[][]{
                {"JQXZ", "XZQJ"}, {"OKBV", "KOVB"}, {"PQJS", "QJPS"}, {"IRNM", "RINM"},
                {"QYDX", "YDXQ"}, {"ARGF", "FGAR"}
        };
    }

    @Test(dataProvider = "fourLetterProvider")
    public void fourLetterTest(String expected, String testPhrase) {
        assertEquals(Alphabet.order(testPhrase), expected);
    }

    @DataProvider
    public Object[][] theGreatBerateProvider() {
        return new Object[][]{
                {"ZNYL", "ZYNL"}, {"YKQV", "QYVK"}, {"ACHZ", "HZAC"}
        };
    }

    @Test(dataProvider = "theGreatBerateProvider")
    public void theGreatBerate(String expected, String testPhrase) {
        assertEquals(Alphabet.order(testPhrase), expected);
    }
}
