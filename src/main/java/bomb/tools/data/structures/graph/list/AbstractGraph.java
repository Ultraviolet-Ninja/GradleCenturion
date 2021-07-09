package bomb.tools.data.structures.graph.list;

public abstract class AbstractGraph<E> {
    protected final boolean biDirectional;

    public AbstractGraph(boolean biDirectional){
        this.biDirectional = biDirectional;
    }

    public abstract boolean addVertex(E vertex);

    public abstract boolean removeEdge(E vertex, E edge);
}
