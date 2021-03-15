package bomb.modules.dh;

import bomb.modules.dh.hexamaze.hexalgorithm.Hex;
import bomb.modules.dh.hexamaze.hexalgorithm.HexComparator;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import bomb.modules.dh.hexamaze.hexalgorithm.ThreadedHexComparator;
import bomb.tools.data.structures.FixedArrayQueue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class HexComparatorTest {
    private static final int NO_ROTATIONS = 0;
    private static final String PATH = System.getProperty("user.dir") +
            "/src/test/resources/bomb/modules/dh/";
    private static final String FAILED_AT = "Failed at test ";

    private static Maze fullMaze;

    @BeforeAll
    static void initializeMaze(){
        try {
            fullMaze = new Maze();
        } catch (IOException e){
            fail(e);
        }
    }

    @Test
    void matchTest(){
        String csvLine;
        int testNumber = 1;
        try {
            BufferedReader csvReader =
                    new BufferedReader(new FileReader(PATH + "existentHexGrids.csv"));
            csvReader.readLine();
            csvLine = csvReader.readLine().replace("\n", "");
            while(csvLine != null){
//                HexGrid[] outputs = getOutputHexGrids(getNextHexagon(new StringTokenizer(csvLine, ",")));
//                assertTrue(hexagonsMatch(outputs[0], outputs[1]), FAILED_AT + testNumber++);
                assertNotNull(HexComparator.evaluate(fullMaze, getNextHexagon(new StringTokenizer(csvLine, ","))),
                        FAILED_AT + testNumber++);
                csvLine = csvReader.readLine();
            }
            csvReader.close();
        } catch (IOException e) {
            fail(e);
        }
    }

    @Test
    void nullTest(){
        String csvLine;
        int testNumber = 1;
        try {
            BufferedReader csvReader =
                    new BufferedReader(new FileReader(PATH + "nonExistentHexGrids.csv"));
            csvReader.readLine();
            csvLine = csvReader.readLine().replace("\n", "");
            while (csvLine != null){
//                HexGrid[] outputs = getOutputHexGrids(getNextHexagon(new StringTokenizer(csvLine, ",")));
                assertNull(HexComparator.evaluate(fullMaze, getNextHexagon(new StringTokenizer(csvLine, ","))),
                        FAILED_AT + testNumber++);
//                assertNull(outputs[1], FAILED_AT + testNumber);
                csvLine = csvReader.readLine();
            }
            csvReader.close();
        } catch (IOException e){
            fail(e);
        }
    }

    private HexGrid[] getOutputHexGrids(HexGrid inputGrid){
        return new HexGrid[]{HexComparator.evaluate(fullMaze, inputGrid),
                ThreadedHexComparator.evaluate(fullMaze, inputGrid)};
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
