package bomb.tools.data.structures.ring;

import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ReadOnlyRing<E> implements Iterable<E> {
    private final ArrayList<E> internalStructure;
    private final int capacity;

    private int headIndex;

    public ReadOnlyRing(int capacity) {
        if (capacity < 1)
            throw new IllegalArgumentException();
        internalStructure = new ArrayList<>(capacity);
        this.capacity = capacity;
        headIndex = 0;
    }

    @SafeVarargs
    public ReadOnlyRing(E... elements) {
        this(elements.length);
        for (E element : elements)
            add(element);
    }

    public ReadOnlyRing(Collection<E> c) {
        if (c.size() < 1)
            throw new IllegalArgumentException();
        internalStructure = new ArrayList<>(c);
        capacity = c.size();
        headIndex = 0;
    }

    public void add(E element) throws BufferOverflowException {
        if (internalStructure.size() == capacity)
            throw new BufferOverflowException();
        internalStructure.add(element);
    }

    public int findAbsoluteIndex(E element) {
        return internalStructure.indexOf(element);
    }

    public int findRelativeIndex(E element) {
        int foundIndex = internalStructure.indexOf(element);
        if (foundIndex == -1)
            return foundIndex;
        if (foundIndex >= headIndex)
            return foundIndex - headIndex;
        return (capacity - Math.abs(foundIndex - headIndex)) % capacity;
    }

    public E getHeadData() {
        return internalStructure.get(headIndex);
    }

    public int getCapacity() {
        return capacity;
    }

    public void rotateClockwise() {
        headIndex++;
        headIndex %= capacity;
    }

    public void rotateClockwise(int rotations) {
        headIndex += rotations;
        headIndex %= capacity;
    }

    public void rotateCounterClockwise() {
        if (--headIndex < 0) headIndex += capacity;
    }

    public void rotateCounterClockwise(int rotations) {
        headIndex -= rotations;
        while (headIndex < 0) headIndex += capacity;
    }

    @Override
    public Iterator<E> iterator() {
        if (headIndex == 0)
            return internalStructure.iterator();
        List<E> temp = internalStructure.subList(headIndex, capacity);
        temp.addAll(internalStructure.subList(0, headIndex));
        return temp.iterator();
    }

    @Override
    public String toString() {
        return "Head Index: " + headIndex + " for " + internalStructure.toString();
    }
}
