package bomb.tools.data.structures;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/**
 * This class acts as a finite stack with an array as the internal storage
 * to contain a specified set of objects.
 *
 * @param <E> Any object
 */
public class BufferedQueue<E> implements Collection<E>, List<E>, Iterable<E>, RandomAccess {
    private final int capacity;
    private final E[] data;
    private final LinkedList<E> linkedData;

    private int size;

    public BufferedQueue(int capacity) {
        this.capacity = capacity;
        data = (E[]) new Object[capacity];
        linkedData = new LinkedList<>();
        size = 0;
    }

    @Override
    public E get(int index) throws ArrayIndexOutOfBoundsException {
        if (index >= 0)
            return data[index];
        throw new ArrayIndexOutOfBoundsException();
    }

    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return linkedData.contains(o);
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public boolean add(E o) {
        if (!isFull()) {
            data[size++] = o;
            linkedData.add(o);
        }
        return isFull();
    }

    /**
     * Removes a number of elements from array of data
     *
     * @param numberOfElements The number of elements to remove
     */
    public void removeFromHead(int numberOfElements) throws IllegalArgumentException {
        if (numberOfElements <= 0) return;
        if (numberOfElements < capacity) {
            removeElements(numberOfElements);
            overwrite();
        } else
            throw new IllegalArgumentException("The removal number is larger than the capacity");
    }

    private void removeElements(int num) {
        for (int i = 0; i < num; i++) {
            linkedData.removeFirst();
            data[size-- - 1] = null;
        }
    }

    private void overwrite() {
        int index = 0;
        for (E element : linkedData)
            data[index++] = element;
    }

    @Override
    public Iterator<E> iterator() {
        return Arrays.asList(data).iterator();
    }

    @Override
    public Object[] toArray() {
        return data;
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
    public ListIterator<E> listIterator() {
        return Arrays.asList(data).listIterator();
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
    public Object[] toArray(Object[] a) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
