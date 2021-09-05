package bomb.modules.dh.hexamaze.hexalgorithm.pathfinding;

import bomb.modules.dh.hexamaze.hexalgorithm.AbstractHexagon;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.HexNodeProperties.HexWall;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure.HexNode;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.BufferedQueue;
import javafx.scene.paint.Color;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static bomb.modules.dh.hexamaze.hexalgorithm.HexNodeProperties.HexWall.Bottom;
import static bomb.modules.dh.hexamaze.hexalgorithm.HexNodeProperties.HexWall.BottomLeft;
import static bomb.modules.dh.hexamaze.hexalgorithm.HexNodeProperties.HexWall.BottomRight;
import static bomb.modules.dh.hexamaze.hexalgorithm.HexNodeProperties.HexWall.Top;
import static bomb.modules.dh.hexamaze.hexalgorithm.HexNodeProperties.HexWall.TopLeft;
import static bomb.modules.dh.hexamaze.hexalgorithm.HexNodeProperties.HexWall.TopRight;

public class MazeRunner {
    private static Color currentPegColor = null;
    private static Coordinates currentLocation = null;

    //Movement Vectors
    private static final Coordinates MOVE_DOWN = new Coordinates(0, 1), MOVE_RIGHT = new Coordinates(1, 0),
            LEFT_SIDE_MOVE_DOWN_RIGHT = new Coordinates(1, 1),
            RIGHT_SIDE_MOVE_TOP_RIGHT = new Coordinates(1, -1);

    /**
     * Don't let anyone instantiate this class
     */
    private MazeRunner() {
    }

    public static void getPegInformation(Color pegColor, int location, int gridSideLength) {
        currentPegColor = pegColor;
        startingPointCalculator(location, gridSideLength);
    }

    private static void startingPointCalculator(int location, int gridSideLength) {
        boolean found = false;
        int[] columnLengths = AbstractHexagon.calculateColumnLengths(gridSideLength);

        for (int k = 0; !found && k < columnLengths.length; k++) {
            if (location < columnLengths[k]) {
                found = true;
                currentLocation = new Coordinates(k, location);
            }
            location -= columnLengths[k];
        }
        if (!found) throw new IllegalStateException("This should be impossible");
    }

    public static List<Coordinates> runMaze(HexGrid grid) {
        if (currentPegColor == null || currentLocation == null) return null;

        List<Coordinates> possibleExits = getPossibleExits(grid, grid.getRing().findRelativeIndex(currentPegColor));

        Graph<Coordinates, DefaultEdge> mappedGraph = mapToGraph(grid);
        List<List<Coordinates>> options = new ArrayList<>();

        for (Coordinates exit : possibleExits) {
            DijkstraShortestPath<Coordinates, DefaultEdge> shortest =
                    new DijkstraShortestPath<>(mappedGraph);
            options.add(shortest.getPath(currentLocation, exit).getVertexList());
        }
        options.sort(Comparator.comparingInt(List::size));

        return options.get(0);
    }

    private static List<Coordinates> getPossibleExits(HexGrid grid, int sideToExit) {
        List<Coordinates> output = switch (sideToExit) {
            case 0 -> getTopLeftSide(grid);
            case 1 -> getTopRightSide(grid);
            case 2 -> getRightSide(grid);
            case 3 -> getBottomRightSide(grid);
            case 4 -> getBottomLeftSide(grid);
            default -> getLeftSide(grid);
        };
        return filterBlockedExits(grid, output, sideToExit);
    }

    private static List<Coordinates> getTopLeftSide(HexGrid grid) {
        ArrayList<Coordinates> output = new ArrayList<>();
        for (int i = 0; i < grid.sideLength(); i++) {
            output.add(new Coordinates(i, 0));
        }
        return output;
    }

    private static List<Coordinates> getTopRightSide(HexGrid grid) {
        ArrayList<Coordinates> output = new ArrayList<>();
        for (int i = grid.sideLength() - 1; i < grid.getSpan(); i++) {
            output.add(new Coordinates(i, 0));
        }
        return output;
    }

    private static List<Coordinates> getRightSide(HexGrid grid) {
        ArrayList<Coordinates> output = new ArrayList<>();
        BufferedQueue<BufferedQueue<HexNode>> internals = grid.exportTo2DQueue();
        int lastIndex = internals.cap() - 1;
        for (int i = 0; i < internals.get(lastIndex).cap(); i++) {
            output.add(new Coordinates(lastIndex, i));
        }
        return output;
    }

    private static List<Coordinates> getBottomRightSide(HexGrid grid) {
        ArrayList<Coordinates> output = new ArrayList<>();
        BufferedQueue<BufferedQueue<HexNode>> internals = grid.exportTo2DQueue();
        for (int i = grid.sideLength() - 1; i < grid.getSpan(); i++) {
            output.add(new Coordinates(i, internals.get(i).cap() - 1));
        }
        return output;
    }

    private static List<Coordinates> getBottomLeftSide(HexGrid grid) {
        ArrayList<Coordinates> output = new ArrayList<>();
        BufferedQueue<BufferedQueue<HexNode>> internals = grid.exportTo2DQueue();
        for (int i = 0; i < grid.sideLength(); i++) {
            output.add(new Coordinates(i, internals.get(i).cap() - 1));
        }
        return output;
    }

    private static List<Coordinates> getLeftSide(HexGrid grid) {
        ArrayList<Coordinates> output = new ArrayList<>();
        for (int i = 0; i < grid.sideLength(); i++) {
            output.add(new Coordinates(0, i));
        }
        return output;
    }

    private static List<Coordinates> filterBlockedExits(HexGrid grid, List<Coordinates> list, int sideToExit) {
        HexWall[] wallsToFind = switch (sideToExit) {
            case 0 -> new HexWall[]{TopLeft, Top};
            case 1 -> new HexWall[]{TopRight, Top};
            case 2 -> new HexWall[]{TopRight, BottomRight};
            case 3 -> new HexWall[]{BottomRight, Bottom};
            case 4 -> new HexWall[]{Bottom, BottomLeft};
            default -> new HexWall[]{TopLeft, BottomLeft};
        };
        return list.stream().filter(coords -> grid.retrieveNode(coords).isPathClear(wallsToFind[0].ordinal()) ||
                        grid.retrieveNode(coords).isPathClear(wallsToFind[1].ordinal()))
                .collect(Collectors.toList());
    }

    private static Graph<Coordinates, DefaultEdge> mapToGraph(HexGrid grid) {
        BufferedQueue<BufferedQueue<HexNode>> internals = grid.exportTo2DQueue();
        Graph<Coordinates, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        for (int x = 0; x < internals.cap(); x++) {
            for (int y = 0; y < internals.get(x).cap(); y++) {
                mapAdjacentNodes(grid, graph, new Coordinates(x, y));
            }
        }
        return graph;
    }

    private static void mapAdjacentNodes(HexGrid grid, Graph<Coordinates, DefaultEdge> graph, Coordinates location) {
        boolean notHalfWay = location.getX() < grid.sideLength() - 1;
        mapSingleNode(grid, graph, TopRight, location, location.immutableAdd(notHalfWay ?
                MOVE_RIGHT : RIGHT_SIDE_MOVE_TOP_RIGHT));
        mapSingleNode(grid, graph, BottomRight, location, location.immutableAdd(notHalfWay ?
                LEFT_SIDE_MOVE_DOWN_RIGHT : MOVE_RIGHT));
        mapSingleNode(grid, graph, Bottom, location, location.immutableAdd(MOVE_DOWN));
    }

    private static void mapSingleNode(HexGrid grid, Graph<Coordinates, DefaultEdge> graph,
                                      HexWall correspondingWall, Coordinates from, Coordinates to) {
        HexNode currentNode = grid.retrieveNode(from);
        HexNode checkExists = grid.retrieveNode(to);

        if (currentNode.isPathClear(correspondingWall.ordinal()) && checkExists != null) {
            graph.addVertex(from);
            graph.addVertex(to);
            graph.addEdge(from, to);
        }
    }
}
