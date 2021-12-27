package bomb.modules.wz.word_search;

import bomb.tools.Coordinates;
import bomb.tools.data.structures.trie.Trie;
import org.javatuples.Pair;

import java.util.Set;

public class WordFinder {
    public static Pair<Coordinates, Coordinates> findWord(char[][] grid, Set<String> possibleWords) {
        validateGrid(grid);

        Trie trie = new Trie(possibleWords);



        return null;
    }

    private static void validateGrid(char[][] grid) throws IllegalArgumentException {
        int width = grid.length;

        for (char[] row : grid) {
            if (row.length != width)
                throw new IllegalArgumentException("Provided grid is not a square");
        }
    }
}
