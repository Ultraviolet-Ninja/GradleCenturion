package bomb.modules.dh.hexamaze.hexalgorithm.maze_finding;

import bomb.modules.dh.hexamaze.hexalgorithm.pathfinding.ExitChecker;
import bomb.modules.dh.hexamaze.hexalgorithm.pathfinding.MazeRunner;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.Maze;
import bomb.tools.Coordinates;
import org.javatuples.Pair;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static bomb.modules.dh.hexamaze.hexalgorithm.pathfinding.ExitCheckerTest.setPegLocations;
import static bomb.modules.dh.hexamaze.hexalgorithm.pathfinding.MazeSearchTest.hexagonFromLine;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

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

        Optional<Grid> optional = MazeSearch.search(maze, grid);

        assertTrue(optional.isPresent());

        grid = optional.get();

        setPegLocations(grid, locations);

        Optional<Pair<String, List<Coordinates>>> pairOptional = ExitChecker.findPossibleExits(grid);
        assertTrue(pairOptional.isPresent());

        Pair<String, List<Coordinates>> pair = pairOptional.get();
        assertNotNull(MazeRunner.runMaze(grid, pair.getValue1()));
    }
}
