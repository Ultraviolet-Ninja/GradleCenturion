package bomb.modules.s.shape;

import bomb.Widget;
import bomb.WidgetSimulations;
import bomb.enumerations.Indicators;
import bomb.enumerations.Ports;
import bomb.enumerations.TriState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bomb.modules.s.shape.ShapeEnd.FLAT;
import static bomb.modules.s.shape.ShapeEnd.POINT;
import static bomb.modules.s.shape.ShapeEnd.ROUND;
import static bomb.modules.s.shape.ShapeEnd.TICKET;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OldShapeShiftTest {
    @BeforeEach
    void setUp(){
        Widget.resetProperties();
    }

    @Test
    void exceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> ShapeShift.solve(ROUND, ROUND));
        Widget.setSerialCode("ti243d");
        assertDoesNotThrow(() -> ShapeShift.solve(ROUND, ROUND));
    }

    @Test
    void videoTestOne(){
        testOneSetup();
        assertPairEquals(new ShapeEnd[]{FLAT, FLAT}, ShapeShift.solve(ROUND, POINT));
        assertPairEquals(new ShapeEnd[]{POINT, POINT}, ShapeShift.solve(ROUND, ROUND));
    }

    private void testOneSetup(){
        Widget.setSerialCode("ee3eu3");
        Widget.setDBatteries(3);
        Widget.setNumHolders(3);
        Widget.addPort(Ports.PARALLEL);
        Widget.addPort(Ports.PARALLEL);
        Widget.addPort(Ports.SERIAL);
    }

    @Test
    void videoTestTwo(){
        testTwoSetup();
        assertPairEquals(new ShapeEnd[]{TICKET, FLAT}, ShapeShift.solve(TICKET, FLAT));
        assertPairEquals(new ShapeEnd[]{POINT, ROUND}, ShapeShift.solve(POINT, TICKET));
        assertPairEquals(new ShapeEnd[]{FLAT, FLAT}, ShapeShift.solve(ROUND, POINT));
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

    @Test
    void theGreatBerate(){
        WidgetSimulations.theGreatBerate();
        assertPairDoesNotEqual(new ShapeEnd[]{TICKET, FLAT}, ShapeShift.solve(FLAT, ROUND));
        assertPairEquals(new ShapeEnd[]{TICKET, POINT}, ShapeShift.solve(FLAT, ROUND));
        WidgetSimulations.theGreatBerateTwo();
        assertPairEquals(new ShapeEnd[]{FLAT, FLAT}, ShapeShift.solve(POINT, FLAT));
        WidgetSimulations.partTwoTakeTwo();
        assertPairEquals(new ShapeEnd[]{FLAT, TICKET}, ShapeShift.solve(TICKET, POINT));
        WidgetSimulations.partTwoTakeThree();

    }

    private void assertPairEquals(ShapeEnd[] expected, ShapeEnd[] actual){
        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);
    }

    private void assertPairDoesNotEqual(ShapeEnd[] expected, ShapeEnd[] actual){
        assertFalse(expected[0].equals(actual[0]) && expected[1].equals(actual[1]));
    }
}
