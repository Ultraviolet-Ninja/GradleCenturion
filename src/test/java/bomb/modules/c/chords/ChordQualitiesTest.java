package bomb.modules.c.chords;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ChordQualitiesTest {
    @DataProvider
    public Object[][] exceptionProvider() {
        return new Object[][]{
                {"A A A A"}, {"A B C"}, {"A B C H"}, {"A B C D"}
        };
    }

    @Test(dataProvider = "exceptionProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(String invalidInput) {
        ChordQualities.solve(invalidInput);
    }

    @DataProvider
    public Object[][] trainingVideoProvider() {
        return new String[][]{
                {"A +2 +2 +3", "D#", "F", "G#", "A"}, {"F +3 +1 +6", "A#", "D", "D#", "F#"},
                {"D# +3 +5 +3", "A", "B", "C#", "E"}, {"E +2 +1 +4", "G#", "C#", "D#", "E"},
                {"E +4 +3 +4", "A", "D", "E", "F"}, {"D +2 +1 +4", "B", "C#", "F#", "G#"}
        };
    }

    @Test(dataProvider = "trainingVideoProvider")
    public void trainingVideoTest(String expected, String... testSet) {
        assertEqualsAnyOrder(expected, testSet);
    }

    @DataProvider
    public Object[][] theGreatBerateProvider() {
        return new String[][]{
                {"G +5 +2 +3", "G#", "B", "D", "E"}, {"E +3 +1 +6", "A#", "D#", "F", "F#"},
                {"C# +3 +1 +6", "F#", "A", "C#", "D#"}
        };
    }

    @Test(dataProvider = "theGreatBerateProvider")
    public void theGreatBerateTest(String expected, String... testSet) {
        assertEqualsAnyOrder(expected, testSet);
    }

    @Test
    public void outOfOrderTest() {
        assertEqualsAnyOrder("G +5 +2 +3", "B", "G#", "D", "E");
    }

    private void assertEqualsAnyOrder(String expected, String... notes) {
        int length = notes.length;
        for (int i = 0; i < length; i++) {
            String input = notes[i % length] + " " + notes[(i + 1) % length] +
                    " " + notes[(i + 2) % length] + " " + notes[(i + 3) % length];
            assertEquals(ChordQualities.solve(input), ChordQualities.NEW_CHORD + expected);
        }
    }
}
