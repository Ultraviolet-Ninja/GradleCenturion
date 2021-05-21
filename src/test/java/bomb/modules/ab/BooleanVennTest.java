package bomb.modules.ab;

import bomb.modules.ab.boolean_venn.BooleanVenn;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BooleanVennTest {
    @DataProvider
    public Object[] exceptionProvider(){
        return new String[] {
                "", "AB", "ABC", "AABC", "A(BC)", "A(B↓C)", "A↓(BC)", "A↓↓(BC)", "A↓(A↓C)"
        };
    }

    @Test(dataProvider = "exceptionProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(String invalidInput){
        BooleanVenn.resultCode(invalidInput);
    }

    @DataProvider //TODO reduce the test cases
    public Object[][] trainingVideoProvider(){
        return new String[][]{
                {"(A∨B)↔C", "10001101"}, {"(A→B)↓C", "00010000"}, {"A∧(B⊻C)", "00000110"}, {"(A|B)↔C", "01001110"},
                {"(A∧B)∧C", "00000001"}, {"(A∨B)←C", "10111111"}, {"(A|B)→C", "01001111"}, {"A∨(B⊻C)", "01110111"},
                {"(A⊻B)↓C", "10000010"}, {"(A←B)|C", "10111010"}, {"A∨(B↓C)", "10010111"}, {"(A∨B)→C", "11001101"},
                {"A⊻(B↓C)", "10000111"}, {"A⊻(B∨C)", "01111000"}, {"(A→B)|C", "10110110"}
        };
    }

    @Test(dataProvider = "trainingVideoProvider")
    public void trainingVideoTest(String input, String expected){
        assertEquals(BooleanVenn.resultCode(input), expected);
    }

    @DataProvider
    public Object[][] theGreatBerateProvider(){
        return new String[][]{
                {"A↓(B∨C)", "10000000"}, {"A|(B∨C)", "11111000"}, {"(A∨B)∧C", "00001101"}
        };
    }

    @Test(dataProvider = "theGreatBerateProvider")
    public void theGreatBerateTest(String input, String expected){
        assertEquals(BooleanVenn.resultCode(input), expected);
    }
}
