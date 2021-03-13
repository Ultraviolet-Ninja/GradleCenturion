package bomb.modules.dh;

import bomb.modules.dh.hexamaze.hexalgorithm.Hex;
import bomb.modules.dh.hexamaze.hexalgorithm.HexComparator;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import bomb.modules.dh.hexamaze.hexalgorithm.ThreadedHexComparator;
import bomb.tools.data.structures.FixedArrayQueue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class HexComparatorTest {
    private static final int NO_ROTATIONS = 0;
    private static final String PATH = System.getProperty("user.dir") +
            "\\src\\test\\resources\\bomb\\modules\\dh\\";

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
        if (!runTests) fail("Full Maze not initialized. Tests cannot proceed");
    }

//    @Test
//    @Order(1)
    void matchTest(){
        String csvLine;
        int testNumber = 1;
        try {
            BufferedReader csvReader =
                    new BufferedReader(new FileReader(PATH + "testHexGrids.csv"));
            csvReader.readLine();
            csvLine = csvReader.readLine();
            while(csvLine != null){
                System.out.println("Test number: " + testNumber++);
                HexGrid inputGrid = getNextHexagon(new StringTokenizer(csvLine, ","));
                HexGrid linearOutput = HexComparator.evaluate(fullMaze, inputGrid);
                HexGrid threadedOutput = ThreadedHexComparator.evaluate(fullMaze, inputGrid);
                assertTrue(hexagonsMatch(linearOutput, threadedOutput));
                csvLine = csvReader.readLine();
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    private HexGrid getNextHexagon(StringTokenizer shapeLine){
        ArrayList<Hex.HexNode> inputNodes = new ArrayList<>();
        while (shapeLine.hasMoreElements())
            inputNodes.add(new Hex.HexNode(Hex.decodeShape(shapeLine.nextToken()), null));
        return new HexGrid(new Hex(inputNodes), NO_ROTATIONS);
    }

    private boolean hexagonsMatch(HexGrid linear, HexGrid threaded){
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> linearQueue = linear.exportTo2DQueue();
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> threadedQueue = threaded.exportTo2DQueue();
        for (int x = 0; x < linearQueue.cap(); x++){
            for (int y = 0; y < linearQueue.get(x).cap(); y++){
                if (!linearQueue.get(x).get(y).equals(threadedQueue.get(x).get(y)))
                    return false;
            }
        }
        return true;
    }
}
