package bomb.tools;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.tools.Mechanics.LOWERCASE_REGEX;
import static bomb.tools.Mechanics.NUMBER_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;
import static org.testng.Assert.assertEquals;

public class FilterTest {
    @DataProvider
    public Object[][] letterProvider(){
        return new Object[][]{
                {"53212323u6434123", "u"},
                {"12T4h65is5 %i34s2 a s(5en34t6e4nce.", "thisisasentence"},
                {"42^&35é", "é"}
        };
    }

    @Test(dataProvider = "letterProvider")
    public void letterTest(String filter, String expected){
        assertEquals(expected, ultimateFilter(filter, LOWERCASE_REGEX));
    }

    @DataProvider
    public Object[][] numberProvider(){
        return new Object[][]{
                {"asjdhwaushaw", ""}, {"Find the5 number", "5"}
        };
    }

    @Test(dataProvider = "numberProvider")
    public void numberTest(String filter, String expected){
        assertEquals(expected, ultimateFilter(filter, NUMBER_REGEX));
    }
}
