package bomb.modules.ab.blind.alley;

import bomb.BombSimulations;
import bomb.ConditionSetter;
import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.TrinarySwitch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Port.DVI;
import static bomb.enumerations.Port.PARALLEL;
import static bomb.enumerations.Port.PS2;
import static bomb.enumerations.Port.RCA;
import static bomb.enumerations.Port.RJ45;
import static bomb.enumerations.Port.SERIAL;
import static bomb.enumerations.TrinarySwitch.ON;
import static org.testng.Assert.assertEquals;

public class BlindAlleyTest {
    @BeforeMethod
    public void methodSetup() {
        Widget.resetProperties();
        BlindAlley.reset();
    }

    @Test
    public void emptyTest() {
        assertArrayEquals(new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}});
    }

    @Test
    public void trainingVideoTestOne() {
        widgetSetupOne();
        assertArrayEquals(new int[][]{{1, 1, 0}, {1, 0, 2}, {1, 2, 0}});
    }

    private void widgetSetupOne() {
        Widget.setSerialCode("bu7we6");
        Widget.setDBatteries(1);
        Widget.setNumHolders(1);
        Widget.setIndicator(ON, CAR);
        Widget.setPortValue(DVI, 1);
        Widget.setPortValue(RJ45, 1);
        Widget.setPortValue(PS2, 1);
        Widget.setPortValue(PARALLEL, 1);
        Widget.setPortValue(SERIAL, 1);
        Widget.setNumberOfPlates(2);
    }

    @Test
    void videoTestTwo() {
        widgetSetupTwo();
        assertArrayEquals(new int[][]{{0, 1, 0}, {2, 1, 1}, {1, 1, 2}});
    }

    private void widgetSetupTwo() {
        Widget.setNumHolders(1);
        Widget.setDoubleAs(2);
        Widget.setNumberOfPlates(2);
        Widget.setPortValue(DVI, 1);
        Widget.setPortValue(RJ45, 1);
        Widget.setPortValue(PS2, 1);
        Widget.setPortValue(PARALLEL, 1);
        Widget.setPortValue(RCA, 1);
        Widget.setSerialCode("718pz5");
        Widget.setIndicator(ON, Indicator.SND);
        Widget.setIndicator(TrinarySwitch.OFF, Indicator.FRQ);
    }

    @DataProvider
    public Object[][] theGreatBerateSimulationProvider() {
        ConditionSetter partOne = BombSimulations::theGreatBerateVideoOne;
        ConditionSetter partTwo = BombSimulations::theGreatBerateVideoTwo;
        ConditionSetter partThree = BombSimulations::videoTwoTakeTwo;
        return new Object[][]{
                {partOne, new int[][]{{0, 0, 0}, {0, 0, 0}, {1, 2, 0}}}, {partTwo, new int[][]{{1, 1, 0}, {1, 0, 2}, {1, 2, 1}}},
                {partThree, new int[][]{{1, 0, 0}, {0, 1, 0}, {0, 2, 2}}}
        };
    }

    @Test(dataProvider = "theGreatBerateSimulationProvider")
    public void theGreatBerateTest(ConditionSetter setter, int[][] arr) {
        setter.setCondition();
        assertArrayEquals(arr);
    }

    private void assertArrayEquals(int[][] numbers) {
        int[][] actual = BlindAlley.getAlleyCat();
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++)
                assertEquals(numbers[i][j], actual[i][j]);
        }
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
