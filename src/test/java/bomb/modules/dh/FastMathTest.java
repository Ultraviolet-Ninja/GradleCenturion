package bomb.modules.dh;

import bomb.Widget;
import bomb.enumerations.Indicators;
import bomb.enumerations.Ports;
import bomb.enumerations.TriState;
import bomb.modules.dh.fast_math.FastMath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FastMathTest {
    @BeforeEach
    void setUp(){
        Widget.resetProperties();
    }

    @Test
    void exceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> FastMath.solve(""));
        assertThrows(IllegalArgumentException.class, () -> FastMath.solve(null));
        assertThrows(IllegalArgumentException.class, () -> FastMath.solve("AY"));
        assertThrows(IllegalArgumentException.class, () -> FastMath.solve("AZ"));
    }

    @Test
    void allPreconditionsTest(){
        Widget.addPort(Ports.SERIAL);
        Widget.addPort(Ports.RJ45);
        Widget.setIndicator(TriState.ON, Indicators.MSA);
        Widget.setDBatteries(4);
        Widget.setSerialCode("fr4op2"); //In total, adds 41 to the count
        assertEquals("40", FastMath.solve("zz"));
        assertEquals("81", FastMath.solve("xS"));
        assertEquals("22", FastMath.solve("KK"));
    }

    @Test
    void belowZeroTest(){
        Widget.setSerialCode("fr4op2");
        Widget.setDBatteries(4);//In total, adds -20 to the count
        assertEquals("41", FastMath.solve("ab"));
        assertEquals("30", FastMath.solve("Dg"));
        assertEquals("43", FastMath.solve("BX"));
    }

    @Test
    void noPreconditionsTest(){
        Widget.setSerialCode("nr4op2");
        assertEquals("25", FastMath.solve("aa"));
        assertEquals("40", FastMath.solve("xS"));
        assertEquals("14", FastMath.solve("KT"));
    }
}
