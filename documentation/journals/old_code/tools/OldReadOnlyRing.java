package bomb.tools.data.structures.ring;

import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Deprecated
public class OldReadOnlyRing<E> implements Iterable<E> {
    private static class Node<E> {
        private final E data;
        private Node<E> prev, next;

        private Node(E data) {
            this.data = data;
            prev = next = null;
        }
    }

    private final int capacity;
    private boolean isCompletedRing;
    private int size;
    private Node<E> head;

    public OldReadOnlyRing(int capacity) {
        this.capacity = capacity;
        size = 0;
        head = null;
        isCompletedRing = false;
    }

    @SafeVarargs
    public OldReadOnlyRing(E ... elements){
        capacity = elements.length;
        size = 0;
        head = null;
        isCompletedRing = false;
        for (int i = 0; i < capacity; i++)
            add(elements[i]);
    }

    public boolean add(E element) throws BufferOverflowException {
        if (!isCompletedRing) {
            if (size == 0)
                head = new Node<>(element);
            else if (size == capacity - 1)
                addLast(element);
            else if (size != capacity)
                addNext(element);
            else throw new BufferOverflowException();
            size++;
        }
        return size != capacity;
    }

    private void addNext(E element) {
        Node<E> temp = head;
        while (temp.next != null) temp = temp.next;
        temp.next = new Node<>(element);
        temp.next.prev = temp;
    }

    private void addLast(E element) {
        Node<E> temp = head;
        while (temp.next != null) temp = temp.next;
        temp.next = new Node<>(element);
        temp.next.prev = temp;
        temp.next.next = head;
        head.prev = temp.next;
        isCompletedRing = true;
    }

    public int findIndex(E element) {
        Node<E> temp = head;
        for (int i = 0; i < capacity; i++) {
            if (element.equals(temp.data))
                return i;
            temp = temp.next;
        }
        return -1;
    }

    public E getHeadData() {
        return head.data;
    }

    public void rotateHeadClockwise() {
        head = head.next;
    }

    public void rotateHeadCounter() {
        head = head.prev;
    }

    public List<E> toList() {
        List<E> outputList = new ArrayList<>();
        Node<E> temp = head;
        while (temp.next != head) {
            outputList.add(temp.data);
            temp = temp.next;
        }
        return outputList;
    }

    @Override
    public Iterator<E> iterator() {
        return toList().iterator();
    }
}
