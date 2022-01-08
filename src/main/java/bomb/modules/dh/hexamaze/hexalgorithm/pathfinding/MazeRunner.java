package bomb.modules.dh.hexamaze.hexalgorithm.pathfinding;

import bomb.modules.dh.hexamaze.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexWall;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.queue.BufferedQueue;
import org.jetbrains.annotations.NotNull;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.Comparator;
import java.util.List;

import static bomb.modules.dh.hexamaze.hexalgorithm.storage.AbstractHexagon.calculateColumnLengthStream;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.Grid.GRID_SIDE_LENGTH;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexWall.BOTTOM;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexWall.BOTTOM_RIGHT;
import static bomb.modules.dh.hexamaze.hexalgorithm.storage.HexNode.HexWall.TOP_RIGHT;

public class MazeRunner {
    //Movement Vectors
    private static final Coordinates
            MOVE_DOWN = new Coordinates(0, 1),
            MOVE_RIGHT = new Coordinates(1, 0),
            LEFT_SIDE_MOVE_DOWN_RIGHT = new Coordinates(1, 1),
            RIGHT_SIDE_MOVE_TOP_RIGHT = new Coordinates(1, -1);

    public static List<Coordinates> runMaze(@NotNull Grid grid, @NotNull List<Coordinates> possibleExits)
            throws IllegalArgumentException {
        Coordinates startingLocation = findStartingLocation(grid);
        Graph<Coordinates, DefaultEdge> mappedGraph = convertGridToGraph(grid);
        DijkstraShortestPath<Coordinates, DefaultEdge> dijkstraAlgorithm =
                new DijkstraShortestPath<>(mappedGraph);

        return possibleExits.stream()
                .map(exit -> dijkstraAlgorithm.getPath(startingLocation, exit))
                .map(GraphPath::getVertexList)
                .min(Comparator.comparingInt(List::size))
                .orElseThrow(IllegalArgumentException::new);
    }

    private static Graph<Coordinates, DefaultEdge> convertGridToGraph(Grid grid) {
        Graph<Coordinates, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        int[] columnLengths = calculateColumnLengthStream(GRID_SIDE_LENGTH);
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
        boolean notHalfWay = location.x() < grid.getHexagon().getSideLength() - 1;
        mapSingleNode(grid, graph, TOP_RIGHT, location,
                location.add(notHalfWay ? MOVE_RIGHT : RIGHT_SIDE_MOVE_TOP_RIGHT));
        mapSingleNode(grid, graph, BOTTOM_RIGHT, location, location.add(notHalfWay ?
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

    private static Coordinates findStartingLocation(Grid grid) {
        BufferedQueue<BufferedQueue<HexNode>> gridQueues = grid.getHexagon().getBufferedQueues();
        int size = gridQueues.size();
        for (int x = 0; x < size; x++) {
            BufferedQueue<HexNode> column = gridQueues.get(x);
            for (int y = 0; y < column.size(); y++) {
                HexNode currentNode = column.get(y);
                if (currentNode.getColor() != -1)
                    return new Coordinates(x, y);
            }
        }
        throw new RuntimeException("Failed to find start position");
    }
}
