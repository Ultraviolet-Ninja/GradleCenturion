package bomb.tools.data.structures.ring;

import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class NewReadOnlyRing<E> implements Iterable<E> {
    private final List<E> internalStructure;
    private final int capacity;

    private int head;

    public NewReadOnlyRing(int capacity){
        if (capacity < 1)
            throw new IllegalArgumentException();
        internalStructure = new ArrayList<>(capacity);
        this.capacity = capacity;
        head = 0;
    }

    @SafeVarargs
    public NewReadOnlyRing(E ... elements){
        this(elements.length);
        for (int i = 0; i < capacity; i++)
            add(elements[i]);
    }

    public NewReadOnlyRing(Collection<E> c){
        if (c.size() < 1)
            throw new IllegalArgumentException();
        internalStructure = new ArrayList<>(c);
        capacity = c.size();
        head = 0;
    }

    public void add(E element){
        if (internalStructure.size() == capacity)
            throw new BufferOverflowException();
        internalStructure.add(element);
    }

    public int findIndex(E element){
        return internalStructure.indexOf(element);
    }

    public E getHeadData(){
        return internalStructure.get(head);
    }

    public void rotateClockwise(){
        head++;
        head %= capacity;
    }

    public void rotateCounterClockwise(){
        head--;
        head += capacity;
        head %= capacity;
    }

    public List<E> toList(){
        return internalStructure;
    }

    @Override
    public Iterator<E> iterator() {
        return internalStructure.iterator();
    }
}
