package bomb.modules.dh.hexamaze.hexalgorithm.pathfinding;

import bomb.modules.dh.hexamaze.hexalgorithm.maze_finding.MazeSearch;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.Maze;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.queue.BufferedQueue;
import org.javatuples.Pair;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static bomb.modules.dh.hexamaze.hexalgorithm.pathfinding.MazeSearchTest.hexagonFromLine;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class ExitCheckerTest {
    private static final int RED_PEG_VALUE = 0;

    private Grid grid;

    @BeforeMethod
    public void setUp() {
        grid = hexagonFromLine(
                "n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,c,n,n,n,n"
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Cannot have more than one peg on the board")
    public void exceptionTest() {
        Set<Integer> locations = new HashSet<>();
        locations.add(31);
        locations.add(32);

        setPegLocations(grid, locations);

        ExitChecker.findPossibleExits(grid);
    }

    @Test
    public void nullTest() {
        assertNull(ExitChecker.findPossibleExits(grid));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void validLocationTest() {
        Maze maze = new Maze();
        Set<Integer> locations = new HashSet<>();
        locations.add(31);

        setPegLocations(grid, locations);

        grid = MazeSearch.search(maze, grid);

        setPegLocations(grid, locations);

        Pair<String, List<Coordinates>> results = ExitChecker.findPossibleExits(grid);
        assertNotNull(results);
        assertEquals(results.getValue0(), "Top Left");
        assertEquals(results.getValue1().size(), 2);
    }

    public static void setPegLocations(Grid grid, Set<Integer> locations) {
        BufferedQueue<BufferedQueue<HexNode>> gridQueues = grid.getHexagon().getBufferedQueues();

        int locationCounter = 0;
        for (BufferedQueue<HexNode> column : gridQueues) {
            for (HexNode node : column) {
                if (locations.contains(locationCounter++))
                    node.setColor(RED_PEG_VALUE);
            }
        }
    }
}
