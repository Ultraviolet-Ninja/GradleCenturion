package bomb.tools.data.structures.graph.list;

public abstract class AbstractListGraph<E> {
    protected final boolean biDirectional;

    public AbstractListGraph(boolean biDirectional){
        this.biDirectional = biDirectional;
    }

    public abstract boolean addVertex(E vertex);

    public abstract boolean removeEdge(E vertex, E edge);
}
