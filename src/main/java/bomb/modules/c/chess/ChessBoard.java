package bomb.modules.c.chess;

import bomb.tools.Coordinates;
import org.jetbrains.annotations.NotNull;

import static bomb.modules.c.chess.Tile.TileColor.BLACK;
import static bomb.modules.c.chess.Tile.TileColor.WHITE;

public class ChessBoard {
    public static final byte BOARD_LENGTH = 6;

    private final Tile[][] board;

    public ChessBoard() {
        board = new Tile[BOARD_LENGTH][BOARD_LENGTH];
        setUpBoard();
    }

    private void setUpBoard() {
        for (int x = 0; x < BOARD_LENGTH; x++) {
            for (int y = 0; y < BOARD_LENGTH; y++) {
                board[x][y] = new Tile((x + y) % 2 == 0 ? WHITE : BLACK);
            }
        }
    }

    public void setTileCovered(@NotNull Coordinates location) {
        board[location.x()][location.y()].coverTile();
    }

    public void setPieceAtLocation(ChessPiece piece, @NotNull Coordinates location) {
        board[location.x()][location.y()].setCurrentPiece(piece);
    }

    public Tile getTile(@NotNull Coordinates location) {
        return board[location.x()][location.y()];
    }

    public boolean isTileCovered(int x, int y) {
        return board[x][y].isCovered();
    }
}
