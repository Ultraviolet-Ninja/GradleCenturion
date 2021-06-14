package bomb.tools.data.structures.graph.list;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class ListGraph<E> extends AbstractListGraph<E> implements UnweightedEdge<E> {
    private final HashMap<E, Set<E>> internal;

    public ListGraph(boolean biDirectional) {
        super(biDirectional);
        internal = new HashMap<>();
    }

    @Override
    public boolean addVertex(E vertex) {
        return false;
    }

    @Override
    public boolean removeEdge(E vertex, E edge) {
        return false;
    }

    @Override
    public LinkedList<E> removeVertex(E vertex) {
        return null;
    }

    @Override
    public boolean addEdge(E vertex, E edge) {
        return false;
    }

    @Override
    public LinkedList<E> shortestPath(E start, E end) {
        return null;
    }
}
