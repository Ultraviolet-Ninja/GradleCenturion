package bomb.modules.s.shape_shift;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.WidgetSimulations;
import bomb.enumerations.Indicators;
import bomb.enumerations.Ports;
import bomb.enumerations.TriState;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.modules.s.shape_shift.ShiftShape.FLAT;
import static bomb.modules.s.shape_shift.ShiftShape.POINT;
import static bomb.modules.s.shape_shift.ShiftShape.ROUND;
import static bomb.modules.s.shape_shift.ShiftShape.TICKET;
import static org.testng.Assert.assertEquals;

public class ShapeShiftTest {
    @BeforeMethod
    public void methodSetup(){
        Widget.resetProperties();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(){
        ShapeShift.solve(ROUND, ROUND);
    }

    @DataProvider
    public Object[][] trainingVideoProvider(){
        ConditionSetter firstVideo = this::testOneSetup;
        ConditionSetter secondVideo = this::testTwoSetup;
        return new Object[][]{
                {firstVideo, new ShiftShape[]{FLAT, FLAT}, ROUND, POINT},
                {firstVideo, new ShiftShape[]{POINT, POINT}, ROUND, ROUND},
                {secondVideo, new ShiftShape[]{TICKET, FLAT}, TICKET, FLAT},
                {secondVideo, new ShiftShape[]{POINT, ROUND}, POINT, TICKET},
                {secondVideo, new ShiftShape[]{FLAT, FLAT}, ROUND, POINT}
        };
    }

    @Test(dataProvider = "trainingVideoProvider")
    public void trainingVideoTest(ConditionSetter setter, ShiftShape[] expectedArr, ShiftShape left, ShiftShape right){
        setter.setCondition();
        assertPairEquals(expectedArr, ShapeShift.solve(left, right));
    }

    @DataProvider
    public Object[][] theGreatBerateProvider(){
        ConditionSetter firstTake = WidgetSimulations::theGreatBerate;
        ConditionSetter secondTake = WidgetSimulations::theGreatBerateTwo;
        ConditionSetter thirdTake = WidgetSimulations::partTwoTakeTwo;
        return new Object[][]{
                {firstTake, new ShiftShape[]{TICKET, POINT}, FLAT, ROUND},
                {secondTake, new ShiftShape[]{FLAT, FLAT}, POINT, FLAT},
                {thirdTake, new ShiftShape[]{FLAT, TICKET}, TICKET, POINT}
        };
    }

    private void testOneSetup(){
        Widget.setSerialCode("ee3eu3");
        Widget.setDBatteries(3);
        Widget.setNumHolders(3);
        Widget.addPort(Ports.PARALLEL);
        Widget.addPort(Ports.PARALLEL);
        Widget.addPort(Ports.SERIAL);
    }

    private void testTwoSetup(){
        Widget.setSerialCode("c88rx4");
        Widget.setNumHolders(2);
        Widget.setDBatteries(1);
        Widget.setDoubleAs(2);
        Widget.setIndicator(TriState.OFF, Indicators.BOB);
        Widget.setIndicator(TriState.OFF, Indicators.IND);
        Widget.setIndicator(TriState.OFF, Indicators.TRN);
    }

    private void assertPairEquals(ShiftShape[] expected, ShiftShape[] actual){
        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);
    }
}
