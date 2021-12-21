package bomb.tools.data.structures.graph.list;

import java.util.LinkedList;
import java.util.List;

public interface UnweightedEdge<E> {
    List<E> removeVertex(E vertex);

    boolean addEdge(E vertex, E edge);

    LinkedList<E> shortestPath(E start, E end);
}
