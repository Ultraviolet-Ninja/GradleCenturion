
package bomb.modules.ab.alphabet;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OldAlphabetTest {
    private static final String[] FOUR_LETTER_INPUTS = {"XZQJ", "KOVB", "QJPS", "RINM", "YDXQ", "FGAR"},
            FOUR_LETTER_CORRECT = {"JQXZ", "OKBV", "PQJS", "IRNM", "QYDX", "ARGF"};

    @Test
    void fourLetterTest(){
        IntStream.range(0, FOUR_LETTER_CORRECT.length)
                .forEach(i -> assertEquals(FOUR_LETTER_CORRECT[i], Alphabet.order(FOUR_LETTER_INPUTS[i])));
    }

    @Test
    void theGreatBerate(){
        assertEquals("ZNYL", Alphabet.order("ZYNL"));
        assertEquals("YKQV", Alphabet.order("QYVK"));
        assertEquals("ACHZ", Alphabet.order("HZAC"));
    }
}