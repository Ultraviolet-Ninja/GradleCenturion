package bomb.modules.ab.battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static bomb.modules.ab.battleship.Tile.UNKNOWN;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class Ocean {
    public static final int BOARD_LENGTH = 5;

    private final Tile[][] gameBoard;

    public Ocean() {
        gameBoard = new Tile[BOARD_LENGTH][BOARD_LENGTH];
        initializeBoard();
    }

    public Ocean(Tile[][] board) {
        gameBoard = board;
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                gameBoard[i][j] = UNKNOWN;
            }
        }
    }

    public void setTileState(int x, int y, Tile state) {
        gameBoard[x][y] = state;
    }

    public Tile getTileState(int x, int y) {
        return gameBoard[x][y];
    }

    public List<Tile> getRow(int column) {
        return Arrays.asList(gameBoard[column]);
    }

    public List<Tile> getColumn(int row) {
        List<Tile> result = new ArrayList<>(BOARD_LENGTH);
        for (Tile[] tileRow : gameBoard) {
            result.add(tileRow[row]);
        }
        return result;
    }

    public void fillColumnsWithTile(int row, Tile tile) {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            if (gameBoard[i][row] == UNKNOWN)
                gameBoard[i][row] = tile;
        }
    }

    public void fillRowsWithTile(int column, Tile tile) {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            if (gameBoard[column][i] == UNKNOWN)
                gameBoard[column][i] = tile;
        }
    }

    public void removeRadarSpots(Tile[] tiles) {
        int counter = 0;
        for (int x = 0; x < BOARD_LENGTH; x++) {
            for (int y = 0; y < BOARD_LENGTH; y++) {
                if (gameBoard[x][y] == Tile.RADAR) {
                    gameBoard[x][y] = tiles[counter++];
                }
            }
        }
    }

    public boolean hasUnknownTile() {
        return stream(gameBoard)
                .flatMap(Arrays::stream)
                .anyMatch(tile -> tile == UNKNOWN);
    }

    public int[] countByTile() {
        int[] counters = new int[Tile.values().length];
        for (Tile[] column : gameBoard) {
            for (Tile tile : column) {
                counters[tile.ordinal()]++;
            }
        }
        return counters;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Ocean))
            return false;

        List<Tile> currentBoard = createBoardList(gameBoard);
        List<Tile> otherBoard = createBoardList(((Ocean) o).gameBoard);

        return currentBoard.equals(otherBoard);
    }

    private List<Tile> createBoardList(Tile[][] board) {
        return stream(board)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(gameBoard);
    }

    @Override
    public String toString() {
        return stream(gameBoard)
                .map(Arrays::toString)
                .map(line -> line.replaceAll("[\\[\\]]", ""))
                .map(line -> line.replaceAll(", ", "\t"))
                .collect(joining("\n"));
    }
}
