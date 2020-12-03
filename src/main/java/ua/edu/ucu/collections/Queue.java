package ua.edu.ucu.collections;

import ua.edu.ucu.collections.immutable.ImmutableLinkedList;

import java.util.Arrays;
import java.util.Iterator;

public class Queue implements Iterable<String>{
    private ImmutableLinkedList elements;

    public Queue() {
        elements = new ImmutableLinkedList();
    }

    public Queue(Object e) {
        elements = new ImmutableLinkedList(e);
    }

    public Queue(Object[] data) {
        elements = new ImmutableLinkedList(data);
    }

    public Object peek() {
        if (elements.size() == 0) {
            return null;
        }
        return elements.getFirst();
    }


    public Object dequeue() {
        Object result = peek();
        elements.removeFirst();
        return result;
    }

    public void enqueue(Object e) {
        elements = elements.addLast(e);
    }

    public ImmutableLinkedList getElements() {
        return elements;
    }


    @Override
    public Iterator<String> iterator() {
        Object[] result = elements.toArray();
        String[] toIter = new String[result.length];
        int q = 0;
        for (int i = result.length - 1; i >= 0; i--) {
            toIter[q++] = (String) result[i];
        }
        return Arrays.asList(toIter).iterator();
    }
}
