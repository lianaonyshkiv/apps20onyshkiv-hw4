package ua.edu.ucu.collections.immutable;

import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class ImmutableLinkedList implements ImmutableList {
    @Getter
    private int length;
    private Node first;
    private Map<Node, Node> connections = new LinkedHashMap<>();

    private static class Node {
        private final Object key;

        private Node(Object key) {
            this.key = key;
        }

        public Object getKey() {
            return key;
        }
    }

    public ImmutableLinkedList() {
        length = 0;
        first = null;
    }

    public ImmutableLinkedList(Object e) {
        first = new Node(e);
        connections.put(first, null);
        length = 1;
    }

    public ImmutableLinkedList(Object[] array) {
        if (array.length == 0) {
            length = 0;
            first = null;
        } else {
            first = new Node(array[0]);
            connections.put(first, null);
            length = 1;
            Node current = first;
            Object[] newArray = Arrays.copyOfRange(array, 1, array.length);
            for (Object el : newArray) {
                Node newNode = new Node(el);
                connections.put(current, newNode);
                current = newNode;
                length += 1;
            }
        }
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return add(length, e);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        boolean x = e.getClass().isArray();
        if (x) {
            return addAll(index, (Object[]) e);
        }
        return addAll(index, new Object[]{e});
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(length, c);
    }

    public ImmutableLinkedList copy() {
        ImmutableLinkedList newLinkedList = new ImmutableLinkedList();
        newLinkedList.connections = new LinkedHashMap<>(connections.size());
        Node current = first;

        for (int i = 0; i < length; i++) {
            newLinkedList.connections.put(current, connections.get(current));
            current = connections.get(current);
        }

        newLinkedList.length = length;
        newLinkedList.first = first;
        return newLinkedList;
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        ImmutableLinkedList result = copy();
        if (c.length == 0) {
            return result;
        }
        Node newNode = new Node(c[0]);
        if (index == 0) {
            result.connections.put(newNode, result.first);
            result.first = newNode;
        } else {
            if (length == 0) {
                return result;
            }
            Node previous = (Node) result.get(index - 1);
            result.connections.put(previous, newNode);
        }
        if (c.length > 1) {
            Node current = (Node) result.get(index);
            for (int i = 1; i < c.length; i++) {
                result.connections.put(current, new Node(c[i]));
                current = result.connections.get(current);
            }
        }
        result.length += c.length;
        return result;
    }

    @Override
    public Node get(int index) {
        Node[] keys = new Node[connections.size()];
        connections.keySet().toArray(keys);
        try {
            return keys[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            return connections.get(keys[index - 1]);
        }
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        ImmutableLinkedList result = copy();
        if (index > length - 1) {
            return result;
        }
        if (index == 0) {
            Node newFirst;
            try {
                newFirst = (Node) result.get(1);
            } catch (ArrayIndexOutOfBoundsException e) {
                result.first = null;
                result.length -= 1;
                result.connections.remove(result.get(index));
                return result;
            }
            result.first = newFirst;
        } else {
            Node previous = (Node) result.get(index - 1);
            result.connections.put(previous, (Node) result.get(index + 1));
        }
        result.connections.remove(result.get(index));
        result.length -= 1;
        return result;
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        if (index > length - 1) {
            throw new ArrayIndexOutOfBoundsException("False index");
        }
        ImmutableLinkedList result = copy();
        Node newNode = new Node(e);
        if (index == 0) {
            result.first = newNode;
        } else {
            result.connections.put((Node) result.get(index - 1), newNode);
            result.connections.put(newNode, (Node) result.get((index + 1)));
            result.connections.remove(result.get(index));

        }
        return result;
    }

    @Override
    public int indexOf(Object e) {
        if (length == 0) {
            throw new ArrayIndexOutOfBoundsException("Empty list");
        }
        Node[] keys = new Node[connections.size()];
        connections.keySet().toArray(keys);
        List<Object> elements = new ArrayList<>();
        for (Node el : keys) {
            elements.add(el.getKey());
        }
        elements.add(connections.get(keys[keys.length - 1]));

        return elements.indexOf(e);
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[length];
        Node current = first;
        for (int i = 0; i < length; i++) {
            result[i] = current.getKey();
            current = connections.get(current);
        }
        return result;
    }

    public ImmutableLinkedList addFirst(Object e) {
        return add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        return add(e);
    }

    public Object getFirst() {
        Node r = (Node) get(0);
        return r.getKey();

    }

    public Object getLast() {
        return get(length - 1).getKey();
    }

    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return remove(length - 1);
    }
}
