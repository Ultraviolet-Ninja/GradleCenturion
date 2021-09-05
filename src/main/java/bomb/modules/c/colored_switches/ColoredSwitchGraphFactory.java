package bomb.modules.c.colored_switches;

import bomb.tools.filter.Regex;
import com.opencsv.exceptions.CsvValidationException;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ColoredSwitchGraphFactory {
    private static final byte OUTGOING_STATE = 1, COLOR_CONDITIONS = 2, SWITCH_TO_FLIP = 3;

    private final List<ColoredSwitchNode> nodeList;

    public ColoredSwitchGraphFactory() throws IOException, CsvValidationException {
        nodeList = new ArrayList<>();
        Scanner csvReader = createReader();
        Regex connectionFinder = new Regex("\\[(\\d{1,2})\\((\\d{1,3})\\)([1-5])\\]");

        String[] nextRecord;
        while (csvReader.hasNextLine()) {
            nextRecord = csvReader.nextLine().split(",");
            nodeList.add(buildNode(nextRecord, connectionFinder));
        }
    }

    private Scanner createReader() throws FileNotFoundException {

        return new Scanner(new File(String.valueOf(getClass().getResource("graph.csv"))));
    }

    private ColoredSwitchNode buildNode(String[] record, Regex connectionFinder) {
        ColoredSwitchNode node = new ColoredSwitchNode(Byte.parseByte(record[0]));

        for (int i = 1; i < record.length; i++) {
            connectionFinder.loadText(record[i]);
            connectionFinder.hasMatch();

            byte outgoingConnection = Byte.parseByte(connectionFinder.captureGroup(OUTGOING_STATE));
            ColoredSwitchProperty[] colorConditions = createConditions(connectionFinder.captureGroup(COLOR_CONDITIONS));
            byte switchToFlip = Byte.parseByte(connectionFinder.captureGroup(SWITCH_TO_FLIP));

            node.addConnection(outgoingConnection, colorConditions, switchToFlip);
        }

        return node;
    }

    private ColoredSwitchProperty[] createConditions(String ordinals) {
        ColoredSwitchProperty[] output = new ColoredSwitchProperty[ordinals.length()];
        int i = 0;
        for (String ordinal : ordinals.split("")) {
            output[i++] = ColoredSwitchProperty.getByIndex(Integer.parseInt(ordinal));
        }
        return output;
    }

    public Graph<ColoredSwitchNode, DefaultEdge> constructGraph() {
        Graph<ColoredSwitchNode, DefaultEdge> output = new SimpleDirectedGraph<>(DefaultEdge.class);
        int i = 0;

        while (i < nodeList.size()) {
            output.addVertex(nodeList.get(i));
            i++;
        }
        i = 0;

        while (i < nodeList.size()) {
            for (byte connection : nodeList.get(i).getOutgoingConnections()) {
                output.addEdge(nodeList.get(i), nodeList.get(connection + 1));
            }
            i++;
        }

        return output;
    }
}
