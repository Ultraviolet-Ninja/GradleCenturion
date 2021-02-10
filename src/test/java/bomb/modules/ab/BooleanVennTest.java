package bomb.modules.ab;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BooleanVennTest {
    @Test
    void incompleteEquationTest(){
        assertThrows(IllegalArgumentException.class, () -> BooleanVenn.resultCode(""));
        assertThrows(IllegalArgumentException.class, () -> BooleanVenn.resultCode("ABC"));
        assertThrows(IllegalArgumentException.class, () -> BooleanVenn.resultCode("A(BC)"));
        assertThrows(IllegalArgumentException.class, () -> BooleanVenn.resultCode("A(B↓C)"));
        assertThrows(IllegalArgumentException.class, () -> BooleanVenn.resultCode("A↓(BC)"));
        assertThrows(IllegalArgumentException.class, () -> BooleanVenn.resultCode("A↓↓(BC)"));
        assertThrows(IllegalArgumentException.class, () -> BooleanVenn.resultCode("A↓(A↓C)"));
        assertDoesNotThrow(() -> BooleanVenn.resultCode("A↓(B↓C)"));
    }

    @Test
    void videoTest(){ //Order: not, c, b, a, bc, ac, ab, all
        assertEquals("10001101", BooleanVenn.resultCode("(A∨B)↔C"));
        assertEquals("00010000", BooleanVenn.resultCode("(A→B)↓C"));
        assertEquals("00000110", BooleanVenn.resultCode("A∧(B⊻C)"));
        assertEquals("01001110", BooleanVenn.resultCode("(A|B)↔C"));
        assertEquals("00000001", BooleanVenn.resultCode("(A∧B)∧C"));
        assertEquals("10111111", BooleanVenn.resultCode("(A∨B)←C"));
        assertEquals("01001111", BooleanVenn.resultCode("(A|B)→C"));
        assertEquals("01110111", BooleanVenn.resultCode("A∨(B⊻C)"));
        assertEquals("10000010", BooleanVenn.resultCode("(A⊻B)↓C"));
        assertEquals("10111010", BooleanVenn.resultCode("(A←B)|C"));
        assertEquals("10010111", BooleanVenn.resultCode("A∨(B↓C)"));
        assertEquals("11001101", BooleanVenn.resultCode("(A∨B)→C"));
        assertEquals("10000111", BooleanVenn.resultCode("A⊻(B↓C)"));
        assertEquals("01111000", BooleanVenn.resultCode("A⊻(B∨C)"));
        assertEquals("10110110", BooleanVenn.resultCode("(A→B)|C"));
    }

    @Test
    void theGreatBerate(){

        assertEquals("11111000", BooleanVenn.resultCode("A|(B∨C)"));
    }
}
