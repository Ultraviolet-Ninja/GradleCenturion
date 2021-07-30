package bomb.tools.data.structures.ring;

import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ReadOnlyRing<E> implements Iterable<E> {
    private final List<E> internalStructure;
    private final int capacity;

    private int headIndex;

    public ReadOnlyRing(int capacity){
        if (capacity < 1)
            throw new IllegalArgumentException();
        internalStructure = new ArrayList<>(capacity);
        this.capacity = capacity;
        headIndex = 0;
    }

    @SafeVarargs
    public ReadOnlyRing(E ... elements){
        this(elements.length);
        for (int i = 0; i < capacity; i++)
            add(elements[i]);
    }

    public ReadOnlyRing(Collection<E> c){
        if (c.size() < 1)
            throw new IllegalArgumentException();
        internalStructure = new ArrayList<>(c);
        capacity = c.size();
        headIndex = 0;
    }

    public void add(E element){
        if (internalStructure.size() == capacity)
            throw new BufferOverflowException();
        internalStructure.add(element);
    }

    public int findAbsoluteIndex(E element){
        return internalStructure.indexOf(element);
    }

    public int findRelativeIndex(E element){
        int foundIndex = internalStructure.indexOf(element);
        if (foundIndex == -1)
            return foundIndex;
        return (capacity - Math.abs(foundIndex - headIndex)) % capacity;
    }

    public E getHeadData(){
        return internalStructure.get(headIndex);
    }

    public void rotateClockwise(){
        headIndex++;
        headIndex %= capacity;
    }

    public void rotateCounterClockwise(){
        headIndex--;
        if (headIndex < 0) headIndex += capacity;
    }

    @Override
    public Iterator<E> iterator() {
        return internalStructure.iterator();
    }
}
