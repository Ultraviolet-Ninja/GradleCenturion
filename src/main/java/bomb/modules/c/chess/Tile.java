package bomb.modules.c.chess;

public final class Tile {
    public enum TileColor {
        BLACK, WHITE
    }

    private final TileColor tileColor;

    private boolean isCovered;
    private ChessPiece currentPiece;

    public Tile(TileColor tileColor) {
        isCovered = false;
        currentPiece = null;
        this.tileColor = tileColor;
    }

    public TileColor getTileColor() {
        return tileColor;
    }

    public boolean isCovered() {
        return isCovered;
    }

    public void coverTile() {
        isCovered = true;
    }

    public ChessPiece getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(ChessPiece currentPiece) {
        this.currentPiece = currentPiece;
    }

    @Override
    public String toString() {
        return tileColor + " Tile - Piece: " + currentPiece + " - Covered: " + isCovered;
    }
}
