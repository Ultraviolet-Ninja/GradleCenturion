package bomb.tools.data.structures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapStackTest {
    private MapStack<Integer, String> stack;

    private final Integer[] integers = new Integer[]{1, 2, 4, 41, 684, 50324, 124, 8345};
    private final String[] strings = {"Check check", "Again", "Thrice Cream", "The Forth",
            "The Fifth", "Incomplete", "Lucky No.", "Sideways Infinity"};

    @BeforeEach
    void setUp(){
        stack = new MapStack<>();
    }

    @Test
    void testAccessKeys(){
        floodFill(stack);
        IntStream.range(0, integers.length).mapToObj(i -> stack.getKey(i)).forEach(Assertions::assertNotNull);
        assertNull(stack.getKey(integers.length));
    }

    @Test
    void testAccessValues(){
        floodFill(stack);
        Arrays.stream(integers)
                .map(integer -> stack.getValue(integer))
                .forEach(Assertions::assertNotNull);

        Arrays.stream(integers)
                .map(integer -> stack.getValue((integer + 100)))
                .forEach(Assertions::assertNull);
    }

    @Test
    void testCopies(){
        assertTrue(stack.push(1, "Check check"));
        assertFalse(stack.push(1, "Again"));
    }

    @Test
    void testPopOff(){
        floodFill(stack);
        for (int i = integers.length - 1; i > 0; i--){
            AbstractMap.SimpleEntry<Integer, String> temp = new AbstractMap.SimpleEntry<>(integers[i], strings[i]);
            assertEquals(temp, stack.pop());
        }
    }

    @Test
    void testReplacement(){
        assertTrue(stack.push(integers[0], strings[0]));
        IntStream.range(1, strings.length)
                .forEach(i -> {
                    assertFalse(stack.push(integers[0], strings[i]));
                    assertEquals(stack.setTopValue(strings[i]), strings[i - 1]);
                });
    }

    private void floodFill(MapStack<Integer, String> stack){
        IntStream.range(0, strings.length)
                .forEach(i -> stack.push(integers[i], strings[i]));
    }
}
