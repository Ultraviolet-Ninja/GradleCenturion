package bomb.modules.ab;

import bomb.modules.ab.alphabet.Alphabet;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class AlphabetTest {
    @DataProvider
    public Object[][] fourLetterProvider(){
        return new Object[][] {
                {"JQXZ", "XZQJ"}, {"OKBV", "KOVB"}, {"PQJS", "QJPS"}, {"IRNM", "RINM"},
                {"QYDX", "YDXQ"}, {"ARGF", "FGAR"}
        };
    }

    @Test(dataProvider = "fourLetterProvider")
    public void fourLetterTest(String expected, String testPhrase){
        assertEquals(Alphabet.order(testPhrase), expected);
    }

    @DataProvider
    public Object[][] theGreatBerateProvider(){
        return new Object[][]{
                {"ZNYL", "ZYNL"}, {"YKQV", "QYVK"}, {"ACHZ", "HZAC"}
        };
    }

    @Test(dataProvider = "theGreatBerateProvider")
    public void theGreatBerate(String expected, String testPhrase){
        assertEquals(Alphabet.order(testPhrase), expected);
    }
}
