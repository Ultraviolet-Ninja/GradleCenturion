package bomb.tools.data.structures.trie;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedMap;
import java.util.Set;
import java.util.TreeMap;

public final class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public Trie(@NotNull Collection<String> startWords) {
        this();
        addWords(startWords);
    }

    public void addWords(@NotNull Collection<String> words) {
        for (String word : words)
            addWord(word.toLowerCase(), 0, root);
    }

    public void addWord(final @NotNull String word) {
        addWord(word.toLowerCase(), 0, root);
    }

    private static void addWord(final String word, int index, TrieNode currentNode) {
        if (index == word.length()) {
            currentNode.setEndOfWord(true);
            return;
        }
        char nextChar = word.charAt(index);

        if (currentNode.doesNotContainChild(nextChar))
            currentNode.addConnection(nextChar);

        addWord(word, index + 1, currentNode.getNextNode(nextChar));
    }

    public Set<String> getWordsStartingWith(@NotNull String prefix) {
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
            words.addAll(getWordsStartingWith(currentNode, builder));

        return new LinkedHashSet<>(words);
    }

    private static List<String> getWordsStartingWith(TrieNode currentNode, StringBuilder builder) {
        if (currentNode.hasNoChild())
            return Collections.emptyList();

        List<String> words = new ArrayList<>();
        if (currentNode.getChildCount() == 1) {
            Character nextChar = currentNode.firstChild();
            currentNode = currentNode.getNextNode(nextChar);
            builder.append(nextChar);

            addIfIsWord(words, builder, currentNode);
            words.addAll(getWordsStartingWith(currentNode, builder));
            return words;
        }

        for (Character nextChar : currentNode.getChildSet()) {
            StringBuilder clonedBuilder = new StringBuilder(builder).append(nextChar);
            TrieNode nextNode = currentNode.getNextNode(nextChar);

            addIfIsWord(words, clonedBuilder, nextNode);
            words.addAll(getWordsStartingWith(nextNode, clonedBuilder));
        }

        return words;
    }

    private static void addIfIsWord(List<String> words, StringBuilder builder, TrieNode currentNode) {
        if (currentNode.isEndOfWord)
            words.add(builder.toString());
    }

    public boolean containsWord(@NotNull String word) {
        if (word.isEmpty())
            return false;
        word = word.toLowerCase();

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
        return getWordsStartingWith("").toString();
    }

    private static final class TrieNode {
        private final SequencedMap<Character, TrieNode> children;
        private boolean isEndOfWord;

        public TrieNode() {
            children = new TreeMap<>();
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
            return children.sequencedKeySet().getFirst();
        }

        public void setEndOfWord(boolean isEndOfWord) {
            this.isEndOfWord = isEndOfWord;
        }
    }
}
