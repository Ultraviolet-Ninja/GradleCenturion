package bomb.modules.t.two_bit;

import bomb.Widget;
import bomb.enumerations.Port;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.modules.t.two_bit.TwoBit.QUERY;
import static bomb.modules.t.two_bit.TwoBit.SUBMIT;
import static org.testng.Assert.assertEquals;

public class TwoBitTest {
    @BeforeMethod
    public void setUp(){
        Widget.resetProperties();
        TwoBit.resetStage();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void initialCodeExceptionTest() {
        TwoBit.initialCode();
    }

    @DataProvider
    public Object[] nextCodeExceptionProvider(){
        return new String[]{
                "1", "311", "ab", "3fs11"
        };
    }

    @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "nextCodeExceptionProvider")
    public void nextCodeExceptionTest(String input){
        TwoBit.nextCode(input);
    }

    @Test
    public void initialCodeTest(){
        widgetSetupOne();
        assertEquals("dk", TwoBit.initialCode());
    }

    @DataProvider
    public Object[][] trainingVideoProvider(){
        widgetSetupOne();
        return new Object[][]{
                {new String[]{QUERY + "gv", QUERY + "vt", SUBMIT + "gz"}, new String[]{"02", "07", "89"}},
                {new String[]{QUERY + "vc", QUERY + "bd", SUBMIT + "vg"}, new String[]{"77", "67", "93"}}
        };
    }

    @Test(dataProvider = "trainingVideoProvider")
    public void trainingVideoQuerySubmitTest(String[] expectedArr, String[] inputArr){
        for (int i = 0; i < expectedArr.length; i++){
            assertEquals(expectedArr[i], TwoBit.nextCode(inputArr[i]));
        }
    }

    private void widgetSetupOne(){
        Widget.setPlates(2);
        Widget.setPortValue(Port.RJ45,1);
        Widget.setPortValue(Port.PS2, 2);
        Widget.setPortValue(Port.RCA,1);
        Widget.setSerialCode("AI3ZC1");
    }

    @AfterClass
    public void tearDown(){
        Widget.resetProperties();
    }
}
