package bomb.tools.data.structures;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class ChainList<E> implements Queue<E> {
    private final LinkedList<E> struct;

    public ChainList() {
        struct = new LinkedList<>();
    }

    @Override
    public int size() {
        return struct.size();
    }

    @Override
    public boolean isEmpty() {
        return struct.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return struct.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return struct.iterator();
    }

    @Override
    public Object[] toArray() {
        return struct.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) throws UnsupportedOperationException {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        struct.clear();
    }

    @Override
    public boolean offer(E e) {
        return struct.offer(e);
    }

    @Override
    public E remove() {
        return struct.remove();
    }

    @Override
    public E poll() {
        if (!struct.isEmpty()) {
            return struct.poll();
        }
        return null;
    }

    @Override
    public E element() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public E peek() {
        if (!struct.isEmpty()) {
            return struct.peek();
        }
        return null;
    }
}
