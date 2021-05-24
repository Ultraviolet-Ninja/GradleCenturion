package bomb.tools;

import org.junit.jupiter.api.Test;

import static bomb.tools.Mechanics.LOWERCASE_REGEX;
import static bomb.tools.Mechanics.NUMBER_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OldFilterTest {
    @Test
    void letterTest(){
        assertEquals("u", ultimateFilter("53212323u6434123", LOWERCASE_REGEX));
        assertEquals("thisisasentence",
                ultimateFilter("12T4h65is5 %i34s2 a s(5en34t6e4nce.", LOWERCASE_REGEX));
        assertEquals("é", ultimateFilter("42^&35é", LOWERCASE_REGEX));
    }

    @Test
    void numberTest(){
        assertEquals("", ultimateFilter("asjdhwaushaw", NUMBER_REGEX));
        assertEquals("5", ultimateFilter("Find the5 number", NUMBER_REGEX));
    }
}
