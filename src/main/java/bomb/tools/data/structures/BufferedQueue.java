package bomb.tools.data.structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * This class acts as a finite stack with an array as the internal storage
 * to contain a specified set of objects.
 *
 * @param <E> Any object
 */
public class BufferedQueue<E> implements Collection<E>, List<E>, Iterable<E> {
    private final int capacity;
    private final LinkedList<E> linkedData;
    private final Object[] data;

    private int size;

    /**
     * Initializes the FinalList with a permanent capacity
     *
     * @param capacity How many objects this can hold
     */
    public BufferedQueue(int capacity) {
        this.capacity = capacity;
        data = new Object[capacity];
        linkedData = new LinkedList<>();
        size = 0;
    }

    /**
     * Returns the object at a specified location in the list
     *
     * @param index The position of the object in the list
     * @return The object at the specified index
     * @throws NumberFormatException The number given was negative
     */
    @Override
    public E get(int index) throws ArrayIndexOutOfBoundsException {
        if (index >= 0)
            return (E) data[index];
        throw new ArrayIndexOutOfBoundsException();
    }

    /**
     * Returns the current number of objects in the FinalList
     *
     * @return The int signifying the capacity
     */
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    /**
     * Returns how many objects can be stored within the FinalList
     *
     * @return The int signifying the capacity
     */
    public int cap() {
        return capacity;
    }

    /**
     * Shows whether the List is at capacity
     *
     * @return True or false whether the FinalList is full
     */
    public boolean full() {
        return size == capacity;
    }

    /**
     * Adds an element to the FinalList and returns a boolean to show whether this list
     * was completed with addition of that last object
     *
     * @param o The object to add to the FinalList
     * @return Whether the last element added made the FinalList full
     */
    @Override
    public boolean add(Object o) {
        if (!full()) {
            data[size++] = o;
            linkedData.add((E) o);
        }
        return full();
    }

    /**
     * Converts the FinalList into an expandable ArrayList
     *
     * @return The ArrayList of info
     */
    public ArrayList<E> convert() {
        return new ArrayList<>(linkedData);
    }

    /**
     * Removes a number of elements from array of data
     *
     * @param num The number of elements to remove
     */
    public void removeFromHead(int num) throws IllegalArgumentException {
        if (num <= 0) return;
        if (num < capacity) {
            removeLoop(num);
            overwrite();
        } else
            throw new IllegalArgumentException("The removal number is larger than the capacity");
    }

    private void removeLoop(int num) {
        for (int i = 0; i < num; i++) {
            linkedData.removeFirst();
            data[size-- - 1] = null;
        }
    }

    private void overwrite() {
        Iterator<E> toArray = linkedData.iterator();
        for (int j = 0; toArray.hasNext(); j++)
            data[j] = toArray.next();
    }

    @Override
    public Iterator<E> iterator() {
        return linkedData.listIterator();
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
    public boolean addAll(Collection c) throws UnsupportedOperationException {
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
        return linkedData.listIterator();
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
