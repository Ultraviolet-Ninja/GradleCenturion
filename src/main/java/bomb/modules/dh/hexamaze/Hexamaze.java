package bomb.modules.dh.hexamaze;

import bomb.Widget;
import bomb.modules.dh.hexamaze.hexalgorithm.maze_finding.MazeSearch;
import bomb.modules.dh.hexamaze.hexalgorithm.pathfinding.ExitChecker;
import bomb.modules.dh.hexamaze.hexalgorithm.pathfinding.MazeRunner;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexagonalPlane;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.Maze;
import bomb.tools.Coordinates;
import javafx.scene.paint.Color;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.CYAN;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.PINK;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.YELLOW;

public class Hexamaze extends Widget {
    public static final Map<Color, Integer> COLOR_MAP = new HashMap<>();
    public static final Color PEG_COLOR = new Color(0.65, 0.65, 0.65, 1.0);

    static {
        COLOR_MAP.put(PEG_COLOR, -1);
        COLOR_MAP.put(RED, 0);
        COLOR_MAP.put(YELLOW, 1);
        COLOR_MAP.put(GREEN, 2);
        COLOR_MAP.put(CYAN, 3);
        COLOR_MAP.put(BLUE, 4);
        COLOR_MAP.put(PINK, 5);
    }

    public static @NotNull Quartet<
            @NotNull Grid,
            @Nullable String,
            @Nullable Integer,
            @Nullable List<Coordinates>>solve(@NotNull List<HexNode> nodeList) throws IllegalArgumentException {
        Maze maze = new Maze();
        Grid original = new Grid(new HexagonalPlane(nodeList));

        Grid found = MazeSearch.search(maze, original);
        if (found == null)
            throw new IllegalArgumentException("Could not find maze from given shapes");

        int colorValue = copyPegLocation(original, found);
        Pair<String, List<Coordinates>> exitInfo = ExitChecker.findPossibleExits(found);
        if (exitInfo == null)
            return new Quartet<>(found, null, null, null);

        return new Quartet<>(
                found,
                exitInfo.getValue0(),
                colorValue,
                MazeRunner.runMaze(found, exitInfo.getValue1())
        );
    }

    private static int copyPegLocation(Grid original, Grid found) {
        List<HexNode> originalList = original.getHexagon().asList();
        List<HexNode> foundList = found.getHexagon().asList();
        int size = originalList.size();

        for (int i = 0; i < size; i++) {
            int currentNodeColorValue = originalList.get(i).getColor();
            if (currentNodeColorValue != -1) {
                foundList.get(i).setColor(currentNodeColorValue);
                return currentNodeColorValue;
            }
        }
        return -1;
    }
}
