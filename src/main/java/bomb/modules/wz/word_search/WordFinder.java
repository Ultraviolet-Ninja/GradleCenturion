package bomb.modules.wz.word_search;

import bomb.tools.Coordinates;
import bomb.tools.data.structures.trie.Trie;
import org.javatuples.Pair;

import java.util.Set;

public class WordFinder {
    public static Pair<Coordinates, Coordinates> findWord(char[][] grid, Set<String> possibleWords) {
        Trie trie = new Trie(possibleWords);



        return null;
    }
}
