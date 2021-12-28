package bomb.modules.wz.word_search;

import bomb.Widget;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertEquals;

public class WordSearchTest {
    private static final String SERIAL_CODE = "AT7ZE2";

    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Serial Code is required\nPlease check formatting on Widget page")
    public void validateSerialCodeExceptionTest() {
        WordSearch.findPossibleWords(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "The input does not contain 4 letters")
    public void invalidCharArrayLengthExceptionTest() {
        Widget.setSerialCode(SERIAL_CODE);

        WordSearch.findPossibleWords(new char[]{'R', 'S', 'E'});
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Input contains a non-letter character")
    public void invalidCharExceptionTest() {
        Widget.setSerialCode(SERIAL_CODE);

        WordSearch.findPossibleWords(new char[]{'R', 'S', 'E', '0'});
    }

    @DataProvider
    public Object[][] validInputTestProvider() {
        return new Object[][]{
                {new char[]{'R', 'S', 'E', 'O'}, Set.of("MATH", "KABOOM", "VICTOR", "WORK")},
                {new char[]{'r', 'p', 'a', 'h'}, Set.of("MATH", "SEE", "HELP", "FOUND")},
                {new char[]{'P', 'J', 'G', 'A'}, Set.of("INDIA", "MODULE", "LIST", "DELTA")},
                {new char[]{'F', 'F', 'L', 'K'}, Set.of("DELTA", "VICTOR", "SEVEN", "ROMEO")}
        };
    }

    @Test(dataProvider = "validInputTestProvider")
    public void validInputTest(char[] letters, Set<String> expectedPossibleWords) {
        Widget.setSerialCode(SERIAL_CODE);

        assertEquals(
                WordSearch.findPossibleWords(letters),
                expectedPossibleWords
        );
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
