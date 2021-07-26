package bomb.tools;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.tools.filter.Filter.CHAR_FILTER;
import static bomb.tools.filter.Filter.NUMBER_PATTERN;
import static org.testng.Assert.assertEquals;

public class FilterTest {
    @DataProvider
    public Object[][] letterProvider(){
        return new Object[][]{
                {"53212323u6434123", "u"},
                {"12T4h65is5 %i34s2 a s(5en34t6e4nce.", "Thisisasentence"},
                {"42^&35é", "é"}
        };
    }

    @Test(dataProvider = "letterProvider")
    public void letterTest(String filter, String expected){
        CHAR_FILTER.loadText(filter);
        assertEquals(expected, CHAR_FILTER.toNewString());
    }

    @DataProvider
    public Object[][] numberProvider(){
        return new Object[][]{
                {"asjdhwaushaw", ""}, {"Find the5 number", "5"}
        };
    }

    @Test(dataProvider = "numberProvider")
    public void numberTest(String filter, String expected){
        NUMBER_PATTERN.loadText(filter);
        assertEquals(expected, NUMBER_PATTERN.toNewString());
    }
}
