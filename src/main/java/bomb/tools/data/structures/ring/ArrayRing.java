package bomb.tools.data.structures.ring;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public final class ArrayRing<E> implements Iterable<E> {
    private final List<E> internalStructure;

    private int headIndex;

    public ArrayRing(int startingCapacity) {
        if (startingCapacity < 1)
            throw new IllegalArgumentException();
        internalStructure = new ArrayList<>(startingCapacity);
        headIndex = 0;
    }

    @SafeVarargs
    public ArrayRing(E @NotNull ... elements) {
        this(elements.length);
        internalStructure.addAll(asList(elements));
    }

    public ArrayRing(@NotNull Collection<E> c) {
        if (c.isEmpty())
            throw new IllegalArgumentException();
        internalStructure = c instanceof ArrayList ?
                (List<E>) c :
                new ArrayList<>(c);
        headIndex = 0;
    }

    public void add(E element) {
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
        int size = internalStructure.size();
        return (size - Math.abs(foundIndex - headIndex)) % size;
    }

    public int getCurrentIndex() {
        return headIndex;
    }

    public E getHeadData() {
        return internalStructure.get(headIndex);
    }

    public int getSize() {
        return internalStructure.size();
    }

    public void rotateClockwise() {
        headIndex++;
        headIndex %= internalStructure.size();
    }

    public void rotateClockwise(int rotations) {
        headIndex += rotations;
        headIndex %= internalStructure.size();
    }

    public void rotateCounterClockwise() {
        if (--headIndex < 0) headIndex += internalStructure.size();
    }

    public void rotateCounterClockwise(int rotations) {
        headIndex -= rotations;
        while (headIndex < 0) headIndex += internalStructure.size();
    }

    @Override
    public @NotNull Iterator<E> iterator() {
        return headIndex == 0 ?
                internalStructure.iterator() :
                reorderList().iterator();
    }

    public Stream<E> stream() {
        return headIndex == 0 ?
                internalStructure.stream() :
                reorderList().stream();
    }

    private List<E> reorderList() {
        List<E> temp = internalStructure.subList(headIndex, internalStructure.size());
        temp.addAll(internalStructure.subList(0, headIndex));
        return temp;
    }

    @Override
    public String toString() {
        return String.format("Head Index: %d for %s", headIndex, internalStructure);
    }

    public void setToIndex(E element) {
        int index = findRelativeIndex(element);
        if (index != -1)
            rotateClockwise(index);
    }

    public void reset() {
        headIndex = 0;
    }
}
