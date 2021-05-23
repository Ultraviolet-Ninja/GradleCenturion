package bomb.modules.dh;

import bomb.modules.dh.emoji.EmojiMath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class EmojiMathTest {
    @DataProvider
    public Object[][] trainingVideoProvider(){
        return new Object[][] {
                {-1, "=)(=-=):|"}, {95, ":|:|+(="}, {189, "|:=)+|:)="}, {-10, ":(:)-)::)"}
        };
    }

    @Test(dataProvider = "trainingVideoProvider")
    public void trainingVideoTest(int expected, String equation){
        assertEquals(EmojiMath.calculate(equation), expected);
    }

    @DataProvider
    public Object[][] theGreatBerateProvider(){
        return new Object[][]{
                {-54, ")=:)-:|:("}, {120, ":((=+(=)="}, {144, "(=:(+(=:)"}
        };
    }

    @Test(dataProvider = "theGreatBerateProvider")
    public void theGreatBerateTest(int expected, String equation){
        assertEquals(EmojiMath.calculate(equation), expected);
    }
}
