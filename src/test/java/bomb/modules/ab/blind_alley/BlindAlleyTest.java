package bomb.modules.ab.blind_alley;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.WidgetSimulations;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TrinarySwitch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BlindAlleyTest {
    @BeforeMethod
    public void methodSetup(){
        Widget.resetProperties();
        BlindAlley.reset();
    }

    @Test
    public void emptyTest(){
        assertArrayEquals(new int[][]{{0,0,0}, {0,0,0}, {0,0,0}});
    }

    @Test
    public void trainingVideoTestOne(){
        widgetSetupOne();
        assertArrayEquals(new int[][] {{1,1,0}, {1,0,2}, {1,2,0}});
    }

    private void widgetSetupOne(){
        Widget.setSerialCode("bu7we6");
        Widget.setDBatteries(1);
        Widget.setNumHolders(1);
        Widget.setIndicator(TrinarySwitch.ON, Indicator.CAR);
        Widget.setPortValue(Port.DVI,1);
        Widget.setPortValue(Port.RJ45,1);
        Widget.setPortValue(Port.PS2,1);
        Widget.setPortValue(Port.PARALLEL,1);
        Widget.setPortValue(Port.SERIAL,1);
        Widget.setNumberOfPlates(2);
    }

    @Test
    void videoTestTwo(){
        widgetSetupTwo();
        assertArrayEquals(new int[][]{{0,1,0}, {2,1,1}, {1,1,2}});
    }

    private void widgetSetupTwo(){
        Widget.setNumHolders(1);
        Widget.setDoubleAs(2);
        Widget.setNumberOfPlates(2);
        Widget.setPortValue(Port.DVI,1);
        Widget.setPortValue(Port.RJ45,1);
        Widget.setPortValue(Port.PS2,1);
        Widget.setPortValue(Port.PARALLEL,1);
        Widget.setPortValue(Port.RCA,1);
        Widget.setSerialCode("718pz5");
        Widget.setIndicator(TrinarySwitch.ON, Indicator.SND);
        Widget.setIndicator(TrinarySwitch.OFF, Indicator.FRQ);
    }

    @DataProvider
    public Object[][] theGreatBerateProvider(){
        ConditionSetter partOne = WidgetSimulations::theGreatBerateVideoOne;
        ConditionSetter partTwo = WidgetSimulations::theGreatBerateVideoTwo;
        ConditionSetter partThree = WidgetSimulations::videoTwoTakeTwo;
        return new Object[][]{
                {partOne, new int[][]{{0,0,0}, {0,0,0}, {1,2,0}}}, {partTwo, new int[][]{{1,1,0}, {1,0,2}, {1,2,1}}},
                {partThree, new int[][]{{1,0,0}, {0,1,0}, {0,2,2}}}
        };
    }

    @Test(dataProvider = "theGreatBerateProvider")
    public void theGreatBerateTest(ConditionSetter setter, int[][] arr){
        setter.setCondition();
        assertArrayEquals(arr);
    }

    private void assertArrayEquals(int[][] numbers){
        int[][] actual = BlindAlley.getAlleyCat();
        for (int i = 0; i < numbers.length; i++){
            for (int j = 0; j < numbers[i].length; j++)
                assertEquals(numbers[i][j], actual[i][j]);
        }
    }

    @AfterClass
    public void tearDown(){
        Widget.resetProperties();
    }
}
