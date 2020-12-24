package tools;

import bomb.tools.data.structures.FixedArrayQueue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FixedArrayQueueTest {
    private FixedArrayQueue<String> testList;
    private final String[] testBuffer = {"", "Purple", "Lexicon", "Ho Chi Minh City", "Delta Echo November", "List"};

    @Test
    void fullListTest(){
        testList = new FixedArrayQueue<>(testBuffer.length);
        assertFalse(testList.add(testBuffer[0]));
        assertFalse(testList.add(testBuffer[1]));
        assertFalse(testList.add(testBuffer[2]));
        assertFalse(testList.add(testBuffer[3]));
        assertFalse(testList.add(testBuffer[4]));
        assertTrue(testList.add(testBuffer[5]));
    }

    @Test
    void partialListTest(){
        testList = new FixedArrayQueue<>(testBuffer.length - 2);
        assertFalse(testList.add(testBuffer[0]));
        assertFalse(testList.add(testBuffer[1]));
        assertFalse(testList.add(testBuffer[2]));
        assertTrue(testList.add(testBuffer[3]));
        assertTrue(testList.add(testBuffer[4]));
        assertTrue(testList.add(testBuffer[5]));
    }

    @Test
    void removeFromHead(){
        testList = new FixedArrayQueue<>(testBuffer.length);
        assertFalse(testList.add(testBuffer[0]));
        assertFalse(testList.add(testBuffer[1]));
        assertFalse(testList.add(testBuffer[2]));
        assertFalse(testList.add(testBuffer[3]));
        assertFalse(testList.add(testBuffer[4]));
        assertTrue(testList.add(testBuffer[5]));

        for (int i = 0; i < testList.cap(); i++){
            testList.removeFromHead(1);
            assertEquals(testList.cap() - i, testList.size());
        }
    }

}