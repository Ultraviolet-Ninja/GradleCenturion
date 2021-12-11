package bomb.modules.dh.hexamaze_redesign;

import bomb.Widget;
import bomb.components.hex.HexTile;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.maze_finding.MazeSearch;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.pathfinding.ExitChecker;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.pathfinding.MazeRunner;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexagonalPlane;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.Maze;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.queue.BufferedQueue;
import javafx.scene.paint.Color;
import org.javatuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bomb.components.hex.HexTile.DEFAULT_BACKGROUND_COLOR;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexagonalPlane.convertFromList;
import static java.util.stream.Collectors.toList;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.CYAN;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.MAGENTA;
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
        COLOR_MAP.put(MAGENTA, 5);
    }

    public static String solve(List<HexTile> tileList) throws IllegalArgumentException {
        Maze maze = new Maze();
        List<HexNode> nodeList = tileList.stream()
                .map(HexTile::getInternalNode)
                .collect(toList());
        Grid original = new Grid(new HexagonalPlane(nodeList));

        Grid found = MazeSearch.search(maze, original);
        if (found == null)
            throw new IllegalArgumentException("Could not find maze from given shapes");

        copyPegLocation(original, found);
        Pair<String, List<Coordinates>> exitInfo = ExitChecker.findPossibleExits(found);
        if (exitInfo == null)
            return "";

        List<Coordinates> resultingPath = MazeRunner.runMaze(found, exitInfo.getValue1());
        fillHexTiles(tileList, resultingPath);
        return exitInfo.getValue0();
    }

    private static void copyPegLocation(Grid original, Grid found) {
        List<HexNode> originalList = original.getHexagon().asList();
        List<HexNode> foundList = found.getHexagon().asList();
        int size = originalList.size();

        for (int i = 0; i < size; i++) {
            int currentNodeColorValue = originalList.get(i).getColor();
            if (currentNodeColorValue != -1) {
                foundList.get(i).setColor(currentNodeColorValue);
                return;
            }
        }
    }

    private static void fillHexTiles(List<HexTile> tileList, List<Coordinates> coordinatesList) {
        for (HexTile hexTile : tileList)
            hexTile.setBackgroundFill(DEFAULT_BACKGROUND_COLOR);

        BufferedQueue<BufferedQueue<HexTile>> tileQueues = convertFromList(tileList);

        coordinatesList.stream()
                .map(c -> tileQueues.get(c.getX()).get(c.getY()))
                .forEach(System.out::println);
    }
}
