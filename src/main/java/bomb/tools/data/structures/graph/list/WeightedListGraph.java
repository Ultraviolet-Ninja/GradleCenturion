package bomb.tools.data.structures.graph.list;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class WeightedListGraph<E> extends AbstractListGraph<E> implements WeightedEdge<E>{
    private final HashMap<E, Set<E>> internal;

    public WeightedListGraph(boolean biDirectional) {
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
    public LinkedList<AbstractMap.SimpleEntry<E, Integer>> removeVertex(E vertex) {
        return null;
    }

    @Override
    public boolean addEdge(E vertex, E edge, int weight) {
        return false;
    }

    @Override
    public LinkedList<E> dijkstraAlgo(E start, E end) {
        return null;
    }
}
