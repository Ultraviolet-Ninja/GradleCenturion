package bomb.modules.c.chess;

import bomb.Widget;
import bomb.modules.s.souvenir.Souvenir;
import bomb.tools.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Chess extends Widget {
    public static final String VALIDITY_REGEX = "[A-Fa-f]-?[1-6]";

    private static final char INT_CONVERSION_LETTER = 'A';

    public static String solve(List<String> inputCoordinateList) throws IllegalArgumentException, IllegalStateException {
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

            int x = xCoordinate - INT_CONVERSION_LETTER;
            int y = ChessBoard.BOARD_LENGTH - Character.getNumericValue(yCoordinate);
            output.add(new Coordinates(x, y));
        }
        return output;
    }

    private static ChessBoard createBoard(List<Coordinates> coordinateList) {
        ChessBoard board = new ChessBoard();
        List<ChessPiece> setPieces = createEmptyList();
        setFourthPosition(board, setPieces, coordinateList.get(3));
        setFifthPosition(board, setPieces, coordinateList.get(4));
        setFirstPosition(board, setPieces, coordinateList.get(0));
        setSecondPosition(board, setPieces, coordinateList.get(1));
        setThirdPosition(board, setPieces, coordinateList.get(2));
        setSixthPosition(board, setPieces, coordinateList.get(5));
        return board;
    }

    private static List<ChessPiece> createEmptyList() {
        List<ChessPiece> output = new ArrayList<>(ChessBoard.BOARD_LENGTH);
        for (int i = 0 ; i < ChessBoard.BOARD_LENGTH; i++)
            output.add(null);
        return output;
    }

    private static void setFirstPosition(ChessBoard board, List<ChessPiece> setPieces, Coordinates coordinates) {
        boolean isQueenAtPosition = setPieces.get(4) == ChessPiece.QUEEN;
        ChessPiece pieceToSet = isQueenAtPosition ? ChessPiece.KING : ChessPiece.BISHOP;

        setPieces.set(0, pieceToSet);
        board.setPieceAtLocation(pieceToSet, coordinates);
    }

    private static void setSecondPosition(ChessBoard board, List<ChessPiece> setPieces, Coordinates coordinates) {
        boolean isLastDigitOdd = getSerialCodeLastDigit() % 2 == 1;
        ChessPiece pieceToSet = isLastDigitOdd ? ChessPiece.ROOK : ChessPiece.KNIGHT;

        setPieces.set(1, pieceToSet);
        board.setPieceAtLocation(pieceToSet, coordinates);
    }

    private static void setThirdPosition(ChessBoard board, List<ChessPiece> setPieces, Coordinates coordinates) {
        int firstRookIndex = setPieces.indexOf(ChessPiece.ROOK);
        int secondRookIndex = setPieces.lastIndexOf(ChessPiece.ROOK);
        boolean lessThanTwoRooks = secondRookIndex - firstRookIndex == 0;

        ChessPiece pieceToSet = lessThanTwoRooks ? ChessPiece.QUEEN : ChessPiece.KING;

        setPieces.set(2, pieceToSet);
        board.setPieceAtLocation(pieceToSet, coordinates);
    }

    private static void setFourthPosition(ChessBoard board, List<ChessPiece> setPieces, Coordinates coordinates) {
        //Rook is always set at fourth position
        setPieces.set(3, ChessPiece.ROOK);
        board.setPieceAtLocation(ChessPiece.ROOK, coordinates);
    }

    private static void setFifthPosition(ChessBoard board, List<ChessPiece> setPieces, Coordinates coordinates) {
        boolean isWhiteTile = board.getTile(coordinates).getTileColor() == Tile.TileColor.WHITE;
        ChessPiece pieceToSet = isWhiteTile ? ChessPiece.QUEEN : ChessPiece.ROOK;

        setPieces.set(4, pieceToSet);
        board.setPieceAtLocation(pieceToSet, coordinates);
    }

    private static void setSixthPosition(ChessBoard board, List<ChessPiece> setPieces, Coordinates coordinates) {
        boolean hasNoQueensOnBoard = !setPieces.contains(ChessPiece.QUEEN);
        boolean hasNoKnightsOnBoard = !setPieces.contains(ChessPiece.KNIGHT);

        ChessPiece pieceToSet = hasNoQueensOnBoard ? ChessPiece.QUEEN :
                hasNoKnightsOnBoard ? ChessPiece.KNIGHT :
                        ChessPiece.BISHOP;

        setPieces.set(5, pieceToSet);
        board.setPieceAtLocation(pieceToSet, coordinates);
    }

    private static void validateList(List<String> inputCoordinateList) throws IllegalArgumentException {
        checkUniqueness(inputCoordinateList);
        if (inputCoordinateList.contains(""))
            throw new IllegalArgumentException("Every space must be filled with a move");

        for (String chessCoordinate : inputCoordinateList) {
            if (!chessCoordinate.matches(VALIDITY_REGEX))
                throw new IllegalArgumentException("Coordinate doesn't match the specified format");
        }
    }

    private static void checkUniqueness(List<String> inputCoordinateList) {
        Set<String> uniqueCoordinateChecker = inputCoordinateList.stream()
                .map(chessCoordinate -> chessCoordinate.toUpperCase().replace("-", ""))
                .collect(Collectors.toSet());
        if (uniqueCoordinateChecker.size() != ChessBoard.BOARD_LENGTH)
            throw new IllegalArgumentException("Size mismatch");
    }

    private static String convertToChessNotation(Coordinates uncoveredLocation) {
        char horizontal = (char) (INT_CONVERSION_LETTER + uncoveredLocation.getX());
        String vertical = String.valueOf(ChessBoard.BOARD_LENGTH - uncoveredLocation.getY());
        return horizontal + "-" + vertical;
    }

    private static void sendToSouvenir(List<String> inputCoordinateList) {
        String interpretedCoordinates = inputCoordinateList.toString().replaceAll("[\\[\\]]", "");
        Souvenir.addRelic("Original Chess Coordinates", interpretedCoordinates);
    }
}
