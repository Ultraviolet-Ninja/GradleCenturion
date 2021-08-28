package bomb.modules.s.shape_shift;

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

import static bomb.modules.s.shape_shift.ShapeEnd.FLAT;
import static bomb.modules.s.shape_shift.ShapeEnd.POINT;
import static bomb.modules.s.shape_shift.ShapeEnd.ROUND;
import static bomb.modules.s.shape_shift.ShapeEnd.TICKET;
import static org.testng.Assert.assertEquals;

public class ShapeShiftTest {
    @BeforeMethod
    public void methodSetup() {
        Widget.resetProperties();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest() {
        ShapeShift.solve(ROUND, ROUND);
    }

    @DataProvider
    public Object[][] trainingVideoProvider() {
        ConditionSetter firstVideo = this::testOneSetup;
        ConditionSetter secondVideo = this::testTwoSetup;
        return new Object[][]{
                {firstVideo, new ShapeEnd[]{FLAT, FLAT}, ROUND, POINT},
                {firstVideo, new ShapeEnd[]{POINT, POINT}, ROUND, ROUND},
                {secondVideo, new ShapeEnd[]{TICKET, FLAT}, TICKET, FLAT},
                {secondVideo, new ShapeEnd[]{POINT, ROUND}, POINT, TICKET},
                {secondVideo, new ShapeEnd[]{FLAT, FLAT}, ROUND, POINT}
        };
    }

    @Test(dataProvider = "trainingVideoProvider")
    public void trainingVideoTest(ConditionSetter setter, ShapeEnd[] expectedArr, ShapeEnd left, ShapeEnd right) {
        setter.setCondition();
        assertPairEquals(expectedArr, ShapeShift.solve(left, right));
    }

    @DataProvider
    public Object[][] theGreatBerateProvider() {
        ConditionSetter firstTake = WidgetSimulations::theGreatBerateVideoOne;
        ConditionSetter secondTake = WidgetSimulations::theGreatBerateVideoTwo;
        ConditionSetter thirdTake = WidgetSimulations::videoTwoTakeTwo;
        return new Object[][]{
                {firstTake, new ShapeEnd[]{TICKET, POINT}, FLAT, ROUND},
                {secondTake, new ShapeEnd[]{FLAT, FLAT}, POINT, FLAT},
                {thirdTake, new ShapeEnd[]{FLAT, TICKET}, TICKET, POINT}
        };
    }

    private void testOneSetup() {
        Widget.setSerialCode("ee3eu3");
        Widget.setDBatteries(3);
        Widget.setNumHolders(3);
        Widget.setPortValue(Port.PARALLEL, 2);
        Widget.setPortValue(Port.SERIAL, 1);
    }

    private void testTwoSetup() {
        Widget.setSerialCode("c88rx4");
        Widget.setNumHolders(2);
        Widget.setDBatteries(1);
        Widget.setDoubleAs(2);
        Widget.setIndicator(TrinarySwitch.OFF, Indicator.BOB);
        Widget.setIndicator(TrinarySwitch.OFF, Indicator.IND);
        Widget.setIndicator(TrinarySwitch.OFF, Indicator.TRN);
    }

    private void assertPairEquals(ShapeEnd[] expected, ShapeEnd[] actual) {
        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
