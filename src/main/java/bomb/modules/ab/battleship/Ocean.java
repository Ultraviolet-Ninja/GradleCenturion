package bomb.modules.ab.battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            for (Tile tile : column) {
                builder.append(tile).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
