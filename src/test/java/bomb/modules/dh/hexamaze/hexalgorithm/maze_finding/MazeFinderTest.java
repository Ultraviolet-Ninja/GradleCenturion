package bomb.modules.dh.hexamaze.hexalgorithm.maze_finding;

import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure;
import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

public class MazeFinderTest {
    @BeforeClass
    public void setup(){
        try {
            Maze maze = new Maze();
            HexHashLibrary.initialize(maze, (2 * HexGrid.STANDARD_SIDE_LENGTH) - 1);
        } catch (IOException e){
            fail();
        }
    }

    @Test
    public void nullTest(){
        HexGrid nullState =
                hexagonFromLine("n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n");
        assertNull(HexHashLibrary.find(nullState));
    }

    @DataProvider
    public Object[][] comparatorProvider(){
        return new Object[][]{
                {"n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,c,n,n,n,n"},
                {"n,n,n,rt,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n"}
        };
    }

    @Test(dataProvider = "comparatorProvider")
    public void comparatorTest(String line){
        assertNotNull(HexHashLibrary.find(hexagonFromLine(line)));
    }

    private static HexGrid hexagonFromLine(String line){
        ArrayList<HexagonDataStructure.HexNode> list = new ArrayList<>();
        for (String shape : line.split(","))
            list.add(new HexagonDataStructure.HexNode(HexagonDataStructure.decodeShape(shape), null));
        return new HexGrid(new HexagonDataStructure(list));
    }
}