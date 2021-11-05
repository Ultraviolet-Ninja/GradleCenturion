package bomb.modules.ab.battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ocean {
    public static final int BOARD_LENGTH = 5;

    private final Tile[][] gameBoard;

    public Ocean() {
        gameBoard = new Tile[BOARD_LENGTH][BOARD_LENGTH];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                gameBoard[i][j] = Tile.UNKNOWN;
            }
        }
    }

    public void setTileState(int x, int y, Tile state) {
        gameBoard[x][y] = state;
    }

    public Tile getTileState(int x, int y) {
        return gameBoard[x][y];
    }

    public List<Tile> getRow(int row) {
        return Arrays.asList(gameBoard[row]);
    }

    public List<Tile> getColumn(int column) {
        List<Tile> result = new ArrayList<>(BOARD_LENGTH);
        for (Tile[] row : gameBoard) {
            result.add(row[column]);
        }
        return result;
    }

    public void removeRadarSpots(Tile[] tiles) {
        int counter = 0;
        for (int x = 0; x < gameBoard.length; x++) {
            for (int y = 0; y < gameBoard.length; y++) {
                if (gameBoard[x][y] == Tile.RADAR) {
                    gameBoard[x][y] = tiles[counter++];
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Tile[] column : gameBoard) {
            builder.append(Arrays.stream(column)
                            .map(Enum::name)
                            .collect(Collectors.joining(", "))
            );
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Ocean))
            return false;

        Ocean other = (Ocean) o;
        for (int x = 0; x < BOARD_LENGTH; x++) {
            for (int y = 0; y < BOARD_LENGTH; y++) {
                if (gameBoard[x][y] != other.gameBoard[x][y])
                    return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(gameBoard);
    }
}
