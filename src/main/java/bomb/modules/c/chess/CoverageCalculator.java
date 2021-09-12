package bomb.modules.c.chess;

import bomb.tools.Coordinates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bomb.modules.c.chess.ChessPiece.BISHOP;

public class CoverageCalculator {
    public static Coordinates findNonCoveredTileLocation(ChessBoard board) {
        for (int x = 0; x < ChessBoard.BOARD_LENGTH; x++) {
            for (int y = 0; y < ChessBoard.BOARD_LENGTH; y++) {
                Coordinates currentLocation = new Coordinates(x, y);
                ChessPiece currentPiece = board.getTile(currentLocation).getCurrentPiece();

                if (currentPiece != null) {
                    List<Coordinates> pieceMoveList = createPieceSpecificMoves(currentPiece, currentLocation);
                    Set<Coordinates> moveSet = new HashSet<>(filterOutOfBoundsMoves(pieceMoveList));
                    coverDesignatedTiles(board, moveSet);
                }
            }
        }
        return findNonCoveredTile(board);
    }

    private static Coordinates findNonCoveredTile(ChessBoard board) throws IllegalStateException {
        List<Coordinates> uncoveredTiles = new ArrayList<>();

        for (int x = 0; x < ChessBoard.BOARD_LENGTH; x++) {
            for (int y = 0; y < ChessBoard.BOARD_LENGTH; y++) {
                if (board.isTileCovered(x, y))
                    uncoveredTiles.add(new Coordinates(x, y));
            }
        }

        if (uncoveredTiles.size() > 1)
            throw new IllegalStateException("There should only be one uncovered tile");
        return uncoveredTiles.get(0);
    }

    private static void coverDesignatedTiles(ChessBoard board, Set<Coordinates> moveSet) {
        for (Coordinates move : moveSet)
            board.setTileCovered(move);
    }

    private static List<Coordinates> filterOutOfBoundsMoves(List<Coordinates> moveList) {
        return moveList.stream()
                .filter(coordinates -> coordinates.getX() >= 0 && coordinates.getX() < ChessBoard.BOARD_LENGTH)
                .filter(coordinates -> coordinates.getY() >= 0 && coordinates.getY() < ChessBoard.BOARD_LENGTH)
                .collect(Collectors.toList());
    }

    private static List<Coordinates> createPieceSpecificMoves(ChessPiece piece, Coordinates position) {
        List<Coordinates> outputList = new ArrayList<>();

        switch (piece) {
            case KING:
                outputList.addAll(createAdjacentMoves(position));
                break;
            case KNIGHT:
                outputList.addAll(createKnightMoves(position));
                break;
            case QUEEN:
            case BISHOP:
                outputList.addAll(createDiagonalMoves(position));
                if (piece == BISHOP) break;
            default:
                outputList.addAll(createFileMoves(position));
                outputList.addAll(createColumnMoves(position));
                break;
        }

        return outputList;
    }

    private static List<Coordinates> createFileMoves(Coordinates originalPosition) {
        List<Coordinates> outputList = new ArrayList<>();
        return outputList;
    }

    private static List<Coordinates> createColumnMoves(Coordinates originalPosition) {
        List<Coordinates> outputList = new ArrayList<>();
        return outputList;
    }

    private static List<Coordinates> createDiagonalMoves(Coordinates originalPosition) {
        List<Coordinates> outputList = new ArrayList<>();
        return outputList;
    }

    private static List<Coordinates> createAdjacentMoves(Coordinates originalPosition) {
        List<Coordinates> outputList = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                Coordinates newCoordinates = originalPosition.immutableAdd(new Coordinates(x, y));
                outputList.add(newCoordinates);
            }
        }

        return outputList;
    }

    private static List<Coordinates> createKnightMoves(Coordinates originalPosition) {
        List<Coordinates> outputList = new ArrayList<>();
        return outputList;
    }
}
