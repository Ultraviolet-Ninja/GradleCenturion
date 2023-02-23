package bomb.modules.c.colored.switches;

import bomb.annotation.DisplayComponent;
import bomb.modules.s.switches.Switches;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static bomb.modules.c.colored.switches.ColoredSwitchGraphFactory.createFromFile;
import static bomb.modules.c.colored.switches.SwitchColor.NEUTRAL;
import static java.util.function.UnaryOperator.identity;

@DisplayComponent(resource = "colored_switches.fxml", buttonLinkerName = "Colored Switches")
public final class ColoredSwitches extends Switches {
    private static final Map<Byte, ColoredSwitchNode> NUMBER_TO_STATE_MAP;

    private static byte secondaryStartLocation = -1;

    static {
        NUMBER_TO_STATE_MAP = createFromFile()
                .stream()
                .collect(Collectors.toMap(ColoredSwitchNode::getState, identity()));
    }

    public static @NotNull List<String> producePreemptiveMoveList(byte startingState) throws IllegalArgumentException {
        validateByte(startingState);

        List<String> outputList = new ArrayList<>();

        secondaryStartLocation = makePreemptiveMove(startingState, outputList);
        secondaryStartLocation = makePreemptiveMove(secondaryStartLocation, outputList);
        secondaryStartLocation = makePreemptiveMove(secondaryStartLocation, outputList);

        return outputList;
    }

    private static byte makePreemptiveMove(byte currentState, List<String> outputList) throws IllegalStateException {
        ColoredSwitchNode currentNode = NUMBER_TO_STATE_MAP.get(currentState);

        Predicate<Byte> filterToBlackPath = connectionState -> {
            Pair<EnumSet<SwitchColor>, Byte> edgeData = currentNode.getEdgeData(connectionState);
            return isBlackPath(edgeData.getValue0());
        };

        Consumer<Byte> appendBlackPath = connectionState -> {
            Pair<EnumSet<SwitchColor>, Byte> edgeData = currentNode.getEdgeData(connectionState);
            outputList.add(String.valueOf(edgeData.getValue1()));
        };

        Optional<Byte> first = currentNode.getOutgoingConnections()
                .stream()
                .filter(filterToBlackPath)
                .findFirst();

        first.ifPresent(appendBlackPath);

        return first.orElseThrow(() -> new IllegalStateException("No black paths found"));
    }

    public static @NotNull List<String> produceFinalMoveList(@NotNull SwitchColor[] startingColors, byte desiredState)
            throws IllegalStateException, IllegalArgumentException {
        validateByte(desiredState);
        validateSwitchColors(startingColors);

        if (startingColors.length != BIT_LENGTH)
            throw new IllegalArgumentException("There should be 5 switches");
        if (!isFirstStepDone())
            throw new IllegalStateException("Must flip 3 switches before producing the final list");

        DijkstraShortestPath<ColoredSwitchNode, DefaultEdge> shortestPath = new DijkstraShortestPath<>(
                ColoredSwitchGraphFactory.makeGraphFromSwitchColors(startingColors)
        );

        ColoredSwitchNode startNode = NUMBER_TO_STATE_MAP.get(secondaryStartLocation);
        ColoredSwitchNode destination = NUMBER_TO_STATE_MAP.get(desiredState);

        List<ColoredSwitchNode> nodeList2 = shortestPath.getPath(startNode, destination)
                .getVertexList();

        return createSwitchToFlipList(nodeList2);
    }

    private static List<String> createSwitchToFlipList(List<ColoredSwitchNode> path) {
        return IntStream.range(0, path.size() - 1)
                .mapToObj(i -> path.get(i).getEdgeData(path.get(i + 1).getState()))
                .map(Pair::getValue1)
                .map(String::valueOf)
                .toList();
    }

    static boolean canFollowPath(EnumSet<SwitchColor> connectionConditions, SwitchColor switchColor) {
        if (isBlackPath(connectionConditions)) return true;

        return connectionConditions.contains(switchColor);
    }

    private static void validateSwitchColors(SwitchColor[] startingColors) {
        for (SwitchColor switchColor : startingColors) {
            if (switchColor == NEUTRAL)
                throw new IllegalArgumentException("All switches must have a color");
        }
    }

    private static boolean isBlackPath(EnumSet<SwitchColor> connectionConditions) {
        return connectionConditions.size() == 1 && connectionConditions.contains(NEUTRAL);
    }

    private static void validateByte(byte state) throws IllegalArgumentException {
        if (inputOutOfRange(state))
            throw new IllegalArgumentException("Input out of range");
    }

    public static boolean isFirstStepDone() {
        return secondaryStartLocation != -1;
    }

    public static void reset() {
        secondaryStartLocation = -1;
    }
}
