package bomb.modules.dh.hexamaze;

import bomb.Widget;
import bomb.abstractions.marker.PostLeftKey;
import bomb.annotation.DisplayComponent;
import bomb.modules.dh.hexamaze.hexalgorithm.maze_finding.MazeSearch;
import bomb.modules.dh.hexamaze.hexalgorithm.pathfinding.ExitChecker;
import bomb.modules.dh.hexamaze.hexalgorithm.pathfinding.MazeRunner;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexagonalPlane;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.Maze;
import bomb.tools.Coordinates;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.PlayerColor.NO_PLAYER;

@DisplayComponent(resource = "hexamaze.fxml", buttonLinkerName = "Hexamaze")
public final class Hexamaze extends Widget implements PostLeftKey {
    public static @NotNull Quartet<
            @NotNull Grid,
            @Nullable String,
            HexNode.@NotNull PlayerColor,
            @Nullable List<Coordinates>> solve(@NotNull List<HexNode> nodeList)
            throws IllegalArgumentException, IllegalStateException {
        Maze maze = new Maze();
        Grid original = new Grid(new HexagonalPlane(nodeList));

        Grid resultGridWithWalls = MazeSearch.search(maze, original)
                .orElseThrow(() -> new IllegalArgumentException("Could not find maze from given shapes"));

        var playerColor = copyPegLocation(original, resultGridWithWalls);
        Optional<Pair<String, List<Coordinates>>> exitInfoOptional = ExitChecker.findPossibleExits(resultGridWithWalls);
        //No peg color was given, so we only give the maze back as output
        if (exitInfoOptional.isEmpty())
            return new Quartet<>(resultGridWithWalls, null, NO_PLAYER, null);

        var exitInfoPair = exitInfoOptional.get();
        var resultingDirection = exitInfoPair.getValue0();
        var possibleExits = exitInfoPair.getValue1();
        var shortestPath = MazeRunner.runMaze(resultGridWithWalls, possibleExits);
        return new Quartet<>(
                resultGridWithWalls,
                resultingDirection,
                playerColor,
                shortestPath
        );
    }

    private static HexNode.PlayerColor copyPegLocation(Grid original, Grid resultGridWithWalls) {
        var originalList = original.getHexagon().asList();
        var foundList = resultGridWithWalls.getHexagon().asList();
        int size = originalList.size();

        for (int i = 0; i < size; i++) {
            var playerColor = originalList.get(i).getPlayerColor();
            if (playerColor != NO_PLAYER) {
                foundList.get(i).setPlayerColor(playerColor);
                return playerColor;
            }
        }
        return NO_PLAYER;
    }
}
