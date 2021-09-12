package bomb.modules.c.chess;

import bomb.tools.Coordinates;

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
                board[x][y] = new Tile((x + y) % 2 == 0 ? Tile.TileColor.WHITE : Tile.TileColor.BLACK);
            }
        }
    }

    public void setTileCovered(Coordinates location) {
        board[location.getX()][location.getY()].coverTile();
    }

    public void setPieceAtLocation(ChessPiece piece, Coordinates location) {
        board[location.getX()][location.getY()].setCurrentPiece(piece);
    }

    public Tile getTile(Coordinates location) {
        return board[location.getX()][location.getY()];
    }

    public boolean isTileCovered(int x, int y) {
        return board[x][y].isCovered();
    }
}