package bomb.modules.c;

import bomb.modules.c.chords.ChordQualities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OldChordQualitiesTest {
    @Test
    void exceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> ChordQualities.solve("A A A A"));
    }

    @Test
    void videoTest(){
        assertEqualsAnyOrder("A +2 +2 +3", new String[]{"D#", "F", "G#", "A"});
        assertEqualsAnyOrder("F +3 +1 +6", new String[]{"A#", "D", "D#", "F#"});
        assertEqualsAnyOrder("D# +3 +5 +3", new String[]{"A", "B", "C#", "E"});
        assertEqualsAnyOrder("E +2 +1 +4", new String[]{"G#", "C#", "D#", "E"});
        assertEqualsAnyOrder("E +4 +3 +4", new String[]{"A", "D", "E", "F"});
        assertEqualsAnyOrder("D +2 +1 +4", new String[]{"B", "C#", "F#", "G#"});
    }

    @Test
    void theGreatBerate(){
        assertEqualsAnyOrder("G +5 +2 +3", new String[]{"G#", "B", "D", "E"});
        assertEqualsAnyOrder("E +3 +1 +6", new String[]{"A#", "D#", "F", "F#"});
        assertEqualsAnyOrder("C# +3 +1 +6", new String[]{"F#", "A", "C#", "D#"});

    }

    private void assertEqualsAnyOrder(String expected, String[] notes){
        int length = notes.length;
        for (int i = 0; i < length; i++) {
            String input = notes[i % length] + " " + notes[(i + 1) % length] +
                    " " + notes[(i + 2) % length] + " " + notes[(i + 3) % length];
            assertEquals(ChordQualities.NEW_CHORD + expected, ChordQualities.solve(input));
        }
    }
}
