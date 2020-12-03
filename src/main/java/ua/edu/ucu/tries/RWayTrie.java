package ua.edu.ucu.tries;

import ua.edu.ucu.collections.Queue;

public class RWayTrie implements Trie {
    private int size;
    private static int R = 26;


    private static class Node {
        private Object key;
        private Node[] children = new Node[R];


        public Node() {
            key = null;
        }
    }

    private Node root = new Node();

    private Node get(String key) {
        Node curNode = root;
        for (int i = 0; i < key.length(); i++) {
            curNode = curNode.children[key.charAt(i) - 'a'];
            if (curNode == null) return null;
        }
        return curNode;
    }


    @Override
    public void add(Tuple t) {
        root = add(root, t.term, t.weight, 0);
        size++;
    }

    private Node add(Node addNode, String term, int weight, int i) {
        if (addNode == null) {
            addNode = new Node();
        }
        if (weight == i) {
            addNode.key = weight;
            return addNode;
        }
        int index = term.charAt(i) - 'a';
        addNode.children[index] = add(addNode.children[index], term, weight, i + 1);
        return addNode;
    }


    @Override
    public boolean contains(String word) {
        return get(word) != null;
    }

    @Override
    public boolean delete(String word) {
        Node deleteNode = get(word);
        if (contains(word)) {
            assert deleteNode != null;
            deleteNode.key = null;
            return true;
        }
        return false;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        return wordsWithPrefix(s, size);
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s, int k) {
        Queue queue = new Queue();
        collect(get(s), s, queue, k);
        return queue;
    }

    private void collect(Node node, String s, Queue queue, int k) {
        if (node == null) return;
        if (node.key != null && k-- > 0) queue.enqueue(s);
        for (int i = 0; i < R; i++) {
            collect(node.children[i], s + (char) (i + 'a'), queue, k);
        }


    }

    @Override
    public int size() {
        return size;
    }


}

