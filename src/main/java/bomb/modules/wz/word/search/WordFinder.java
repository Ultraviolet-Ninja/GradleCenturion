package bomb.modules.wz.word.search;

import bomb.tools.Coordinates;
import bomb.tools.data.structures.trie.Trie;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

@SuppressWarnings("SuspiciousNameCombination")
public final class WordFinder {
    private static int currentGridLength;

    public static Optional<Pair<@NotNull Coordinates, @NotNull Coordinates>> findWordCoordinates(
            char[] @NotNull [] grid, @NotNull Set<String> possibleWords) {
        validate(grid);

        Coordinates startPosition, endPosition;
        Trie trie = new Trie(possibleWords);
        currentGridLength = grid.length;

        int longestWordLength = possibleWords.stream()
                .mapToInt(String::length)
                .max()
                .orElse(currentGridLength);
        longestWordLength = Math.min(longestWordLength, currentGridLength);

        for (int i = 0; i < currentGridLength; i++) {
            for (int j = 0; j < currentGridLength; j++) {
                startPosition = new Coordinates(j, i);
                endPosition = searchLocation(grid, i, j, trie, longestWordLength);

                if (endPosition != null)
                    return Optional.of(new Pair<>(startPosition, endPosition));
            }
        }

        return Optional.empty();
    }

    private static void validate(char[][] grid) throws IllegalArgumentException {
        int width = grid.length;

        for (char[] row : grid) {
            if (row.length != width)
                throw new IllegalArgumentException("Provided grid is not a square");
        }
    }

    private static Coordinates searchLocation(char[][] grid, int x, int y, Trie trie, int longestWordLength) {
        StringBuilder constructedWord = new StringBuilder().append(grid[x][y]);

        if (trie.containsWord(constructedWord.toString()))
            return new Coordinates(y, x);

        Coordinates result;
        for (int dX = -1; dX <= 1; dX++) {
            for (int dY = -1; dY <= 1; dY++) {
                if (dX == 0 && dY == 0) continue;

                result = searchWithVector(grid, x, y, dX, dY, constructedWord, trie, longestWordLength);
                if (result != null) return result;
                else constructedWord.setLength(1);
            }
        }

        return null;
    }

    private static Coordinates searchWithVector(char[][] grid, int x, int y,  int dX, int dY,
                                                StringBuilder constructedWord, Trie trie, int longestWordLength) {
        x += dX;
        y += dY;

        if (isOutOfRange(x, y))
            return null;
        constructedWord.append(grid[x][y]);
        if (constructedWord.length() > longestWordLength)
            return null;
        if (trie.containsWord(constructedWord.toString()))
            return new Coordinates(y, x);

        return searchWithVector(grid, x, y, dX, dY, constructedWord, trie, longestWordLength);
    }

    private static boolean isOutOfRange(int x, int y) {
        return x < 0 || y < 0 || x >= currentGridLength || y >= currentGridLength;
    }
}
