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

    static List<ColoredSwitchNode> createFromFile() throws IllegalStateException {
        List<ColoredSwitchNode> output = new ArrayList<>(32);
        Regex connectionRegex = new Regex("\\[(\\d{1,2})\\((\\d{1,3})\\)([1-5])]");
        InputStream in = ColoredSwitchGraphFactory.class.getResourceAsStream(FILENAME);

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(in))) {
            csvReader.forEach(record -> output.add(buildNode(record, connectionRegex)));
            return output;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    static @NotNull Graph<ColoredSwitchNode, DefaultEdge> makeGraphFromSwitchColors(
            @NotNull SwitchColor[] startingColors) throws IllegalStateException {
        return buildGraph(createFromFile(), startingColors);
    }

    private static Graph<ColoredSwitchNode, DefaultEdge> buildGraph(List<ColoredSwitchNode> nodeList,
                                                                    SwitchColor[] startingSwitchColors) {
        Graph<ColoredSwitchNode, DefaultEdge> createdGraph = new SimpleDirectedGraph<>(DefaultEdge.class);

        nodeList.forEach(createdGraph::addVertex);

        for (ColoredSwitchNode node : nodeList) {
            for (byte connection : node.getOutgoingConnections()) {
                if (isTraversableEdge(startingSwitchColors, node, nodeList.get(connection))) {
                    createdGraph.addEdge(node, nodeList.get(connection));
                }
            }
        }

        return createdGraph;
    }

    private static boolean isTraversableEdge(SwitchColor[] startingColors, ColoredSwitchNode startNode,
                                             ColoredSwitchNode outgoingNode) {
        var edgeData = startNode.getEdgeData(outgoingNode.getState());
        SwitchColor switchColor = startingColors[edgeData.getValue1() - 1];

        return canFollowPath(edgeData.getValue0(), switchColor);
    }

    private static ColoredSwitchNode buildNode(String[] record, Regex connectionRegex) {
        ColoredSwitchNode node = new ColoredSwitchNode(Byte.parseByte(record[0]));

        for (int i = 1; i < record.length; i++) {
            if (record[i].isEmpty()) return node;
            connectionRegex.loadText(record[i]);
            connectionRegex.hasMatch();

            byte outgoingConnection = Byte.parseByte(connectionRegex.captureGroup(OUTGOING_STATE));
            EnumSet<SwitchColor> edgeColors = createConditions(connectionRegex.captureGroup(COLOR_CONDITIONS));
            byte switchToFlip = Byte.parseByte(connectionRegex.captureGroup(SWITCH_TO_FLIP));

            node.addConnection(outgoingConnection, edgeColors, switchToFlip);
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
