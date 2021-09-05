package bomb.modules.c.colored_switches;

import bomb.modules.s.switches.Switches;
import com.opencsv.exceptions.CsvValidationException;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.util.List;

public class ColoredSwitches extends Switches {
    private static final Graph<ColoredSwitchNode, DefaultEdge> INTERNAL_GRAPH;

    static {
        ColoredSwitchGraphFactory factory;
        try {
            factory = new ColoredSwitchGraphFactory();
            INTERNAL_GRAPH = factory.constructGraph();
        } catch (CsvValidationException | IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public static List<String> producePreemptiveMoveList(byte startingState) {
        return null;
    }

    public static List<String> produceFinalMoveList(List<ColoredSwitchProperty> startingColors, byte desiredState) {
        return null;
    }
}
