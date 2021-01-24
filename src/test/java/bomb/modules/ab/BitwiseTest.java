package bomb.modules.ab;

import bomb.Widget;
import bomb.enumerations.Indicators;
import bomb.enumerations.Ports;
import bomb.enumerations.TriState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bomb.enumerations.BitwiseOps.AND;
import static bomb.enumerations.BitwiseOps.NOT;
import static bomb.enumerations.BitwiseOps.OR;
import static bomb.enumerations.BitwiseOps.XOR;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class BitwiseTest {
    private static final int DEFAULT_START_TIME = 5;
    private static final String MIN_AND = "00000001", MIN_OR = "00000011", MIN_XOR = "00000010", MIN_NOT = "11111100",
            MAX_AND = "11111110", MAX_OR = "11111111", MAX_XOR = "00000001", MAX_NOT = "00000000",
            LAST_DIGIT_EVEN = "ask412", LAST_DIGIT_ODD = "wo24l5";

    @BeforeEach
    void clearProperties(){
        Widget.resetProperties();
    }

    @Test
    void exceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> Bitwise.getByte(NOT));
        Widget.setNumModules(1);
        assertThrows(IllegalArgumentException.class, () -> Bitwise.getByte(NOT));
        Widget.setStartTime(4);
        assertThrows(IllegalArgumentException.class, () -> Bitwise.getByte(NOT));
        Widget.setSerialCode("942kws");
        assertDoesNotThrow(() -> Bitwise.getByte(NOT));
    }

    @Test
    void minimumConditionTest(){
        setEssentialFalseConditions();
        Widget.setDoubleAs(2);
        assertEquals(MIN_AND, Bitwise.getByte(AND));
        assertEquals(MIN_OR, Bitwise.getByte(OR));
        assertEquals(MIN_XOR, Bitwise.getByte(XOR));
        assertEquals(MIN_NOT, Bitwise.getByte(NOT));
    }

    @Test
    void maximumConditionTest(){
        setEssentialTrueConditions();
        maximizeConditions();
        assertEquals(MAX_AND, Bitwise.getByte(AND));
        assertEquals(MAX_OR, Bitwise.getByte(OR));
        assertEquals(MAX_XOR, Bitwise.getByte(XOR));
        assertEquals(MAX_NOT, Bitwise.getByte(NOT));
    }

    private void setEssentialTrueConditions(){
        Widget.setSerialCode(LAST_DIGIT_ODD);
        Widget.setStartTime(DEFAULT_START_TIME);
        Widget.setNumModules(6);
    }

    private void setEssentialFalseConditions(){
        Widget.setSerialCode(LAST_DIGIT_EVEN);
        Widget.setStartTime(DEFAULT_START_TIME);
        Widget.setNumModules(1);
    }

    private void maximizeConditions(){
        for (int i = 2; i >= 0; i--) Widget.addPort(Ports.PARALLEL);
        Widget.setIndicator(TriState.ON, Indicators.BOB);
        Widget.setIndicator(TriState.ON, Indicators.NSA);
        Widget.setIndicator(TriState.OFF, Indicators.CLR);
        Widget.setIndicator(TriState.OFF, Indicators.CAR);
        Widget.setDBatteries(1);
        Widget.setNumHolders(2);

    }
}
