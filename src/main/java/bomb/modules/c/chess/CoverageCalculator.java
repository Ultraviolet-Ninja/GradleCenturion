package bomb.modules.c.chess;

import bomb.tools.Coordinates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static bomb.modules.c.chess.ChessPiece.BISHOP;

public class CoverageCalculator {
    public static Coordinates findNonCoveredTileLocation(ChessBoard board) {
        for (int x = 0; x < ChessBoard.BOARD_LENGTH; x++) {
            for (int y = 0; y < ChessBoard.BOARD_LENGTH; y++) {
                Coordinates currentLocation = new Coordinates(x, y);
                ChessPiece currentPiece = board.getTile(currentLocation).getCurrentPiece();

                if (currentPiece != null) {
                    List<Coordinates> pieceMoveList = createPieceSpecificMoves(board, currentPiece, currentLocation);
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
                if (!board.isTileCovered(x, y))
                    uncoveredTiles.add(new Coordinates(x, y));
            }
        }

        if (uncoveredTiles.size() != 1) {
            String moreThanOneTileError = """
                There's more than one tile uncovered.
                Are you sure you entered the positions in correctly?
                """;

            throw new IllegalStateException(uncoveredTiles.size() > 1 ?
                    moreThanOneTileError :
                    "All tiles were covered");
        }
        return uncoveredTiles.get(0);
    }

    private static void coverDesignatedTiles(ChessBoard board, Set<Coordinates> moveSet) {
        for (Coordinates move : moveSet)
            board.setTileCovered(move);
    }

    private static List<Coordinates> createPieceSpecificMoves(ChessBoard board, ChessPiece piece, Coordinates position) {
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
                outputList.addAll(createDiagonalMoves(board, position));
                if (piece == BISHOP) break;
            default:
                outputList.addAll(createStraightMoves(board, position));
                break;
        }

        return outputList;
    }

    private static List<Coordinates> createStraightMoves(ChessBoard board, Coordinates originalPosition) {
        Collection<Coordinates> leftwardMoveList = new ArrayList<>(),
                rightwardMoveList = new ArrayList<>(),
                downwardMoveList = new ArrayList<>(),
                upwardMoveList = new ArrayList<>();

        for (int i = 1; i < ChessBoard.BOARD_LENGTH; i++) {
            leftwardMoveList.add(originalPosition.immutableAdd(-i, 0));
            rightwardMoveList.add(originalPosition.immutableAdd(i, 0));
            upwardMoveList.add(originalPosition.immutableAdd(0, -i));
            downwardMoveList.add(originalPosition.immutableAdd(0, i));
        }

        List<Coordinates> out = new ArrayList<>(removeAllIllegalMoves(board, leftwardMoveList, false));
        out.addAll(removeAllIllegalMoves(board, rightwardMoveList, true));
        out.addAll(removeAllIllegalMoves(board, upwardMoveList, false));
        out.addAll(removeAllIllegalMoves(board, downwardMoveList, true));
        out.add(originalPosition);

        return out;
    }

    private static List<Coordinates> createDiagonalMoves(ChessBoard board, Coordinates originalPosition) {
        Collection<Coordinates> upLeftMoveList = new ArrayList<>(),
                upRightMoveList = new ArrayList<>(),
                downLeftMoveList = new ArrayList<>(),
                downRightMoveList = new ArrayList<>();

        for (int i = 1; i < ChessBoard.BOARD_LENGTH; i++) {
            upLeftMoveList.add(originalPosition.immutableAdd(-i, -i));
            upRightMoveList.add(originalPosition.immutableAdd(i, -i));
            downLeftMoveList.add(originalPosition.immutableAdd(-i, i));
            downRightMoveList.add(originalPosition.immutableAdd(i, i));
        }

        List<Coordinates> out = new ArrayList<>();
        out.add(originalPosition);
        out.addAll(removeAllIllegalMoves(board, upLeftMoveList, false));
        out.addAll(removeAllIllegalMoves(board, downLeftMoveList, false));
        out.addAll(removeAllIllegalMoves(board, upRightMoveList, true));
        out.addAll(removeAllIllegalMoves(board, downRightMoveList, true));

        return out;
    }

    private static List<Coordinates> createAdjacentMoves(Coordinates originalPosition) {
        List<Coordinates> outputList = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                Coordinates newCoordinates = originalPosition.immutableAdd(x, y);
                outputList.add(newCoordinates);
            }
        }

        return outputList;
    }

    private static List<Coordinates> createKnightMoves(Coordinates originalPosition) {
        List<Coordinates> outputList = new ArrayList<>();
        outputList.add(originalPosition);
        int[][] moveOutlines = new int[][]{
                {1,2}, {2,1}
        };

        for (int[] moveOutline : moveOutlines) {
            outputList.add(originalPosition.immutableAdd(moveOutline[0], -moveOutline[1]));
            outputList.add(originalPosition.immutableAdd(moveOutline[0], moveOutline[1]));
            outputList.add(originalPosition.immutableAdd(-moveOutline[0], -moveOutline[1]));
            outputList.add(originalPosition.immutableAdd(-moveOutline[0], moveOutline[1]));
        }

        return outputList;
    }

    private static Collection<Coordinates> removeAllIllegalMoves(ChessBoard board, Collection<Coordinates> coordinatesList, boolean isAscending) {
        coordinatesList = filterOutOfBoundsMoves(coordinatesList);
        return removeBlockedMoves(board, coordinatesList, isAscending);
    }

    private static List<Coordinates> filterOutOfBoundsMoves(Collection<Coordinates> moveList) {
        return moveList.stream()
                .filter(coordinates -> coordinates.getX() >= 0 && coordinates.getX() < ChessBoard.BOARD_LENGTH)
                .filter(coordinates -> coordinates.getY() >= 0 && coordinates.getY() < ChessBoard.BOARD_LENGTH)
                .collect(Collectors.toList());
    }

    private static Collection<Coordinates> removeBlockedMoves(ChessBoard board, Collection<Coordinates> coordinatesList, boolean isAscending) {
        NavigableSet<Coordinates> sortedMoves;

        if (isAscending) {
            sortedMoves = new TreeSet<>(coordinatesList);
            for (Coordinates move : sortedMoves) {
                ChessPiece currentPiece = board.getTile(move).getCurrentPiece();
                if (currentPiece != null)
                    return sortedMoves.headSet(move);
            }
            return coordinatesList;
        }
        sortedMoves = new TreeSet<>(coordinatesList).descendingSet();

        for (Coordinates move : sortedMoves) {
            ChessPiece currentPiece = board.getTile(move).getCurrentPiece();
            if (currentPiece != null)
                return sortedMoves.headSet(move);
        }
        return coordinatesList;
    }
}
