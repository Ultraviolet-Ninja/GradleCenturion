package bomb.modules.dh.hexamaze;

import bomb.Widget;
import bomb.components.hex.HexTile;
import bomb.modules.dh.hexamaze.hexalgorithm.maze_finding.MazeSearch;
import bomb.modules.dh.hexamaze.hexalgorithm.pathfinding.ExitChecker;
import bomb.modules.dh.hexamaze.hexalgorithm.pathfinding.MazeRunner;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexagonalPlane;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.Maze;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.queue.BufferedQueue;
import javafx.scene.paint.Color;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bomb.components.hex.HexTile.DEFAULT_BACKGROUND_COLOR;
import static java.util.stream.Collectors.toList;
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

    public static String solve(@NotNull List<HexTile> tileList) throws IllegalArgumentException {
        Maze maze = new Maze();
        List<HexNode> nodeList = tileList.stream()
                .map(HexTile::getInternalNode)
                .collect(toList());
        Grid original = new Grid(new HexagonalPlane(nodeList));

        Grid found = MazeSearch.search(maze, original);
        if (found == null)
            throw new IllegalArgumentException("Could not find maze from given shapes");

        int colorValue = copyPegLocation(original, found);
        setHexTileWalls(tileList, found);
        Pair<String, List<Coordinates>> exitInfo = ExitChecker.findPossibleExits(found);
        if (exitInfo == null)
            return "";

        List<Coordinates> resultingPath = MazeRunner.runMaze(found, exitInfo.getValue1());
        fillHexTiles(tileList, resultingPath, colorValue);
        return exitInfo.getValue0();
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

    private static void setHexTileWalls(List<HexTile> tileList, Grid found) {
        List<HexNode> foundNodes = found.getHexagon().asList();

        int size = foundNodes.size();
        for (int i = 0; i < size; i++) {
            tileList.get(i).setInternalNode(foundNodes.get(i));
        }
    }

    private static void fillHexTiles(List<HexTile> tileList, List<Coordinates> coordinatesList, int colorValue) {
        Color color = RED;

        for (Map.Entry<Color, Integer> entry : COLOR_MAP.entrySet()) {
            if (entry.getValue() == colorValue)
                color = entry.getKey();
        }

        for (HexTile hexTile : tileList)
            hexTile.setBackgroundFill(DEFAULT_BACKGROUND_COLOR);

        BufferedQueue<BufferedQueue<HexTile>> tileQueues = HexagonalPlane.convertFromList(tileList);

        Color finalColor = color;
        coordinatesList.stream()
                .map(c -> tileQueues.get(c.x()).get(c.y()))
                .forEach(tile -> tile.setBackgroundFill(finalColor));
    }
}
