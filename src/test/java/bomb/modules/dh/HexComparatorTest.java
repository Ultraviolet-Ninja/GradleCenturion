package bomb.modules.dh;

import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class HexComparatorTest {
    private static Maze fullMaze;
    private static boolean runTests = true;

    @BeforeAll
    static void initializeMaze(){
        try {
            fullMaze = new Maze();
        } catch (IOException e){
            runTests = false;
        }
    }

    @Test
    @Order(0)
    void stop(){
        if (!runTests)
            fail("Full Maze not initialized. Tests cannot proceed");
    }


}
