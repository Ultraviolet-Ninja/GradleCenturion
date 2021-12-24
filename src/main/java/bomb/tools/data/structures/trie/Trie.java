package bomb.tools.data.structures.trie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public Trie(Collection<String> startWords) {
        this();
        addWords(startWords);
    }

    public void addWords(Collection<String> words) {
        for (String word : words)
            addWord(word);
    }

    public void addWord(final String word) {
        addWord(word.toLowerCase(), 0, root);
    }

    private void addWord(final String word, int index, TrieNode currentNode) {
        if (index == word.length()) {
            currentNode.setEndOfWord(true);
            return;
        }
        char nextChar = word.charAt(index);

        if (currentNode.doesNotContainChild(nextChar))
            currentNode.addConnection(nextChar);

        addWord(word, index + 1, currentNode.getNextNode(nextChar));
    }

    public Set<String> getWordsWithPrefix(String prefix) {
        List<String> words = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        TrieNode currentNode = root;

        if (!prefix.isEmpty()) {
            for (char nextChar : prefix.toLowerCase().toCharArray()) {
                if (currentNode.doesNotContainChild(nextChar))
                    return null;

                currentNode = currentNode.getNextNode(nextChar);
                builder.append(nextChar);
                addIfIsWord(words, builder, currentNode);
            }
        }

        if (!currentNode.hasNoChild())
            words.addAll(getWordsWithPrefix(currentNode, builder));

        return new LinkedHashSet<>(words);
    }

    private List<String> getWordsWithPrefix(TrieNode currentNode, StringBuilder builder) {
        List<String> words = new ArrayList<>();

        if (currentNode.hasNoChild())
            return words;

        if (currentNode.getChildCount() == 1) {
            Character nextChar = currentNode.firstChild();
            currentNode = currentNode.getNextNode(nextChar);
            builder.append(nextChar);

            addIfIsWord(words, builder, currentNode);
            words.addAll(getWordsWithPrefix(currentNode, builder));
            return words;
        }

        for (Character nextChar : currentNode.getChildSet()) {
            StringBuilder clonedBuilder = new StringBuilder(builder).append(nextChar);
            TrieNode nextNode = currentNode.getNextNode(nextChar);

            addIfIsWord(words, clonedBuilder, nextNode);
            words.addAll(getWordsWithPrefix(nextNode, clonedBuilder));
        }

        return words;
    }

    private void addIfIsWord(List<String> words, StringBuilder builder, TrieNode currentNode) {
        if (currentNode.isEndOfWord)
            words.add(builder.toString());
    }

    public boolean search(String word) {
        if (word == null || word.isEmpty())
            return false;

        TrieNode currentNode = root;
        for (char letter : word.toCharArray()) {
            currentNode = currentNode.getNextNode(letter);
            if (currentNode == null)
                return false;
        }
        return currentNode.isEndOfWord;
    }

    @Override
    public String toString() {
        return getWordsWithPrefix("").toString();
    }

    private static class TrieNode {
        private final Map<Character, TrieNode> children;
        private boolean isEndOfWord;

        public TrieNode() {
            children = new HashMap<>(5);
            isEndOfWord = false;
        }

        public Set<Character> getChildSet() {
            return children.keySet();
        }

        public void addConnection(char c) {
            children.put(c, new TrieNode());
        }

        public TrieNode getNextNode(char c) {
            return children.get(c);
        }

        public boolean doesNotContainChild(char c) {
            return !children.containsKey(c);
        }

        public int getChildCount() {
            return children.size();
        }

        public boolean hasNoChild() {
            return children.isEmpty();
        }

        public Character firstChild() {
            return children.keySet().iterator().next();
        }

        public void setEndOfWord(boolean isEndOfWord) {
            this.isEndOfWord = isEndOfWord;
        }
    }
}
