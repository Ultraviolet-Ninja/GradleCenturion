package bomb.tools.data.structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class FixedRingTest {
    private FixedRing<Integer> ring;

    private final Integer[] intArray = new Integer[]{1, 248, 20, 823, 127, 234, 495};

    @BeforeEach
    void setUp() {
        ring = new FixedRing<>(intArray.length);
    }

    @Test
    void addToRing(){
        for (int i = 0; i < intArray.length; i++){
            if (i < intArray.length - 1)
                assertTrue(ring.add(intArray[i]));
            else
                assertFalse(ring.add(intArray[i]));
        }

        assertThrows(BufferOverflowException.class, () ->{
            ring.add(1924);
        });
    }

    @Test
    void findIndex() {
        fillRing();
        IntStream.range(0, intArray.length)
                .forEach(i -> assertEquals(i, ring.findIndex(intArray[i])));

        assertEquals(-1, ring.findIndex(-149));
    }

    @Test
    void rotationTest(){
        fillRing();
        Arrays.stream(intArray)
                .forEach(integer -> {
                    assertEquals(integer, ring.getHeadData());
                    ring.rotateHeadClockwise();
                });

        for (int i = intArray.length - 1; i >= 0; i--){
            ring.rotateHeadCounter();
            assertEquals(intArray[i], ring.getHeadData());
        }
    }

    @Test
    void arrayListTest(){
        fillRing();
        ArrayList<Integer> ringList = ring.toArrayList(),
                toCompare = new ArrayList<>(Arrays.asList(intArray));

        IntStream.range(0, toCompare.size() - 1)
                .forEach(i -> assertEquals(toCompare.get(i), ringList.get(i)));
        ring.rotateHeadClockwise();
        ArrayList<Integer> ringList2 = ring.toArrayList();

        IntStream.range(0, toCompare.size() - 1)
                .forEach(i -> assertNotEquals(toCompare.get(i), ringList2.get(i)));
    }

    private void fillRing(){
        for (int singleNum : intArray)
            ring.add(singleNum);
    }
}

