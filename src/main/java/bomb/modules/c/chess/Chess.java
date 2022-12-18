package bomb.modules.c.chess;

import bomb.Widget;
import bomb.modules.s.souvenir.Souvenir;
import bomb.tools.Coordinates;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static bomb.modules.c.chess.ChessBoard.BOARD_LENGTH;
import static bomb.modules.c.chess.ChessPiece.BISHOP;
import static bomb.modules.c.chess.ChessPiece.KING;
import static bomb.modules.c.chess.ChessPiece.KNIGHT;
import static bomb.modules.c.chess.ChessPiece.QUEEN;
import static bomb.modules.c.chess.ChessPiece.ROOK;
import static bomb.modules.c.chess.Tile.TileColor.WHITE;
import static bomb.tools.string.StringFormat.INDEX_ZERO_UPPERCASE_LETTER;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toUnmodifiableSet;

public final class Chess extends Widget {
    @Language("regexp")
    public static final String VALIDITY_PATTERN;

    static {
        VALIDITY_PATTERN = "[A-Fa-f]-?[1-6]";
    }

    public static @NotNull String solve(@NotNull List<String> inputCoordinateList)
            throws IllegalArgumentException, IllegalStateException {
        checkSerialCode();
        validateList(inputCoordinateList);

        if (isSouvenirActive)
            sendToSouvenir(inputCoordinateList);

        List<Coordinates> graphCoordinateList = convertNotation(inputCoordinateList);
        ChessBoard board = createBoard(graphCoordinateList);
        Coordinates uncoveredLocation = CoverageCalculator.findNonCoveredTileLocation(board);
        return convertToChessNotation(uncoveredLocation);
    }

    private static List<Coordinates> convertNotation(List<String> inputCoordinateList) {
        List<Coordinates> output = new ArrayList<>();

        for (String chessCoordinate : inputCoordinateList) {
            char xCoordinate = chessCoordinate.toUpperCase().charAt(0);
            char yCoordinate = chessCoordinate.charAt(chessCoordinate.length() - 1);

            int x = xCoordinate - INDEX_ZERO_UPPERCASE_LETTER;
            int y = BOARD_LENGTH - Character.getNumericValue(yCoordinate);
            output.add(new Coordinates(x, y));
        }
        return output;
    }

    private static ChessBoard createBoard(List<Coordinates> coordinateList) {
        ChessBoard board = new ChessBoard();
        List<ChessPiece> setPieces = new ArrayList<>(
                asList(new ChessPiece[]{null, null, null, null, null, null})
        );

        setFourthPosition(board, setPieces, coordinateList.get(3));
        setFifthPosition(board, setPieces, coordinateList.get(4));
        setFirstPosition(board, setPieces, coordinateList.get(0));
        setSecondPosition(board, setPieces, coordinateList.get(1));
        setThirdPosition(board, setPieces, coordinateList.get(2));
        setSixthPosition(board, setPieces, coordinateList.get(5));
        return board;
    }

    private static void setFirstPosition(ChessBoard board, List<ChessPiece> setPieces, Coordinates coordinates) {
        boolean isQueenAtPosition = setPieces.get(4) == QUEEN;
        ChessPiece pieceToSet = isQueenAtPosition ? KING : BISHOP;

        setPieces.set(0, pieceToSet);
        board.setPieceAtLocation(pieceToSet, coordinates);
    }

    private static void setSecondPosition(ChessBoard board, List<ChessPiece> setPieces, Coordinates coordinates) {
        boolean isLastDigitOdd = getSerialCodeLastDigit() % 2 == 1;
        ChessPiece pieceToSet = isLastDigitOdd ? ROOK : KNIGHT;

        setPieces.set(1, pieceToSet);
        board.setPieceAtLocation(pieceToSet, coordinates);
    }

    private static void setThirdPosition(ChessBoard board, List<ChessPiece> setPieces, Coordinates coordinates) {
        int firstRookIndex = setPieces.indexOf(ROOK);
        int secondRookIndex = setPieces.lastIndexOf(ROOK);
        boolean lessThanTwoRooks = secondRookIndex - firstRookIndex == 0;

        ChessPiece pieceToSet = lessThanTwoRooks ? QUEEN : KING;

        setPieces.set(2, pieceToSet);
        board.setPieceAtLocation(pieceToSet, coordinates);
    }

    private static void setFourthPosition(ChessBoard board, List<ChessPiece> setPieces, Coordinates coordinates) {
        //Rook is always set at fourth position
        setPieces.set(3, ROOK);
        board.setPieceAtLocation(ROOK, coordinates);
    }

    private static void setFifthPosition(ChessBoard board, List<ChessPiece> setPieces, Coordinates coordinates) {
        boolean isWhiteTile = board.getTile(coordinates).getTileColor() == WHITE;
        ChessPiece pieceToSet = isWhiteTile ? QUEEN : ROOK;

        setPieces.set(4, pieceToSet);
        board.setPieceAtLocation(pieceToSet, coordinates);
    }

    private static void setSixthPosition(ChessBoard board, List<ChessPiece> setPieces, Coordinates coordinates) {
        boolean hasNoQueensOnBoard = !setPieces.contains(QUEEN);
        boolean hasNoKnightsOnBoard = !setPieces.contains(KNIGHT);

        ChessPiece pieceToSet = hasNoQueensOnBoard ? QUEEN :
                hasNoKnightsOnBoard ?
                        KNIGHT :
                        BISHOP;

        setPieces.set(5, pieceToSet);
        board.setPieceAtLocation(pieceToSet, coordinates);
    }

    private static void validateList(List<String> inputCoordinateList) throws IllegalArgumentException {
        checkUniqueness(inputCoordinateList);
        if (inputCoordinateList.contains(""))
            throw new IllegalArgumentException("Every space must be filled with a move");

        for (String chessCoordinate : inputCoordinateList) {
            if (!chessCoordinate.matches(VALIDITY_PATTERN))
                throw new IllegalArgumentException("Coordinate doesn't match the specified format");
        }
    }

    private static void checkUniqueness(List<String> inputCoordinateList) {
        Set<String> uniqueCoordinateChecker = inputCoordinateList.stream()
                .map(chessCoordinate -> chessCoordinate.toUpperCase().replace("-", ""))
                .collect(toUnmodifiableSet());
        if (uniqueCoordinateChecker.size() != BOARD_LENGTH)
            throw new IllegalArgumentException("Not all positions were unique. Please remove duplicates");
    }

    private static String convertToChessNotation(Coordinates uncoveredLocation) {
        char horizontal = (char) (INDEX_ZERO_UPPERCASE_LETTER + uncoveredLocation.x());
        String vertical = String.valueOf(BOARD_LENGTH - uncoveredLocation.y());
        return horizontal + "-" + vertical;
    }

    private static void sendToSouvenir(List<String> inputCoordinateList) {
        String interpretedCoordinates = inputCoordinateList.toString().replaceAll("[\\[\\]]", "");
        Souvenir.addRelic("Original Chess Coordinates", interpretedCoordinates);
    }
}
