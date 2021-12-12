package bomb.modules.dh.hexamaze.hexalgorithm.maze_finding;

import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure;
import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class MazeFinderTest {
    @BeforeClass
    public void setup() {
        Maze maze = new Maze();
        HexHashLibrary.initialize(maze, (2 * HexGrid.STANDARD_SIDE_LENGTH) - 1);
    }

    @Test(enabled = false)
    public void nullTest() {
        HexGrid nullState = hexagonFromLine(
                "n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n"
        );
        assertNull(HexHashLibrary.find(nullState));
    }

    @DataProvider
    public Object[][] comparatorTestProvider() {
        return new Object[][]{
                {"n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,c,n,n,n,n"},
                {"n,n,n,rt,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n"}
        };
    }

    @Test(enabled = false, dataProvider = "comparatorTestProvider")
    public void comparatorTest(String line) {
        assertNotNull(HexHashLibrary.find(hexagonFromLine(line)));
    }

    private static HexGrid hexagonFromLine(String line) {
        ArrayList<HexagonDataStructure.HexNode> list = new ArrayList<>();
        for (String shape : line.split(","))
            list.add(new HexagonDataStructure.HexNode(HexagonDataStructure.decodeShape(shape), null));
        return new HexGrid(new HexagonDataStructure(list));
    }
}