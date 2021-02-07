package bomb.modules.s;

import bomb.Widget;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShapeShiftTest {
    @BeforeEach
    void setUp(){
        Widget.resetProperties();
    }

    @Test
    void videoTestOne(){
        testOneSetup();
        testPair(new ShiftShape[]{FLAT, FLAT}, ShapeShift.solve(ROUND, POINT));
        testPair(new ShiftShape[]{POINT, POINT}, ShapeShift.solve(ROUND, ROUND));
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
        testPair(new ShiftShape[]{TICKET, FLAT}, ShapeShift.solve(TICKET, FLAT));
        testPair(new ShiftShape[]{POINT, ROUND}, ShapeShift.solve(POINT, TICKET));
        testPair(new ShiftShape[]{FLAT, FLAT}, ShapeShift.solve(ROUND, POINT));
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

    private void testPair(ShiftShape[] expected, ShiftShape[] actual){
        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);
    }
}
