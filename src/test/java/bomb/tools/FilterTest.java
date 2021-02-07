package bomb.tools;

import org.junit.jupiter.api.Test;

import static bomb.tools.Mechanics.lowercaseRegex;
import static bomb.tools.Mechanics.numberRegex;
import static bomb.tools.Mechanics.ultimateFilter;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterTest {
    @Test
    void letterTest(){
        assertEquals("u", ultimateFilter("53212323u6434123", lowercaseRegex));
        assertEquals("thisisasentence",
                ultimateFilter("12T4h65is5 %i34s2 a s(5en34t6e4nce.", lowercaseRegex));
        assertEquals("é", ultimateFilter("42^&35é", lowercaseRegex));
    }

    @Test
    void numberTest(){
        assertEquals("", ultimateFilter("asjdhwaushaw", numberRegex));
        assertEquals("5", ultimateFilter("Find the5 number", numberRegex));
    }
}
