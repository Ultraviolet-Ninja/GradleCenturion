package bomb.tools.data.structures.graph.list;

import java.util.AbstractMap;
import java.util.LinkedList;

public interface WeightedEdge<E> {
    LinkedList<AbstractMap.SimpleEntry<E, Integer>> removeVertex(E vertex);
    boolean addEdge(E vertex, E edge, int weight);
    LinkedList<E> dijkstraAlgo(E start, E end);
}
