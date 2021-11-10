package bomb.modules.c.colored_switches;

import bomb.tools.filter.Regex;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ColoredSwitchGraphFactory {
    private static final byte OUTGOING_STATE = 1, COLOR_CONDITIONS = 2, SWITCH_TO_FLIP = 3;
    private static final String FILENAME = "graph.csv";

    private final List<ColoredSwitchNode> nodeList;

    public ColoredSwitchGraphFactory() throws IOException, CsvValidationException {
        nodeList = new ArrayList<>();
        CSVReader csvReader = createReader();
        Regex connectionFinder = new Regex("\\[(\\d{1,2})\\((\\d{1,3})\\)([1-5])]");

        String[] nextRecord;
        while ((nextRecord = csvReader.readNext()) != null) {
            nodeList.add(buildNode(nextRecord, connectionFinder));
        }
        csvReader.close();
    }

    private CSVReader createReader() {
        InputStream in = ColoredSwitchGraphFactory.class.getResourceAsStream(FILENAME);
        Reader reader = new InputStreamReader(Objects.requireNonNull(in));
        return new CSVReader(reader);
    }

    private ColoredSwitchNode buildNode(String[] record, Regex connectionFinder) {
        ColoredSwitchNode node = new ColoredSwitchNode(Byte.parseByte(record[0]));

        for (int i = 1; i < record.length; i++) {
            if (record[i].isEmpty()) return node;
            connectionFinder.loadText(record[i]);
            connectionFinder.hasMatch();

            byte outgoingConnection = Byte.parseByte(connectionFinder.captureGroup(OUTGOING_STATE));
            SwitchColor[] colorConditions = createConditions(connectionFinder.captureGroup(COLOR_CONDITIONS));
            byte switchToFlip = Byte.parseByte(connectionFinder.captureGroup(SWITCH_TO_FLIP));

            node.addConnection(outgoingConnection, colorConditions, switchToFlip);
        }

        return node;
    }

    private SwitchColor[] createConditions(String ordinals) {
        SwitchColor[] output = new SwitchColor[ordinals.length()];
        int i = 0;
        for (String ordinal : ordinals.split("")) {
            output[i++] = SwitchColor.getByIndex(Integer.parseInt(ordinal));
        }
        return output;
    }

    public Graph<ColoredSwitchNode, DefaultEdge> constructGraph() {
        Graph<ColoredSwitchNode, DefaultEdge> output = new SimpleDirectedGraph<>(DefaultEdge.class);

        for (ColoredSwitchNode node : nodeList) {
            output.addVertex(node);
        }

        for (ColoredSwitchNode node : nodeList) {
            for (byte connection : node.getOutgoingConnections()) {
                output.addEdge(node, nodeList.get(connection));
            }
        }

        return output;
    }
}
