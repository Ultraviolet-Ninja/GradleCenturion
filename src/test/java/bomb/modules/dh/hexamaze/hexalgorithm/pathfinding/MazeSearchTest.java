package bomb.modules.dh.hexamaze.hexalgorithm.pathfinding;

import bomb.modules.dh.hexamaze.hexalgorithm.factory.MazeFactory;
import bomb.modules.dh.hexamaze.hexalgorithm.maze_finding.MazeSearch;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexagonalPlane;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.Maze;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class MazeSearchTest {
    private Maze maze;

    @BeforeClass
    public void setUp() {
        maze = new Maze();
    }

    @Test
    public void nullTest() {
        Grid nullState = hexagonFromLine(
                "n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n"
        );
        Optional<Grid> emptyOptional = MazeSearch.search(maze, nullState);
        assertTrue(emptyOptional.isEmpty());
    }

    @DataProvider
    public Object[][] comparatorTestProvider() {
        return new Object[][]{
                {"n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,c,n,n,n,n"},
                {"n,n,n,rt,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n"}
        };
    }

    @Test(dataProvider = "comparatorTestProvider")
    public void comparatorTest(String line) {
        assertNotNull(MazeSearch.search(maze, hexagonFromLine(line)));
    }

    public static Grid hexagonFromLine(String line) {
        List<HexNode> list = new ArrayList<>();
        for (String shape : line.split(","))
            list.add(new HexNode(MazeFactory.decodeShape(shape), EnumSet.noneOf(HexNode.HexWall.class)));
        return new Grid(new HexagonalPlane(list));
    }
}
