package bomb.tools.data.structures.queue;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.stream.Stream;

@SuppressWarnings({"unchecked", "NullableProblems"})
public class BufferedQueue<E> implements List<E>, Iterable<E>, RandomAccess {
    private final int capacity;
    private final ArrayDeque<E> dataDeque;

    private WeakReference<E[]> dataCache;

    public BufferedQueue(int capacity) {
        this.capacity = capacity;
        dataDeque = new ArrayDeque<>(capacity);
        dataCache = new WeakReference<>(null);
    }

    public BufferedQueue(@NotNull Collection<E> c) {
        this.capacity = c.size();
        dataDeque = new ArrayDeque<>(c);
        dataCache = new WeakReference<>(null);
    }

    @Override
    public E get(int index) throws ArrayIndexOutOfBoundsException {
        if (index >= 0)
            return cachedGet()[index];
        throw new ArrayIndexOutOfBoundsException();
    }

    public int size() {
        return dataDeque.size();
    }

    @Override
    public boolean isEmpty() {
        return dataDeque.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return dataDeque.contains(o);
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isFull() {
        return dataDeque.size() == capacity;
    }

    @Override
    public boolean add(E o) {
        if (!isFull()) dataDeque.add(o);
        return isFull();
    }

    public E removeFirst() {
        return dataDeque.pollFirst();
    }

    public List<E> removeCount(int count) throws IllegalArgumentException {
        if (count >= dataDeque.size() || count <= 0)
            throw new IllegalArgumentException("Invalid count");
        List<E> removalList = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            removalList.add(dataDeque.pollFirst());

        return removalList;
    }

    @Override
    public Iterator<E> iterator() {
        return dataDeque.iterator();
    }

    @Override
    public Stream<E> stream() {
        return dataDeque.stream();
    }

    @Override
    public Object[] toArray() {
        return dataDeque.toArray();
    }

    @Override
    public boolean remove(Object o) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public E set(int index, E element) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof BufferedQueue)) return false;
        BufferedQueue<E> other = (BufferedQueue<E>) o;
        return Arrays.equals(cachedGet(), other.cachedGet());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(cachedGet());
    }

    private E[] cachedGet() {
        E[] output = dataCache.get();
        if (output == null) {
            output = (E[]) dataDeque.toArray();
            dataCache = new WeakReference<>(output);
        }
        return output;
    }
}
