package bomb.modules.c;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChordQualitiesTest {
    @Test
    void exceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> ChordQualities.solve("A A A A"));
    }

    @Test
    void videoTest(){
        assertEqualsAnyOrder("New Chord: A +2 +2 +3", new String[]{"D#", "F", "G#", "A"});
    }

    private void assertEqualsAnyOrder(String expected, String[] notes){
        int length = notes.length;
        for (int i = 0; i < length; i++) {
            String input = notes[i % length] + " " + notes[(i + 1) % length] +
                    " " + notes[(i + 2) % length] + " " + notes[(i + 3) % length];
            assertEquals(expected, ChordQualities.solve(input));
        }
    }
}
