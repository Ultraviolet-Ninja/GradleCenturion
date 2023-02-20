package bomb.modules.c.colored.switches;

import bomb.tools.filter.Regex;
import com.opencsv.CSVReader;
import org.jetbrains.annotations.NotNull;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static bomb.modules.c.colored.switches.ColoredSwitches.canFollowPath;

@SuppressWarnings("ConstantConditions")
public class ColoredSwitchGraphFactory {
    private static final byte OUTGOING_STATE = 1, COLOR_CONDITIONS = 2, SWITCH_TO_FLIP = 3;
    private static final String FILENAME = "graph.csv";

    public static @NotNull Graph<ColoredSwitchNode, DefaultEdge> makeGraph() throws IllegalStateException {
        return buildGraph(createFromFile());
    }

    private static List<ColoredSwitchNode> createFromFile() throws IllegalStateException {
        List<ColoredSwitchNode> output = new ArrayList<>(32);
        Regex connectionFinder = new Regex("\\[(\\d{1,2})\\((\\d{1,3})\\)([1-5])]");
        InputStream in = ColoredSwitchGraphFactory.class.getResourceAsStream(FILENAME);

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(in))) {
            csvReader.forEach(record -> output.add(buildNode(record, connectionFinder)));
            return output;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Graph<ColoredSwitchNode, DefaultEdge> buildGraph(List<ColoredSwitchNode> nodeList) {
        Graph<ColoredSwitchNode, DefaultEdge> output = new SimpleDirectedGraph<>(DefaultEdge.class);

        for (ColoredSwitchNode node : nodeList)
            output.addVertex(node);

        for (ColoredSwitchNode node : nodeList) {
            for (byte connection : node.getOutgoingConnections()) {
                output.addEdge(node, nodeList.get(connection));
            }
        }

        return output;
    }

    public static @NotNull Graph<ColoredSwitchNode, DefaultEdge> makeGraphFromSwitchColors(@NotNull SwitchColor[] startingColors)
            throws IllegalStateException {
        return buildGraph2(createFromFile(), startingColors);
    }

    private static Graph<ColoredSwitchNode, DefaultEdge> buildGraph2(List<ColoredSwitchNode> nodeList,
                                                                     SwitchColor[] startingColors) {
        Graph<ColoredSwitchNode, DefaultEdge> createdGraph = new SimpleDirectedGraph<>(DefaultEdge.class);

        nodeList.forEach(createdGraph::addVertex);

        for (ColoredSwitchNode node : nodeList) {
            for (byte connection : node.getOutgoingConnections()) {
                if (isPlausibleEdge(startingColors, node, connection)) {
                    createdGraph.addEdge(node, nodeList.get(connection));
                }
            }
        }

        return createdGraph;
    }

    private static boolean isPlausibleEdge(SwitchColor[] startingColors, ColoredSwitchNode node,
                                           int connectionValue) {
        SwitchColor switchColor = startingColors[connectionValue];
        var edges = node.getEdgeData((byte) connectionValue)
                .getValue0();

        return canFollowPath(edges, switchColor);
    }

    private static ColoredSwitchNode buildNode(String[] record, Regex connectionFinder) {
        ColoredSwitchNode node = new ColoredSwitchNode(Byte.parseByte(record[0]));

        for (int i = 1; i < record.length; i++) {
            if (record[i].isEmpty()) return node;
            connectionFinder.loadText(record[i]);
            connectionFinder.hasMatch();

            byte outgoingConnection = Byte.parseByte(connectionFinder.captureGroup(OUTGOING_STATE));
            EnumSet<SwitchColor> colorConditions = createConditions(connectionFinder.captureGroup(COLOR_CONDITIONS));
            byte switchToFlip = Byte.parseByte(connectionFinder.captureGroup(SWITCH_TO_FLIP));

            node.addConnection(outgoingConnection, colorConditions, switchToFlip);
        }

        return node;
    }

    private static EnumSet<SwitchColor> createConditions(String ordinals) {
        return EnumSet.copyOf(
                Arrays.stream(ordinals.split(""))
                .mapToInt(Integer::parseInt)
                .mapToObj(SwitchColor::getByIndex)
                .toList()
        );
    }
}
