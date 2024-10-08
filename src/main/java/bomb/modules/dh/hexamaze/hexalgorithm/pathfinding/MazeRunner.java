package bomb.modules.dh.hexamaze.hexalgorithm.pathfinding;

import bomb.modules.dh.hexamaze.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexWall;
import bomb.tools.Coordinates;
import org.jetbrains.annotations.NotNull;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.Comparator;
import java.util.List;

import static bomb.modules.dh.hexamaze.hexalgorithm.storage.AbstractHexagon.calculateColumnLengthArray;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.Grid.GRID_SIDE_LENGTH;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexWall.BOTTOM;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexWall.BOTTOM_RIGHT;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexWall.TOP_RIGHT;

public final class MazeRunner {
    //Movement Vectors
    private static final Coordinates MOVE_DOWN, MOVE_RIGHT,
            LEFT_SIDE_MOVE_DOWN_RIGHT, RIGHT_SIDE_MOVE_TOP_RIGHT;

    /**
     * Runs the maze through Dijkstra's algorithm for every exit that's possible
     *
     * @param grid The grid with all shape and wall information
     * @param possibleExits The list of coordinates for all possible exits
     * @return The coordinate list of the shortest path from the player location to the best possible exit
     * @throws IllegalStateException If there is no possible exit, which theoretically shouldn't happen
     */
    public static @NotNull List<Coordinates> runMaze(@NotNull Grid grid, @NotNull List<Coordinates> possibleExits)
            throws IllegalStateException {
        var startingLocation = grid.getStartingLocation()
                .orElseThrow(() -> new IllegalArgumentException("Failed to find start position"));

        Graph<Coordinates, DefaultEdge> mappedGraph = convertGridToGraph(grid);
        DijkstraShortestPath<Coordinates, DefaultEdge> dijkstraAlgorithm =
                new DijkstraShortestPath<>(mappedGraph);

        return possibleExits.stream()
                .map(exit -> dijkstraAlgorithm.getPath(startingLocation, exit))
                .map(GraphPath::getVertexList)
                .min(Comparator.comparingInt(List::size))
                .orElseThrow(() -> new IllegalStateException("No exits were found for the given configuration and possible exits."));
    }

    private static Graph<Coordinates, DefaultEdge> convertGridToGraph(Grid grid) {
        Graph<Coordinates, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        int[] columnLengths = calculateColumnLengthArray(GRID_SIDE_LENGTH);
        int x = 0;

        for (int columnLength : columnLengths) {
            for (int y = 0; y < columnLength; y++) {
                mapAdjacentNodes(grid, graph, new Coordinates(x, y));
            }
            x++;
        }
        return graph;
    }

    private static void mapAdjacentNodes(Grid grid, Graph<Coordinates, DefaultEdge> graph, Coordinates location) {
        boolean isOnHexagonLeftSide = location.x() < grid.getHexagon().getSideLength() - 1;

        mapSingleNode(grid, graph, TOP_RIGHT, location,
                location.add(isOnHexagonLeftSide ? MOVE_RIGHT : RIGHT_SIDE_MOVE_TOP_RIGHT));

        mapSingleNode(grid, graph, BOTTOM_RIGHT, location, location.add(isOnHexagonLeftSide ?
                LEFT_SIDE_MOVE_DOWN_RIGHT : MOVE_RIGHT));

        mapSingleNode(grid, graph, BOTTOM, location, location.add(MOVE_DOWN));
    }

    private static void mapSingleNode(Grid grid, Graph<Coordinates, DefaultEdge> graph,
                                      HexWall correspondingWall, Coordinates from, Coordinates to) {
        HexNode currentNode = grid.getAtCoordinates(from);
        HexNode checkExists = grid.getAtCoordinates(to);

        if (checkExists == null || currentNode.isPathBlocked(correspondingWall))
            return;
        graph.addVertex(from);
        graph.addVertex(to);
        graph.addEdge(from, to);
    }

    static {
        MOVE_DOWN = new Coordinates(0, 1);
        MOVE_RIGHT = new Coordinates(1, 0);
        LEFT_SIDE_MOVE_DOWN_RIGHT = new Coordinates(1, 1);
        RIGHT_SIDE_MOVE_TOP_RIGHT = new Coordinates(1, -1);
    }
}
