package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;


public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie1) {
        this.trie = trie1;
    }

    public int load(String... strings) {
        String[] words;
        for (String string : strings) {
            words = string.split(" ");
            for (String s : words) {
                if (s.length() > 2) {
                    trie.add(new Tuple(s, s.length()));
                }
            }
        }
        return size();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        return trie.wordsWithPrefix(pref, k);
    }

    public int size() {
        return trie.size();
    }
}
