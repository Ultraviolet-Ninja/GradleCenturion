package bomb.modules.wz.word.search;

import bomb.tools.Coordinates;
import org.javatuples.Pair;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class WordFinderTest {
    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Provided grid is not a square")
    public void irregularGridValidationTest() {
        int length = 3;
        char[][] grid = new char[length][];

        for (int i = 0; i < length; i++) {
            grid[i] = new char[i];
        }
        WordFinder.findWordCoordinates(grid, Set.of("MATH", "SEE", "HELP", "FOUND"));
    }

    @DataProvider
    public Object[][] nonNullTestProvider() {
        return new Object[][]{
                {Set.of("MATH", "SEE", "HELP", "FOUND"), new Coordinates(1, 5), new Coordinates(1,1),
                        new char[][]{
                                {'R', 'S', 'R', 'B', 'Q', 'P'},
                                {'E', 'D', 'A', 'E', 'D', 'O'},
                                {'A', 'N', 'C', 'E', 'D', 'R'},
                                {'D', 'U', 'S', 'P', 'A', 'T'},
                                {'L', 'O', 'O', 'K', 'R', 'Q'},
                                {'A', 'F', 'I', 'N', 'D', 'H'}
                }},
                {Set.of("INDIA", "MODULE", "LIST", "DELTA"), new Coordinates(5,4), new Coordinates(2,4),
                        new char[][]{
                                {'P', 'M', 'B', 'J', 'R', 'J'},
                                {'A', 'K', 'A', 'O', 'L', 'F'},
                                {'N', 'O', 'L', 'M', 'O', 'X'},
                                {'I', 'O', 'E', 'K', 'I', 'M'},
                                {'C', 'L', 'T', 'S', 'I', 'L'},
                                {'G', 'R', 'E', 'E', 'N', 'A'}
                }}
        };
    }

    @Test(dataProvider = "nonNullTestProvider")
    public void nonNullTest(Set<String> possibleWords, Coordinates start, Coordinates end, char[][] grid) {
        Optional<Pair<Coordinates, Coordinates>> resultingOptional =
                WordFinder.findWordCoordinates(grid, possibleWords);

        assertTrue(resultingOptional.isPresent());

        Pair<Coordinates, Coordinates> resultPair = resultingOptional.get();

        assertEquals(resultPair.getValue0(), start);
        assertEquals(resultPair.getValue1(), end);
    }
}
