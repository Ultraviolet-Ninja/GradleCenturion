package bomb.tools.data.structures.graph.list;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class ListGraph<E> {
    private final LinkedHashMap<E, ArrayList<E>> list;
    private final boolean biDirectional;

    public ListGraph(boolean biDirectional) {
        this.biDirectional = biDirectional;
        list = new LinkedHashMap<>();
    }

    public void addVertex(E vertex) {
        if (containsVertex(vertex)) return;
        list.put(vertex, new ArrayList<>());
    }

    public void addEdge(E vertex, E edge) {
        if (!containsVertex(vertex))
            addVertex(vertex);
        if (biDirectional && !containsVertex(edge))
            addVertex(edge);
        if (isNotDuplicate(vertex, edge))
            list.get(vertex).add(edge);
        if (biDirectional && isNotDuplicate(edge, vertex))
            list.get(edge).add(vertex);
    }

    public boolean containsVertex(E vertex) {
        return list.containsKey(vertex);
    }

    public List<E> getTargetVertices(E vertex) {
        return list.get(vertex);
    }

    private boolean isNotDuplicate(E vertex, E edge) {
        return !list.get(vertex).contains(edge);
    }

    public List<E> removeVertex(E vertex) {
        if (!containsVertex(vertex)) return null;
        if (biDirectional) removeReferences(vertex, list.get(vertex));
        return list.remove(vertex);
    }

    private void removeReferences(E vertex, List<E> refList) {
        for (E reference : refList)
            list.get(reference).remove(vertex);
    }

    public boolean removeEdge(E vertex, E edge) {
        if (containsVertex(vertex))
            return list.get(vertex).remove(edge);
        return false;
    }
}
