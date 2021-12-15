package bomb.modules.c.colored_switches;

import bomb.modules.s.switches.Switches;
import org.javatuples.Pair;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static bomb.modules.c.colored_switches.SwitchColor.NEUTRAL;

public class ColoredSwitches extends Switches {
    private static final double WRONG_PATH_VALUE = Double.MAX_VALUE;
    private static final Graph<ColoredSwitchNode, DefaultEdge> INTERNAL_GRAPH;

    private static byte secondaryStartLocation = -1;

    static {
        try {
            INTERNAL_GRAPH = ColoredSwitchGraphFactory.makeGraph();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public static List<String> producePreemptiveMoveList(byte startingState) throws IllegalArgumentException {
        validateByte(startingState);

        List<String> outputList = new ArrayList<>();
        secondaryStartLocation = makePreemptiveMove(startingState, outputList);

        for (int i = 0; i < 2; i++)
            secondaryStartLocation = makePreemptiveMove(secondaryStartLocation, outputList);

        return outputList;
    }

    private static byte makePreemptiveMove(byte currentState, List<String> outputList) throws IllegalStateException {
        ColoredSwitchNode currentNode = getNodeByState(currentState);

        for (Byte connection : currentNode.getOutgoingConnections()) {
            Pair<SwitchColor[], Byte> edgeData = currentNode.getEdgeData(connection);

            if (isBlackPath(edgeData.getValue0())) {
                outputList.add(String.valueOf(edgeData.getValue1()));
                return connection;
            }
        }

        throw new IllegalStateException("This should be an unreachable state");
    }

    private static ColoredSwitchNode getNodeByState(byte startingState) throws IllegalStateException {
        for (ColoredSwitchNode coloredSwitchNode : INTERNAL_GRAPH.vertexSet()) {
            if (coloredSwitchNode.getState() == startingState)
                return coloredSwitchNode;
        }
        throw new IllegalStateException();
    }

    public static List<String> produceFinalMoveList(SwitchColor[] startingColors, byte desiredState) throws IllegalStateException, IllegalArgumentException {
        validateByte(desiredState);
        validateSwitchColors(startingColors);

        if (startingColors.length != BIT_LENGTH)
            throw new IllegalArgumentException("There should be 5 switches");
        if (!isFirstStepDone())
            throw new IllegalStateException("Must flip 3 switches before producing the final list");

        AStarAdmissibleHeuristic<ColoredSwitchNode> heuristic = createFinalMoveHeuristic(startingColors, desiredState);
        AStarShortestPath<ColoredSwitchNode, DefaultEdge> aStarShortestPath =
                new AStarShortestPath<>(INTERNAL_GRAPH, heuristic);

        ColoredSwitchNode startNode = getNodeByState(secondaryStartLocation);
        ColoredSwitchNode destination = getNodeByState(desiredState);

        List<ColoredSwitchNode> nodeList = aStarShortestPath.getPath(startNode, destination).getVertexList();

        return createSwitchToFlipList(nodeList);
    }

    private static AStarAdmissibleHeuristic<ColoredSwitchNode> createFinalMoveHeuristic(SwitchColor[] startingColors, byte desiredState) {
        return ((sourceVertex, targetVertex) -> {
            //Weight from source to target
            double gX = Math.abs(sourceVertex.getState() - targetVertex.getState());
            //Weight from target to desired state
            double hX = Math.abs(desiredState - targetVertex.getState());

            Pair<SwitchColor[], Byte> edgeData = sourceVertex.getEdgeData(targetVertex.getState());
            if (edgeData == null)
                return WRONG_PATH_VALUE;
            SwitchColor switchToFlip = startingColors[edgeData.getValue1()];

            if (!canFollowPath(edgeData.getValue0(), switchToFlip))
                return WRONG_PATH_VALUE;

            return gX + hX;
        });
    }

    private static List<String> createSwitchToFlipList(List<ColoredSwitchNode> path) {
        List<String> output = new ArrayList<>();

        for (int i = 0; i < path.size() - 1; i++) {
            Pair<SwitchColor[], Byte> edgeData = path.get(i).getEdgeData(path.get(i + 1).getState());
            output.add(String.valueOf(edgeData.getValue1()));
        }

        return output;
    }

    private static boolean canFollowPath(SwitchColor[] connectionConditions, SwitchColor switchColor) {
        if (isBlackPath(connectionConditions)) return true;

        for (SwitchColor possibleConnection : connectionConditions) {
            if (switchColor == possibleConnection)
                return true;
        }
        return false;
    }

    private static void validateSwitchColors(SwitchColor[] startingColors) {
        for (SwitchColor switchColor : startingColors) {
            if (switchColor == NEUTRAL)
                throw new IllegalArgumentException("All switches must have a color");
        }
    }

    private static boolean isBlackPath(SwitchColor[] connectionConditions) {
        return connectionConditions.length == 1 && connectionConditions[0] == NEUTRAL;
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
