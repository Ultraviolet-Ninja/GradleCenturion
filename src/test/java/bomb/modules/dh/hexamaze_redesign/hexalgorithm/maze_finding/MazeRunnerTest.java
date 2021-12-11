package bomb.modules.dh.hexamaze_redesign.hexalgorithm.maze_finding;

import bomb.modules.dh.hexamaze_redesign.hexalgorithm.pathfinding.ExitChecker;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.pathfinding.MazeRunner;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.Maze;
import bomb.tools.Coordinates;
import org.javatuples.Pair;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.maze_finding.ExitCheckerTest.setPegLocations;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.pathfinding.MazeSearchTest.hexagonFromLine;
import static org.testng.Assert.assertNotNull;

@SuppressWarnings("ALL")
public class MazeRunnerTest {
    private Maze maze;
    private Grid grid;

    @BeforeMethod
    public void setUp() {
        maze = new Maze();
        grid = hexagonFromLine(
                "n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,c,n,n,n,n"
        );
    }

    @Test
    public void validPathTest() {
        Set<Integer> locations = new HashSet<>();
        locations.add(31);

        setPegLocations(grid, locations);

        grid = MazeSearch.search(maze, grid);

        setPegLocations(grid, locations);

        Pair<String, List<Coordinates>> pair = ExitChecker.findPossibleExits(grid);
        assertNotNull(MazeRunner.runMaze(grid, pair.getValue1()));
    }
}
