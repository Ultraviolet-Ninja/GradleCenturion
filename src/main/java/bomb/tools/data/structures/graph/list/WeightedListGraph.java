package bomb.tools.data.structures.graph.list;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;

public class WeightedListGraph<E> extends AbstractListGraph<E> implements WeightedEdge<E>{
    private final HashMap<E, LinkedList<AbstractMap.SimpleEntry<E, Integer>>> list;

    public WeightedListGraph(boolean biDirectional){
        super(biDirectional);
        list = new HashMap<>();
    }

    @Override
    public boolean addVertex(E vertex) {
        if (list.containsKey(vertex)) return false;
        list.put(vertex, new LinkedList<>());
        return true;
    }

    @Override
    public boolean addEdge(E vertex, E edge, int weight) {
        return false;
    }

    @Override
    public LinkedList<E> dijkstraAlgo(E start, E end) {
        return null;
    }

    @Override
    public LinkedList<AbstractMap.SimpleEntry<E, Integer>> removeVertex(E vertex) {
        if (!list.containsKey(vertex)) return null;
        return list.remove(vertex);
    }

    @Override
    public boolean removeEdge(E vertex, E edge) {
        if (list.containsKey(vertex)) return true;
        return false;
    }
}
