package bomb.modules.c.chords;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class ChordQualitiesTest {
    @Test
    public void exceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> ChordQualities.solve("A A A A"));
    }

    @DataProvider
    public Object[][] trainingVideoProvider(){
        return new String[][]{
                {"A +2 +2 +3", "D#", "F", "G#", "A"}, {"F +3 +1 +6", "A#", "D", "D#", "F#"},
                {"D# +3 +5 +3", "A", "B", "C#", "E"}, {"E +2 +1 +4", "G#", "C#", "D#", "E"},
                {"E +4 +3 +4", "A", "D", "E", "F"}, {"D +2 +1 +4", "B", "C#", "F#", "G#"}
        };
    }

    @Test(dataProvider = "trainingVideoProvider")
    public void trainingVideoTest(String expected, String ... testSet){
        assertEqualsAnyOrder(expected, testSet);
    }

    @DataProvider
    public Object[][] theGreatBerateProvider(){
        return new String[][]{
                {"G +5 +2 +3", "G#", "B", "D", "E"}, {"E +3 +1 +6", "A#", "D#", "F", "F#"},
                {"C# +3 +1 +6", "F#", "A", "C#", "D#"}
        };
    }

    @Test(dataProvider = "theGreatBerateProvider")
    public void theGreatBerateTest(String expected, String ... testSet){
        assertEqualsAnyOrder(expected, testSet);
    }

    private void assertEqualsAnyOrder(String expected, String[] notes){
        int length = notes.length;
        for (int i = 0; i < length; i++) {
            String input = notes[i % length] + " " + notes[(i + 1) % length] +
                    " " + notes[(i + 2) % length] + " " + notes[(i + 3) % length];
            assertEquals(ChordQualities.solve(input), ChordQualities.NEW_CHORD + expected);
        }
    }
}
