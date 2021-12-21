package bomb.tools.data.structures.graph.list;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class ListGraph<E> extends AbstractGraph<E> implements UnweightedEdge<E> {
    private final LinkedHashMap<E, ArrayList<E>> list;

    public ListGraph(boolean biDirectional) {
        super(biDirectional);
        list = new LinkedHashMap<>();
    }

    @Override
    public boolean addVertex(E vertex) {
        if (list.containsKey(vertex)) return false;
        list.put(vertex, new ArrayList<>());
        return true;
    }

    @Override
    public boolean addEdge(E vertex, E edge) {
        if (!list.containsKey(vertex))
            addVertex(vertex);
        if (biDirectional && !list.containsKey(edge))
            addVertex(edge);
        if (isNotDuplicate(vertex, edge))
            list.get(vertex).add(edge);
        if (biDirectional && isNotDuplicate(edge, vertex))
            list.get(edge).add(vertex);
        return true;
    }

    public List<E> get(E vertex) {
        return list.get(vertex);
    }

    @Override
    public LinkedList<E> shortestPath(E start, E end) {
        return null;
    }

    private boolean isNotDuplicate(E vertex, E edge) {
        return !list.get(vertex).contains(edge);
    }

    @Override
    public List<E> removeVertex(E vertex) {
        if (!list.containsKey(vertex)) return null;
        if (biDirectional) removeReferences(vertex, list.get(vertex));
        return list.remove(vertex);
    }

    private void removeReferences(E vertex, List<E> refList) {
        for (E reference : refList)
            list.get(reference).remove(vertex);
    }

    @Override
    public boolean removeEdge(E vertex, E edge) {
        if (list.containsKey(vertex))
            return list.get(vertex).remove(edge);
        return false;
    }
}
