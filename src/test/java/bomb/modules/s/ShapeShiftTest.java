package bomb.modules.s;

import bomb.Widget;
import bomb.WidgetSimulations;
import bomb.enumerations.Indicators;
import bomb.enumerations.Ports;
import bomb.enumerations.ShiftShape;
import bomb.enumerations.TriState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bomb.enumerations.ShiftShape.FLAT;
import static bomb.enumerations.ShiftShape.POINT;
import static bomb.enumerations.ShiftShape.ROUND;
import static bomb.enumerations.ShiftShape.TICKET;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShapeShiftTest {
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
        assertPairEquals(new ShiftShape[]{FLAT, FLAT}, ShapeShift.solve(ROUND, POINT));
        assertPairEquals(new ShiftShape[]{POINT, POINT}, ShapeShift.solve(ROUND, ROUND));
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
        assertPairEquals(new ShiftShape[]{TICKET, FLAT}, ShapeShift.solve(TICKET, FLAT));
        assertPairEquals(new ShiftShape[]{POINT, ROUND}, ShapeShift.solve(POINT, TICKET));
        assertPairEquals(new ShiftShape[]{FLAT, FLAT}, ShapeShift.solve(ROUND, POINT));
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

        WidgetSimulations.theGreatBerateTwo();
        assertPairEquals(new ShiftShape[]{FLAT, FLAT}, ShapeShift.solve(POINT, FLAT));
        //TODO - Add back
    }

    private void assertPairEquals(ShiftShape[] expected, ShiftShape[] actual){
        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);
    }
}
