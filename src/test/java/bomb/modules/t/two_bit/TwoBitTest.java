package bomb.modules.t.two_bit;

import bomb.Widget;
import bomb.enumerations.Ports;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.modules.t.two_bit.TwoBit.QUERY;
import static bomb.modules.t.two_bit.TwoBit.SUBMIT;
import static org.testng.Assert.assertEquals;

public class TwoBitTest {
    @BeforeTest
    void setUp(){
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
        return new String[][]{
                {QUERY + "gv", "02"}, {QUERY + "vt", "07"}, {SUBMIT + "gz", "89"},
                {QUERY + "vc", "77"}, {QUERY + "bd", "67"}, {SUBMIT + "vg", "93"}
        };
    }

    @Test(dataProvider = "trainingVideoProvider")
    public void trainingVideoQuerySubmitTest(String expected, String input){
        assertEquals(TwoBit.nextCode(input), expected);
    }

    private void widgetSetupOne(){
        Widget.setPlates(2);
        Widget.addPort(Ports.RJ45);
        Widget.addPort(Ports.PS2);
        Widget.addPort(Ports.PS2);
        Widget.addPort(Ports.RCA);
        Widget.setSerialCode("AI3ZC1");
    }

    @AfterClass
    public void tearDown(){
        Widget.resetProperties();
    }
}
