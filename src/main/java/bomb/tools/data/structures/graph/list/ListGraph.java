package bomb.tools.data.structures.graph.list;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedMap;
import java.util.SequencedSet;

public final class ListGraph<E> {
    private final SequencedMap<E, SequencedSet<E>> list;
    private final boolean isBidirectional;

    public ListGraph(boolean isBidirectional) {
        this.isBidirectional = isBidirectional;
        list = new LinkedHashMap<>();
    }

    public void addVertex(E vertex) {
        if (containsVertex(vertex)) return;
        list.put(vertex, new LinkedHashSet<>());
    }

    public void addEdge(E vertex, E edge) {
        if (!containsVertex(vertex))
            addVertex(vertex);
        if (isBidirectional && !containsVertex(edge))
            addVertex(edge);
        if (isNotDuplicate(vertex, edge))
            list.get(vertex).add(edge);
        if (isBidirectional && isNotDuplicate(edge, vertex))
            list.get(edge).add(vertex);
    }

    public boolean containsVertex(E vertex) {
        return list.containsKey(vertex);
    }

    public List<E> getTargetVertices(E vertex) {
        return new ArrayList<>(list.get(vertex));
    }

    private boolean isNotDuplicate(E vertex, E edge) {
        return !list.get(vertex).contains(edge);
    }

    public SequencedSet<E> removeVertex(E vertex) {
        if (!containsVertex(vertex)) return null;
        if (isBidirectional) removeReferences(vertex, list.get(vertex));
        return list.remove(vertex);
    }

    private void removeReferences(E vertex, SequencedSet<E> refList) {
        for (E reference : refList)
            list.get(reference).remove(vertex);
    }

    public boolean removeEdge(E vertex, E edge) {
        if (containsVertex(vertex))
            return list.get(vertex).remove(edge);
        return false;
    }
}
